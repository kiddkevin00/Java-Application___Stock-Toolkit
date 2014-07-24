/**
 * 
 */
package marcus.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * @author Marcus
 * 
 */
public class UpdateStockData {
	public String[] getAStockData(String stockName) throws IOException {
		String resultDatas = null;

		URL url = null;
		URLConnection urlConnection = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		// get stock open price, stock name, price, trade data, trade time, etc.
		String attribute = "osl1d1t1c1ohgv";
		url = new URL("http://quote.yahoo.com/d/quotes.csv?s=" + stockName
				+ "&f=" + attribute + "&e=.csv");
		urlConnection = url.openConnection();
		inputStreamReader = new InputStreamReader(
				urlConnection.getInputStream());
		bufferedReader = new BufferedReader(inputStreamReader);
		resultDatas = bufferedReader.readLine();
		// System.out.println(resultDatas);

		String[] DataArray = resultDatas.split(",");
		System.out.println("open price: " + DataArray[0]);
		System.out.println("stock name : " + DataArray[1]);
		System.out.println("price now: " + DataArray[2]);
		System.out.println("trade date : " + DataArray[3]);
		System.out.println("updated time : " + DataArray[4]);

		// or using StringTokenizer to split a string
		// StringTokenizer stringTokenizer = new StringTokenizer(resultDatas,
		// ",");
		// String openString = stringTokenizer.nextToken();
		// String stockName2 = stringTokenizer.nextToken();
		// System.out.println(openString + "  " + stockName2);

		inputStreamReader.close();
		bufferedReader.close();

		return DataArray;

	}

	public void storeADataToDynamoDB(String tableName, String[] items) {
		DynamoDBManager dbDynamoDBManager = new DynamoDBManager();
		if (tableName != null && !tableName.equals("")) {
			dbDynamoDBManager.createTable(tableName);
		}

		// Primary Key: stockName
		String[] keys = new String[] { "openPrice", "stockName",
				"currentPrice", "tradeDate", "updatedTime" };
		dbDynamoDBManager.saveAItemToDynamoDB(tableName, keys, items);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("This tool can help you find any most "
				+ "updated stock you want and store to AWS "
				+ "dynamoDB for future use");
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter a stock name : ");
		// let user input a stock name
		String stockName = input.nextLine();
		System.out
				.println("The following are the current information of the stock : "
						+ stockName);

		String dbTable = "stocks";
		int maxDataSize = 20;
		String[] stockData = new String[maxDataSize];
		UpdateStockData updateStockData = new UpdateStockData();
		try {
			stockData = updateStockData.getAStockData(stockName);
			// in case the stock doesn't exist
			if (stockData[2].equals("0.00")) {
				System.out.println("Sorry, the following stock name you "
						+ "input doesn't exist in current stock market : "
						+ stockData[1]);
				return;
			}
			// System.out.println(stockData.length);
		} catch (IOException e) {
			e.printStackTrace();
		}

		updateStockData.storeADataToDynamoDB(dbTable, stockData);

		input.close();
	}
}

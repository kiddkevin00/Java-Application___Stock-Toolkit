/**
 * 
 */
package marcus.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

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

		url = new URL("http://quote.yahoo.com/d/quotes.csv?s=" + stockName
				+ "&f=osl1d1t1c1ohgv&e=.csv");
		urlConnection = url.openConnection();
		inputStreamReader = new InputStreamReader(
				urlConnection.getInputStream());
		bufferedReader = new BufferedReader(inputStreamReader);
		resultDatas = bufferedReader.readLine();
		System.out.println(resultDatas);

		String[] DataArray = resultDatas.split(",");
		System.out.println("open : " + DataArray[0]);
		System.out.println("stock name : " + DataArray[1]);
		System.out.println("price : " + DataArray[2]);
		System.out.println("trade date : " + DataArray[3]);
		System.out.println("trade time : " + DataArray[4]);

		// or using StringTokenizer to split a string
		// StringTokenizer stringTokenizer = new StringTokenizer(resultDatas,
		// ",");
		// String openString = stringTokenizer.nextToken();
		// String stockName2 = stringTokenizer.nextToken();
		// System.out.println(openString + "  " + stockName2);

		return DataArray;

	}

	public void storeADataToDynamoDB(String tableName) {
		DynamoDBManager dbDynamoDBManager = new DynamoDBManager();
		if (tableName != null && !tableName.equals("")) {
			dbDynamoDBManager.createTable(tableName);
		}
		dbDynamoDBManager.saveAItemToDynamoDB(tableName, key1, item1, key2,
				item2);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String stockName = "GOOG";
		UpdateStockData updateStockData = new UpdateStockData();
		try {
			updateStockData.getAStockData(stockName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		updateStockData.storeADataToDynamoDB(stockName);
	}
}

/**
 * 
 */
package marcus.functions;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Marcus
 * 
 */
public class YahooStockApiLauncher {

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
				input.close();
				return;
			}
			// System.out.println(stockData.length);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// updateStockData.storeADataToDynamoDB(dbTable, stockData);

		DynamoDBManager dbDynamoDBManager = new DynamoDBManager();
		if (dbTable != null && !dbTable.equals("")) {
			dbDynamoDBManager.createTable(dbTable);
		}

		// Primary Key: stockName
		String[] keys = new String[] { "openPrice", "stockName",
				"currentPrice", "tradeDate", "updatedTime" };
		dbDynamoDBManager.saveAItemToDynamoDB(dbTable, keys, stockData);

		input.close();

		// deleteAllTable

	}

}

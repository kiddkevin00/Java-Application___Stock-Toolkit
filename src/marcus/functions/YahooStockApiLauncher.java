/**
 * 
 */
package marcus.functions;

import java.io.IOException;
import java.util.Map;
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
		Scanner input = new Scanner(System.in);
		System.out.println("(1) If you want to search for a current stock's "
				+ "information, please press 1");
		System.out.println("(2) If you want to search a stock from your "
				+ "database, please press 2");
		// set default choice
		int menuChoice = 1;
		if (input.hasNextInt()) {
			menuChoice = Integer.parseInt(input.nextLine());
		} else {
			input.nextLine();
		}
		DynamoDBManager_v2 dbDynamoDBManager = new DynamoDBManager_v2();
		String dbTable = "stocks";

		switch (menuChoice) {
		case 1:
			System.out.println("Please enter a stock name : ");
			// let user input a stock name
			String stockName = input.nextLine();
			System.out.println("searching..");
			System.out.println("The following are the current "
					+ "information of the stock : " + stockName);

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

			if (dbTable != null && !dbTable.equals("")) {
				dbDynamoDBManager.createTable(dbTable);
			}

			System.out.println("Storing and updating your Database..");
			// Primary Key: stockName
			String[] keys = new String[] { "openPrice", "stockName",
					"currentPrice", "tradeDate", "updatedTime", "itemAddedTime" };
			dbDynamoDBManager.saveAItemToDynamoDB(dbTable, keys, stockData);
			System.out.println("Update successfully");
			break;

		case 2:
			System.out
					.println("Please enter a stock name to search from your Database : ");
			String searchStockName = input.nextLine();
			searchStockName = searchStockName.toUpperCase();
			Map<String, String> foundStock = null;
			foundStock = dbDynamoDBManager.retreiveItem(dbTable, "stockName",
					searchStockName);
			if (foundStock != null) {
				System.out.println("stock name: "
						+ foundStock.get("stockName").substring(1,
								foundStock.get("stockName").length() - 1));
				System.out
						.println("open price: " + foundStock.get("openPrice"));
				System.out.println("price now: "
						+ foundStock.get("currentPrice"));
				System.out.println("trade date : "
						+ foundStock.get("tradeDate"));
				System.out.println("updated time : "
						+ foundStock.get("updatedTime"));
				break;
			}
		default:
			break;
		}

		System.out.println("This is the end of the program");
		input.close();

		// delete all table in AWS DynamoDB
		// dbDynamoDBManager.deleteAllTable();

	}
}

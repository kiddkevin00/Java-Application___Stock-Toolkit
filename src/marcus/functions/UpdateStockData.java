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

	public void getStockDataIn30Days() {

	}

}

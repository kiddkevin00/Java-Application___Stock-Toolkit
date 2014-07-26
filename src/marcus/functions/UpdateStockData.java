/**
 * 
 */
package marcus.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;

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
		// for sorting index
		DataArray[5] = Long.toString(new Date().getTime());

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

	public String[] getStockDataIn30Days() throws IOException {
		String stockName = "GOOG";
		String resultDatas = null;
		URL url = null;
		URLConnection urlConn = null;
		InputStreamReader inStream = null;
		BufferedReader buff = null;
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH);
		int day = ca.get(Calendar.DATE) - 1;
		String finalyear = String.valueOf(year);
		String finalmonth = String.valueOf(month);
		String finalday = String.valueOf(day);
		String startday = "1";
		String startmonth = finalmonth;
		String startyear = finalyear;
		System.out.println(year);
		System.out.println(month);
		System.out.println(day);
		url = new URL("http://ichart.finance.yahoo.com/table.csv?s="
				+ stockName + "&d=" + finalmonth + "&e=" + finalday + "&f="
				+ finalyear + "&g=d&a=" + startmonth + "&b=" + startday + "&c="
				+ startyear + "&ignore=.csv");
		urlConn = url.openConnection();
		inStream = new InputStreamReader(urlConn.getInputStream());
		buff = new BufferedReader(inStream);
		resultDatas = buff.readLine();
		// read the first line for column name
		System.out.println(resultDatas);
		// read next line for actual data
		resultDatas = buff.readLine();
		System.out.println(resultDatas);

		String[] DataArray = resultDatas.split(",");

		inStream.close();
		buff.close();

		return DataArray;
	}
}

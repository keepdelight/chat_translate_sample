package org.plab.papago.test.httpurlconnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

public class TranslateChatTest {

	private static final String CLIENT_ID = "clientid";
	private static final String CLIENT_SECRET = "secret";
	private static final String API_URL = "https://openapi.naver.com/v1/language/translate";
	
	public static void main(String[] args) {
		try {
			String text = URLEncoder.encode("하늘과 바람과 별과 시", "UTF-8");
			String params = "source=ko&target=en&text=" + text;
			
			URL url = new URL(API_URL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
			con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
			con.setDoOutput(true);
			
			DataOutputStream writer = new DataOutputStream(con.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();
			
			int responseCode = con.getResponseCode();
			BufferedReader bufferedReader;
			if(responseCode == 200) {
				bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				bufferedReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = bufferedReader.readLine()) != null) {
				response.append(inputLine);
			}
			bufferedReader.close();
			
			// print result
			System.out.println("result -----------------");
			System.out.println(response.toString());
			
			// json parsing
			JSONObject jsonObject = new JSONObject(response.toString());
//			JSONArray jsonArray = (JSONArray)jsonObject.get("message");
//			JSONObject resultJsonObject = (JSONObject)jsonArray.get(jsonArray.length());
//			JSONObject resultJsonObject = jsonObject.getJSONObject("message").getJSONObject("result");
			String translatedText = jsonObject.getJSONObject("message").getJSONObject("result").getString("translatedText");
			
			System.out.println("translated text -----------------");
			System.out.println(translatedText);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

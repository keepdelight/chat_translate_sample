package org.plab.papago.test.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TranslateChatTest {

	private static final String CLIENT_ID = "YT3sGk0MoJ7PQmDphfUt";
	private static final String CLIENT_SECRET = "6g5G0Z7pXy";
	private static final String API_URL = "https://openapi.naver.com/v1/language/translate";
	
//	private static final Logger logger = LoggerFactory.getLogger(TranslateChatTest.class);
	
	public static void main(String[] args) {
		
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			String text = "사과";
			
			URI uri = new URI(API_URL);
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setHeader("X-Naver-Client-Id", CLIENT_ID);
			httpPost.setHeader("X-Naver-Client-Secret", CLIENT_SECRET);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			
			// params
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("source", "ko"));
			params.add(new BasicNameValuePair("target", "en"));
			params.add(new BasicNameValuePair("text", text));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			
			// response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				
				@Override
				public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if(status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return (entity != null) ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("unexpected response stauts : " + status);
					}
				}
			};
			
			// response
			String response = httpClient.execute(httpPost, responseHandler);
			System.out.println("url -------------------------");
			System.out.println(httpPost.getParams());
			System.out.println(httpPost.getAllHeaders());
			System.out.println(httpPost.getEntity());
			System.out.println(httpPost.getRequestLine());
			System.out.println(httpPost.getConfig());
			System.out.println(httpPost.getURI());
			System.out.println(httpPost.getEntity());
			System.out.println("text -------------------------");
			System.out.println(text);
			System.out.println("response -------------------------");
			System.out.println(response);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

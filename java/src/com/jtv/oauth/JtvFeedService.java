package com.jtv.oauth;
/**
 * Created by JunctionTV on 07-11-2016.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JtvFeedService implements Runnable{
	private String apiUrl;
	private String token;
	private static JtvHandler handler;
	public JtvFeedService(String url){
		apiUrl = url;
	}
	public JtvFeedService(String url,String token,JtvHandler handler){
		apiUrl = url;
		this.token = token;
		this.handler = handler;
	}
	public void setApiUrl(String url){
		apiUrl = url;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		postIndex(apiUrl);

	}
	private void  postIndex(String urlStr){	

		try {

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();

			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Bearer " + token);

			InputStream content = (InputStream)connection.getInputStream();
			BufferedReader in   = 
					new BufferedReader (new InputStreamReader (content));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);

				if(null!=handler){
					handler.handleMessage(line);
				}
			}

			connection.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
}
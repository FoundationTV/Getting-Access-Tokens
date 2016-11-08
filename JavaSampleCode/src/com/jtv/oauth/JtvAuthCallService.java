package com.jtv.oauth;
/**
 * Created by JunctionTV on 07-11-2016.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.codec.binary.Base64;

public class JtvAuthCallService implements Runnable{

	private String apiUrl;
	private String clientId;
	private String clientSecret;
	private static JtvHandler handler;

	public JtvAuthCallService(String url){
		apiUrl = url;
	}
	public JtvAuthCallService(String clientId,String clientSecret,String url,JtvHandler handler){
		apiUrl = url;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
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
	private void postIndex(String urlStr){	

		try {

			URL url = new URL(urlStr);
			HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

			String data = clientId+":"+clientSecret;
			String encoding = Base64.encodeBase64String(data.getBytes());
			encoding = encoding.replace("\n", "");

			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Authorization", "Basic " + encoding);

			InputStream content = (InputStream)connection.getInputStream();
			BufferedReader in   = 
					new BufferedReader (new InputStreamReader (content));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);

				if(null!=handler){
					handler.handleToken(line);
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
package com.jtv.oauth;
/**
 * Created by JunctionTV on 07-11-2016.
 */
public class JtvAuthUtil {

	private String clientId = "";
	private String clientSecret = "";
	private JtvHandler handler;
	JtvAuthUtil(String clientId,String clientSecret,JtvHandler handler){
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.handler = handler;
	}
	public void getAuth(String apiUrl){
		JtvAuthCallService callWebservice  = new JtvAuthCallService(clientId,clientSecret,apiUrl,handler);
		callWebservice.run();
	}

}

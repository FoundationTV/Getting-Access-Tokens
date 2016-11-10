package com.jtv.oauth;
/**
 * Created by JunctionTV on 07-11-2016.
 */
public interface JtvHandler {
	public boolean handleMessage(String message);
	public boolean handleToken(String accessToken);
}

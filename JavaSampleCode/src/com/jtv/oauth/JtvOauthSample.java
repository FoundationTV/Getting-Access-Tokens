package com.jtv.oauth;
/**
 * Created by JunctionTV on 07-11-2016.
 */
import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONObject;

public class JtvOauthSample {

	private JtvAuthUtil jtvAuthUtil;
	private String clientId = "xxxxx";
	private String clientSecret = "xxxxxxx";
	private String apiOauth = "https://cloud.junctiontv.net/ums/2.0/oauth/";
	private String apiFeed = "http://metax.stage.junctiontv.net/metax/1.1/feed/json/all";

	public static void main(String args[]){
		System.out.println("Inside main method");
		new JtvOauthSample().connectJtvAuth();;
	}

	private void connectJtvAuth(){
		try{
			jtvAuthUtil  = new JtvAuthUtil(clientId,clientSecret,handler);
			jtvAuthUtil.getAuth(apiOauth);
		}
		catch(Throwable t){
			t.printStackTrace();
		}
	}

	private final JtvHandler handler = new JtvHandler() {

		@Override
		public boolean handleMessage(String message) {
			// TODO Auto-generated method stub
			try {
				System.out.println("handler message:: "+message);
				if(message.startsWith("[")){
					JSONArray array = new JSONArray(message);
					System.out.println("array:: "+array);
				}
				else{
					JSONObject jobject = new JSONObject(message);
					System.out.println("jsonObject:: "+jobject);
				}

			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		public boolean handleToken(String accessToken) {
			try{
				JSONObject jobject = new JSONObject(accessToken);
				System.out.println("jsonObject:: "+jobject);

				if (jobject.has("access_token")) {
					String token = jobject.getString("access_token");
					if(null!=token){
						getFeedDetails(token);
					}
				}
			}
			catch(Throwable t){

			}
			return false;
		};
	};

	private void getFeedDetails(String token){

		try {
			JtvFeedService jtvFeedService  = new JtvFeedService(apiFeed,token,null);
			jtvFeedService.run();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

/**
 * 
 */
package com.refunited.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.refunited.utils.PropertiesHandler;

/**
 * @author elena
 * 
 */
public class ApiConnect {

	public static final String URL = "http://api.ru.istykker.dk/";
	public static final String USERNAME = "username";
	private static DefaultHttpClient httpclient;
	public static final String PWD = "password";
	public static final String ENCODING = "UTF-8"; 

	public static void main(String[] srgs) {
		authenticate(URL);
		StringBuffer sbParams = new StringBuffer("");
		sbParams.append("?username=userx5");
		sbParams.append("&password=test5");
		sbParams.append("&name=userx5");
		sbParams.append("&givenName=userx5");
		sbParams.append("&surName=userx5");//324325 324326 324327 324328 324329
		StringBuffer sbParamsFav = new StringBuffer("");
		sbParamsFav.append("?targetProfileId=324326");
		//executeGetRequest(URL + "profile/324329/favorites");
		//executeDeleteRequest(URL + "profile/324328/favorites" + sbParamsFav.toString());
		//executePostRequest(URL + "profile/324328/favorites" + sbParamsFav.toString());
		//executePutRequest(URL + "profile/324325"+sbParams.toString());
		// getHTML(URL + "profile" + sbParams.toString(), "POST");
	    //executeGetRequest(URL + "profile/324327");
		// getHTML(URL + "/partner", "GET");
		//executePostRequest(URL + "profile" + sbParams.toString());
		//executeGetRequest(URL + "search?name=elena%20croitoru");
		//executeGetRequest(URL + "profile/324329/favorites");
		StringBuffer sbParamsMsg = new StringBuffer("");
		sbParamsMsg.append("?messageBody=test2");
		//executePostRequest(URL + "profile/324329/messages/280976"+sbParamsMsg.toString());
		//executeGetRequest(URL + "profile/324329/messages");
		//StringBuffer sbParamsFav = new StringBuffer("");
		//sbParamsFav.append("?targetProfileId=324326");
		//executePostRequest(URL + "profile/324325/favorites" + sbParamsFav.toString());
		executeGetRequest(URL + "profile/login/userx1?password=test1");
		//executeGetRequest(URL + "profile/159376/searchhistory");
		//executeGetRequest(URL + "profile/login/elena_iulia?password=Iuliana1");
		//executeGetRequest(URL + "profile/324329/savedsearches");
		//executeGetRequest(URL + "referral");
	}

	public static void authenticate(String url) {
		httpclient = new DefaultHttpClient();
		PropertiesHandler propertiesHandler = new PropertiesHandler();
		String sUsername = propertiesHandler.getPropertyValue(USERNAME);
		String sPassword = propertiesHandler.getPropertyValue(PWD);
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(null, -1, null),
				new UsernamePasswordCredentials(sUsername, sPassword));
		executeGetRequest(URL);

	}

	public static String executeGetRequest(final String sUrl) {
		HttpGet httpget = new HttpGet(sUrl);
		System.out.println("executing request" + httpget.getRequestLine());
		return executeRequest(httpget);
	}
	
	private static String convertStreamToString(InputStream inputstream) {
	    BufferedReader reader = null;
	    try {
	        reader = new BufferedReader(new InputStreamReader(inputstream, ENCODING));
	    } catch (UnsupportedEncodingException e1) {
	        e1.printStackTrace();
	    }
	    StringBuilder sb = new StringBuilder();
	    String line;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            inputstream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	public static void executePostRequest(final String url) {
		HttpPost httppost = new HttpPost(url);
		executeRequest(httppost);
	}
	public static void executePutRequest(final String url) {
		HttpPut httpput = new HttpPut(url);
		executeRequest(httpput);
	}
	public static void executeDeleteRequest(final String url) {
		HttpDelete httpdelete = new HttpDelete(url);
		executeRequest(httpdelete);
		
	}
	public static String executeRequest(final HttpUriRequest httpuriRequest){
		HttpResponse response = null;
		String result = "";
		try {
			response = httpclient.execute(httpuriRequest);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			try {
				result = convertStreamToString(entity.getContent());
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}

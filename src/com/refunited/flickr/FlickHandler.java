/**
 * 
 */
package com.refunited.flickr;

import java.io.IOException;
import org.xml.sax.SAXException;
import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.uploader.UploadMetaData;
import com.aetrion.flickr.uploader.Uploader;
import com.refunited.utils.Constants;
import com.refunited.utils.PropertiesHandler;

/**
 * @author Elena
 * Flickr handler for usage when the app is not running on a accessible(external) URL.
 */
public class FlickHandler {
	private String m_apiKey;
	private String m_secretKey;
	private String m_token;
	private Flickr m_flickr;
	private Uploader m_uploader;
	private RequestContext m_requestContext;
	private AuthInterface m_authInterface;
	@SuppressWarnings("unused")
	private String m_frob = "";

	/** ------------------- CONSTANTS ---------------------- */
	public static final String FLICKRURLPREFIX = "http://farm";
	public static final String FLICKRURLSUFFIX = ".static.flickr.com/";
	public static final String SLASH = "/";
	public static final String UNDERSCORE = "_";
	public static final String FLICKRIMAGEFORMAT = ".jpg";
	public static final char FORMAT = 'm';

	/**
	 * Method that uses the keys and tokens to authenticate to Flickr.
	 * 
	 * */
	public void authenticate() {
		PropertiesHandler propertiesHandler = new PropertiesHandler();
		m_apiKey = propertiesHandler.getPropertyValue(Constants.Properties.API_KEY_FLICKR);
		m_secretKey = propertiesHandler.getPropertyValue(Constants.Properties.API_SECRET_FLICKR);
		m_token = propertiesHandler.getPropertyValue(Constants.Properties.API_TOKEN_FLICKR);
		m_uploader = new Uploader(m_apiKey, m_secretKey);
		m_flickr = new Flickr(m_apiKey, m_secretKey,
				(new Flickr(m_apiKey)).getTransport());
		m_uploader = m_flickr.getUploader();
		m_authInterface = m_flickr.getAuthInterface();
		m_requestContext = RequestContext.getRequestContext();
		m_requestContext.setSharedSecret(m_secretKey);
		Auth auth = null;
		try {
			m_frob = m_authInterface.getFrob();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (FlickrException e) {
			e.printStackTrace();
		}
		auth = new Auth();
		m_requestContext.setAuth(auth);
		auth.setToken(m_token);
		auth.setPermission(Permission.WRITE);
		m_requestContext.setAuth(auth);
		m_flickr.setAuth(auth);
	}

	/**
	 * Method that uploads a picture to flickr.
	 * 
	 * @param the
	 *            image to upload in bytes
	 * @param strFileName
	 *            the file name to upload the picture into
	 * */
	@SuppressWarnings("deprecation")
	public String upload(byte data[], String strFileName) {
		try {
			authenticate();
			UploadMetaData uploadMetaData = new UploadMetaData();
			uploadMetaData.setTitle(strFileName);
			uploadMetaData.setPublicFlag(true);
			String strResponse = m_uploader.upload(data, uploadMetaData);
			System.out.println(strResponse);
			PhotosInterface photosInterface = new PhotosInterface(m_apiKey,
					m_secretKey, (new Flickr(m_apiKey)).getTransport());
			Photo photo = photosInterface.getPhoto(strResponse);
			return getFlickrImageURL(photo.getFarm(), photo.getServer(),
					strResponse, photo.getSecret(), FORMAT);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * Method that returns the URL of the image based on other details.
	 * 
	 * @param farmID
	 *            the farm id - cloud
	 * @param serverID
	 *            the server id
	 * @param id
	 *            the is of the pic
	 * @param secret
	 *            the secret for a particular pic
	 * @param format
	 *            the format for the pic
	 * */
	final static String getFlickrImageURL(String farmID, String serverID,
			String id, String secret, char format) {
		// Flickr URL format:
		// http://farm{farm-id}.static.flickr.com/{server-id}/{id}_{secret}.jpg
		StringBuffer url = new StringBuffer();
		url.append(FLICKRURLPREFIX);
		url.append(farmID);
		url.append(FLICKRURLSUFFIX);
		url.append(serverID);
		url.append(SLASH);
		url.append(id);
		url.append(UNDERSCORE);
		url.append(secret);
		url.append(UNDERSCORE);
		url.append(format);
		url.append(FLICKRIMAGEFORMAT);
		return url.toString();
	}

}

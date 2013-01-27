package com.refunited.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;
import com.refunited.api.ApiConnect;
import com.refunited.datamodel.User;
import com.refunited.facedetection.FaceDetector;
import com.refunited.flickr.FlickHandler;
import com.refunited.http.EnrichedHttpRequest;
import com.refunited.sms.GatewayHandler;
import com.refunited.sql.SqlConnection;
import com.refunited.utils.Constants;
import com.refunited.utils.Constants.Request;
import static com.refunited.utils.ProcessingUtils.*;

/**
 * @author Elena
 * 
 */
@SuppressWarnings("serial")
public class ProfileServlet extends HttpServlet implements Servlet {
	private Map<String, String[]> m_extraParams = new TreeMap<String, String[]>();
	private User m_user = new User();
	private String m_lusername = "";
	private String m_lpassword = "";
	public static String PROFILE_ID;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// populateData(request, response);
		login(request);
		HttpServletRequest wrappedRequest = new EnrichedHttpRequest(request,
				m_extraParams);
		request.getRequestDispatcher(Constants.VIEW_PAGE).forward(
				wrappedRequest, response);

		return;
	}

	public void login(ServletRequest request) {
		String sUsername = request.getParameter(Request.LOGIN_USERNAME);
		String sPassword = request.getParameter(Request.LOGIN_PASSWORD);
		System.out.println("--------------------Userx-----------" + sUsername);
		System.out.println(sPassword);
		ApiConnect.authenticate(ApiConnect.URL);

		String result = ApiConnect.executeGetRequest(ApiConnect.URL
				+ "profile/login/" + sUsername + "?password=" + sPassword);
		try {
			JSONObject jsonObj = new JSONObject(result);
			PROFILE_ID = jsonObj.getString("profileId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// populateData(request, response);
		login(request);
		HttpServletRequest wrappedRequest = new EnrichedHttpRequest(request,
				m_extraParams);
		request.getRequestDispatcher(Constants.VIEW_PAGE).forward(
				wrappedRequest, response);

		return;
	}

	/*
	 * public void populateData(HttpServletRequest request, HttpServletResponse
	 * response) { SqlConnection sqlConnection = new SqlConnection();
	 * checkForPictures(request); User user = authenticate(request,
	 * sqlConnection, m_lusername, m_lpassword); if (user != null) {
	 * populateUser(request, user); } else { if
	 * (isNotEmpty(m_user.getUsername()) && isNotEmpty(m_user.getPassword())) {
	 * sqlConnection.addUser(m_user.getUsername(), m_user.getPassword(),
	 * m_user.getFname(), m_user.getSname(), m_user.getNickname(),
	 * m_user.getTown(), m_user.getCountry(), m_user.getEmail(),
	 * m_user.getSex(), m_user.getPhone(), m_user.getUrl());
	 * populateUser(request, m_user); } else { request.getSession()
	 * .setAttribute("error", "Not authenticated!"); } searchMatches(request); }
	 * sqlConnection.close();
	 * 
	 * }
	 * 
	 * public void searchMatches(HttpServletRequest request) { SqlConnection
	 * sqlConnection = new SqlConnection(); List<User> lstUsers =
	 * sqlConnection.searchUser(null); List<User> lstResultedUsers = new
	 * ArrayList<User>(); try {
	 * lstResultedUsers.addAll(FaceDetector.searchByPhoto(m_user.getUrl(),
	 * m_user.getUsername(), lstUsers)); } catch (FaceClientException e) {
	 * e.printStackTrace(); } catch (FaceServerException e) {
	 * e.printStackTrace(); } GatewayHandler gatewayHandler = new
	 * GatewayHandler(); for (User user : lstResultedUsers) {
	 * gatewayHandler.sendSms(user.getPhone(), user.getUsername()); } }
	 */
	/**
	 * Method that creates an array from a field.
	 * 
	 * @param field
	 *            the field contained in the array
	 * @return the array
	 * */
	public String[] createArrayFromField(String field) {
		return new String[] { processField(field) };
	}

	/**
	 * Method that populates a User object from request parameters.
	 * 
	 * @param request
	 *            the http request
	 * @param user
	 *            the user object
	 */
	public void populateUser(HttpServletRequest request, User user) {
		m_extraParams.put(Request.TYPED_USERNAME,
				createArrayFromField(user.getUsername()));
		m_extraParams.put(Request.TYPED_PASSWORD,
				createArrayFromField(user.getPassword()));
		m_extraParams.put(Request.TYPED_FIRST_NAME,
				createArrayFromField(user.getFname()));
		m_extraParams.put(Request.TYPED_FAMILY_NAME,
				createArrayFromField(user.getSname()));
		m_extraParams.put(Request.TYPED_NICKNAME,
				createArrayFromField(user.getNickname()));
		m_extraParams.put(Request.TYPED_TOWN,
				createArrayFromField(user.getTown()));
		m_extraParams.put(Request.TYPED_COUNTRY,
				createArrayFromField(user.getCountry()));
		m_extraParams.put(Request.TYPED_EMAIL,
				createArrayFromField(user.getEmail()));
		m_extraParams.put(Request.TYPED_PHONE,
				createArrayFromField(user.getPhone()));
		m_extraParams.put(Request.TYPED_SEX,
				createArrayFromField(user.getSex()));
		m_extraParams.put(Request.URL, createArrayFromField(user.getUrl()));
	}

	// /**
	// * This method returns a user object if the authentication was successful.
	// *
	// * @param request
	// * The HttpServletRequest
	// * @param sqlConnection
	// * the Sql connection
	// * @return true if the Authorisation was successful
	// **/
	//
	// private User authenticate(HttpServletRequest request,
	// SqlConnection sqlConnection) {
	// User user = null;
	// HashMap<String, String> map = new HashMap<String, String>();
	// map.put(Constants.Request.USERNAME, m_lusername);
	// map.put(Constants.Request.PASSWORD, m_lpassword);
	// if (isNotEmpty(m_lusername) && isNotEmpty(m_lpassword)) {
	// ArrayList<User> arrUsers = (ArrayList<User>) sqlConnection
	// .searchUser(map);
	// if (arrUsers != null && arrUsers.size() > 0) {
	// user = arrUsers.get(0);
	// }
	// }
	// return user;
	// }

	/**
	 * Method that checks to see if there are images in the current http
	 * request.
	 * 
	 * @param request
	 *            the http request
	 * 
	 * */
	/*
	 * public void checkForPictures(HttpServletRequest request) { boolean
	 * isMultipartContent = ServletFileUpload .isMultipartContent(request); if
	 * (!isMultipartContent) { return; } FileItemFactory factory = new
	 * DiskFileItemFactory(); ServletFileUpload upload = new
	 * ServletFileUpload(factory); try {
	 * 
	 * @SuppressWarnings("unchecked") List<FileItem> fields =
	 * upload.parseRequest(request); Iterator<FileItem> it = fields.iterator();
	 * if (!it.hasNext()) { return; } while (it.hasNext()) { FileItem fileItem =
	 * it.next(); boolean isFormField = fileItem.isFormField(); if (isFormField)
	 * { setUser(fileItem.getFieldName(), fileItem.getString(), m_lusername,
	 * m_lpassword, m_user); System.out.println(fileItem.getString()); } else {
	 * String strFileName = m_user.getUsername() + Constants.FILE_EXTENSION; if
	 * (fileItem.getSize() != 0) { byte[] bytesToUpload =
	 * IOUtils.toByteArray(fileItem .getInputStream()); FlickHandler
	 * flickHandler = new FlickHandler(); String strImageUrl =
	 * flickHandler.upload(bytesToUpload, strFileName);
	 * m_user.setUrl(strImageUrl); } } } } catch (FileUploadException e) {
	 * e.printStackTrace(); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */
}

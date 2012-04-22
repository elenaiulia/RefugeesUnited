package com.refunited.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;
import com.refunited.datamodel.User;
import com.refunited.facedetection.FaceDetector;
import com.refunited.sql.SqlConnection;
import com.refunited.utils.Constants;
import com.refunited.utils.Constants.Request;
import static com.refunited.utils.ProcessingUtils.*;

/**
 * @author Elena
 * 
 */
@SuppressWarnings("serial")
public class SearchServlet extends HttpServlet implements Servlet {

	/** Result list after a search with criteria. */
	public List<User> m_lstUsers = new ArrayList<User>();
	/** List of all the users - used for the search by picture functionality. */
	public static List<User> m_lstAllUsers = new ArrayList<User>();

	/**
	 * Getter.
	 * 
	 * @return the list of users.
	 * */
	public List<User> getUsers() {
		return m_lstUsers;
	}

	/**
	 * Setter.
	 * 
	 * @param the
	 *            list of users.
	 * */
	public void setUsers(ArrayList<User> users) {
		m_lstUsers = users;
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String strUsername = request.getParameter(Constants.Request.TYPED_USERNAME);
		String strUrl = request.getParameter(Constants.Request.URL);
		SqlConnection sqlConnection = new SqlConnection();
		m_lstUsers = searchUsersByTextCriteria(request, sqlConnection,
				strUsername);
		addUsersWithPhotoMatches(strUrl, strUsername);
		request.getSession().setAttribute(Constants.Request.USERS, getUsers());
		getServletContext().getRequestDispatcher(Constants.RESULTS_PAGE)
				.forward(request, response);
		sqlConnection.close();
		return;
	}

	/**
	 * Method that adds to the list of users the users that matched by photo
	 * criteria.
	 * 
	 * @param strUrl
	 *            the url of the current user that performs this search
	 * @param strUsername
	 *            the username of the current user that performs this search -
	 *            used as part of the namespace for network training
	 * */
	public void addUsersWithPhotoMatches(final String strUrl,
			final String strUsername) {
		try {
			m_lstUsers.addAll(FaceDetector.searchByPhoto(strUrl, strUsername,
					m_lstAllUsers));
		} catch (FaceClientException e) {
			e.printStackTrace();
		} catch (FaceServerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that performs a search by text criteria.
	 * 
	 * @param request
	 *            the http request
	 * @param sqlConnection
	 * @param username
	 *            the username of the current user that performs the search
	 * */
	public static List<User> searchUsersByTextCriteria(
			HttpServletRequest request, SqlConnection sqlConnection,
			String username) {
		HashMap<String, String> searchCriteriaMap = populateSearchCriteriaMap(request);
		List<User> lstUsers = new ArrayList<User>();
		if (sqlConnection != null) {
			lstUsers = sqlConnection.searchUser(searchCriteriaMap, username);
			m_lstAllUsers = sqlConnection.searchUser(null);
			sqlConnection.close();
		}
		return lstUsers;
	}

	/**
	 * Method that takes the request paramteters and poppulates a criteria hash
	 * map that will be used to generate an SQL where clause.
	 * 
	 * @param the
	 *            http request
	 * */
	public static HashMap<String, String> populateSearchCriteriaMap(
			final HttpServletRequest request) {
		String strUsername = getRequestParamValue(request,
				Request.SEARCH_USERNAME);
		String strPassword = getRequestParamValue(request,
				Request.SEARCH_PASSWORD);
		String strFName = getRequestParamValue(request,
				Request.SEARCH_FIRST_NAME);
		String strSName = getRequestParamValue(request,
				Request.SEARCH_FAMILY_NAME);
		String strNickname = getRequestParamValue(request,
				Request.SEARCH_NICKNAME);
		String strTown = getRequestParamValue(request, Request.SEARCH_TOWN);
		String strCountry = getRequestParamValue(request,
				Request.SEARCH_COUNTRY);
		String strEmail = getRequestParamValue(request, Request.SEARCH_EMAIL);
		String strPhone = getRequestParamValue(request, Request.SEARCH_PHONE);
		String strSex = getRequestParamValue(request, Request.SEARCH_SEX);
		String strUrl = getRequestParamValue(request, "url");
		HashMap<String, String> map = new HashMap<String, String>();
		if (isNotEmpty(strUsername))
			map.put(Constants.Map.USERNAME, strUsername);
		if (isNotEmpty(strPassword))
			map.put(Constants.Map.PASSWORD, strPassword);
		if (isNotEmpty(strFName))
			map.put(Constants.Map.FIRST_NAME, strFName);
		if (isNotEmpty(strSName))
			map.put(Constants.Map.FAMILY_NAME, strSName);
		if (isNotEmpty(strNickname))
			map.put(Constants.Map.NICKNAME, strNickname);
		if (isNotEmpty(strTown))
			map.put(Constants.Map.TOWN, strTown);
		if (isNotEmpty(strCountry))
			map.put(Constants.Map.COUNTRY, strCountry);
		if (isNotEmpty(strEmail))
			map.put(Constants.Map.EMAIL, strEmail);
		if (isNotEmpty(strPhone))
			map.put(Constants.Map.PHONE, strPhone);
		if (isNotEmpty(strSex))
			map.put(Constants.Map.PHONE, strSex);
		return map;
	}

}

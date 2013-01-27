package com.refunited.main;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;
import com.refunited.api.ApiConnect;
import com.refunited.datamodel.User;
import com.refunited.facedetection.FaceDetector;
import com.refunited.sql.SqlConnection;
import com.refunited.utils.Constants;
import com.refunited.utils.ValidationHandler;
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
	public static List<User> lstFavUsers = new ArrayList<User>();
	public static List<User> lstMsgUsers = new ArrayList<User>();
	public static String currentUserProfileId = "324329";

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
		currentUserProfileId = ProfileServlet.PROFILE_ID;
		lstFavUsers.clear();
		lstMsgUsers.clear();
		m_lstUsers.clear();
		String strUsername = request
				.getParameter(Constants.Request.TYPED_USERNAME);
		String strUrl = request.getParameter(Constants.Request.URL);
		Enumeration<String> names = request.getParameterNames();
		String message = null;
		while (names.hasMoreElements()) {
			String currentName = names.nextElement();
			if (currentName.startsWith("message")) {
				message = request.getParameter(currentName);
				message = message.replaceAll("\t", "");
				String profile = currentName.replace("message", "username");
				String toProfile = request.getParameter(profile);
				if (message != null && !isValid(message)) {
					request.getSession()
							.setAttribute(
									"error",
									"Your message contained sensitive information (phone number or email address) therefore it was not sent.");
					request.getSession().setAttribute(currentName, message);
					break;
				} else {
					if (message.trim().length() != 0) {
						message = URLEncoder.encode(message, "UTF8");
						request.getSession().setAttribute("error", "");
						StringBuffer sbParamsMsg = new StringBuffer("");
						sbParamsMsg.append("?messageBody=" + message);
						ApiConnect.executePostRequest(ApiConnect.URL
								+ "profile/" + currentUserProfileId
								+ "/messages/" + toProfile
								+ sbParamsMsg.toString());
						ApiConnect.executePostRequest(ApiConnect.URL
								+ "profile/" + currentUserProfileId
								+ "/favorites/" + toProfile
								+ sbParamsMsg.toString());
					}
				}
			}
		}
		// SqlConnection sqlConnection = new SqlConnection();
		m_lstUsers = searchUsersByTextCriteria(request, strUsername);
		// addUsersWithPhotoMatches(strUrl, strUsername);
		request.getSession().setAttribute(Constants.Request.USERS, getUsers());

		getServletContext().getRequestDispatcher(Constants.RESULTS_PAGE)
				.forward(request, response);
		// sqlConnection.close();
		return;
	}

	public boolean isValid(String message) {
		String[] words = message.split(" ");
		for (int index = 0; index < words.length; index++) {
			String word = words[index];
			word = word.trim();
			if ((word.matches("[0-9]+") && word.length() > 5)
					|| (word.contains("@") && ValidationHandler
							.checkEmail(word))) {
				return false;
			}
		}
		return true;
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
			HttpServletRequest request, String username) {
		HashMap<String, String> searchCriteriaMap = populateSearchCriteriaMap(request);
		List<User> lstUsers = new ArrayList<User>();
		try {
			searchFavorites(currentUserProfileId, searchCriteriaMap);
			lstUsers.addAll(lstFavUsers);
			searchMessages(currentUserProfileId, searchCriteriaMap);
			lstUsers.addAll(lstMsgUsers);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String result = ApiConnect.executeGetRequest(ApiConnect.URL
				+ "search?name="
				+ searchCriteriaMap.get(Constants.Map.FIRST_NAME) + "%20"
				+ searchCriteriaMap.get(Constants.Map.FAMILY_NAME));
		StringBuffer sbParams = new StringBuffer();
		sbParams.append("?givenName="
				+ searchCriteriaMap.get(Constants.Map.FIRST_NAME));
		sbParams.append("&surName="
				+ searchCriteriaMap.get(Constants.Map.FAMILY_NAME));
		ApiConnect.executePostRequest(ApiConnect.URL
				+ "profile/324325/savedsearches" + sbParams.toString());
		System.out.print(result);
		try {
			JSONObject jsonObj = new JSONObject(result);
			JSONArray jsonArray = jsonObj.getJSONArray("results");
			for (int index = 0; index < jsonArray.length(); index++) {
				JSONObject searchResultItm = jsonArray.getJSONObject(index);
				User user = new User();
				String profileid = searchResultItm.getString("profileid");
				user.setUsername(profileid);
				user.setFname(eliminateNull(searchResultItm
						.getString("givenName")));
				user.setSname(eliminateNull(searchResultItm
						.getString("surName")));
				user.setAge(eliminateNull(searchResultItm.getString("age")));
				m_lstAllUsers.add(user);
				lstUsers.add(user);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lstUsers;
	}

	public static String eliminateNull(String value) {
		if (value == null || value.equalsIgnoreCase("null")) {
			value = "";
		}
		return value;
	}

	public static List<User> searchFavorites(String profileId,
			HashMap<String, String> searchCriteriaMap) throws JSONException {
		String result = ApiConnect.executeGetRequest(ApiConnect.URL
				+ "profile/" + profileId + "/favorites");
		JSONObject jsonObj = new JSONObject(result);
		JSONArray jsonArray = jsonObj.getJSONArray("favorites");
		for (int index = 0; index < jsonArray.length(); index++) {
			JSONObject searchResultItm = jsonArray.getJSONObject(index);
			User user = new User();
			String givenName = searchResultItm.getString("givenName");
			String surName = searchResultItm.getString("surName");
			String profileid = searchResultItm.getString("id");
			String criteriaGivenName = searchCriteriaMap
					.get(Constants.Map.FIRST_NAME);
			String criteriaSurName = searchCriteriaMap
					.get(Constants.Map.FAMILY_NAME);
			if (criteriaGivenName == null
					|| criteriaSurName == null
					|| (givenName != null && criteriaGivenName != null && givenName
							.contains(criteriaGivenName))
					|| (surName != null && criteriaSurName != null && surName
							.contains(criteriaSurName))) {
				user.setUsername(profileid);
				System.out.print("profile id of the favorite " + profileid);
				user.setFname(eliminateNull(searchResultItm
						.getString("givenName")));
				user.setSname(eliminateNull(searchResultItm
						.getString("surName")));
				user.setAge("");
				m_lstAllUsers.add(user);
				lstFavUsers.add(user);
			}
			searchFavorites(profileid, searchCriteriaMap);
		}
		return lstFavUsers;
	}

	public static List<User> searchMessages(String profileId,
			HashMap<String, String> searchCriteriaMap) throws JSONException {

		String result = ApiConnect.executeGetRequest(ApiConnect.URL
				+ "profile/" + profileId + "/messages");
		JSONObject jsonObj = new JSONObject(result);
		JSONArray jsonArray = jsonObj.getJSONArray("threads");
		for (int index = 0; index < jsonArray.length(); index++) {
			JSONObject searchResultItm = jsonArray.getJSONObject(index);
			User user = new User();
			String givenName = searchResultItm.getString("givenName");
			String surName = searchResultItm.getString("surName");
			String profileid = searchResultItm.getString("targetProfileId");
			String criteriaGivenName = searchCriteriaMap
					.get(Constants.Map.FIRST_NAME);
			String criteriaSurName = searchCriteriaMap
					.get(Constants.Map.FAMILY_NAME);
			if (criteriaGivenName == null
					|| criteriaSurName == null
					|| (givenName != null && criteriaGivenName != null && givenName
							.contains(criteriaGivenName))
					|| (surName != null && criteriaSurName != null && surName
							.contains(criteriaSurName))) {
				user.setUsername(profileid);
				String targetProfileName = searchResultItm
						.getString("targetProfileName");
				if (targetProfileName != null
						&& targetProfileName.length() != 0) {
					String[] names = targetProfileName.split(" ");
					System.out.print("profile id of the favorite " + profileid);
					if (names.length > 0)
						user.setFname(names[0]);
					if (names.length > 1)
						user.setSname(names[1]);
					user.setAge("");
				}
				m_lstAllUsers.add(user);
				lstMsgUsers.add(user);
			}
			searchMessages(profileid, searchCriteriaMap);
		}
		return lstMsgUsers;
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

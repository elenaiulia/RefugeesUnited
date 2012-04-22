package com.refunited.main;


import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.refunited.datamodel.User;
import com.refunited.sql.SqlConnection;
import com.refunited.utils.Constants;
import static com.refunited.utils.ProcessingUtils.*;

/**
 * @author Elena
 * 
 */
@SuppressWarnings("serial")
public class UserServlet extends HttpServlet implements Servlet {

	/**
	 * Method that sets members of the user object depending on the name
	 * parameter.
	 * 
	 * @param strName
	 *            the name of the parameter to be checked
	 * @param strValue
	 *            the value of the parameter to be set
	 * */
	public void setUser(String strName, String strValue, String strLusername,
			String strLpassword, User user) {
		if (Constants.Request.LOGIN_USERNAME.equalsIgnoreCase(strName)) {
			strLusername = strValue;
		}
		if (Constants.Request.LOGIN_PASSWORD.equalsIgnoreCase(strName)) {
			strLpassword = strValue;
		}
		if (Constants.Request.USERNAME.equalsIgnoreCase(strName)) {
			user.setUsername(strValue);
		}
		if (Constants.Request.PASSWORD.equalsIgnoreCase(strName)) {
			user.setPassword(strValue);
		}
		if (Constants.Request.FAMILY_NAME.equalsIgnoreCase(strName)) {
			user.setSname(strValue);
		}
		if (Constants.Request.FIRST_NAME.equalsIgnoreCase(strName)) {
			user.setFname(strValue);
		}
		if (Constants.Request.TOWN.equalsIgnoreCase(strName)) {
			user.setTown(strValue);
		}
		if (Constants.Request.COUNTRY.equalsIgnoreCase(strName)) {
			user.setCountry(strValue);
		}
		if (Constants.Request.SEX.equalsIgnoreCase(strName)) {
			user.setSex(strValue);
		}
		if (Constants.Request.EMAIL.equalsIgnoreCase(strName)) {
			user.setEmail(strValue);
		}
		if (Constants.Request.NICKNAME.equalsIgnoreCase(strName)) {
			user.setNickname(strValue);
		}
		if (Constants.Request.PHONE.equalsIgnoreCase(strName)) {
			user.setPhone(strValue);
		}
	}

	/**
	 * This method returns a user object if the authentication was successful.
	 * 
	 * @param request
	 *            The HttpServletRequest
	 * @param sqlConnection
	 *            the Sql connection
	 * @return true if the Authorisation was successful
	 **/

	protected User authenticate(HttpServletRequest request,
			SqlConnection sqlConnection, String strLusername, String strLpassword) {
		User user = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.Request.USERNAME, strLusername);
		map.put(Constants.Request.PASSWORD, strLpassword);
		if (isNotEmpty(strLusername) && isNotEmpty(strLpassword)) {
			ArrayList<User> arrUsers = (ArrayList<User>) sqlConnection
					.searchUser(map);
			if (arrUsers != null && arrUsers.size() > 0) {
				user = arrUsers.get(0);
			}
		}
		return user;
	}

	
}

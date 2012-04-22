package com.refunited.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.refunited.datamodel.User;
import com.refunited.utils.Constants;
import com.refunited.utils.PropertiesHandler;

public class SqlConnection {
	private Connection m_connection = null;

	public SqlConnection() {
		getConnection();
	}

	/**
	 * Method that gets an SQL connection if one doesn't exist.
	 * 
	 * @return connection the sql connection
	 * */
	private Connection getConnection() {
		if (m_connection == null) {
			try {
				PropertiesHandler propertiesHandler = new PropertiesHandler();
				String userName = propertiesHandler
						.getPropertyValue(Constants.Properties.USER_SQL);
				String password = propertiesHandler
						.getPropertyValue(Constants.Properties.PWD_SQL);
				Class.forName(Constants.SQL.DRIVER).newInstance();
				m_connection = DriverManager.getConnection(
						Constants.SQL.JDBC_URL, userName, password);
				System.out.println("Database connection established");
			} catch (Exception e) {
				System.err.println("Cannot connect to database server");
				e.printStackTrace();
			}
			return m_connection;
		} else {
			return m_connection;
		}
	}

	/**
	 * Method that closes the current connection.
	 * 
	 * */
	public void close() {
		try {
			m_connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addUser(String strUsername, String strPassword,
			String strFName, String strSName, String strNickname,
			String strTown, String strCountry, String strEmail, String strSex,
			String phone, String strNoPhotos) {
		try {
			PreparedStatement pstmt = m_connection
					.prepareStatement(Constants.SQL.INSERT_STMT);
			pstmt.setString(1, strUsername);
			pstmt.setString(2, strPassword);
			pstmt.setString(3, strFName);
			pstmt.setString(4, strSName);
			pstmt.setString(5, strNickname);
			pstmt.setString(6, strTown);
			pstmt.setString(7, strCountry);
			pstmt.setString(8, strEmail);
			pstmt.setString(9, strSex);
			pstmt.setString(10, phone);
			pstmt.setString(11, strNoPhotos);
			pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Method that gets the name of the column from its index.
	 * 
	 * @return the name of the column
	 * */
	public String getName(int index) {
		switch (index) {
		case 0:
			return Constants.SQL.COL_USERNAME;
		case 1:
			return Constants.SQL.COL_PASSWORD;
		case 2:
			return Constants.SQL.COL_FIRST_NAME;
		case 3:
			return Constants.SQL.COL_FAMILY_NAME;
		case 4:
			return Constants.SQL.COL_NICKNAME;
		case 5:
			return Constants.SQL.COL_TOWN;
		case 6:
			return Constants.SQL.COL_COUNTRY;
		case 7:
			return Constants.SQL.COL_EMAIL;
		case 8:
			return "sex";
		default:
			return "";
		}

	}

	public List<User> searchUser(HashMap<String, String> arrParameters,
			String... username) {
		try {
			StringBuffer sbStatement = new StringBuffer(
					Constants.SQL.SELECT_ALL);
			String statement = "";
			if (arrParameters != null) {
				sbStatement.append(Constants.SQL.WHERE);
				for (String key : arrParameters.keySet()) {
					if (arrParameters.get(key) != null
							&& arrParameters.get(key).length() != 0) {
						sbStatement.append(key);
						sbStatement.append("=");
						sbStatement.append("'");
						sbStatement.append(arrParameters.get(key));
						sbStatement.append("'");
						sbStatement.append(Constants.SQL.AND);
					}
				}
				if (username.length != 0) {
					sbStatement.append("username != '" + username[0] + "'");
					statement = sbStatement.toString();
				} else {
					statement = sbStatement.toString();
					statement = statement.substring(0,
							statement.lastIndexOf(Constants.SQL.AND));
				}
			} else {
				statement = sbStatement.toString();
			}
			Statement pstmt = m_connection.createStatement();
			ResultSet rs = pstmt.executeQuery(statement.toString());
			List<User> lstUsers = new ArrayList<User>();
			User user = null;
			while (rs.next()) {
				user = getUserFromRs(rs);
				lstUsers.add(user);
			}
			rs.close();
			pstmt.close();
			return lstUsers;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Creates a user object from the result set.
	 * 
	 * @param rs
	 *            the result set
	 * @return the user object
	 * */
	public User getUserFromRs(final ResultSet rs) {
		User user = new User();
		try {
			user.setId(String.valueOf(rs.getInt(Constants.SQL.COL_ID)));
			user.setUsername(rs.getString(Constants.SQL.COL_USERNAME));
			user.setEmail(rs.getString(Constants.SQL.COL_EMAIL));
			user.setFname(rs.getString(Constants.SQL.COL_FIRST_NAME));
			user.setSname(rs.getString(Constants.SQL.COL_FAMILY_NAME));
			user.setSex(rs.getString(Constants.SQL.COL_SEX));
			user.setNoPhotos(rs.getString(Constants.SQL.COL_URL));
			user.setNickname(rs.getString(Constants.SQL.COL_NICKNAME));
			user.setTown(rs.getString(Constants.SQL.COL_TOWN));
			user.setCountry(rs.getString(Constants.SQL.COL_COUNTRY));
			user.setPhone(rs.getString(Constants.SQL.COL_PHONE));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;

	}

	/**
	 * Method that gets all the picture URLs from the DB.
	 * 
	 * @return the list of users
	 * */
	public List<User> searchUrls() {
		try {
			StringBuffer sbStatement = new StringBuffer(
					Constants.SQL.SELECT_ALL);
			Statement pstmt = m_connection.createStatement();
			ResultSet rs = pstmt.executeQuery(sbStatement.toString());
			List<User> lstUsers = new ArrayList<User>();
			User user = null;
			while (rs.next()) {
				user = getUserFromRs(rs);
				lstUsers.add(user);
			}
			rs.close();
			pstmt.close();
			return lstUsers;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
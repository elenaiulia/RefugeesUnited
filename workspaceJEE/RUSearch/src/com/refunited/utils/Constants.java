/**
 * 
 */
package com.refunited.utils;

/**
 * @author Elena
 * 
 */
public class Constants {
	public static final String NULL = "null";
	public static final String EMPTY = "";
	public static final String RESULTS_PAGE = "/Result.jsp";
	public static final String VIEW_PAGE = "/ViewUser.jsp";
	public static final String FILE_EXTENSION = ".png";
	public static final String PIPE = "|";
	public static final String STANDARD_SMS_TEXT = "Found+a+match:+username+";
	public static final String SENDER = "0123456789";

	public static class Map {
		public static final String USERNAME = "username";
		public static final String PASSWORD = "password";
		public static final String FIRST_NAME = "first_name";
		public static final String FAMILY_NAME = "family_name";
		public static final String NICKNAME = "nickname";
		public static final String TOWN = "town";
		public static final String COUNTRY = "country";
		public static final String PHONE = "phone";
		public static final String EMAIL = "email";
		public static final String SEX = "sex";
	}
	
	public static class Properties{
		public static final String API_KEY_FACE = "faceapikey";
		public static final String API_SECRET_FACE = "faceapisec";
		public static final String API_KEY_FLICKR = "flickrapikey";
		public static final String API_SECRET_FLICKR = "flickrapisec";
		public static final String API_TOKEN_FLICKR = "flickrtoken";
		public static final String API_KEY_CLICKATELL = "clickatellapikey";
		public static final String USER_CLICKATELL = "clickatelluser";
		public static final String PWD_CLICKATELL = "clickatellpassword";
		public static final String USER_SQL = "sqluser";
		public static final String PWD_SQL = "sqlpassword";
	}

	public static class Request {
		public static final String USERS = "users";
		public static final String SEARCH_USERNAME = "susername";
		public static final String SEARCH_PASSWORD = "spassword";
		public static final String SEARCH_FIRST_NAME = "sfname";
		public static final String SEARCH_FAMILY_NAME = "ssname";
		public static final String SEARCH_NICKNAME = "snickname";
		public static final String SEARCH_TOWN = "stown";
		public static final String SEARCH_COUNTRY = "scountry";
		public static final String SEARCH_PHONE = "sphone";
		public static final String SEARCH_EMAIL = "semail";
		public static final String SEARCH_SEX = "ssex";
		public static final String TYPED_USERNAME = "fusername";
		public static final String TYPED_PASSWORD = "fpassword";
		public static final String TYPED_FIRST_NAME = "ffname";
		public static final String TYPED_FAMILY_NAME = "fsname";
		public static final String TYPED_NICKNAME = "fnickname";
		public static final String TYPED_TOWN = "ftown";
		public static final String TYPED_COUNTRY = "fcountry";
		public static final String TYPED_PHONE = "fphone";
		public static final String TYPED_EMAIL = "femail";
		public static final String TYPED_SEX = "fsex";
		public static final String USERNAME = "username";
		public static final String PASSWORD = "password";
		public static final String FIRST_NAME = "fname";
		public static final String FAMILY_NAME = "sname";
		public static final String NICKNAME = "nickname";
		public static final String TOWN = "town";
		public static final String COUNTRY = "country";
		public static final String PHONE = "phone";
		public static final String EMAIL = "email";
		public static final String SEX = "sex";
		public static final String URL = "url";
		public static final String LOGIN_USERNAME = "lusername";
		public static final String LOGIN_PASSWORD = "lpassword";
	}
	
	public class SQL{
		public static final String JDBC_URL = "jdbc:mysql://localhost/refunited";
		public static final String DRIVER = "com.mysql.jdbc.Driver"; 
		public static final String COL_USERNAME = "username";
		public static final String COL_PASSWORD = "password";
		public static final String COL_NICKNAME = "nickname";
		public static final String COL_FIRST_NAME = "first_name";
		public static final String COL_FAMILY_NAME = "family_name";
		public static final String COL_TOWN = "town";
		public static final String COL_PHONE = "phone";
		public static final String COL_COUNTRY = "country";
		public static final String COL_EMAIL= "email";
		public static final String COL_URL = "url";
		public static final String COL_SEX = "sex";
		public static final String COL_ID = "id";
		public static final String SELECT_ALL = "SELECT * FROM refugees";
		public static final String WHERE = " where ";
		public static final String AND = " and ";
		public static final String INSERT_STMT = "INSERT INTO refugees "
				+ "(username, password, first_name, family_name, nickname, town, country, "
				+ "email, sex, phone, url) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}
}

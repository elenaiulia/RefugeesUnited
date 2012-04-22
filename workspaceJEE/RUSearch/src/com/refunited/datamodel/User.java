package com.refunited.datamodel;
/**
 * @author Elena
 * This class should be generated: ORM
 */
public class User {
	private String username;
	private String password;
	private String fname;
	private String sname;
	private String nickname;
	private String sex;
	private String email;
	private String url;
	private String phone;
	private String id;
	private String town;
	private String country;
	
	public User()
	{
		/** Default*/
	}
	/** Constructor.*/
	public User(String username, String password, String fname, String sname, String nickname, 
			String town, String country,
			 String email, String sex, String phone, String url){
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.sname = sname;
		this.nickname = nickname;
		this.sex = sex;
		this.email = email;
		this.url = url;
		this.town = town;
		this.country = country;
		this.phone = phone;
	}
	/**
	 * @return the town
	 */
	public String getTown() {
		return town;
	}

	/**
	 * @param town the town to set
	 */
	public void setTown(String town) {
		this.town = town;
	}
	/**
	 * @return the phone no
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone no to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the phone no
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param phone the phone no to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname
	 *            the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the sname
	 */
	public String getSname() {
		return sname;
	}

	/**
	 * @param sname
	 *            the sname to set
	 */
	public void setSname(String sname) {
		this.sname = sname;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the noPhotos
	 */
	public String getNoPhotos() {
		return url;
	}

	/**
	 * @param noPhotos
	 *            the noPhotos to set
	 */
	public void setNoPhotos(String noPhotos) {
		this.url = noPhotos;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}

package com.refunited.security;
import java.security.MessageDigest;


public class EncryptionManager {
	private static final String ALGORITHM = "SHA-256";
	private static final char ZERO = '0';
	/**
	 * Method that generates a hash based on a String value
	 * @param password the string to be hashed.
	 * */
	public static String generateHash(String password){
	    MessageDigest digest = null;
	    String hash = "";
	    try {
	        digest = MessageDigest.getInstance(ALGORITHM);
	        digest.update(password.getBytes());
	        hash = bytesToHexString(digest.digest());
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return hash;
	}
	/**
	 * Method that converts from bytes to a String representation of the same bytes in hexa.
	 * @param bytes the bytes for conversion
	 * */
    private static String bytesToHexString(byte[] bytes) {
        StringBuffer sbHex = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sbHex.append(ZERO);
            }
            sbHex.append(hex);
        }
        return sbHex.toString();
    }
}
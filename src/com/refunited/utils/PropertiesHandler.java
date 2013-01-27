/**
 * 
 */
package com.refunited.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Elena
 * 
 */
public class PropertiesHandler {
	public static final String PROPERTIES_FILE = "C:\\Development\\refunited\\refunited.properties";
	private Properties m_properties = new Properties();

	public PropertiesHandler() {
		loadProperties();
	}

	/**
	 * Method that loads properties form a file on the disk.
	 * 
	 * @return the Properties object
	 * */
	public void loadProperties() {
		try {
			m_properties.load(new FileInputStream(PROPERTIES_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that gets the value of a property given by strName
	 * 
	 * @param strName
	 *            the name of the property
	 * */
	public String getPropertyValue(final String strName) {
		return (String) m_properties.get(strName);
	}
}

/**
 * 
 */
package com.refunited.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Elena
 * 
 */
public class ProcessingUtils {
	/**
	 * Method that processes the string for display in the jsp pages.
	 * 
	 * @param strField
	 *            the field value to process
	 * @return the processed value
	 * */
	public static String processField(final String strField) {
		if (strField == null || Constants.NULL.equalsIgnoreCase(strField)) {
			return Constants.EMPTY;
		} else
			return strField;
	}

	/**
	 * Method that checks for empty values.
	 * 
	 * @param value
	 *            the value to check
	 * @return true if not empty
	 * */
	public static boolean isNotEmpty(final String value) {
		return (value != null && value.length() != 0 && !Constants.NULL
				.equalsIgnoreCase(value));
	}

	/**
	 * Method that gets the value of the parameter named by name
	 * 
	 * @param request
	 *            the http request
	 * @param name
	 *            the name of the parameter
	 * @return the value
	 * */
	public static String getRequestParamValue(final HttpServletRequest request,
			final String name) {
		return String.valueOf(request.getParameter(name));
	}

	/**
	 * Method that creates a String array from a field.
	 * 
	 * @param the
	 *            field value
	 * @return the array reference
	 * */
	public String[] createArrayFromField(String field) {
		return new String[] { processField(field) };
	}

}

/**
 * 
 */
package com.refunited.http;

/**
 * @author Elena
 *
 */
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EnrichedHttpRequest extends HttpServletRequestWrapper {
	
	private final Map<String, String[]> m_additionalParameters;
	private Map<String, String[]> m_parameters = null;

	/**
	 * Create a request wrapper object that will add additional parameters into
	 * the HTTP request.
	 * 
	 * @param request the http request
	 * @param additionalParams the additional paramteters to be added to the collection
	 */
	public EnrichedHttpRequest(final HttpServletRequest request,
			final Map<String, String[]> additionalParams) {
		super(request);
		m_additionalParameters = new TreeMap<String, String[]>();
		m_additionalParameters.putAll(additionalParams);
	}

	@Override
	public String getParameter(final String name) {
		String[] strings = getParameterMap().get(name);
		if (strings != null) {
			return strings[0];
		}
		return null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		if (m_parameters == null) {
			m_parameters = new TreeMap<String, String[]>();
			m_parameters.putAll(super.getParameterMap());
			m_parameters.putAll(m_additionalParameters);
		}
		return Collections.unmodifiableMap(m_parameters);
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(getParameterMap().keySet());
	}

	@Override
	public String[] getParameterValues(final String name) {
		return getParameterMap().get(name);
	}
}
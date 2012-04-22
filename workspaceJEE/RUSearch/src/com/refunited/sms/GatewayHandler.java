/**
 * 
 */
package com.refunited.sms;

import java.io.IOException;
import org.marre.SmsSender;
import org.marre.sms.SmsException;
import com.refunited.utils.Constants;
import com.refunited.utils.PropertiesHandler;

/**
 * @author Elena
 * 
 */
public class GatewayHandler {
	/** Sms sender object to be used for sending texts. */
	private SmsSender m_smsSender;

	public GatewayHandler() {
		m_smsSender = authenticate();
	}

	/**
	 * Method that authenticates in Clickatell and creates an sms sender object.
	 * 
	 * @return the smsSender object
	 * */
	public SmsSender authenticate() {
		PropertiesHandler propertiesHandler = new PropertiesHandler();
		String strApiKey = propertiesHandler
				.getPropertyValue(Constants.Properties.API_KEY_CLICKATELL);
		String strUser = propertiesHandler
				.getPropertyValue(Constants.Properties.USER_CLICKATELL);
		String strPassword = propertiesHandler
				.getPropertyValue(Constants.Properties.PWD_CLICKATELL);
		SmsSender smsSender = null;
		try {
			smsSender = SmsSender.getClickatellSender(strUser, strPassword,
					strApiKey);
		} catch (SmsException e2) {
			e2.printStackTrace();
		}
		return smsSender;
	}

	/**
	 * Method that sends SMSs via the Clickatell gateway.
	 * 
	 * @param strPhone
	 *            the phone number
	 * @param strUsername
	 *            the username
	 * */
	public void sendSms(String strPhone, String strUsername) {
		StringBuffer strMsgToSend = new StringBuffer(
				Constants.STANDARD_SMS_TEXT).append(strUsername);
		String strSender = Constants.SENDER;
		// hardcoded - to be decided if this is private info - it should be the
		// phone number
		// of the user performing the search
		if (m_smsSender == null) {
			return;
		}
		try {
			m_smsSender.connect();
			m_smsSender.sendTextSms(strMsgToSend.toString(), strPhone,
					strSender);
		} catch (SmsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				m_smsSender.disconnect();
			} catch (SmsException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

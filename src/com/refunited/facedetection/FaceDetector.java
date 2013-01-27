package com.refunited.facedetection;

import java.util.ArrayList;
import java.util.List;
import com.github.mhendred.face4j.DefaultFaceClient;
import com.github.mhendred.face4j.FaceClient;
import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;
import com.github.mhendred.face4j.model.Face;
import com.github.mhendred.face4j.model.Guess;
import com.github.mhendred.face4j.model.Photo;
import com.refunited.datamodel.User;
import com.refunited.utils.Constants;
import com.refunited.utils.PropertiesHandler;

/**
 * @author Elena
 * 
 */
public class FaceDetector {

	/** The namespace for labelling. */
	private static final String NAMESPACE = "nsrefunited";
	/** The all namespace.*/
	private static final String ALL = "all@" + NAMESPACE;



	public static List<User> searchByPhoto(String strUrl, String strUsername,
			List<User> lstUsers) throws FaceClientException,
			FaceServerException {
		PropertiesHandler propertiesHandler = new PropertiesHandler();
		String faceapikey = propertiesHandler
				.getPropertyValue(Constants.Properties.API_KEY_FACE);
		String faceapisec = propertiesHandler
				.getPropertyValue(Constants.Properties.API_SECRET_FACE);
		FaceClient faceClient = new DefaultFaceClient(faceapikey, faceapisec);
		Photo photo = faceClient.detect(strUrl).get(0);
		Face f = photo.getFace();
		List<User> lstFoundUsers = new ArrayList<User>();
		for (User user : lstUsers) {
			if (!strUsername.equalsIgnoreCase(user.getUsername())) {
				String strTrainingLabel = composeTrainingLabel(user
						.getUsername());
				faceClient.saveTags(f.getTID(), strTrainingLabel, "a label");
				faceClient.status(strTrainingLabel);
				faceClient.train(strTrainingLabel);
				photo = faceClient.recognize(user.getUrl(), ALL).get(0);
				for (Face face : photo.getFaces()) {
					List<Guess> guesses = face.getGuesses();
					if (guesses != null && guesses.size() != 0) {
						for (int index = 0; index < guesses.size(); index++) {
							String strLabel = guesses.get(index).toString();
							if (strLabel.contains(getRecognitionLabel(user
									.getUsername())))
								lstFoundUsers.add(user);// the threshold for
														// accuracy is part of the guess
							System.out.println("face guesses "
									+ face.getGuesses());
						}
					}
				}
			}
		}
		return lstFoundUsers;
	}

	/**
	 * Method that creates a training label out of username and namespace
	 * 
	 * @param strusername
	 *            the username
	 * @return the training label
	 * */
	public static String composeTrainingLabel(final String strUsername) {
		return new StringBuffer(strUsername).append("@").append(NAMESPACE)
				.toString();// use sb for speed
	}

	/**
	 * Method that composes a recognition label.
	 * 
	 * @param strUsername
	 *            the username
	 * @return the recognition label
	 * */
	public static String getRecognitionLabel(final String strUsername) {
		return new StringBuffer("=").append(strUsername).append("@").toString();

	}

}

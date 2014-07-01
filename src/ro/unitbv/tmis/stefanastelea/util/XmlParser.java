package ro.unitbv.tmis.stefanastelea.util;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import ro.unitbv.tmis.stefanastelea.datamodel.Project;
import ro.unitbv.tmis.stefanastelea.datamodel.User;

public class XmlParser {

	/**
	 * Login -> Result as User
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public User parseXML(XmlPullParser parser) throws XmlPullParserException,
			IOException {

		int eventType = parser.getEventType();
		User result = new User();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			String name = null;

			switch (eventType) {
			case XmlPullParser.START_TAG:
				name = parser.getName();
				if (name.equals("id")) {
					result.setId(Integer.parseInt(parser.nextText()));
				} else if (name.equals("Error")) {
					System.out.println("Web API Error!");
				} else if (name.equals("firstName")) {
					result.setFirstName(parser.nextText());
				} else if (name.equals("lastName")) {
					result.setLastName(parser.nextText());
				} else if (name.equals("email")) {
					result.setEmail(parser.nextText());
				} else if (name.equals("username")) {
					result.setUsername(parser.nextText());
				} else if (name.equals("userType")) {
					result.setUserType(parser.nextText());
				}

				break;

			case XmlPullParser.END_TAG:
				break;
			} // end switch

			eventType = parser.next();
		} // end while

		return result;
	}

	public Project parseXMLProject(XmlPullParser parser)
			throws XmlPullParserException, IOException {

		int eventType = parser.getEventType();
		Project result = new Project();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			String name = null;

			switch (eventType) {
			case XmlPullParser.START_TAG:
				name = parser.getName();
				if (name.equals("id")) {
					result.setId(Integer.parseInt(parser.nextText()));
				} else if (name.equals("Error")) {
					System.out.println("Web API Error!");
				} else if (name.equals("name")) {
					result.setName(parser.nextText());
				} else if (name.equals("description")) {
					result.setDescription(parser.nextText());
				} else if (name.equals("status")) {
					result.setStatus(Integer.parseInt(parser.nextText()));
				}

				break;

			case XmlPullParser.END_TAG:
				break;
			} // end switch

			eventType = parser.next();
		} // end while

		return result;
	}
}

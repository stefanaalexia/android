//package ro.unitbv.tmis.stefanastelea.proxy;
//
//import java.io.BufferedInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//import org.xmlpull.v1.XmlPullParserFactory;
//
//import ro.unitbv.tmis.stefanastelea.activities.DisplayHomeActivity;
//import ro.unitbv.tmis.stefanastelea.activities.MainActivity;
//import ro.unitbv.tmis.stefanastelea.datamodel.User;
//import android.content.Intent;
//import android.os.AsyncTask;
//
//public class CallAPI extends AsyncTask<String, String, String> {
//
//		@Override
//		protected String doInBackground(String... params) {
//			String urlString = params[0];
//			String urlParameters = params[1];
//			String resultToDisplay;
//			User result = null;
//			InputStream in = null;
//
//			// HTTP Get
//			try {
//				URL url = new URL(urlString);
//				HttpURLConnection urlConnection = (HttpURLConnection) url
//						.openConnection();
//				urlConnection.setRequestMethod("POST");
//
//				urlConnection.setRequestProperty("Content-Type",
//						"application/x-www-form-urlencoded");
//
//				urlConnection.setRequestProperty("Content-Length",
//						"" + Integer.toString(urlParameters.getBytes().length));
//				urlConnection.setRequestProperty("Content-Language", "en-US");
//
//				urlConnection.setUseCaches(false);
//				urlConnection.setDoInput(true);
//				urlConnection.setDoOutput(true);
//
//				// Send request
//				DataOutputStream wr = new DataOutputStream(
//						urlConnection.getOutputStream());
//				wr.writeBytes(urlParameters);
//				wr.flush();
//				wr.close();
//
//				int code = urlConnection.getResponseCode();
//				System.out.println(code);
//
//				in = new BufferedInputStream(urlConnection.getInputStream());
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//				return e.getMessage();
//			}
//
//			// Parse XML
//			XmlPullParserFactory pullParserFactory;
//
//			try {
//				pullParserFactory = XmlPullParserFactory.newInstance();
//				XmlPullParser parser = pullParserFactory.newPullParser();
//
//				parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,
//						false);
//				parser.setInput(in, null);
//				result = xmlParser.parseXML(parser);
//			} catch (XmlPullParserException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			// Simple logic to determine if the email is dangerous, invalid, or
//			// valid
//			if (result != null) {
//				if (result.getFirstName().equals("Stefana")) {
//					resultToDisplay = "Stefana logged in";
//				} else {
//					resultToDisplay = "Someone else logged in";
//				}
//			} else {
//				resultToDisplay = "Exception Occured";
//			}
//
//			return resultToDisplay;
//		}
//
//		protected void onPostExecute(String result) {
//			Intent intent = new Intent(MainActivity.this,
//					DisplayHomeActivity.class);
//			intent.putExtra("result", result);
//			startActivity(intent);
//		}
//
//	} // end CallAPI
// }

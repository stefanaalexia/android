package ro.unitbv.tmis.stefanastelea.activities;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import ro.unitbv.tmis.stefanastelea.R;
import ro.unitbv.tmis.stefanastelea.datamodel.User;
import ro.unitbv.tmis.stefanastelea.util.ApplicationConstants;
import ro.unitbv.tmis.stefanastelea.util.XmlParser;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText usernameEditText = null;
	private EditText passwordEditText = null;
	private Button login;
	private XmlParser xmlParser;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		xmlParser = new XmlParser();
		setContentView(R.layout.activity_main);
		usernameEditText = (EditText) findViewById(R.id.editTextEmail);
		passwordEditText = (EditText) findViewById(R.id.editTextPassword);

		login = (Button) findViewById(R.id.buttonLogin);

	}

	public void onClick(View view) {
		login(view);
	}

	String urlParameters = "";

	public void login(View view) {
		if (usernameEditText.getText().toString() != null
				&& passwordEditText.getText() != null) {

			try {
				urlParameters = "email="
						+ URLEncoder.encode(usernameEditText.getText()
								.toString(), "UTF-8")
						+ "&password="
						+ URLEncoder.encode(passwordEditText.getText()
								.toString(), "UTF-8");

				// urlParameters = "email="
				// + URLEncoder
				// .encode("stefana_stelea@yahoo.com", "UTF-8")
				// + "&password="
				// + URLEncoder.encode("asdASD123@", "UTF-8");

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String urlString = ApplicationConstants.apiURL
					+ ApplicationConstants.LOGIN;

			new CallAPI().execute(urlString);
			Toast.makeText(getApplicationContext(), "Redirecting...",
					Toast.LENGTH_SHORT).show();

		} else {
			Toast.makeText(getApplicationContext(), "Wrong Credentials",
					Toast.LENGTH_SHORT).show();
		}
	}

	private class CallAPI extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String urlString = params[0];
			String resultToDisplay;
			user = null;
			InputStream in = null;

			// HTTP Get
			try {
				URL url = new URL(urlString);
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				urlConnection.setRequestMethod("POST");

				urlConnection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");

				urlConnection.setRequestProperty("Content-Length",
						"" + Integer.toString(urlParameters.getBytes().length));
				urlConnection.setRequestProperty("Content-Language", "en-US");

				urlConnection.setUseCaches(false);
				urlConnection.setDoInput(true);
				urlConnection.setDoOutput(true);

				// Send request
				DataOutputStream wr = new DataOutputStream(
						urlConnection.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();

				int code = urlConnection.getResponseCode();
				System.out.println(code);

				in = new BufferedInputStream(urlConnection.getInputStream());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return e.getMessage();
			}

			// Parse XML
			XmlPullParserFactory pullParserFactory;

			try {
				pullParserFactory = XmlPullParserFactory.newInstance();
				XmlPullParser parser = pullParserFactory.newPullParser();

				parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,
						false);
				parser.setInput(in, null);
				user = xmlParser.parseXML(parser);
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Simple logic to determine if the email is dangerous, invalid, or
			// valid
			if (user != null) {
				if (user.getFirstName().equals("Stefana")) {
					resultToDisplay = "Stefana logged in";
				} else {
					resultToDisplay = "Someone else logged in";
				}
			} else {
				resultToDisplay = "Exception Occured";
			}

			return resultToDisplay;
		}

		protected void onPostExecute(String result) {
			if (result.equals("Exception Occured")) {
				findViewById(R.id.textViewWrongCredentials).setVisibility(
						View.VISIBLE);
				Toast.makeText(getApplicationContext(), "Wrong Credentials",
						Toast.LENGTH_SHORT).show();
			} else {
				findViewById(R.id.textViewWrongCredentials).setVisibility(
						View.INVISIBLE);
				Intent intent = new Intent(MainActivity.this,
						DisplayHomeActivity.class);
				intent.putExtra("result", result);
				intent.putExtra("User", user);
				startActivity(intent);
			}
		}

	} // end CallAPI

}
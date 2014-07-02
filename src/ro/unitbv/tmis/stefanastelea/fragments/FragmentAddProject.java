package ro.unitbv.tmis.stefanastelea.fragments;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import ro.unitbv.tmis.stefanastelea.R;
import ro.unitbv.tmis.stefanastelea.activities.UserActivity;
import ro.unitbv.tmis.stefanastelea.util.ApplicationConstants;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentAddProject extends Fragment {

	private EditText projectNameEditText = null;
	private EditText projectDescriptionEditText = null;
	private Button addProjectButton;
	View rootView;

	String urlParameters = "";
	private UserActivity displayActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_propose_project,
				container, false);

		projectNameEditText = (EditText) rootView
				.findViewById(R.id.editTextAddProjectName);
		projectDescriptionEditText = (EditText) rootView
				.findViewById(R.id.editTextAddProjectDescription);
		addProjectButton = (Button) rootView
				.findViewById(R.id.buttonAddProject);
		addProjectButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				addProject();
				Toast.makeText(displayActivity, "Button Clicked",
						Toast.LENGTH_SHORT).show();

			}

		});

		return rootView;
	}

	public void addProject() {
		if (projectNameEditText.getText().toString() != null
				&& projectDescriptionEditText.getText() != null) {
			int id = displayActivity.getUser().getId();
			try {
				urlParameters = "id="
						+ URLEncoder.encode(id + "", "UTF-8")
						+ "&name="
						+ URLEncoder.encode(projectNameEditText.getText()
								.toString(), "UTF-8")
						+ "&description="
						+ URLEncoder.encode(projectDescriptionEditText
								.getText().toString(), "UTF-8");

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String urlString = ApplicationConstants.apiURL
					+ ApplicationConstants.ADD_PROJECT;

			new CallAPI().execute(urlString);
			Toast.makeText(displayActivity.getApplicationContext(),
					"Saving project...", Toast.LENGTH_SHORT).show();

		} else {
			Toast.makeText(displayActivity.getApplicationContext(),
					"Please fill in the fields", Toast.LENGTH_SHORT).show();
		}
	}

	private class CallAPI extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String urlString = params[0];
			String resultToDisplay;

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

			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}

			resultToDisplay = "success";

			return resultToDisplay;
		}

		protected void onPostExecute(String result) {
			if (result.equals("Exception Occured")) {
				rootView.findViewById(R.id.textViewWrongData).setVisibility(
						View.VISIBLE);
				Toast.makeText(displayActivity.getApplicationContext(),
						"Wrong Data", Toast.LENGTH_SHORT).show();
			} else {
				rootView.findViewById(R.id.textViewWrongData).setVisibility(
						View.INVISIBLE);

			}
		}
	} // end CallAPI

	@Override
	public void onAttach(Activity myActivity) {
		super.onAttach(myActivity);
		this.displayActivity = (UserActivity) myActivity;
	}
}
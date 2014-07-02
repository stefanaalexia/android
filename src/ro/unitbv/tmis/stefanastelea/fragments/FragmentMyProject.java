package ro.unitbv.tmis.stefanastelea.fragments;

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
import ro.unitbv.tmis.stefanastelea.activities.UserActivity;
import ro.unitbv.tmis.stefanastelea.datamodel.Project;
import ro.unitbv.tmis.stefanastelea.datamodel.User;
import ro.unitbv.tmis.stefanastelea.util.ApplicationConstants;
import ro.unitbv.tmis.stefanastelea.util.XmlParser;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentMyProject extends Fragment {

	private UserActivity displayActivity;
	private View rootView;
	private XmlParser xmlParser;
	String urlParameters = "";
	Project project;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_my_project, container,
				false);
		xmlParser = new XmlParser();
		User user = displayActivity.getUser();
		getMyProject(user.getId());

		return rootView;
	}

	public void updateViews() {
		if (project != null) {
			((TextView) rootView.findViewById(R.id.textview_project_name))
					.setText(project.getName());
			((TextView) rootView
					.findViewById(R.id.textview_project_description))
					.setText(project.getDescription());
			((TextView) rootView.findViewById(R.id.textview_project_status))
					.setText(project.getStatus() + "");

			Toast.makeText(getActivity(), "Succedded getting my project",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void getMyProject(int id) {

		try {
			urlParameters = "id=" + URLEncoder.encode(id + "", "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String urlString = ApplicationConstants.apiURL
				+ ApplicationConstants.GET_MY_PROJECT;

		new CallAPI().execute(urlString);

	}

	private class CallAPI extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String urlString = params[0];
			String resultToDisplay;
			project = null;
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
				project = xmlParser.parseXMLProject(parser);
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Simple logic to determine if the email is dangerous, invalid, or
			// valid
			if (project != null) {

				resultToDisplay = "My project here";

			} else {
				resultToDisplay = "Exception Occured";
			}

			return resultToDisplay;
		}

		protected void onPostExecute(String result) {
			if (result.equals("Exception Occured")) {

				Toast.makeText(getActivity(), "Error getting my project",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), "Success getting my project",
						Toast.LENGTH_SHORT).show();
				updateViews();
			}
		}

	} // end CallAPI

	@Override
	public void onAttach(Activity myActivity) {
		super.onAttach(myActivity);
		this.displayActivity = (UserActivity) myActivity;
	}
}
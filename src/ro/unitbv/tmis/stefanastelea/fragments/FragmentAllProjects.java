package ro.unitbv.tmis.stefanastelea.fragments;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import ro.unitbv.tmis.stefanastelea.R;
import ro.unitbv.tmis.stefanastelea.activities.UserActivity;
import ro.unitbv.tmis.stefanastelea.adapters.MySimpleArrayAdapter;
import ro.unitbv.tmis.stefanastelea.datamodel.Project;
import ro.unitbv.tmis.stefanastelea.util.ApplicationConstants;
import ro.unitbv.tmis.stefanastelea.util.XmlParser;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentAllProjects extends Fragment {

	private UserActivity displayActivity;
	View rootView;
	private List<Project> projects;
	private XmlParser xmlParser;
	String urlParameters = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_all_projects, container,
				false);
		xmlParser = new XmlParser();
		getAllProjects();

		return rootView;
	}

	public void getAllProjects() {
		int userId = displayActivity.getUser().getId();
		try {
			urlParameters = "id=" + URLEncoder.encode(userId + "", "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String urlString = ApplicationConstants.apiURL
				+ ApplicationConstants.GET_ALL_PROJECTS;

		new CallAPI().execute(urlString);

	}

	private class CallAPI extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String urlString = params[0];
			String resultToDisplay;
			projects = null;
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
				projects = xmlParser.parseXMLListProjects(parser);
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Simple logic to determine if the email is dangerous, invalid, or
			// valid
			if (projects != null) {

				resultToDisplay = "Some projects here";

			} else {
				resultToDisplay = "Exception Occured";
			}

			return resultToDisplay;
		}

		protected void onPostExecute(String result) {
			if (result.equals("Exception Occured")) {

				Toast.makeText(getActivity(), "Error getting projects",
						Toast.LENGTH_SHORT).show();
			} else {
				ListView list = (ListView) rootView
						.findViewById(R.id.listviewproposed);
				MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(
						displayActivity, projects);
				list.setAdapter(adapter);

				Toast.makeText(getActivity(), "Succedded getting projects",
						Toast.LENGTH_SHORT).show();

			}
		}
	} // end CallAPI

	@Override
	public void onAttach(Activity myActivity) {
		super.onAttach(myActivity);
		this.displayActivity = (UserActivity) myActivity;
	}
}

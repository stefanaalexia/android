package ro.unitbv.tmis.stefanastelea.fragments;

import ro.unitbv.tmis.stefanastelea.R;
import ro.unitbv.tmis.stefanastelea.activities.UserActivity;
import ro.unitbv.tmis.stefanastelea.datamodel.User;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentMyAccount extends Fragment {

	private UserActivity displayActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_my_account,
				container, false);

		User user = displayActivity.getUser();

		((TextView) rootView.findViewById(R.id.textview_myaccount_id))
				.setText(user.getId() + "");
		((TextView) rootView.findViewById(R.id.textview_myaccount_firstname))
				.setText(user.getFirstName());
		((TextView) rootView.findViewById(R.id.textview_myaccount_lastname))
				.setText(user.getLastName());
		((TextView) rootView.findViewById(R.id.textview_myaccount_email))
				.setText(user.getEmail());
		((TextView) rootView.findViewById(R.id.textview_myaccount_username))
				.setText(user.getUsername());
		((TextView) rootView.findViewById(R.id.textview_myaccount_usertype))
				.setText(user.getUserType());

		return rootView;
	}

	@Override
	public void onAttach(Activity myActivity) {
		super.onAttach(myActivity);
		this.displayActivity = (UserActivity) myActivity;
	}
}

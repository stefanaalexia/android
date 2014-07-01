package ro.unitbv.tmis.stefanastelea.fragments;

import ro.unitbv.tmis.stefanastelea.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentProposeProject extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_propose_projkect,
				container, false);

		return rootView;
	}
}
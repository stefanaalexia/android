package ro.unitbv.tmis.stefanastelea.adapters;

import ro.unitbv.tmis.stefanastelea.fragments.FragmentAllProjects;
import ro.unitbv.tmis.stefanastelea.fragments.FragmentMyAccount;
import ro.unitbv.tmis.stefanastelea.fragments.FragmentProposeProject;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new FragmentMyAccount();
		case 1:
			// Games fragment activity
			return new FragmentProposeProject();
		case 2:
			// Movies fragment activity
			return new FragmentAllProjects();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
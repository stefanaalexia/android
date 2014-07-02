package ro.unitbv.tmis.stefanastelea.adapters;

import ro.unitbv.tmis.stefanastelea.fragments.FragmentAllProjects;
import ro.unitbv.tmis.stefanastelea.fragments.FragmentMyAccount;
import ro.unitbv.tmis.stefanastelea.fragments.FragmentMyProject;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class TabsPagerAdapterStudent extends TabsPagerAdapter {

	public TabsPagerAdapterStudent(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new FragmentMyAccount();
		case 1:

			return new FragmentMyProject();
		case 2:
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
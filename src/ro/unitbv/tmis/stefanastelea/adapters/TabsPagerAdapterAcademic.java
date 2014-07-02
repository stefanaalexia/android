package ro.unitbv.tmis.stefanastelea.adapters;

import ro.unitbv.tmis.stefanastelea.fragments.FragmentAddProject;
import ro.unitbv.tmis.stefanastelea.fragments.FragmentAllProjects;
import ro.unitbv.tmis.stefanastelea.fragments.FragmentMyAccount;
import ro.unitbv.tmis.stefanastelea.fragments.FragmentProposedProjects;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class TabsPagerAdapterAcademic extends TabsPagerAdapter {

	public TabsPagerAdapterAcademic(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new FragmentMyAccount();
		case 1:
			return new FragmentAddProject();
		case 2:
			return new FragmentAllProjects();
		case 3:
			return new FragmentProposedProjects();

		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
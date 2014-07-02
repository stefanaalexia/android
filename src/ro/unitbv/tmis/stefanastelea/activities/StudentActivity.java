package ro.unitbv.tmis.stefanastelea.activities;

import ro.unitbv.tmis.stefanastelea.util.ApplicationConstants;

public class StudentActivity extends UserActivity {

	private static String[] tabs = { "My Account", "My Project", "All Projects" };

	public StudentActivity() {
		super(tabs, ApplicationConstants.STUDENT);
	}

}

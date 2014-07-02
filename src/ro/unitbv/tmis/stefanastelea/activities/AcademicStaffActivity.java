package ro.unitbv.tmis.stefanastelea.activities;

import ro.unitbv.tmis.stefanastelea.util.ApplicationConstants;

public class AcademicStaffActivity extends UserActivity {

	private static String[] tabs = { "My Account", "Propose Project",
			"All Projects", "Proposed Projects" };

	public AcademicStaffActivity() {
		super(tabs, ApplicationConstants.ACADEMIC_STAFF);
	}
}

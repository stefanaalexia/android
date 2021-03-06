package ro.unitbv.tmis.stefanastelea.activities;

import ro.unitbv.tmis.stefanastelea.R;
import ro.unitbv.tmis.stefanastelea.adapters.TabsPagerAdapter;
import ro.unitbv.tmis.stefanastelea.adapters.TabsPagerAdapterAcademic;
import ro.unitbv.tmis.stefanastelea.adapters.TabsPagerAdapterStudent;
import ro.unitbv.tmis.stefanastelea.datamodel.User;
import ro.unitbv.tmis.stefanastelea.util.ApplicationConstants;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;

public class UserActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs;
	private String type;
	private User user;

	public UserActivity(String[] tabs, String user_type) {
		this.tabs = new String[tabs.length];
		this.tabs = tabs;
		this.type = user_type;
	}

	public User getUser() {
		return user;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// request action bar
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		setContentView(R.layout.main_tab);

		user = (User) getIntent().getSerializableExtra("User");

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		if (type.equals(ApplicationConstants.STUDENT)) {
			mAdapter = new TabsPagerAdapterStudent(getSupportFragmentManager());
		} else if (type.equals(ApplicationConstants.ACADEMIC_STAFF)) {
			mAdapter = new TabsPagerAdapterAcademic(getSupportFragmentManager());
		}

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
}

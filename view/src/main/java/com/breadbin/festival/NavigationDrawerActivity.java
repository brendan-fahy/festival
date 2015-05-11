package com.breadbin.festival;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.navdrawer.NavigationDrawerFragment;
import com.breadbin.festival.news.NewsFragment;
import com.breadbin.festival.presenter.ContentPresenter;
import com.breadbin.festival.presenter.busevents.ArticlesListRetrievedEvent;
import com.breadbin.festival.presenter.busevents.OfflineEvent;
import com.breadbin.festival.presenter.busevents.ScheduleRetrievedEvent;
import com.breadbin.festival.schedule.SchedulePagerFragment;

import de.greenrobot.event.EventBus;

public abstract class NavigationDrawerActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	private static final String KEPT_FRAGMENT_KEY = "keptFragment";
	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	private DrawerLayout drawerLayout;
	private boolean userLearnedDrawer;

	private String[] navDrawerOptions = new String[] {
			NewsFragment.class.getName(),
			SchedulePagerFragment.class.getName()
	};

	private Fragment currentFragment;

	public abstract ContentRestClient.ContentRestClientConfig getRestClientConfig();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		mNavigationDrawerFragment = (NavigationDrawerFragment)
				getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(
				R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		// Check for an existing Fragment to restore
		if (savedInstanceState != null) {
			currentFragment = getSupportFragmentManager().getFragment(savedInstanceState, KEPT_FRAGMENT_KEY);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		getSupportFragmentManager().putFragment(outState, KEPT_FRAGMENT_KEY, currentFragment);
	}

	@Override
	protected void onStart() {
		super.onStart();

		EventBus.getDefault().register(this);

		if (currentFragment == null) {
			fetchNewsArticles();
		} else {
			updateCurrentFragment();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// Quick hack using a String array of the Fragment names, making sure to remember that the 0th element in the Nav Drawer list is the header, so have to do [position -1]
		if (currentFragment != null && !navDrawerOptions[position - 1].equals(currentFragment.getClass().getName())) {
			switch (position) {
				case 1:
					fetchNewsArticles();
					break;
				case 2:
					fetchCalendarEvents();
					break;
			}
		}
	}

	private void fetchNewsArticles() {
		ContentPresenter.getInstance(this, getRestClientConfig()).fetchNewsArticlesList();
	}

	private void fetchCalendarEvents() {
		ContentPresenter.getInstance(this, getRestClientConfig()).fetchEventsList();
	}

	public void onEvent(ArticlesListRetrievedEvent event) {
		currentFragment = NewsFragment.newInstance(event.getArticleList());
		updateCurrentFragment();
	}

	public void onEvent(ScheduleRetrievedEvent event) {
		currentFragment = SchedulePagerFragment.newInstance(event.getSchedule());
		updateCurrentFragment();
	}

	public void onEvent(OfflineEvent event) {
		TextView defaultText = ((TextView) findViewById(R.id.default_text));
		if (defaultText != null) {
			defaultText.setText(R.string.offline_error);
		}
	}

	public void updateToolbarForNavDrawer(Toolbar toolbar, int titleStringId) {
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(titleStringId);

		getActionBarDrawerToggle(toolbar, titleStringId);
	}

	private ActionBarDrawerToggle getActionBarDrawerToggle(Toolbar toolbar, int stringId) {
		userLearnedDrawer = PreferenceManager.getDefaultSharedPreferences(NavigationDrawerActivity.this).getBoolean(NavigationDrawerFragment.PREF_USER_LEARNED_DRAWER, false);

		ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
				this,
				drawerLayout,
				toolbar,
				stringId,
				stringId
		) {
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (!userLearnedDrawer) {
					// The user manually opened the drawer; store this flag to prevent auto-showing
					// the navigation drawer automatically in the future.
					userLearnedDrawer = true;
					SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(NavigationDrawerActivity.this);
					sp.edit().putBoolean(NavigationDrawerFragment.PREF_USER_LEARNED_DRAWER, true).apply();
				}
			}
		};

		drawerLayout.setDrawerListener(drawerToggle);
		drawerToggle.syncState();

		return drawerToggle;
	}

	protected void updateCurrentFragment() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, currentFragment)
				.commit();}

}

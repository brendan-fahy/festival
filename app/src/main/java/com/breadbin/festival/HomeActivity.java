package com.breadbin.festival;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.navdrawer.NavigationDrawerFragment;
import com.breadbin.festival.news.NewsFragment;
import com.breadbin.festival.presenter.ContentPresenter;
import com.breadbin.festival.presenter.busevents.ArticlesListRetrievedEvent;
import com.breadbin.festival.presenter.busevents.ScheduleRetrievedEvent;
import com.breadbin.festival.schedule.SchedulePagerFragment;

import de.greenrobot.event.EventBus;

public class HomeActivity extends BaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	private DrawerLayout drawerLayout;
	private boolean userLearnedDrawer;

	private NavigationDrawerFragment.NavDrawerItem currentNavDrawerItem;

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
	}

	private void fetchNewsArticles() {
		ContentPresenter.getInstance(this, restClientConfig).fetchNewsArticlesList();
	}

	private void fetchCalendarEvents() {
		ContentPresenter.getInstance(this, restClientConfig).fetchEventsList();
	}

	public void onEvent(ArticlesListRetrievedEvent event) {
		currentNavDrawerItem = NavigationDrawerFragment.NavDrawerItem.values()[1];
		replaceFragment(NewsFragment.newInstance(event.getArticleList()));
	}

	public void onEvent(ScheduleRetrievedEvent event) {
		currentNavDrawerItem = NavigationDrawerFragment.NavDrawerItem.values()[2];
		replaceFragment(SchedulePagerFragment.newInstance(event.getSchedule()));
	}

	@Override
	protected void onStart() {
		super.onStart();

		EventBus.getDefault().register(this);

		fetchNewsArticles();
	}

	@Override
	protected void onStop() {
		super.onStop();

		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		NavigationDrawerFragment.NavDrawerItem selectedItem = NavigationDrawerFragment.NavDrawerItem.values()[position];
		if (selectedItem != currentNavDrawerItem) {
			switch (selectedItem) {
				case NEWS:
					fetchNewsArticles();
					break;
				case SCHEDULE:
					fetchCalendarEvents();
					break;
			}
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
		userLearnedDrawer = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getBoolean(NavigationDrawerFragment.PREF_USER_LEARNED_DRAWER, false);

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
					SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
					sp.edit().putBoolean(NavigationDrawerFragment.PREF_USER_LEARNED_DRAWER, true).apply();
				}
			}
		};

		drawerLayout.setDrawerListener(drawerToggle);
		drawerToggle.syncState();

		return drawerToggle;
	}

	private ContentRestClient.ContentRestClientConfig restClientConfig = new ContentRestClient.ContentRestClientConfig() {
		@Override
		public String getCalendarEndpoint() {
			return getString(R.string.googleCalendarEndpoint);
		}

		@Override
		public String getRssEndpoint() {
			return getString(R.string.rssEndpoint);
		}
	};

}

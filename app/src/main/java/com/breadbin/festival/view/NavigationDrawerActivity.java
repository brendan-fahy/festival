package com.breadbin.festival.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.app.R;
import com.breadbin.festival.presenter.ContentPresenter;
import com.breadbin.festival.presenter.busevents.ArticlesListRetrievedEvent;
import com.breadbin.festival.presenter.busevents.OfflineEvent;
import com.breadbin.festival.presenter.busevents.ScheduleRetrievedEvent;
import com.breadbin.festival.view.news.NewsFragment;
import com.breadbin.festival.view.schedule.SchedulePagerFragment;

import de.greenrobot.event.EventBus;

public abstract class NavigationDrawerActivity extends AppCompatActivity {

	private static final String KEPT_FRAGMENT_KEY = "keptFragment";
	private static final String PREF_USER_LEARNED_DRAWER = "PREF_USER_LEARNED_DRAWER";

	private DrawerLayout drawerLayout;
	private NavigationView navigationView;
	private boolean userLearnedDrawer;

	private Fragment currentFragment;

	public abstract ContentRestClient.ContentRestClientConfig getRestClientConfig();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		navigationView = (NavigationView) findViewById(R.id.navigation_drawer);

		setupNavigationDrawer();

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

	private void setupNavigationDrawer() {
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem menuItem) {
				if (R.id.nav_drawer_news == menuItem.getItemId()) {
					fetchNewsArticles();
				} else if (R.id.nav_drawer_schedule == menuItem.getItemId()) {
					fetchCalendarEvents();
				}
				menuItem.setChecked(true);
				drawerLayout.closeDrawer(navigationView);
				return true;
			}
		});
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
		userLearnedDrawer = PreferenceManager.getDefaultSharedPreferences(NavigationDrawerActivity.this).getBoolean(PREF_USER_LEARNED_DRAWER, false);

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
					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
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

package com.breadbin.festival;

import android.content.Context;
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

import com.breadbin.festival.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomescreenView {

	private static final String KEPT_FRAGMENT_KEY = "keptFragment";
	private static final String PREF_USER_LEARNED_DRAWER = "PREF_USER_LEARNED_DRAWER";

  @Bind(R.id.drawer_layout)
	DrawerLayout drawerLayout;
  @Bind(R.id.navigation_drawer)
	NavigationView navigationView;
  @Bind(R.id.default_text)
  TextView defaultText;

	private boolean userLearnedDrawer;

	private Fragment currentFragment;

  private HomescreenPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

    ButterKnife.bind(this);

		setupNavigationDrawer();

    presenter = new HomescreenPresenterImpl(this);
    presenter.onCreate(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		getSupportFragmentManager().putFragment(outState, KEPT_FRAGMENT_KEY, currentFragment);
	}

  @Override
	public void setupNavigationDrawer() {
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (R.id.nav_drawer_news == menuItem.getItemId()) {
          presenter.fetchNewsArticles();
        } else if (R.id.nav_drawer_schedule == menuItem.getItemId()) {
          presenter.fetchCalendarEvents();
        }
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(navigationView);
        return true;
      }
    });
	}

  @Override
  public Fragment checkForExistingView(Bundle savedInstanceState) {
    return (savedInstanceState != null)
        ? getSupportFragmentManager().getFragment(savedInstanceState, KEPT_FRAGMENT_KEY)
        : null;
  }

  @Override
  public void updateToolbarForNavDrawer(Toolbar toolbar, int titleStringId) {
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle(titleStringId);

    getActionBarDrawerToggle(toolbar, titleStringId);
  }

  @Override
  public void updateCurrentView(Fragment fragment) {
    currentFragment = fragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction()
        .replace(R.id.container, currentFragment)
        .commit();
  }

  @Override
  public void showDefaultText() {
    if (defaultText != null) {
      defaultText.setText(R.string.offline_no_data_error);
    }
  }

  @Override
  public Context getContext() {
    return getApplicationContext();
  }

	private ActionBarDrawerToggle getActionBarDrawerToggle(Toolbar toolbar, int stringId) {
		userLearnedDrawer = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getBoolean(PREF_USER_LEARNED_DRAWER, false);

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
					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
				}
			}
		};

		drawerLayout.setDrawerListener(drawerToggle);
		drawerToggle.syncState();

		return drawerToggle;
	}
}

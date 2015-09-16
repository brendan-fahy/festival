package com.breadbin.festival;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.breadbin.festival.app.R;
import com.breadbin.festival.common.ContentModel;
import com.breadbin.festival.common.api.ContentRestClient;
import com.breadbin.festival.common.api.NoDataException;
import com.breadbin.festival.news.NewsFragment;
import com.breadbin.festival.news.model.Article;
import com.breadbin.festival.schedule.SchedulePagerFragment;
import com.breadbin.festival.schedule.model.Schedule;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.functions.Action1;

public abstract class NavigationDrawerActivity extends AppCompatActivity {

	private static final String KEPT_FRAGMENT_KEY = "keptFragment";
	private static final String PREF_USER_LEARNED_DRAWER = "PREF_USER_LEARNED_DRAWER";

  @InjectView(R.id.drawer_layout)
	DrawerLayout drawerLayout;
  @InjectView(R.id.navigation_drawer)
	NavigationView navigationView;

	private boolean userLearnedDrawer;

	private Fragment currentFragment;

	public abstract ContentRestClient.ContentRestClientConfig getRestClientConfig();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

    ButterKnife.inject(this);

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

		if (currentFragment == null) {
			fetchNewsArticles();
		} else {
			updateCurrentFragment();
		}
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
    ContentModel.getInstance(this, getRestClientConfig()).fetchNewsArticlesList()
        .subscribe(new Action1<List<Article>>() {
          @Override
          public void call(List<Article> articles) {
            currentFragment = NewsFragment.newInstance(articles);
            updateCurrentFragment();
          }
        }, onErrorAction);
  }

  private Action1<Throwable> onErrorAction = new Action1<Throwable>() {
    @Override
    public void call(Throwable e) {
      if (e instanceof NoDataException) {
        TextView defaultText = ((TextView) findViewById(R.id.default_text));
        if (defaultText != null) {
          defaultText.setText(R.string.offline_no_data_error);
        }
        Log.d("NavDrawerActivity", "Observable onError, " + e.getMessage());
      }
    }
  };

  private void fetchCalendarEvents() {
    ContentModel.getInstance(this, getRestClientConfig()).fetchEventsList()
        .subscribe(new Action1<Schedule>() {
          @Override
          public void call(Schedule schedule) {
            currentFragment = SchedulePagerFragment.newInstance(schedule);
            updateCurrentFragment();
          }
        }, onErrorAction);
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

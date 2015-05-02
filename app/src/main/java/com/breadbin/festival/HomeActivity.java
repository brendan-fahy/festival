package com.breadbin.festival;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.breadbin.festival.presenter.ContentPresenter;
import com.breadbin.festival.presenter.busevents.ScheduleRetrievedEvent;
import com.breadbin.festival.rss.RssFragment;
import com.breadbin.festival.schedule.SchedulePagerFragment;

import de.greenrobot.event.EventBus;

public class HomeActivity extends NavigationDrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();

//		fetchCalendarEvents();
		fetchRssArticles();
	}

	private void fetchCalendarEvents() {
		ContentPresenter.getInstance(this).fetchEventsList();
	}

	private void fetchRssArticles() {
		ContentPresenter.getInstance(this).fetchRssArticlesList();
	}

	public void onEvent(ScheduleRetrievedEvent event) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, SchedulePagerFragment.newInstance(event.getSchedule()))
				.commit();
	}

	@Override
	protected void onStart() {
		super.onStart();

		EventBus.getDefault().register(this);

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, new RssFragment())
				.commit();
	}

	@Override
	protected void onStop() {
		super.onStop();

		EventBus.getDefault().unregister(this);
	}

}

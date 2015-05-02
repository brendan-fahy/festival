package com.breadbin.festival;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.breadbin.festival.presenter.ContentPresenter;
import com.breadbin.festival.presenter.busevents.ScheduleRetrievedEvent;
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

		fetchCalendarEvents();
	}

	private void fetchCalendarEvents() {
		ContentPresenter.getInstance(this).fetchEventsList();
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
	}

	@Override
	protected void onStop() {
		super.onStop();

		EventBus.getDefault().unregister(this);
	}

}

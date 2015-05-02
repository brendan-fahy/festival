package com.breadbin.festival;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.api.googlecalendar.CalendarCallback;
import com.breadbin.festival.storage.CalendarDataStorage;
import com.model.error.ErrorResponse;
import com.model.googlecalendarapi.CalendarData;
import com.model.googlecalendarapi.CalendarResponse;


public class HomeActivity extends NavigationDrawerActivity {

	public static final String CALENDAR_DATA_KEY = "calendar_data";
	public static final String CALENDAR_STORAGE_NAME = "calendar_storage";

	private CalendarDataStorage storage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		storage = new CalendarDataStorage(this, CALENDAR_STORAGE_NAME);
	}

	@Override
	protected void onResume() {
		super.onResume();

		CalendarData calendarData = storage.find(CALENDAR_DATA_KEY);
		if (calendarData != null) {

			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.container, CalendarEventsListFragment.newInstance(calendarData))
					.commit();
		} else {
			getCalendarEvents();
		}
	}

	private void getCalendarEvents() {
		final String googleCalendarEndpoint = "http://www.google.com/calendar/feeds/volunteers@ejc2014.org/public/basic?alt=jsonc&max-results=2000";
		final String rssEndpoint = "http://www.ejc2014.org/?option=com_content&view=category&layout=blog&id=43&format=fe%20ed&type=rss&utm_campaign=apps&utm_medium=android&utm_source=rss_feed";
		ContentRestClient restClient = new ContentRestClient(this, new ContentRestClient.ContentRestClientConfig() {
			@Override
			public String getCalendarEndpoint() {
				return googleCalendarEndpoint;
			}

			@Override
			public String getRssEndpoint() {
				return rssEndpoint;
			}
		});
		restClient.getCalendarEvents(getCalendarCallback());
	}

	private CalendarCallback getCalendarCallback() {
		return new CalendarCallback() {
			@Override
			public void onSuccess(CalendarResponse calendarResponse) {
				storage.save(CALENDAR_DATA_KEY, calendarResponse.getData());
				Log.d("CalendarCallback", "Number of items retrieved: " + calendarResponse.getData().getItems().size());
			}

			@Override
			public void onFailure(ErrorResponse errorResponse) {

			}

			@Override
			public void onFinish() {

			}
		};
	}

}

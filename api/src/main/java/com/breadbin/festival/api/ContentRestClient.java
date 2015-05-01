package com.breadbin.festival.api;

import android.content.Context;

import com.breadbin.festival.api.googlecalendar.CalendarCallback;
import com.breadbin.festival.api.googlecalendar.GoogleCalendarConnector;
import com.breadbin.festival.api.rss.RssConnector;
import com.breadbin.festival.api.rss.RssHandler;

public class ContentRestClient {

	private GoogleCalendarConnector calendarConnector;

	private RssConnector rssConnector;

	private RssHandler rssHandler;

	public ContentRestClient(Context context, ContentRestClientConfig config) {
		calendarConnector = new GoogleCalendarConnector(context, config.getCalendarEndpoint());
	}

	public void getCalendarEvents(CalendarCallback callback) {
		calendarConnector.getCalendarEvents(callback);
	}

	public interface ContentRestClientConfig {

		String getCalendarEndpoint();

		String getRssEndpoint();

	}

}

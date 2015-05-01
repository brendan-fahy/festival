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

	public ContentRestClient(Context context, String calendarEndpoint, String rssEndpoint) {
		calendarConnector = new GoogleCalendarConnector(context, calendarEndpoint);
	}

	public void getCalendarEvents(CalendarCallback callback) {
		calendarConnector.getCalendarEvents(callback);
	}

}

package com.breadbin.festival.api;

import android.content.Context;

import com.breadbin.festival.api.googlecalendar.CalendarCallback;
import com.breadbin.festival.api.googlecalendar.GoogleCalendarConnector;
import com.breadbin.festival.api.rss.RssConnector;
import com.breadbin.festival.api.rss.RssHandler;

/**
 * Created by brendanfahy1 on 02/05/15.
 */
public class DefaultContentRestClient extends ContentRestClient {

	private GoogleCalendarConnector calendarConnector;

	private RssConnector rssConnector;

	private RssHandler rssHandler;

	public DefaultContentRestClient(Context context, ContentRestClient.ContentRestClientConfig config) {
		super(config);
		calendarConnector = new GoogleCalendarConnector(context, config.getCalendarEndpoint());
	}

	public void getCalendarEvents(CalendarCallback callback) {
		calendarConnector.getCalendarEvents(callback);
	}

}

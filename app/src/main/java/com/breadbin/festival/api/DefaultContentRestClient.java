package com.breadbin.festival.api;

import android.content.Context;

import com.breadbin.festival.api.googlecalendar.GoogleCalendarConnector;
import com.breadbin.festival.api.rss.RssConnector;

public class DefaultContentRestClient extends ContentRestClient {

	private GoogleCalendarConnector calendarConnector;

	private RssConnector rssConnector;

	public DefaultContentRestClient(Context context, ContentRestClientConfig config) {
		super(config);
		calendarConnector = new GoogleCalendarConnector(context, config.getCalendarEndpoint());
		rssConnector = new RssConnector(config.getRssEndpoint());
	}

	@Override
	public void getCalendarEvents(Callback callback) {
		calendarConnector.getCalendarEvents(callback);
	}

	@Override
	public void getNewsArticles(Callback callback) {
		rssConnector.getRssArticles(callback);
	}

}

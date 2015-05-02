package com.breadbin.festival.api;

import android.content.Context;

import com.breadbin.festival.api.googlecalendar.CalendarCallback;
import com.breadbin.festival.api.googlecalendar.GoogleCalendarConnector;
import com.breadbin.festival.api.rss.ArticleCallback;
import com.breadbin.festival.api.rss.RssConnector;

public class DefaultContentRestClient extends ContentRestClient {

	private GoogleCalendarConnector calendarConnector;

	private RssConnector rssConnector;

	public DefaultContentRestClient(Context context, ContentRestClient.ContentRestClientConfig config) {
		super(config);
		calendarConnector = new GoogleCalendarConnector(context, config.getCalendarEndpoint());
		rssConnector = new RssConnector(config.getRssEndpoint());
	}

	@Override
	public void getCalendarEvents(CalendarCallback callback) {
		calendarConnector.getCalendarEvents(callback);
	}

	@Override
	public void getNewsArticles(ArticleCallback callback) {
		rssConnector.getRssArticles(callback);
	}

}

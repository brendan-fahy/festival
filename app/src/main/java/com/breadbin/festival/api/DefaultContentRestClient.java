package com.breadbin.festival.api;

import com.breadbin.festival.api.googlecalendar.GoogleCalendarConnector;
import com.breadbin.festival.api.rss.RssConnector;
import com.breadbin.festival.model.googlecalendarapi.CalendarResponse;
import com.breadbin.festival.model.news.Article;

import java.util.List;

import rx.Observable;

public class DefaultContentRestClient extends ContentRestClient {

	private GoogleCalendarConnector calendarConnector;

	private RssConnector rssConnector;
  
  private String calendarEndpoint;

	public DefaultContentRestClient(ContentRestClientConfig config) {
		super(config);
		calendarConnector = new GoogleCalendarConnector(config.getCalendarBaseUrl());
    calendarEndpoint = config.getCalendarEndpoint();
		rssConnector = new RssConnector(config.getRssEndpoint());
	}

	@Override
	public Observable<CalendarResponse> getCalendarEvents() {
		return calendarConnector.getCalendarEvents(calendarEndpoint);
	}

	@Override
	public Observable<List<Article>> getNewsArticles() {
		return rssConnector.getRssArticles();
	}

}

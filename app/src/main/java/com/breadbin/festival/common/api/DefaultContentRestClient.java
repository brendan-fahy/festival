package com.breadbin.festival.common.api;

import com.breadbin.festival.news.model.Article;
import com.breadbin.festival.news.model.api.RssConnector;
import com.breadbin.festival.schedule.model.api.CalendarResponse;
import com.breadbin.festival.schedule.model.api.GoogleCalendarConnector;

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

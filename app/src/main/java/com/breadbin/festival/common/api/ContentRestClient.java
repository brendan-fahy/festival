package com.breadbin.festival.common.api;

import com.breadbin.festival.news.model.Article;
import com.breadbin.festival.schedule.model.api.CalendarResponse;

import java.util.List;

import rx.Observable;

public abstract class ContentRestClient {

	protected final ContentRestClientConfig config;

	public ContentRestClient(ContentRestClientConfig config) {
		this.config = config;
	}

	public abstract Observable<CalendarResponse> getCalendarEvents();

	public abstract Observable<List<Article>> getNewsArticles();

	public interface ContentRestClientConfig {

    String getCalendarBaseUrl();

		String getCalendarEndpoint();

		String getRssEndpoint();

	}

}

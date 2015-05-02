package com.breadbin.festival.api;


import com.breadbin.festival.api.googlecalendar.CalendarCallback;
import com.breadbin.festival.api.rss.ArticleCallback;

public abstract class ContentRestClient {

	protected final ContentRestClientConfig config;

	public ContentRestClient(ContentRestClientConfig config) {
		this.config = config;
	}

	public abstract void getCalendarEvents(CalendarCallback callback);

	public abstract void getNewsArticles(ArticleCallback callback);

	public interface ContentRestClientConfig {

		String getCalendarEndpoint();

		String getRssEndpoint();

	}

}

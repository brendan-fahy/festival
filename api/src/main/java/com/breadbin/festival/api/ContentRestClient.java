package com.breadbin.festival.api;


import com.breadbin.festival.api.googlecalendar.CalendarCallback;

public abstract class ContentRestClient {

	protected final ContentRestClientConfig config;

	public ContentRestClient(ContentRestClientConfig config) {
		this.config = config;
	}

	public abstract void getCalendarEvents(CalendarCallback callback);

	public interface ContentRestClientConfig {

		String getCalendarEndpoint();

		String getRssEndpoint();

	}

}

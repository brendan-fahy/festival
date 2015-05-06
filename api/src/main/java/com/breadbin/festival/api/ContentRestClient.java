package com.breadbin.festival.api;

public abstract class ContentRestClient {

	protected final ContentRestClientConfig config;

	public ContentRestClient(ContentRestClientConfig config) {
		this.config = config;
	}

	public abstract void getCalendarEvents(Callback callback);

	public abstract void getNewsArticles(Callback callback);

	public interface ContentRestClientConfig {

		String getCalendarEndpoint();

		String getRssEndpoint();

	}

}

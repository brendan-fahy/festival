package com.breadbin.festival.app;

import com.breadbin.festival.NavigationDrawerActivity;
import com.breadbin.festival.api.ContentRestClient;

public class HomeActivity extends NavigationDrawerActivity {
	@Override
	public ContentRestClient.ContentRestClientConfig getRestClientConfig() {
		return new ContentRestClient.ContentRestClientConfig() {
			@Override
			public String getCalendarEndpoint() {
				return getString(R.string.googleCalendarEndpoint);
			}

			@Override
			public String getRssEndpoint() {
				return getString(R.string.rssEndpoint);
			}
		};
	}
}

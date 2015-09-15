package com.breadbin.festival.app;

import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.view.NavigationDrawerActivity;

public class HomeActivity extends NavigationDrawerActivity {
	@Override
	public ContentRestClient.ContentRestClientConfig getRestClientConfig() {
		return new ContentRestClient.ContentRestClientConfig() {
      @Override
      public String getCalendarBaseUrl() {
        return getString(R.string.googleCalendarBaseUrl);
      }

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

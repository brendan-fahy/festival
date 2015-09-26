package com.breadbin.festival;

import android.support.annotation.VisibleForTesting;

import com.breadbin.festival.app.R;
import com.breadbin.festival.common.api.ContentRestClient;

public class HomeActivity extends AbsHomescreenActivity {
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

  @VisibleForTesting
  protected String getDummyString() {
    return "test String";
  }
}

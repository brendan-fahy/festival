package com.breadbin.festival;

import com.breadbin.festival.app.R;
import com.breadbin.festival.common.api.ContentRestClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

  private FestivalApplication app;

  public ApplicationModule(FestivalApplication app) {
    this.app = app;
  }

  @Provides @Singleton
  public ContentRestClient.ContentRestClientConfig provideRestClientConfig() {
    return new ContentRestClient.ContentRestClientConfig() {
      @Override
      public String getCalendarBaseUrl() {
        return app.getString(R.string.googleCalendarBaseUrl);
      }

      @Override
      public String getCalendarEndpoint() {
        return app.getString(R.string.googleCalendarEndpoint);
      }

      @Override
      public String getRssEndpoint() {
        return app.getString(R.string.rssEndpoint);
      }
    };
  }
}

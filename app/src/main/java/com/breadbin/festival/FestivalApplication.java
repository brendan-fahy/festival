package com.breadbin.festival;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

public class FestivalApplication extends Application {

  private ApplicationComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    JodaTimeAndroid.init(this);
  }

  protected ApplicationModule getApplicationModule() {
    return new ApplicationModule(this);
  }

  public static ApplicationComponent getAppComponent(Context context) {
    FestivalApplication app = (FestivalApplication) context.getApplicationContext();
    if (app.appComponent == null) {
      app.appComponent = DaggerApplicationComponent.builder()
          .applicationModule(app.getApplicationModule())
          .build();
    }
    return app.appComponent;
  }

  public static void clearAppComponent(Context context) {
    FestivalApplication app = (FestivalApplication)context.getApplicationContext();
    app.appComponent = null;
  }
}

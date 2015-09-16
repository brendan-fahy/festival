package com.breadbin.festival;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

public class FestivalApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    JodaTimeAndroid.init(this);
  }
}

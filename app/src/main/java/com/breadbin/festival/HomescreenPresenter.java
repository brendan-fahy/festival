package com.breadbin.festival;

import android.os.Bundle;

public interface HomescreenPresenter {

  void onCreate(Bundle savedInstanceState);

  void fetchNewsArticles();

  void fetchCalendarEvents();
}

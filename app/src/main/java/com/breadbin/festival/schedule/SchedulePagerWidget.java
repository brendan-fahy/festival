package com.breadbin.festival.schedule;

import android.content.Context;
import android.support.v4.view.PagerAdapter;

public interface SchedulePagerWidget {

  Context getContext();

  void setupViewPager(PagerAdapter adapter);

  void setupTabLayout(PagerAdapter adapter);

  void setupTitle();

}

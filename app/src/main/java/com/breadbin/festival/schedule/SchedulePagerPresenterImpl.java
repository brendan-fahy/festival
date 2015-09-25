package com.breadbin.festival.schedule;

import android.support.v4.view.PagerAdapter;

import com.breadbin.festival.schedule.model.Schedule;

public class SchedulePagerPresenterImpl implements SchedulePagerPresenter {

  private SchedulePagerView view;

  private Schedule schedule;

  public SchedulePagerPresenterImpl(SchedulePagerView view, Schedule schedule) {
    this.view = view;
    this.schedule = schedule;
  }

  public void onStart() {
    PagerAdapter adapter = new SchedulePagerAdapter(schedule);
    view.setupViewPager(adapter);
    view.setupTabLayout(adapter);
    view.setupTitle();
  }

}

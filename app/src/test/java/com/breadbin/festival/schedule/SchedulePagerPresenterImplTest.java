package com.breadbin.festival.schedule;

import com.breadbin.festival.schedule.model.Schedule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SchedulePagerPresenterImplTest {

  private SchedulePagerPresenterImpl presenter;

  private Schedule mockSchedule;

  private SchedulePagerView mockPagerView;

  @Before
  public void setUp() throws Exception {
    mockSchedule = mock(Schedule.class);
    mockPagerView = mock(SchedulePagerView.class);

    presenter = new SchedulePagerPresenterImpl(mockPagerView, mockSchedule);
  }

  @Test
  public void shouldSetupScheduleViewOnStart() {
    presenter.onStart();

    verify(mockPagerView).setupTabLayout(any(SchedulePagerAdapter.class));
    verify(mockPagerView).setupViewPager(any(SchedulePagerAdapter.class));
    verify(mockPagerView).setupTitle();
  }
}

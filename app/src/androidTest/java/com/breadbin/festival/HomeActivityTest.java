package com.breadbin.festival;

import android.test.ActivityInstrumentationTestCase2;

public class HomeActivityTest
    extends ActivityInstrumentationTestCase2<HomeActivity> {

  private HomeActivity homeActivity;

  public HomeActivityTest() {
    super(HomeActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    homeActivity = getActivity();
  }

  public void testActivityDummyTest() {
    assertEquals("test String", homeActivity.getDummyString());
  }

}


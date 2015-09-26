package com.breadbin.festival;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.breadbin.festival.common.api.ContentRestClient;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomescreenPresenterImplTest {

  private HomescreenPresenterImpl presenter;

  private HomescreenView mockView;

  @Before
  public void setUp() throws Exception {
    mockView = mock(HomescreenView.class);

    presenter = new HomescreenPresenterImpl(mockView, config);
  }

  @Test
  public void shouldRestoreExistingFragmentOnStart() {
    Fragment mockFragment = mock(Fragment.class);
    Bundle mockBundle = mock(Bundle.class);
    when(mockView.checkForExistingView(mockBundle)).thenReturn(mockFragment);

    presenter.onCreate(mockBundle);

    verify(mockView).setupNavigationDrawer();
    verify(mockView).checkForExistingView(mockBundle);
    verify(mockView).updateCurrentView(mockFragment);
  }

  private ContentRestClient.ContentRestClientConfig config = new ContentRestClient.ContentRestClientConfig() {
    @Override
    public String getCalendarBaseUrl() {
      return "http://www.google.com/calendar/feeds/";
    }

    @Override
    public String getCalendarEndpoint() {
      return "calendarEndpoint";
    }

    @Override
    public String getRssEndpoint() {
      return "rssEndpoint";
    }
  };
}
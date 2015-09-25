package com.breadbin.festival;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

public interface HomescreenView {

  Context getContext();

  void setupNavigationDrawer();

  Fragment checkForExistingView(Bundle savedInstanceState);

  void updateToolbarForNavDrawer(Toolbar toolbar, int titleStringId);

  void updateCurrentView(Fragment fragment);

  void showDefaultText();
}

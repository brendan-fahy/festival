package com.breadbin.festival;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {
  HomeActivity inject(HomeActivity activity);
  HomescreenPresenterImpl inject(HomescreenPresenterImpl presenter);
}

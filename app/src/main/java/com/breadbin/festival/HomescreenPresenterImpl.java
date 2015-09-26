package com.breadbin.festival;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.breadbin.festival.common.ContentModel;
import com.breadbin.festival.common.api.ContentRestClient;
import com.breadbin.festival.common.api.NoDataException;
import com.breadbin.festival.news.NewsFragment;
import com.breadbin.festival.news.model.Article;
import com.breadbin.festival.schedule.SchedulePagerFragment;
import com.breadbin.festival.schedule.model.Schedule;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HomescreenPresenterImpl implements HomescreenPresenter {

  private HomescreenView view;

  private ContentRestClient.ContentRestClientConfig restClientConfig;

  public HomescreenPresenterImpl(HomescreenView view,
                                 ContentRestClient.ContentRestClientConfig restClientConfig) {
    this.view = view;
    this.restClientConfig = restClientConfig;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    view.setupNavigationDrawer();
    Fragment fragment = view.checkForExistingView(savedInstanceState);
    if (fragment == null) {
      fetchNewsArticles();
    } else {
      view.updateCurrentView(fragment);
    }
  }

  @Override
  public void fetchNewsArticles() {
    ContentModel.getInstance(view.getContext(), restClientConfig).fetchNewsArticlesList()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<Article>>() {
          @Override
          public void call(List<Article> articles) {
            view.updateCurrentView(NewsFragment.newInstance(articles));
          }
        }, onErrorAction);
  }

  @Override
  public void fetchCalendarEvents() {
    ContentModel.getInstance(view.getContext(), restClientConfig).fetchEventsList()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Schedule>() {
          @Override
          public void call(Schedule schedule) {
            view.updateCurrentView(SchedulePagerFragment.newInstance(schedule));
          }
        }, onErrorAction);
  }

  private Action1<Throwable> onErrorAction = new Action1<Throwable>() {
    @Override
    public void call(Throwable e) {
      if (e instanceof NoDataException) {
        view.showDefaultText();
      }
    }
  };
}

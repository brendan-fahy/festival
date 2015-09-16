package com.breadbin.festival.common;

import android.content.Context;

import com.breadbin.festival.common.api.ContentRestClient;
import com.breadbin.festival.common.api.DefaultContentRestClient;
import com.breadbin.festival.news.model.Article;
import com.breadbin.festival.news.presenter.NewsPresenter;
import com.breadbin.festival.schedule.model.Event;
import com.breadbin.festival.schedule.model.Schedule;
import com.breadbin.festival.schedule.presenter.CalendarPresenter;
import com.breadbin.festival.schedule.presenter.ScheduleTransformer;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ContentPresenter {

	private static ContentPresenter instance;

	private Presenter<List<Event>> calendarPresenter;

	private Presenter<List<Article>> newsPresenter;

	// Singleton accessor method
	public static ContentPresenter getInstance(Context context, ContentRestClient.ContentRestClientConfig clientConfig) {
		if (instance == null) {
			instance = new ContentPresenter(context, clientConfig);
		}
		return instance;
	}

	// Private constructor
	private ContentPresenter(Context context, ContentRestClient.ContentRestClientConfig clientConfig) {
		ContentRestClient restClient = new DefaultContentRestClient(clientConfig);
		this.calendarPresenter = new CalendarPresenter(context, restClient);
		this.newsPresenter = new NewsPresenter(context, restClient);
	}

  public Observable<Schedule> fetchEventsList() {
    return calendarPresenter.getObservable()
        .map(new Func1<List<Event>, Schedule>() {
          @Override
          public Schedule call(List<Event> events) {
            return ScheduleTransformer.getOrderedSchedule(events);
          }
        })
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread());
  }

	public Observable<List<Article>> fetchNewsArticlesList() {
    return newsPresenter.getObservable();
	}

}

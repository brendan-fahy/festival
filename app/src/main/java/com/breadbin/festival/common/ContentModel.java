package com.breadbin.festival.common;

import android.content.Context;

import com.breadbin.festival.common.api.ContentRestClient;
import com.breadbin.festival.common.api.DefaultContentRestClient;
import com.breadbin.festival.news.model.Article;
import com.breadbin.festival.news.model.NewsModel;
import com.breadbin.festival.schedule.model.CalendarModel;
import com.breadbin.festival.schedule.model.Event;
import com.breadbin.festival.schedule.model.Schedule;
import com.breadbin.festival.schedule.model.ScheduleTransformer;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class ContentModel {

	private static ContentModel instance;

	private Model<List<Event>> calendarModel;

	private Model<List<Article>> newsModel;

	// Singleton accessor method
	public static ContentModel getInstance(Context context, ContentRestClient.ContentRestClientConfig clientConfig) {
		if (instance == null) {
			instance = new ContentModel(context, clientConfig);
		}
		return instance;
	}

	// Private constructor
	private ContentModel(Context context, ContentRestClient.ContentRestClientConfig clientConfig) {
		ContentRestClient restClient = new DefaultContentRestClient(clientConfig);
		this.calendarModel = new CalendarModel(context, restClient);
		this.newsModel = new NewsModel(context, restClient);
	}

  public Observable<Schedule> fetchEventsList() {
    return calendarModel.getObservable()
        .map(new Func1<List<Event>, Schedule>() {
          @Override
          public Schedule call(List<Event> events) {
            return ScheduleTransformer.getOrderedSchedule(events);
          }
        });
  }

	public Observable<List<Article>> fetchNewsArticlesList() {
    return newsModel.getObservable();
	}

}

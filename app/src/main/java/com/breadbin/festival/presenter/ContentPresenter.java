package com.breadbin.festival.presenter;

import android.content.Context;

import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.api.DefaultContentRestClient;
import com.breadbin.festival.presenter.calendar.CalendarPresenter;
import com.breadbin.festival.presenter.news.NewsPresenter;

public class ContentPresenter {

	private static ContentPresenter instance;

	private Presenter calendarPresenter;

	private Presenter newsPresenter;

	// Singleton accessor method
	public static ContentPresenter getInstance(Context context, ContentRestClient.ContentRestClientConfig clientConfig) {
		if (instance == null) {
			instance = new ContentPresenter(context, clientConfig);
		}
		return instance;
	}

	// Private constructor
	private ContentPresenter(Context context, ContentRestClient.ContentRestClientConfig clientConfig) {
		ContentRestClient restClient = new DefaultContentRestClient(context, clientConfig);
		this.calendarPresenter = new CalendarPresenter(context, restClient);
		this.newsPresenter = new NewsPresenter(context, restClient);
	}

	/**
	 * Public method for asynchronous retrieval of List of Event objects. Will read from storage, post results, then read from network, and post results.
 	 */
	public void fetchEventsList() {
		calendarPresenter.getFromStorage();
		calendarPresenter.getFromNetwork();
	}

	/**
	 * Public method for asynchronous retrieval of List of Article objects. Will read from storage, post results, then read from network, and post results.
	 */
	public void fetchNewsArticlesList() {
		newsPresenter.getFromStorage();
		newsPresenter.getFromNetwork();
	}

}

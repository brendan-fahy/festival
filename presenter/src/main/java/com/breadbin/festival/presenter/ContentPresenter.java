package com.breadbin.festival.presenter;

import android.content.Context;

import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.api.DefaultContentRestClient;
import com.breadbin.festival.api.googlecalendar.CalendarCallback;
import com.breadbin.festival.api.rss.ArticleCallback;
import com.breadbin.festival.presenter.busevents.ArticlesListRetrievedEvent;
import com.breadbin.festival.presenter.busevents.ScheduleRetrievedEvent;
import com.breadbin.festival.presenter.busevents.ScheduleUpdatedEvent;
import com.breadbin.festival.presenter.storage.ArticlesStorage;
import com.breadbin.festival.presenter.storage.EventsStorage;
import com.model.error.ErrorResponse;
import com.model.events.Event;
import com.model.news.Article;

import java.util.List;

import de.greenrobot.event.EventBus;

public class ContentPresenter {

	private Context context;

	private ContentRestClient restClient;

	private static ContentPresenter instance;

	// Singleton accessor method
	public static ContentPresenter getInstance(Context context, ContentRestClient.ContentRestClientConfig clientConfig) {
		if (instance == null) {
			instance = new ContentPresenter(context, clientConfig);
		}
		return instance;
	}

	// Private constructor
	private ContentPresenter(Context context, ContentRestClient.ContentRestClientConfig clientConfig) {
		this.context = context;
		this.restClient = new DefaultContentRestClient(context, clientConfig);
	}

	/**
	 * Public method for asynchronous retrieval of List of Event objects. Will read from storage, post results, then read from network, and post results.
 	 */
	public void fetchEventsList() {
		checkStorageForEventsList();
		checkNetworkForEventsList();
	}

	/**
	 * Public method for asynchronous retrieval of List of Article objects. Will read from storage, post results, then read from network, and post results.
	 */
	public void fetchNewsArticlesList() {
		checkStorageForNewsArticlesList();
		checkNetworkForNewsArticlesList();
	}

	private void checkStorageForEventsList() {
		List<Event> eventList = EventsStorage.getInstance(context).readEvents();
		if (eventList != null && !eventList.isEmpty()) {
			postEventsListDeliveredEvent(eventList);
		}
	}

	private void checkStorageForNewsArticlesList() {
		List<Article> articleList = ArticlesStorage.getInstance(context).readArticles();
		if (articleList != null && !articleList.isEmpty()) {
			postArticlesListDeliveredEvent(articleList);
		}
	}

	private void checkNetworkForEventsList() {
		restClient.getCalendarEvents(calendarCallback);
	}

	private CalendarCallback calendarCallback = new CalendarCallback() {
		@Override
		public void onSuccess(List<Event> eventList) {
			onCalendarSuccess(eventList);
		}

		@Override
		public void onFailure(ErrorResponse errorResponse) {
			// TODO
		}

		@Override
		public void onFinish() {
			// TODO
		}
	};

	protected void onCalendarSuccess(List<Event> retrievedEvents) {
		EventsStorage storage = EventsStorage.getInstance(context);
		if (storage.readEvents() == null) {
			postEventsListDeliveredEvent(retrievedEvents);
		} else if (EventsListUtils.eventsHaveChanged(storage.readEvents(), retrievedEvents)) {
			postEventsListUpdatedEvent(retrievedEvents);
		}
		storage.saveEvents(retrievedEvents);
	}

	private void postEventsListDeliveredEvent(List<Event> events) {
		EventBus.getDefault().post(new ScheduleRetrievedEvent(ScheduleTransformer.getOrderedSchedule(events)));
	}

	private void postEventsListUpdatedEvent(List<Event> events) {
		EventBus.getDefault().post(new ScheduleUpdatedEvent(ScheduleTransformer.getOrderedSchedule(events)));
	}

	private void checkNetworkForNewsArticlesList() {
		restClient.getNewsArticles(articlesCallback);
	}

	private ArticleCallback articlesCallback = new ArticleCallback() {
		@Override
		public void onSuccess(List<Article> articleList) {
			onRssSuccess(articleList);
		}

		@Override
		public void onFailure(ErrorResponse errorResponse) {
			// TODO
		}

		@Override
		public void onFinish() {
			// TODO
		}
	};

	private void onRssSuccess(List<Article> retrievedArticles) {
		ArticlesStorage.getInstance(context).saveArticles(retrievedArticles);
		postArticlesListDeliveredEvent(retrievedArticles);
	}

	private void postArticlesListDeliveredEvent(List<Article> retrievedArticles) {
		EventBus.getDefault().post(new ArticlesListRetrievedEvent(retrievedArticles));
	}

}

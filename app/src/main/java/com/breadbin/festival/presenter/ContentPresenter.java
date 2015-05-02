package com.breadbin.festival.presenter;

import android.content.Context;

import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.api.DefaultContentRestClient;
import com.breadbin.festival.api.googlecalendar.CalendarCallback;
import com.breadbin.festival.busevents.EventsListRetrievedEvent;
import com.breadbin.festival.busevents.EventsListUpdatedEvent;
import com.breadbin.festival.storage.EventsStorage;
import com.model.error.ErrorResponse;
import com.model.events.Event;

import java.util.List;

import de.greenrobot.event.EventBus;

public class ContentPresenter {

	private Context context;

	private ContentRestClient restClient;

	private static ContentPresenter instance;

	// Singleton accessor method
	public static ContentPresenter getInstance(Context context) {
		if (instance == null) {
			instance = new ContentPresenter(context);
		}
		return instance;
	}

	// Private constructor
	private ContentPresenter(Context context) {
		this.context = context;
		this.restClient = new DefaultContentRestClient(context, restClientConfig);
	}

	/**
	 * Public method for asynchronous retrieval of List of Event objects. Will read from storage, post results, then read from network, and post results.
 	 */
	public void fetchEventsList() {
		checkStorageForEventsList();
		checkNetworkForEventsList();
	}

	private void checkNetworkForEventsList() {
		restClient.getCalendarEvents(calendarCallback);
	}

	private void checkStorageForEventsList() {
		List<Event> eventList = EventsStorage.getInstance(context).readEvents();
		if (eventList != null && !eventList.isEmpty()) {
			postEventsListDeliveredEvent(eventList);
		}
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
		EventBus.getDefault().post(new EventsListRetrievedEvent(events));
	}

	private void postEventsListUpdatedEvent(List<Event> events) {
		EventBus.getDefault().post(new EventsListUpdatedEvent(events));
	}

	private ContentRestClient.ContentRestClientConfig restClientConfig = new ContentRestClient.ContentRestClientConfig() {
		@Override
		public String getCalendarEndpoint() {
			return googleCalendarEndpoint;
		}

		@Override
		public String getRssEndpoint() {
			return rssEndpoint;
		}
	};

	//TODO EXTRACT FOR BETTER CONFIGURABILITY
	final String googleCalendarEndpoint = "http://www.google.com/calendar/feeds/volunteers@ejc2014.org/public/basic?alt=jsonc&max-results=2000";
	final String rssEndpoint = "http://www.ejc2014.org/?option=com_content&view=category&layout=blog&id=43&format=fe%20ed&type=rss&utm_campaign=apps&utm_medium=android&utm_source=rss_feed";
}

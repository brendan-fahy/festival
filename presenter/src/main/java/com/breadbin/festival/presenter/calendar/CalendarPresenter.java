package com.breadbin.festival.presenter.calendar;

import android.content.Context;

import com.breadbin.festival.api.Callback;
import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.presenter.Presenter;
import com.breadbin.festival.presenter.busevents.ScheduleRetrievedEvent;
import com.breadbin.festival.presenter.busevents.ScheduleUpdatedEvent;
import com.breadbin.festival.presenter.storage.EventsStorage;
import com.model.error.ErrorResponse;
import com.model.events.Event;

import java.util.List;

import de.greenrobot.event.EventBus;

public class CalendarPresenter extends Presenter<List<Event>> {

	public CalendarPresenter(Context context, ContentRestClient restClient) {
		super(context, restClient);
	}

	@Override
	public void getFromStorage() {
		List<Event> eventList = EventsStorage.getInstance(context).readEvents();
		if (eventList != null && !eventList.isEmpty()) {
			postDeliveredEvent(eventList);
		}
	}

	@Override
	protected void requestFromNetwork() {
		restClient.getCalendarEvents(calendarCallback);
	}

	private Callback calendarCallback = new Callback<List<Event>>() {
		@Override
		public void onSuccess(List<Event> eventList) {
			handleSuccessResponse(eventList);
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

	@Override
	public void postDeliveredEvent(List<Event> eventList) {
		EventBus.getDefault().post(new ScheduleRetrievedEvent(ScheduleTransformer.getOrderedSchedule(eventList)));
	}

	@Override
	public void postUpdatedEvent(List<Event> eventList) {
		EventBus.getDefault().post(new ScheduleUpdatedEvent(ScheduleTransformer.getOrderedSchedule(eventList)));
	}

	public void handleSuccessResponse(List<Event> eventList) {
		EventsStorage storage = EventsStorage.getInstance(context);
		if (storage.readEvents() == null) {
			postDeliveredEvent(eventList);
		} else if (EventsListUtils.eventsHaveChanged(storage.readEvents(), eventList)) {
			postUpdatedEvent(eventList);
		}
		storage.saveEvents(eventList);
	}
}

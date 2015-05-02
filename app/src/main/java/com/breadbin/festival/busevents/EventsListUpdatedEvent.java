package com.breadbin.festival.busevents;

import com.model.events.Event;

import java.util.List;

public class EventsListUpdatedEvent {

	private final List<Event> eventList;

	public EventsListUpdatedEvent(List<Event> eventList) {
		this.eventList = eventList;
	}

	public List<Event> getEventList() {
		return eventList;
	}
}

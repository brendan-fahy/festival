package com.breadbin.festival.busevents;

import com.model.events.Event;

import java.util.List;

public class EventsListRetrievedEvent {

	private final List<Event> events;

	public EventsListRetrievedEvent(List<Event> events) {
		this.events = events;
	}

	public List<Event> getEvents() {
		return events;
	}
}

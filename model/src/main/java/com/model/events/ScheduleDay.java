package com.model.events;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDay implements Serializable, Comparable<ScheduleDay> {

	private DateTime date;

	private List<Event> eventList;

	public void addEvent(Event event) {
		if (eventList == null) {
			eventList = new ArrayList<>();
		}
		eventList.add(event);
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	@Override
	public int compareTo(ScheduleDay another) {
		return (date.isBefore(another.date)) ? -1 : 1;
	}
}

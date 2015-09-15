package com.breadbin.festival.presenter.busevents;

import com.breadbin.festival.model.events.Schedule;

public class ScheduleRetrievedEvent {

	private final Schedule schedule;

	public ScheduleRetrievedEvent(Schedule schedule) {
		this.schedule = schedule;
	}

	public Schedule getSchedule() {
		return schedule;
	}
}

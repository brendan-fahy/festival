package com.breadbin.festival.presenter.busevents;

import com.breadbin.festival.model.events.Schedule;

public class ScheduleUpdatedEvent {

	private final Schedule schedule;

	public ScheduleUpdatedEvent(Schedule schedule) {
		this.schedule = schedule;
	}

	public Schedule getSchedule() {
		return schedule;
	}
}

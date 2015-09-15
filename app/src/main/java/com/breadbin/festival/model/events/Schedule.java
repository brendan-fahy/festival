package com.breadbin.festival.model.events;

import java.io.Serializable;
import java.util.List;

public class Schedule implements Serializable {

	private List<ScheduleDay> days;

	public List<ScheduleDay> getDays() {
		return days;
	}

	public void setDays(List<ScheduleDay> days) {
		this.days = days;
	}
}

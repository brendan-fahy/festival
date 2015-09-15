package com.breadbin.festival.presenter.calendar;

import com.breadbin.festival.model.events.Event;
import com.breadbin.festival.model.events.Schedule;
import com.breadbin.festival.model.events.ScheduleDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScheduleTransformer {

	public static Schedule getOrderedSchedule(List<Event> allEvents) {
		Schedule schedule = new Schedule();

		schedule.setDays(getScheduleDays(allEvents));
		sortSchedule(schedule);

		return schedule;
	}

	protected static List<ScheduleDay> getScheduleDays(List<Event> allEvents) {
		List<ScheduleDay> scheduleDays = new ArrayList<>();

		for(Event event: allEvents) {
			ScheduleDay day = getScheduleDayForEvent(scheduleDays, event);
			day.addEvent(event);
		}

		return scheduleDays;
	}

	protected static ScheduleDay getScheduleDayForEvent(List<ScheduleDay> scheduleDays, Event event) {
		ScheduleDay day = getScheduleDayFromList(scheduleDays, event);
		if (day == null) {
			day = new ScheduleDay();
			day.setDate(event.getTime());
			scheduleDays.add(day);
		}
		return day;
	}

	protected static ScheduleDay getScheduleDayFromList(List<ScheduleDay> scheduleDays, Event event) {
		for (ScheduleDay scheduleDay: scheduleDays) {
			if (scheduleDay.getDate().getDayOfYear() == event.getTime().getDayOfYear()) {
				return scheduleDay;
			}
		}
		return null;
	}

	protected static void sortSchedule(Schedule schedule) {
		for (ScheduleDay day: schedule.getDays()) {
			Collections.sort(day.getEventList());
		}
		Collections.sort(schedule.getDays());
	}
}

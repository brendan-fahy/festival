package com.breadbin.festival.presenter;

import com.breadbin.festival.presenter.calendar.EventsListUtils;
import com.model.events.Event;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class EventsListUtilsTest {

	@Test
	public void shouldReturnFalseIfNoEventsChanged() {
		assertFalse(EventsListUtils.eventsHaveChanged(getEventsList(3), getEventsList(3)));
	}

	@Test
	public void shouldReturnTrueIfNewEventsAdded() {
		assertTrue(EventsListUtils.eventsHaveChanged(getEventsList(3), getEventsList(4)));
	}

	@Test
	public void shouldReturnTrueIfEventRemoved() {
		assertTrue(EventsListUtils.eventsHaveChanged(getEventsList(4), getEventsList(3)));
	}

	@Test
	public void shouldReturnTrueIfEventChanged() {
		List<Event> eventList = getEventsList(3);
		List<Event> changedEventList = getEventsList(3);
		changedEventList.get(0).setTitle("Different title");

		assertTrue(EventsListUtils.eventsHaveChanged(eventList, changedEventList));
	}

	public List<Event> getEventsList(int numberOfEvents) {
		List<Event> events = new ArrayList<>();
		for (int i = 0; i < numberOfEvents; i++) {
			Event event = new Event();
			event.setTitle("Title " + i);
			event.setDescription("Description " + i);
			event.setTeacher("Teacher " + i);
			event.setLocation("Location " + i);
			event.setTime(DateTime.now());
			events.add(event);
		}
		return events;
	}
}
package com.breadbin.festival.schedule.presenter;

import android.util.Log;

import com.breadbin.festival.schedule.model.Event;

import java.util.List;

public class EventsListUtils {

	public static boolean eventsHaveChanged(List<Event> oldEvents, List<Event> newEvents) {
    if (oldEvents == null) {
      return true;
    }
		if (oldEvents.size() != newEvents.size()) {
			Log.d("EventsListFragment", "Events have changed. Stored events: " + oldEvents.size() + ", retrievedEvents: " + newEvents.size());
			return true;
		}

		for (Event retrievedEvent: newEvents) {
			boolean matched = false;
			for (Event storedEvent: oldEvents) {
				if (retrievedEvent.equals(storedEvent)) {
					matched = true;
				}
			}
			if (!matched) {
				Log.d("EventListFragment", "Events have changed. Retrieved event: \"" + retrievedEvent.getTitle() + "\" was not matched with a stored event.");
				return true;
			}
		}
		Log.d("EventListFragment", "Events have not changed. No need to redraw list.");
		return false;
	}
}

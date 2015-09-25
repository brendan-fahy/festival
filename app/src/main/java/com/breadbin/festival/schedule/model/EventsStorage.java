package com.breadbin.festival.schedule.model;

import android.content.Context;

import com.breadbin.festival.common.storage.CachedObject;
import com.breadbin.festival.common.storage.ObjectCacher;

import java.util.ArrayList;
import java.util.List;

public class EventsStorage {

	public static final String EVENTS_STORAGE_NAME = "events_storage";

	private static EventsStorage instance;

  private ObjectCacher<List<Event>> eventsCacher;

	public static EventsStorage getInstance(Context context) {
		if (instance == null) {
			instance = new EventsStorage(context);
		}
		return instance;
	}

	public EventsStorage(Context context) {
    eventsCacher = new ObjectCacher<>(context.getApplicationContext().getCacheDir(),
        EVENTS_STORAGE_NAME);
	}

	public boolean saveEvents(List<Event> events) {
    return eventsCacher.save(events);
	}

	public List<Event> readEvents() {
    CachedObject<List<Event>> cachedEvents = eventsCacher.get(CachedEvents.class);
    if (cachedEvents == null || cachedEvents.get() == null) {
      return new ArrayList<>(0);
    }
		return cachedEvents.get();
	}
}

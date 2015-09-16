package com.breadbin.festival.schedule.model;

import android.content.Context;

import com.breadbin.festival.common.InternalStorageAdapter;

import java.util.List;

public class EventsStorage extends InternalStorageAdapter<List<Event>> {

	public static final String EVENTS_STORAGE_NAME = "events_storage";
	public static final String EVENTS_KEY = "events";

	private static EventsStorage instance;

	public static EventsStorage getInstance(Context context) {
		if (instance == null || instance.isClosed()) {
			instance = new EventsStorage(context);
		}
		return instance;
	}

	public EventsStorage(Context context) {
		super(context, EVENTS_STORAGE_NAME);
	}

	public boolean saveEvents(List<Event> events) {
		return super.save(EVENTS_KEY, events);
	}

	public List<Event> readEvents() {
		return super.find(EVENTS_KEY);
	}
}

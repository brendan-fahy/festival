package com.breadbin.festival.schedule.model;

import android.content.Context;

import com.breadbin.festival.common.storage.ObjectCacher;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

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

	public CachedEvents saveEvents(List<Event> events) {
    return new CachedEvents(eventsCacher.save(events));
	}

	public Observable<CachedEvents> readEvents() {
    return Observable.create(new Observable.OnSubscribe<CachedEvents>() {
      @Override
      public void call(Subscriber<? super CachedEvents> subscriber) {
        CachedEvents cachedEvents = (CachedEvents) eventsCacher.get(CachedEvents.class);
        subscriber.onNext(cachedEvents);
        subscriber.onCompleted();
      }
    });
	}
}

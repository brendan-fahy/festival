package com.breadbin.festival.schedule.model;

import android.content.Context;

import com.breadbin.festival.common.Model;
import com.breadbin.festival.common.api.ContentRestClient;
import com.breadbin.festival.schedule.model.api.CalendarConverter;
import com.breadbin.festival.schedule.model.api.CalendarResponse;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class CalendarModel extends Model<List<Event>> {

	public CalendarModel(Context context, ContentRestClient restClient) {
		super(context, restClient);
	}

  @Override
  public Observable<List<Event>> getObservable() {
    final EventsStorage storage = EventsStorage.getInstance(context);

    Observable<CachedEvents> storageData = storage.readEvents();

    Observable<CachedEvents> networkWithSave = restClient.getCalendarEvents()
        .map(new Func1<CalendarResponse, CachedEvents>() {
          @Override
          public CachedEvents call(CalendarResponse calendarResponse) {
            List<Event> eventList = CalendarConverter.convertToEvents(calendarResponse.getData());
            return storage.saveEvents(eventList);
          }
        });

    return Observable
        .concat(storageData, networkWithSave)
        .first(new Func1<CachedEvents, Boolean>() {
          @Override
          public Boolean call(CachedEvents events) {
            return events != null && isUpToDate(events.getCacheStatus().getLastRefreshTime());
          }
        })
        .map(new Func1<CachedEvents, List<Event>>() {
          @Override
          public List<Event> call(CachedEvents cachedEvents) {
            return cachedEvents.get();
          }
        });
  }
}

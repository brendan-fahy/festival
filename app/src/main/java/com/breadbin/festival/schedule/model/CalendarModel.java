package com.breadbin.festival.schedule.model;

import android.content.Context;

import com.breadbin.festival.common.Model;
import com.breadbin.festival.common.api.ContentRestClient;
import com.breadbin.festival.common.api.NoDataException;
import com.breadbin.festival.schedule.model.api.CalendarConverter;
import com.breadbin.festival.schedule.model.api.CalendarResponse;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class CalendarModel extends Model<List<Event>> {

	public CalendarModel(Context context, ContentRestClient restClient) {
		super(context, restClient);
	}

  @Override
  public Observable<List<Event>> getObservable() {
    return Observable
        .create(new Observable.OnSubscribe<List<Event>>() {
          @Override
          public void call(final Subscriber<? super List<Event>> subscriber) {
            final EventsStorage storage = EventsStorage.getInstance(context);

            List<Event> eventList = storage.readEvents();
            if (eventList != null && !eventList.isEmpty()) {
              subscriber.onNext(eventList);
            } else if (!isConnectedOrConnecting()) {
              subscriber.onError(new NoDataException());
            }

            restClient.getCalendarEvents().subscribe(new Subscriber<CalendarResponse>() {
              @Override
              public void onCompleted() {

              }

              @Override
              public void onError(Throwable e) {
                subscriber.onError(e);
              }

              @Override
              public void onNext(CalendarResponse calendarResponse) {
                List<Event> eventList = CalendarConverter.convertToEvents(calendarResponse.getData());
                if (EventsListUtils.eventsHaveChanged(storage.readEvents(), eventList)) {
                  storage.saveEvents(eventList);
                  subscriber.onNext(eventList);
                }
              }
            });
          }
        });
  }
}

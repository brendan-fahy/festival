package com.breadbin.festival.presenter.calendar;

import android.content.Context;

import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.api.NoDataException;
import com.breadbin.festival.api.googlecalendar.CalendarConverter;
import com.breadbin.festival.model.events.Event;
import com.breadbin.festival.model.googlecalendarapi.CalendarResponse;
import com.breadbin.festival.presenter.Presenter;
import com.breadbin.festival.presenter.storage.EventsStorage;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CalendarPresenter extends Presenter<List<Event>> {

	public CalendarPresenter(Context context, ContentRestClient restClient) {
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
            subscriber.onCompleted();
          }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }
}

package com.breadbin.festival.schedule.model.api;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

public class GoogleCalendarConnector {

  private GoogleCalendarService calendarService;

	public GoogleCalendarConnector(String baseUrl) {
    Retrofit retrofit = new Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    calendarService = retrofit.create(GoogleCalendarService.class);
	}

	public Observable<CalendarResponse> getCalendarEvents(String endpoint) {
    return calendarService.getCalendarData(endpoint);
	}

}

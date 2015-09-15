package com.breadbin.festival.api.googlecalendar;

import com.breadbin.festival.model.googlecalendarapi.CalendarResponse;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface GoogleCalendarService {

  @GET("{path}")
  Observable<CalendarResponse> getCalendarData(@Path("path") String path);

}

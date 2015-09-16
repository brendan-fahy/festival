package com.breadbin.festival.schedule.model.api;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface GoogleCalendarService {

  @GET("{path}/public/basic?alt=jsonc&max-results=2000")
  Observable<CalendarResponse> getCalendarData(@Path("path") String path);

}

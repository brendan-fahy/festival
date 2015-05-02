package com.breadbin.festival.api.googlecalendar;

import com.model.error.ErrorResponse;
import com.model.events.Event;

import java.util.List;

public interface CalendarCallback {

	void onSuccess(List<Event> calendarResponse);

	void onFailure(ErrorResponse errorResponse);

	void onFinish();

}

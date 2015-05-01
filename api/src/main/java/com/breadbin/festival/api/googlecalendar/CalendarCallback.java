package com.breadbin.festival.api.googlecalendar;

import com.model.error.ErrorResponse;
import com.model.googlecalendarapi.CalendarResponse;

public interface CalendarCallback {

	void onSuccess(CalendarResponse calendarResponse);

	void onFailure(ErrorResponse errorResponse);

	void onFinish();

}

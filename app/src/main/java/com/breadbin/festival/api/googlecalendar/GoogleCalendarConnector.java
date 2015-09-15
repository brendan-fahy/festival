package com.breadbin.festival.api.googlecalendar;

import android.content.Context;
import android.net.Uri;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.breadbin.festival.api.Callback;
import com.breadbin.festival.model.error.ErrorResponse;
import com.breadbin.festival.model.events.Event;
import com.breadbin.festival.model.googlecalendarapi.CalendarResponse;

import java.util.List;

public class GoogleCalendarConnector {

	private RequestQueue requestQueue;

	private final String endpoint;

	public GoogleCalendarConnector(Context context, String endpoint) {
		this.requestQueue = Volley.newRequestQueue(context);
		this.endpoint = endpoint;
	}

	public void getCalendarEvents(Callback callback) {
		requestQueue.add(getCalendarGsonRequest(callback));
	}

	private GsonRequest<CalendarResponse> getCalendarGsonRequest(Callback callback) {
		return new GsonRequest.Builder()
				.withMethod(Request.Method.GET)
				.withUri(Uri.parse(endpoint))
				.withResponseClass(CalendarResponse.class)
				.withResponseListener(getResponseListenerFromCallback(callback))
				.withErrorResponseListener(getErrorResponseListenerFromCallback(callback))
				.withRetryPolicy(new DefaultRetryPolicy(60000, 5, 1))
				.build();
	}

	private Response.Listener<CalendarResponse> getResponseListenerFromCallback(final Callback callback) {
		return new Response.Listener<CalendarResponse>() {
			@Override
			public void onResponse(CalendarResponse calendarResponse) {
				List<Event> eventList = CalendarConverter.convertToEvents(calendarResponse.getData());
				callback.onSuccess(eventList);
				callback.onFinish();
			}
		};
	}

	private Response.ErrorListener getErrorResponseListenerFromCallback(final Callback callback) {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				callback.onFailure(getErrorResponseFromVolleyError(volleyError));
				callback.onFinish();
			}
		};
	}

	private ErrorResponse getErrorResponseFromVolleyError(VolleyError volleyError) {
		return new ErrorResponse(
				((volleyError.networkResponse != null) ? volleyError.networkResponse.statusCode : -1),
				volleyError.getMessage()
		);
	}
}

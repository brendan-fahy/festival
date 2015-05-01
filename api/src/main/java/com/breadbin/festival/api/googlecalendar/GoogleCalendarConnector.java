package com.breadbin.festival.api.googlecalendar;

import android.content.Context;
import android.net.Uri;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.model.error.ErrorResponse;
import com.model.googlecalendarapi.CalendarResponse;

public class GoogleCalendarConnector {

	private RequestQueue requestQueue;

	private final String endpoint;

	public GoogleCalendarConnector(Context context, String endpoint) {
		this.requestQueue = Volley.newRequestQueue(context);
		this.endpoint = endpoint;
	}

	public void getCalendarEvents(CalendarCallback callback) {
		requestQueue.add(getCalendarGsonRequest(callback));
	}

	private GsonRequest<CalendarResponse> getCalendarGsonRequest(CalendarCallback callback) {
		return new GsonRequest.Builder()
				.withMethod(Request.Method.GET)
				.withUri(Uri.parse(endpoint))
				.withResponseClass(CalendarResponse.class)
				.withResponseListener(getResponseListenerFromCallback(callback))
				.withErrorResponseListener(getErrorResponseListenerFromCallback(callback))
				.withRetryPolicy(new DefaultRetryPolicy(60000, 5, 1))
				.build();
	}

	private Response.Listener<CalendarResponse> getResponseListenerFromCallback(final CalendarCallback callback) {
		return new Response.Listener<CalendarResponse>() {
			@Override
			public void onResponse(CalendarResponse calendarResponse) {
				callback.onSuccess(calendarResponse);
				callback.onFinish();
			}
		};
	}

	private Response.ErrorListener getErrorResponseListenerFromCallback(final CalendarCallback callback) {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				callback.onFailure(getErrorResponseFromVolleyError(volleyError));
				callback.onFinish();
			}
		};
	}

	private ErrorResponse getErrorResponseFromVolleyError(VolleyError volleyError) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(volleyError.networkResponse.statusCode);
		errorResponse.setMessage(volleyError.getMessage());
		return errorResponse;
	}
}

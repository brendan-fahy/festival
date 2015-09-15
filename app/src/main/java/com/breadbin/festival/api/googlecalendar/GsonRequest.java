package com.breadbin.festival.api.googlecalendar;

import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {
	private static final String APPLICATION_JSON_CONTENT_TYPE = "application/json";
	private final Gson gson;
	private final Class<T> responseClazz;
	private final Map<String, String> headers;
	private final Response.Listener<T> listener;
	private Object body;
	private Type bodyType;

	private GsonRequest(int method, Uri uri, Class<T> responseClazz, Object body, Type bodyType, Map<String, String> headers,
											Response.Listener<T> listener, Response.ErrorListener errorListener, RetryPolicy retryPolicy) {
		super(method, uri.toString(), errorListener);
		this.responseClazz = responseClazz;
		this.headers = headers;
		this.listener = listener;
		this.gson = initGson();
		this.body = body;
		this.bodyType = bodyType;
		this.setRetryPolicy(retryPolicy);
	}

	private Gson initGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeTypeConverter());
		return gsonBuilder.create();
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers != null ? headers : super.getHeaders();
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(
					response.data,
					HttpHeaderParser.parseCharset(response.headers));
			return Response.success(
					gson.fromJson(json, responseClazz),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		return gson.toJson(body, bodyType).getBytes();
	}

	@Override
	public String getBodyContentType() {
		return APPLICATION_JSON_CONTENT_TYPE;
	}

	public static class Builder {

		private int method;
		private Uri uri;
		private Class responseClass;
		private Map<String, String> headers;
		private Object body;
		private Type bodyType;
		private Response.Listener listener;
		private Response.ErrorListener errorResponseListener;
		private RetryPolicy retryPolicy;

		public Builder withMethod(int method) {
			this.method = method;
			return this;
		}

		public Builder withUri(Uri uri) {
			this.uri = uri;
			return this;
		}

		public Builder withResponseClass(Class responseClass) {
			this.responseClass = responseClass;
			return this;
		}

		public Builder withBody(Object body) {
			this.body = body;
			return this;
		}

		public Builder withBodyType(Type bodyType) {
			this.bodyType = bodyType;
			return this;
		}

		public Builder withHeaders(Map<String, String> headers) {
			this.headers = headers;
			return this;
		}

		public Builder withResponseListener(Response.Listener responseListener) {
			this.listener = responseListener;
			return this;
		}

		public Builder withErrorResponseListener(Response.ErrorListener errorResponseListener) {
			this.errorResponseListener = errorResponseListener;
			return this;
		}

		public Builder withRetryPolicy(RetryPolicy retryPolicy) {
			this.retryPolicy = retryPolicy;
			return this;
		}

		public <T> GsonRequest<T> build() {
			return new GsonRequest<T>(method, uri, responseClass, body, bodyType, headers, listener, errorResponseListener, retryPolicy);
		}

	}
}

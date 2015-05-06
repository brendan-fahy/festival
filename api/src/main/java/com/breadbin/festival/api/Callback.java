package com.breadbin.festival.api;

import com.model.error.ErrorResponse;

public interface Callback<T> {

	void onSuccess(T t);

	void onFailure(ErrorResponse errorResponse);

	void onFinish();
}

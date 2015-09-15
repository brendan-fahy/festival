package com.breadbin.festival.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.breadbin.festival.api.ContentRestClient;

import rx.Observable;

public abstract class Presenter<T> {

	protected Context context;

	protected ContentRestClient restClient;

	public Presenter(Context context, ContentRestClient restClient) {
		this.context = context;
		this.restClient = restClient;
	}

  public abstract Observable<T> getObservable();

	protected boolean isConnectedOrConnecting() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnectedOrConnecting());
	}

}

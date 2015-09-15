package com.breadbin.festival.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.presenter.busevents.OfflineEvent;

import de.greenrobot.event.EventBus;

public abstract class Presenter<T> {

	protected Context context;

	protected ContentRestClient restClient;

	public Presenter(Context context, ContentRestClient restClient) {
		this.context = context;
		this.restClient = restClient;
	}

	public abstract void getFromStorage();

	public final void getFromNetwork() {
		if (!isConnectedOrConnecting()) {
			postOffLineEvent();
		} else {
			requestFromNetwork();
		}
	}

	protected abstract void requestFromNetwork();

	public abstract void postDeliveredEvent(T t);

	public abstract void postUpdatedEvent(T t);

	private boolean isConnectedOrConnecting() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnectedOrConnecting());
	}

	private void postOffLineEvent() {
		EventBus.getDefault().post(new OfflineEvent());
	}

}

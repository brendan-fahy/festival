package com.breadbin.festival.presenter.news;

import android.content.Context;

import com.breadbin.festival.api.Callback;
import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.presenter.Presenter;
import com.breadbin.festival.presenter.busevents.ArticlesListRetrievedEvent;
import com.breadbin.festival.presenter.storage.ArticlesStorage;
import com.model.error.ErrorResponse;
import com.model.news.Article;

import java.util.List;

import de.greenrobot.event.EventBus;

public class NewsPresenter extends Presenter<List<Article>> {

	public NewsPresenter(Context context, ContentRestClient restClient) {
		super(context, restClient);
	}

	@Override
	public void getFromStorage() {
		List<Article> articleList = ArticlesStorage.getInstance(context).readArticles();
		if (articleList != null && !articleList.isEmpty()) {
			postDeliveredEvent(articleList);
		}
	}

	@Override
	protected void requestFromNetwork() {
		restClient.getNewsArticles(articlesCallback);
	}

	private Callback articlesCallback = new Callback<List<Article>>() {
		@Override
		public void onSuccess(List<Article> articleList) {
			handleSuccessResponse(articleList);
		}

		@Override
		public void onFailure(ErrorResponse errorResponse) {
			// TODO
		}

		@Override
		public void onFinish() {
			// TODO
		}
	};

	@Override
	public void postDeliveredEvent(List<Article> articleList) {
		EventBus.getDefault().post(new ArticlesListRetrievedEvent(articleList));
	}

	@Override
	public void postUpdatedEvent(List<Article> articleList) {
		// Not used.
	}

	private void handleSuccessResponse(List<Article> retrievedArticles) {
		ArticlesStorage.getInstance(context).saveArticles(retrievedArticles);
		postDeliveredEvent(retrievedArticles);
	}
}

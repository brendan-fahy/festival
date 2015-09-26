package com.breadbin.festival.news.model;

import android.content.Context;

import com.breadbin.festival.common.storage.ObjectCacher;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class ArticlesStorage {

	private static final String ARTICLE_STORAGE_NAME = "article_storage";

	private static ArticlesStorage instance;

  private ObjectCacher<List<Article>> articleCacher;

	public static ArticlesStorage getInstance(Context context) {
		if (instance == null) {
			instance = new ArticlesStorage(context);
		}
		return instance;
	}

	private ArticlesStorage(Context context) {
    articleCacher = new ObjectCacher<>(context.getApplicationContext().getCacheDir(),
        ARTICLE_STORAGE_NAME);
	}

	public CachedArticles saveArticles(List<Article> articles) {
    return new CachedArticles(articleCacher.save(articles));
	}

	public Observable<CachedArticles> readArticles() {
    return Observable.create(new Observable.OnSubscribe<CachedArticles>() {
      @Override
      public void call(Subscriber<? super CachedArticles> subscriber) {
        CachedArticles cachedArticles = (CachedArticles) articleCacher.get(CachedArticles.class);
        subscriber.onNext(cachedArticles);
        subscriber.onCompleted();
      }
    });
	}
}

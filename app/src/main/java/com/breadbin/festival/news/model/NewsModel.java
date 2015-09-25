package com.breadbin.festival.news.model;

import android.content.Context;

import com.breadbin.festival.common.Model;
import com.breadbin.festival.common.api.ContentRestClient;
import com.breadbin.festival.common.api.NoDataException;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class NewsModel extends Model<List<Article>> {

  public NewsModel(Context context, ContentRestClient restClient) {
    super(context, restClient);
  }

  @Override
  public Observable<List<Article>> getObservable() {
    return Observable
        .create(new Observable.OnSubscribe<List<Article>>() {
          @Override
          public void call(final Subscriber<? super List<Article>> subscriber) {
            final ArticlesStorage storage = ArticlesStorage.getInstance(context);
            final List<Article> articleList = storage.readArticles();
            if (articleList != null && !articleList.isEmpty()) {
              subscriber.onNext(articleList);
            } else if (!isConnectedOrConnecting()) {
              subscriber.onError(new NoDataException());
            }

            restClient.getNewsArticles().subscribe(new Subscriber<List<Article>>() {
              @Override
              public void onCompleted() {

              }

              @Override
              public void onError(Throwable e) {
                subscriber.onError(e);
              }

              @Override
              public void onNext(List<Article> articles) {
                if (articles != null && !articles.isEmpty()) {
                  storage.saveArticles(articles);
                  subscriber.onNext(articles);
                }
              }
            });
            subscriber.onCompleted();
          }
        });
  }
}

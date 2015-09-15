package com.breadbin.festival.presenter.news;

import android.content.Context;

import com.breadbin.festival.api.ContentRestClient;
import com.breadbin.festival.api.NoDataException;
import com.breadbin.festival.model.news.Article;
import com.breadbin.festival.presenter.Presenter;
import com.breadbin.festival.presenter.storage.ArticlesStorage;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewsPresenter extends Presenter<List<Article>> {

  public NewsPresenter(Context context, ContentRestClient restClient) {
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
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }
}

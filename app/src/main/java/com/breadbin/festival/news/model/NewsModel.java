package com.breadbin.festival.news.model;

import android.content.Context;

import com.breadbin.festival.common.Model;
import com.breadbin.festival.common.api.ContentRestClient;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class NewsModel extends Model<List<Article>> {

  public NewsModel(Context context, ContentRestClient restClient) {
    super(context, restClient);
  }

  @Override
  public Observable<List<Article>> getObservable() {
    final ArticlesStorage storage = ArticlesStorage.getInstance(context);

    Observable<CachedArticles> storageData = storage.readArticles();

    Observable<CachedArticles> networkData = restClient.getNewsArticles()
        .map(new Func1<List<Article>, CachedArticles>() {
          @Override
          public CachedArticles call(List<Article> articles) {
            return storage.saveArticles(articles);
          }
        });

    return Observable
        .concat(storageData, networkData)
        .first(new Func1<CachedArticles, Boolean>() {
          @Override
          public Boolean call(CachedArticles cachedArticles) {
            return cachedArticles != null
                && isUpToDate(cachedArticles.getCacheStatus().getLastRefreshTime());
          }
        })
        .map(new Func1<CachedArticles, List<Article>>() {
          @Override
          public List<Article> call(CachedArticles cachedArticles) {
            return cachedArticles.get();
          }
        });
  }
}

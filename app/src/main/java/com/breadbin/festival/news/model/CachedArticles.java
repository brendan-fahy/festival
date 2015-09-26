package com.breadbin.festival.news.model;

import com.breadbin.festival.common.storage.CacheStatus;
import com.breadbin.festival.common.storage.CachedObject;

import java.util.List;

public class CachedArticles extends CachedObject<List<Article>> {

  public CachedArticles(List<Article> object, CacheStatus cacheStatus) {
    super(object, cacheStatus);
  }

  public CachedArticles(CachedObject<List<Article>> cachedObject) {
    this(cachedObject.get(), cachedObject.getCacheStatus());
  }

}

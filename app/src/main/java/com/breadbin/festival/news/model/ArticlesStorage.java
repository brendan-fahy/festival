package com.breadbin.festival.news.model;

import android.content.Context;

import com.breadbin.festival.common.storage.CachedObject;
import com.breadbin.festival.common.storage.ObjectCacher;

import java.util.ArrayList;
import java.util.List;

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

	public boolean saveArticles(List<Article> articles) {
    return articleCacher.save(articles);
	}

	public List<Article> readArticles() {
    CachedObject<List<Article>> cachedObject = articleCacher.get(CachedArticles.class);
    if (cachedObject == null || cachedObject.get() == null) {
      return new ArrayList<>(0);
    }
    return cachedObject.get();
	}
}

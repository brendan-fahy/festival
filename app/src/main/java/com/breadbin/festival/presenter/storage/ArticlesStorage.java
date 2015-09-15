package com.breadbin.festival.presenter.storage;

import android.content.Context;

import com.breadbin.festival.model.news.Article;

import java.util.List;

public class ArticlesStorage extends InternalStorageAdapter<List<Article>> {

	private static final String ARTICLE_STORAGE_NAME = "article_storage";

	private static final String ARTICLE_KEY = "article";

	private static ArticlesStorage instance;

	public static ArticlesStorage getInstance(Context context) {
		if (instance == null || instance.isClosed()) {
			instance = new ArticlesStorage(context);
		}
		return instance;
	}

	private ArticlesStorage(Context context) {
		super(context, ARTICLE_STORAGE_NAME);
	}

	public boolean saveArticles(List<Article> articles) {
		return super.save(ARTICLE_KEY, articles);
	}

	public List<Article> readArticles() {
		return super.find(ARTICLE_KEY);
	}
}

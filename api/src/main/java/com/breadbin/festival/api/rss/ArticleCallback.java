package com.breadbin.festival.api.rss;

import com.model.error.ErrorResponse;
import com.model.news.Article;

import java.util.List;

public interface ArticleCallback {

	void onSuccess(List<Article> articleList);

	void onFailure(ErrorResponse errorResponse);

	void onFinish();
}

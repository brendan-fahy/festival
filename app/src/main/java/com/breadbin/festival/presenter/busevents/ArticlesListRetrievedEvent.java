package com.breadbin.festival.presenter.busevents;

import com.breadbin.festival.model.news.Article;

import java.util.List;

public class ArticlesListRetrievedEvent {

	private final List<Article> articleList;

	public ArticlesListRetrievedEvent(List<Article> articleList) {
		this.articleList = articleList;
	}

	public List<Article> getArticleList() {
		return articleList;
	}
}

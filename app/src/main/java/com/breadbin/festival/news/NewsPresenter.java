package com.breadbin.festival.news;

import com.breadbin.festival.news.model.Article;

import java.util.List;

public interface NewsPresenter {

  void onStart(List<Article> articles);

}

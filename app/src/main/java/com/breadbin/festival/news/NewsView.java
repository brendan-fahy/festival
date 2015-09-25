package com.breadbin.festival.news;

import android.content.Context;
import android.widget.BaseAdapter;

import com.breadbin.festival.news.model.Article;

import java.util.List;

public interface NewsView {

  Context getContext();

  void setupTitle();

  void setAdapter(BaseAdapter adapter);

  void setItemClickListener(List<Article> articles);
}

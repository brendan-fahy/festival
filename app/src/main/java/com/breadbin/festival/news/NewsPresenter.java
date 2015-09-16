package com.breadbin.festival.news;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.breadbin.festival.news.model.Article;

import java.util.List;

public class NewsPresenter {

  private NewsWidget widget;

  public NewsPresenter(NewsWidget widget) {
    this.widget = widget;
  }

  public void onStart(final List<Article> articles) {
    widget.setAdapter(new ArticlesAdapter(articles));
    widget.setItemClickListener(articles);
  }

  private class ArticlesAdapter extends BaseAdapter {

    private List<Article> articlesList;

    public ArticlesAdapter(List<Article> articlesList) {
      this.articlesList = articlesList;
    }

    @Override
    public int getCount() {
      return articlesList.size();
    }

    @Override
    public Object getItem(int position) {
      return articlesList.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
        convertView = ArticleCard.build(widget.getContext());
      }
      ArticleCard articleCard = (ArticleCard) convertView;
      articleCard.bindTo(articlesList.get(position));
      return articleCard;
    }
  };
}

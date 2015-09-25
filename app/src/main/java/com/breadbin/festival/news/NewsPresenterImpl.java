package com.breadbin.festival.news;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.breadbin.festival.news.model.Article;

import java.util.List;

public class NewsPresenterImpl implements NewsPresenter {

  private NewsView view;

  public NewsPresenterImpl(NewsView view) {
    this.view = view;
  }

  public void onStart(final List<Article> articles) {
    view.setAdapter(new ArticlesAdapter(articles));
    view.setItemClickListener(articles);
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
        convertView = ArticleCard.build(parent.getContext());
      }
      ArticleCard articleCard = (ArticleCard) convertView;
      articleCard.bindTo(articlesList.get(position));
      return articleCard;
    }
  }
}

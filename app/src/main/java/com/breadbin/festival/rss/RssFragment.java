package com.breadbin.festival.rss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.breadbin.festival.NavigationDrawerActivity;
import com.breadbin.festival.R;
import com.breadbin.festival.presenter.busevents.ArticlesListRetrievedEvent;
import com.breadbin.festival.views.ArticleCard;
import com.model.news.Article;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class RssFragment extends Fragment {

	private Toolbar toolbar;

	private ListView listView;

	private List<Article> articlesList = new ArrayList<>();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_rss, container, false);
		toolbar = (Toolbar) viewGroup.findViewById(R.id.toolbar);
		listView = (ListView) viewGroup.findViewById(R.id.listView);

		((NavigationDrawerActivity) getActivity()).updateToolbarForNavDrawer(toolbar, R.string.app_name);

		listView.setAdapter(articlesAdapter);

		return viewGroup;
	}

	public void onEvent(ArticlesListRetrievedEvent event) {
		articlesList = event.getArticleList();
		articlesAdapter.notifyDataSetChanged();
	}

	private BaseAdapter articlesAdapter = new BaseAdapter() {
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
				convertView = ArticleCard.build(getActivity());
			}
			ArticleCard articleCard = (ArticleCard) convertView;
			articleCard.bindTo(articlesList.get(position));
			return articleCard;
		}
	};

	@Override
	public void onStart() {
		super.onStart();

		EventBus.getDefault().register(this);
	}

	@Override
	public void onStop() {
		super.onStop();

		EventBus.getDefault().unregister(this);
	}
}

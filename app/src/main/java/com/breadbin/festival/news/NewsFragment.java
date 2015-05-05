package com.breadbin.festival.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.breadbin.festival.HomeActivity;
import com.breadbin.festival.R;
import com.breadbin.festival.presenter.busevents.ArticlesListRetrievedEvent;
import com.breadbin.festival.views.ArticleCard;
import com.model.news.Article;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class NewsFragment extends Fragment {

	private static final String ARTICLES_ARG = "articles_arg";

	private Toolbar toolbar;

	private ListView listView;

	private List<Article> articlesList = new ArrayList<>();

	public static NewsFragment newInstance(List<Article> articles) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(ARTICLES_ARG, new ArrayList<>(articles));

		NewsFragment newsFragment = new NewsFragment();
		newsFragment.setArguments(bundle);

		return newsFragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_news, container, false);
		toolbar = (Toolbar) viewGroup.findViewById(R.id.toolbar);
		listView = (ListView) viewGroup.findViewById(R.id.listView);

		((HomeActivity) getActivity()).updateToolbarForNavDrawer(toolbar, R.string.app_name);

		articlesList = (List<Article>) getArguments().getSerializable(ARTICLES_ARG);
		articlesAdapter.notifyDataSetChanged();
		listView.setAdapter(articlesAdapter);
		setupItemClickListener();

		return viewGroup;
	}

	private void setupItemClickListener() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(articlesList.get(position).getLink()));
				startActivity(intent);
			}
		});
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

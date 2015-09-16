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

import com.breadbin.festival.NavigationDrawerActivity;
import com.breadbin.festival.app.R;
import com.breadbin.festival.news.model.Article;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsFragment extends Fragment {

	private static final String ARTICLES_ARG = "articles_arg";

  @InjectView(R.id.toolbar)
	Toolbar toolbar;
  @InjectView(R.id.listView)
	ListView listView;

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
    ButterKnife.inject(this, viewGroup);

		((NavigationDrawerActivity) getActivity()).updateToolbarForNavDrawer(toolbar, R.string.app_name);

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
}

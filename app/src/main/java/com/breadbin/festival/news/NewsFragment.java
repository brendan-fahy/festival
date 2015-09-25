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

import com.breadbin.festival.AbsHomescreenActivity;
import com.breadbin.festival.app.R;
import com.breadbin.festival.news.model.Article;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsFragment extends Fragment implements NewsView {

	private static final String ARTICLES_ARG = "articles_arg";

  @InjectView(R.id.toolbar)
	Toolbar toolbar;
  @InjectView(R.id.listView)
	ListView listView;

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

    NewsPresenter presenter = new NewsPresenterImpl(this);
    presenter.onStart((List<Article>) getArguments().getSerializable(ARTICLES_ARG));

		return viewGroup;
	}

  @Override
  public void setupTitle() {
    ((AbsHomescreenActivity) getActivity()).updateToolbarForNavDrawer(toolbar, R.string.app_name);
  }

  @Override
  public void setAdapter(BaseAdapter adapter) {
    listView.setAdapter(adapter);
  }

  @Override
	public void setItemClickListener(final List<Article> articles) {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(articles.get(position).getLink()));
				startActivity(intent);
			}
		});
	}

}

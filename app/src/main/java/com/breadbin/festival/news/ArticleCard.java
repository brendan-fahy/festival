package com.breadbin.festival.news;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.breadbin.festival.app.R;
import com.breadbin.festival.news.model.Article;

public class ArticleCard extends LinearLayout {

	private TextView titleView;

	private TextView bodyView;

	public ArticleCard(Context context) {
		super(context);

		setupViews(context);
	}

	public ArticleCard(Context context, AttributeSet attrs) {
		super(context, attrs);

		setupViews(context);
	}

	public ArticleCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setupViews(context);
	}

	public static ArticleCard build(Context context) {
		return new ArticleCard(context, null);
	}

	private void setupViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.card_article, this, true);
		titleView = (TextView) viewGroup.findViewById(R.id.title);
		bodyView = (TextView) viewGroup.findViewById(R.id.body);
	}

	public void bindTo(Article article) {
		titleView.setText(article.getTitle());
		bodyView.setText(article.getDescription());
	}
}

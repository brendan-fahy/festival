package com.breadbin.festival.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.breadbin.festival.NavigationDrawerActivity;
import com.breadbin.festival.app.R;
import com.breadbin.festival.schedule.model.Schedule;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SchedulePagerFragment extends Fragment implements SchedulePagerView {

	private static final String EVENTS_ARG = "schedule";

  @InjectView(R.id.viewPager)
	ViewPager viewPager;
  @InjectView(R.id.toolbar)
	Toolbar toolbar;
  @InjectView(R.id.tabLayout)
	TabLayout tabLayout;

	public static SchedulePagerFragment newInstance(Schedule schedule) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(EVENTS_ARG, schedule);

		SchedulePagerFragment fragment = new SchedulePagerFragment();
		fragment.setArguments(bundle);

		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_schedule_pager, container, false);

    ButterKnife.inject(this, viewGroup);

		return viewGroup;
	}

  @Override
  public void onStart() {
    super.onStart();

    SchedulePagerPresenter presenter = new SchedulePagerPresenterImpl(
        this, (Schedule) getArguments().getSerializable(EVENTS_ARG));

    presenter.onStart();
  }

  @Override
	public void setupViewPager(PagerAdapter adapter) {
		viewPager.setAdapter(adapter);
	}

  @Override
	public void setupTabLayout(PagerAdapter adapter) {
		tabLayout.setupWithViewPager(viewPager);
		tabLayout.setTabsFromPagerAdapter(adapter);
		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
	}

  @Override
	public void setupTitle() {
    NavigationDrawerActivity activity = ((NavigationDrawerActivity) getActivity());
    activity.setSupportActionBar(toolbar);
    activity.updateToolbarForNavDrawer(toolbar, R.string.app_name);
	}

}

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

import com.breadbin.festival.HomescreenView;
import com.breadbin.festival.app.R;
import com.breadbin.festival.schedule.model.Schedule;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SchedulePagerFragment extends Fragment implements SchedulePagerView {

	private static final String EVENTS_ARG = "schedule";

  @Bind(R.id.viewPager)
	ViewPager viewPager;
  @Bind(R.id.toolbar)
	Toolbar toolbar;
  @Bind(R.id.tabLayout)
	TabLayout tabLayout;

	public static SchedulePagerFragment newInstance(Schedule schedule) {
		Bundle bundle = new Bundle();
		bundle.putParcelable(EVENTS_ARG, schedule);

		SchedulePagerFragment fragment = new SchedulePagerFragment();
		fragment.setArguments(bundle);

		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_schedule_pager, container, false);

    ButterKnife.bind( this, viewGroup);

		return viewGroup;
	}

  @Override
  public void onStart() {
    super.onStart();

    SchedulePagerPresenter presenter = new SchedulePagerPresenterImpl(
        this, (Schedule) getArguments().getParcelable(EVENTS_ARG));

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
    ((HomescreenView) getActivity()).updateToolbarForNavDrawer(toolbar, R.string.app_name);
	}

}

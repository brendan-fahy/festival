package com.breadbin.festival.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.breadbin.festival.BaseActivity;
import com.breadbin.festival.R;
import com.breadbin.festival.presenter.busevents.ScheduleUpdatedEvent;
import com.breadbin.festival.views.SlidingTabLayout;
import com.model.events.Schedule;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import de.greenrobot.event.EventBus;

public class SchedulePagerFragment extends Fragment {

	private static final String EVENTS_ARG = "schedule";

	private ViewPager viewPager;
	private Toolbar toolbar;
	private SlidingTabLayout slidingTabLayout;

	private Schedule schedule;

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

		setupViews(viewGroup);
		setupSchedule();
		setupViewPager();
		setupSlidingTabLayout();
		setupTitle();

		return viewGroup;
	}

	private void setupViews(ViewGroup viewGroup) {
		viewPager = (ViewPager) viewGroup.findViewById(R.id.viewPager);
		toolbar = (Toolbar) viewGroup.findViewById(R.id.toolbar);
		slidingTabLayout = (SlidingTabLayout) viewGroup.findViewById(R.id.slidingTabLayout);
	}

	private void setupSchedule() {
		schedule = (Schedule) getArguments().getSerializable(EVENTS_ARG);
	}

	private void setupViewPager() {
		viewPager.setAdapter(new ScheduleDaysPagerAdapter(getActivity().getSupportFragmentManager()));
	}

	private void setupSlidingTabLayout() {
		slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
			@Override
			public int getIndicatorColor(int position) {
				return getResources().getColor(R.color.primaryDark);
			}

			@Override
			public int getDividerColor(int position) {
				return getResources().getColor(R.color.dividercolor);
			}
		});
		slidingTabLayout.setCustomTabView(R.layout.tab_title_textview, R.id.tabTitleText);
		slidingTabLayout.setViewPager(viewPager);
	}

	private void setupTitle() {
		((BaseActivity) getActivity()).setSupportActionBar(toolbar);
	}

	public void onEvent(ScheduleUpdatedEvent event) {
		schedule = event.getSchedule();
		setupViewPager();
		setupSlidingTabLayout();
	}

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

	private class ScheduleDaysPagerAdapter extends FragmentStatePagerAdapter {

		// TODO Maybe extract for more configurability?
		private DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("EEE dd MMMMMMMM");

		public ScheduleDaysPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return ScheduleDayFragment.newInstance(schedule.getDays().get(position));
		}

		@Override
		public int getCount() {
			return schedule.getDays().size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return DATE_FORMATTER.print(schedule.getDays().get(position).getDate());
		}
	}
}

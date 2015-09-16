package com.breadbin.festival.schedule;

import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.breadbin.festival.NavigationDrawerActivity;
import com.breadbin.festival.app.R;
import com.breadbin.festival.schedule.model.Event;
import com.breadbin.festival.schedule.model.Schedule;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SchedulePagerFragment extends Fragment {

	private static final String EVENTS_ARG = "schedule";

  @InjectView(R.id.viewPager)
	ViewPager viewPager;
  @InjectView(R.id.toolbar)
	Toolbar toolbar;
  @InjectView(R.id.tabLayout)
	TabLayout tabLayout;

	private PagerAdapter scheduleDaysPagerAdapter;

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

    ButterKnife.inject(this, viewGroup);

		setupSchedule();
		setupAdapter();
		setupViewPager();
		setupTabLayout();
		setupTitle();

		return viewGroup;
	}

	private void setupSchedule() {
		schedule = (Schedule) getArguments().getSerializable(EVENTS_ARG);
	}

	private void setupViewPager() {
		viewPager.setAdapter(scheduleDaysPagerAdapter);
	}

	private void setupTabLayout() {
		tabLayout.setupWithViewPager(viewPager);
		tabLayout.setTabsFromPagerAdapter(scheduleDaysPagerAdapter);
		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
	}

	private void setupTitle() {
		((NavigationDrawerActivity) getActivity()).setSupportActionBar(toolbar);
	}

	@Override
	public void onStart() {
		super.onStart();

		((NavigationDrawerActivity) getActivity()).updateToolbarForNavDrawer(toolbar, R.string.app_name);

		setupAdapter();
	}

	private void setupAdapter() {
		scheduleDaysPagerAdapter = new ListPagerAdapter();
	}

	private class ListPagerAdapter extends PagerAdapter {

		private DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("EEE dd MMMMMMMM");

		@Override
		public int getCount() {
			return schedule.getDays().size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return DATE_FORMATTER.print(schedule.getDays().get(position).getDate());
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listview_for_cards, container, false);

			((ListView) view).setAdapter(new EventsAdapter(schedule.getDays().get(position).getEventList()));

			container.addView(view, 0);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}

	private class EventsAdapter extends BaseAdapter {

		private List<Event> events;

		public EventsAdapter(List<Event> events) {
			this.events = events;
		}

		@Override
		public int getCount() {
			return events.size();
		}

		@Override
		public Object getItem(int position) {
			return events.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = EventCard.build(getActivity());
			}
			EventCard eventCard = (EventCard) convertView;
			eventCard.bindTo(events.get(position));
			return eventCard;
		}
	}
}

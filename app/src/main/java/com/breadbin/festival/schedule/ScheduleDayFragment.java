package com.breadbin.festival.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.breadbin.festival.R;
import com.breadbin.festival.views.EventCard;
import com.model.events.Event;
import com.model.events.ScheduleDay;

import java.util.List;

public class ScheduleDayFragment extends Fragment {

	private static final String EVENT_LIST_ARG = "eventListArg";

	private ScheduleDay scheduleDay;

	private ListView listView;

	public static ScheduleDayFragment newInstance(ScheduleDay schedule) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(EVENT_LIST_ARG, schedule);

		ScheduleDayFragment fragment = new ScheduleDayFragment();
		fragment.setArguments(bundle);

		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.listview_for_cards, container, false);
		listView = (ListView) viewGroup.findViewById(R.id.listView);

		scheduleDay = (ScheduleDay) getArguments().getSerializable(EVENT_LIST_ARG);

		listView.setAdapter(new EventsAdapter(scheduleDay.getEventList()));

		return viewGroup;
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

package com.breadbin.festival;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.breadbin.festival.busevents.EventsListUpdatedEvent;
import com.breadbin.festival.views.EventCard;
import com.model.events.Event;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class CalendarEventsListFragment extends Fragment {

	public static final String EVENTS_ARG = "events";

	private ListView listView;

	private Toolbar toolbar;

	private List<Event> events;

	public static CalendarEventsListFragment newInstance(List<Event> events) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(EVENTS_ARG, new ArrayList<>(events));

		CalendarEventsListFragment fragment = new CalendarEventsListFragment();
		fragment.setArguments(bundle);

		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_calendar_events_list, container, false);
		listView = (ListView) viewGroup.findViewById(R.id.listView);
		toolbar = (Toolbar) viewGroup.findViewById(R.id.toolbar);

		events = (List<Event>) getArguments().getSerializable(EVENTS_ARG);
		showListOfEvents();

		return viewGroup;
	}

	private void showListOfEvents() {
		listView.setAdapter(new EventsAdapter(events));
	}

	public void onEvent(EventsListUpdatedEvent event) {
		events = event.getEventList();
		showListOfEvents();
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

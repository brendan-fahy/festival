package com.breadbin.festival;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.breadbin.festival.api.googlecalendar.CalendarConverter;
import com.breadbin.festival.views.EventCard;
import com.model.events.Event;
import com.model.googlecalendarapi.CalendarData;
import com.model.googlecalendarapi.CalendarItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CalendarEventsListFragment extends Fragment {

	public static final String CALENDAR_DATA_ARG = "calendarData";

	private ListView listView;

	private Toolbar toolbar;

	public static CalendarEventsListFragment newInstance(CalendarData calendarData) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(CALENDAR_DATA_ARG, calendarData);

		CalendarEventsListFragment fragment = new CalendarEventsListFragment();
		fragment.setArguments(bundle);

		return fragment;
	}

	public CalendarEventsListFragment() {

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_calendar_events_list, container, false);
		listView = (ListView) viewGroup.findViewById(R.id.listView);
		toolbar = (Toolbar) viewGroup.findViewById(R.id.toolbar);

		listView.setAdapter(new EventsAdapter(getEventsFromCalendarData()));

		return viewGroup;
	}

	private List<Event> getEventsFromCalendarData() {
		CalendarData calendarData = (CalendarData) getArguments().getSerializable(CALENDAR_DATA_ARG);

		List<Event> allEvents = new ArrayList<>();

		for (CalendarItem item: calendarData.getItems()) {
			try {
				allEvents.add(CalendarConverter.convertToEvent(item));
			} catch (Exception ex) {
				Log.w("CalendarEventsList", "Exception converting CalendarItem to Event: " + ex.getMessage());
			}
		}

		return allEvents;
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

package com.breadbin.festival.view.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.breadbin.festival.api.googlecalendar.CalendarConverter;
import com.breadbin.festival.app.R;
import com.breadbin.festival.model.events.Event;

import org.joda.time.DateTime;

public class EventCard extends LinearLayout {

	private TextView titleView;

	private TextView descriptionView;

	private TextView locationView;

	private TextView timeView;

	public EventCard(Context context, AttributeSet attrs) {
		super(context, attrs);

		setupViews(context);
	}

	public EventCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setupViews(context);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public EventCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);

		setupViews(context);
	}

	public static EventCard build(Context context) {
		return new EventCard(context, null);
	}

	private void setupViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.card_event, this, true);
		titleView = (TextView) viewGroup.findViewById(R.id.title);
		descriptionView = (TextView) viewGroup.findViewById(R.id.description);
		locationView = (TextView) viewGroup.findViewById(R.id.location);
		timeView = (TextView) viewGroup.findViewById(R.id.time);
	}

	public void bindTo(Event event) {
		titleView.setText(event.getTitle());
		descriptionView.setText(event.getDescription());
		locationView.setText(event.getLocation());
		timeView.setText(getTimeForDisplay(event.getTime()));
	}

	private String getTimeForDisplay(DateTime dateTime) {
		return (dateTime.getHourOfDay() == 0) ? "" : CalendarConverter.HOUR_MINUTES_FORMATTER.print(dateTime);
	}

}

package com.breadbin.festival.schedule;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.breadbin.festival.app.R;
import com.breadbin.festival.schedule.model.Event;
import com.breadbin.festival.schedule.model.Schedule;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

public class SchedulePagerAdapter extends PagerAdapter {

  private final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("EEE dd MMMMMMMM");

  private Schedule schedule;

  public SchedulePagerAdapter(Schedule schedule) {
    this.schedule = schedule;
  }

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
    View view = LayoutInflater.from(container.getContext()).inflate(R.layout.listview_for_cards, container, false);

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
        convertView = EventCard.build(parent.getContext());
      }
      EventCard eventCard = (EventCard) convertView;
      eventCard.bindTo(events.get(position));
      return eventCard;
    }
  }
}

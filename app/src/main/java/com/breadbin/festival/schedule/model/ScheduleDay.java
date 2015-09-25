package com.breadbin.festival.schedule.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDay implements Comparable<ScheduleDay>, Parcelable {

  private long date;

  private List<Event> eventList;

  public ScheduleDay() {
  }

  public void addEvent(Event event) {
    if (eventList == null) {
      eventList = new ArrayList<>();
    }
    eventList.add(event);
  }

  public DateTime getDate() {
    return new DateTime(date);
  }

  public void setDate(DateTime date) {
    this.date = date.getMillis();
  }

  public List<Event> getEventList() {
    return eventList;
  }

  public void setEventList(List<Event> eventList) {
    this.eventList = eventList;
  }

  @Override
  public int compareTo(ScheduleDay another) {
    return (date < another.date) ? -1 : 1;
  }


  protected ScheduleDay(Parcel in) {
    date = in.readLong();
    if (in.readByte() == 0x01) {
      eventList = new ArrayList<Event>();
      in.readList(eventList, Event.class.getClassLoader());
    } else {
      eventList = null;
    }
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(date);
    if (eventList == null) {
      dest.writeByte((byte) (0x00));
    } else {
      dest.writeByte((byte) (0x01));
      dest.writeList(eventList);
    }
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<ScheduleDay> CREATOR = new Parcelable.Creator<ScheduleDay>() {
    @Override
    public ScheduleDay createFromParcel(Parcel in) {
      return new ScheduleDay(in);
    }

    @Override
    public ScheduleDay[] newArray(int size) {
      return new ScheduleDay[size];
    }
  };
}
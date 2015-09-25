package com.breadbin.festival.schedule.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Schedule implements Parcelable {

  private List<ScheduleDay> days;

  public List<ScheduleDay> getDays() {
    return days;
  }

  public void setDays(List<ScheduleDay> days) {
    this.days = days;
  }

  public Schedule() {
  }

  protected Schedule(Parcel in) {
    if (in.readByte() == 0x01) {
      days = new ArrayList<ScheduleDay>();
      in.readList(days, ScheduleDay.class.getClassLoader());
    } else {
      days = null;
    }
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    if (days == null) {
      dest.writeByte((byte) (0x00));
    } else {
      dest.writeByte((byte) (0x01));
      dest.writeList(days);
    }
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {
    @Override
    public Schedule createFromParcel(Parcel in) {
      return new Schedule(in);
    }

    @Override
    public Schedule[] newArray(int size) {
      return new Schedule[size];
    }
  };
}
package com.breadbin.festival.schedule.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

public class Event implements Comparable<Event>, Parcelable {

  private String title;

  private String description;

  private String teacher;

  private long time;

  private String location;

  private boolean allDayEvent;

  private Event(String title, String description, String teacher, long time, String location,
                boolean allDayEvent) {
    this.title = title;
    this.description = description;
    this.teacher = teacher;
    this.time = time;
    this.location = location;
    this.allDayEvent = allDayEvent;
  }

  public boolean isAllDayEvent() {
    return allDayEvent;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getTeacher() {
    return teacher;
  }

  public DateTime getTime() {
    return new DateTime(time);
  }

  public String getLocation() {
    return location;
  }

  public static class Builder {
    private String title;
    private String description;
    private String teacher;
    private long time;
    private String location;
    private boolean allDayEvent;

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder withTeacher(String teacher) {
      this.teacher = teacher;
      return this;
    }

    public Builder withTime(long time) {
      this.time = time;
      return this;
    }

    public Builder withLocation(String location) {
      this.location = location;
      return this;
    }

    public Builder withAllDayEvent(boolean allDayEvent) {
      this.allDayEvent = allDayEvent;
      return this;
    }

    public Event build() {
      return new Event(title, description, teacher, time, location, allDayEvent);
    }
  }

  @Override
  public int compareTo(Event another) {
    return (getTime().isBefore(another.getTime())) ? -1 : 1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Event event = (Event) o;

    if (time != event.time) return false;
    if (allDayEvent != event.allDayEvent) return false;
    if (title != null ? !title.equals(event.title) : event.title != null) return false;
    if (description != null ? !description.equals(event.description) : event.description != null)
      return false;
    if (teacher != null ? !teacher.equals(event.teacher) : event.teacher != null) return false;
    return !(location != null ? !location.equals(event.location) : event.location != null);

  }

  @Override
  public int hashCode() {
    int result = title != null ? title.hashCode() : 0;
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
    result = 31 * result + (int) (time ^ (time >>> 32));
    result = 31 * result + (location != null ? location.hashCode() : 0);
    result = 31 * result + (allDayEvent ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Event{" +
        "title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", teacher='" + teacher + '\'' +
        ", time=" + time +
        ", location='" + location + '\'' +
        ", allDayEvent=" + allDayEvent +
        '}';
  }

  protected Event(Parcel in) {
    title = in.readString();
    description = in.readString();
    teacher = in.readString();
    time = in.readLong();
    location = in.readString();
    allDayEvent = in.readByte() != 0x00;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeString(description);
    dest.writeString(teacher);
    dest.writeLong(time);
    dest.writeString(location);
    dest.writeByte((byte) (allDayEvent ? 0x01 : 0x00));
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
    @Override
    public Event createFromParcel(Parcel in) {
      return new Event(in);
    }

    @Override
    public Event[] newArray(int size) {
      return new Event[size];
    }
  };
}
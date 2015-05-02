package com.model.events;

import org.joda.time.DateTime;

import java.io.Serializable;

public class Event implements Comparable<Event>, Serializable {

  private String title;

  private String description;

  private String teacher;

  private DateTime time;

  private String location;

	private boolean allDayEvent;

	public boolean isAllDayEvent() {
		return allDayEvent;
	}

	public void setAllDayEvent(boolean allDayEvent) {
		this.allDayEvent = allDayEvent;
	}

	public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTeacher() {
    return teacher;
  }

  public void setTeacher(String teacher) {
    this.teacher = teacher;
  }

  public DateTime getTime() {
    return time;
  }

  public void setTime(DateTime time) {
    this.time = time;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

	@Override
	public int compareTo(Event another) {
		return (time.isBefore(another.time)) ? -1 : 1;
	}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Event event = (Event) o;

    if (allDayEvent != event.allDayEvent) return false;
    if (title != null ? !title.equals(event.title) : event.title != null) return false;
    if (description != null ? !description.equals(event.description) : event.description != null)
      return false;
    if (teacher != null ? !teacher.equals(event.teacher) : event.teacher != null) return false;
    if (time != null ? !time.equals(event.time) : event.time != null) return false;
    return !(location != null ? !location.equals(event.location) : event.location != null);

  }

  @Override
  public int hashCode() {
    int result = title != null ? title.hashCode() : 0;
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + (location != null ? location.hashCode() : 0);
    result = 31 * result + (allDayEvent ? 1 : 0);
    return result;
  }
}

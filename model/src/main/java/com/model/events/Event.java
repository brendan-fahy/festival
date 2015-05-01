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
}

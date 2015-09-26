package com.breadbin.festival.schedule.model;

import org.joda.time.DateTime;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class EventTest {

  private static final String TITLE = "title";
  private static final String DESCRIPTION = "description";
  private static final String LOCATION = "location";
  private static final String TEACHER = "teacher";
  private static final long TIME = new DateTime().getMillis();
  private static final boolean ALL_DAY = true;

  @Test
  public void shouldCreateEventUsingBuilder() {
    Event event = new Event.Builder()
        .withTitle(TITLE)
        .withDescription(DESCRIPTION)
        .withLocation(LOCATION)
        .withTeacher(TEACHER)
        .withAllDayEvent(ALL_DAY)
        .withTime(TIME)
        .build();

    assertEquals(TITLE, event.getTitle());
    assertEquals(DESCRIPTION, event.getDescription());
    assertEquals(LOCATION, event.getLocation());
    assertEquals(TEACHER, event.getTeacher());
    assertEquals(ALL_DAY, event.isAllDayEvent());
    assertEquals(TIME, event.getTime().getMillis());
  }

}
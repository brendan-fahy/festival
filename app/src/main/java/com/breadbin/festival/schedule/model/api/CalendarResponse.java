package com.breadbin.festival.schedule.model.api;

import java.io.Serializable;

public class CalendarResponse implements Serializable {

  private String apiVersion;

  private CalendarData data;

  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public CalendarData getData() {
    return data;
  }

  public void setData(CalendarData data) {
    this.data = data;
  }
}

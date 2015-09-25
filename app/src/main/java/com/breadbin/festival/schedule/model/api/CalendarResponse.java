package com.breadbin.festival.schedule.model.api;

public class CalendarResponse {

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

package com.breadbin.festival.schedule.model.api;

import java.io.Serializable;
import java.util.List;

public class CalendarData implements Serializable {

  private String kind;

  private String id;

  private Author author;

  private String title;

  private String details;

  private String updated;

  private int totalResults;

  private int startIndex;

  private int itemsPerPage;

  private String feedLink;

  private String selfLink;

  private boolean canPost;

  private String timeZone;

  private int timesCleaned;

  private List<CalendarItem> items;

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getUpdated() {
    return updated;
  }

  public void setUpdated(String updated) {
    this.updated = updated;
  }

  public int getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(int totalResults) {
    this.totalResults = totalResults;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }

  public int getItemsPerPage() {
    return itemsPerPage;
  }

  public void setItemsPerPage(int itemsPerPage) {
    this.itemsPerPage = itemsPerPage;
  }

  public String getFeedLink() {
    return feedLink;
  }

  public void setFeedLink(String feedLink) {
    this.feedLink = feedLink;
  }

  public String getSelfLink() {
    return selfLink;
  }

  public void setSelfLink(String selfLink) {
    this.selfLink = selfLink;
  }

  public boolean isCanPost() {
    return canPost;
  }

  public void setCanPost(boolean canPost) {
    this.canPost = canPost;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public int getTimesCleaned() {
    return timesCleaned;
  }

  public void setTimesCleaned(int timesCleaned) {
    this.timesCleaned = timesCleaned;
  }

  public List<CalendarItem> getItems() {
    return items;
  }

  public void setItems(List<CalendarItem> items) {
    this.items = items;
  }
}

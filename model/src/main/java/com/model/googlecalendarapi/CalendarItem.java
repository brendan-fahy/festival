package com.model.googlecalendarapi;

import org.joda.time.DateTime;

import java.io.Serializable;

public class CalendarItem implements Serializable {

  private String kind;

  private String id;

  private String selfLink;

  private String alternateLink;

  private boolean canEdit;

  private String title;

  private DateTime created;

  private DateTime updated;

  private String details;

  private Creator creator;

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

  public String getSelfLink() {
    return selfLink;
  }

  public void setSelfLink(String selfLink) {
    this.selfLink = selfLink;
  }

  public String getAlternateLink() {
    return alternateLink;
  }

  public void setAlternateLink(String alternateLink) {
    this.alternateLink = alternateLink;
  }

  public boolean isCanEdit() {
    return canEdit;
  }

  public void setCanEdit(boolean canEdit) {
    this.canEdit = canEdit;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public DateTime getCreated() {
    return created;
  }

  public void setCreated(DateTime created) {
    this.created = created;
  }

  public DateTime getUpdated() {
    return updated;
  }

  public void setUpdated(DateTime updated) {
    this.updated = updated;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public Creator getCreator() {
    return creator;
  }

  public void setCreator(Creator creator) {
    this.creator = creator;
  }
}

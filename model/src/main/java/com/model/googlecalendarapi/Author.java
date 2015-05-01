package com.model.googlecalendarapi;

import java.io.Serializable;

public class Author implements Serializable {

  private String displayName;

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
}

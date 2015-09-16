package com.breadbin.festival.schedule.model.api;

import java.io.Serializable;

public class Creator implements Serializable {

  private String displayName;

  private String email;

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}

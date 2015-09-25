package com.breadbin.festival.common.storage;

public class CacheStatus {

  CachedObject.Source source;
  long lastRefreshTime;

  public CacheStatus(CachedObject.Source source, long lastRefreshTime) {
    this.source = source;
    this.lastRefreshTime = lastRefreshTime;
  }

  public CachedObject.Source getSource() {
    return source;
  }

  public long getLastRefreshTime() {
    return lastRefreshTime;
  }
}

package com.breadbin.festival.common.storage;

public class CachedObject<T> {
  private T object;
  private CacheStatus cacheStatus;

  public enum Source {
    CACHE, NETWORK
  }

  public CachedObject(T object, CacheStatus cacheStatus) {
    this.object = object;
    this.cacheStatus = cacheStatus;
  }

  public T get() {
    return object;
  }

  public CacheStatus getCacheStatus() {
    return cacheStatus;
  }
}

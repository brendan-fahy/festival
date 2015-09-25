package com.breadbin.festival.schedule.model;

import com.breadbin.festival.common.storage.CacheStatus;
import com.breadbin.festival.common.storage.CachedObject;

import java.util.List;

public class CachedEvents extends CachedObject<List<Event>> {

  public CachedEvents(List<Event> object, CacheStatus cacheStatus) {
    super(object, cacheStatus);
  }

}

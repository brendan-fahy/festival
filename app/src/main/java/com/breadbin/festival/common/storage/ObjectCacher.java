package com.breadbin.festival.common.storage;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class ObjectCacher<T> {

  private File file;
  private final Gson gson;

  public ObjectCacher(File cacheDir, String filename) {
    this(cacheDir, filename, new Gson());
  }

  public ObjectCacher(File cacheDir, String filename, Gson gson) {
    this.file = new File(cacheDir, filename);
    this.gson = gson;
  }

  public boolean save(T object) {
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(file);


      CacheStatus cacheStatus = new CacheStatus(CachedObject.Source.CACHE, new Date().getTime());
      CachedObject<T> cachedObject = new CachedObject<>(object, cacheStatus);
      String cachedObjectJson = gson.toJson(cachedObject);

      ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(fos));
      os.writeObject(cachedObjectJson);

      os.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return false;
  }

  public CachedObject<T> get(Class type) {
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(file);
      ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(fis));

      String cachedJson = (String) is.readObject();
      is.close();

      return (CachedObject<T>) gson.fromJson(cachedJson, type);
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  public boolean delete() {
    try {
      return file.delete();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}

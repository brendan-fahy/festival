package com.breadbin.festival.common;

import android.content.Context;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;
import com.jakewharton.disklrucache.DiskLruCache.Editor;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Storage based on local storage using DiskLruCache.
 */
public class InternalStorageAdapter<T> {

  private static final int DISK_CACHE_SIZE = 1024 * 1024 * 5; // 5MB
  private static final int VALUE_COUNT = 1;
  private static final String CACHE_DIR = "internal_storage";
  private static final String TAG = InternalStorageAdapter.class.getSimpleName();
  private static final int STORAGE_SCHEMA_VERSION = 1;

  private final String cacheName;

  private DiskLruCache diskCache;

  /**
   * Create InternalStorageAdapter under the given name.
   * @param context Application context.
   * @param name Unique name of the file where the objects will be stored.
   */
  public InternalStorageAdapter(Context context, String name) {
    this.cacheName = name;

    try {
      initCache(context);
    } catch (IOException e) {
      Log.e(TAG, "ERROR: " + e.getMessage());
    }
  }

  public boolean save(String key, T value) {
    Editor editor = null;
    boolean result = true;
    try {
      editor = diskCache.edit(key);
      if (editor == null) throw new IllegalStateException("Other edit in progress.");
      writeValue(value, editor);
      editor.commit();
    } catch (IOException e) {
      e.printStackTrace();
      result = false;
      Log.e(TAG, "ERROR: " + e.getMessage());
    }finally {
      if(editor != null){
        editor.abortUnlessCommitted();
      }
    }
    return result;
  }

  private void writeValue(T value, Editor editor) throws IOException {
    ObjectOutputStream out = new ObjectOutputStream(editor.newOutputStream(0));
    out.writeObject(value);
    out.close();
  }

  public T find(String key) {
    try {
      return (T) getObject(key);
    } catch (IOException |ClassNotFoundException e) {
      Log.e(TAG, "ERROR: " + e.getMessage());
    }
    return null;
  }

  public void remove(String key) {
    try {
      diskCache.remove(key);
    } catch (IOException e) {
      Log.e(TAG, "ERROR: " + e.getMessage());
    }
  }

  public boolean isClosed() {
    return diskCache.isClosed();
  }

  public void clearAll() {
    try {
      diskCache.delete();
    } catch (IOException e) {
      Log.e(TAG, "ERROR: " + e.getMessage());
    }
  }

  /**
   * Opens the stored cache, based on a "storage schema version".
   * If no cache file matches the version number, then any existing cache files are ignored, and a new one is created.
   *
   * @param context
   * @throws IOException
   */
  private void initCache(Context context) throws IOException {
    File f = getCacheFile(context, cacheName);

    diskCache = DiskLruCache.open(f, STORAGE_SCHEMA_VERSION, VALUE_COUNT, DISK_CACHE_SIZE);
  }

  private File getCacheFile(Context context, String name) {
    return new File(context.getCacheDir().getPath() + File.separator + CACHE_DIR + File.separator + name);
  }

  private Object getObject(String key) throws IOException, ClassNotFoundException {
    DiskLruCache.Snapshot snapshot = diskCache.get(key);
    if (snapshot != null) {
      ObjectInputStream in = new ObjectInputStream(snapshot.getInputStream(0));
      return in.readObject();

    }
    return null;
  }
}

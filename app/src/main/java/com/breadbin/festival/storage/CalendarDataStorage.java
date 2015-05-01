package com.breadbin.festival.storage;

import android.content.Context;

import com.model.googlecalendarapi.CalendarData;

public class CalendarDataStorage extends InternalStorageAdapter<CalendarData> {
	/**
	 * Create InternalStorageAdapter under the given name.
	 *
	 * @param context Application context.
	 * @param name    Unique name of the file where the objects will be stored.
	 */
	public CalendarDataStorage(Context context, String name) {
		super(context, name);
	}


}

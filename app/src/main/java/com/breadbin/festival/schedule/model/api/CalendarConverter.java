package com.breadbin.festival.schedule.model.api;

import android.text.Html;

import com.breadbin.festival.schedule.model.Event;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CalendarConverter {

  public static final String DATETIME_FORMAT = "EEE dd MMM yyyy HH:mm";
  public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormat.forPattern(DATETIME_FORMAT).withLocale(Locale.UK);
	public static final String DATETIME_NO_TIME_FORMAT = "EEE dd MMM yyyy";
	public static final DateTimeFormatter DATETIME_NO_TIME_FORMATTER = DateTimeFormat.forPattern(DATETIME_NO_TIME_FORMAT).withLocale(Locale.UK);
	public static final String DATETIME_FIRST_START_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final DateTimeFormatter FIRST_START_FORMATTER = DateTimeFormat.forPattern(DATETIME_FIRST_START_FORMAT).withLocale(Locale.UK);
	public static final String HOUR_MINUTES_FORMAT = "HH:mm";
	public static final DateTimeFormatter HOUR_MINUTES_FORMATTER = DateTimeFormat.forPattern(HOUR_MINUTES_FORMAT).withLocale(Locale.UK);
  public static final String WHEN = "When: ";
  public static final String TO = " to ";
  public static final String WHERE = "<br />Where: ";
  public static final String EVENT_STATUS = "<br />Event Status: ";
  public static final String EVENT_DESCRIPTION = "<br />Event Description: ";
	public static final String RECURRING_EVENT = "Recurring Event";
	public static final String RECURRING_FIRST_START = "First start: ";

	private String calendarItemDetailsExample =
      "When: Wed 19 Feb 2014 23:30 to Thu 20 Feb 2014 00:30 \n" +
          "GMT<br />\n" +
          "\n" +
          "<br />Where: main hall\n" +
          "<br />Event Status: confirmed\n" +
          "<br />Event Description: this is the description";

	private String recurringEventExample = "Recurring Event<br>\n" +
			"First start: 2014-07-20 11:30:00 IST<br >\n" +
			"Duration: 5400\n\n\n<br />" +
			"Where: School hall\n\u003cbr /\u003e" +
			"Event Status: confirmed\n\u003cbr /\u003e" +
			"Event Description: A series of workshops covering exercises and positions that are too difficult for " +
			"true beginners because they require already a certain level of strength, balance, body-tension and experience. " +
			"This series will be a good choice for participants who have acrobatics experience already.";

	public static List<Event> convertToEvents(CalendarData calendarData) {
		List<Event> eventList = new ArrayList<>();
		for (CalendarItem item: calendarData.getItems()) {
			eventList.add(convertToEvent(item));
		}
		return eventList;
	}

	public static Event convertToEvent(CalendarItem item) {
    String itemDetails = item.getDetails();

    Event event = new Event();
    event.setTime(getDate(itemDetails));
		event.setAllDayEvent(isAllDayEvent(itemDetails));
		try {
			event.setLocation(Html.fromHtml(URLDecoder.decode(getLocation(itemDetails), "UTF-8")).toString());
			event.setTitle(Html.fromHtml(URLDecoder.decode(item.getTitle(), "UTF-8")).toString());
			if (hasDescription(item.getDetails())) {
				event.setDescription(Html.fromHtml(getDescription(URLDecoder.decode(itemDetails, "UTF-8"))).toString());
			}
		} catch (UnsupportedEncodingException e) {
			// Broken VM doesn't support UTF-8
		}
    return event;
  }

  private static DateTime getDate(String itemDetails) {
		if (isRecurringEvent(itemDetails)) {
			return parseRecurringEventTimes(itemDetails);
		} else if (isAllDayEvent(itemDetails)) {
			return createDateTimeWithoutTime(itemDetails);
		} else {
			return createDateTime(itemDetails);
		}
  }

	private static boolean isRecurringEvent(String itemDetails) {
		return itemDetails.contains(RECURRING_EVENT);
	}

	private static DateTime parseRecurringEventTimes(String itemDetails) {
		String rawDate = itemDetails.substring(indexAfter(itemDetails, RECURRING_FIRST_START), itemDetails.indexOf(" IST\n" +
				"<br"));
		return FIRST_START_FORMATTER.parseDateTime(rawDate);
	}

	private static boolean isAllDayEvent(String dateLine) {
		return (!dateLine.contains(TO));
	}

	private static DateTime createDateTimeWithoutTime(String dateLine) {
		String rawDate = dateLine.substring(indexAfter(dateLine, WHEN), dateLine.indexOf("<br"));
		return DATETIME_NO_TIME_FORMATTER.parseDateTime(rawDate);
	}

	private static DateTime createDateTime(String itemDetails) {
		String rawDate = itemDetails.substring(indexAfter(itemDetails, WHEN), itemDetails.indexOf(TO));
		return DATETIME_FORMATTER.parseDateTime(rawDate);
	}

	private static String getLocation(String itemDetails) {
		return itemDetails.substring(indexAfter(itemDetails, WHERE), itemDetails.indexOf(EVENT_STATUS)).trim();
	}

	private static boolean hasDescription(String details) {
		return details.contains(EVENT_DESCRIPTION);
	}
  private static String getDescription(String itemDetails) {
    return itemDetails.substring(indexAfter(itemDetails, EVENT_DESCRIPTION), itemDetails.length());
  }

  private static String getStatus(String itemDetails) {
    return itemDetails.substring(indexAfter(itemDetails, EVENT_STATUS), itemDetails.indexOf(EVENT_DESCRIPTION));
  }

  private static int indexAfter(String full, String substring) {
    return full.indexOf(substring) + substring.length();
  }
}

package com.example.mealmanagement.util;

import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {


	public static String GetFormatedtDateWithDateYearMonthTime2(long date) {
		String dateString = "";
		try {
			SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss a ");

			dateString = ft.format(date);
		} catch (Exception ex) {
			Log.w("Exception", ex.getMessage());
		}

		return dateString;

	}

	public static Date GetDateTimeFromDateString2(String formatedDate) {
		Date dateObj = null;
		try {
			// java.text.SimpleDateFormat sdf =
			// new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
			dateObj = curFormater.parse(formatedDate);

			// String formatedDateString = sdf.format(dateObj);
			// return formatedDateString;

			// return dateObj;
		} catch (Exception ex) {

		}

		return dateObj;
	}



	public static void clearHourMinutesSeconds(Date date) {
		if (date == null)
			return;
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
	}







	public static String GetFormatedTimeString1(long formatedDate) {

		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(formatedDate);
		SimpleDateFormat sdf = new SimpleDateFormat(
				"hh:mm a");

		// SimpleDateFormat curFormater = new SimpleDateFormat("hh:mm a");

		String formatedDateString = sdf.format(formatedDate);
		return formatedDateString;

	}





	public static Calendar getdDefaultCalenderByHourAndMinute(int hour,
                                                              int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1971, 1, 16, hour, minute);

		return calendar;
	}

	public static String GetFormatedDateTimeString(Date formatedDate) {

		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		String formatedDateString = sdf.format(formatedDate);
		return formatedDateString;

	}

	public static Date GetDateFromDateString(String formatedDate) {
		Date dateObj = null;
		try {
			// java.text.SimpleDateFormat sdf =
			// new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
			dateObj = curFormater.parse(formatedDate);

			// String formatedDateString = sdf.format(dateObj);
			// return formatedDateString;

			// return dateObj;
		} catch (Exception ex) {
			Log.w("Exception", ex.getMessage());
		}

		return dateObj;
	}

	public static Date GetDateTimeFromDateString(String formatedDate) {
		Date dateObj = null;
		try {
			// java.text.SimpleDateFormat sdf =
			// new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			SimpleDateFormat curFormater = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateObj = curFormater.parse(formatedDate);

			// String formatedDateString = sdf.format(dateObj);
			// return formatedDateString;

			// return dateObj;
		} catch (Exception ex) {
			Log.w("Exception", ex.getMessage());
		}

		return dateObj;
	}

	public static String GetDateTimeFromDateString(Calendar formatedDate) {
		String dateString = "";

		try {
			Date date = formatedDate.getTime();
			SimpleDateFormat curFormater = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateString = curFormater.format(date);

//			dateString = date.getYear() + "-" + date.getMonth() + "-"
//					+ date.getDate() + " " + date.getHours() + ":00:00";

		} catch (Exception ex) {

		}

		return dateString;
	}

	// String date1 = "31/12/2011";
	// SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
	// java.util.Date d1 = null;
	// Calendar tdy1;
	//
	// try {
	// d1 = form.parse(date1);

	public static String GetFormatedDateString(Date formatedDate) {
		String formatedDateString = "";
		try {
			if (formatedDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd");

				formatedDateString = sdf.format(formatedDate);
			}
		} catch (Exception ex) {
			Log.w("Exception", ex.getMessage());
		}
		return formatedDateString;

	}

	public Date GetFormatedTime(int hour, int minute) {
		Date date = new Date(1971, 1, 1, hour, minute);

		return date;
	}

	public Date GetDefaultDate() {
		Date date = new Date(1971, 1, 1);
		return date;
	}

	public static String GetFormatedtDateWithDateYearMonthTime(Date date) {
		String dateString = "";
		try {
			SimpleDateFormat ft = new SimpleDateFormat("hh:mm a E, MMM dd yyyy");

			dateString = ft.format(date);
		} catch (Exception ex) {
			Log.w("Exception", ex.getMessage());
		}

		return dateString;

	}

	public static Date GetFormatedtDateWithDateYearMonthTime(String date) {
		Date dateString = null;
		try {
			SimpleDateFormat ft = new SimpleDateFormat("hh:mm a E, MMM dd yyyy");

			dateString = ft.parse(date);
		} catch (Exception ex) {
			Log.w("Exception", ex.getMessage());
		}

		return dateString;

	}

	// public static String GetFormatedTimeString(Date formatedDate) {
	//
	// try {
	// java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
	// "hh:mm a");
	//
	// String formatedDateString = sdf.format(formatedDate);
	// return formatedDateString;
	// } catch (Exception ex) {
	// }
	//
	// return "";
	//
	// }



	public static String GetChatFormatedTimeString(Date formatedDate) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					" hh:mm");

			String formatedDateString = sdf.format(formatedDate);
			return formatedDateString;
		} catch (Exception ex) {
		}

		return "";

	}

	// public static String GetFormatedTimeString1(Date formatedDate) {
	//
	// try {
	// java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
	// "hh:mm a");
	//
	// String formatedDateString = sdf.format(formatedDate);
	// return formatedDateString;
	// } catch (Exception ex) {
	// }
	//
	// return "";
	//
	// }

	public static String GetFormatedTimeString(Date formatedDate) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"hh:mm a");

			String formatedDateString = sdf.format(formatedDate);
			return formatedDateString;
		} catch (Exception ex) {
		}

		return "";

	}



}

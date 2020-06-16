package cl.huerta.codingdojo.subscriptions.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date nextDueDate(Date hired) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(hired);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		//get number of max days of next month
		Calendar NewCal = Calendar.getInstance();
		NewCal.set(year, month, 1);
		NewCal.add(Calendar.MONTH, 1);
		int nextMax=NewCal.getActualMaximum(Calendar.DAY_OF_MONTH);

		if(nextMax>=day) //if max day of next month is lower than current day, just add one month
		{
		  cal.set(year,month,1);
		  cal.add(Calendar.MONTH, 1);
		  cal.set(Calendar.DAY_OF_MONTH, day);
		} else { //if not: go to next month, set day to maximum number of day
		  cal.set(year,month,1);
		  cal.add(Calendar.MONTH, 1);
		  cal.set(Calendar.DAY_OF_MONTH, nextMax);
		}

		return cal.getTime();
	}
}

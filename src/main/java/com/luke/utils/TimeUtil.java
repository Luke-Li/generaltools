package com.luke.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间格式
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //设置日期格式

	/**
	 * 返回当前时间
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentTime() {
		String time = sdf.format(Calendar.getInstance().getTime());
		return time;
	}

	/**
	 * 返回当前日期
	 * yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentDate(){
		String day = df.format(Calendar.getInstance().getTime());
		return day;
	}

	/**
	 * 根据所给参数，返回当前时间
	 * @param format = "yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String getCurrentTime(String format) {
		SimpleDateFormat sdFormat = new SimpleDateFormat(format);
		String time = sdFormat.format(Calendar.getInstance().getTime());
		return time;
	}

	/**
	 * 获取几天前时间
	 * yyyy-mm-dd hh:mm:ss
	 * @param day
	 * @return
	 */
	public static String getTimeBeforeDays(int day) {
		Date currentDate = Calendar.getInstance().getTime();
		String time = sdf.format(getDateBefore(currentDate, day));
		return time;
	}
	
	/**
	 * 获取几天前日期
	 * yyyy-mm-dd
	 * @param day
	 * @return
	 */
	public static String getDateBeforeDays(int day) {
		Date currentDate = Calendar.getInstance().getTime();
		String time = df.format(getDateBefore(currentDate, day));
		return time;
	}

	/**
	 * 得到几天前的日期
	 *
	 * @param d
	 * @param day
	 * @return Date
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的日期
	 * 
	 * @param d
	 * @param day
	 * @return Date
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
	
	/**
	 * 得到几天后的时间
	 * @param day
	 * @return yyyy-mm-dd hh:mm:ss
	 */
	public static String getTimeAfterDays(int day) {
		Date currentDate = Calendar.getInstance().getTime();
		String time = sdf.format(getDateBefore(currentDate, day));
		return time;
	}

	/**
	 * 得到几天后的日期
	 * @param day
	 * @return yyyy-mm-dd
	 */
	public static String getDateAfterDays(int day) {
		Date currentDate = Calendar.getInstance().getTime();
		String time = df.format(getDateBefore(currentDate, day));
		return time;
	}
	
	/**
	 * 返回当前时间的时间戳
	 * @return
	 */
	public static Long getCurrentTimestamp(){
		return new Date().getTime();
	}
}

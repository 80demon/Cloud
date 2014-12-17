// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DateUtil.java

package com.feng.common.util;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{

	private static String hours[] = {
		"", "1Сʱ", "2Сʱ", "3Сʱ", "4Сʱ", "5Сʱ", "6Сʱ", "7Сʱ", "8Сʱ", "9Сʱ", 
		"10Сʱ", "11Сʱ", "12Сʱ", "13Сʱ", "14Сʱ", "15Сʱ", "16Сʱ", "17Сʱ", "18Сʱ", "19Сʱ", 
		"20Сʱ", "21Сʱ", "22Сʱ", "23Сʱ"
	};
	private static String mins[] = {
		"", "1����", "2����", "3����", "4����", "5����", "6����", "7����", "8����", "9����", 
		"10����", "11����", "12����", "13����", "14����", "15����", "16����", "17����", "18����", "19����", 
		"20����", "21����", "22����", "23����", "24����", "25����", "26����", "27����", "28����", "29����", 
		"30����", "31����", "32����", "33����", "34����", "35����", "36����", "37����", "38����", "39����", 
		"40����", "41����", "42����", "43����", "44����", "45����", "46����", "47����", "48����", "49����", 
		"50����", "51����", "52����", "53����", "54����", "55����", "56����", "57����", "58����", "59����"
	};
	private static String secs[] = {
		"", "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", 
		"10��", "11��", "12��", "13��", "14��", "15��", "16��", "17��", "18��", "19��", 
		"20��", "21��", "22��", "23��", "24��", "25��", "26��", "27��", "28��", "29��", 
		"30��", "31��", "32��", "33��", "34��", "35��", "36��", "37��", "38��", "39��", 
		"40��", "41��", "42��", "43��", "44��", "45��", "46��", "47��", "48��", "49��", 
		"50��", "51��", "52��", "53��", "54��", "55��", "56��", "57��", "58��", "59��"
	};
	private static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
	private static final String DATE_FORMAT_MMDD = "MM-dd";
	private static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT_YYMMDDHHMMSS = "yyMMddHHmmss";
	private static int DAY = 0x5265c00;
	private static int HOUR = 0x36ee80;
	private static int MIN = 60000;
	private static int SEC = 1000;

	public DateUtil()
	{
	}

	public static String dateFormat(Date date)
	{
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String formatCur = dateFormatter.format(date);
		return formatCur;
	}

	public static String dateFormatMMDD(Date date)
	{
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd");
		String formatCur = dateFormatter.format(date);
		return formatCur;
	}

	public static String dateFormat(String date)
		throws ParseException
	{
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFormat = dateFormatter.parse(date);
		String formatCur = dateFormatter.format(dateFormat);
		return formatCur;
	}

	public static String dateFormatHHMMSS(Date date)
	{
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (date == null)
			return "";
		else
			return dateFormatter.format(date);
	}

	public static String timestampFormatyyMMddHHmmss(Date date)
	{
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyMMddHHmmss");
		if (date == null)
			return "";
		else
			return dateFormatter.format(date);
	}

	public static String dateFormatHHMMSS(String date)
		throws ParseException
	{
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateFormat = dateFormatter.parse(date);
		String formatCur = dateFormatter.format(dateFormat);
		return formatCur;
	}

	public static long DateDiff(Date startDate, Date endDate)
	{
		long diff = endDate.getTime() - startDate.getTime();
		return diff / 0x5265c00L;
	}

	public static java.sql.Date DateAddDays(java.sql.Date date, int days)
	{
		Calendar temp = Calendar.getInstance();
		temp.setTimeInMillis(date.getTime());
		temp.add(5, days);
		return new java.sql.Date(temp.getTimeInMillis());
	}

	public static java.sql.Date getCurrentDateTime()
	{
		Date date = new Date();
		return new java.sql.Date(date.getTime());
	}

	public static String getTimeStr(long diff)
	{
		long day = 0L;
		long hour = 0L;
		long min = 0L;
		long sec = 0L;
		day = diff / (long)DAY;
		hour = (diff % (long)DAY) / (long)HOUR;
		min = (diff % (long)HOUR) / (long)MIN;
		sec = (diff % (long)MIN) / (long)SEC;
		return (new StringBuilder(String.valueOf(day <= 0L ? "" : ((Object) ((new StringBuilder(String.valueOf(day))).append("��").toString()))))).append(hours[(int)hour]).append(mins[(int)min]).append(secs[(int)sec]).toString();
	}

	public static String getLimitTimeStr(long diff)
	{
		long day = 0L;
		long hour = 0L;
		long min = 0L;
		long sec = 0L;
		long time = diff - System.currentTimeMillis();
		String ret = "";
		if (time < 0L)
		{
			ret = "�ѹ�ȥ";
			time *= -1L;
		} else
		if (time > 0L)
			ret = "����";
		day = time / (long)DAY;
		hour = (time % (long)DAY) / (long)HOUR;
		min = (time % (long)HOUR) / (long)MIN;
		sec = (time % (long)MIN) / (long)SEC;
		return (new StringBuilder(String.valueOf(ret))).append(day <= 0L ? "" : (new StringBuilder(String.valueOf(day))).append("��").toString()).append(hours[(int)hour]).append(mins[(int)min]).append(secs[(int)sec]).toString();
	}

	public static String dateFormat(Date date, String format)
	{
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static void main(String args[])
	{
		System.out.println(getLimitTimeStr(System.currentTimeMillis() - 0x5f5e100L));
	}

}

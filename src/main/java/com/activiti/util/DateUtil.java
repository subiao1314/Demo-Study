package com.activiti.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

	/**
	 * 格式化时间 MM-dd
	 * @param String date
	 * @return String date
	 */
	public static String getMMDD(String date) {
		return new SimpleDateFormat("MM-dd").format(date);
	}

	/**
	 * 格式化时间 MM-dd
	 * @param Date date
	 * @return String date
	 */
	public static String formatMMDD(Date date){
		return new SimpleDateFormat("MM-dd").format(date);
	}
	
	/**
	 * 格式化GMT时间 EEE, dd MMM yyyy HH:mm:ss z
	 * @param Date date
	 * @return String date
	 */
	public static String getGMTDateString(Date date){
		DateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return httpDateFormat.format(date);
	}
	/**
	 * mongodb中的时间生成的Date转化为的字符串
	 * 再转为Date
	 * @param timestamp mongodb中的时间生成的Date转化为的字符串
	 * @return Date
	 */
	public static Date fromCSTDateString(String timestamp){
		DateFormat format = new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy",Locale.ENGLISH);
		try {
			return format.parse(timestamp);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 格式化ISO时间 yyyy-MM-dd'T'HH:mm:ss
	 * @param Date date
	 * @return String date
	 */
	public static String getISODateString(Date date) {
		DateFormat httpDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'");
		return httpDateFormat.format(date);
	}
	
	/**
	 * 格式化时间 yyyyMMddHHmmssSSS
	 * @param Date date
	 * @return String date
	 */
	public static String getYYYMMddHHmmssSSS(Date date){
	    return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
	}
	
	/**
	 * 格式化时间 yyyyMMddHHmmssSSS
	 * @param String date
	 * @return Date date
	 */
	public static Date parseYYYMMddHHmmssSSS(String date) throws ParseException {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").parse(date);
    }
	
	/**
	 * 格式化时间 yyyyMMddHHmmss
	 * @param Date date
	 * @return String date
	 */
	public static String getYYYYMMddHHmmss(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}

	/**
	 * 格式化时间 yyyyMMddHHmm
	 * @param Date date
	 * @return String date
	 */
	public static String getYYYYMMddHHmm(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmm").format(date);
	}

	/**
	 * 格式化时间 yyyyMMddHHmm
	 * @param String date
	 * @return Date date
	 */
	public static Date parseYYYYMMddHHmm(String date) throws ParseException {
		return new SimpleDateFormat("yyyyMMddHHmm").parse(date);
	}
	
	/**
	 * 格式化时间 yyyyMMddHHmmss
	 * @param String date
	 * @return Date date
	 */
	public static Date parseYYYYMMddHHmmss(String date) throws ParseException {
		return new SimpleDateFormat("yyyyMMddHHmmss").parse(date);
	}
	
	/**
	 * 格式化时间 yyyyMMddHH
	 * @param String date
	 * @return Date date
	 */
	public static Date parseYYYYMMddHH(String date) throws ParseException {
		return new SimpleDateFormat("yyyyMMddHH").parse(date);
	}

	/**
	 * 格式化时间 yyyyMMdd
	 * @param Date date
	 * @return String date
	 */
	public static String getYYYYMMdd(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * 格式化时间 yyyyMMdd
	 * @param String date
	 * @return Date date
	 */
	public static Date parseYYYYMMdd(String dateString) throws ParseException {
		return new SimpleDateFormat("yyyyMMdd").parse(dateString);
	}

	/**
	 * 格式化时间 yyyy-MM-dd HH:mm:ss
	 * @param Date date
	 * @return String date
	 */
	public static String formatFullTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 格式化时间 yyyy-MM-dd HH:mm:ss
	 * @param long date
	 * @return String date
	 */
	public static String formatFullTime(long date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 格式化时间 yyyy-MM-dd HH:mm
	 * @param Date date
	 * @return String date
	 */
	public static String minuteTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}
	
	/**
	 * 格式化时间 yyyy-MM-dd HH:mm
	 * @param long date
	 * @return String date
	 */
	public static String minuteTime(long datetime) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(datetime);
	}

	/**
	 * 格式化时间 yyyy-MM-dd HH:mm
	 * @param String date
	 * @return Date date
	 */
	public static Date parseMinuteTime(String fullTimeString) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(fullTimeString);
	}

	/**
	 * 格式化时间 yyyy-MM-dd HH:mm:ss
	 * @param String date
	 * @return Date date
	 */
	public static Date parseFullTime(String fullTimeString) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fullTimeString);
	}

	/**
	 * 格式化时间 yyyy-MM-dd
	 * @param Date date
	 * @return String date
	 */
	public static String formatCommonFormat(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * 格式化时间 yyyy-MM-dd
	 * @param String date
	 * @return Date date
	 */
	public static Date parsecommonFormat(String commonDateString) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(commonDateString);
	}

	/**
	 * 格式化时间 yyMMddHHmm
	 * @param Date date
	 * @return String date
	 */
	public static String getYYMMddHHmm(Date date) {
		return new SimpleDateFormat("yyMMddHHmm").format(date);
	}

	/**
	 * 格式化时间 HHmm
	 * @param Date date
	 * @return String date
	 */
	public static String getHHmm(Date date) {
		return new SimpleDateFormat("HHmm").format(date);
	}
	
	/**
	 * 格式化时间 HH:mm
	 * @param String date
	 * @return Date date
	 */
	public static Date parseTimeformat(String timeFormatString) throws ParseException {
		return new SimpleDateFormat("HH:mm").parse(timeFormatString);
	}
	
	/**
	 * 格式化时间 yyMMddHHmm
	 * @param String date
	 * @return Date date
	 */
	public static Date parseYYMMddHHmm(String dateString) {
		try {
			return new SimpleDateFormat("yyMMddHHmm").parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	/**
	 * 格式化时间MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatMMddHHmmss(long date){
		return new SimpleDateFormat("MM-dd HH:mm:ss").format(date);
	}
	
	/**
	 * 格式化时间MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatMMddHHmmss(Date date){
		return new SimpleDateFormat("MM-dd HH:mm:ss").format(date);
	}
	/**
	 * 格式化时间HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatHHmmss(Date date){
		return new SimpleDateFormat("HH:mm:ss").format(date);
	}
	
	/**
     * 
     * @param date 日期
     * @param formatSymbols 日期符号构造  例如yyyyMMdd
     * @return 返回指定日期的字符串
     */
    public static String formate(Date date, String formatSymbols){
        return new SimpleDateFormat(formatSymbols).format(date);
    }
	
	/**
	 * 根据接收的字符串获取起始查询日期
	 * @param date 起始日期 yyyy-MM-dd
	 * @return Date 起始查询日期时分秒为0
	 * @throws ParseException
	 */
	public static Date getBeginDate(String date) throws ParseException{
			Date beginDate = DateUtil.parsecommonFormat(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE,0);
			cal.set(Calendar.SECOND, 0);
			return cal.getTime();
	}
	
	/**
	 * 根据接收的字符串获取起始查询日期
	 * @param date 起始日期 yyyy-MM-dd
	 * @return Date 起始查询日期时分秒为0
	 * @throws ParseException
	 */
	public static Date getBeginDate(Date date) throws ParseException{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE,0);
			cal.set(Calendar.SECOND, 0);
			return cal.getTime();
	}
	
	/**
     * 根据接收的字符串获取起始查询日期
     * @param date 起始日期 yyyy-MM-dd
     * @return Date 起始查询日期时分秒为0
     * @throws ParseException
     */
    public static Date getDate(Date date, int hourOfDay, int minite, int second){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE,minite);
        cal.set(Calendar.SECOND, minite);
        return cal.getTime();
    }
	
	/**
	 * 根据接收的字符串获取结束查询日期
	 * @param date 起始日期 yyyy-MM-dd
	 * @return Date 起始查询日期时分秒为0
	 * @throws ParseException
	 */
	public static Date getEndDate(String date) throws ParseException{
			Date endDate = DateUtil.parsecommonFormat(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE,0);
			cal.set(Calendar.SECOND, 0);
			return cal.getTime();
	}
	
	/**
	 * 根据接收的字符串获取结束查询日期
	 * @param date 起始日期 yyyy-MM-dd
	 * @return Date 起始查询日期时分秒为0
	 * @throws ParseException
	 */
	public static Date getEndDate(Date date) throws ParseException{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE,0);
			cal.set(Calendar.SECOND, 0);
			return cal.getTime();
	}
	
	/**
	 * 得到给定日期的月初的日子
	 * @param date 给定的日期
	 * @return Date 月初的时间
	 */
	public static Date getBeginMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	/**
	 * 得到查询的起始日期,根据参数得到几天前的查询起始日期
	 * @param date 当前时刻
	 * @param num 要得到几天前
	 * @return
	 */
	public static Date getBeginDateByParam(Date date,int num){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)-num);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 得到查询的起始日期,根据参数得到几天后的查询起始日期
	 * @param date 当前时刻
	 * @param num 要得到几天前
	 * @return
	 */
	public static Date getEndDateByParam(Date date,int num){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, num);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 指定时间与当前系统相隔时间的描述，如3分钟前，30秒钟前
	 * @param date 指定时间
	 * @return 返回中文描述字符串
	 */
	public static String since(Date date){
	    if(date == null) return "";
        Date now = new Date();
        if(now.before(date)){
            return "";
        }
        long delta = (now.getTime() - date.getTime()) / 1000;
        if(delta <= 60){
            if(delta > 10){
                delta = delta - 5;
            }else{
                if(delta > 5){
                    delta = delta - 3;
                }                
            }
            return (int)delta + "秒钟前";
        }
        if (delta <= 60 * 60) {
            long minutes = delta / 60;
            if(minutes > 10){
                minutes = minutes - 3;
            }else{
                if(minutes > 5){
                    minutes = minutes - 2;
                }else if(minutes > 2){
                    minutes = minutes - 1;
                }
            }
            return (int)minutes + "分钟前";
        }
        if (delta <= 24 * 60 * 60) {
            long hours = delta / (60 * 60);
            return (int)hours + "小时前";
        }

        if (delta < 7 * 24 * 60 * 60) {
            long days = delta / (24 * 60 * 60);
            return (int)days + "天前";
        }
        
        if (7 * 24 * 60 * 60 < delta && delta < 30 * 24 * 60 * 60) {
            long weekends = delta / (7 * 24 * 60 * 60);
            return (int)weekends + "周前";
        }

        if (30 * 24 * 60 * 60 < delta && delta < 180 * 24 * 60 * 60) {
            long months = delta / (30 * 24 * 60 * 60);
            return (int)months + "月前";
        }
        
        if (180 * 24 * 60 * 60 < delta && delta < 365 * 24 * 60 * 60) {
            return "半年前";
        }

        long years = delta / (365 * 24 * 60 * 60);
        return (int)years+"年前";
    }
	
	
	/**
	 * 根据月数向后推算
	 * @param months 月数
	 * @return Date
	 */
	public static Date parseToDate(Date date, int months){
	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
	    if(months > 0){
	        if(months < 12){
	            calendar.add(Calendar.MONTH, months);
	        }else{
	            int years = months / 12;
	            months = months % 12;
	            calendar.add(Calendar.YEAR, years);
	            if(months != 0) calendar.add(Calendar.MONTH, months);
	        }
	    }else{
	        if(months >= -1){
                calendar.add(Calendar.MONTH, months);
            }else{
                int years = months / 12;
                months = months % 12;
                calendar.add(Calendar.YEAR, years);
                if(months != 0) calendar.add(Calendar.MONTH, months);
            }
	    }
	    return calendar.getTime();
	}
	
	/**
	 * 计算某个时间距离现在还有几天
	 * @param date yyyy-MM-dd
	 * @return int天数
	 */
	public static int countdays(String date){
	    try {
            Date end = parsecommonFormat(date);
            Date today = new Date();
            today = parsecommonFormat(formatCommonFormat(today));
            long time = end.getTime() - today.getTime();
            return (int)(time/(1000*60*60*24));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
	}
	/**
	 * 解析一个字符串日期变成查询的Date日期
	 * @param dateStr 日期字符串 yyyy-MM-dd
	 * @param isBegin 时间的起始 还是 末尾  true起始,false末尾
	 * @return Date 日期对象
	 */
	public static Date getQueryDate(Date date,boolean isBegin){
		if(date==null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if(isBegin){
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
		}else{
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
		}
		return cal.getTime();
	}
	/**
	 * 得到几天前的日期
	 * @param days 天数
	 * @return Date
	 */
	public static Date getBeforeDate(int days){
		if(days <= 0) return null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)-days);
		return cal.getTime();
	}
	
	/**
	 * 获取日期年代
	 * @param date
	 * @return
	 */
	public static int getYear(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * yyyy年代字符串转为时间类型
	 * @param year 年代
	 * @return Date
	 */
	public static Date parseYYYYTime(String year){
		try {
			return new SimpleDateFormat("yyyy").parse(year);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	/**
	 * 按天计算时间
	 * @param amount 天数
	 * @return Date
	 */
	public static Date afterTimeByDate(int amount){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}
	
	/**
	 * 按月计算时间
	 * @param amount 月数
	 * @return Date
	 */
	public static Date afterTimeByMonth(int amount){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, amount);
		return cal.getTime();
	}
	
	/**
	 * 按年计算时间
	 * @param amount 年数
	 * @return Date
	 */
	public static Date afterTimeByYear(int amount){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, amount);
		return cal.getTime();
	}
}

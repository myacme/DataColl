package com.bonc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/14
 * Time:14:43
 * todo:时间工具类
 */
public class TimeUtil {
	/***
	 * todo:获取当前时间
	 * @return
	 */
	public static String getCurrentTime() {
		// 小写的hh取得12小时，大写的HH取的是24小时
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return df.format(date);
	}

	/***
	 * todo:获取版本号
	 * @return
	 */
	public static String getVersion() {
		final String DATE_FORMAT = "yyyyMMdd";
		final String TIME_FORMAT = "HHmmss";
		Date date = new Date();
		SimpleDateFormat df1 = new SimpleDateFormat(DATE_FORMAT);
		SimpleDateFormat df2 = new SimpleDateFormat(TIME_FORMAT);
		String d = df1.format(date);
		String d2 = df2.format(date);
		return "v" + d + "_" + d2;
	}

	public static String getCode() {
		final String DATE_FORMAT = "yyyyMMddHHmmss";
		Date date = new Date();
		SimpleDateFormat df1 = new SimpleDateFormat(DATE_FORMAT);
		String d = df1.format(date);
		return d;
	}

	public static void main(String[] args) {
		System.out.println(TimeUtil.getCode());
	}
}

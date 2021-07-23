package com.bonc.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 〈公共工具类〉
 *
 * @author ljx
 * @create 2021/7/12 16:36
 * @since 1.0.0
 */

public class CommonUtil {


	/**
	 * 生产版本编号
	 * @return
	 */
	public static String getVersionCode() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		// 日期时间转字符串
		LocalDateTime now = LocalDateTime.now();
		String nowText = now.format(formatter);
		return "a" + nowText;
	}

	/**
	 * 生产版本编号
	 * @return
	 */
	public static String getNowDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		// 日期时间转字符串
		LocalDateTime now = LocalDateTime.now();
		String nowText = now.format(formatter);
		return nowText;
	}

	/**
	 * 使用UUID生成主键
	 * @return
	 */
	public static String getUUID32() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

	public static void main(String[] args) {
		System.out.println(getVersionCode());
		System.out.println(getUUID32());
	}
}
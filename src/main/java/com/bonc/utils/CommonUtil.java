package com.bonc.utils;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	 *
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
	 * @return
	 */
	public static String getNowTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		// 日期时间转字符串
		return LocalDateTime.now().format(formatter);
	}

	/**
	 * 使用UUID生成主键
	 *
	 * @return
	 */
	public static String getUUID20() {
		StringBuilder code = new StringBuilder(20);
		for (int i = 0; i < 7; i++) {
			char c = (char) (int) (Math.random() * 26 + 'a');
			code.append(c);
		}
		code.append(Instant.now().toEpochMilli());
		return code.toString();
	}

	public static void main(String[] args) {
		System.out.println(getVersionCode());
		for (int i = 0; i < 10; i++) {
			System.out.println(getUUID20());
		}

	}
}
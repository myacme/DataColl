package com.bonc.utils;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


	/**
	 * 验证手机号
	 *
	 * @param value
	 * @return
	 */
	public static boolean verifyPhone(String value) {
		String regex = "^[1][3,4,5,7,8][0-9]{9}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		boolean bool = matcher.matches();
		return bool;
	}

	/**
	 * 验证身份证
	 *
	 * @param IDNumber
	 * @return
	 */
	public static boolean verifyIDCard(String IDNumber) {
		if (IDNumber == null || "".equals(IDNumber)) {
			return false;
		}
		// 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
		String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
				"(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
		boolean matches = IDNumber.matches(regularExpression);
		//判断第18位校验值
		if (matches) {
			if (IDNumber.length() == 18) {
				try {
					char[] charArray = IDNumber.toCharArray();
					//前十七位加权因子
					int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
					//这是除以11后，可能产生的11位余数对应的验证码
					String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
					int sum = 0;
					for (int i = 0; i < idCardWi.length; i++) {
						int current = Integer.parseInt(String.valueOf(charArray[i]));
						int count = current * idCardWi[i];
						sum += count;
					}
					char idCardLast = charArray[17];
					int idCardMod = sum % 11;
					if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
						return true;
					} else {
						return false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return matches;
	}

	public static void main(String[] args) {
		System.out.println(getVersionCode());
		for (int i = 0; i < 10; i++) {
			System.out.println(getUUID20());
		}
	}
}
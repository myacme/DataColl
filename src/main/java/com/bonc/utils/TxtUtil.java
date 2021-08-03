package com.bonc.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 〈txt文件工具〉
 *
 * @author ljx
 * @create 2021/7/13 9:22
 * @since 1.0.0
 */

public class TxtUtil {

	/**
	 * 读取txt 返回字符串
	 *
	 * @param txt 文件
	 * @return String
	 */
	public static String readTxtToString(File txt) {
		StringBuilder content = new StringBuilder();
		byte[] b = new byte[1024];
		try (FileInputStream fis = new FileInputStream(txt)) {
			while (fis.read(b) != -1) {
				content.append(new String(b));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}


	/**
	 * 字符串写入到txt
	 *
	 * @param content  内容
	 * @param fileName 文件
	 * @return File
	 */
	public static File writrTxt(String content, String fileName) {
		File txt = new File(ZipUtil.getProjectPath() + fileName + ".txt");
		try (FileOutputStream fos = new FileOutputStream(txt)) {
			fos.write(content.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txt;
	}
}
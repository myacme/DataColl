package com.bonc.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Zip工具类
 * 〈〉
 *
 * @author ljx
 * @create 2021/7/9
 * @since 1.0.0
 */

public class ZipUtil {

	public static void main(String[] args) {
		try {
			System.out.println(getProjectPath());
//			File file = new File("D:\\OneDrive\\Desktop\\proxy1\\background.js");
//			File file1 = new File("D:\\OneDrive\\Desktop\\proxy1\\manifest.json");
//			ArrayList<File> files = new ArrayList<>();
//			files.add(file1);
//			files.add(file);
//			zipTodir(files, "1234", "123");
//			zip("D:\\OneDrive\\Desktop\\proxy1", "D:\\OneDrive\\Desktop\\aa1.zip", "123");
//			File[] unzip = unzip("D:\\OneDrive\\Desktop\\aa1.zip", "123");
//			for (File file : unzip) {
//				System.out.println(file.getName());
//			}
		} catch (Exception e) {
			throw new RuntimeException("", e);
		}
		System.out.println("成功");
	}

	/**
	 * 默认编码为GBK
	 */
	private static final String DEFAULT_ENCODING = "GBK";

	/**
	 * @param file zip压缩文件
	 * @return void
	 *
	 * @Author
	 * @brief 检测file是否为zip压缩包
	 */
	public static boolean isZipFile(File file) {
		boolean flag = false;
		if (file.exists() && file.getName().endsWith(".zip")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * @param file     zip压缩包
	 * @param destDir  解压缩目录
	 * @param encoding 编码
	 * @return boolean
	 *
	 * @Author
	 * @brief 解压缩zip
	 */
	public static boolean unzip(File file, String destDir, String... encoding) {
		if (!file.exists()) {
			throw new IllegalArgumentException("请检查文件" + file.getName() + "是否存在");
		}
		File destFile = new File(destDir);
		if (!destFile.exists()) {
			destFile.mkdir();
		}
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(file);
			String encodingStr = DEFAULT_ENCODING;
			if (encoding.length > 0) {
				encodingStr = encoding[0];
			}
			zipFile.setFileNameCharset(encodingStr);
			zipFile.extractAll(destDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


	/**
	 * 使用给定密码解压指定的ZIP压缩文件到指定目录
	 * <p>
	 * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
	 *
	 * @param zip    指定的ZIP压缩文件
	 * @param dest   解压目录
	 * @param passwd ZIP文件的密码
	 * @return 解压后文件数组
	 *
	 * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
	 * @Author
	 */
	public static void unzip(String zip, String dest, String passwd) {
		if (dest == null) {
			File zipFile = new File(zip);
			File parentDir = zipFile.getParentFile();
			unzip(zipFile, parentDir.getAbsolutePath(), passwd);
		}
		File zipFile = new File(zip);
		unzip(zipFile, dest, passwd);
	}

	/**
	 * 使用给定密码解压指定的ZIP压缩文件到指定目录
	 * <p>
	 * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
	 *
	 * @param zipFile 指定的ZIP压缩文件
	 * @param dest    解压目录
	 * @param passwd  ZIP文件的密码
	 * @return 解压后文件数组
	 *
	 * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
	 * @Author
	 */
	public static void unzip(File zipFile, String dest, String passwd) {
		try {
			ZipFile zFile = new ZipFile(zipFile);
			zFile.setFileNameCharset(DEFAULT_ENCODING);
			if (!zFile.isValidZipFile()) {
				throw new ZipException("压缩文件不合法,可能被损坏.");
			}
			File destDir = new File(dest);
			if (destDir.isDirectory() && !destDir.exists()) {
				destDir.mkdir();
			}
			if (zFile.isEncrypted()) {
				zFile.setPassword(passwd.toCharArray());
			}
			zFile.extractAll(dest);
			List<FileHeader> headerList = zFile.getFileHeaders();
			List<File> extractedFileList = new ArrayList<File>();
			for (FileHeader fileHeader : headerList) {
				if (!fileHeader.isDirectory()) {
					new File(destDir, fileHeader.getFileName());
				}
			}
		} catch (ZipException e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * 使用给定密码解压指定的ZIP压缩文件返回文件
	 *
	 * @param zipFile 指定的ZIP压缩文件
	 * @param passwd  ZIP文件的密码
	 * @return 解压后文件数组
	 *
	 * @Author
	 */
	public static File[] unzip(File zipFile, String passwd) {
		File[] extractedFiles = new File[0];
		try {
			ZipFile zFile = new ZipFile(zipFile);
			zFile.setFileNameCharset(DEFAULT_ENCODING);
			if (!zFile.isValidZipFile()) {
				throw new ZipException("压缩文件不合法,可能被损坏.");
			}
			if (zFile.isEncrypted()) {
				zFile.setPassword(passwd.toCharArray());
			}
			zFile.extractAll(getProjectPath());
			List<FileHeader> headerList = zFile.getFileHeaders();
			List<File> extractedFileList = new ArrayList<File>();
			for (FileHeader fileHeader : headerList) {
				if (!fileHeader.isDirectory()) {
					System.out.println(fileHeader.getFileName() + "kang");
					extractedFileList.add(new File(getProjectPath() + fileHeader.getFileName()));
				}
			}
			extractedFiles = new File[extractedFileList.size()];
			extractedFileList.toArray(extractedFiles);
		} catch (ZipException e) {
			e.printStackTrace();
		} finally {
		}
		return extractedFiles;
	}

	/**
	 * 使用给定密码解压指定的ZIP压缩文件到指定目录
	 * <p>
	 * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
	 *
	 * @param zipFile 指定的ZIP压缩文件
	 * @param dest    解压目录
	 * @param passwd  ZIP文件的密码
	 * @return 解压后文件数组
	 *
	 * @Author
	 */
	public static File[] unzip(File zipFile, String dest, String passwd,
	                           String... encoding) {
		File[] extractedFiles = new File[0];
		try {
			ZipFile zFile = new ZipFile(zipFile);
			String encodingStr = DEFAULT_ENCODING;
			if (encoding.length > 0) {
				encodingStr = encoding[0];
			}
			zFile.setFileNameCharset(encodingStr);
			if (!zFile.isValidZipFile()) {
				throw new ZipException("压缩文件不合法,可能被损坏.");
			}
			File destDir = new File(dest);
			if (destDir.isDirectory() && !destDir.exists()) {
				destDir.mkdir();
			}
			if (zFile.isEncrypted()) {
				zFile.setPassword(passwd.toCharArray());
			}
			zFile.extractAll(dest);
			List<FileHeader> headerList = zFile.getFileHeaders();
			List<File> extractedFileList = new ArrayList<File>();
			for (FileHeader fileHeader : headerList) {
				if (!fileHeader.isDirectory()) {
					extractedFileList.add(new File(destDir, fileHeader
							.getFileName()));
				}
			}
			extractedFiles = new File[extractedFileList.size()];
			extractedFileList.toArray(extractedFiles);
		} catch (ZipException e) {
			e.printStackTrace();
		} finally {
		}
		return extractedFiles;
	}

	/**
	 * 使用给定密码压缩指定文件或文件夹到当前目录
	 *
	 * @param src    要压缩的文件
	 * @param passwd 压缩使用的密码
	 * @return 最终的压缩文件存放的绝对路径, 如果为null则说明压缩失败.
	 *
	 * @Author
	 */
	public static String zipTodir(String src, String passwd) {
		return zipTodir(src, null, passwd);
	}

	/**
	 * 使用给定密码压缩指定文件或文件夹到当前目录
	 *
	 * @param src    要压缩的文件
	 * @param dest   压缩文件存放路径
	 * @param passwd 压缩使用的密码
	 * @return 最终的压缩文件存放的绝对路径, 如果为null则说明压缩失败.
	 *
	 * @Author
	 */
	public static String zipTodir(String src, String dest, String passwd) {
		return zipTodir(src, dest, true, passwd);
	}

	/**
	 * 构建压缩文件存放路径,如果不存在将会创建
	 * 传入的可能是文件名或者目录,也可能不传,此方法用以转换最终压缩文件的存放路径
	 *
	 * @param srcFile   源文件
	 * @param destParam 压缩目标路径
	 * @return 正确的压缩文件存放路径
	 *
	 * @Author
	 */
	private static String buildDestinationZipFilePath(File srcFile, String destParam) {
		if (StringUtils.isEmpty(destParam)) {
			if (srcFile.isDirectory()) {
				destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
			} else {
				String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				destParam = srcFile.getParent() + File.separator + fileName + ".zip";
			}
		} else {
			createDestDirectoryIfNecessary(destParam);  // 在指定路径不存在的情况下将其创建出来
			if (destParam.endsWith(File.separator)) {
				String fileName = "";
				if (srcFile.isDirectory()) {
					fileName = srcFile.getName();
				} else {
					fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				}
				destParam += fileName + ".zip";
			}
		}
		return destParam;
	}

	/**
	 * 使用给定密码压缩指定文件或文件夹到指定位置.
	 * <p>
	 * dest可传最终压缩文件存放的绝对路径,也可以传存放目录,也可以传null或者"".<br />
	 * 如果传null或者""则将压缩文件存放在当前目录,即跟源文件同目录,压缩文件名取源文件名,以.zip为后缀;<br />
	 * 如果以路径分隔符(File.separator)结尾,则视为目录,压缩文件名取源文件名,以.zip为后缀,否则视为文件名.
	 *
	 * @param src         要压缩的文件或文件夹路径
	 * @param dest        压缩文件存放路径
	 * @param isCreateDir 是否在压缩文件里创建目录,仅在压缩文件为目录时有效.<br />
	 *                    如果为false,将直接压缩目录下文件到压缩文件.
	 * @param passwd      压缩使用的密码
	 * @return 最终的压缩文件存放的绝对路径, 如果为null则说明压缩失败.
	 *
	 * @Author
	 */
	public static String zipTodir(String src, String dest, boolean isCreateDir, String passwd) {
		File srcFile = new File(src);
		dest = buildDestinationZipFilePath(srcFile, dest);
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);           // 压缩方式
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);    // 压缩级别
		if (!StringUtils.isEmpty(passwd)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
			parameters.setPassword(passwd.toCharArray());
		}
		try {
			ZipFile zipFile = new ZipFile(dest);
			if (srcFile.isDirectory()) {
				// 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
				if (!isCreateDir) {
					File[] subFiles = srcFile.listFiles();
					if (subFiles != null && subFiles.length > 0) {
						for (File f : subFiles) {
							if (f.isDirectory()) {
								zipFile.addFolder(f, parameters);
							} else {
								zipFile.addFile(f, parameters);
							}
						}
					}
					return dest;
				}
				zipFile.addFolder(srcFile, parameters);
			} else {
				zipFile.addFile(srcFile, parameters);
			}
		} catch (ZipException e) {
			e.printStackTrace();
		} finally {
		}
		return dest;
	}

	/**
	 * 使用给定密码压缩指定文件或文件夹到工程目录
	 *
	 * @param files    要压缩的文件
	 * @param fileName 文件名称
	 * @param passwd   压缩使用的密码
	 * @return 最终的压缩文件存放的绝对路径, 如果为null则说明压缩失败.
	 *
	 * @Author
	 */
	public static String zipTodir(ArrayList<File> files, String fileName, String passwd) {
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);           // 压缩方式
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);    // 压缩级别
		if (!StringUtils.isEmpty(passwd)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
			parameters.setPassword(passwd.toCharArray());
		}
		String path = null;
		if (getProjectPath() != null) {
			path = getProjectPath() + "\\" + fileName + ".zip";
			try {
				ZipFile zipFile = new ZipFile(path);
				for (File file : files) {
					if (file.isDirectory()) {
						zipFile.addFolder(file, parameters);
					} else {
						zipFile.addFile(file, parameters);
					}
				}
			} catch (ZipException e) {
				e.printStackTrace();
			} finally {
			}
		}
		return path;
	}

	/**
	 * 使用给定密码压缩指定文件或文件夹并下载.
	 *
	 * @param response 要压缩的文件或文件夹路径
	 * @param files    要压缩的文件或文件夹路径
	 * @param password 压缩使用的密码
	 * @Author
	 */
	public static void zipDownload(HttpServletResponse response, ArrayList<File> files,
	                               String fileName, String password) {
		ZipOutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8"));
			outputStream = new ZipOutputStream(response.getOutputStream());
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			parameters.setPassword(password);
			for (File f : files) {
				outputStream.putNextEntry(f, parameters);
				inputStream = new FileInputStream(f);
				byte[] readBuff = new byte[4096];
				int readLen = -1;
				while ((readLen = inputStream.read(readBuff)) != -1) {
					outputStream.write(readBuff, 0, readLen);
				}
				outputStream.closeEntry();
				inputStream.close();
			}
			outputStream.finish();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 在必要的情况下创建压缩文件存放目录,比如指定的存放路径并没有被创建
	 *
	 * @param destParam 指定的存放路径,有可能该路径并没有被创建
	 * @Author
	 */
	private static void createDestDirectoryIfNecessary(String destParam) {
		File destDir = null;
		if (destParam.endsWith(File.separator)) {
			destDir = new File(destParam);
		} else {
			destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
		}
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
	}

	/**
	 * 获取当前工程的父路径
	 *
	 * @return String
	 */
	public static String getProjectPath() {
//		String path = System.getProperty("user.dir");
//		return path.substring(0, path.lastIndexOf(File.separatorChar));
		String folder = System.getProperty("java.io.tmpdir");
		String canonicalPath = null;
		try {
			canonicalPath = new File("..").getCanonicalPath() + File.separator;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return canonicalPath;
	}
}

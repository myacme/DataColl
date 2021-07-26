package com.bonc.colldata.config;


import com.bonc.colldata.entity.SysConfig;
import com.bonc.colldata.mapper.baseData.SysConfigDao;

/**
 * 〈系统配置类〉
 *
 * @author ljx
 * @create 2021/7/26 17:27
 * @since 1.0.0
 */

public class SystemConfig {

	private static String zipPassWord = null;


	/**
	 * 获取压缩文件密码
	 * @return
	 */
	public static String getZipPassWord() {
		if (zipPassWord == null) {
			SysConfigDao sysConfigDao = SpringContextUtil.getBean(SysConfigDao.class);
			SysConfig bean = sysConfigDao.queryById("zip_password");
			zipPassWord = bean.getConfigValue();
		}
		return zipPassWord;
	}
}
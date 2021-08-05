package com.bonc.colldata.entity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/8/5
 * Time:11:32
 */
public enum TableFieldEnum {
	ID("id","数据唯一编码"),
	DATACODE("data_code","编码"),
	BUSINESSTYPECODE("business_type_code","业务编码"),
	TABLEBUSINESSCODED("table_business_code","表编码"),
	TABLECONFIGCODE("table_config_code","表字段配置编码"),
	VERSION("version","版本号"),
	DEPARTMENTCODE("department_code","部门编码"),
	DATAVALUE("data_value","数据"),
	CREATETIME("create_time","创建时间"),
	STATE("state","状态"),
	THISUPDATE("this_update","更新状态");
	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	TableFieldEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	/**
	 * 将枚举类转成map
	 */
	public static Map<String, String> payTypeMap = new LinkedHashMap<>();
	static {
		TableFieldEnum[] types = TableFieldEnum.values();
		for (TableFieldEnum type : types) {
			payTypeMap.put(String.valueOf(type.getCode()), type.getName()+"("+String.valueOf(type.getCode())+")");
		}
	}
}

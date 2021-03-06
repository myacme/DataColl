package com.bonc.colldata.entity;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 〈人员表字段配置〉
 *
 * @author MyAcme
 * @create 2021/8/4
 * @since 1.0.0
 */

public enum PersonEnum {
	ID("人员ID", "id"),
	SFH("身份号", "sfh"),
	XM("姓名", "xm"),
	BB("部别", "bb"),
	ZWMC("职务名称", "zwmc"),
	XB("性别", "xb"),
	JG("籍贯", "jg"),
	JGDM("籍贯代码", "jgdm"),
	ZZMM("政治面貌", "zzmm"),
	ZZMMMC("政治面貌名称", "zzmmmc"),
	CSSJ("出生时间", "cssj"),
	RWSJ("工作时间", "rwsj"),
	XLMC("学历名称", "xlmc"),
	XL("学历", "xl"),
	BYYX("毕业院校及专业", "byyx"),
	SZDWCJID("所在单位ID", "szdwcjid"),
	SFLB("身份类别","sflb"),
	SFLBMV("身份类别名称","sflbmc");
	public String name;
	public String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	PersonEnum(String name, String code) {
		this.code = code;
		this.name = name;
	}
	/**
	 * 将枚举类转成map
	 */
	public static Map<String,Object> payTypeMap = new LinkedHashMap<>();
	static {
		PersonEnum[] types = PersonEnum.values();
		for (PersonEnum type : types) {
			payTypeMap.put(String.valueOf(type.getCode()), type.getName()+"("+String.valueOf(type.getCode())+")");
		}
	}

	public static void main(String[] args) {
		System.out.println(PersonEnum.payTypeMap);
	}
}
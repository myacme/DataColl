package com.bonc.colldata.entity;


/**
 * 〈机构表字段配置〉
 *
 * @author MyAcme
 * @create 2021/8/4
 * @since 1.0.0
 */

public enum DepartEnum {
	ID("机构ID", "id"),
	JGMC("机构名称", "jgmc"),
	LEVEL("机构等级", "level"),
	JGDJMC("机构等级名称", "jgdjmc"),
	ZZJGMCM("组织机构名称码", "zzjgmcm"),
	DWFB("单位分布", "dwfb"),
	JGXZ("机构性质", "jgxz"),
	JGXZMC("机构性质名称", "jgxzmc"),
	QZQF("强制区分", "qzqf"),
	QZQFMC("强制区分名称", "qzqfmc"),
	PXM("排序码", "pxm"),
	FDWID("父单位ID", "fdwid"),
	DWQLJ("单位全路径", "dwqlj"),
	CCM("层次码", "ccm");
	public String name;
	public String code;
	DepartEnum(String name, String code) {
		this.code = code;
		this.name = name;
	}
}
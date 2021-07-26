package com.bonc.colldata.entity;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/26
 * Time:11:00
 * todo：人员基础数据维护
 */
public class QueryParam {
	private String code;
	private int type;
	private String value;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

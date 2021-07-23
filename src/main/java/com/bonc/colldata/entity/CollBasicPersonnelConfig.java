package com.bonc.colldata.entity;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/13
 * Time:15:09
 * todo:人员基本配置
 */
public class CollBasicPersonnelConfig {
	private String basicCode;
	private String personnelConfigName;
	private String personnelConfigValue;
	private String state;
	private String ifList;
	private String ifCheck;

	public String getIfList() {
		return ifList;
	}

	public void setIfList(String ifList) {
		this.ifList = ifList;
	}

	public String getIfCheck() {
		return ifCheck;
	}

	public void setIfCheck(String ifCheck) {
		this.ifCheck = ifCheck;
	}

	public String getBasicCode() {
		return basicCode;
	}

	public void setBasicCode(String basicCode) {
		this.basicCode = basicCode;
	}

	public String getPersonnelConfigName() {
		return personnelConfigName;
	}

	public void setPersonnelConfigName(String personnelConfigName) {
		this.personnelConfigName = personnelConfigName;
	}

	public String getPersonnelConfigValue() {
		return personnelConfigValue;
	}

	public void setPersonnelConfigValue(String personnelConfigValue) {
		this.personnelConfigValue = personnelConfigValue;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}

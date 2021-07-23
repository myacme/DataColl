package com.bonc.colldata.entity;

import java.io.Serializable;

/**
 * (CollBusinessTableType)实体类
 *
 * @author ljx
 * @since 2021-07-13 16:57:12
 */
public class CollBusinessTableType implements Serializable {
	private static final long serialVersionUID = 578197900628774021L;

	private String businessTypeTableCode;

	private String businessTypeCode;

	private String businessTypeTableName;

	private String businessTypeTableNameCode;

	private String remarks;

	private String state;


	public String getBusinessTypeTableCode() {
		return businessTypeTableCode;
	}

	public void setBusinessTypeTableCode(String businessTypeTableCode) {
		this.businessTypeTableCode = businessTypeTableCode;
	}

	public String getBusinessTypeCode() {
		return businessTypeCode;
	}

	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	public String getBusinessTypeTableName() {
		return businessTypeTableName;
	}

	public void setBusinessTypeTableName(String businessTypeTableName) {
		this.businessTypeTableName = businessTypeTableName;
	}

	public String getBusinessTypeTableNameCode() {
		return businessTypeTableNameCode;
	}

	public void setBusinessTypeTableNameCode(String businessTypeTableNameCode) {
		this.businessTypeTableNameCode = businessTypeTableNameCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
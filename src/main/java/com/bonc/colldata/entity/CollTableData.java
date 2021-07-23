package com.bonc.colldata.entity;

import java.io.Serializable;

/**
 * (CollTableData)实体类
 *
 * @author ljx
 * @since 2021-07-14 11:25:09
 */
public class CollTableData implements Serializable {
	private static final long serialVersionUID = 255412016495629627L;

	private String dataCode;

	private String businessTypeCode;

	private String tableBusinessCode;

	private String tableConfigCode;

	private String departmentCode;

	private String dataValue;

	private String createTime;

	private String version;

	private String tableConfigName;

	private String state;

	private String thisUpdate;

	private String lowerUpdate;


	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public String getBusinessTypeCode() {
		return businessTypeCode;
	}

	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	public String getTableBusinessCode() {
		return tableBusinessCode;
	}

	public void setTableBusinessCode(String tableBusinessCode) {
		this.tableBusinessCode = tableBusinessCode;
	}

	public String getTableConfigCode() {
		return tableConfigCode;
	}

	public void setTableConfigCode(String tableConfigCode) {
		this.tableConfigCode = tableConfigCode;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTableConfigName() {
		return tableConfigName;
	}

	public void setTableConfigName(String tableConfigName) {
		this.tableConfigName = tableConfigName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getThisUpdate() {
		return thisUpdate;
	}

	public void setThisUpdate(String thisUpdate) {
		this.thisUpdate = thisUpdate;
	}

	public String getLowerUpdate() {
		return lowerUpdate;
	}

	public void setLowerUpdate(String lowerUpdate) {
		this.lowerUpdate = lowerUpdate;
	}
}
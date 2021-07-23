package com.bonc.colldata.entity;

import java.io.Serializable;

/**
 * (CollDataDictType)实体类
 *
 * @author ljx
 * @since 2021-07-13 10:08:07
 */
public class CollDataDictType implements Serializable {
	private static final long serialVersionUID = -40115557917626590L;

	private String dataDictTypeCode;

	private String dataDictTypeName;

	private String remark;

	private String state;

	private String dictCode;


	public String getDataDictTypeCode() {
		return dataDictTypeCode;
	}

	public void setDataDictTypeCode(String dataDictTypeCode) {
		this.dataDictTypeCode = dataDictTypeCode;
	}

	public String getDataDictTypeName() {
		return dataDictTypeName;
	}

	public void setDataDictTypeName(String dataDictTypeName) {
		this.dataDictTypeName = dataDictTypeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
}
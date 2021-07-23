package com.bonc.colldata.entity;

import java.io.Serializable;

/**
 * (CollDataDictValue)实体类
 *
 * @author ljx
 * @since 2021-07-13 10:17:35
 */
public class CollDataDictValue implements Serializable {
	private static final long serialVersionUID = -15175272703529842L;

	private String codeId;

	private String codeType;

	private String codeName;

	private String codeValue;

	private String state;


	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
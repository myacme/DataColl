package com.bonc.colldata.entity;

import java.io.Serializable;

/**
 * (CollBusinessType)实体类
 *
 * @author ljx
 * @since 2021-07-13 16:29:17
 */
public class CollBusinessType implements Serializable {
	private static final long serialVersionUID = 229751048718075287L;

	private String businessCode;

	private String businessName;

	private String state;


	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
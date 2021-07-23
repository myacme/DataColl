package com.bonc.colldata.entity;

import java.util.List;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/13
 * Time:10:18
 */
public class CollDepartment {
	private String instiutionsId;
	private String instiutionsName;
	private String instiutionsContact;
	private String instiutionsPhone;
	private String isLeaf;
	private String parentCode;
	private String businessType;
	private String state;
	private List<CollDepartment> list;

	public String getInstiutionsId() {
		return instiutionsId;
	}

	public void setInstiutionsId(String instiutionsId) {
		this.instiutionsId = instiutionsId;
	}

	public String getInstiutionsName() {
		return instiutionsName;
	}

	public void setInstiutionsName(String instiutionsName) {
		this.instiutionsName = instiutionsName;
	}

	public String getInstiutionsContact() {
		return instiutionsContact;
	}

	public void setInstiutionsContact(String instiutionsContact) {
		this.instiutionsContact = instiutionsContact;
	}

	public String getInstiutionsPhone() {
		return instiutionsPhone;
	}

	public void setInstiutionsPhone(String instiutionsPhone) {
		this.instiutionsPhone = instiutionsPhone;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<CollDepartment> getList() {
		return list;
	}

	public void setList(List<CollDepartment> list) {
		this.list = list;
	}
}

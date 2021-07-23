package com.bonc.colldata.entity;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/19
 * Time:14:41
 * todo:用户角色
 */
public class UserRole {
	private String roleId;
	private String name;
	private String description;
	private String createTime;
	private String updateTime;
	private String state;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}

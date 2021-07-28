package com.bonc.colldata.entity;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/28
 * Time:15:35
 * todo：管理员新建下发任务
 */
public class CollTask {
	private String collTaskCode;
	private String collTaskName;
	private String collTaskVersion;
	private String collTaskType;
	private String createTime;

	public String getCollTaskType() {
		return collTaskType;
	}

	public void setCollTaskType(String collTaskType) {
		this.collTaskType = collTaskType;
	}

	public String getCollTaskCode() {
		return collTaskCode;
	}

	public void setCollTaskCode(String collTaskCode) {
		this.collTaskCode = collTaskCode;
	}

	public String getCollTaskName() {
		return collTaskName;
	}

	public void setCollTaskName(String collTaskName) {
		this.collTaskName = collTaskName;
	}

	public String getCollTaskVersion() {
		return collTaskVersion;
	}

	public void setCollTaskVersion(String collTaskVersion) {
		this.collTaskVersion = collTaskVersion;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}

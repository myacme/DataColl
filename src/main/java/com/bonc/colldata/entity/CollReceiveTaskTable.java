package com.bonc.colldata.entity;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/14
 * Time:9:48
 * todo:任务下发表清单关联
 */
public class CollReceiveTaskTable {

	private String sendTaskCode;
	private String sendTaskTableCode;
	private String sendTaskTableName;
	private String createTime;
	private String state;

	public String getSendTaskTableName() {
		return sendTaskTableName;
	}

	public void setSendTaskTableName(String sendTaskTableName) {
		this.sendTaskTableName = sendTaskTableName;
	}

	public String getSendTaskCode() {
		return sendTaskCode;
	}

	public void setSendTaskCode(String sendTaskCode) {
		this.sendTaskCode = sendTaskCode;
	}

	public String getSendTaskTableCode() {
		return sendTaskTableCode;
	}

	public void setSendTaskTableCode(String sendTaskTableCode) {
		this.sendTaskTableCode = sendTaskTableCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}

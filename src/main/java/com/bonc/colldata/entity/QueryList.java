package com.bonc.colldata.entity;

import java.util.List;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/26
 * Time:12:09
 */
public class QueryList {
	private int pageSize;
	private int pageNum;
	private String xm;
    private String szdwcjid;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getSzdwcjid() {
		return szdwcjid;
	}

	public void setSzdwcjid(String szdwcjid) {
		this.szdwcjid = szdwcjid;
	}
}

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
	private List<QueryParam> list;

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

	public List<QueryParam> getList() {
		return list;
	}

	public void setList(List<QueryParam> list) {
		this.list = list;
	}
}

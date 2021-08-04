package com.bonc.colldata.entity;

import java.util.List;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/8/4
 * Time:9:34
 * TODO:机构宽表
 */
public class JGKB {
	private String id;
	private String jgmc;
	private String level;
	private String jgdjmc;
	private String zzjgmcm;
	private String dwfb;
	private String jgxz;
	private String jgxzmc;
	private String qzqf;
	private String qzqfmc;
	private int pxm;
	private String fdwid;
	private String dwqlj;
	private String ccm;
    private List<JGKB> list;

	public List<JGKB> getList() {
		return list;
	}

	public void setList(List<JGKB> list) {
		this.list = list;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJgmc() {
		return jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getJgdjmc() {
		return jgdjmc;
	}

	public void setJgdjmc(String jgdjmc) {
		this.jgdjmc = jgdjmc;
	}

	public String getZzjgmcm() {
		return zzjgmcm;
	}

	public void setZzjgmcm(String zzjgmcm) {
		this.zzjgmcm = zzjgmcm;
	}

	public String getDwfb() {
		return dwfb;
	}

	public void setDwfb(String dwfb) {
		this.dwfb = dwfb;
	}

	public String getJgxz() {
		return jgxz;
	}

	public void setJgxz(String jgxz) {
		this.jgxz = jgxz;
	}

	public String getJgxzmc() {
		return jgxzmc;
	}

	public void setJgxzmc(String jgxzmc) {
		this.jgxzmc = jgxzmc;
	}

	public String getQzqf() {
		return qzqf;
	}

	public void setQzqf(String qzqf) {
		this.qzqf = qzqf;
	}

	public String getQzqfmc() {
		return qzqfmc;
	}

	public void setQzqfmc(String qzqfmc) {
		this.qzqfmc = qzqfmc;
	}

	public int getPxm() {
		return pxm;
	}

	public void setPxm(int pxm) {
		this.pxm = pxm;
	}

	public String getFdwid() {
		return fdwid;
	}

	public void setFdwid(String fdwid) {
		this.fdwid = fdwid;
	}

	public String getDwqlj() {
		return dwqlj;
	}

	public void setDwqlj(String dwqlj) {
		this.dwqlj = dwqlj;
	}

	public String getCcm() {
		return ccm;
	}

	public void setCcm(String ccm) {
		this.ccm = ccm;
	}
}

package com.entity;

import java.util.Date;
/**
 * 商品销售信息内容表
 * @author Goddard
 *
 */
public class SalesInfo {
	private long salesid;
	private int gid;
	private int snum;
	public SalesInfo() {
		super();
	}
	public SalesInfo(long salesid, int gid, int snum) {
		super();
		this.salesid = salesid;
		this.gid = gid;
		this.snum = snum;
	}
	public long getSalesid() {
		return salesid;
	}
	public void setSalesid(long salesid) {
		this.salesid = salesid;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getSnum() {
		return snum;
	}
	public void setSnum(int snum) {
		this.snum = snum;
	}
	public String toString() {
		return "SalesInfo [salesid=" + salesid + ", gid=" + gid + ", snum=" + snum + "]";
	}
	
	
}

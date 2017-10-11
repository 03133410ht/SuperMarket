package com.entity;

import java.sql.Date;

/**
 * 销售记录表
 * @author Goddard
 *
 */


public class Sales {
	private long salesid;
	private int eid;
	private int mid;
	private Date sdate;
	private double mgathering;
	private String remark;
	public long getSalesid() {
		return salesid;
	}
	public void setSalesid(long salesid) {
		this.salesid = salesid;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public double getMgathering() {
		return mgathering;
	}
	public void setMgathering(double mgathering) {
		this.mgathering = mgathering;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Sales(long salesid, int eid, int mid, Date sdate, double mgathering, String remark) {
		super();
		this.salesid = salesid;
		this.eid = eid;
		this.mid = mid;
		this.sdate = sdate;
		this.mgathering = mgathering;
		this.remark = remark;
	}
	public String toString() {
		return "Sales [salesid=" + salesid + ", eid=" + eid + ", mid=" + mid + ", sdate=" + sdate + ", mgathering="
				+ mgathering +", remark=" + remark + "]";
	}
	public Sales() {
		super();
	}
	
}

package com.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 对应数据库PURCHASE_INFO表,一张才各表中可以多个商品
 * @author wuhong
 *
 */
public class PurchaseInfo {
	private int pid;
	private int gid;
	private int pnum;
	
	public PurchaseInfo() {
	}
	public PurchaseInfo(int pid, int gid, int pnum) {
		this.pid = pid;
		this.gid = gid;
		this.pnum = pnum;
	}
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	@Override
	public String toString() {
		return "PurchaseInfo [pid=" + pid + ", gid=" + gid + ", pnum=" + pnum + "]";
	}
	
}

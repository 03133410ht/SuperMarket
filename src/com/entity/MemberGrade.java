package com.entity;

/**
 *会员等级表
 * @author Goddard
 *
 */
public class MemberGrade {
	private String mgrade;
	private double mdiscount;
	private double minicharge;
	public MemberGrade() {
		super();
	}
	public MemberGrade(String mgrade, double mdiscount, double minicharge) {
		super();
		this.mgrade = mgrade;
		this.mdiscount = mdiscount;
		this.minicharge = minicharge;
	}
	public String getMgrade() {
		return mgrade;
	}
	public void setMgrade(String mgrade) {
		this.mgrade = mgrade;
	}
	public double getMdiscount() {
		return mdiscount;
	}
	public void setMdiscount(double mdiscount) {
		this.mdiscount = mdiscount;
	}
	public double getMinicharge() {
		return minicharge;
	}
	public void setMinicharge(double minicharge) {
		this.minicharge = minicharge;
	}
	@Override
	public String toString() {
		return "MemberGrade [mgrade=" + mgrade + ", mdiscount=" + mdiscount + ", minicharge=" + minicharge + "]";
	}
	
}

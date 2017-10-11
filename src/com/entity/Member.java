package com.entity;


/**
 * 会员实体类
 * @author Goddard
 *
 */
public class Member {
	private int mid;
	private String mname;
	private String msex;
	private String mgrade;
	private double mmonetary;
	private long mtel;
	private double maccount;
	private String mpassword;
	public Member() {
		super();
	}
	
	public Member(int mid, String mpassword) {
		super();
		this.mid = mid;
		this.mpassword = mpassword;
	}

	public Member(int mid, String mname, String msex, String mgrade, double mmonetary, long mtel, double maccount,
			String mpassword) {
		super();
		this.mid = mid;
		this.mname = mname;
		this.msex = msex;
		this.mgrade = mgrade;
		this.mmonetary = mmonetary;
		this.mtel = mtel;
		this.maccount = maccount;
		this.mpassword = mpassword;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getMsex() {
		return msex;
	}
	public void setMsex(String msex) {
		this.msex = msex;
	}
	public String getMgrade() {
		return mgrade;
	}
	public void setMgrade(String mgrade) {
		this.mgrade = mgrade;
	}
	public double getMmonetary() {
		return mmonetary;
	}
	public void setMmonetary(double mmonetary) {
		this.mmonetary = mmonetary;
	}
	public long getMtel() {
		return mtel;
	}
	public void setMtel(long mtel) {
		this.mtel = mtel;
	}
	public double getMaccount() {
		return maccount;
	}
	public void setMaccount(double maccount) {
		this.maccount = maccount;
	}
	public String getMpassword() {
		return mpassword;
	}
	public void setMpassword(String mpassword) {
		this.mpassword = mpassword;
	}
	public String toString() {
		return "Member [mid=" + mid + ", mname=" + mname + ", msex=" + msex + ", mgrade=" + mgrade + ", mmonetary="
				+ mmonetary + ", mtel=" + mtel + ", maccount=" + maccount + ", mpassword=" + mpassword + "]";
	}
	
	
}

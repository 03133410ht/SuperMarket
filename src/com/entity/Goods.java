package com.entity;
/**
 * 商品实体类
 * @author Goddard
 *
 */
public class Goods {
	private int gid;
	private String gname;
	private double gprice;
	private double gcost_price;
	private int gstock;
	private double gdiscount;
	private String gcategory;
	private int gpoint;
	private String gunit;
	private String gremark;
	
	
	
	public Goods() {
		super();
	}

	
	public Goods(int gid, String gname, double gprice, double gcost_price, int gstock, double gdiscount,
			String gcategory, int gpoint, String gunit, String gremark) {
		super();
		this.gid = gid;
		this.gname = gname;
		this.gprice = gprice;
		this.gcost_price = gcost_price;
		this.gstock = gstock;
		this.gdiscount = gdiscount;
		this.gcategory = gcategory;
		this.gpoint = gpoint;
		this.gunit = gunit;
		this.gremark = gremark;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public double getGprice() {
		return gprice;
	}

	public void setGprice(double gprice) {
		this.gprice = gprice;
	}

	public double getGcost_price() {
		return gcost_price;
	}

	public void setGcost_price(double gcost_price) {
		this.gcost_price = gcost_price;
	}

	public int getGstock() {
		return gstock;
	}

	public void setGstock(int gstock) {
		this.gstock = gstock;
	}

	public double getGdiscount() {
		return gdiscount;
	}

	public void setGdiscount(double gdiscount) {
		this.gdiscount = gdiscount;
	}

	public String getGcategory() {
		return gcategory;
	}

	public void setGcategory(String gcategory) {
		this.gcategory = gcategory;
	}

	public int getGpoint() {
		return gpoint;
	}

	public void setGpoint(int gpoint) {
		this.gpoint = gpoint;
	}

	public String getGunit() {
		return gunit;
	}

	public void setGunit(String gunit) {
		this.gunit = gunit;
	}

	public String getGremark() {
		return gremark;
	}

	public void setGremark(String gremark) {
		this.gremark = gremark;
	}

	public String toString() {
		return "Goods [gid=" + gid + ", gname=" + gname + ", gprice=" + gprice + ", gcost=" + gcost_price
				+ ", gstock=" + gstock + ", gdiscount=" + gdiscount + ", gcategory=" + gcategory + ", gpoints="
				+ gpoint + ", gunit=" + gunit + ", gremark=" + gremark + "]";
	}
	
	
}

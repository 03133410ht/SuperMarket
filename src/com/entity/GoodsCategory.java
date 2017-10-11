package com.entity;

/**
 * 商品种类 
 * @author Goddard
 *
 */
public class GoodsCategory {
	private String gcategory;

	public GoodsCategory(String gcategory) {
		super();
		this.gcategory = gcategory;
	}

	public GoodsCategory() {
		super();
	}

	public String getGcategory() {
		return gcategory;
	}

	public void setGcategory(String gcategory) {
		this.gcategory = gcategory;
	}

	public String toString() {
		return "GoodsCategory [gcategory=" + gcategory + "]";
	}
	
}

package com.entity;


/**
 * 员工实体表
 * @author Goddard
 *
 */
public class Employee {
	private int eid;
	private String ename;
	private String esex;
	private String eadress;
	private long etel;
	private String email;
	public Employee() {
		super();
	}
	public Employee(int eid, String ename, String esex, String eadress, long etel, String email) {
		super();
		this.eid = eid;
		this.ename = ename;
		this.esex = esex;
		this.eadress = eadress;
		this.etel = etel;
		this.email = email;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getEsex() {
		return esex;
	}
	public void setEsex(String esex) {
		this.esex = esex;
	}
	public String getEadress() {
		return eadress;
	}
	public void setEadress(String eadress) {
		this.eadress = eadress;
	}
	public long getEtel() {
		return etel;
	}
	public void setEtel(long etel) {
		this.etel = etel;
	}
	public String getEmail() {
		return email;
	}
	public void setEamil(String email) {
		this.email = email;
	}
	public String toString() {
		return "Employee [eid=" + eid + ", ename=" + ename + ", esex=" + esex + ", eadress=" + eadress + ", etel="
				+ etel + ", email=" + email + "]";
	}
	
}

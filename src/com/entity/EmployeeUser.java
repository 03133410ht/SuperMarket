package com.entity;

/**
 * 员工会员混合表
 * @author Goddard
 *
 */
public class EmployeeUser {
	private int eid;
	private String ename;
	private String esex;
	private String eadress;
	private long etel;
	private String email;
	private int sid;	//用户id
	private String username;	//帐号
	private String password;	//密码
	private String usertype;	//用户类型
	
	
	public EmployeeUser(int eid, String ename, String esex, String eadress, long etel, String email, String username,
			String password, String usertype) {
		this.eid = eid;
		this.ename = ename;
		this.esex = esex;
		this.eadress = eadress;
		this.etel = etel;
		this.email = email;
		this.username = username;
		this.password = password;
		this.usertype = usertype;
	}
	public EmployeeUser(int eid, String ename, String esex, String eadress, long etel, String email, int sid,
			String username, String password, String usertype) {
		super();
		this.eid = eid;
		this.ename = ename;
		this.esex = esex;
		this.eadress = eadress;
		this.etel = etel;
		this.email = email;
		this.sid = sid;
		this.username = username;
		this.password = password;
		this.usertype = usertype;
	}
	public EmployeeUser() {
		super();
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
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	@Override
	public String toString() {
		return "EmployeeUser [eid=" + eid + ", ename=" + ename + ", esex=" + esex + ", eadress=" + eadress + ", etel="
				+ etel + ", email=" + email + ", sid=" + sid + ", username=" + username + ", password=" + password
				+ ", usertype=" + usertype + "]";
	}
	
	
	
}

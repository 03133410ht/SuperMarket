package com.entity;
/**
 * 用户实体类
 * @author Goddard
 *
 */
public class User {
	private int sid;	//用户id
	private int eid;	//员工id
	private String username;	//帐号
	private String password;	//密码
	private String usertype;	//用户类型
	
	
	public User(String username, String password, String usertype) {
		super();
		this.username = username;
		this.password = password;
		this.usertype = usertype;
	}

	public User() {
	}
	
	public User(int sid, int eid, String username, String password, String usertype) {
		super();
		this.sid = sid;
		this.eid = eid;
		this.username = username;
		this.password = password;
		this.usertype = usertype;
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
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
		return "User [sid=" + sid + ", eid=" + eid + ", username=" + username + ", password=" + password + ", usertype="
				+ usertype + "]";
	}
	
	
	
}

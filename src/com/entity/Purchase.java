package com.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * �ɹ���Ϣ�࣬��Ӧ�ɹ���Ϣ��
 * @author wuhong
 *
 */
public class Purchase {
	private int pid;//�ɹ�����
	private Date pdate;//�ɹ�����
	private int did;//��Ʒ��Ӧ��id
	private int sid;//����Աid
	private String remark;//��ע
	private List<PurchaseInfo> purchaseInfolist=new ArrayList<PurchaseInfo>();
	
	public Purchase() {
	}
	
	public Purchase(int pid, Date pdate, int did, int sid, String remark,List<PurchaseInfo> purchaseInfolist) {
		this.pid = pid;
		this.pdate = pdate;
		this.did = did;
		this.sid = sid;
		this.remark = remark;
		this.purchaseInfolist=purchaseInfolist;
	}

	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<PurchaseInfo> getPurchaseInfolist() {
		return purchaseInfolist;
	}
	public void setPurchaseInfolist(List<PurchaseInfo> purchaseInfolist) {
		this.purchaseInfolist = purchaseInfolist;
	}

	@Override
	public String toString() {
		return "Purchase [pid=" + pid + ", pdate=" + pdate + ", did=" + did + ", sid=" + sid + ", remark=" + remark
				+ ", purchaseInfolist=" + purchaseInfolist + "]";
	}


	
}

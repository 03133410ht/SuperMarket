package com.entity;

/**
 * 供应商实体类
 * @author wuhong
 *
 */
public class Distributor {
	private int did;
	private String dname;
	private String dtel;
	private String demail;
	
	public Distributor() {
	}
	
	public Distributor(int did, String dname, String dtel, String demail) {
		this.did = did;
		this.dname = dname;
		this.dtel = dtel;
		this.demail = demail;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getDtel() {
		return dtel;
	}

	public void setDtel(String dtel) {
		this.dtel = dtel;
	}

	public String getDemail() {
		return demail;
	}

	public void setDemail(String demail) {
		this.demail = demail;
	}

	@Override
	public String toString() {
		return "Distributor [did=" + did + ", dname=" + dname + ", dtel=" + dtel + ", demail=" + demail + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((demail == null) ? 0 : demail.hashCode());
		result = prime * result + did;
		result = prime * result + ((dname == null) ? 0 : dname.hashCode());
		result = prime * result + ((dtel == null) ? 0 : dtel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Distributor other = (Distributor) obj;
		if (demail == null) {
			if (other.demail != null)
				return false;
		} else if (!demail.equals(other.demail))
			return false;
		if (did != other.did)
			return false;
		if (dname == null) {
			if (other.dname != null)
				return false;
		} else if (!dname.equals(other.dname))
			return false;
		if (dtel == null) {
			if (other.dtel != null)
				return false;
		} else if (!dtel.equals(other.dtel))
			return false;
		return true;
	}



}

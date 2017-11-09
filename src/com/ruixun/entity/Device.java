package com.ruixun.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class Device extends IdEntity {

	private String name;
	private int status;//是否上锁
	private String createtime;
	private String updatetime;
	private String remark;
//	private int fromaccountid;
	
	@Basic
	@Column(name="remark")
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "updatetime")
	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	@Basic
	@Column(name = "createtime")
	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createdate) {
		this.createtime = createdate;
	}

//	@Basic
//	@Column(name = "fromaccountid")
//	public int getFromaccountid() {
//		return fromaccountid;
//	}

//	public void setFromaccountid(int fromaccountid) {
//		this.fromaccountid = fromaccountid;
//	}

	@Basic
	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	//
	// @Basic
	// @Column(name = "remark")
	// public String getRemark() {
	// return remark;
	// }
	//
	// public void setRemark(String remark) {
	// this.remark = remark;
	// }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		// result = prime * result + ((descrip == null) ? 0 :
		// descrip.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Device other = (Device) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (createtime == null) {
			if (other.createtime != null)
				return false;
		} else if (!createtime.equals(other.createtime))
			return false;
		return true;
	}

}

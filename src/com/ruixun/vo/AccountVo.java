package com.ruixun.vo;

/**
 * 
 * @author qinchi
 *
 */
public class AccountVo {

	private int id;
	private String name;
	private String pwd;
	private int status;
	private String createtime;
	private String updatetime;
	// private String remark;
	private int deviceid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public int getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}

	@Override
	public String toString() {
		return "account=[id=" + id + ",name=" + name + ",date=" + createtime + ",deviceId=" + deviceid;
	}

}

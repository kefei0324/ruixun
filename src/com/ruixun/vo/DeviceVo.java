package com.ruixun.vo;

/**
 * device VO
 * 
 * @author feona
 *
 */
public class DeviceVo {

	private int id;
	private String name;
	private String createtime;
	private String updatetime;
	private int status;
	private String remark;
	
	public String getRemark(){
		return remark;
	}
	
	public void setRemark(String remark){
		this.remark=remark;
	}
	
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}


}

package com.ruixun.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "msgmodel")
public class MsgModel extends IdEntity {
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private String data5;
	private String data6;
	private String data7;
	private String data8;
	private String data9;
	private String data10;
	private String data11;
	private boolean data1f;
	private boolean data2f;
	private boolean data3f;
	private boolean data4f;
	private boolean data5f;
	private boolean data6f;
	private boolean data7f;
	private boolean data8f;
	private boolean data9f;
	private boolean data10f;
	private boolean data11f;
	private float data1sc;
	private float data2sc;
	private float data3sc;
	private float data4sc;
	private float data5sc;
	private float data6sc;
	private float data7sc;
	private float data8sc;
	private float data9sc;
	private float data10sc;
	private float data11sc;
	private float total;
	private String data1s;
	private String data2s;
	private String data3s;
	private String data4s;
	private String data5s;
	private String data6s;
	private String data7s;
	private String data8s;
	private String data9s;
	private String data10s;
	private String data11s;
	private int msgid;
	private String createtime;
	private String updatetime;
	private int status;
	private String devicename;
	private int deviceid;
	private int accountid;

	@Basic
	@Column(name = "data1")
	public String getData1() {
		return data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	@Basic
	@Column(name = "data2")
	public String getData2() {
		return data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	@Basic
	@Column(name = "data3")
	public String getData3() {
		return data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	@Basic
	@Column(name = "data4")
	public String getData4() {
		return data4;
	}

	public void setData4(String data4) {
		this.data4 = data4;
	}

	@Basic
	@Column(name = "data5")
	public String getData5() {
		return data5;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}

	@Basic
	@Column(name = "data6")
	public String getData6() {
		return data6;
	}

	public void setData6(String data6) {
		this.data6 = data6;
	}

	@Basic
	@Column(name = "data7")
	public String getData7() {
		return data7;
	}

	public void setData7(String data7) {
		this.data7 = data7;
	}

	@Basic
	@Column(name = "data8")
	public String getData8() {
		return data8;
	}

	public void setData8(String data8) {
		this.data8 = data8;
	}

	@Basic
	@Column(name = "data9")
	public String getData9() {
		return data9;
	}

	public void setData9(String data9) {
		this.data9 = data9;
	}

	@Basic
	@Column(name = "data10")
	public String getData10() {
		return data10;
	}

	public void setData10(String data10) {
		this.data10 = data10;
	}

	@Basic
	@Column(name = "data11")
	public String getData11() {
		return data11;
	}

	public void setData11(String data11) {
		this.data11 = data11;
	}
	
	@Basic
	@Column(name = "createtime")
	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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
	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Basic
	@Column(name = "devicename")
	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	@Basic
	@Column(name = "deviceid")
	public int getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}

	@Basic
	@Column(name = "accountid")
	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	@Basic
	@Column(name = "msgid")
	public int getMsgid() {
		return msgid;
	}

	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}

	@Basic
	@Column(name = "data1f")
	public boolean isData1f() {
		return data1f;
	}

	public void setData1f(boolean data1) {
		this.data1f = data1;
	}

	@Basic
	@Column(name = "data2f")
	public boolean isData2f() {
		return data2f;
	}

	public void setData2f(boolean data1) {
		this.data2f = data1;
	}

	@Basic
	@Column(name = "data3f")
	public boolean isData3f() {
		return data3f;
	}

	public void setData3f(boolean data1) {
		this.data3f = data1;
	}

	@Basic
	@Column(name = "data4f")
	public boolean isData4f() {
		return data4f;
	}

	public void setData4f(boolean data1) {
		this.data4f = data1;
	}

	@Basic
	@Column(name = "data5f")
	public boolean isData5f() {
		return data5f;
	}

	public void setData5f(boolean data1) {
		this.data5f = data1;
	}

	@Basic
	@Column(name = "data6f")
	public boolean isData6f() {
		return data6f;
	}

	public void setData6f(boolean data1) {
		this.data6f = data1;
	}

	@Basic
	@Column(name = "data7f")
	public boolean isData7f() {
		return data7f;
	}

	public void setData7f(boolean data1) {
		this.data7f = data1;
	}

	@Basic
	@Column(name = "data8f")
	public boolean isData8f() {
		return data8f;
	}

	public void setData8f(boolean data1) {
		this.data8f = data1;
	}

	@Basic
	@Column(name = "data9f")
	public boolean isData9f() {
		return data9f;
	}

	public void setData9f(boolean data1) {
		this.data9f = data1;
	}

	@Basic
	@Column(name = "data10f")
	public boolean isData10f() {
		return data10f;
	}

	public void setData10f(boolean data1) {
		this.data10f = data1;
	}

	@Basic
	@Column(name = "data11f")
	public boolean isData11f() {
		return data11f;
	}

	public void setData11f(boolean data1) {
		this.data11f = data1;
	}

	@Basic
	@Column(name = "total")
	public float getTotal() {
		return total;
	}

	public void setTotal(float data1) {
		this.total = data1;
	}

	@Basic
	@Column(name = "data1sc")
	public float getData1sc() {
		return data1sc;
	}

	public void setData1sc(float data1) {
		this.data1sc = data1;
	}

	@Basic
	@Column(name = "data2sc")
	public float getData2sc() {
		return data2sc;
	}

	public void setData2sc(float data1) {
		this.data2sc = data1;
	}

	@Basic
	@Column(name = "data3sc")
	public float getData3sc() {
		return data3sc;
	}

	public void setData3sc(float data1) {
		this.data3sc = data1;
	}

	@Basic
	@Column(name = "data4sc")
	public float getData4sc() {
		return data4sc;
	}

	public void setData4sc(float data1) {
		this.data4sc = data1;
	}

	@Basic
	@Column(name = "data5sc")
	public float getData5sc() {
		return data5sc;
	}

	public void setData5sc(float data1) {
		this.data5sc = data1;
	}

	@Basic
	@Column(name = "data6sc")
	public float getData6sc() {
		return data6sc;
	}

	public void setData6sc(float data1) {
		this.data6sc = data1;
	}

	@Basic
	@Column(name = "data7sc")
	public float getData7sc() {
		return data7sc;
	}

	public void setData7sc(float data1) {
		this.data7sc = data1;
	}

	@Basic
	@Column(name = "data8sc")
	public float getData8sc() {
		return data8sc;
	}

	public void setData8sc(float data1) {
		this.data8sc = data1;
	}

	@Basic
	@Column(name = "data9sc")
	public float getData9sc() {
		return data9sc;
	}

	public void setData9sc(float data1) {
		this.data9sc = data1;
	}

	@Basic
	@Column(name = "data10sc")
	public float getData10sc() {
		return data10sc;
	}

	public void setData10sc(float data1) {
		this.data10sc = data1;
	}

	@Basic
	@Column(name = "data11sc")
	public float getData11sc() {
		return data11sc;
	}

	public void setData11sc(float data1) {
		this.data11sc = data1;
	}

	@Basic
	@Column(name = "data1s")
	public String getData1s() {
		return data1s;
	}

	public void setData1s(String data1) {
		this.data1s = data1;
	}

	@Basic
	@Column(name = "data2s")
	public String getData2s() {
		return data2s;
	}

	public void setData2s(String data1) {
		this.data2s = data1;
	}

	@Basic
	@Column(name = "data3s")
	public String getData3s() {
		return data3s;
	}

	public void setData3s(String data1) {
		this.data3s = data1;
	}

	@Basic
	@Column(name = "data4s")
	public String getData4s() {
		return data4s;
	}

	public void setData4s(String data1) {
		this.data4s = data1;
	}

	@Basic
	@Column(name = "data5s")
	public String getData5s() {
		return data5s;
	}

	public void setData5s(String data1) {
		this.data5s = data1;
	}

	@Basic
	@Column(name = "data6s")
	public String getData6s() {
		return data6s;
	}

	public void setData6s(String data1) {
		this.data6s = data1;
	}

	@Basic
	@Column(name = "data7s")
	public String getData7s() {
		return data7s;
	}

	public void setData7s(String data1) {
		this.data7s = data1;
	}

	@Basic
	@Column(name = "data8s")
	public String getData8s() {
		return data8s;
	}

	public void setData8s(String data1) {
		this.data8s = data1;
	}

	@Basic
	@Column(name = "data9s")
	public String getData9s() {
		return data9s;
	}

	public void setData9s(String data1) {
		this.data9s = data1;
	}

	@Basic
	@Column(name = "data10s")
	public String getData10s() {
		return data10s;
	}

	public void setData10s(String data1) {
		this.data10s = data1;
	}

	@Basic
	@Column(name = "data11s")
	public String getData11s() {
		return data11s;
	}

	public void setData11s(String data1) {
		this.data11s = data1;
	}

}

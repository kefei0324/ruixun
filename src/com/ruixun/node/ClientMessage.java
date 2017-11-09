/*
 *Copyright 2014 DDPush
 *Author: AndyKwok(in English) GuoZhengzhu(in Chinese)
 *Email: ddpush@126.com
 *

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/
package com.ruixun.node;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

import org.springframework.util.StringUtils;

import com.ruixun.entity.Msg;
import com.ruixun.entity.MsgModel;
import com.ruixun.utils.DateTimeUtil;
import com.ruixun.utils.StringUtil;

public final class ClientMessage {

	protected SocketAddress address;// 单片机地址
	protected byte[] data;
	private String dataStr;

	public ClientMessage(SocketAddress address, byte[] data) throws Exception {
		this.address = address;
		this.data = data;
		this.dataStr = new String(data);
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return this.data;
	}

	public SocketAddress getSocketAddress() {
		return this.address;
	}

	public void setSocketAddress(SocketAddress addr) {
		this.address = addr;
	}

	public int getVersionNum() {
		byte b = data[0];
		return b & 0xff;
	}

	public int getCmd() {
		byte b = data[1];
		return b & 0xff;
	}

	public int getDataLength() {
		return (int) ByteBuffer.wrap(data, 19, 2).getChar();
	}

	public String getUuidHexString() {
		return StringUtil.convert(data, 3, 16);
	}

	public boolean checkFormat() {
		if (this.data == null) {
			return false;
		}
		if (data.length < Constant.CLIENT_MESSAGE_MIN_LENGTH) {
			return false;
		}
		if (getVersionNum() != Constant.VERSION_NUM) {
			return false;
		}

		int cmd = getCmd();
		if (cmd != ClientStatMachine.CMD_0x00
				// && cmd != ClientStatMachine.CMD_0x01
				&& cmd != ClientStatMachine.CMD_0x10 && cmd != ClientStatMachine.CMD_0x11
				&& cmd != ClientStatMachine.CMD_0x20 && cmd != ClientStatMachine.CMD_0xff) {
			return false;
		}
		int dataLen = getDataLength();
		if (data.length != dataLen + Constant.CLIENT_MESSAGE_MIN_LENGTH) {
			return false;
		}

		if (cmd == ClientStatMachine.CMD_0x00 && dataLen != 0) {
			return false;
		}

		if (cmd == ClientStatMachine.CMD_0x10 && dataLen != 0) {
			return false;
		}

		if (cmd == ClientStatMachine.CMD_0x11 && dataLen != 8) {
			return false;
		}

		if (cmd == ClientStatMachine.CMD_0x20 && dataLen != 0) {
			return false;
		}

		return true;
	}

	/**
	 * 检查数据格式 终端单片机
	 * 
	 * @return
	 */
	public boolean checkFormatDevice() {
		if (this.data == null) {
			return false;
		}
		if (this.data.length != 34) {
			return false;
		}
		if (StringUtils.isEmpty(dataStr)) {
			return false;
		}
		if (!checkFrameTopAndTail()) {
			return false;
		}
		// 校验和
		// TODO
		return true;
	}

	/**
	 * 校验和
	 * 
	 * @return
	 */
	public int getDataLengthDevice() {
		// FIXME
		return (int) ByteBuffer.wrap(data, 30, 1).getChar();
	}

	/**
	 * 检查帧头帧尾
	 * 
	 * @return
	 */
	private boolean checkFrameTopAndTail() {
		return dataStr.startsWith("{") && dataStr.endsWith("}");
	}

	public String getDeviceName() {
		return dataStr.substring(1, 7);
	}

	public Msg createMsg(int i, Integer integer, String devicename) {
		Msg msg = new Msg();
		msg.setAccountid(i);
		msg.setDeviceid(integer);
		msg.setDevicename(devicename);
		// Date now = new Date();
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");// 可以方便地修改日期格式
		// String d = dateFormat.format(now);
		String date = DateTimeUtil.getCurDateTime();
		msg.setCreatetime(date);
		msg.setData1(dataStr.substring(7, 10));
		msg.setData2(dataStr.substring(10, 11));
		msg.setData3(dataStr.substring(11, 13));
		msg.setData4(dataStr.substring(13, 15));
		msg.setData5(dataStr.substring(15, 20));
		msg.setData6(dataStr.substring(20, 22));
		msg.setData7(dataStr.substring(22, 24));
		msg.setData8(dataStr.substring(24, 26));
		msg.setData9(dataStr.substring(26, 28));
		msg.setData10(dataStr.substring(28, 30));
		msg.setData11(dataStr.substring(30, 32));
		return msg;
	}

	private static final float NORMAL_SC = 9.9F;

	public MsgModel createMsgModel(Msg msg) {
		MsgModel vo = new MsgModel();
		String date = DateTimeUtil.getCurDateTime();
		vo.setCreatetime(date);
		String data1 = msg.getData1();
		vo.setData1(data1);
		String data2 = msg.getData2();
		vo.setData2(data2);
		String data3 = msg.getData3();
		vo.setData3(data3);
		String data4 = msg.getData4();
		vo.setData4(data4);
		String data5 = msg.getData5();
		vo.setData5(data5);
		String data6 = msg.getData6();
		vo.setData6(data6);
		String data7 = msg.getData7();
		vo.setData7(data7);
		String data8 = msg.getData8();
		vo.setData8(data8);
		String data9 = msg.getData9();
		vo.setData9(data9);
		String data10 = msg.getData10();
		vo.setData10(data10);
		String data11 = msg.getData11();
		vo.setData11(data11);
		float data_1 = Float.parseFloat(data1);
		if (data_1 >= 5.0 && data_1 < 7.5) {
			vo.setData1f(true);
			vo.setData1s("中性体质");
			vo.setData1sc(NORMAL_SC);
		} else {
			vo.setData1f(false);
			vo.setData1s(data_1 < 5.0 ? "酸性体质" : "碱性体质");
			vo.setData1sc(6.36f);
		}

		if ("- ".equals(data2)) {
			vo.setData2f(true);
			vo.setData2s("正常");
			vo.setData2sc(NORMAL_SC);
		} else {
			vo.setData2f(false);
			vo.setData2s("可能存在泌尿系统感染");
			vo.setData2sc(0f);
		}

		if ("-".equals(data3) || "±".equals(data3)) {
			vo.setData3f(true);
			vo.setData3s("正常");
			vo.setData3sc(NORMAL_SC);
		} else {
			vo.setData3f(false);
			vo.setData3s("尿葡萄糖值偏高");
			if ("+1".equals(data3)) {
				vo.setData3sc(6.82f);
			}
			if ("+2".equals(data3)) {
				vo.setData3sc(4.55f);
			}
			if ("+3".equals(data3)) {
				vo.setData3sc(2.27f);
			}
			if ("+4".equals(data3)) {
				vo.setData3sc(0f);
			}
		}
		if ("- ".equals(data4) || "±".equals(data4)) {
			vo.setData4f(true);
			vo.setData4s("正常");
			vo.setData4sc(NORMAL_SC);
		} else {
			vo.setData4f(false);
			vo.setData4s("维生素值偏高");
			if ("+1".equals(data4)) {
				vo.setData4sc(6.82f);
			}
			if ("+2".equals(data4)) {
				vo.setData4sc(4.55f);
			}
			if ("+3".equals(data4)) {
				vo.setData4sc(2.27f);
			}
			if ("+4".equals(data4)) {
				vo.setData4sc(0f);
			}
		}
		float data_5 = Float.parseFloat(data5);
		if (data_5 <= 1.015) {
			vo.setData5f(true);
			vo.setData5s("正常");
			vo.setData5sc(NORMAL_SC);
		} else {
			vo.setData5f(false);
			vo.setData5s("尿比重偏高");
			vo.setData5sc(data_5 > 1.025 ? 0f : 6.36f);
		}

		if ("- ".equals(data6)) {
			vo.setData6f(true);
			vo.setData6s("正常");
			vo.setData6sc(NORMAL_SC);
		} else {
			vo.setData6f(false);
			vo.setData6s("尿中有隐血");
			if ("±".equals(data6)) {
				vo.setData6sc(6.82f);
			}
			if ("+1".equals(data6)) {
				vo.setData6sc(4.55f);
			}
			if ("+2".equals(data6)) {
				vo.setData6sc(2.27f);
			}
			if ("+3".equals(data6)) {
				vo.setData6sc(0f);
			}
		}
		if ("- ".equals(data7) || "±".equals(data7)) {
			vo.setData7f(true);
			vo.setData7s("正常");
			vo.setData7sc(NORMAL_SC);
		} else {
			vo.setData7f(false);
			vo.setData7s("尿中蛋白质偏高");
			if ("+1".equals(data7)) {
				vo.setData7sc(6.82f);
			}
			if ("+2".equals(data7)) {
				vo.setData7sc(4.55f);
			}
			if ("+3".equals(data7)) {
				vo.setData7sc(2.27f);
			}
			if ("+4".equals(data7)) {
				vo.setData7sc(0f);
			}
		}
		if ("- ".equals(data8)) {
			vo.setData8f(true);
			vo.setData8s("正常");
			vo.setData8sc(NORMAL_SC);
		} else {
			vo.setData8f(false);
			vo.setData8s("尿中胆红素偏高");
			if ("+1".equals(data8)) {
				vo.setData8sc(6.82f);
			}
			if ("+2".equals(data8)) {
				vo.setData8sc(4.55f);
			}
			if ("+3".equals(data8)) {
				vo.setData8sc(2.27f);
			}
			if ("+4".equals(data8)) {
				vo.setData8sc(0f);
			}
		}
		if (com.mysql.jdbc.StringUtils.isEmptyOrWhitespaceOnly(data9)) {
			vo.setData9f(true);
			vo.setData9s("正常");
			vo.setData9sc(NORMAL_SC);
		} else {
			vo.setData9f(false);
			vo.setData9s("尿胆原偏高");
			if ("+1".equals(data9)) {
				vo.setData9sc(6.82f);
			}
			if ("+2".equals(data9)) {
				vo.setData9sc(4.55f);
			}
			if ("+3".equals(data9)) {
				vo.setData9sc(2.27f);
			}
			if ("+4".equals(data9)) {
				vo.setData9sc(0f);
			}
		}
		if ("- ".equals(data10)) {
			vo.setData10f(true);
			vo.setData10s("正常");
			vo.setData10sc(NORMAL_SC);
		} else {
			vo.setData10f(false);
			vo.setData10s("尿中酮体偏高");
			if ("±".equals(data10)) {
				vo.setData10sc(6.82f);
			}
			if ("+1".equals(data10)) {
				vo.setData10sc(4.55f);
			}
			if ("+2".equals(data10)) {
				vo.setData10sc(2.27f);
			}
			if ("+3".equals(data10)) {
				vo.setData10sc(0f);
			}
		}
		if ("- ".equals(data11)) {
			vo.setData11f(true);
			vo.setData11s("正常");
			vo.setData11sc(NORMAL_SC);
		} else {
			vo.setData11f(false);
			vo.setData11s("尿中白细胞值偏高");
			if ("±".equals(data11)) {
				vo.setData11sc(6.82f);
			}
			if ("+1".equals(data11)) {
				vo.setData11sc(4.55f);
			}
			if ("+2".equals(data11)) {
				vo.setData11sc(2.27f);
			}
			if ("+3".equals(data11)) {
				vo.setData11sc(0f);
			}
		}
		vo.setTotal(vo.getData11sc() + vo.getData10sc() + vo.getData9sc() + vo.getData8sc() + vo.getData7sc()
				+ vo.getData6sc() + vo.getData5sc() + vo.getData4sc() + vo.getData3sc() + vo.getData2sc()
				+ vo.getData1sc());
		vo.setMsgid(msg.getId());
		vo.setDevicename(msg.getDevicename());
		vo.setDeviceid(msg.getDeviceid());
		vo.setAccountid(msg.getAccountid());
		return vo;
	}

	// public byte[] getUUID(){
	// return
	// }

}

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
package com.ruixun.udp.manager;

import com.ruixun.entity.Account;
import com.ruixun.entity.Device;
import com.ruixun.entity.Msg;
import com.ruixun.entity.MsgModel;
import com.ruixun.node.ClientMessage;
import com.ruixun.node.NodeStatus;
import com.ruixun.service.AccountService;
import com.ruixun.service.DeviceService;
import com.ruixun.service.MsgModleService;
import com.ruixun.service.MsgService;
import com.ruixun.udp.connector.UdpConnector;

/**
 * 
 * 数据处理
 * 
 * @author qinchi
 *
 */
public class Messenger implements Runnable {

	private UdpConnector connector;
	private NodeStatus nodeStat;// this is very large and dynamic
	private Thread hostThread;

	boolean started = false;
	boolean stoped = false;

	public Messenger(UdpConnector connector, NodeStatus nodeStat) {
		this.connector = connector;
		this.nodeStat = nodeStat;
	}

	@Override
	public void run() {
		this.started = true;

		while (stoped == false) {
			try {
				procMessage();
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

	}

	public void stop() {
		this.stoped = true;
	}

	private void procMessage() throws Exception {
		ClientMessage m = this.obtainMessage();
//		System.out.println("m null");
		if (m == null) {
			try {
				Thread.sleep(5);
			} catch (Exception e) {
				;
			}
			return;
		}
		this.deliverMessage(m);

	}

	AccountService accountService = (AccountService) UDP.getBean("accountService");
	MsgService msgService = (MsgService) UDP.getBean("msgService");
	DeviceService deviceService = (DeviceService) UDP.getBean("deviceService");
	MsgModleService msgmodelService = (MsgModleService) UDP.getBean("msgModleService");

	// 处理消息
	private void deliverMessage(ClientMessage m) throws Exception {
		String devicename = m.getDeviceName();// 设备编号--设备名
		System.out.println("devicename="+devicename);
		Device device = deviceService.findByName(devicename);// 搜到设备
		if (null != device) {
			Msg msg = null;
			int status = device.getStatus();// 获取设备是否已被用户控制
			int deviceid = device.getId();// 设备id
			if (status == 1) {
				// 查用户权限
//				Account account = accountService.findByStatus(1);// 设备当前用户
				Account account = accountService.findByStatusAndDeviceid(status, deviceid);
				if (null != account) {
					msg = m.createMsg(account.getId(), deviceid, devicename);
				} else {
					// 无使用用户，则保存到admin记录下
					msg = m.createMsg(6, deviceid, devicename);
				}
			} else {
				// 无使用用户，则保存到admin记录下
				msg = m.createMsg(6, deviceid, devicename);
			}
			System.out.println("m="+m.getDeviceName());
			if (null != m) {
				msg = msgService.saveMsgInfo(msg);
				MsgModel mm = m.createMsgModel(msg);
				mm=msgmodelService.saveMsgModleInfo(mm);
			}
		} else {
			// 未查询到设备
			System.out.println(devicename+"这台设备未注册");
		}
		// 使用设备id查询当前用户，并确定操作人
		// Account account = accountService.findByIdentityAndDeviceid(1,
		// deviceId);
		// 插入数据
		// MsgService msgService = (MsgService)UDP.getBean("msgService");

		// msgService.saveMsgInfo(msg);
		// String uuid = m.getUuidHexString();
		// ClientStatMachine csm = NodeStatus.getInstance().getClientStat(uuid);
		// ClientStatMachine csm = nodeStat.getClientStat(uuid);
		// if (csm == null) {
		// csm = ClientStatMachine.newByClientTick(m);
		// if (csm == null) {
		// return;
		// }
		// nodeStat.putClientStat(uuid, csm);
		// }
		// ArrayList<ServerMessage> smList = csm.onClientMessage(m);
		// if (smList == null) {
		// return;
		// }
		// for (int i = 0; i < smList.size(); i++) {
		// ServerMessage sm = smList.get(i);
		// if (sm.getSocketAddress() == null)
		// continue;
		// // this.connector.send(sm);//FIXME
		// }

	}

	private ClientMessage obtainMessage() throws Exception {
		return connector.receive();
	}

	public void setHostThread(Thread t) {
		this.hostThread = t;
	}

	public Thread getHostThread() {
		return this.hostThread;
	}

}

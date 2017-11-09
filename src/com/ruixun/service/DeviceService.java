package com.ruixun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruixun.dao.DeviceDao;
import com.ruixun.entity.Device;

//Spring Bean的标识.
// 默认将类中的所有public函数纳入事务管理.
@Component("deviceService")
@Transactional
public class DeviceService {
	private DeviceDao deviceDao;
	
	/**
	 * 条件查询
	 * @param id
	 * @return
	 */
	public Device findByName(String name) {
		return deviceDao.findByName(name);
//		return null;
	}

	public List<Device> getAll() {
		return deviceDao.findAll();
	}

	public Device saveDeviceInfo(Device device) {
		return deviceDao.save(device);
	}

	public Device findById(Integer id) {
		return deviceDao.findOne(id);
	}

	public void delDeviceInfo(Device device) {
		deviceDao.delete(device);
	}

	@Autowired
	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}
	
}

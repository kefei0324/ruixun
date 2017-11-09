package com.ruixun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruixun.dao.MsgDao;
import com.ruixun.entity.Msg;

//Spring Bean的标识.
//默认将类中的所有public函数纳入事务管理.
@Component("msgService")
@Transactional
public class MsgService {
	private MsgDao msgDao;

	/**
	 * 条件查询
	 * 
	 * @param accountId
	 * @param deviceId
	 * @return
	 */
	public List<Msg> findByAccountidAndDeviceidOrderByCreatetimeDesc(int accountId, int deviceId, Pageable pageable) {
		return msgDao.findByAccountidAndDeviceidOrderByCreatetimeDesc(accountId, deviceId, pageable);
	}

	public List<Msg> getAll() {
		return msgDao.findAll();
	}

	public Msg saveMsgInfo(Msg msg) {
		return msgDao.save(msg);
	}

	public Msg findById(Integer id) {
		return msgDao.findOne(id);
	}

	public void delMsgInfo(Msg msg) {
		msgDao.delete(msg);
	}

	@Autowired
	public void setMsgDao(MsgDao msgDao) {
		this.msgDao = msgDao;
	}
}

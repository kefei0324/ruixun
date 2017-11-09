package com.ruixun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruixun.dao.MsgModleDao;
import com.ruixun.entity.MsgModel;
import com.ruixun.vo.MsgModleVo;

@Component("msgModleService")
@Transactional
public class MsgModleService {
	private MsgModleDao msgModleDao;

	public List<MsgModel> getAll() {
		return msgModleDao.findAll();
	}

	public MsgModel saveMsgModleInfo(MsgModel msg) {
		return msgModleDao.save(msg);
	}

	public MsgModel findById(Integer id) {
		return msgModleDao.findOne(id);
	}

	public void delMsgInfo(MsgModel msg) {
		msgModleDao.delete(msg);
	}

	@Autowired
	public void setMsgDao(MsgModleDao msgDao) {
		this.msgModleDao = msgDao;
	}

	public MsgModleVo findByMsgid(int msgid) {
		return msgModleDao.findByMsgid(msgid);
	}

	public List<MsgModel> findByAccountidAndDeviceidOrderByCreatetimeDesc(int accountId, int deviceId,
			Pageable pageable) {
		return msgModleDao.findByAccountidAndDeviceidOrderByCreatetimeDesc(accountId, deviceId, pageable);
	}
}

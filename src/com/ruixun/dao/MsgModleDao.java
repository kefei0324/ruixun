package com.ruixun.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springside.modules.persistence.MyJpaRepository;

import com.ruixun.entity.MsgModel;
import com.ruixun.vo.MsgModleVo;

public interface MsgModleDao  extends MyJpaRepository<MsgModel, Serializable> {

	MsgModleVo findByMsgid(int msgid);

	List<MsgModel> findByAccountidAndDeviceidOrderByCreatetimeDesc(int accountId, int deviceId, Pageable pageable);

}

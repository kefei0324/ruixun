package com.ruixun.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springside.modules.persistence.MyJpaRepository;

import com.ruixun.entity.Msg;

public interface MsgDao extends MyJpaRepository<Msg, Serializable> {

	List<Msg> findByAccountidAndDeviceidOrderByCreatetimeDesc(int accountId, int deviceId,Pageable pageable);

	List<Msg> findByDeviceid(int deviceid);

}

package com.ruixun.dao;

import java.io.Serializable;
import java.util.List;

import org.springside.modules.persistence.MyJpaRepository;

import com.ruixun.entity.Account;

public interface AccountDao extends MyJpaRepository<Account, Serializable>{
	List<Account>  findByDeviceid(int id);
	
	Account findByNameAndPwdAndDeviceid(String name, String pwd, int deviceId);
	
	Account findByNameAndPwd(String name, String pwd);

	Account findByIdAndDeviceid(int accountid, int deviceid);

	Account findByStatus(int status);
	
	Account findByStatusAndDeviceid(int status,int deviceid);
}

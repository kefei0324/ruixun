package com.ruixun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruixun.dao.AccountDao;
import com.ruixun.entity.Account;

@Component(value="accountService")
@Transactional
public class AccountService {

	private AccountDao accountDao;

	// 条件查询
	public List<Account> getByDeviceId(int deviceId) {
		return accountDao.findByDeviceid(deviceId);
	}

	public Account findByStatus(int status){
		return accountDao.findByStatus(status);
	}
	public Account findByStatusAndDeviceid(int status,int deviceid){
		return accountDao.findByStatusAndDeviceid(status,deviceid);
	}
	
	public Account findByNameAndPwdAndDeviceid(String name, String pwd, int deviceId) {
		return accountDao.findByNameAndPwdAndDeviceid(name, pwd, deviceId);
	}
	
	public Account findByNameAndPwd(String name,String pwd) {
		return accountDao.findByNameAndPwd(name, pwd);
	}

	public Account findByIdAndDeviceid(int accountid, int deviceid) {
		return accountDao.findByIdAndDeviceid(accountid, deviceid);
	}

	public List<Account> getAll() {
		return accountDao.findAll();
	}

	public Account saveAccountInfo(Account account) {
		return accountDao.save(account);
	}

	public Account findById(Integer id) {
		return accountDao.findOne(id);
	}

	public void delAccountInfo(Account account) {
		accountDao.delete(account);
	}

	@Autowired
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

}

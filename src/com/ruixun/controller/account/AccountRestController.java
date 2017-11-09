package com.ruixun.controller.account;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.web.MediaTypes;

import com.google.common.collect.Lists;
import com.ruixun.entity.Account;
import com.ruixun.entity.Device;
import com.ruixun.service.AccountService;
import com.ruixun.service.DeviceService;
import com.ruixun.utils.DateTimeUtil;
import com.ruixun.vo.AccountVo;
import com.ruixun.vo.DefaultVo;
import com.ruixun.vo.ResultVo;

/**
 * 用户管理
 * 
 * @author qinchi
 *
 */
@Controller
@RequestMapping(value = "/api/account")
public class AccountRestController {
	private static Logger logger = LoggerFactory.getLogger(AccountRestController.class);
	@Autowired
	private AccountService accountService;
	@Autowired
	private DeviceService deviceService;

	private static final String USER_RESET_PWD = "e10adc3949ba59abbe56e057f20f883e";

	/**
	 * 用户注册接口
	 * 
	 * @param name
	 * @param pwd
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo<AccountVo> register(@RequestParam("name") String name, @RequestParam("pwd") String pwd,
			@RequestParam("deviceId") int deviceId) {
		try {
			name = new String(name.getBytes("iso-8859-1"), "utf-8");
			// 验证用户名密码的合法性
			List<Account> accouts = accountService.getByDeviceId(deviceId);
			boolean has = false;// 是否已注册
			if (null != accouts) {
				if (accouts.size() >= 10) {
					return buildVo(null, "该设备已注册10人");
				}
				for (Account account : accouts) {
					if (!name.equals(account.getName())) {

					} else {
						// 有人注册过该用户名
						has = true;
						break;
					}
				}
				if (!has) {
					// 无人注册则保存用户到数据库
					Account account = new Account();
					account.setName(name);
					account.setPwd(pwd);
					account.setDeviceid(deviceId);
					String d = DateTimeUtil.getCurDateTime();
					account.setCreatetime(d);
					account = accountService.saveAccountInfo(account);
					// 返回用户信息
					// account =
					// accountService.findByNameAndPwdAndDeviceid(name, pwd,
					// deviceId);
					logger.info("new account build:" + account.getId());
					return buildVo(account, null);
				} else {
					// 已经有人注册
					return buildVo(null, "该用户名已经注册!");
				}
			} else {
				return buildVo(null, "网络异常，请重试");
			}
		} catch (Exception e) {
			return buildVo(null, null);
		}
	}

	/**
	 * 创建account相关返回数据
	 * 
	 * @param account
	 * @param msg
	 * @return
	 */
	private ResultVo<AccountVo> buildVo(Account account, String msg) {
		if (null != account) {
			AccountVo vo = new AccountVo();
			vo.setCreatetime(account.getCreatetime());
			vo.setDeviceid(account.getDeviceid());
			vo.setId(account.getId());
			vo.setName(account.getName());
			vo.setPwd(account.getPwd());
			vo.setStatus(account.getStatus());
			logger.info(vo.toString());
			return new ResultVo<AccountVo>().ok(vo);
		} else {
			return new ResultVo<AccountVo>().error(null, msg == null ? "注册失败!" : msg);
		}
	}

	/**
	 * 用户登录接口
	 * 
	 * @param name
	 * @param pwd
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo<AccountVo> login(@RequestParam("name") String name, @RequestParam("pwd") String pwd,
			@RequestParam("deviceId") int deviceId) {
		try {
			name = new String(name.getBytes("iso-8859-1"), "utf-8");
			// 返回用户信息
			Account account = accountService.findByNameAndPwdAndDeviceid(name, pwd, deviceId);
			if (null != account) {
				return buildVo(account, null);
			}
		} catch (Exception e) {
			// 已经有人注册
			return buildVo(null, "登陆失败,请检查用户名和密码!");
		}
		return buildVo(null, "登陆失败,请检查用户名和密码!");
	}

	/**
	 * 查用户列表
	 * 
	 * @param identity
	 * @return
	 */
	@RequestMapping(value = "/getUserList", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultVo<List<AccountVo>> getAccountList(@RequestParam("identity") int identity) {
		List<Account> accounts = Lists.newArrayList();
		try {
			if (checkIdentity(identity)) {
				accounts = accountService.getAll();
			} else {
				return new ResultVo<List<AccountVo>>().error(null, "需要管理员权限");
			}
		} catch (Exception e) {
			logger.error("服务器通讯错误!", e);
			return new ResultVo<List<AccountVo>>().error(null, "服务器通讯错误!");
		}
		return buildVo(accounts);
	}

	private boolean checkIdentity(int identity) {
		// if (identity != -1)
		// return false;
		return identity == -1;
	}

	/**
	 * 创建account list 相关返回数据
	 * 
	 * @param list
	 * @return
	 */
	private ResultVo<List<AccountVo>> buildVo(List<Account> list) {
		List<AccountVo> vos = Lists.newArrayList();
		for (Account account : list) {
			AccountVo vo = new AccountVo();
			// AccountVo vo = new AccountVo();
			vo.setCreatetime(account.getCreatetime());
			vo.setDeviceid(account.getDeviceid());
			vo.setId(account.getId());
			vo.setName(account.getName());
			vo.setPwd(account.getPwd());
			vo.setStatus(account.getStatus());
			logger.info(vo.toString());
			// return new ResultVo<AccountVo>().ok(vo);
			vos.add(vo);
		}
		logger.info(JsonMapper.nonDefaultMapper().toJson(vos));
		return new ResultVo<List<AccountVo>>().ok(vos);
	}

	/**
	 * 通过id删除用户
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/delegeUserById", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo<DefaultVo> deleteUser(@RequestParam("deleteUserId") int id,
			@RequestParam("identity") int identity) {
		try {
			if (checkIdentity(identity)) {
				Account account = accountService.findById(id);
				if (null != account) {
					accountService.delAccountInfo(account);
					return new ResultVo<DefaultVo>().ok(new DefaultVo().setMsg("删除成功!"));
				}
			} else {
				return new ResultVo<DefaultVo>().error(null, "需要管理员权限");
			}
		} catch (Exception e) {
			return new ResultVo<DefaultVo>().error(null, "删除失败!");
		}
		return new ResultVo<DefaultVo>().error(null, "请求失败，请查证用户");
	}

	/**
	 * 用户重置密码
	 * 
	 * @param identity
	 * @return
	 */
	@RequestMapping(value = "/resetPWD", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultVo<AccountVo> resetPWD(@RequestParam("identity") int identity, @RequestParam("userid") int userid) {
		try {
			// 返回用户信息
			Account account = accountService.findById(userid);
			if (null != account) {
				account.setPwd(USER_RESET_PWD);
				account=accountService.saveAccountInfo(account);
				return buildVo(account, null);
			}
		} catch (Exception e) {
			// 已经有人注册
			return buildVo(null, "重置密码失败，请重试");
		}
		return buildVo(null, "重置密码失败，请重试");
	}

	/**
	 * 修改密码
	 * 
	 * @param name
	 * @param pwd
	 * @param deviceId
	 * @param new_pwd
	 * @return
	 */
	@RequestMapping(value = "/modifyPWD", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultVo<AccountVo> modifyPWD(@RequestParam("name") String name, @RequestParam("pwd") String pwd,
			@RequestParam("deviceId") int deviceId, @RequestParam("new_pwd") String new_pwd) {
		try {
			name = new String(name.getBytes("iso-8859-1"), "utf-8");
			// 返回用户信息
			Account account = accountService.findByNameAndPwdAndDeviceid(name, pwd, deviceId);
			if (null != account) {
				account.setPwd(new_pwd);
				account = accountService.saveAccountInfo(account);
				return buildVo(account, null);
			}
		} catch (Exception e) {
			return buildVo(null, "修改失败,请检查用户名和密码!");
		}
		return buildVo(null, "修改失败,请检查用户名和密码!");
	}

	/**
	 * 修改用户权限
	 * 
	 * @param accountid
	 * @param deviceid
	 * @param identity
	 * @return
	 */
	@RequestMapping(value = "/modifyIdenity", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo<AccountVo> getIdenity(@RequestParam("accountid") int accountid,
			@RequestParam("deviceid") int deviceid) {
		try {
			Device device = deviceService.findById(deviceid);// 相关设备
			Account account = accountService.findById(accountid);// 相关用户
			if (null != device) {
				if (null != account) {
					int deviceStatus = device.getStatus();// 设备状态
					int accountStatus = account.getStatus();// 用户状态
					if (deviceStatus == DEVICE_NONE_OPERATION) {
						// 设备处于无操作状态
						account.setStatus(USER_UNDER_OPERATION);// 用户设为操作状态
						device.setStatus(DEVICE_UNDER_OPERATION);
						device=deviceService.saveDeviceInfo(device);
						account = accountService.saveAccountInfo(account);
						return buildVo(account, null);
					} else {
						// 设备处于操作状态
						if (accountStatus == USER_UNDER_OPERATION) {
							// 用户处于操作状态
							account.setStatus(USER_NONE_OPERATION);// 取消操作状态
							device.setStatus(DEVICE_NONE_OPERATION);
							device=deviceService.saveDeviceInfo(device);
							account = accountService.saveAccountInfo(account);// 保存
							return buildVo(account, null);// 返回用户信息
						} else {
							// 用户非操作状态
							return buildVo(null, "其他用户正在操作设备，请稍后");
						}
					}
				}
			} else {
				return buildVo(null, "无此设备,请查证设备编号后重试!");
			}
		} catch (Exception e) {
			return buildVo(null, "请求失败,请重试!");
		}
		return buildVo(null, "请求失败，请查证用户和设备名");
	}

	private static final int DEVICE_UNDER_OPERATION = 1;
	private static final int DEVICE_NONE_OPERATION = 0;
	private static final int USER_UNDER_OPERATION = 1;
	private static final int USER_NONE_OPERATION = 0;

}

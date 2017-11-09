package com.ruixun.controller.device;

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
import com.ruixun.vo.DeviceVo;
import com.ruixun.vo.ResultVo;

/**
 * 设备管理
 * 
 * @author qinchi
 *
 */
@Controller
@RequestMapping(value = "/api/device")
public class DeviceRestController {
	private static Logger logger = LoggerFactory.getLogger(DeviceRestController.class);
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private AccountService accountService;

	/**
	 * 获取设备列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getDeviceList", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultVo<List<DeviceVo>> getDeviceList() {
		List<Device> devices = Lists.newArrayList();
		try {
			devices = deviceService.getAll();
		} catch (Exception e) {
			logger.error("服务器通讯错误!", e);
			return new ResultVo<List<DeviceVo>>().error(null, "服务器通讯错误!");
		}
		return buildVo(devices);

	}

	/**
	 * 获取用户列表
	 * 
	 * @param deviceid
	 * @param identity
	 * @return
	 */
	@RequestMapping(value = "/getAccountList", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultVo<List<AccountVo>> getAccountList(@RequestParam("deviceid") int deviceid,
			@RequestParam("identity") int identity) {
		if (identity != -1) {
			return new ResultVo<List<AccountVo>>().error(null, "你没有权限!");
		}
		List<Account> accounts = Lists.newArrayList();
		try {
			if (checkIdentity(identity)) {
				accounts = accountService.getByDeviceId(deviceid);
			} else {
				return new ResultVo<List<AccountVo>>().error(null, "需要管理员权限");
			}
		} catch (Exception e) {
			logger.error("服务器通讯错误!", e);
			return new ResultVo<List<AccountVo>>().error(null, "服务器通讯错误!");
		}
		return buildAccountVo(accounts);

	}

	private boolean checkIdentity(int identity) {
		return identity == -1;
	}

	/**
	 * 创建account list 相关返回数据
	 * 
	 * @param list
	 * @return
	 */
	private ResultVo<List<AccountVo>> buildAccountVo(List<Account> list) {
		List<AccountVo> vos = Lists.newArrayList();
		for (Account account : list) {
			AccountVo vo = new AccountVo();
			vo.setCreatetime(account.getCreatetime());
			vo.setDeviceid(account.getDeviceid());
			vo.setId(account.getId());
			vo.setName(account.getName());
			vo.setPwd(account.getPwd());
			vo.setStatus(account.getStatus());
			logger.info(vo.toString());
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
	@RequestMapping(value = "/deleteAccount", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo<DefaultVo> deleteUser(@RequestParam("accountid") int id,
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
	 * 删除设备
	 * 
	 * @param deviceid
	 * @return
	 */
	@RequestMapping(value = "/deleteDevice", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultVo<DefaultVo> deleteDevice(@RequestParam("deviceid") int deviceid,
			@RequestParam("identity") int identity) {
		if (identity != -1) {
			return new ResultVo<DefaultVo>().error(null, "你没有权限删除设备!");
		}
		try {
			Device device = deviceService.findById(deviceid);
			if (null != device) {
				deviceService.delDeviceInfo(device);
				return new ResultVo<DefaultVo>().ok(new DefaultVo().setMsg("删除成功!"));
			}
		} catch (Exception e) {
			return new ResultVo<DefaultVo>().error(null, "删除失败!");
		}
		return new ResultVo<DefaultVo>().error(null, "请求失败，请查证设备");
	}

	/**
	 * 查询设备信息
	 * 
	 * @param deviceid
	 * @return
	 */
	@RequestMapping(value = "/getDeviceInfo", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultVo<DeviceVo> getDeviceInfo(@RequestParam("deviceid") int deviceid) {
		try {
			Device device = deviceService.findById(deviceid);
			if (null != device) {
				return buildVo(device, null);
			} else {
				return buildVo(null, "未查询到该设备，请重试");
			}
		} catch (Exception e) {
			return buildVo(null, "服务器出错，请重试");
		}
	}

	/**
	 * 新建设备
	 * 
	 * @param name
	 * @param descrip
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/createNewDevice", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultVo<DeviceVo> createNewDevice(@RequestParam("name") String name, @RequestParam("identity") int identity,
			@RequestParam("remark") String remark) {
		try {
			name = new String(name.getBytes("iso-8859-1"), "utf-8");
			remark = new String(remark.getBytes("iso-8859-1"), "utf-8");
			if (identity != -1) {
				return buildVo(null, "你没有权限新建设备!");
			}
			List<Device> devices = deviceService.getAll();
			boolean has = false;// 是否已注册
			for (Device device : devices) {
				if (!name.equals(device.getName())) {
				} else {
					has = true;
					break;
				}
			}
			if (!has) {
				Device device = new Device();
				device.setName(name);
				device.setStatus(0);
				device.setRemark(remark);
				String d = DateTimeUtil.getCurDateTime();
				device.setCreatetime(d);
				device =deviceService.saveDeviceInfo(device);
//				device = deviceService.findByName(name);
				return buildVo(device, null);
			} else {
				return buildVo(null, "该设备编号已经存在!");
			}
		} catch (Exception e) {
			return buildVo(null, null);
		}

	}

	/**
	 * 创建device相关返回数据
	 * 
	 * @param account
	 * @param msg
	 * @return
	 */
	private ResultVo<DeviceVo> buildVo(Device device, String msg) {
		if (null != device) {
			DeviceVo vo = new DeviceVo();
			vo.setCreatetime(device.getCreatetime());
			vo.setId(device.getId());
			vo.setName(device.getName());
			vo.setRemark(device.getRemark());
			logger.info(vo.toString());
			return new ResultVo<DeviceVo>().ok(vo);
		} else {
			return new ResultVo<DeviceVo>().error(null, msg == null ? "创建失败!" : msg);
		}
	}

	/**
	 * 创建device list 相关返回数据
	 * 
	 * @param list
	 * @return
	 */
	private ResultVo<List<DeviceVo>> buildVo(List<Device> list) {
		List<DeviceVo> vos = Lists.newArrayList();
		for (Device device : list) {
			DeviceVo vo = new DeviceVo();
			vo.setCreatetime(device.getCreatetime());
			vo.setId(device.getId());
			vo.setName(device.getName());
			vo.setRemark(device.getRemark());
			vo.setStatus(device.getStatus());
			vos.add(vo);
		}
		logger.info(JsonMapper.nonDefaultMapper().toJson(vos));
		return new ResultVo<List<DeviceVo>>().ok(vos);
	}

}

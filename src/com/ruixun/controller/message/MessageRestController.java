package com.ruixun.controller.message;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.web.MediaTypes;

import com.google.common.collect.Lists;
import com.ruixun.entity.Msg;
import com.ruixun.entity.MsgModel;
import com.ruixun.service.MsgModleService;
import com.ruixun.service.MsgService;
import com.ruixun.vo.MsgModleVo;
import com.ruixun.vo.MsgVo;
import com.ruixun.vo.ResultVo;

@Controller
@RequestMapping(value = "/api/msg")
public class MessageRestController {
	private static Logger logger = LoggerFactory.getLogger(MessageRestController.class);
	@Autowired
	private MsgService msgService;
	@Autowired
	private MsgModleService msgModleService;
	private static final int CUR_MSG_LIST_SIZE = 8;

	/**
	 * 获取msg列表
	 * 
	 * @param accountId
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = "/getMsg", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultVo<List<MsgVo>> getHistoryMsg(@RequestParam("accountId") int accountId,
			@RequestParam("deviceId") int deviceId, @RequestParam("page") int page) {
		List<Msg> msgs = Lists.newArrayList();
		try {
			msgs = msgService.findByAccountidAndDeviceidOrderByCreatetimeDesc(accountId, deviceId,
					new PageRequest(page, CUR_MSG_LIST_SIZE));
		} catch (Exception e) {
			logger.error("服务器通讯错误!", e);
			return new ResultVo<List<MsgVo>>().error(null, "服务器通讯错误!");
		}
		return buildVo(msgs);
	}

	/**
	 * 获取模板列表
	 * 
	 * @param msgId
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "/getMsgModleList", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultVo<List<MsgModleVo>> getMsgModleList(@RequestParam("accountId") int accountId,
			@RequestParam("deviceId") int deviceId, @RequestParam("page") int page) {
		List<MsgModel> list = Lists.newArrayList();
		try {
			list = msgModleService.findByAccountidAndDeviceidOrderByCreatetimeDesc(accountId, deviceId,
					new PageRequest(page, CUR_MSG_LIST_SIZE));
		} catch (Exception e) {
			logger.error("服务器通讯错误!", e);
			return new ResultVo<List<MsgModleVo>>().error(null, "服务器通讯错误!");
		}
		return buildModleVo(list);
	}

	/**
	 * 获取模板
	 * 
	 * @param msgId
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "/getMsgModle", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultVo<List<MsgModleVo>> getMsgModle(@RequestParam("msgid") int msgid,
			@RequestParam("userid") int userid) {
		try {
			List<MsgModleVo> list = Lists.newArrayList();
			Msg msg = msgService.findById(msgid);
			if (null != msg) {
				int accountid = msg.getAccountid();
				if (userid == accountid) {
					MsgModleVo vo = msgModleService.findByMsgid(msgid);
					list.add(vo);
					return new ResultVo<List<MsgModleVo>>().ok(list);
				} else {
					return new ResultVo<List<MsgModleVo>>().error(null, "你没有查询权限");
				}
			}
			return new ResultVo<List<MsgModleVo>>().error(null, "服务器通讯错误!");
		} catch (Exception e) {
			logger.error("服务器通讯错误!", e);
			return new ResultVo<List<MsgModleVo>>().error(null, "服务器通讯错误!");
		}
	}

	/**
	 * 创建msgmodellist相关返回数据
	 * 
	 * @param list
	 * @return
	 */
	private ResultVo<List<MsgModleVo>> buildModleVo(List<MsgModel> list) {
		List<MsgModleVo> vos = Lists.newArrayList();
		for (MsgModel msg : list) {
			MsgModleVo vo = new MsgModleVo();
			vo.setCreatetime(msg.getCreatetime());
			vo.setId(msg.getId());
			vo.setDeviceid(msg.getDeviceid());
			vo.setAccountid(msg.getAccountid());
			vo.setDevicename(msg.getDevicename());
			vo.setData1(msg.getData1());
			vo.setData2(msg.getData2());
			vo.setData3(msg.getData3());
			vo.setData4(msg.getData4());
			vo.setData5(msg.getData5());
			vo.setData6(msg.getData6());
			vo.setData7(msg.getData7());
			vo.setData8(msg.getData8());
			vo.setData9(msg.getData9());
			vo.setData10(msg.getData10());
			vo.setData11(msg.getData11());
			vo.setData1s(msg.getData1s());
			vo.setData2s(msg.getData2s());
			vo.setData3s(msg.getData3s());
			vo.setData4s(msg.getData4s());
			vo.setData5s(msg.getData5s());
			vo.setData6s(msg.getData6s());
			vo.setData7s(msg.getData7s());
			vo.setData8s(msg.getData8s());
			vo.setData9s(msg.getData9s());
			vo.setData10s(msg.getData10s());
			vo.setData11s(msg.getData11s());
			vo.setData1sc(msg.getData1sc());
			vo.setData2sc(msg.getData2sc());
			vo.setData3sc(msg.getData3sc());
			vo.setData4sc(msg.getData4sc());
			vo.setData5sc(msg.getData5sc());
			vo.setData6sc(msg.getData6sc());
			vo.setData7sc(msg.getData7sc());
			vo.setData8sc(msg.getData8sc());
			vo.setData9sc(msg.getData9sc());
			vo.setData10sc(msg.getData10sc());
			vo.setData11sc(msg.getData11sc());
			vo.setData1f((msg.isData1f()));
			vo.setData2f((msg.isData2f()));
			vo.setData3f((msg.isData3f()));
			vo.setData4f((msg.isData4f()));
			vo.setData5f((msg.isData5f()));
			vo.setData6f((msg.isData6f()));
			vo.setData7f((msg.isData7f()));
			vo.setData8f((msg.isData8f()));
			vo.setData9f((msg.isData9f()));
			vo.setData10f((msg.isData10f()));
			vo.setData11f((msg.isData11f()));
			vo.setTotal(msg.getTotal());
			vos.add(vo);
		}
		logger.info(JsonMapper.nonDefaultMapper().toJson(vos));
		return new ResultVo<List<MsgModleVo>>().ok(vos);
	}

	/**
	 * 创建msg list 相关返回数据
	 * 
	 * @param list
	 * @return
	 */
	private ResultVo<List<MsgVo>> buildVo(List<Msg> list) {
		List<MsgVo> vos = Lists.newArrayList();
		for (Msg msg : list) {
			MsgVo vo = new MsgVo();
			vo.setCreatetime(msg.getCreatetime());
			vo.setId(msg.getId());
			vo.setDeviceid(msg.getDeviceid());
			vo.setAccountid(msg.getAccountid());
			vo.setDevicename(msg.getDevicename());
			vo.setData1(msg.getData1());
			vo.setData2(msg.getData2());
			vo.setData3(msg.getData3());
			vo.setData4(msg.getData4());
			vo.setData5(msg.getData5());
			vo.setData6(msg.getData6());
			vo.setData7(msg.getData7());
			vo.setData8(msg.getData8());
			vo.setData9(msg.getData9());
			vo.setData10(msg.getData10());
			vo.setData11(msg.getData11());
			vos.add(vo);
		}
		logger.info(JsonMapper.nonDefaultMapper().toJson(vos));
		return new ResultVo<List<MsgVo>>().ok(vos);
	}
}

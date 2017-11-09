package com.ruixun.vo;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.ruixun.utils.Constants;

public class ResultVo<T> {
	public static final String RESULT_STATUS_CODE = "code";
	public static final String RESULT_STATUS_MESSAGE = "message";

	private Map<String, Object> resultStatus;
	private T resultValue;

	public ResultVo() {
	}

	public ResultVo(T t) {
		this.resultValue = t;
	}

	public Map<String, Object> getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Map<String, Object> resultStatus) {
		this.resultStatus = resultStatus;
	}

	public T getResultValue() {
		return resultValue;
	}

	public void setResultValue(T resultValue) {
		this.resultValue = resultValue;
	}

	public ResultVo<T> ok(T resultValue) {
		Map<String, Object> resultStatus = Maps.newHashMap();
		resultStatus.put(ResultVo.RESULT_STATUS_CODE, Constants.RESPONSE_CODE_OK);
		resultStatus.put(ResultVo.RESULT_STATUS_MESSAGE, "ok");
		this.setResultStatus(resultStatus);
		this.resultValue = resultValue;
		return this;
	}

	public ResultVo<T> error(T resultValue, String msg) {
		Map<String, Object> resultStatus = Maps.newHashMap();
		resultStatus.put(ResultVo.RESULT_STATUS_CODE, Constants.RESPONSE_CODE_ERROR);
		resultStatus.put(ResultVo.RESULT_STATUS_MESSAGE, StringUtils.isBlank(msg) ? "System Internal error." : msg);
		this.setResultStatus(resultStatus);
		this.resultValue = resultValue;
		return this;
	}
}

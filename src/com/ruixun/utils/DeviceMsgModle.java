package com.ruixun.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceMsgModle {

	private static final String TAG = "DEVICE_MSG_MODLE_TAG";
	private static Logger logger = LoggerFactory.getLogger(DeviceMsgModle.class);

	public static String getValue(String key) {
		String result = PropertyUtil.getProperty(key);
		logger.info(TAG, key, result);
		return result;
	}

	public static int getValueInt(String key) {
		int result = PropertyUtil.getPropertyInt(key);
		logger.info(TAG, key, result);
		return result;
	}

	public static float getValueFloat(String key) {
		float result = PropertyUtil.getPropertyFloat(key);
		logger.info(TAG, key, result);
		return result;
	}

}

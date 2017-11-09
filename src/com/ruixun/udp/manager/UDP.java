package com.ruixun.udp.manager;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;

/**
 * 
 * @author qinchi
 *
 */
public class UDP implements ApplicationListener<ContextRefreshedEvent> {

	private static ApplicationContext applicationContext = null;

	public void startUdp() {
		UdpManager manager = UdpManager.getInstance();
		new Thread(manager).start();
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

	// @Override
	// public void onApplicationEvent(ContextRefreshedEvent event) {
	// if (event.getApplicationContext().getParent() == null) {// root
	// // 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
	// applicationContext = event.getApplicationContext();
	// startUdp();
	// }
	// }

	// @Override
	// public void setApplicationContext(ApplicationContext context) throws
	// BeansException {
	// if (context != null) {
	// // 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
	// applicationContext = context;
	// startUdp();
	// }
	// }

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {// root
			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			applicationContext = event.getApplicationContext();
			startUdp();
		}
	}

}

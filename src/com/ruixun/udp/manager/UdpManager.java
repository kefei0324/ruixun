package com.ruixun.udp.manager;

import java.util.ArrayList;
import java.util.Date;

import com.ruixun.node.ClientStatMachine;
import com.ruixun.node.ClientStatMachineCleaner;
import com.ruixun.node.IMServerConsole;
import com.ruixun.node.NodeStatus;
import com.ruixun.udp.connector.UdpConnector;
import com.ruixun.utils.DateTimeUtil;
import com.ruixun.utils.PropertyUtil;

/**
 * 
 * @author qinchi
 *
 */
public class UdpManager implements Runnable {

	private UdpConnector udpConnector;

	private boolean stoped = false;
	private long startTime;

	private ClientStatMachineCleaner cleaner = null;
	private Thread clearnThread = null;

	private int workerNum = PropertyUtil.getPropertyInt("CLIENT_UDP_WORKER_THREAD");
	private ArrayList<Messenger> workerList = new ArrayList<Messenger>();

	private NodeStatus nodeStatus = NodeStatus.getInstance();

	private Thread cmdThread = null;
	private IMServerConsole console = null;

	public void init() throws Exception {
		// initPushListener();
		initConsole();
		initUdpConnector();
		// initTcpConnector();
		initWorkers();
		initCleaner();
	}

	public void initConsole() throws Exception {
		console = new IMServerConsole();
		cmdThread = new Thread(console, "IMServer-console");
		cmdThread.setDaemon(true);
		cmdThread.start();
	}

	public void initUdpConnector() throws Exception {
		System.out.println("start connector...");
		udpConnector = new UdpConnector();
		udpConnector.start();
	}

	public void initWorkers() throws Exception {
		System.out.println("start " + workerNum + " workers...");
		for (int i = 0; i < workerNum; i++) {
			Messenger worker = new Messenger(udpConnector, nodeStatus);
			workerList.add(worker);
			Thread t = new Thread(worker, "IMServer-worker-" + i);
			worker.setHostThread(t);
			t.setDaemon(true);
			t.start();
		}
	}

	public void initCleaner() throws Exception {
		cleaner = new ClientStatMachineCleaner();
		clearnThread = new Thread(cleaner, "IMServer-cleaner");
		clearnThread.start();
	}

	public void startUdpServer() throws Exception {
		System.out.println("working dir: " + System.getProperty("user.dir"));
		init();
		final Thread mainT = Thread.currentThread();
		Runtime.getRuntime().addShutdownHook(new Thread() {

			public void run() {
				stoped = true;
				System.out.println("\r\nshut down server... ");
				try {
					mainT.join();
					System.out.println("\r\nserver is down, bye ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		this.startTime = System.currentTimeMillis();
		System.out.println("\r\nserver is up ");
		while (stoped == false) {
			try {
				synchronized (this) {
					this.wait(1000 * 60);
					if (stoped == false) {
						autoClean();
					}
				}
			} catch (Exception e) {
			}
		}
		this.quit();
	}

	private void autoClean() {
		float percent = PropertyUtil.getPropertyFloat("CLEANER_AUTO_RUN_MEM_PERCENT");
		if (percent >= 1 || percent <= 0) {
			return;
		}
		Runtime rt = Runtime.getRuntime();
		if ((rt.totalMemory() - rt.freeMemory()) / (double) rt.maxMemory() > percent) {
			System.out.println("run auto clean...");
			cleaner.wakeup();
		}
	}

	protected void quit() throws Exception {
		try {
			// stopWorkers();
			stopUdpConnector();
			// stopTcpConnector();
			// stopCleaner();
			// stopPushListener();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		saveStatus();
	}

	public void saveStatus() throws Exception {
		nodeStatus.saveToFile();
	}

	public void stopUdpConnector() throws Exception {
		if (udpConnector == null) {
			return;
		}
		udpConnector.stop();
	}

	public String getStatusString() {
		StringBuffer sb = new StringBuffer();

		String end = "\r\n";

		sb.append("server start up at: ").append(DateTimeUtil.formatDate(new Date(this.startTime))).append(end);
		long runtime = System.currentTimeMillis() - this.startTime;
		sb.append("up time: ").append(runtime / (1000 * 3600 * 24)).append(" day ").append(runtime / (1000 * 3600))
				.append(" hour ").append(runtime / (1000 * 60)).append(" minute").append(end);
		sb.append("messagers: ").append(this.workerList.size()).append(end);
		sb.append("current stat machines: ").append(nodeStatus.size()).append(end);
		sb.append("udp recieve packages: ").append(this.udpConnector.getInqueueIn()).append(end);
		sb.append("udp recieve packages pending: ")
				.append(this.udpConnector.getInqueueIn() - this.udpConnector.getInqueueOut()).append(end);
		// sb.append("udp send packages:
		// ").append(this.udpConnector.getOutqueueIn()).append(end);
		// sb.append("udp send packages pending: ")
		// .append(this.udpConnector.getOutqueueIn() -
		// this.udpConnector.getOutqueueOut()).append(end);
		sb.append("jvm  max  mem: ").append(Runtime.getRuntime().maxMemory()).append(end);
		sb.append("jvm total mem: ").append(Runtime.getRuntime().totalMemory()).append(end);
		sb.append("jvm  free mem: ").append(Runtime.getRuntime().freeMemory()).append(end);
		sb.append("last clean time: ").append(DateTimeUtil.formatDate(new Date(this.cleaner.getLastCleanTime())))
				.append(end);
		sb.append("messengers threads:----------------------").append(end);
		for (int i = 0; i < workerList.size(); i++) {
			Thread t = workerList.get(i).getHostThread();
			sb.append(t.getName() + " stat: " + t.getState().toString()).append(end);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		try {
			UdpManager manager = UdpManager.getInstance();
			new Thread(manager).start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	public static UdpManager manager;

	public static UdpManager getInstance() {
		if (manager == null) {
			synchronized (UdpManager.class) {
				if (manager == null) {
					manager = new UdpManager();
				}
			}
		}
		return manager;
	}

	public void stop() {
		this.stoped = true;
		synchronized (this) {
			this.notifyAll();
		}
	}

	public String getUuidStatString(String uuid) {
		ClientStatMachine csm = this.nodeStatus.getClientStat(uuid);
		if (csm == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		String end = "\r\n";

		sb.append("stat of   uuid: " + uuid).append(end);
		sb.append("last tick time: " + DateTimeUtil.formatDate(new Date(csm.getLastTick()))).append(end);
		sb.append("last ip addres: " + csm.getLastAddr()).append(end);
		sb.append("last tcp  time: " + DateTimeUtil
				.formatDate(new Date(csm.getMessengerTask() == null ? 0 : csm.getMessengerTask().getLastActive())))
				.append(end);
		sb.append("0x10   message: " + csm.has0x10Message()).append(end);
		sb.append("last 0x10 time: " + DateTimeUtil.formatDate(new Date(csm.getLast0x10Time()))).append(end);
		sb.append("0x11   message: " + csm.get0x11Message()).append(end);
		sb.append("last 0x11 time: " + DateTimeUtil.formatDate(new Date(csm.getLast0x11Time()))).append(end);
		sb.append("0x20   message: " + csm.has0x20Message()).append(end);
		sb.append("last 0x20 time: " + DateTimeUtil.formatDate(new Date(csm.getLast0x20Time()))).append(end);
		sb.append("0x20 arry  len: " + csm.getMessage0x20Len()).append(end);

		return sb.toString();
	}

	public void cleanExpiredMachines(int hours) {
		cleaner.setExpiredHours(hours);
		cleaner.wakeup();
	}

	private UdpManager() {
		System.out.println("main()..启动socket");
	}

	@Override
	public void run() {
		try {
			startUdpServer();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

}

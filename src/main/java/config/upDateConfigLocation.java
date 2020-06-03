package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class upDateConfigLocation implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(upDateConfigLocation.class);
	private WatchService service;
	private String listenerPath;
	private Boolean truk = true;

	public upDateConfigLocation(WatchService service, String listenerPath) {
		this.service = service;
		this.listenerPath = listenerPath;
	}
	

	public upDateConfigLocation(WatchService service, String listenerPath,
			Boolean truk) {
		super();
		this.service = service;
		this.listenerPath = listenerPath;
		this.truk = truk;
	}


	public void run() {
		try {
			while (truk) {
				WatchKey watchKey = service.take();
				List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
				for (WatchEvent<?> event : watchEvents) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
						logger.info(event.context().toString()+"文件线发生更改！！！");
						ConfigLocation.getInstance().putConfCache(event.context().toString(), listenerPath);
					}
				}
				if (!watchKey.reset()) {
					truk = false;
					logger.error("热部署配置文件线程出错");
				}
			}
		} catch (InterruptedException e) {
			logger.error("upDateConfigLocation的InterruptedException", e);
		} finally {
			try {
				service.close();
			} catch (IOException e) {
				service = null;
				logger.error("upDateConfigLocation的IOException", e);
			}
		}

	}

}

package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 资源文件监听器
 * @author dawei
 *
 */
public class FileResourceListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileResourceListener.class);
	private static final Map<String, FileResourceListener> ListenrList = new HashMap<String, FileResourceListener>();
	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);  
    private WatchService ws;  
    private String listenerPath;  
    private FileResourceListener(String path) {  
        try {  
            ws = FileSystems.getDefault().newWatchService();  
            this.listenerPath = path;  
            start();  
        } catch (IOException e) { 
        	LOGGER.error(e.getMessage(), e);
        }  
    }  
  
    private void start() {  
        fixedThreadPool.execute(new upDateConfigLocation(ws,this.listenerPath));  
    }  
  
    public static void addListener(String path){  
    	try {
    		FileResourceListener listener = ListenrList.get(path);
    		if(listener==null){
    			FileResourceListener resourceListener = new FileResourceListener(path);  
    			ListenrList.put(path, resourceListener);
    			Path p = Paths.get(path);  
    			LOGGER.info(">>>监听的配置文件的路径"+path);
    			p.register(resourceListener.ws,StandardWatchEventKinds.ENTRY_MODIFY,  
    					StandardWatchEventKinds.ENTRY_DELETE,  
    					StandardWatchEventKinds.ENTRY_CREATE);
    		}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}  
    }  
}

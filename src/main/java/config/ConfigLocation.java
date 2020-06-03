package config;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 用于项目配置文件的管理
 * 
 * @author dawei
 * 
 */
public class ConfigLocation {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLocation.class);

	private static final ConfigLoader loader = ConfigLoader.newInstall();// 用于存储所有配置文件信息

	public static final Map<String, File> ConfCache = new HashMap<String, File>();// 用于存储所有配置文件信息

	public Boolean isMemory = true;// 是否加入内存，true存入内存

	public Boolean hotDeployment = false;// 是否热读取

	private List<String> dirs = new ArrayList<>();// 配置文件的路径

	/**
	 * 私有化构造器
	 */
	private ConfigLocation() {
		loadDefaultConfig();
	}

	/**
	 * 获取ConfigLocation对象
	 * 
	 * @return
	 */
	public static ConfigLocation getInstance() {
		return new ConfigLocation();
	}

	public File getConfcache(String key) {
		return ConfCache.get(key);
	}

	/**
	 * 设置加载配置文件的路径
	 * 
	 * @param dir
	 */
	public void setConfigLocationDir(String dir) {
		// 如果窜入的dir为空
		if (StringUtils.isBlank(dir)) {
			dir = "classPath";
		}
		if (dir.contains(",")) {
			dirs = Arrays.asList(dir.split(","));
		} else {
			dirs.add(dir);
		}
	}

	/**
	 * 读取默认的配置文件，获取系统需要加载的配置文件目录
	 * 
	 * @return
	 */
	private void loadDefaultConfig() {
		Properties properties;
		try {
			properties = loader.loadClasspathProperties("configload.properties");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (properties != null) {
			String pathConf = properties.getProperty("dirs");
			String isMemoryConf = properties.getProperty("isMemory");
			String hotDeploymentConf = properties.getProperty("hotDeployment");
			setConfigLocationDir(pathConf);
			if (StringUtils.isNotBlank(isMemoryConf)) {
				isMemory = Boolean.valueOf(isMemoryConf);
			}
			if (StringUtils.isNotBlank(hotDeploymentConf)) {
				hotDeployment = Boolean.valueOf(hotDeploymentConf);
			}
		}
	}

	/**
	 * 
	 * @param dirs
	 *            需要加载的文件目录
	 * 
	 */
	private ConfigLocation loadDirs(List<String> dirs) {
		for (String dir : dirs) {
			dir = PathUtil.getSystemPath(dir);
			if (StringUtils.isNotBlank(dir)) {
				LOGGER.info("获取需要加载配置文件的路径" + dir);
				loadDirFiles(dir);
			}
		}
		return this;

	}

	/**
	 * 加载目录下的所有文件
	 * 
	 * @param dir
	 */
	private void loadDirFiles(String dir) {
		try {
			Path path = Paths.get(dir);
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
			for (Path filePath : directoryStream) {
				String absolutePath = filePath.toAbsolutePath().toString();
				String fileName = filePath.getFileName().toString();
				putConfCache(fileName, absolutePath);
			}
		} catch (IOException e) {
			LOGGER.error("获取改路径下的文件", e);
		}
	}

	/**
	 * 加载配置文件
	 * 
	 * @param fileName
	 * @param absolutePath
	 */
	public void putConfCache(String fileName, String absolutePath) {
		try {
			File file = new File(absolutePath);
			String fileExtensionName = PathUtil.getExtension(fileName);
			String key = new ConfigFile(fileName, fileExtensionName).generateKey();
			ConfCache.put(key, file);
		} catch (Exception e) {
			LOGGER.error("加载文件出错", e);
		}

	}

	/**
	 * 加在系统的所有配置文件
	 */
	public ConfigLocation loadConfig() {
		return loadConfig(hotDeployment);
	}

	/**
	 * 加在系统的所有配置文件
	 */
	public ConfigLocation loadConfig(Boolean isListener) {
		loadDirs(dirs);
		isOpenFileListener(isListener);
		return this;
	}

	private void isOpenFileListener(Boolean isListener) {
		if (isListener) {
			for (String dir : dirs) {
				dir = PathUtil.getSystemPath(dir);
				if (StringUtils.isNotBlank(dir)) {
					LOGGER.info("加入文件监控的目录：" + dir);
					FileResourceListener.addListener(dir);
				}
			}
		}
	}

}

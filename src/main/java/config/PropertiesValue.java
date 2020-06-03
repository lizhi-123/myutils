package config;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

abstract public class PropertiesValue {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesValue.class);
	private static final ConfigLoader loader = ConfigLoader.newInstall();// 用于存储所有配置文件信息
	private static final ConfigLocation location = ConfigLocation.getInstance();

	/**
	 * 从内存缓存中去取配置信息
	 * 
	 * @param property
	 * @param key
	 * @return
	 */
	private static String getValue4Memory(String property, String key) {
		String value = null;
		if (!StringUtils.isBlank(property)) {
			if (!property.contains(".properties")) {
				property += ".properties";
			}
			String propertiesName = property;// properties文件的真实名称
			if (property.contains("/")) {
				propertiesName = property.substring(property.lastIndexOf("/") + 1);
			}
			ConfigFile configFile=new ConfigFile(propertiesName, PathUtil.getExtension(propertiesName));
			File file = location.getConfcache(configFile.generateKey());
			if(file==null){
				String absolutePath=PathUtil.getSystemPath(property);
				file=new File(absolutePath);
			}
		    Properties properties = getProperties(file);
			value = properties.getProperty(key);
		}
		return value;

	}

	private static Properties getProperties(File file){
		Properties properties=null;
		FileInputStream inputStream=null;
		try {
			properties = new Properties();
			inputStream=new FileInputStream(file);
			properties.load(inputStream);
		} catch (Exception e) {
			LOGGER.error(file.getName()+"获取数据失败", e);
		}finally {
			try {
				if(inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				LOGGER.error(file.getName()+"关闭文件流失败", e);
			}		
		}
		return properties;
	}

	public static String getValue(String property, String key) {
		String value = null;
		Boolean isMemory = location.isMemory;
		if (isMemory) {
			value = getValue4Memory(property, key);
		} else {
			value = getValueNotMemory(property, key);
		}
		return value;

	}

	/**
	 * 实时获取配置信息
	 * 
	 * @param property
	 * @param key
	 * @return
	 */
	private static String getValueNotMemory(String property, String key) {
		String absolutePath = PathUtil.getSystemPath(property);
		Properties properties = loader.loadProperties(absolutePath);
		return properties.getProperty(key);
	}
	
	public static String getJarValue(String property, String key){
		if (StringUtils.isBlank(property)) {
			throw new RuntimeException( "配置文件读取失败,配置文件名不能为空！");
		}
		if (!property.contains(".properties")) {
			property += ".properties";
		}
		String value= null;
		InputStream in = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			in = classLoader.getResourceAsStream(property);
			if (in == null) {
				throw new RuntimeException("配置文件读取失败,找不到相应的路径："+property);
			}
			Properties properties = new Properties();
			properties.load(in);
			value = properties.getProperty(key);
		} catch (Exception e) {
			LOGGER.error("获取数据失败", e);
		}finally {
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				LOGGER.error("获取数据失败", e);
			}
		}
		return value;
	}

}

package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.xml.XmlUtils;
import org.w3c.dom.Document;

import java.io.*;
import java.util.Properties;

/**
 * 常用文件加载类
 * 
 * @author dawei
 */
public class ConfigLoader {

	private final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
	private static ConfigLoader configLoader = null;
	private static final ThreadLocal<ConfigLoader> threadLocal = new ThreadLocal<>();

	private ConfigLoader() {
	}

	public static ConfigLoader newInstall() {
		configLoader = threadLocal.get();
		if (configLoader != null) {
			return configLoader;
		}
		threadLocal.set(new ConfigLoader());
		return newInstall();
	}

	/**
	 * 通过classpath来获取绝对路径
	 * 
	 * @param classPath
	 *            类路径
	 * @return
	 */
	public String getAbsolutePath(String classPath) {
		return PathUtil.getSystemPath(classPath);
	}

	/**
	 * 加载Properties文件
	 * 
	 * @param absolutePath
	 *            Properties文件的绝对路劲
	 * @return
	 */
	public Properties loadProperties(String absolutePath) {
		Properties properties = null;
		InputStream inputStream = null;
		try {
//			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(absolutePath);
			inputStream = new FileInputStream(absolutePath);
			properties=new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			LOGGER.error("读取配置文件失败", e);
		}finally {
			try {
				if(inputStream != null) {
					inputStream.close();
				}				
			} catch (IOException e) {
				LOGGER.error("文件关闭异常", e);
			}
		}
		if(properties==null){
			 throw new RuntimeException( "没有找到相应"+absolutePath+"文件！");
		}
		return properties;

	}
	public Properties loadClasspathProperties(String path) throws IOException {
		Properties result = null;
		InputStream is = null;
		try {			
			is = getClass().getClassLoader().getResourceAsStream(path);
			if(is == null) {
				throw new FileNotFoundException("无法找到Properties文件:" + path);
			}
			result = new Properties();
			result.load(is);
		}catch(IOException ioe) {
			throw new FileNotFoundException("无法找到Properties文件:" + path);
		}finally {
			if(is != null) {
				is.close();				
			}
		}
		return result;
	}
	/**
	 * 
	 * @param absolutePath
	 *            xml文件的句对路径
	 * @return
	 * @throws DocumentException
	 */
//	public Document loadXML(String absolutePath) {
//		File xmlFile = new File(absolutePath);
//		Document loadXML = loadXML(xmlFile);
//		return loadXML;
//	}
	/**
	 * 
	 * @param xmlFile
	 *            xml文件
	 * @return
	 * @throws DocumentException
	 */
//	public Document loadXML(File xmlFile) {
//		Document document = null;
//		try {
//			document = XmlUtils.getDocument(xmlFile);
//		} catch (Exception e) {
//			LOGGER.error("读取配置" + xmlFile.getName() + "文件失败", e);
//		}
//		return document;
//	}

}

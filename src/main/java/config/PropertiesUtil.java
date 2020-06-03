package config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);
	/**
	 * 获取文件的值
	 * @param filePath 文件
	 * @param key 键
	 * @return 值
	 * @throws IOException
	 */
	public static String getValue(String filePath, String key) throws IOException {
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(filePath));
		return properties.getProperty(key);
	}
	
	public static String getValueUTF8(String filePath, String key) throws IOException {
		Properties properties = new Properties();
		InputStreamReader is = null;
		try {
			is = new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath), "UTF-8");
			properties.load(is);
		}catch(IOException ioe) {
			LOGGER.error(filePath + "读取异常：" + ioe); 
		}finally {
			is.close();
		}
		return properties.getProperty(key);
	}
}

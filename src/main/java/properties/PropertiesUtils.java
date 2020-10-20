package properties;

import config.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @ClassName PropertiesUtils
 * @description: 读取指定的配置文件  节约内存  那是配置文件写死
 * @author: li zhi x
 * @create: 2020/6/21
 **/
public class PropertiesUtils {

    private static Properties props;

    //Tomcat运行时执行
    //代码块执行顺序：静态代码块>普通代码块>构造代码块
    //构造代码块每次都执行，但是静态代码块只执行一次
    static {
        String fileName = "init.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    /**
     * 根据key读取properties配置文件中的value值
     * @param key
     * @return
     */
    public static String getProperty(String key){
        String value= props.getProperty(key.trim());
        if (StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }

    /**
     * 读取配置文件中的value 如果没有则使用默认defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getProperty(String key,String defaultValue){
        String value= props.getProperty(key.trim());
        if (StringUtils.isBlank(value)){
            value = defaultValue;
        }
        return value.trim();
    }

    public static void main(String[] args) {
//        MyJar myJar = new MyJar();
//        myJar.getProperitiesVal2();
    }

}

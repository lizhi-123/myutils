package properties;

import config.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtils {

    private static Properties props;

    //Tomcat运行时执行
    //代码块执行顺序：静态代码块>普通代码块>构造代码块
    //构造代码块每次都执行，但是静态代码块只执行一次
    static {
        String fileName = "mmall.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {

        }
    }



    //自定义俩个get方法，方便调用工具类读取properties文件的属性
    public static String getProperty(String key){
        String value= props.getProperty(key.trim());
        if (StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue){
        String value= props.getProperty(key.trim());
        if (StringUtils.isBlank(value)){
            value = defaultValue;
        }
        return value.trim();
    }

//    public static String getValue(String property,String key){
////        props.get()
//    }

}

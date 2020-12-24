package hutools;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HutoolTest
 * @description: TODO
 * @author: li zhi x
 * @create: 2020/5/7
 **/
public class HutoolTest {

    @Test
    public void String2Date() {
        Map<String,Object> map    = new HashMap<>();
        map.put("name","张三");
        map.put("age",12);

//        System.out.println(HttpRequest.get("http://192.168.0.104:8080/test").setEncodeUrlParams(true).form(map).execute());
        String params = HttpUtil.toParams(map, StandardCharsets.UTF_8);
        String url = HttpUtil.urlWithForm("http://192.168.0.104:8080/test", map, StandardCharsets.UTF_8, false);
        System.out.println("url-->"+url);
        System.out.println(params);
        System.out.println(HttpUtil.get("http://192.168.0.104:8080/test?" + params));


    }

}

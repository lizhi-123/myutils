package json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JsonUtil
 * @description: TODO
 * @author: li zhi x
 * @create: 2020/5/8
 **/
public class JsonUtil {

    //language=JSON
    private static String jsonStr = "{\"msg\":\"success\",\"code\":200,\"result\": [{\"name\": \"张飞\",\"age\": 18},{\"name\": \"关羽\",\"age\": 19}]}";

    public static  void test01(){
        //json字符串转json
        JSONObject object = JSONObject.parseObject(jsonStr);
        System.out.println(object);
        //json字符串获取里面的list
        List result = (List) object.get("result");
        System.out.println(result.get(0));

    }

    public static void main(String[] args) {
        test01();
    }

}

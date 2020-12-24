package myworktest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTest {

    public static void main(String[] args) {
        List<Map<String, Object>> mapList = buildData();

    }

    public static List<Map<String,Object>> buildData(){
        List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("姓名","张一丰");
        map1.put("存款",154.031);
        map1.put("年龄",25);
        mapList.add(map1);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("姓名","张二丰");
        map2.put("存款",154.032);
        map2.put("年龄",25);
        mapList.add(map2);
        Map<String,Object> map3 = new HashMap<>();
        map3.put("姓名","张三丰");
        map3.put("存款",154.033);
        map3.put("年龄",25);
        mapList.add(map3);
        Map<String,Object> map4 = new HashMap<>();
        map4.put("姓名","张四丰");
        map4.put("存款",154.034);
        map4.put("年龄",25);
        mapList.add(map4);
        return mapList;

    }
}

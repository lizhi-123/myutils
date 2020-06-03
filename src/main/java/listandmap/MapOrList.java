package listandmap;


import org.junit.Test;

import java.util.*;

/**
 * @ClassName MapOrList
 * @description: map和list之间的相互转换
 * @author: li zhi x
 * @create: 2020/5/7
 **/
public class MapOrList {

//    private static final Logger log = LoggerFactory.getLogger(MapOrList.class);



    /**
     * @description: 不同map中相同字段的值相同，则将这两个map转换成
     * 一个map
     **/
    @Test
    public void mergeSameIdMap() {
        Map map1 = new HashMap<>();
        Map map2 = new HashMap<>();
        Map map3 = new HashMap<>();
        Map map4 = new HashMap<>();
        Map map5 = new HashMap<>();
        Map map6 = new HashMap<>();
        Map map7 = new HashMap<>();

        map1.put("maId", 17);
        map1.put("loginNum", 396);

        map2.put("maId", 17);
        map2.put("shareGoodsOrderNum", 396);

        map3.put("maId", 39);
        map3.put("loginNum", 3);

        map4.put("maId", 239);
        map4.put("loginNum", 1323);

        map5.put("maId", 17);
        map5.put("orderNum", 1);

        map6.put("maId", 39);
        map6.put("loginNum", 3);

        map7.put("maId", 17);
        map7.put("addFamerInfoNum", 24);

        List<Map> list = new ArrayList();
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        list.add(map7);
        System.out.println("原本的list" + list);
        //合并相同map
        for (int i = 0; i < list.size(); i++) {
            Map map = list.get(i);
            // 从当前项的下一项开始检查
            for (int j = i + 1; j < list.size(); j++) {
                Map<String, Object> childMap = list.get(j);
                // ID 相等
                if (null != map.get("maId") & null != childMap.get("maId")) {
                    if (map.get("maId").equals(childMap.get("maId"))) {
                        // 合并，此处需要先 remove
                        list.remove(childMap);
                        map.putAll(childMap);
                    }
                }

            }
        }
        System.out.println("合并后的list:" + list);
//原本的list[{maId=17, loginNum=396}, {shareGoodsOrderNum=396, maId=17}, {maId=39, loginNum=3}, {maId=239, loginNum=1323}, {orderNum=1, maId=17}, {maId=39, loginNum=3}, {maId=17, addFamerInfoNum=24}]
//合并后的list:[{shareGoodsOrderNum=396, orderNum=1, maId=17, loginNum=396, addFamerInfoNum=24}, {maId=239, loginNum=1323}, {maId=39, loginNum=3}]
    }

    /**
     * 对象转为map
     * */
    public static Map<String, Object> makeStrObj(Object ...paramArray){
        Map<String, Object> map=new HashMap<>();
        for(int i=0; i<paramArray.length-1; i=i+2){
            map.put(paramArray[i].toString(), paramArray[i+1]);
        }
        return map;
    }

    public static void main(String[] args) {
        Map<String, Object> map = makeStrObj("a", "b", "v","ss");
        for (String s : map.keySet()) {
            System.out.println(s+":"+map.get(s));
        }

    }
}

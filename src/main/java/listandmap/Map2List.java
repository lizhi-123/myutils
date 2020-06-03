package listandmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Map2List {

    //将map中有相同key的值相同的map去重后合并成一个map，并放到list中
    public List<Map<String,Object>>  map2List1( List<Map<String,Object>> mapList) {
       //答案
        // [{id=1, name=zs, age=18},
        // {id=2, name=ww, age=null},
        // {id=3, name=zl, age=19, sex=girl}]
        Map<String,Object> map1 = new HashMap<>();
        map1.put("id","1");
        map1.put("name","zs");
        map1.put("age",null);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("id",1);
        map2.put("age","18");

        Map<String,Object> map3 = new HashMap<>();
        map3.put("id","2");
        map3.put("name","ww");
        Map<String,Object> map4 = new HashMap<>();
        map4.put("id","2");
        map4.put("age",null);

        Map<String,Object> map5 = new HashMap<>();
        map5.put("id","3");
        map5.put("name","zl");
        map5.put("age",19);
        map5.put("sex","girl");

        Map<String,Object> map6 = new HashMap<>();
        map6.put("id",null);
        map6.put("name","zf");
        map6.put("age",19);
        map6.put("sex","girl");

        Map<String,Object> map7 = new HashMap<>();
        map7.put(null,"");
        map7.put("name","zf");
        map7.put("age",20);
        map7.put("sex","boy");

        List<Map<String,Object>> list = new ArrayList();
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        list.add(map7);
        /**
         * 从旧list中拿出一个mapA,判断新list中是否有mapB和mapA的key的值相同的，有则覆盖，没有则直接放进来
         *
         */
        List<Map<String,Object>> newList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            //旧的list中拿出一个mapA
            Map<String, Object> mapA = list.get(i);
            //如果有id才遍历获取id对应的value
            if (mapA.containsKey("id")){
                Object val = mapA.get("id");
                //把id对应的value转换成相同类型再进行比较
                String valueA = String.valueOf(val);  //源码  return (obj == null) ? "null" : obj.toString();
//                System.out.println("valueA:"+valueA);
                //排除valueA为空的结果
                if(valueA.equals("null") || valueA.length()==0) continue;
                //放到新的list中
                int contains = 0;   //假设一开始新list中不包含mapA相同的id
                if (newList.size()>0){
                    //判断新list中是否有id=valueA的map
                    for (int j = 0; j < newList.size(); j++) {
                        Map<String, Object> mapB = newList.get(j);
                      String valueB =  String.valueOf(mapB.get("id"));
                        if(valueA.equals(valueB)){
                            mapB.putAll(mapA);
                            contains = 1;
                        }
                    }
                    //循环结束，不包含则放进来
                    if (contains==0){
                        newList.add(mapA);
                    }
                    //下一次判断，恢复标记
                    contains = 0;
                }else { //第一次为空时，直接放置
                    newList.add(mapA);
                }
            }

        }
        //循环结束，输出
        System.out.println(newList);

        return newList;

    }

    




}

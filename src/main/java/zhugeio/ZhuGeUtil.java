package zhugeio;

import cn.hutool.http.HttpRequest;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class ZhuGeUtil {
    /**应用id*/
    private Integer app_id;
    /**指标 */
    private String metrics;
    /**分组条件 每次查询最多同时可以有3个分组条件 示例如下: dimensions=$day,$utm_source*/
    private String dimensions;
    /**过滤条件*/
    private String conditions;
    /**用户筛选*/
    private String user_filters;

    private String url = "http://hlbzb.tyfo.com:8800/v2/stat/";
    private String headAuthKey = "Authorization";
    private String headAuthVal = "Basic MToxMjIxZTRiMS04MGY3LTQ4ZjAtOTNmYi1jNWI2NDAyNDNlOWU=";

    public ZhuGeUtil(Integer app_id, String metrics) {
        this.app_id = app_id;
        this.metrics = metrics;
    }

    // http://hlbzb.tyfo.com:8800/v2/stat/7?
    // metrics=occurrences&conditions={"$event_name":["==","web-登录"]}

    public String executeZhuGe(){
        // 1.构建请求url
        String queryUrl = buildQueryUrl();
        System.out.println("请求诸葛完整url:"+queryUrl);
        // 2.请求诸葛
        HttpRequest httpRequest = HttpRequest.get(queryUrl).header(headAuthKey, headAuthVal)
                .setEncodeUrlParams(true);
        System.out.println(httpRequest.getUrl());
        return
                httpRequest.execute().body();
    }

    private String buildQueryUrl(){
        StringBuilder sb = new StringBuilder();
        sb.append(url).append(app_id).append("?metrics=").append(metrics).append("&");
        if (StringUtils.isNotEmpty(dimensions)){
            sb.append("dimensions=").append(dimensions).append("&");
        }
        if (StringUtils.isNotEmpty(conditions)){
            sb.append("conditions=").append(conditions).append("&");
        }
        if (StringUtils.isNotEmpty(user_filters)){
            sb.append("user_filters=").append(user_filters).append("&");
        }
        return sb.toString().substring(0,sb.length()-1);
    }
    /*

    zhuge.track("web-登录", {
        'menuName': '益农便民服务页登录'
        'ma_id'：'aaa'
        ’类型‘：
        ’maId‘:''
    },function(){
       // 跳转xxx
    });
    在’web-登录‘点击事件中,设置了自定义属性名：menuName   自定义属性值  ’益农电商集市点击登录‘、’益农便民服务页登录‘、’点击登录‘
                             自定义属性名： ma_id   自定义属性值  'aaa','bbb',''

         需求：
         查询 点击事件为 ’web-登录‘中属性名为’益农电商集市点击登录’的点击次数，按照ma_id进行分组

         select 点击次数 from table  where 点击事件=’web-登录‘ and menuName=’益农电商集市点击登录‘
            group by ma_id
    */


    public static void main(String[] args) {
        // 对自定义属性 menuName进行分组求和
//        ZhuGeUtil zhuGeUtil = new ZhuGeUtil(7,"sum(event.menuName)");
        ZhuGeUtil zhuGeUtil = new ZhuGeUtil(7,"occurrences");
        // 过滤条件 事件名 web-登录
//        zhuGeUtil.setConditions("{\"$event_name\":[\"==\",\"web-首页-便民服务-预约挂号\"],\"$day\":[\"between\",\"2020-11-01\",\"2020-11-20\"]}");
        zhuGeUtil.setConditions("{\"$event_name\":[\"==\",\"web-首页-便民服务-物流\"],\"$day\":[\"between\",\"2020-10-01\",\"2020-11-23\"]}");
//        zhuGeUtil.setConditions("{\"$event_name\":[\"==\",\"web-登录\"],\"$day\":[\"between\",\"2020-09-01\",\"2020-09-30\"],\"event.menuName\":[\"==\",\"益农便民服务页登录\"]}");
        // 分组条件 事件的自定义属性 menuName
        zhuGeUtil.setDimensions("event.maId,$day");
        System.out.println(zhuGeUtil.executeZhuGe());
    }
//        public static void main(String[] args) {
//        // web查询 114医疗挂号点击次数
//         // 1.初始化诸葛Util
//        ZhuGeUtil util = new ZhuGeUtil(7,"occurrences");
//        // 1.对自定义属性 menuName进行分组求和
//        String conditions = "{\"$event_name\":[\"==\",\"首页-便民服务\"],$day\":[\"between\",\"2020-11-01\",\"2020-11-17\"],\"event.位置\":[\"==\",\"114挂号\"]}";
//        util.setConditions(conditions);
//        // 2.过滤条件 事件名 web-登录
//        String dimensions ="$day,event.maid";
//        util.setDimensions(dimensions);
//        // 3.分组条件 事件的自定义属性 menuName
//        String executeZhuGe = util.executeZhuGe();
//        System.out.println(executeZhuGe);
//    }


}

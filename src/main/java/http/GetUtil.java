package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

class GetUtil {

    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        String urlName = url;
        if (param!=null){
             urlName = url + "?" + param;
        }
        System.out.println("urlName:"+urlName);
        try {
            URL realURL = new URL(urlName);
            URLConnection conn = realURL.openConnection();
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.113 Safari/537.36");
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            for (String s : map.keySet()) {
                System.out.println(s + "-->" + map.get(s));
                
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line =null;
            while ((line = in.readLine()) != null) {
                result.append("\n").append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    public static class App {
        public static void main(String[] args) throws Exception {
            String url = "http://wap.scgh114.com/singleLogin/authtoken";
            String param = "appkey=yinong_app&phone=18950905558&code=a826eebce4934478ae593c5c0c3bcbda&sign=6EFA8B33EB4F56EB16731FBFE50A1BE3&qDT2VUhE=qGrpkqr0wop0wop0wok_zF3RB9jTwYZjWlCuNcUVbgWqqoA";
            String zhugeUrl = "http://hlbzb.tyfo.com:8800/v2/stat/7?metrics=occurrences&dimensions=$day,event.maid&conditions={\"$event_name\":[\"==\",\"首页-便民服务\"],$day\":[\"between\",\"2020-11-01\",\"2020-11-17\"],\"event.位置\":[\"==\",\"114挂号\"]}";
            String result = sendGet(zhugeUrl, null);
            System.out.println(result);
        }

    }
}





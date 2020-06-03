package http;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class PostUtil {

    public static void main(String[] args) {
//        String url = "https://m.sc.189.cn/DQCSB/UnionHttpByNoLogin/POST/RAW/qryGridData";
//        Map<String,String> queryMap = new HashMap<>();
//        queryMap.put("staffCode","2045916");
//        queryMap.put("qryTime","20200520");
//        String param = JSONObject.toJSONString(queryMap);
//        System.out.println("请求的json数据"+param);
//        System.out.println(sendPost(url, param));
        String url = "http://localhost:8098/springmvc/excelServer/privideUrl2";
        System.out.println(sendPost(url, null));

    }

    /**
     * 使用post请求，application/json
     * @param url 请求路径
     * @param param json字符串
     * @return 响应输出对象
     */
    public static String sendPost(String url,String param){
        OutputStreamWriter out =null;
        BufferedReader reader = null;
        String response = "";

        //创建连接
        try {
            URL httpUrl = null; //HTTP URL类 用这个类来创建连接
            //创建URL
            httpUrl = new URL(url);
            //建立连接
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setUseCaches(false);//设置不要缓存
            conn.setInstanceFollowRedirects(true);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //POST请求
            out = new OutputStreamWriter(
                    conn.getOutputStream());
            if (param!=null) {
                out.write(param);
            }
            out.flush();
            //读取响应
            reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes("UTF-8"), "UTF-8");
                response+=lines;
            }
            reader.close();
            // 断开连接
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(reader!=null){
                    reader.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return response;
    }
}

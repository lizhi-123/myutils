package http;

import org.junit.Test;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName HttpUtil
 * @description: http请求
 * @author: li zhi x
 * @create: 2020/5/7
 **/
public class HttpUtil {
//    private static String urlStr = "http://wap.scgh114.com/singleLogin/authtoken?appkey=yinong_app&phone=18950905558&code=a826eebce4934478ae593c5c0c3bcbda&sign=6EFA8B33EB4F56EB16731FBFE50A1BE3";
    private static String urlStr = "http://localhost:8098/springmvc/excelServer/privideUrl2";

    public static String httpRequest() {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
//           url= new URL(null, urlStr, new sun.net.www.protocol.https.Handler());
            //创建连接

            connection = (HttpURLConnection) url.openConnection();
            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//            connection.setRequestProperty("Content-Type", "application/json");
            //读取接口数据
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) { // 读取数据
                result.append(line).append("\n");
            }
            br.close();
            connection.disconnect();
            String resultStr = result.toString();
            System.out.println(resultStr);
            return resultStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送HttpPost请求
     *
     * @param strURL 服务地址
     * @param params json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
     * @return 成功:返回json字符串<br/>
     */
    public static String post(String strURL, String params) {
        System.out.println(strURL);
        System.out.println(params);
        BufferedReader reader = null;
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            // connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder res = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
            //TAG: 2020/5/8 li zhi x
            reader.close();
            return res.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "请求错误"; // 自定义错误信息
    }

    public static void main(String[] args) {
//        String url = "http://wap.scgh114.com/singleLogin/authcode";
//        String params = "appkey=yinong_app&phone=18950905558&code=a826eebce4934478ae593c5c0c3bcbda&sign=6EFA8B33EB4F56EB16731FBFE50A1BE3";
//        post(url,params);
        httpRequest();
    }
}

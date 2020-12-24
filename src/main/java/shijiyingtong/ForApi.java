package shijiyingtong;

import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReadConfig;
import cn.hutool.core.text.csv.CsvReader;

import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 世纪银通手动导单
 */
public class ForApi {

    static String path = "E:\\myselfWorkspace\\simple-utils\\2805-11-1-11.csv";
    static String brushOrderSignKey="huahuajjh3";
//    static String url = "http://yinongcz.tyfo.com:9000/aivs/yinong/trade/brushOrder";
//    static String url = "http://192.168.20.168:9000/aivs/yinong/trade/brushOrder";
    static String url = "";
    static String  merchBackUrl = "http://yinongcz.tyfo.com:9000/aivs/yinong/trade/brushOrderCallBack";


    public static void main(String[] args) {
        CsvData orders = getOrders();
        //使用多个线程处理集
        sendData(orders);
    }



    public static CsvData getOrders(){
        CsvReadConfig config = new CsvReadConfig();
        config.setContainsHeader(true);
        config.setErrorOnDifferentFieldCount(true);
        CsvReader reader = new CsvReader(config);
        CsvData read = reader.read(new File(path), StandardCharsets.UTF_8);
        System.out.println("标题》》》"+read.getHeader());
        return read;
    }

    public static void sendData(CsvData orders) {

        List<CsvRow> rows = orders.getRows();
        try {
            ThreadRead.readList(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void querySave(List<CsvRow> rows) {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        for (CsvRow row : rows) {//每行数据请求一次
            String merchNo = row.getByName("\uFEFFmerchNo");
            String account = row.getByName("account");
            String productId = row.getByName("productId");
//            String parameter = row.getByName("parameter");
            String parameter = "2";
            String amt = row.getByName("amt");
            String orderNo = row.getByName("orderNo");

            paramMap.put("merchNo", merchNo);
            paramMap.put("account", account);
            paramMap.put("productId", productId);
            paramMap.put("parameter", parameter);
            paramMap.put("amt", String.valueOf(amt));
            paramMap.put("orderNo", orderNo);
            paramMap.put("merchParam", "12");

            // 签名
            String signParams = HttpRequest.formatUrlParams("merchNo", merchNo,
                    "account", account,
                    "productId", productId,
                    "amt", String.valueOf(amt),
                    "parameter", parameter,
                    "orderNo", orderNo,
                    "merchBackUrl", merchBackUrl);
            String signString = MD5.sign(signParams, brushOrderSignKey, "GB2312");

            paramMap.put("signMsg", signString);


            String param = HttpRequest.formatUrlParams("merchNo", merchNo,
                    "account", account,
                    "productId", productId,
                    "amt", String.valueOf(amt),
                    "parameter", parameter,
                    "orderNo", orderNo,
                    "merchBackUrl", merchBackUrl,
                    "signMsg", signString);

            String s = HttpRequest.sendGet(url, param);
            System.out.println(Thread.currentThread().getName()+"请求结果：" + s);


//            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
//                System.out.print(entry.getKey() + "=" + entry.getValue() + "\t");
//            }
//            System.out.println();

            //请求map
            paramMap.clear();
        }
    }

}

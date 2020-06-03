package http;

import cn.hutool.http.HttpUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientUtils {

    @Test
    public void doGetTestOne() throws IOException {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet("http://localhost:8098/springmvc/excelServer/provideUrl/2");
//        httpGet.setHeader("Content-Type","text/json;charset=utf-8");  //应该是设置请求头
        // 响应模型
        CloseableHttpResponse response = httpClient.execute(httpGet);
        Header[] allHeaders = response.getAllHeaders();
        for (Header header : allHeaders) {
            System.out.println(header.getName() + "===" + header.getValue());
        }
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        String a = EntityUtils.toString(responseEntity);
        System.out.println("获取链接："+a);
        //下载资源
//        readFileJoinTender("http://localhost:8098/springmvc/excel/sheet1.xls");
//        readFileJoinTender("http://"+a);

        long l = HttpUtil.downloadFile("http://localhost:8098/springmvc/excel/sheet1.xls", "C:\\Users\\Administrator\\Desktop\\excels");
        System.out.println(l);

    }


    public static void readFileJoinTender(String path) {
        //excel文件路径
        try {
            Workbook wb =getWorkbook(path);

            //开始解析
            //读取sheet 0
            Sheet sheet = wb.getSheetAt(0);
            //第一行是列名，所以不读
            int firstRowIndex = sheet.getFirstRowNum() + 1;
            int lastRowIndex = sheet.getLastRowNum();

            //遍历行
            for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                Row row = sheet.getRow(rIndex);

                if (row != null) {
                    int indexMin=row.getFirstCellNum();
                    int indexMax=row.getLastCellNum();
                    for (int i = indexMin; i < indexMax; i++) {
                        Cell cell=row.getCell(i);
                        if(cell!=null){
                            System.out.println(cell.toString());
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Workbook getWorkbook(String path) throws Exception{
        String type = path.substring(path.lastIndexOf(".") + 1);
        Workbook wb;
        //根据文件后缀（xls/xlsx）进行判断
        InputStream input = new URL(path).openStream();
        if ("xls".equals(type)) {

            //文件流对象
            wb = new HSSFWorkbook(input);
        } else if ("xlsx".equals(type)) {
            wb = new XSSFWorkbook(input);
        } else {
            throw new Exception("文件 类型错误");
        }
        return wb;
    }





    /**
     * 正则获取字符编码
     * @param content
     * @return
     */
    private static String getCharSet(String content){
        String regex = ".*charset=([^;]*).*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if(matcher.find())
            return matcher.group(1);
        else
            return null;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {

        String s1 = new String("中文乱码");
        System.out.println(s1);
        System.out.println(getEncoding(s1));
        String s2 = new String("中文乱码".getBytes("GBK"),"UTF-8" );
        System.out.println(s2);
        System.out.println(getEncoding(s2));
    }


    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }


}

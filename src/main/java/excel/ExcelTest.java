package excel;

import cn.hutool.core.convert.impl.DateConverter;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelUtil;
import config.PropertiesValue;
import http.HttpClientUtils;
import md5.Md5Util;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class ExcelTest {


    @Test
    public void test02() {
        //
        String loadPath =  "C:\\Users\\Administrator\\Desktop\\excels";
        long l = HttpUtil.downloadFile("http://localhost:8098/springmvc/excel/sheet1.xls", loadPath);
        //parse excel



    }

    /**
     * 1.定时任务调用controller接口
     * public String searchDigtalSalesInfo(String formatDate)
     * 2.controller接口使用httpUtil下载excel到本地
     * public String saveDigtalSalesInfo(String formatDate)
     * 3.service解析excel中的数据，将数据存放到对应表中
     *
     */
    public static String searchDigtalSalesInfo(String formatDate) throws IOException {
       return saveDigtalSalesInfo(formatDate);
    }

    public static String saveDigtalSalesInfo(String formatDate) throws IOException {
        //1.获取配置文件中数字小店链接  文件保存路径
        ResourceBundle resource = ResourceBundle.getBundle("config/digtal");
        String url = resource.getString("testUrl").trim();
        String savePath = resource.getString("savePath").trim();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = df.format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //获取文件后缀  假设是.xls
        File file = new File(savePath+"/"+uuid+date+".xls");
        //2.使用hutools下载链接文件，保存到指定位置
        long l = HttpUtil.downloadFile(url, file);
        //3.读取文件中的数据，封装成相应对象
        System.out.println(l);
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
        int numberOfSheets = workbook.getNumberOfSheets();

        return null;
    }

    public static void main(String[] args) throws IOException {
//        String testString = "http://localhost:8080/mvc/abc?name=张三&age=19";
//        String encoderString = URLEncoder.encode(testString, "utf-8");
//        System.out.println(encoderString);
//        String decodedString = URLDecoder.decode(encoderString, "utf-8");
//        System.out.println(decodedString);
//        String newFileName = UUID.randomUUID().toString();
//        System.out.println(newFileName);
//        searchDigtalSalesInfo("2020");
        try {
            // ClassPathResource类的构造方法接收路径名称，自动去classpath路径下找文件
            ClassPathResource classPathResource = new ClassPathResource("test/test.txt");
            // 获得File对象，当然也可以获取输入流对象
            File file = classPathResource.getFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file)); //相对文件路径，如果是放在项目文件夹下，则为new FileReader("test.txt");
            String string;
            while ((string=bufferedReader.readLine()) != null){
                System.out.println(string);
            }
            System.out.println(string);
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}

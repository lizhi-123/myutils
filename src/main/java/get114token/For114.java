package get114token;


import md5.Md5Util;

public class For114 {

    public static void main(String[] args) {
        //百度回调获取的code
        String baiDuHuiDiao = "http://wap.scgh114.com/singleLogin/authcode?phone=17608065223&appkey=yinong_app&redirecturl=http://www.baidu.com?phone=17608065223";
        String code_ = "";
        String phone = "17608065223";
        String secretkey = "YN4tN72UNOkGL2ns79H46Nyinong114G";
        String appkey = "yinong_app";  ///channelnumber
        String paramsUnion = secretkey + "appkey" + appkey + "code" + code_ + "phone" + phone;
        String sign = Md5Util.GetMD5Code(paramsUnion).toUpperCase();
        System.out.println("生成的签名：" + sign);


        //获取token的url
       String  hospital114Url  ="http://wap.scgh114.com"+"/singleLogin/authtoken";
       //拼接好的链接去请求114获取token
        String token114url ="Wap.scgh114.com/singleLogin/authtoken?appkey=yinong_app&phone=18900000000&code=117de2a6fe564440a50cd4&sign=A92C1B8FAA5C222AE82B";



//        "web/http%3A%2F%2Fwap.scgh114.com%2Fwap%2Findex%3Fappkey%3Dyinong_app%26phone%3D18981815323%26token%3D1A80D876FB72982210D8FC9FDDDB7F00";
//        web/http://wap.scgh114.com/wap/index?appkey=yinong_app&phone=18981815323&token=1A80D876FB72982210D8FC9FDDDB7F00

    }
}

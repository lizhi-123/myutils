package md5;


import org.junit.Test;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Md5Util {
//    private static final Logger log = LoggerFactory.getLogger(Md5Util.class);

    // 全局数组
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 获取114加密的签名
     */
    @Test
    public void get114SecretSign() {


        //114正式挂号KEY
        String secretkey = "YN4tN72UNOkGL2ns79H46Nyinong114G";
        //114挂号渠道号
        String appkey = "yinong_app";
        //114接口获取的code
        String code = "a826eebce4934478ae593c5c0c3bcbda";
        String newCode = code.substring(code.length() - 32); //只要后32位，去空格
        //挂号电话
        String phone = "18950905558";
        //组合参数
        String paramsUnion = secretkey + "appkey" + appkey + "code" + newCode + "phone" + phone;
        //加密
        String sign = Md5Util.GetMD5Code(paramsUnion).toUpperCase();
        System.out.println("加密后的sign:" + sign);

    }

    /**
     * 生成32位小写加密字符串
     *
     * @param strObj
     * @return
     */
    public static String GetMD5Code(String strObj) {
        String m  = "{\"result\": [{\"code\": 2000},{\"msg\":\"sss\"}]}";

        if (strObj == null) {
            return "";
        }
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes("utf-8")));
        } catch (Exception ex) {
            throw new RuntimeException("md5加密异常：" + ex.getMessage());
        }
        return resultString;
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }
}

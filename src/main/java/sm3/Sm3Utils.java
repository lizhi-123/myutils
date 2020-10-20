package sm3;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class Sm3Utils {

    private static final Logger logger = LoggerFactory.getLogger(Sm3Utils.class);

    /**
     * sm3算法加密
     * @param paramStr 待加密字符串
     * @return 加密后 固定长度为64的16进制字符串
     */
    public static String encrypt(String paramStr) {
        String resultHexString = "";
        try {
            byte[] scrData = paramStr.getBytes("utf-8");
            byte[] resultHash = hash(scrData);
            //将返回的hash值转换成16进制字符串
            resultHexString = ByteUtils.toHexString(resultHash);
        } catch (UnsupportedEncodingException e) {
            logger.info("加密出错，加密数据：{}",paramStr);
            e.printStackTrace();
        }
        return resultHexString;
    }

    /**
     *  返回长度为32的byte数组
     * @explain 生成对应的hash值
     * @param srcData
     * @return
     */
    public static byte[] hash(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    public static void main(String[] args) {
        String encrypt = encrypt("722ac82f661f4acc81333f78ac68502c李四");
        System.out.println(encrypt);
        System.out.println(encrypt.equals("66c86d09c68857aae78be14fc0b839572275ae3f507f43f6583ef4125daeefe4"));
    }
}

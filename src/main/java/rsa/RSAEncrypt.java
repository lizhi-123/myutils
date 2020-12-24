package rsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RSAEncrypt
 * @description: 使用RSA一般需要产生公钥和私钥，当采用公钥加密时，使用私钥解密；采用私钥加密时，使用公钥解密。
 * @author: li zhi x
 * @create: 2020/9/16
 **/
public class RSAEncrypt {


    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static Map<Integer, String> genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        Map<Integer,String> keyMap = new HashMap<>();
        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
        return keyMap;
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        System.out.println(">>>>"+new String(decoded));
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    public static void main(String[] args) throws Exception {
         String public_key="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDbykZh8nGugR4hMUtXeQBpMFhif0JXBAzR8KCLapoXtMfO6vXrkKU1ZtBxGAg7nnyItbEkAMNB3ONQcaI4naXnfsjbs083bE9dkvtov8VeHyHM7n/0O6ULcZsFTJvP1z5Z4aifzjh4DZU3Fsw2TT2xz1k9WKibeFtOxLj21XNccQIDAQAB";
         String secret_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANvKRmHyca6BHiExS1d5AGkwWGJ/QlcEDNHwoItqmhe0x87q9euQpTVm0HEYCDuefIi1sSQAw0Hc41Bxojidped+yNuzTzdsT12S+2i/xV4fIczuf/Q7pQtxmwVMm8/XPlnhqJ/OOHgNlTcWzDZNPbHPWT1YqJt4W07EuPbVc1xxAgMBAAECgYAGeCC0N1ejREenwHSwQW6Cqhqf45EHUKYz87o2AFBCzF5pN78/TMWgGcOe4I81ege+WmK5ZZBZuu+x6XZV5kYAyVwjYojBoqKQnfF4r0DUCrUf4SGSDqJQTrXw5yo7JpXzbPuOs82FlOZ0SIZO4zX6nG04ox1ft/0iBRFN1iAkQQJBAPwiksPBmPrX57O7vEfNq707WTJZ4BFvuQfgj0aM7YyMGcKhwYJ75IQBSUGgriX50vskTrTay33zHwrsAZQFYpkCQQDfKMYgPU5dzH6yP3UqIXYxcELdAF0hAIK3dtReByUR4eRtMiyMNF0R3hcb059PGKFUbZdnazrlMWAjh0wf20eZAkBAqQEocLKxycLjBgdABs+/RMQYNJJRBmzWR1GXDzcwbxGAJ4l/1BQDgmzuBq4CkTH5NBN3MBE1qK7SVzoEYukpAkEAoiyUh1tmNx5kuI8LS5nTtiv6O3eHNnOTi1atEMQqeWtrQLvkyeNH+7MlohBRxv6ER8H49Kxluaf/UPKDLOeDiQJADpXpvFeIXsRyMxaoR45543emA40hNsYe/wrv2Rzb188R2d/WdHg9dhUtoIql/KK92usGlGJL5ffG4I0TyUFr3A==";

        //生成公钥和私钥
        Map<Integer, String> keyMap = genKeyPair();
        String publicKey = keyMap.get(0);
        String privateKey = keyMap.get(1);
        System.out.println("publicKey:"+publicKey);
        System.out.println("privateKey:"+privateKey);
        String name = "张三丰";
        //加密
        String encrypt = encrypt(name, public_key);
        System.out.println(encrypt);
        //解密
        String  decrypt = decrypt(encrypt,secret_key);
        System.out.println(decrypt);


    }

}

package com.lizhi;

import static org.junit.Assert.assertTrue;

import cn.hutool.core.text.StrSpliter;
import cn.hutool.system.UserInfo;
import com.alibaba.fastjson.JSON;
import entity.User;
import md5.Md5Util;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import javax.sound.midi.VoiceStatus;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {

//       String s ="asdbcb";
//        int end = s.indexOf("bcbs");
//        s.substring(0,end);

        String date = "2020-05-20";
        System.out.println(date.replace("-", ""));
    }



    public static void test01() throws UnsupportedEncodingException {
//        Properties initProp = new Properties(System.getProperties());
//        System.out.println("当前系统编码:" + initProp.getProperty("file.encoding"));
//        System.out.println("当前系统语言:" + initProp.getProperty("user.language"));

//        File file = new File("c:/" + "/" + "sheet2.xsl");

        String md5Hex = DigestUtils.md5Hex("#sFvL0cSYP");
        System.out.println(md5Hex);
        int[] nums ={1,1,2};
//        System.out.println(removeTheAgain02(nums));
//        for (int num : nums) {
//            System.out.println(num);
        }


    public static void main(String[] args) {
        int[] arr = new int[]{0,0,1,1,1,2,2,3,3,4};
        System.out.println(removeDuplicates(arr));
        for (int i : arr) {
            System.out.println(i);
        }
    }
    public static int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 1){
            return nums.length;
        }
        int i = 0;
        int j = 1;
        for(j = 1; j < nums.length;){
            if(nums[i] == nums[j]){
                j++;
            }else{
                i++;
                nums[i] = nums[j];
                j++;
            }
        }
        return i + 1;
    }

    @Test
    public void test03(){
        String json =
                "{\"name\": \"demoData\",\n"
                        + "\"gender\": \"demoData\",\n"
                        + "\"age\": 1,\n"
                        + "\"action\":\"eatApple\"\n }";
//        return JSON.toJavaObject(.)
    }


}

package mytest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test00 {
    /**
     * 判断是否包含汉字
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        boolean b = isContainChinese("123励志");
    }
}

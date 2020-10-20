package reg;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegTest {
    @Test // 问新家  101
    public void test01(){
        String content = "runooob";
        String pattern ="runo?b"; //前面字符出现次数小于等于1次
        String pattern2 = "runo*b"; //前面字符出现次数大于0次
        String pattern3 ="runo+b"; //前面字符出现次数大于等于1次
        System.err.println(Pattern.matches(pattern,content));
        System.err.println(Pattern.matches(pattern2,content));
        System.err.println(Pattern.matches(pattern3,content));
    }
    @Test // [] 匹配中括号中所有的字符  注意是字符
    public void test02(){
        String reg = "[a-z]";
        System.out.println( Pattern.matches(reg,"a")); //true
        System.out.println( Pattern.matches(reg,"ab")); //false
        String reg2 ="[abcdefg]";
        System.out.println(Pattern.matches(reg2,"a"));

    }
}

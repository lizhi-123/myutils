import cn.hutool.core.lang.Console;
import md5.Md5Util;

import java.util.*;

public class SimpleMain {

    public static final int   MAX_VALUE = 0x7fffffff;

    public static void main(String[] args) {
       Student student = new Student("张飞",19);
       concat(student);
        System.out.println(student);
    }


    public static void concat(Student student){
       student.setName("李逵");

    }

}

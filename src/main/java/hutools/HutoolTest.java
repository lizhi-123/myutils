package hutools;

import cn.hutool.core.convert.Convert;
import org.junit.Test;

import java.util.Date;

/**
 * @ClassName HutoolTest
 * @description: TODO
 * @author: li zhi x
 * @create: 2020/5/7
 **/
public class HutoolTest {

    @Test
    public void String2Date() {
        String a = "2017-05-06";
        Date dataA = Convert.toDate(a);
        System.out.println(dataA); //Sat May 06 00:00:00 CST 2017
        String b = "2019/08/08 12:23:22";
        Date datab = Convert.toDate(b);
        System.out.println(datab); //Thu Aug 08 12:23:22 CST 2019
    }

}

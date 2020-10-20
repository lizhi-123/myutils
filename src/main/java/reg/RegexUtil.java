package reg;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * 所有常用正则匹配规则
 */
public class RegexUtil {
    /*必须包含大写字母、小写字母、数字、特殊字符中其中3个以上，且密码总长度不低于8位*/
    private static final String
            PASSWORD = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]{8,16}$";

    public static boolean checkPassWord(String input){
        return Pattern.matches(PASSWORD,input);
    }

}

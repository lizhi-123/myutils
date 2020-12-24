package poi.multiTitleBar.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelAnno {
    /**
     * 标题名称
     */
    String value();

    /**
     * 对应excel列坐标
     */
    int index();

}

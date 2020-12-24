package areaCode.jsonUtilr;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AreaForm {

//    n为名称，i为id，p为pid，y为拼音前缀
    @JSONField(name = "n")
    private String area_name;
    @JSONField(name = "i")
    private Integer area_code;
    @JSONField(name = "p")
    private Integer p_area_code;
    @JSONField(name = "y")
    private String pinyin;

}

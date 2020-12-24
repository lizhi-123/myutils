package areaCode;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class Area {
    /**名称*/
    private String name;
    /**主键*/
    private Integer laaId; //
    /**父主键*/
    private Integer pLaaId; //
    /**等级  0：国家 1：省  2：市  3：县  4：乡/镇  5：村*/
    private Integer laaLevel;
    /**邮编*/
    private Integer postCode;
    /**经纬度*/
    private String latitude;
    /**行政区域代码*/
    private String regionCode;
    /**父级行政区域代码*/
    private String pRegionCode;
    /**下级的链接地址*/
    private String subUrl;

    @Override
    public String toString() {
        return "Area{" +
                "name='" + name + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", pRegionCode='" + pRegionCode + '\'' +
                ", subUrl='" + subUrl + '\'' +
                '}';
    }
}

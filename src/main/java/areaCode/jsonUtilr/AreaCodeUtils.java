package areaCode.jsonUtilr;

import cn.hutool.core.io.FileUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.alibaba.fastjson.JSONArray;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

public class AreaCodeUtils {

    public static void main(String[] args) throws SQLException {
        String path = "E:\\myselfWorkspace\\simple-utils\\src\\main\\resources\\file\\area_format_array.json";
        String s = FileUtil.readString(path, StandardCharsets.UTF_8);
        List<AreaForm> areaForms = JSONArray.parseArray(s, AreaForm.class);
        //存入mysql 四川省 51 成都市 5101 大邑县 510129 安仁镇 510129105
        for (AreaForm areaForm : areaForms) {
            Db.use().insert(
                    Entity.create("areacode_base")
                            .set("area_code",areaForm.getArea_code())
                            .set("area_name",areaForm.getArea_name())
                            .set("p_area_code",areaForm.getP_area_code())
            );
        }

    }

}

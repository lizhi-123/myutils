package zhugeio;

import lombok.Data;

/**
 * @ClassName ZhuGeParamsAttribute
 * @description: 诸葛请求的参数属性
 *          诸葛文档：https://docs.zhugeio.com/open/api.html
 * @author: li zhi x
 * @create: 2020/11/11
 **/
@Data
public class ZhuGeParamsAttribute {
    /**应用id*/
    private Integer app_id;
    /**指标 */
    private String metrics;
    /**分组条件 每次查询最多同时可以有3个分组条件 示例如下: dimensions=$day,$utm_source*/
    private String dimensions;
    /**过滤条件*/
    private String conditions;
    /**用户筛选*/
    private String user_filters;


    /**app_id和metrics为必填参数*/
    public ZhuGeParamsAttribute(Integer app_id, String metrics) {
        this.app_id = app_id;
        this.metrics = metrics;
    }





}

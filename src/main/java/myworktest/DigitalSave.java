package myworktest;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;


import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

/**
 * 解析数据小店传递的json数据
 */
public class DigitalSave {



    private static String jsonMsg = JsonMsg.jsonMsg;

    //将字符串转换成json对象，获取对象中的msg
    //将msg中的数组对象拿出来

    //  海报扫码对象 posterScanNum
    //  登录次数统计 loginCount
    //  数字小店订单信息
    public  static  void getMsg(){
        JSONObject resultObject = JSON.parseObject(jsonMsg);
        String message = resultObject.getString("message");
        JSONObject object = JSON.parseObject(message);
        if(!"0".equals(resultObject.get("retCode"))) {
            throw new RuntimeException("查询云商店铺状态失败");
        }
//        //  登录事件对象loginLogEvtList
//        TMDigitalSalesVO digitalSalesVO = new TMDigitalSalesVO();
//        List<Map<String,Object>> businessOrders = (List<Map<String, Object>>) object.get("businessOrders");
//        digitalSalesVO.setBusinessOrders(businessOrders);
//        System.out.println(digitalSalesVO);
//        List<TMSalesLoginVO> salesLoginVOS = (List<TMSalesLoginVO>) object.get("loginLogEvtList");
//        for (TMSalesLoginVO vo : salesLoginVOS) {
//            System.out.println(vo);
//        }

        JSONArray array = (JSONArray) object.get("loginLogEvtList");
        List<TMSalesLoginVO> salesLoginVOS = array.toJavaList(TMSalesLoginVO.class);
        for (int i = 0; i < salesLoginVOS.size(); i++) {
            System.out.println(salesLoginVOS.get(i));
        }


    }



    public static void main(String[] args) {
        getMsg();
    }




}

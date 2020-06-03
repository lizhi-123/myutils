package myworktest;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TMDigitalSalesVO
 * @description: TODO  数字小店协消员vo
 * @author: li zhi x
 * @create: 2020/5/24
 **/
public class TMDigitalSalesVO {

    private List<Map<String,Object>> loginLogEvtList;
    private List<Map<String,Object>> businessOrders;
    private Integer  loginCount;
    private List<Map<String,Object>> posterScanNum;

    public TMDigitalSalesVO() {
    }

    public List<Map<String, Object>> getLoginLogEvtList() {
        return loginLogEvtList;
    }

    public void setLoginLogEvtList(List<Map<String, Object>> loginLogEvtList) {
        this.loginLogEvtList = loginLogEvtList;
    }

    public List<Map<String, Object>> getBusinessOrders() {
        return businessOrders;
    }

    public void setBusinessOrders(List<Map<String, Object>> businessOrders) {
        this.businessOrders = businessOrders;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public List<Map<String, Object>> getPosterScanNum() {
        return posterScanNum;
    }

    public void setPosterScanNum(List<Map<String, Object>> posterScanNum) {
        this.posterScanNum = posterScanNum;
    }

    @Override
    public String toString() {
        return "TMDigitalSalesVO{" +
                "loginLogEvtList=" + loginLogEvtList +
                ", businessOrders=" + businessOrders +
                ", loginCount=" + loginCount +
                ", posterScanNum=" + posterScanNum +
                '}';
    }
}

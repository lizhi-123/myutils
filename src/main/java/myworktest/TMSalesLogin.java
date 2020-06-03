package myworktest;

import java.io.Serializable;

/**
 * @ClassName TMSalesLoginVO
 * @description: TODO 协销员登录信息vo
 * @author: li zhi x
 * @create: 2020/5/24
 **/
public class TMSalesLogin implements Serializable {
    /**
     *              "accountNum": "oSEKSswI_Bwdixt3SLiU7NxNXa2Q",
     * 				"accountNumId": 9501199,
     * 				"accountType": 2,
     * 				"channelCode": "DIGITAL_STORE",
     * 				"createTime": "2020-05-20 20:20:21",
     * 				"id": 5174657,
     * 				"remark1": "5100343A",
     * 				"remark2": null,
     * 				"remark3": null,
     * 				"status": "E",
     * 				"updateTime": "2020-05-20 20:20:21",
     * 				"userId": 9452235
     */
    private String accountNum;
    private String accountNumId;
    private Integer accountType;
    private String channelCode;
    private String createTime;
    private String id;
    private String remark1;
    private String status;
    private String updateTime;
    private Integer userId;


    public TMSalesLogin() {
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountNumId() {
        return accountNumId;
    }

    public void setAccountNumId(String accountNumId) {
        this.accountNumId = accountNumId;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "TMSalesLoginVO{" +
                "accountNum='" + accountNum + '\'' +
                ", accountNumId='" + accountNumId + '\'' +
                ", accountType=" + accountType +
                ", channelCode='" + channelCode + '\'' +
                ", createTime='" + createTime + '\'' +
                ", id=" + id +
                ", remark1='" + remark1 + '\'' +
                ", status='" + status + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", userId=" + userId +
                '}';
    }
}

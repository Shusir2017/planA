package com.shusir.plana.bean;

/**
 * Created by SuSir on  2017/8/17 0017
 * <br>Explanation:
 */
public class UserInfo {
    private String userName;
    private String userTotalMoney;
    private String userNetAsset;
    public UserInfo(){}

    public UserInfo(String userName, String userTotalMoney, String userNetAsset) {
        this.userName = userName;
        this.userTotalMoney = userTotalMoney;
        this.userNetAsset = userNetAsset;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTotalMoney() {
        return userTotalMoney;
    }

    public void setUserTotalMoney(String userTotalMoney) {
        this.userTotalMoney = userTotalMoney;
    }

    public String getUserNetAsset() {
        return userNetAsset;
    }

    public void setUserNetAsset(String userNetAsset) {
        this.userNetAsset = userNetAsset;
    }



}

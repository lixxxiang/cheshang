package com.android.lixiang.cheshang.presenter.data.greenDao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by vslimit on 17/1/21.
 */
@Entity
public class User {
    @Id
    private Long id;
    private String hrAccount;
    private String deviceId;
    private String name;
    private String tel;
    private String shopId;
    private String shopName;
    private String shopAddress;
    private String deptId;
    @Generated(hash = 259182200)
    public User(Long id, String hrAccount, String deviceId, String name, String tel,
            String shopId, String shopName, String shopAddress, String deptId) {
        this.id = id;
        this.hrAccount = hrAccount;
        this.deviceId = deviceId;
        this.name = name;
        this.tel = tel;
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.deptId = deptId;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHrAccount() {
        return this.hrAccount;
    }
    public void setHrAccount(String hrAccount) {
        this.hrAccount = hrAccount;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTel() {
        return this.tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getShopId() {
        return this.shopId;
    }
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public String getShopName() {
        return this.shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getShopAddress() {
        return this.shopAddress;
    }
    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }
    public String getDeptId() {
        return this.deptId;
    }
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

}

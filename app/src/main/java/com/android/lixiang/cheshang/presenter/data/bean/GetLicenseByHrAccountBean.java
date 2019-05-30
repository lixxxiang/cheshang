package com.android.lixiang.cheshang.presenter.data.bean;

import java.io.Serializable;
import java.util.List;

public class GetLicenseByHrAccountBean {

    /**
     * data : [{"brandModel":"东风牌LZ6441XQ15M","hrAccount":"1","identiCode":"LGX13948174827183","owner":"测试字段","plateNumber":"吉A12098","registDate":"2015-05-14 00:00:00","reportTime":"2018-11-08 15:55:38","reporter":"庞井洲","shopAddress":"长春市宽城区青年路3875号","shopId":"2201005000012","shopName":"长春华之城世达","tel":"12345678910"}]
     * message : success
     * status : 200
     */

    private String message;
    private int status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * brandModel : 东风牌LZ6441XQ15M
         * hrAccount : 1
         * identiCode : LGX13948174827183
         * owner : 测试字段
         * plateNumber : 吉A12098
         * registDate : 2015-05-14 00:00:00
         * reportTime : 2018-11-08 15:55:38
         * reporter : 庞井洲
         * shopAddress : 长春市宽城区青年路3875号
         * shopId : 2201005000012
         * shopName : 长春华之城世达
         * tel : 12345678910
         */

        private String brandModel;
        private String hrAccount;
        private String dueDate;
        private String engineNumber;
        private String insuranceCompany;
        private String identiCode;
        private String owner;
        private String plateNumber;
        private String registDate;
        private String reportTime;
        private String reporter;
        private String shopAddress;
        private String shopId;
        private String shopName;
        private String tel;

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public String getEngineNumber() {
            return engineNumber;
        }

        public void setEngineNumber(String engineNumber) {
            this.engineNumber = engineNumber;
        }

        public String getInsuranceCompany() {
            return insuranceCompany;
        }

        public void setInsuranceCompany(String insuranceCompany) {
            this.insuranceCompany = insuranceCompany;
        }

        public String getBrandModel() {
            return brandModel;
        }

        public void setBrandModel(String brandModel) {
            this.brandModel = brandModel;
        }

        public String getHrAccount() {
            return hrAccount;
        }

        public void setHrAccount(String hrAccount) {
            this.hrAccount = hrAccount;
        }

        public String getIdentiCode() {
            return identiCode;
        }

        public void setIdentiCode(String identiCode) {
            this.identiCode = identiCode;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public String getRegistDate() {
            return registDate;
        }

        public void setRegistDate(String registDate) {
            this.registDate = registDate;
        }

        public String getReportTime() {
            return reportTime;
        }

        public void setReportTime(String reportTime) {
            this.reportTime = reportTime;
        }

        public String getReporter() {
            return reporter;
        }

        public void setReporter(String reporter) {
            this.reporter = reporter;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
}

package com.android.lixiang.cheshang.presenter.data.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetShopsByHrAccountBean {

    private String message;
    private int status;
    private List<DataBean> data;

    public static GetShopsByHrAccountBean objectFromData(String str) {

        return new Gson().fromJson(str, GetShopsByHrAccountBean.class);
    }

    public static GetShopsByHrAccountBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), GetShopsByHrAccountBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<GetShopsByHrAccountBean> arrayGetShopsByHrAccountBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<GetShopsByHrAccountBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<GetShopsByHrAccountBean> arrayGetShopsByHrAccountBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<GetShopsByHrAccountBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

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

    public static class DataBean {
        private String address;
        private String brand;
        private String commissioner;
        private int coopStatus;
        private String deptId;
        private String endTime;
        private String fullName;
        private String insureSaler;
        private String legalPerson;
        private int property;
        private String shopId;
        private String shopName;
        private String startTime;
        private String teamLeader;
        private int urban;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static DataBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), DataBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<DataBean> arrayDataBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<DataBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCommissioner() {
            return commissioner;
        }

        public void setCommissioner(String commissioner) {
            this.commissioner = commissioner;
        }

        public int getCoopStatus() {
            return coopStatus;
        }

        public void setCoopStatus(int coopStatus) {
            this.coopStatus = coopStatus;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getInsureSaler() {
            return insureSaler;
        }

        public void setInsureSaler(String insureSaler) {
            this.insureSaler = insureSaler;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
        }

        public int getProperty() {
            return property;
        }

        public void setProperty(int property) {
            this.property = property;
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

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getTeamLeader() {
            return teamLeader;
        }

        public void setTeamLeader(String teamLeader) {
            this.teamLeader = teamLeader;
        }

        public int getUrban() {
            return urban;
        }

        public void setUrban(int urban) {
            this.urban = urban;
        }
    }
}

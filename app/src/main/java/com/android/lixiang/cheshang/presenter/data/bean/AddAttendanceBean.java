package com.android.lixiang.cheshang.presenter.data.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddAttendanceBean {
    private DataBean data;
    private String message;
    private int status;

    public static AddAttendanceBean objectFromData(String str) {

        return new Gson().fromJson(str, AddAttendanceBean.class);
    }

    public static AddAttendanceBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), AddAttendanceBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<AddAttendanceBean> arrayAddAttendanceBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<AddAttendanceBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<AddAttendanceBean> arrayAddAttendanceBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<AddAttendanceBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        private int attendanceFlag;
        private String createTime;
        private String hrAccount;
        private String latitude;
        private String longitude;
        private String position;
        private int positionFlag;
        private String shopId;

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

        public int getAttendanceFlag() {
            return attendanceFlag;
        }

        public void setAttendanceFlag(int attendanceFlag) {
            this.attendanceFlag = attendanceFlag;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHrAccount() {
            return hrAccount;
        }

        public void setHrAccount(String hrAccount) {
            this.hrAccount = hrAccount;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getPositionFlag() {
            return positionFlag;
        }

        public void setPositionFlag(int positionFlag) {
            this.positionFlag = positionFlag;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }
    }

    /**
     * status : 200
     * message : success
     * data : null
     */


}

package com.android.lixiang.cheshang.presenter.data.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UpdateLoginInfoBean {

    private DataBean data;
    private String message;
    private int status;

    public static UpdateLoginInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, UpdateLoginInfoBean.class);
    }

    public static UpdateLoginInfoBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), UpdateLoginInfoBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<UpdateLoginInfoBean> arrayUpdateLoginInfoBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<UpdateLoginInfoBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<UpdateLoginInfoBean> arrayUpdateLoginInfoBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<UpdateLoginInfoBean>>() {
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
        private String deviceId;
        private String hrAccount;
        private int jobId;
        private String name;
        private String role;
        private String tel;
        private List<DeptListBean> deptList;
        private List<?> shopList;

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

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getHrAccount() {
            return hrAccount;
        }

        public void setHrAccount(String hrAccount) {
            this.hrAccount = hrAccount;
        }

        public int getJobId() {
            return jobId;
        }

        public void setJobId(int jobId) {
            this.jobId = jobId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public List<DeptListBean> getDeptList() {
            return deptList;
        }

        public void setDeptList(List<DeptListBean> deptList) {
            this.deptList = deptList;
        }

        public List<?> getShopList() {
            return shopList;
        }

        public void setShopList(List<?> shopList) {
            this.shopList = shopList;
        }

        public static class DeptListBean {
            private String deptId;
            private String fullName;

            public static DeptListBean objectFromData(String str) {

                return new Gson().fromJson(str, DeptListBean.class);
            }

            public static DeptListBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), DeptListBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<DeptListBean> arrayDeptListBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<DeptListBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<DeptListBean> arrayDeptListBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<DeptListBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getDeptId() {
                return deptId;
            }

            public void setDeptId(String deptId) {
                this.deptId = deptId;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }
        }
    }
}

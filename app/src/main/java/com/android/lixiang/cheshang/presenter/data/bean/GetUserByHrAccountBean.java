package com.android.lixiang.cheshang.presenter.data.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetUserByHrAccountBean implements Serializable {

    /**
     * data : {"deptList":[{"deptId":"22","fullName":"吉林省公司"},{"deptId":"2201","fullName":"长春市公司"},{"deptId":"22019000","fullName":"长春市西安大路支公司"},{"deptId":"220190000002","fullName":"西安大路业务二部"}],"deviceId":"0","endTime":"17:00","hrAccount":"22049262","jobId":1,"name":"庞井洲","newDeviceId":"863125036123405","newTel":"13331649289","password":"1234567890","role":"用户","shopList":[{"address":"吉林省长春市朝阳区东朝阳胡同334号","shopId":"201811221655000228059","shopName":"长春华之城小车"},{"address":"长春市宽城区青年路3875号","shopId":"201811221655000222443","shopName":"长春华之城世达"},{"address":"长春市汽车经济技术开发区洛阳路2100号","shopId":"201811221655000225344","shopName":"长春仲昆"},{"address":"长春市长沈路4077号","shopId":"201811221655000234343","shopName":"长春建达"},{"address":"洛阳街2100号","shopId":"201811221655000235511","shopName":"省瑞成"},{"address":"长沈路和谐大街661号","shopId":"201811221655000239330","shopName":"长春华之城大车"}],"startTime":"10:00","tel":"13331649289","updateStatus":"1"}
     * message : success
     * status : 200
     */

    private DataBean data;
    private String message;
    private int status;

    public static GetUserByHrAccountBean objectFromData(String str) {

        return new Gson().fromJson(str, GetUserByHrAccountBean.class);
    }

    public static GetUserByHrAccountBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), GetUserByHrAccountBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<GetUserByHrAccountBean> arrayGetUserByHrAccountBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<GetUserByHrAccountBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<GetUserByHrAccountBean> arrayGetUserByHrAccountBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<GetUserByHrAccountBean>>() {
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

    public static class DataBean implements Serializable{
        /**
         * deptList : [{"deptId":"22","fullName":"吉林省公司"},{"deptId":"2201","fullName":"长春市公司"},{"deptId":"22019000","fullName":"长春市西安大路支公司"},{"deptId":"220190000002","fullName":"西安大路业务二部"}]
         * deviceId : 0
         * endTime : 17:00
         * hrAccount : 22049262
         * jobId : 1
         * name : 庞井洲
         * newDeviceId : 863125036123405
         * newTel : 13331649289
         * password : 1234567890
         * role : 用户
         * shopList : [{"address":"吉林省长春市朝阳区东朝阳胡同334号","shopId":"201811221655000228059","shopName":"长春华之城小车"},{"address":"长春市宽城区青年路3875号","shopId":"201811221655000222443","shopName":"长春华之城世达"},{"address":"长春市汽车经济技术开发区洛阳路2100号","shopId":"201811221655000225344","shopName":"长春仲昆"},{"address":"长春市长沈路4077号","shopId":"201811221655000234343","shopName":"长春建达"},{"address":"洛阳街2100号","shopId":"201811221655000235511","shopName":"省瑞成"},{"address":"长沈路和谐大街661号","shopId":"201811221655000239330","shopName":"长春华之城大车"}]
         * startTime : 10:00
         * tel : 13331649289
         * updateStatus : 1
         */

        private String deviceId;
        private String endTime;
        private String hrAccount;
        private int jobId;
        private String name;
        private String newDeviceId;
        private String newTel;
        private String password;
        private String role;
        private String startTime;
        private String tel;
        private String updateStatus;
        private List<DeptListBean> deptList;
        private List<ShopListBean> shopList;

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

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
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

        public String getNewDeviceId() {
            return newDeviceId;
        }

        public void setNewDeviceId(String newDeviceId) {
            this.newDeviceId = newDeviceId;
        }

        public String getNewTel() {
            return newTel;
        }

        public void setNewTel(String newTel) {
            this.newTel = newTel;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUpdateStatus() {
            return updateStatus;
        }

        public void setUpdateStatus(String updateStatus) {
            this.updateStatus = updateStatus;
        }

        public List<DeptListBean> getDeptList() {
            return deptList;
        }

        public void setDeptList(List<DeptListBean> deptList) {
            this.deptList = deptList;
        }

        public List<ShopListBean> getShopList() {
            return shopList;
        }

        public void setShopList(List<ShopListBean> shopList) {
            this.shopList = shopList;
        }

        public static class DeptListBean implements Serializable{
            /**
             * deptId : 22
             * fullName : 吉林省公司
             */

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

        public static class ShopListBean implements Serializable{
            /**
             * address : 吉林省长春市朝阳区东朝阳胡同334号
             * shopId : 201811221655000228059
             * shopName : 长春华之城小车
             */

            private String address;
            private String shopId;
            private String shopName;

            public static ShopListBean objectFromData(String str) {

                return new Gson().fromJson(str, ShopListBean.class);
            }

            public static ShopListBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), ShopListBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<ShopListBean> arrayShopListBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<ShopListBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<ShopListBean> arrayShopListBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<ShopListBean>>() {
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
        }
    }
}

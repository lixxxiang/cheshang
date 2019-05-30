package com.android.lixiang.cheshang.presenter.data.bean;

public class UpdateAttendanceByHrAccountBean {

    /**
     * data : {"attendanceFlag":1,"createTime":"2018-11-24 12:57:58","hrAccount":"22049262","latitude":"43.847862","longitude":"125.240447","position":"长春华之城小车","positionFlag":0,"shopId":"201811221655000228059","timeFlag":2}
     * message : success
     * status : 200
     */

    private DataBean data;
    private String message;
    private int status;

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
        /**
         * attendanceFlag : 1
         * createTime : 2018-11-24 12:57:58
         * hrAccount : 22049262
         * latitude : 43.847862
         * longitude : 125.240447
         * position : 长春华之城小车
         * positionFlag : 0
         * shopId : 201811221655000228059
         * timeFlag : 2
         */

        private int attendanceFlag;
        private String createTime;
        private String hrAccount;
        private String latitude;
        private String longitude;
        private String position;
        private int positionFlag;
        private String shopId;
        private int timeFlag;

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

        public int getTimeFlag() {
            return timeFlag;
        }

        public void setTimeFlag(int timeFlag) {
            this.timeFlag = timeFlag;
        }
    }
}

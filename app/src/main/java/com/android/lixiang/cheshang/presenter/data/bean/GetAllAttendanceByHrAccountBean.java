package com.android.lixiang.cheshang.presenter.data.bean;

import java.util.List;

public class GetAllAttendanceByHrAccountBean {

    /**
     * data : [{"attendanceFlag":0,"createTime":"2018-11-08 09:10:09","hrAccount":"1","latitude":"43.977187","longitude":"125.389442","position":"中国吉林省长春市宽城区北远达大街辅路","positionFlag":1,"shopId":"2201005000012","timeFlag":0},{"attendanceFlag":0,"createTime":"2018-11-08 09:13:47","hrAccount":"1","latitude":"43.977187","longitude":"125.389442","position":"中国吉林省长春市宽城区北远达大街辅路","positionFlag":0,"shopId":"2201005000012"},{"attendanceFlag":0,"createTime":"2018-11-08 09:15:13","hrAccount":"1","latitude":"43.970317","longitude":"125.387817","position":"中国吉林省长春市二道区双龙桥","positionFlag":0,"shopId":"2201005000012"},{"attendanceFlag":0,"createTime":"2018-11-08 09:16:22","hrAccount":"1","latitude":"43.970317","longitude":"125.387817","position":"中国吉林省长春市二道区双龙桥","positionFlag":0,"shopId":"2201005000012","timeFlag":1},{"attendanceFlag":0,"createTime":"2018-11-08 09:18:18","hrAccount":"1","latitude":"43.987792","longitude":"125.409782","position":"中国吉林省长春市宽城区","positionFlag":0,"shopId":"2201005000012"},{"attendanceFlag":0,"createTime":"2018-11-08 09:24:14","hrAccount":"1","latitude":"43.986537","longitude":"125.408312","position":"吉星楼","positionFlag":0,"shopId":"2201005000011"}]
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

    public static class DataBean {
        /**
         * attendanceFlag : 0
         * createTime : 2018-11-08 09:10:09
         * hrAccount : 1
         * latitude : 43.977187
         * longitude : 125.389442
         * position : 中国吉林省长春市宽城区北远达大街辅路
         * positionFlag : 1
         * shopId : 2201005000012
         * timeFlag : 0
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
        private int reason;

        public int getReason() {
            return reason;
        }

        public void setReason(int reason) {
            this.reason = reason;
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

        public int getTimeFlag() {
            return timeFlag;
        }

        public void setTimeFlag(int timeFlag) {
            this.timeFlag = timeFlag;
        }
    }
}

package com.android.lixiang.cheshang.presenter.data.bean;

import java.util.List;

public class GetShopByHrAccountAndPositionBean {

    /**
     * status : 200
     * message : success
     * data : [{"address":"长沈路和谐大街661号","distance":4000,"latitude":"43.810434","shopName":"长春华之城大车","shopId":"长春华之城大车","longitude":"125.185386"},{"address":"长春市长沈路4077号","distance":5000,"latitude":"43.826205","shopName":"长春建达","shopId":"长春建达","longitude":"125.211025"},{"address":"长沈路488号","distance":7000,"latitude":"43.847862","shopName":"长春华之城小车","shopId":"长春华之城小车","longitude":"125.240447"},{"address":"长春市汽车经济技术开发区洛阳路2100号","distance":11000,"latitude":"43.883949","shopName":"长春仲昆","shopId":"长春仲昆","longitude":"125.254918"},{"address":"洛阳街2100号","distance":13000,"latitude":"43.899314","shopName":"省瑞成","shopId":"省瑞成","longitude":"125.260794"},{"address":"长春市宽城区青年路3875号","distance":18000,"latitude":"43.939967","shopName":"长春华之城世达","shopId":"长春华之城世达","longitude":"125.299339"}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address : 长沈路和谐大街661号
         * distance : 4000
         * latitude : 43.810434
         * shopName : 长春华之城大车
         * shopId : 长春华之城大车
         * longitude : 125.185386
         */

        private String address;
        private int distance;
        private String latitude;
        private String shopName;
        private String shopId;
        private String longitude;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}

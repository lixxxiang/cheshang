package com.android.lixiang.cheshang.presenter.data.bean;

import java.util.List;

public class RecordBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }



    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * address : hrtjtj
             * area : 124
             * createtime : 1539830064000
             * flag : 1
             * floor : 21
             * id : 12
             * iid : 2207847354
             * info : fewert
             * isdanger : 1
             * isliangwei : 1
             * line : 7636358
             * name : 325325
             * number : L051539830064430
             * perimeter : 32
             * phone : 1111111111
             * status : 6
             * time : 1252425600000
             * type : 1
             * url : grehre
             * work : fgrher
             */

            private String detail;
            private String time;
            private String remind;

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getRemind() {
                return remind;
            }

            public void setRemind(String remind) {
                this.remind = remind;
            }
        }
    }
}

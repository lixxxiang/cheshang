package com.android.lixiang.cheshang.presenter.data.bean;

import java.util.List;

public class PushDocumentBean {

    /**
     * data : [{"docId":"1811091557000529203","filePath":"http://59.110.161.48:8089/Shiro教程.pdf","name":"Shiro教程.pdf","uploadTime":"2018-11-09 15:57:53"},{"docId":"1811091604000183371","filePath":"http://59.110.161.48:8089/postgis-2.3.pdf","name":"postgis-2.3.pdf","uploadTime":"2018-11-09 16:04:19"},{"docId":"1811091604000497187","filePath":"http://59.110.161.48:8089/阿里巴巴Java开发手册（终极版）.pdf","name":"阿里巴巴Java开发手册（终极版）.pdf","uploadTime":"2018-11-09 16:04:49"}]
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
         * docId : 1811091557000529203
         * filePath : http://59.110.161.48:8089/Shiro教程.pdf
         * name : Shiro教程.pdf
         * uploadTime : 2018-11-09 15:57:53
         */

        private String docId;
        private String filePath;
        private String name;
        private String uploadTime;

        public String getDocId() {
            return docId;
        }

        public void setDocId(String docId) {
            this.docId = docId;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }
    }
}

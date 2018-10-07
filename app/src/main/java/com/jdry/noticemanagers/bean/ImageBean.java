package com.jdry.noticemanagers.bean;

/**
 * Created by JDRY-SJM on 2017/11/21.
 */

public class ImageBean {

    /**
     * data : {"path":"new/4BA0858606574886ABE7822B187ADAF3.JPEG"}
     * message : 上传成功
     * status : 1
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
         * path : new/4BA0858606574886ABE7822B187ADAF3.JPEG
         */

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}

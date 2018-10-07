package com.jdry.noticemanagers.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JDRY-SJM on 2017/10/10.
 */
@Entity
public class VersionBean {
    @Id(autoincrement = false)
    private Long id;
    @Unique
    private String versionCode;
    private String downUrl;
    private long publishTime;
    private String updateContent;
    private String appName;
    @Generated(hash = 1016581111)
    public VersionBean(Long id, String versionCode, String downUrl,
            long publishTime, String updateContent, String appName) {
        this.id = id;
        this.versionCode = versionCode;
        this.downUrl = downUrl;
        this.publishTime = publishTime;
        this.updateContent = updateContent;
        this.appName = appName;
    }
    @Generated(hash = 673304504)
    public VersionBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVersionCode() {
        return this.versionCode;
    }
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
    public String getDownUrl() {
        return this.downUrl;
    }
    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }
    public long getPublishTime() {
        return this.publishTime;
    }
    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
    public String getUpdateContent() {
        return this.updateContent;
    }
    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }
    public String getAppName() {
        return this.appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
}

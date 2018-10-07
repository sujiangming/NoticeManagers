package com.jdry.noticemanagers.bean;

import java.io.Serializable;

public class ReadMsgBean implements Serializable{

    /**
     * appUserId : 13985411016
     * content : <p>傻掉了卡机的拉开大来得及垃圾<span style="font-size: 12px;">?</span></p>
     * id : 40283381660e81e201660ecb990e0003
     * issueTime : 1537846518000
     * issueUserName : 徐磊
     * noticeId : 40283381660e81e201660ecb7f050000
     * readMark : 0
     * readTime :
     * status : 1
     * title : 测试一下
     */

    private String appUserId;
    private String content;
    private String id;
    private long issueTime;
    private String issueUserName;
    private String noticeId;
    private String readMark;
    private String readTime;
    private int status;
    private String title;

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(long issueTime) {
        this.issueTime = issueTime;
    }

    public String getIssueUserName() {
        return issueUserName;
    }

    public void setIssueUserName(String issueUserName) {
        this.issueUserName = issueUserName;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getReadMark() {
        return readMark;
    }

    public void setReadMark(String readMark) {
        this.readMark = readMark;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

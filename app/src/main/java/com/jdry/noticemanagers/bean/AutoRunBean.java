package com.jdry.noticemanagers.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AutoRunBean {
    @Id(autoincrement = false)
    private Long id;
    @Unique
    private boolean isAutoRun;
    @Generated(hash = 1753717481)
    public AutoRunBean(Long id, boolean isAutoRun) {
        this.id = id;
        this.isAutoRun = isAutoRun;
    }
    @Generated(hash = 913002184)
    public AutoRunBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getIsAutoRun() {
        return this.isAutoRun;
    }
    public void setIsAutoRun(boolean isAutoRun) {
        this.isAutoRun = isAutoRun;
    }
}

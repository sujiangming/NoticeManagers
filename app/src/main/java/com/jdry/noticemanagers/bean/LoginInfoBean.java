package com.jdry.noticemanagers.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LoginInfoBean {
    /**
     * approveTime : null
     * deptId : 111
     * deptName : 测试部门
     * enabled : 1
     * mobilePhone : 13985411016
     * name : 张三
     * registerTime : 1537804244000
     * remark : null
     * token : 75DC4312A9428880B982E44BE0323D64521EE70A86F90911F3614358
     */
    @Id(autoincrement = false)
    private Long id;

    private String approveTime;
    private String deptId;
    private String deptName;
    private String enabled;

    @Unique
    private String mobilePhone;

    private String password;

    private String name;
    private long registerTime;
    private String remark;
    private String token;
    @Generated(hash = 1715668572)
    public LoginInfoBean(Long id, String approveTime, String deptId,
            String deptName, String enabled, String mobilePhone, String password,
            String name, long registerTime, String remark, String token) {
        this.id = id;
        this.approveTime = approveTime;
        this.deptId = deptId;
        this.deptName = deptName;
        this.enabled = enabled;
        this.mobilePhone = mobilePhone;
        this.password = password;
        this.name = name;
        this.registerTime = registerTime;
        this.remark = remark;
        this.token = token;
    }
    @Generated(hash = 410655696)
    public LoginInfoBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getApproveTime() {
        return this.approveTime;
    }
    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }
    public String getDeptId() {
        return this.deptId;
    }
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    public String getDeptName() {
        return this.deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getEnabled() {
        return this.enabled;
    }
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
    public String getMobilePhone() {
        return this.mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getRegisterTime() {
        return this.registerTime;
    }
    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}

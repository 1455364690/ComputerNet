package com.sunjiahui.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by 14553 on 2018/5/13.
 */
@Entity
@Table(name = "weixin_user_info")
public class WeixinUserInfo {
    @Id
    @Column(name = "openid")
    private String openId;

    @Column(name = "nickname")
    private String nickName;


    @Column(name = "userinfo")
    private String userInfo;


    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_at")
    private Timestamp updateAt;

    public WeixinUserInfo() {
    }

    public WeixinUserInfo(String openId, String nickName, String userInfo, Timestamp createAt, Timestamp updateAt) {
        this.openId = openId;
        this.nickName = nickName;
        this.userInfo = userInfo;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }
}

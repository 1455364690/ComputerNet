package com.sunjiahui.service;


import com.sunjiahui.model.WeixinUserInfo;

import java.sql.Timestamp;
import java.util.List;

public interface WeixinUserInfoService {
    WeixinUserInfo getWeixinUserInfoByOpenId(String openId);

    void deleteByOpenId(String openId);

    WeixinUserInfo addWeixinUserInfo(String openId, String nickName, String userInfo, Timestamp createAt, Timestamp updateAt);
    //WeixinUserInfo update

    List<WeixinUserInfo> findWeixinUserInfosByOpenIdIn(List<String> openIdList);

    /**
     * 如果存在，更新；不存在，插入
     * @param openId
     * @param nickName
     * @param userInfo
     * @param createAt
     * @param updateAt
     */
    void saveWeixinUserInfo(String openId, String nickName, String userInfo, Timestamp createAt, Timestamp updateAt);
}

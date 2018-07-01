package com.sunjiahui.service.impl;

import com.sunjiahui.dao.WeixinUserInfoRepository;
import com.sunjiahui.model.WeixinUserInfo;
import com.sunjiahui.service.WeixinUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.List;

@Service("weixinUserInfoService")
@Transactional
public class WeixinUserInfoServiceImpl implements WeixinUserInfoService {
    @Autowired
    private WeixinUserInfoRepository weixinUserInfoDao;

    public WeixinUserInfo getWeixinUserInfoByOpenId(String openId) {
        return weixinUserInfoDao.findWeixinUserInfoByOpenId(openId);
    }

    public void deleteByOpenId(String openId) {
        weixinUserInfoDao.deleteByOpenId(openId);
    }

    public WeixinUserInfo addWeixinUserInfo(String openId, String nickName, String userInfo, Timestamp createAt, Timestamp updateAt) {
        WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
        weixinUserInfo.setOpenId(openId);
        weixinUserInfo.setNickName(nickName);
        weixinUserInfo.setUserInfo(userInfo);
        weixinUserInfo.setCreateAt(createAt);
        weixinUserInfo.setUpdateAt(updateAt);
        return weixinUserInfoDao.save(weixinUserInfo);
    }

    public List<WeixinUserInfo> findWeixinUserInfosByOpenIdIn(List<String> openIdList) {
        return weixinUserInfoDao.findWeixinUserInfosByOpenIdIn(openIdList);
    }

    @Override
    public void saveWeixinUserInfo(String openId, String nickName, String userInfo, Timestamp createAt, Timestamp updateAt) {
        WeixinUserInfo weixinUserInfo = weixinUserInfoDao.findWeixinUserInfoByOpenId(openId);
        if (ObjectUtils.isEmpty(weixinUserInfo)) {
            weixinUserInfo = new WeixinUserInfo();
            weixinUserInfo.setOpenId(openId);
            weixinUserInfo.setNickName(nickName);
            weixinUserInfo.setUserInfo(userInfo);
            weixinUserInfo.setCreateAt(createAt);
            weixinUserInfo.setUpdateAt(updateAt);
        } else {
            weixinUserInfo.setNickName(nickName);
            weixinUserInfo.setUserInfo(userInfo);
            weixinUserInfo.setUpdateAt(updateAt);
        }
        weixinUserInfoDao.save(weixinUserInfo);
    }
}

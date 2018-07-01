package com.sunjiahui.dao;

import com.sunjiahui.model.WeixinUserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("weixinUserInfoDao")
public interface WeixinUserInfoRepository extends CrudRepository<WeixinUserInfo, Integer> {
    WeixinUserInfo findWeixinUserInfoByOpenId(String openId);

    void deleteByOpenId(String openId);

    List<WeixinUserInfo> findWeixinUserInfosByOpenIdIn(List<String> openIdList);
}

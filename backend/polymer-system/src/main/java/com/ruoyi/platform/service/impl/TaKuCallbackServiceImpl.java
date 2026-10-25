package com.ruoyi.platform.service.impl;

import java.util.Date;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TaKuCallbackMapper;
import com.ruoyi.platform.domain.TaKuCallback;
import com.ruoyi.platform.service.ITaKuCallbackService;

/**
 * taKu广告回调Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-27
 */
@Service
public class TaKuCallbackServiceImpl implements ITaKuCallbackService {
    @Autowired
    private TaKuCallbackMapper taKuCallbackMapper;

    /**
     * 查询taKu广告回调
     *
     * @param id taKu广告回调主键
     * @return taKu广告回调
     */
    @Override
    public TaKuCallback selectTaKuCallbackById(Long id) {
        return taKuCallbackMapper.selectTaKuCallbackById(id);
    }

    /**
     * 查询taKu广告回调列表
     *
     * @param taKuCallback taKu广告回调
     * @return taKu广告回调
     */
    @Override
    public List<TaKuCallback> selectTaKuCallbackList(TaKuCallback taKuCallback) {
        Date[] times = taKuCallback.getTime();
        if (times != null) {
            if (times.length == 1) {
                taKuCallback.setStartTime(times[0]);
            } else if (times.length == 2) {
                taKuCallback.setStartTime(times[0]);
                taKuCallback.setEndTime(times[1]);
            }
        }
        List<TaKuCallback> taKuCallbacks = taKuCallbackMapper.selectTaKuCallbackList(taKuCallback);
        //处理JSON数据
        if (!taKuCallbacks.isEmpty()) {
            for (TaKuCallback kuCallback : taKuCallbacks) {
//                if (StrUtil.isNotEmpty(kuCallback.getIlrd())) {
//                    JSONObject jsonObject = JSONObject.parseObject(kuCallback.getIlrd());
//                    kuCallback.setAdsourcePrice(jsonObject.get("adsource_price").toString());
//                    kuCallback.setPublisherRevenue(jsonObject.get("publisher_revenue").toString());
//                }
                if (StrUtil.isNotEmpty(kuCallback.getNetworkFirmId())) {
                    if (kuCallback.getNetworkFirmId().equals("1")) {
                        kuCallback.setNetworkFirmIdStr("Meta(Facebook)");
                    } else if (kuCallback.getNetworkFirmId().equals("2")) {
                        kuCallback.setNetworkFirmIdStr("Admob");
                    } else if (kuCallback.getNetworkFirmId().equals("3")) {
                        kuCallback.setNetworkFirmIdStr("Inmobi");
                    } else if (kuCallback.getNetworkFirmId().equals("5")) {
                        kuCallback.setNetworkFirmIdStr("Applovin");
                    } else if (kuCallback.getNetworkFirmId().equals("6")) {
                        kuCallback.setNetworkFirmIdStr("Mintegral");
                    } else if (kuCallback.getNetworkFirmId().equals("8")) {
                        kuCallback.setNetworkFirmIdStr("腾讯广告");
                    } else if (kuCallback.getNetworkFirmId().equals("9")) {
                        kuCallback.setNetworkFirmIdStr("Chartboost");
                    } else if (kuCallback.getNetworkFirmId().equals("11")) {
                        kuCallback.setNetworkFirmIdStr("ironSource");
                    } else if (kuCallback.getNetworkFirmId().equals("12")) {
                        kuCallback.setNetworkFirmIdStr("UnityAds");
                    } else if (kuCallback.getNetworkFirmId().equals("13")) {
                        kuCallback.setNetworkFirmIdStr("Liftoff(Vungle)");
                    } else if (kuCallback.getNetworkFirmId().equals("15")) {
                        kuCallback.setNetworkFirmIdStr("穿山甲（CSJ）");
                    } else if (kuCallback.getNetworkFirmId().equals("22")) {
                        kuCallback.setNetworkFirmIdStr("百度联盟");
                    } else if (kuCallback.getNetworkFirmId().equals("23")) {
                        kuCallback.setNetworkFirmIdStr("Nend");
                    } else if (kuCallback.getNetworkFirmId().equals("24")) {
                        kuCallback.setNetworkFirmIdStr("Maio");
                    } else if (kuCallback.getNetworkFirmId().equals("25")) {
                        kuCallback.setNetworkFirmIdStr("Start.io");
                    } else if (kuCallback.getNetworkFirmId().equals("28")) {
                        kuCallback.setNetworkFirmIdStr("快手");
                    } else if (kuCallback.getNetworkFirmId().equals("29")) {
                        kuCallback.setNetworkFirmIdStr("Sigmob");
                    } else if (kuCallback.getNetworkFirmId().equals("32")) {
                        kuCallback.setNetworkFirmIdStr("VK（myTarget）");
                    } else if (kuCallback.getNetworkFirmId().equals("33")) {
                        kuCallback.setNetworkFirmIdStr("Google Ad Manager");
                    } else if (kuCallback.getNetworkFirmId().equals("34")) {
                        kuCallback.setNetworkFirmIdStr("Yandex");
                    } else if (kuCallback.getNetworkFirmId().equals("36")) {
                        kuCallback.setNetworkFirmIdStr("Ogury");
                    } else if (kuCallback.getNetworkFirmId().equals("37")) {
                        kuCallback.setNetworkFirmIdStr("Digital Turbine(Fyber)");
                    } else if (kuCallback.getNetworkFirmId().equals("39")) {
                        kuCallback.setNetworkFirmIdStr("华为广告");
                    } else if (kuCallback.getNetworkFirmId().equals("45")) {
                        kuCallback.setNetworkFirmIdStr("Kidoz");
                    } else if (kuCallback.getNetworkFirmId().equals("48")) {
                        kuCallback.setNetworkFirmIdStr("A4G(Admob)");
                    } else if (kuCallback.getNetworkFirmId().equals("49")) {
                        kuCallback.setNetworkFirmIdStr("米盟（Mi Uion）");
                    } else if (kuCallback.getNetworkFirmId().equals("50")) {
                        kuCallback.setNetworkFirmIdStr("Pangle");
                    } else if (kuCallback.getNetworkFirmId().equals("57")) {
                        kuCallback.setNetworkFirmIdStr("ReklamUp");
                    } else if (kuCallback.getNetworkFirmId().equals("58")) {
                        kuCallback.setNetworkFirmIdStr("Verve Group");
                    } else if (kuCallback.getNetworkFirmId().equals("59")) {
                        kuCallback.setNetworkFirmIdStr("Bigo");
                    } else if (kuCallback.getNetworkFirmId().equals("65")) {
                        kuCallback.setNetworkFirmIdStr("Bidmachine");
                    } else if (kuCallback.getNetworkFirmId().equals("66")) {
                        kuCallback.setNetworkFirmIdStr("TaKu");
                    } else if (kuCallback.getNetworkFirmId().equals("69")) {
                        kuCallback.setNetworkFirmIdStr("TapTap");
                    } else if (kuCallback.getNetworkFirmId().equals("72")) {
                        kuCallback.setNetworkFirmIdStr("京媒平台");
                    } else if (kuCallback.getNetworkFirmId().equals("74")) {
                        kuCallback.setNetworkFirmIdStr("趣盟");
                    } else if (kuCallback.getNetworkFirmId().equals("75")) {
                        kuCallback.setNetworkFirmIdStr("Amazon Publisher");
                    } else if (kuCallback.getNetworkFirmId().equals("79")) {
                        kuCallback.setNetworkFirmIdStr("VIVO 广告联盟");
                    } else if (kuCallback.getNetworkFirmId().equals("80")) {
                        kuCallback.setNetworkFirmIdStr("OPPO 广告联盟");
                    } else if (kuCallback.getNetworkFirmId().equals("81")) {
                        kuCallback.setNetworkFirmIdStr("小米 Columbus");
                    } else if (kuCallback.getNetworkFirmId().equals("82")) {
                        kuCallback.setNetworkFirmIdStr("阿里妈妈 Tanx");
                    } else if (kuCallback.getNetworkFirmId().equals("83")) {
                        kuCallback.setNetworkFirmIdStr("TaurusX(Webeye)");
                    } else if (kuCallback.getNetworkFirmId().equals("84")) {
                        kuCallback.setNetworkFirmIdStr("Smaato");
                    } else if (kuCallback.getNetworkFirmId().equals("93")) {
                        kuCallback.setNetworkFirmIdStr("美数AdMateX");
                    } else if (kuCallback.getNetworkFirmId().equals("94")) {
                        kuCallback.setNetworkFirmIdStr("爱奇艺联盟");
                    }
                }
            }
        }
        return taKuCallbacks;
    }

    /**
     * 新增taKu广告回调
     *
     * @param taKuCallback taKu广告回调
     * @return 结果
     */
    @Override
    public int insertTaKuCallback(TaKuCallback taKuCallback) {
        taKuCallback.setCreateTime(DateUtils.getNowDate());
        return taKuCallbackMapper.insertTaKuCallback(taKuCallback);
    }

    /**
     * 修改taKu广告回调
     *
     * @param taKuCallback taKu广告回调
     * @return 结果
     */
    @Override
    public int updateTaKuCallback(TaKuCallback taKuCallback) {
        taKuCallback.setUpdateTime(DateUtils.getNowDate());
        return taKuCallbackMapper.updateTaKuCallback(taKuCallback);
    }

    /**
     * 批量删除taKu广告回调
     *
     * @param ids 需要删除的taKu广告回调主键
     * @return 结果
     */
    @Override
    public int deleteTaKuCallbackByIds(Long[] ids) {
        return taKuCallbackMapper.deleteTaKuCallbackByIds(ids);
    }

    /**
     * 删除taKu广告回调信息
     *
     * @param id taKu广告回调主键
     * @return 结果
     */
    @Override
    public int deleteTaKuCallbackById(Long id) {
        return taKuCallbackMapper.deleteTaKuCallbackById(id);
    }
}

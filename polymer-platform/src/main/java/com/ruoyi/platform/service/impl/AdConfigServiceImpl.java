package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.domain.Detection;
import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.domain.TaKuConfig;
import com.ruoyi.platform.enums.DetectionTypeEnum;
import com.ruoyi.platform.enums.ParamsConfigFollowTypeEnum;
import com.ruoyi.platform.enums.ParamsConfigTypeEnum;
import com.ruoyi.platform.enums.TaKuAdConfigTypeEnum;
import com.ruoyi.platform.mapper.DetectionDao;
import com.ruoyi.platform.mapper.TaKuConfigDao;
import com.ruoyi.platform.service.AdConfigService;
import com.ruoyi.platform.service.ParamsConfigService;
import com.ruoyi.platform.vo.TaKuAdConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdConfigServiceImpl implements AdConfigService {

    @Autowired
    private ParamsConfigService paramsConfigService;
    @Autowired
    private TaKuConfigDao taKuConfigDao;
    @Autowired
    private DetectionDao detectionDao;



    @Override
    public TaKuAdConfigVO getTaKuAdConfigVO() {
        TaKuAdConfigVO vo=new TaKuAdConfigVO();
        //广告配置
        List<TaKuConfig> taKuConfigs = taKuConfigDao.selectTaKuAdConfigListAll();
        vo.setOpenAdvId(taKuConfigs.stream()
                .filter(item -> TaKuAdConfigTypeEnum.TYPE_1.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0).getValue());

        vo.setMotivateId(taKuConfigs.stream()
                .filter(item -> TaKuAdConfigTypeEnum.TYPE_2.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0).getValue());

        vo.setTaKuAppId(taKuConfigs.stream()
                .filter(item -> TaKuAdConfigTypeEnum.TYPE_3.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0).getValue());

        vo.setTaKuKey(taKuConfigs.stream()
                .filter(item -> TaKuAdConfigTypeEnum.TYPE_4.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0).getValue());

        vo.setTopBannerId(taKuConfigs.stream()
                .filter(item -> TaKuAdConfigTypeEnum.TYPE_5.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0).getValue());

        vo.setBottomBannerId(taKuConfigs.stream()
                .filter(item -> TaKuAdConfigTypeEnum.TYPE_6.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0).getValue());

        vo.setInterstitialId(taKuConfigs.stream()
                .filter(item -> TaKuAdConfigTypeEnum.TYPE_7.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0).getValue());

        vo.setNativeId(taKuConfigs.stream()
                .filter(item -> TaKuAdConfigTypeEnum.TYPE_8.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0).getValue());

        //微信配置
        List<ParamsConfig> paramsList1 = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_3.getCode());
        vo.setWxAppId(paramsConfigService.getFollowParams(paramsList1, ParamsConfigFollowTypeEnum.TYPE_10.getCode()).getValue());
        //风控配置
        List<Detection> detections = detectionDao.selectDetectionAll();
        vo.setIsRoot(detections.stream()
                .filter(item -> DetectionTypeEnum.TYPE_1.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0).getStatus());

        vo.setIsSimulator(detections.stream()
                .filter(item -> DetectionTypeEnum.TYPE_2.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0).getStatus());
        return vo;
    }
}

package com.ruoyi.platform.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.Hashing;
import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.constant.Constant;
import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.dto.TaKuAdCallbackDTO;
import com.ruoyi.platform.enums.*;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.*;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.TaKuAdCallbackVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class TaKuCallbackServiceImpl implements TaKuCallbackService {


    @Autowired
    private TaKuConfigDao taKuConfigDao;
    @Autowired
    private TaKuCallbackDao taKuCallbackDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ConvertRatioService convertRatioService;
    @Autowired
    private BillGoldService billGoldService;
    @Autowired
    private DramaDetailsDao dramaDetailsDao;
    @Autowired
    private DramaDao dramaDao;
    @Autowired
    private DramaUnlockDao dramaUnlockDao;
    @Autowired
    private DramaPublicizeDao dramaPublicizeDao;
    @Autowired
    private VideoDao videoDao;
    @Autowired
    private VideoPublicizeDao videoPublicizeDao;
    @Autowired
    private VideoSeriesUnlockDao videoSeriesUnlockDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ParamsConfigService paramsConfigService;
    @Autowired
    private DetectionService detectionService;
    @Autowired
    private DetectionDao detectionDao;
    @Autowired
    private DailyService dailyService;
    @Autowired
    private TaskDailyLookVideoToCoinDao taskDailyLookVideoToCoinDao;
    @Autowired
    private TaskDailyLookVideoToCoinRecordDao taskDailyLookVideoToCoinRecordDao;


    @Override
    @Transactional
    public Integer taKuCallback(HttpServletRequest request, TaKuAdCallbackDTO dto) {
        if (StrUtil.isNotEmpty(dto.getIs_test()) && "1".equals(dto.getIs_test())) {
            log.info("TaKu回调测试");
            return 200;
        }
        log.info("准备执行业务功能");
        //进行验签
        if (!check(dto)) {
            return 601;
        }
        List<Detection> detections = detectionDao.selectDetectionAll();

        //准备执行业务逻辑
        Date date = new Date();
        TaKuCallback adCallback = new TaKuCallback();
        adCallback.setCreateTime(date);
        adCallback.setUserId(StrUtil.isNotEmpty(dto.getScenario_id()) ? Integer.parseInt(dto.getScenario_id()) : null);
        adCallback.setExtraData(StrUtil.isNotEmpty(dto.getExtra_data()) ? dto.getExtra_data() : null);
        adCallback.setTransId(StrUtil.isNotEmpty(dto.getTrans_id()) ? dto.getTrans_id() : null);
        adCallback.setRewardAmount(StrUtil.isNotEmpty(dto.getReward_amount()) ? dto.getReward_amount() : null);
        adCallback.setRewardName(StrUtil.isNotEmpty(dto.getReward_name()) ? dto.getReward_name() : null);
        adCallback.setPlacementId(StrUtil.isNotEmpty(dto.getPlacement_id()) ? dto.getPlacement_id() : null);
        adCallback.setNetworkFirmId(StrUtil.isNotEmpty(dto.getNetwork_firm_id()) ? dto.getNetwork_firm_id() : null);
        adCallback.setScenarioId(StrUtil.isNotEmpty(dto.getScenario_id()) ? dto.getScenario_id() : null);
        adCallback.setPackageName(StrUtil.isNotEmpty(dto.getPackage_name()) ? dto.getPackage_name() : null);
        adCallback.setPlatform(StrUtil.isNotEmpty(dto.getPlatform()) ? dto.getPlatform() : null);
        adCallback.setIlrd(StrUtil.isNotEmpty(dto.getIlrd()) ? dto.getIlrd() : null);
        adCallback.setSign(StrUtil.isNotEmpty(dto.getSign()) ? dto.getSign() : null);

        JSONObject jsonObject1 = JSONObject.parseObject(adCallback.getIlrd());
        Object object = jsonObject1.get("req_id");
        if (Objects.isNull(object)) {
            //此值不能为空
            return 602;
        }
        String reqId = object.toString();
        adCallback.setReqId(reqId);

        String publisherRevenue = jsonObject1.get("publisher_revenue").toString();
        log.info("publisherRevenue:{}", publisherRevenue);

        String adsourcePrice = jsonObject1.get("adsource_price").toString();
        log.info("adsourcePrice:{}", adsourcePrice);

        adCallback.setAdsourcePrice(adsourcePrice);
        adCallback.setPublisherRevenue(publisherRevenue);

        // 优先从 extra_data 取业务参数，兼容 ilrd.show_custom_ext
        String extraDataForBiz = StrUtil.isNotEmpty(dto.getExtra_data()) ? dto.getExtra_data() : null;
        if (extraDataForBiz == null && jsonObject1.containsKey("show_custom_ext") && Objects.nonNull(jsonObject1.get("show_custom_ext"))) {
            extraDataForBiz = jsonObject1.get("show_custom_ext").toString();
        }
        log.info("extraDataForBiz:{}", extraDataForBiz);
        if (StrUtil.isEmpty(extraDataForBiz)) {
            log.error("Taku回调缺少业务参数 extra_data/show_custom_ext");
            return 603;
        }
        JSONObject jsonObject2 = JSONObject.parseObject(extraDataForBiz);
        String value = jsonObject2.get("value").toString();
        String type = jsonObject2.get("type").toString();

        //处理风控两次广告间隔时间
        Boolean b3 = detectionService.handleDetection3(detections.stream()
                .filter(item -> DetectionTypeEnum.TYPE_3.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0), adCallback.getUserId(), date);
        if (!b3) {
            return 200;
        }
        //处理风控单次收益大于某个值
        Boolean b4 = detectionService.handleDetection4(detections.stream()
                .filter(item -> DetectionTypeEnum.TYPE_4.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0), adCallback.getUserId(), date, publisherRevenue);
        if (!b4) {
            return 200;
        }
        //处理风控同一个IP使用N个账号登录
        Boolean b6 = detectionService.handleDetection6(detections.stream()
                .filter(item -> DetectionTypeEnum.TYPE_6.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0), adCallback.getUserId(), date, publisherRevenue);
        if (!b6) {
            return 200;
        }
        //新增
        if ("1".equals(type)) {
            adCallback.setScene(PublicizeSceneEnum.TYPE_1.getCode());
        } else if ("2".equals(type)) {
            if ("1".equals(value)) {
                adCallback.setScene(PublicizeSceneEnum.TYPE_2.getCode());
            } else if ("2".equals(value)) {
                adCallback.setScene(PublicizeSceneEnum.TYPE_3.getCode());
            } else if ("3".equals(value)) {
                adCallback.setScene(PublicizeSceneEnum.TYPE_4.getCode());
            }
        } else if ("3".equals(type)) {
            adCallback.setScene(PublicizeSceneEnum.TYPE_5.getCode());
        } else if ("4".equals(type)) {
            adCallback.setScene(PublicizeSceneEnum.TYPE_6.getCode());
        } else if ("5".equals(type)) {
            adCallback.setScene(PublicizeSceneEnum.TYPE_7.getCode());
        }
        taKuCallbackDao.insertSelective(adCallback);
        //处理风控单连续N条低于某一个值
        Boolean b5 = detectionService.handleDetection5(detections.stream()
                .filter(item -> DetectionTypeEnum.TYPE_5.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0), adCallback.getUserId(), date, publisherRevenue);
        if (!b5) {
            return 200;
        }
        //处理风控单每个用户每天激励广告限制数量
        Boolean b7 = detectionService.handleDetection7(detections.stream()
                .filter(item -> DetectionTypeEnum.TYPE_7.getCode().equals(item.getType()))
                .collect(Collectors.toList()).get(0), adCallback.getUserId(), date, publisherRevenue);
        if (!b7) {
            taKuCallbackDao.updateByPrimaryKeySelective(adCallback);
            return 200;
        }

        //处理业务
        if (Objects.nonNull(adCallback.getUserId())) {
            //处理广告结果
            if ("1".equals(type)) {
                //处理看广告解锁短剧
                taKuHandleAdUnlockShortDrama(adCallback.getUserId(), Integer.parseInt(value), date, adCallback);
                //查看是不是设置了解锁短剧也可奖励
                int result = Integer.parseInt(paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_11.getCode()).get(0).getValue());
                if (result == 1) {
                    //解锁短剧同样给金币奖励
                    taKuAdvertisingRewards(adCallback.getUserId(), date, adCallback, adsourcePrice);
                }
            } else if ("2".equals(type)) {
                //小曲库相关广告
                if ("1".equals(value)) {
                    //通关奖励
                    log.info("通关奖励");
                    taKuAdvertisingRewards(adCallback.getUserId(), date, adCallback, adsourcePrice);
                } else if ("2".equals(value)) {
                    //幸运奖励
                    log.info("幸运奖励");
                    taKuAdvertisingRewards(adCallback.getUserId(), date, adCallback, adsourcePrice);
                } else if ("3".equals(value)) {
                    //红包奖励
                    log.info("红包奖励");
                    taKuAdvertisingRewards(adCallback.getUserId(), date, adCallback, adsourcePrice);
                }
            } else if ("3".equals(type)) {
                //小说相关广告
                log.info("小说奖励");
                taKuAdvertisingRewards(adCallback.getUserId(), date, adCallback, adsourcePrice);
            } else if ("4".equals(type)) {
                //任务广告奖励
                taKuAdvertisingRewards(adCallback.getUserId(), date, adCallback, adsourcePrice);
                dailyService.lookVideoToCoin(adCallback.getUserId(), new Date());
            } else if ("5".equals(type)) {
                //看广告解锁视频单集
                taKuHandleAdUnlockVideo(adCallback.getUserId(), Integer.parseInt(value), date, adCallback);
            }
        }
        return 200;
    }


    /**
     * taKu处理看广告解锁短剧
     */
    private void taKuHandleAdUnlockShortDrama(Integer userId, Integer value, Date date, TaKuCallback adCallback) {
        //看广告解锁短剧；value表示需要观看的单集ID
        //通过单集查询整壳子信息
        DramaDetails dramaDetails1 = dramaDetailsDao.selectByPrimaryKey(value);
        if (Objects.nonNull(dramaDetails1)) {
            Drama drama = dramaDao.selectByPrimaryKey(dramaDetails1.getDramaId());
            //查看配置的解锁集数
            DramaUnlock dramaUnlock = dramaUnlockDao.selectDramaUnlock();
            List<DramaDetails> list = dramaDetailsDao.
                    selectNeedUnlockDramaDetailsId(userId, dramaDetails1.getDramaId(), dramaUnlock.getUnlockNum());
            if (!list.isEmpty()) {
                //进行解锁，添加数据
                //先查看有没有数据，如果没有进行新增
                //处理回调信息
                for (DramaDetails dramaDetails : list) {
                    DramaPublicize dramaPublicize1 = dramaPublicizeDao.
                            selectDramaPublicizeByUserIdAndDramaDetailsId(userId, dramaDetails.getId());
                    if (Objects.isNull(dramaPublicize1)) {
                        DramaPublicize dramaPublicize2 = new DramaPublicize();
                        dramaPublicize2.setCreateTime(date);
                        dramaPublicize2.setUserId(userId);
                        dramaPublicize2.setDramaDetailsId(dramaDetails.getId());
                        dramaPublicize2.setUnlock(1);
                        dramaPublicizeDao.insertSelective(dramaPublicize2);
                    }
                }
                List<Integer> gathers = list.stream().map(DramaDetails::getGather).collect(Collectors.toList());
                adCallback.setRemarks("解锁短剧：" + drama.getTitle() + ";解锁集数：" +
                        StringUtils.join(",", gathers));
                adCallback.setUnlock(1);
                taKuCallbackDao.updateByPrimaryKeySelective(adCallback);
            }
        }
    }

    /**
     * taKu处理看广告解锁视频系列（从点击集开始向后批量解锁N集）
     */
    private void taKuHandleAdUnlockVideo(Integer userId, Integer value, Date date, TaKuCallback adCallback) {
        //看广告解锁视频；value表示用户点击的视频ID
        Video video = videoDao.selectByPrimaryKey(value);
        if (Objects.nonNull(video)) {
            VideoSeriesUnlock unlockConfig = videoSeriesUnlockDao.selectVideoSeriesUnlock();
            int unlockNum = unlockConfig != null && unlockConfig.getUnlockNum() != null ? unlockConfig.getUnlockNum() : 5;
            List<Integer> videoIds = videoDao.selectNeedUnlockVideoIds(userId, value, unlockNum);
            if (!videoIds.isEmpty()) {
                for (Integer vid : videoIds) {
                    VideoPublicize publicize = videoPublicizeDao.selectVideoPublicizeByUserIdAndVideoId(userId, vid);
                    if (Objects.isNull(publicize)) {
                        VideoPublicize newPublicize = new VideoPublicize();
                        newPublicize.setUserId(userId);
                        newPublicize.setVideoId(vid);
                        newPublicize.setUnlock(1);
                        newPublicize.setCreateTime(date);
                        videoPublicizeDao.insertSelective(newPublicize);
                    }
                }
                adCallback.setRemarks("解锁系列：" + video.getName() + ";共解锁：" + videoIds.size() + "集");
            } else {
                adCallback.setRemarks("解锁视频：" + video.getName());
            }
            adCallback.setUnlock(1);
            taKuCallbackDao.updateByPrimaryKeySelective(adCallback);
        }
    }

    @Autowired
    private ITUserRoleService userRoleService;

    /**
     * 处理看广告获取奖励
     */
    private void taKuAdvertisingRewards(Integer userId, Date date, TaKuCallback adCallback, String adsourcePrice) {
        BigDecimal initRatio;
        //查看用户信息，处理分销功能
        User user = userDao.selectByPrimaryKey(userId);
        if (Objects.nonNull(user)) {
            TUserRole tUserRole = userRoleService.selectTUserRoleById(user.getRoleId().longValue());
            initRatio = tUserRole.getMyRatio();
            //处理结果
            double dEcpm = Double.parseDouble(adsourcePrice);
            log.info("initRatio.doubleValue()" + initRatio.doubleValue());
            //查看是不是代理
            if (tUserRole.getRoleType() == 1) {
                log.info(user.getId() + "是代理身份");
                taKuViewAdsWithAmountInformation(adCallback, initRatio, userId, date, dEcpm);
            } else {
                log.info(user.getId() + "不是代理身份");
                if (Objects.isNull(user.getParentId()) || user.getParentId() == 0) {
                    log.info(user.getId() + "没有上级");
                    taKuViewAdsWithAmountInformation(adCallback, initRatio, userId, date, dEcpm);
                } else {
                    log.info(user.getId() + "有上级");
                    User user1 = userDao.selectByPrimaryKey(user.getParentId());
                    User user2 = null;
                    if (!Objects.isNull(user1.getParentId()) && user1.getParentId() != 0) {
                        user2 = userDao.selectByPrimaryKey(user1.getParentId());
                    }
                    taKuViewAdsWithAmountInformation(adCallback, initRatio, userId, date, dEcpm);
                    log.info("准备处理上级问题");
                    taKuHandleSellingFunction(user1, date, dEcpm);
                    if (Objects.nonNull(user2)) taKuHandleSellingFunctionTwo(user2, date, dEcpm);
                }
            }
        }
    }


    /**
     * 处理看广告加金额具体信息（ecpm）
     */
    private void taKuViewAdsWithAmountInformation(TaKuCallback adCallback, BigDecimal initRatio,
                                                  Integer userId, Date date, Double eCpm) {
        //查询收益方式
        BigDecimal money;
        //ecpm
        double n = eCpm / 1000;
        BigDecimal bigDecimal = BigDecimal.valueOf(n);
        //再乘兑换比例
        Integer convertRatio = convertRatioService.getConvertRatio();
        bigDecimal = bigDecimal.multiply(new BigDecimal(convertRatio));
        //查看扩展字段下的类型
        //设置的金额*比例值（有上级或者无上级）*0.01；不保留小数
        money = bigDecimal
                .multiply(initRatio)
                .multiply(Constant.PROPORTIONAL_CALCULATION_METHOD)
                .setScale(0, RoundingMode.DOWN);
        // 最低奖励 100 金币
        if (money.compareTo(new BigDecimal("100")) < 0) {
            money = new BigDecimal("100");
        }
        adCallback.setMoney(money);
        //修改记录值
        if (StrUtil.isNotEmpty(adCallback.getRemarks())) {
            adCallback.setRemarks(adCallback.getRemarks() + "===看广告获取奖励：" + adCallback.getMoney());
        } else {
            adCallback.setRemarks("看广告获取奖励：" + adCallback.getMoney());
        }
        taKuCallbackDao.updateByPrimaryKeySelective(adCallback);
        //加金币
        log.info("加金币:" + adCallback.getMoney());
        userDao.addGoldCoin(userId, adCallback.getMoney());
        //加账单
        //添加具体描述
        billGoldService.addBillGold(userId,
                BillSceneTypeEnum.SCENE_1.getCode(), PublicizeSceneEnum.codeToMessage(adCallback.getScene()), adCallback.getMoney(), date);
    }


    /**
     * 处理看广告加金额具体信息（区间收益）
     */
    private void taKuViewAdsWithAmountInformation2(TaKuCallback adCallback, BigDecimal initRatio,
                                                   Integer userId, Date date, BigDecimal moneyInfo) {
        //查询收益方式
        BigDecimal money;
        //设置的金额*比例值（有上级或者无上级）*0.01；不保留小数
        money = moneyInfo
                .multiply(initRatio)
                .multiply(Constant.PROPORTIONAL_CALCULATION_METHOD)
                .setScale(0, RoundingMode.DOWN);
        adCallback.setMoney(money);
        //修改记录值
        if (StrUtil.isNotEmpty(adCallback.getRemarks())) {
            adCallback.setRemarks(adCallback.getRemarks() + "===看广告获取奖励：" + adCallback.getMoney());
        } else {
            adCallback.setRemarks("看广告获取奖励：" + adCallback.getMoney());
        }
        taKuCallbackDao.updateByPrimaryKeySelective(adCallback);
        //加金币
        log.info("加金币:" + adCallback.getMoney());
        userDao.addGoldCoin(userId, adCallback.getMoney());
        //加账单
        billGoldService.addBillGold(userId,
                BillSceneTypeEnum.SCENE_1.getCode(), PublicizeSceneEnum.codeToMessage(adCallback.getScene()), adCallback.getMoney(), date);
    }

    /**
     * 处理分销(ecpm)
     */
    private void taKuHandleSellingFunction(User user, Date date,
                                           Double eCpm) {
        double n = eCpm / 1000;
        BigDecimal bigDecimal = BigDecimal.valueOf(n);
        //再乘兑换比例
        Integer convertRatio = convertRatioService.getConvertRatio();
        bigDecimal = bigDecimal.multiply(new BigDecimal(convertRatio));
        //查看上级是不是代理并设置了分销比例
        TUserRole tUserRole = userRoleService.selectTUserRoleById(user.getRoleId().longValue());
        if (tUserRole.getRatio().compareTo(BigDecimal.ZERO) > 0) {
            //进行分销处理
            BigDecimal money = bigDecimal;
            //设置的金额*比例值（代理比例）*0.01；保留两位小数
            money = money
                    .multiply(tUserRole.getRatio())
                    .multiply(Constant.PROPORTIONAL_CALCULATION_METHOD)
                    .setScale(0, RoundingMode.DOWN);
            //加金币
            userDao.addGoldCoin(user.getId(), money);
            //加账单
            billGoldService.addBillGold(user.getId(),
                    BillSceneTypeEnum.SCENE_1.getCode(), "分销奖励", money, date);
        }
    }

    private void taKuHandleSellingFunctionTwo(User user, Date date,
                                              Double eCpm) {
        double n = eCpm / 1000;
        BigDecimal bigDecimal = BigDecimal.valueOf(n);
        //再乘兑换比例
        Integer convertRatio = convertRatioService.getConvertRatio();
        bigDecimal = bigDecimal.multiply(new BigDecimal(convertRatio));
        //查看上级是不是代理并设置了分销比例
        TUserRole tUserRole = userRoleService.selectTUserRoleById(user.getRoleId().longValue());
        if (tUserRole.getIndirectRatio().compareTo(BigDecimal.ZERO) > 0) {
            //进行分销处理
            BigDecimal money = bigDecimal;
            //设置的金额*比例值（代理比例）*0.01；保留两位小数
            money = money
                    .multiply(tUserRole.getIndirectRatio())
                    .multiply(Constant.PROPORTIONAL_CALCULATION_METHOD)
                    .setScale(0, RoundingMode.DOWN);
            //加金币
            userDao.addGoldCoin(user.getId(), money);
            //加账单
            billGoldService.addBillGold(user.getId(),
                    BillSceneTypeEnum.SCENE_1.getCode(), "分销奖励", money, date);
        }
    }

    /**
     * 处理分销(区间收益)
     */
    private void taKuHandleSellingFunction2(User user, Date date,
                                            BigDecimal moneyInfo) {
        //查看上级是不是代理并设置了分销比例
        if (user.getCommander().equals(UserIsCommanderEnum.TYPE_1.getCode())) {
            if (user.getCommanderRatio().doubleValue() > 0) {
                //进行分销处理
                BigDecimal money = moneyInfo;
                //设置的金额*比例值（代理比例）*0.01；保留两位小数
                money = money
                        .multiply(user.getCommanderRatio())
                        .multiply(Constant.PROPORTIONAL_CALCULATION_METHOD)
                        .setScale(0, RoundingMode.DOWN);
                //加金币
                userDao.addGoldCoin(user.getId(), money);
                //加账单
                billGoldService.addBillGold(user.getId(),
                        BillSceneTypeEnum.SCENE_1.getCode(), "分销奖励", money, date);
            }
        }
    }

    @Override
    public TaKuAdCallbackVO getTaKuAdCallbackVO(String transId) {
        TaKuCallback taKuCallback = taKuCallbackDao.selectTaKuCallbackByReqId(transId);
        if (Objects.nonNull(taKuCallback)) {
            TaKuAdCallbackVO taKuAdCallbackVO = new TaKuAdCallbackVO();
            BeanUtils.copyProperties(taKuCallback, taKuAdCallbackVO);
            taKuAdCallbackVO.setIsUnlock(taKuCallback.getUnlock());
            return taKuAdCallbackVO;
        }
        return null;
    }

    /**
     * 处理验签
     */
    private Boolean check(TaKuAdCallbackDTO dto) {
        try {
            List<TaKuConfig> taKuConfigs = taKuConfigDao.selectTaKuAdConfigListAll();
            String sign = taKuConfigs.stream()
                    .filter(item -> TaKuAdConfigTypeEnum.TYPE_2.getCode().equals(item.getType()))
                    .collect(Collectors.toList()).get(0).getSign();
            String trans_id = dto.getTrans_id();
            String placement_id = dto.getPlacement_id();
            String adsource_id = dto.getAdsource_id();
            String reward_amount = dto.getReward_amount();
            String reward_name = dto.getReward_name();
            String sec_key = sign;
            String ilrd = dto.getIlrd();
            String s = "trans_id=" + trans_id +
                    "&placement_id=" + placement_id +
                    "&adsource_id=" + adsource_id +
                    "&reward_amount=" + reward_amount +
                    "&reward_name=" + reward_name +
                    "&sec_key=" + sec_key +
                    "&ilrd=" + ilrd;

            String md5Hash = Hashing.md5()
                    .hashString(s, StandardCharsets.UTF_8)
                    .toString();
            log.info("TaKu回调验签 本地sign=[{}], Taku sign=[{}]", md5Hash, dto.getSign());
            log.info("TaKu回调验签 拼接字符串=[{}], sec_key=[{}]", s, sec_key);
            log.info("TaKu回调验签 dto=[{}]", dto);
            if (md5Hash.equals(dto.getSign())) {
                log.info("TaKu验签成功===========================================");
                return true;
            }
        } catch (Exception e) {
            log.info("TaKu验签失败，报错===========================================", e);
            return false;
        }
        log.info("TaKu验签失败===========================================");
        return false;
    }


    @Override
    public void checkIsLookAd() {
        //查询当前用户今天看了多少次广告
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        Integer count = taKuCallbackDao.selectTaKuCallbackCount(requestUserId);
        //查询配置的信息
        int checkCount = Integer.parseInt(paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_10.getCode()).get(0).getValue());
        if (count >= checkCount) {
            throw new ApiException("今日广告查看次数已达上限！");
        }
    }
    @Override
    public Integer lookAdCount() {
        //查询当前用户今天看了多少次广告
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        Integer count = taKuCallbackDao.selectTaKuCallbackCount(requestUserId);
        return count;
    }



    @Override
    public Map checkIsLookMakeMoneyAd() {
        Map map = new HashMap<>();
        map.put("secondInfo", 0);
        map.put("todaySum", 0);
        map.put("todayList", new ArrayList<>());
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        map.put("totalSum", taskDailyLookVideoToCoinRecordDao.selectTotalCount(requestUserId));
        TaskDailyLookVideoToCoin taskDailyLookVideoToCoin = taskDailyLookVideoToCoinDao.selectTaskDailyLookVideoToCoinInfo();
        map.put("secondInfo", taskDailyLookVideoToCoin.getSecondInfo());
//        map.put("interstitialAdCountdown", Integer.parseInt(paramsConfigService.getParamsList(17).get(0).getValue()));
//        TaskDailyLookVideoToCoinRecord taskDailyLookVideoToCoinRecord = taskDailyLookVideoToCoinRecordDao.selectTaskDailyLookVideoToCoinRecordTheSameDay(requestUserId);
//        if (Objects.nonNull(taskDailyLookVideoToCoinRecord)) {
//            TaskDailyLookVideoToCoin taskDailyLookVideoToCoin = taskDailyLookVideoToCoinDao.selectTaskDailyLookVideoToCoinInfo();
//            Integer timeNum = taskDailyLookVideoToCoin.getSecondInfo();
//            long lastTimeNum = DatesUtil.dateDiff(taskDailyLookVideoToCoinRecord.getUpdateTime(), new Date()) / 1000;
//            Integer time = new Long(lastTimeNum).intValue();
//            if (timeNum > time) {
//                map.put("secondInfo", timeNum - time);
//                map.put("todaySum", taskDailyLookVideoToCoinRecord.getCount());
//            }
//            map.put("todayList", taKuCallbackDao.selectTodayTaKuCallList(requestUserId));
//        }
        return map;
    }
}

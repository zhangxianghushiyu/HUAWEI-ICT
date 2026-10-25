package com.ruoyi.platform.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.ruoyi.platform.config.GenerateQRCodeImageConfig;
import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.constant.Constant;
import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.dto.*;
import com.ruoyi.platform.enums.*;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.*;
import com.ruoyi.platform.utils.EncryptUtils;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.utils.RedisUtil;
import com.ruoyi.platform.vo.MyInvitationVO;
import com.ruoyi.platform.vo.UserDetailVO;
import com.ruoyi.platform.vo.UserVO;
import com.ruoyi.platform.vo.WxAccessTokenVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ConvertRatioService convertRatioService;
    @Autowired
    private BillGoldService billGoldService;
    @Autowired
    private BillCashService billCashService;
    @Autowired
    private ParamsConfigService paramsConfigService;
    @Autowired
    private WxService wxService;
    @Autowired
    private UserGradeDao userGradeDao;
    @Autowired
    private BillGoldDao billGoldDao;
    @Autowired
    private DramaUnlockDao dramaUnlockDao;
    @Autowired
    private UserLoginLogService userLoginLogService;
    @Autowired
    private ITUserRoleService userRoleService;
    @Autowired
    private WithdrawalUserDao withdrawalUserDao;
    @Autowired
    private GenerateQRCodeImageConfig generateQRCodeImageConfig;


    @Override
    @Transactional
    public UserVO userLogin(UserLoginDTO dto) {
        //查看验证码是否存在
        Object object = redisUtil.get(Constant.SEND_CODE + dto.getScene() + ":" + dto.getPhone());
        if (Objects.isNull(object)) {
            throw new ApiException("验证码信息不正确");
        }
        if (!object.toString().equals(dto.getCode())) {
            throw new ApiException("验证码填写不正确");
        }
        Date date = new Date();
        UserVO userVO = new UserVO();
        //查询之前有没有这个用户
        User user = userDao.selectUserByPhoneNoUnsubscribe(dto.getPhone());
        if (Objects.nonNull(user)) {
            checkUser(user);
            BeanUtils.copyProperties(user, userVO);
            userVO.setIsRegister(0);
        } else {
            //注册
            User user1 = new User();
            //生成邀请码
            //生成6位随机数(数字)
            String code = RandomUtil.randomNumbers(6);
            //查询是否有重复的信息
            User user2 = userDao.selectUserByInvitationCode(code);
            while (Objects.nonNull(user2)) {
                //重新生成
                code = RandomUtil.randomNumbers(6);
                //再次查询
                user2 = userDao.selectUserByInvitationCode(code);
            }
            user1.setAvatar(
                    paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_5.getCode()).get(0).getValue()
            );
            user1.setNickName(
                    paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_6.getCode()).get(0).getValue()
                            + "_" +
                            RandomUtil.randomNumbers(4));
            //生成一个盐
            String salt = RandomUtil.randomString(16);
            user1.setPassword(EncryptUtils.passwordEncryption(
                    paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_7.getCode()).get(0).getValue()
                    , salt));
            user1.setPhone(dto.getPhone());
            user1.setCreateTime(date);
            user1.setInvitationCode(code);
            user1.setQrUrl("https://jkll-polymer-h5-re.nanjingriyao.com?invitationCode=" + code);
            user1.setQrCode(generateQRCodeImageConfig.generateQRCodeImage(user1.getQrUrl()));
            user1.setSalt(salt);
            //生成一个账号
            String userName = RandomUtil.randomNumbers(11);
            User user3 = userDao.selectUserByUserNameNoUnsubscribe(userName);
            while (Objects.nonNull(user3)) {
                //重新生成
                userName = RandomUtil.randomNumbers(11);
                //再次查询
                user3 = userDao.selectUserByUserNameNoUnsubscribe(userName);
            }
            user1.setUserName(userName);
            userDao.insertSelective(user1);

            //初始化用户阶级记录
            UserGrade userGrade = new UserGrade();
            userGrade.setUserId(user1.getId());
            //默认已通过0
            userGrade.setBarrier(0);

            //目标等级是2
            userGrade.setGrade(2);
            userGrade.setCreateTime(date);
            userGradeDao.insertSelective(userGrade);

            BeanUtils.copyProperties(user1, userVO);
            userVO.setIsRegister(1);
        }
        //处理token
        String s = userVO.getId() + "";
        String token = JwtUtil.createJWT(s);
        redisUtil.set(Constant.USER_LOGIN_TOKEN + userVO.getId(), token, Constant.USER_LOGIN_TOKEN_EXPIRE_TIME, TimeUnit.DAYS);
        userVO.setToken(token);
        //删除验证码
        redisUtil.del(Constant.SEND_CODE + dto.getScene() + ":" + dto.getPhone());
        //登录日志
        userLoginLogService.addUserLoginLogInfo(userVO.getId(), date);
        return userVO;
    }


    @Override
    @Transactional
    public UserVO accountLogin(AccountLoginDTO dto) {
        if (dto.getUserName().length() < 8 || dto.getUserName().length() > 11) {
            throw new ApiException("账号必须是8-11位");
        }
        if (dto.getPassword().length() < 6 || dto.getPassword().length() > 10) {
            throw new ApiException("密码必须是6-10位");
        }
        UserVO userVO = new UserVO();
        //查询之前有没有这个用户
        User user = userDao.selectUserByUserNameNoUnsubscribe(dto.getUserName());
        if (Objects.isNull(user)) {
            throw new ApiException("账号或密码错误，登录失败");
        }
        //查看密码是不是正确
        String password = EncryptUtils.passwordEncryption(dto.getPassword(), user.getSalt());
        if (!password.equals(user.getPassword())) {
            throw new ApiException("账号或密码错误，登录失败");
        }
        checkUser(user);
        BeanUtils.copyProperties(user, userVO);
        //处理token
        String s = userVO.getId() + "";
        String token = JwtUtil.createJWT(s);
        redisUtil.set(Constant.USER_LOGIN_TOKEN + userVO.getId(), token, Constant.USER_LOGIN_TOKEN_EXPIRE_TIME, TimeUnit.DAYS);
        userVO.setToken(token);
        //登录日志
        userLoginLogService.addUserLoginLogInfo(userVO.getId(), new Date());
        return userVO;
    }


    @Override
    @Transactional
    public UserVO registerAccount(RegisterAccountDTO dto) {
        UserVO userVO = new UserVO();
        if (dto.getUserName().length() < 8 || dto.getUserName().length() > 11) {
            throw new ApiException("账号必须是8-11位");
        }
        if (dto.getPassword().length() < 6 || dto.getPassword().length() > 10) {
            throw new ApiException("密码必须是6-10位");
        }
        //查询账号是不是已经被注册
        User user = userDao.selectUserByUserNameNoUnsubscribe(dto.getUserName());
        if (Objects.nonNull(user)) {
            throw new ApiException("已存在此账号，无法重复注册");
        }
        //注册
        User user1 = new User();
        //生成邀请码
        //生成6位随机数(数字)
        String code = RandomUtil.randomNumbers(6);
        //查询是否有重复的信息
        User user2 = userDao.selectUserByInvitationCode(code);
        while (Objects.nonNull(user2)) {
            //重新生成
            code = RandomUtil.randomNumbers(6);
            //再次查询
            user2 = userDao.selectUserByInvitationCode(code);
        }
        Date date = new Date();
        user1.setAvatar(
                paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_5.getCode()).get(0).getValue()
        );
        user1.setNickName(
                paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_6.getCode()).get(0).getValue()
                        + "_" +
                        RandomUtil.randomNumbers(4));
        //生成一个盐
        String salt = RandomUtil.randomString(16);
        user1.setPassword(EncryptUtils.passwordEncryption(dto.getPassword(), salt));
        user1.setCreateTime(date);
        user1.setInvitationCode(code);
        user1.setQrUrl("https://jkll-polymer-h5-re.nanjingriyao.com?invitationCode=" + code);
        user1.setQrCode(generateQRCodeImageConfig.generateQRCodeImage(user1.getQrUrl()));
        user1.setUserName(dto.getUserName());
        user1.setSalt(salt);
        //查看是不是填了邀请码
        if (!StringUtils.isEmpty(dto.getInvitationCode())) {
            //通过邀请码查询用户信息
            User user3 = userDao.selectUserByInvitationCode(dto.getInvitationCode());
            if (Objects.isNull(user3)) {
                throw new ApiException("邀请码信息不存在");
            }
            //判断邀请人职位
            if (user3.getRoleId() == 1) {
                user1.setRoleId(2);
            } else if (user3.getRoleId() == 4) {
                user1.setRoleId(4);
            } else {
                user1.setRoleId(3);
            }
            //绑定邀请关系
            user1.setParentId(user3.getId());
            user1.setInvitationTime(date);
        }
        userDao.insertSelective(user1);


        //初始化用户阶级记录
        UserGrade userGrade = new UserGrade();
        userGrade.setUserId(user1.getId());
        //默认已通过0
        userGrade.setBarrier(0);

        //目标等级是2
        userGrade.setGrade(2);
        userGrade.setCreateTime(date);
        userGradeDao.insertSelective(userGrade);

        BeanUtils.copyProperties(user1, userVO);
        return userVO;
    }


    @Override
    @Transactional
    public UserVO wxLogin(WxLoginDTO dto) {
        UserVO userVO = new UserVO();
        WxAccessTokenVO wxAccessTokenVO = wxService.wxCodeToWxAccessTokenVO(dto.getCode());
        if (Objects.isNull(wxAccessTokenVO)) {
            throw new ApiException("获取微信信息失败");
        }
        String openId = wxAccessTokenVO.getOpenId();
        Date date = new Date();
        //查看之前是否注册过
        User user = userDao.selectUserByOpenIdNoUnsubscribe(openId);
        String s;
        if (Objects.nonNull(user)) {
            //检查用户
            checkUser(user);
            //注册过，更新一次头像及昵称
            user.setWxNickName(StrUtil.isNotEmpty(wxAccessTokenVO.getNickName()) ? wxAccessTokenVO.getNickName() : null);
            user.setWxAvatar(StrUtil.isNotEmpty(wxAccessTokenVO.getHeadimgurl()) ? wxAccessTokenVO.getHeadimgurl() : null);
            userDao.updateByPrimaryKeySelective(user);
            s = user.getId() + "";
        } else {
            //创建
            User userAdd = new User();
            userAdd.setWxOpenId(StrUtil.isNotEmpty(openId) ? openId : null);
            userAdd.setWxNickName(StrUtil.isNotEmpty(wxAccessTokenVO.getNickName()) ? wxAccessTokenVO.getNickName() : null);
            userAdd.setWxAvatar(StrUtil.isNotEmpty(wxAccessTokenVO.getHeadimgurl()) ? wxAccessTokenVO.getHeadimgurl() : null);
            userAdd.setWxUnionId(StrUtil.isNotEmpty(wxAccessTokenVO.getUnionId()) ? wxAccessTokenVO.getUnionId() : null);
            userAdd.setCreateTime(date);

            //生成邀请码
            //生成6位随机数(数字)
            String code = RandomUtil.randomNumbers(6);
            //查询是否有重复的信息
            User user2 = userDao.selectUserByInvitationCode(code);
            while (Objects.nonNull(user2)) {
                //重新生成
                code = RandomUtil.randomNumbers(6);
                //再次查询
                user2 = userDao.selectUserByInvitationCode(code);
            }
            userAdd.setAvatar(
                    paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_5.getCode()).get(0).getValue()
            );
            userAdd.setNickName(
                    paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_6.getCode()).get(0).getValue()
                            + "_" +
                            RandomUtil.randomNumbers(4));
            //生成一个盐
            String salt = RandomUtil.randomString(16);
            userAdd.setPassword(EncryptUtils.passwordEncryption(
                    paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_7.getCode()).get(0).getValue()
                    , salt));
            userAdd.setInvitationCode(code);
            userAdd.setQrUrl("https://jkll-polymer-h5-re.nanjingriyao.com?invitationCode=" + code);
            userAdd.setQrCode(generateQRCodeImageConfig.generateQRCodeImage(userAdd.getQrUrl()));
            userAdd.setSalt(salt);
            //生成一个账号
            String userName = RandomUtil.randomNumbers(11);
            User user3 = userDao.selectUserByUserNameNoUnsubscribe(userName);
            while (Objects.nonNull(user3)) {
                //重新生成
                userName = RandomUtil.randomNumbers(11);
                //再次查询
                user3 = userDao.selectUserByUserNameNoUnsubscribe(userName);
            }
            userAdd.setUserName(userName);
            userDao.insertSelective(userAdd);

            //生成提现绑定信息
            WithdrawalUser withdrawalUser1 = new WithdrawalUser();
            withdrawalUser1.setUserId(userAdd.getId());
            withdrawalUser1.setType(WithdrawalUserEnum.WEI.getCode());
            withdrawalUser1.setOpenId(openId);
            withdrawalUser1.setNickName(wxAccessTokenVO.getNickName());
            withdrawalUser1.setUnionId(wxAccessTokenVO.getUnionId());
            withdrawalUser1.setHeadImgUrl(wxAccessTokenVO.getHeadimgurl());
            withdrawalUser1.setCreateTime(new Date());
            withdrawalUserDao.insertSelective(withdrawalUser1);

            //初始化用户阶级记录
            UserGrade userGrade = new UserGrade();
            userGrade.setUserId(userAdd.getId());
            //默认已通过0
            userGrade.setBarrier(0);

            //目标等级是2
            userGrade.setGrade(2);
            userGrade.setCreateTime(date);
            userGradeDao.insertSelective(userGrade);

            s = userAdd.getId() + "";
        }
        //再次查询
        User userTwo = userDao.selectUserByOpenIdNoUnsubscribe(openId);
        BeanUtils.copyProperties(userTwo, userVO);
        //处理token
        String token = JwtUtil.createJWT(s);
        redisUtil.set(Constant.USER_LOGIN_TOKEN + userVO.getId(), token, Constant.USER_LOGIN_TOKEN_EXPIRE_TIME, TimeUnit.DAYS);
        userVO.setToken(token);
        //登录日志
        userLoginLogService.addUserLoginLogInfo(userVO.getId(), date);
        return userVO;
    }

    @Override
    public void modifyPassword(ModifyPasswordDTO dto) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        User user = userDao.selectByPrimaryKey(requestUserId);
        checkUser(user);
        if (dto.getOldPassword().length() < 6 || dto.getOldPassword().length() > 10) {
            throw new ApiException("密码必须是6-10位");
        }
        if (dto.getNewPassword().length() < 6 || dto.getNewPassword().length() > 10) {
            throw new ApiException("密码必须是6-10位");
        }
        String oldPass = EncryptUtils.passwordEncryption(dto.getOldPassword(), user.getSalt());
        if (!user.getPassword().equals(oldPass)) {
            throw new ApiException("原密码不正确");
        }
        //修改密码
        user.setPassword(EncryptUtils.passwordEncryption(dto.getNewPassword(), user.getSalt()));
        userDao.updateByPrimaryKeySelective(user);
    }


    @Override
    public void modifyZfb(ModifyZfbDTO dto) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        User user = userDao.selectByPrimaryKey(requestUserId);
        checkUser(user);
        user.setZfbName(dto.getZfbName());
        user.setZfbUserName(dto.getZfbUserName());
        userDao.updateByPrimaryKeySelective(user);
        WithdrawalUser withdrawalUser = withdrawalUserDao.selectWithdrawalUserByTypeAndUserId(WithdrawalUserEnum.ALI.getCode(), requestUserId);
        if(Objects.isNull(withdrawalUser)){
            withdrawalUser = new WithdrawalUser();
            withdrawalUser.setUserId(requestUserId);
            withdrawalUser.setType(WithdrawalUserEnum.ALI.getCode());
            withdrawalUser.setOpenId(user.getZfbUserName());
            withdrawalUser.setNickName(user.getZfbName());
            withdrawalUser.setCreateTime(new Date());
            withdrawalUserDao.insertSelective(withdrawalUser);
        }else{
            withdrawalUser.setOpenId(user.getZfbUserName());
            withdrawalUser.setNickName(user.getZfbName());
            withdrawalUserDao.updateByPrimaryKeySelective(withdrawalUser);
        }
    }

    @Override
    public UserDetailVO getUserDetail(Integer requestUserId) {
        UserDetailVO vo = new UserDetailVO();
        User user = userDao.selectByPrimaryKey(requestUserId);
        checkUser(user);
        BeanUtils.copyProperties(user, vo);
        //总人数
        vo.setSumJuniorCount(userDao.selectSumJuniorCount(requestUserId));
        //今日收益
        vo.setTodayGetMoney(billGoldDao.selectTodayGetMoney(requestUserId));
        //今日新增
        vo.setTodayJuniorCount(userDao.selectTodayJuniorCount(requestUserId));
        //昨日新增
        vo.setYesterdayJuniorCount(userDao.selectYesterdayJuniorCount(requestUserId));
        //解锁短剧需要的金额
        DramaUnlock dramaUnlock = dramaUnlockDao.selectDramaUnlock();
        if (Objects.nonNull(dramaUnlock)) {
            vo.setDramaUnlockMoney(dramaUnlock.getMoney());
        }
        String value1 = paramsConfigService.getParamsList(12).get(0).getValue();
        String value2 = paramsConfigService.getParamsList(13).get(0).getValue();
        vo.setMotivateMinute(Integer.parseInt(value1));
        vo.setInformationFlowPageNum(Integer.parseInt(value2));
        return vo;
    }

    @Override
    public void modify(UserModifyDTO dto) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        User user = userDao.selectByPrimaryKey(requestUserId);
        checkUser(user);
        user.setAvatar(dto.getAvatar());
        user.setNickName(dto.getNickName());
        userDao.updateByPrimaryKeySelective(user);
    }


    @Override
    public void checkUser(User user) {
        if (Objects.isNull(user)) {
            throw new ApiException("用户信息不存在");
        }
        if (user.getDelStatus().equals(DelStatusEnum.DELETE.getCode())) {
            throw new ApiException("该用户已被删除");
        }
        if (user.getStatus().equals(UserStatusEnum.STATUS_0.getCode())) {
            throw new ApiException("该账号已被停用");
        }
    }


    @Override
    public R setInvitationCode(String invitationCode) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        //查询自己的信息
        User user1 = userDao.selectByPrimaryKey(requestUserId);
        checkUser(user1);
        if (user1.getCommander().equals(UserIsCommanderEnum.TYPE_1.getCode())) {
            return R.error("您已是代理，无法填写邀请码");
        }
        if (Objects.nonNull(user1.getParentId())) {
            return R.error("您已存在上级，无法填写邀请码");
        }
        if (invitationCode.equals(user1.getInvitationCode())) {
            return R.error("不能填写自己的邀请码");
        }
        //通过邀请码查询用户信息
        User user2 = userDao.selectUserByInvitationCode(invitationCode);
        if (Objects.isNull(user2)) {
            return R.error("邀请码信息不存在");
        }
        //判断邀请人职位
        if (user2.getRoleId() == 1) {
            user1.setRoleId(2);
        } else if (user2.getRoleId() == 4) {
            user1.setRoleId(4);
        } else {
            user1.setRoleId(3);
        }
        //绑定邀请关系
        user1.setParentId(user2.getId());
        user1.setInvitationTime(new Date());
        userDao.updateByPrimaryKeySelective(user1);
        return R.ok();
    }


    @Override
    public List<MyInvitationVO> myInvitation() {
        return userDao.myInvitation(JwtUtil.getRequestUserId(request));
    }


    @Override
    @Transactional
    public void goldCoinToCash(Integer goldCoin) {
        Integer convertRatio = convertRatioService.getConvertRatio();
        if (goldCoin < convertRatio) {
            throw new ApiException("兑换金额不能小于比例值");
        }
        if (goldCoin % 100 != 0) {//判断是否为10的倍数，即能被10整除且余数为0
            throw new ApiException("请输入100的倍数");
        }
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        //查看用户金币是否足够
        User user = userDao.selectByPrimaryKey(requestUserId);
        checkUser(user);
        Date date = new Date();
        //比较大小
        if (user.getGoldCoin().intValue() < goldCoin) {
            throw new ApiException("金额不足");
        }
        BigDecimal num = BigDecimal.valueOf(goldCoin).divide(BigDecimal.valueOf(convertRatio));
        //加现金
        userDao.addCash(requestUserId, num);
        //减金币
        userDao.subGoldCoin(requestUserId, new BigDecimal(goldCoin));
        //添加到记录中
        billGoldService.addBillGold(requestUserId, BillSceneTypeEnum.SCENE_2.getCode(), "金币兑换现金",
                new BigDecimal(goldCoin), date);
        billCashService.addBillCash(null, BillCashTypeEnum.TYPE_3.getCode(), null, requestUserId,
                BillSceneTypeEnum.SCENE_1.getCode(), "金币兑换现金",
                num, date, null, null, BillCashTakeEnum.TYPE_1.getCode(),
                null, null, null, null, null);
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 10; i++) {
            System.out.println(RandomUtil.randomNumbers(10));
        }
    }
}

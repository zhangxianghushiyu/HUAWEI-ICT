package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.vo.MyInvitationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectByParentId(@Param("parentId") Integer parentId);

    /**
     * 查询用户信息，通过手机号(不查询注销)
     *
     * @param phone 手机号
     * @return 结果
     */
    User selectUserByPhoneNoUnsubscribe(@Param("phone") String phone);


    /**
     * 通过邀请码查询用户信息
     *
     * @param invitationCode
     * @return
     */
    User selectUserByInvitationCode(@Param("invitationCode") String invitationCode);

    /**
     * 加用户金币
     */
    Integer addGoldCoin(@Param("userId") Integer userId, @Param("goldCoin") BigDecimal goldCoin);

    /**
     * 减用户金币
     */
    Integer subGoldCoin(@Param("userId") Integer userId, @Param("goldCoin") BigDecimal goldCoin);

    /**
     * 我的邀请记录
     */
    List<MyInvitationVO> myInvitation(@Param("parentId") Integer parentId);


    /**
     * 加用户现金
     */
    Integer addCash(@Param("userId") Integer userId, @Param("cash") BigDecimal cash);

    /**
     * 减用户现金
     */
    Integer subCash(@Param("userId") Integer userId, @Param("cash") BigDecimal cash);

    /**
     * 通过账号查询用户信息是不是存在
     */
    User selectUserByUserNameNoUnsubscribe(@Param("userName") String userName);

    /**
     * 通过账号查询用户信息是不是存在
     */
    User selectUserByOpenIdNoUnsubscribe(@Param("openId") String openId);

    /**
     * 查询下级总人数
     */
    Integer selectSumJuniorCount(@Param("userId") Integer userId);

    /**
     * 查询今日下级总人数
     */
    Integer selectTodayJuniorCount(@Param("userId") Integer userId);

    /**
     * 查询昨日下级总人数
     */
    Integer selectYesterdayJuniorCount(@Param("userId") Integer userId);

    /**
     * 通过ip查询用户
     */
    List<User> selectUserListByLastIp(@Param("lastIp") String lastIp);
}
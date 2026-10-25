package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaKuCallback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaKuCallbackDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaKuCallback record);

    int insertSelective(TaKuCallback record);

    TaKuCallback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaKuCallback record);

    int updateByPrimaryKey(TaKuCallback record);

    /**
     * 查询信息根据transId
     */
    TaKuCallback selectTaKuCallbackByReqId(@Param("transId") String transId);

    /**
     * 查询用户当天观看广告的次数
     */
    Integer selectTaKuCallbackCount(@Param("userId") Integer userId);

    /**
     * 查询用户上次广告信息
     */
    TaKuCallback selectTaKuCallbackRecently(@Param("userId") Integer userId);

    /**
     * 查询连续条数
     */
    List<TaKuCallback> selectContinuousTaKuCallback(@Param("userId") Integer userId,@Param("limit")Integer limit);
}
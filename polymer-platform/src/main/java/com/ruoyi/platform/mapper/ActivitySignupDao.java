package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.ActivitySignup;
import com.ruoyi.platform.vo.MySignupListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报名记录Mapper接口
 */
public interface ActivitySignupDao {

    /**
     * 查询报名记录
     */
    ActivitySignup selectByPrimaryKey(Long id);

    /**
     * 根据用户ID和活动ID查询报名记录
     */
    ActivitySignup selectByUserIdAndActivityId(@Param("userId") Integer userId, @Param("activityId") Long activityId);

    /**
     * 查询用户的报名记录列表
     */
    List<MySignupListVO> selectMySignupList(Integer userId);

    /**
     * 新增报名记录
     */
    int insert(ActivitySignup record);

    /**
     * 修改报名记录
     */
    int updateByPrimaryKeySelective(ActivitySignup record);

    /**
     * 删除报名记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 统计活动报名人数
     */
    int countByActivityId(Long activityId);
}

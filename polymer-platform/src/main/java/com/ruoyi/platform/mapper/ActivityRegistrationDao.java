package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.ActivityRegistration;
import com.ruoyi.platform.vo.ActivityListVO;

import java.util.List;

/**
 * 活动Mapper接口
 */
public interface ActivityRegistrationDao {

    /**
     * 查询活动列表
     */
    List<ActivityListVO> selectActivityList(Integer delStatus);

    /**
     * 查询活动详情
     */
    ActivityRegistration selectByPrimaryKey(Long id);
}

package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskDailySign;
import com.ruoyi.platform.vo.DailySignInForSevenDaysDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskDailySignDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDailySign record);

    int insertSelective(TaskDailySign record);

    TaskDailySign selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDailySign record);

    int updateByPrimaryKey(TaskDailySign record);


    /**
     * 链表查询用户的签到信息
     */
    List<DailySignInForSevenDaysDetailVO> selectDailySignInForSevenDaysDetailVO(@Param("userId") Integer userId,
                                                                                @Param("batchNo") String batchNo);
}
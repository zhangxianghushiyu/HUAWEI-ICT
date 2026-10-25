package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskNewPeopleSign;
import com.ruoyi.platform.vo.NewcomersSignInForSevenDaysDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskNewPeopleSignDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskNewPeopleSign record);

    int insertSelective(TaskNewPeopleSign record);

    TaskNewPeopleSign selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskNewPeopleSign record);

    int updateByPrimaryKey(TaskNewPeopleSign record);


    /**
     * 查询所有配置信息
     */
    List<TaskNewPeopleSign> selectTaskNewPeopleSignAll();

    /**
     * 链表查询用户的签到信息
     */
    List<NewcomersSignInForSevenDaysDetailVO> selectNewcomersSignInForSevenDaysDetailVO(@Param("userId")Integer userId);
}
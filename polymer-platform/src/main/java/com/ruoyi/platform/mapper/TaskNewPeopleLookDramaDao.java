package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskNewPeopleLookDrama;
import com.ruoyi.platform.vo.NewPeopleLookDramaDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskNewPeopleLookDramaDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskNewPeopleLookDrama record);

    int insertSelective(TaskNewPeopleLookDrama record);

    TaskNewPeopleLookDrama selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskNewPeopleLookDrama record);

    int updateByPrimaryKey(TaskNewPeopleLookDrama record);


    /**
     * 链表查询用户的新人看剧领现金信息
     */
    List<NewPeopleLookDramaDetailVO> selectNewPeopleLookDramaDetailVO(@Param("userId")Integer userId);
}
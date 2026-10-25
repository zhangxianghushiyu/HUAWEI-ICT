package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.VoteActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VoteActivityDao {
    int deleteByPrimaryKey(Integer id);

    int insert(VoteActivity record);

    int insertSelective(VoteActivity record);

    VoteActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VoteActivity record);

    int updateByPrimaryKey(VoteActivity record);

    /**
     * 查询进行中的投票活动列表
     */
    List<VoteActivity> selectActiveList(@Param("userId") Integer userId);

    /**
     * 查询投票活动详情
     */
    VoteActivity selectDetailById(@Param("id") Integer id, @Param("userId") Integer userId);

    /**
     * 增加总投票人数
     */
    int addTotalVoters(@Param("id") Integer id);

    /**
     * 增加总投票数
     */
    int addTotalVotes(@Param("id") Integer id, @Param("count") Integer count);

    /**
     * 减少总投票数
     */
    int subTotalVotes(@Param("id") Integer id, @Param("count") Integer count);
}

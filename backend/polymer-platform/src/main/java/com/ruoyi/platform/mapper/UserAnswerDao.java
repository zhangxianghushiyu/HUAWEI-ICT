package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.UserAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserAnswerDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAnswer record);

    int insertSelective(UserAnswer record);

    UserAnswer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAnswer record);

    int updateByPrimaryKey(UserAnswer record);

    /**
     * 查询用户答题最新信息
     */
    UserAnswer selectUserAnswerByUserIdAndRecently(@Param("userId") Integer userId);
}
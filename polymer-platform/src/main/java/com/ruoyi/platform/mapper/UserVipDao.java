package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.UserVip;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserVipDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserVip record);

    int insertSelective(UserVip record);

    UserVip selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserVip record);

    int updateByPrimaryKey(UserVip record);


    /**
     * 根据用户Id查询会员信息
     * @param userId
     * @return
     */
    UserVip selectUserVipByUserId(@Param("userId")Integer userId);
}

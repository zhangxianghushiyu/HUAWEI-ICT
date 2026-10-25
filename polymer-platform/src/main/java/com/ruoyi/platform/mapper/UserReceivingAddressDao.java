package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.UserReceivingAddress;
import com.ruoyi.platform.vo.UserReceivingAddressListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserReceivingAddressDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserReceivingAddress record);

    int insertSelective(UserReceivingAddress record);

    UserReceivingAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserReceivingAddress record);

    int updateByPrimaryKey(UserReceivingAddress record);

    /**
     * 查询集合
     */
    List<UserReceivingAddressListVO> selectUserReceivingAddressListVOByUserId(@Param("userId") Integer userId);


}
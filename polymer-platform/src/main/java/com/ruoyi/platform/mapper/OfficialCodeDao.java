package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.OfficialCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OfficialCodeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(OfficialCode record);

    int insertSelective(OfficialCode record);

    OfficialCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OfficialCode record);

    int updateByPrimaryKey(OfficialCode record);


    /**
     * 查询官方二维码
     *
     * @return
     */
    OfficialCode selectOfficialCodeInfo();
}
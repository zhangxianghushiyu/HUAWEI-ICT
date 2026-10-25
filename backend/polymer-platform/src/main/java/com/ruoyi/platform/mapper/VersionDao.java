package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.Version;
import com.ruoyi.platform.vo.VersionVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VersionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Version record);

    int insertSelective(Version record);

    Version selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Version record);

    int updateByPrimaryKey(Version record);


    /**
     * 获取版本信息
     * @return
     */
    VersionVO selectVersionVO();
}
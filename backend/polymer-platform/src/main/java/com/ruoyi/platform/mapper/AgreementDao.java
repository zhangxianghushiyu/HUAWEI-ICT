package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.Agreement;
import com.ruoyi.platform.vo.AgreementVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AgreementDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Agreement record);

    int insertSelective(Agreement record);

    Agreement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Agreement record);

    int updateByPrimaryKey(Agreement record);


    /**
     * 协议信息
     * @param type
     * @return
     */
    AgreementVO selectAgreementVO(@Param("type")Integer type);
}
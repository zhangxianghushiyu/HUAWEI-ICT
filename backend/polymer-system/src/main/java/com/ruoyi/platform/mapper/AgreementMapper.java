package com.ruoyi.platform.mapper;

import java.util.List;

import com.ruoyi.platform.domain.Agreement;
import org.apache.ibatis.annotations.Param;

/**
 * 协议信息Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-11
 */
public interface AgreementMapper {
    /**
     * 查询协议信息
     *
     * @param id 协议信息主键
     * @return 协议信息
     */
    public Agreement selectAgreementById(Long id);

    /**
     * 查询协议信息列表
     *
     * @param agreement 协议信息
     * @return 协议信息集合
     */
    public List<Agreement> selectAgreementList(Agreement agreement);

    /**
     * 新增协议信息
     *
     * @param agreement 协议信息
     * @return 结果
     */
    public int insertAgreement(Agreement agreement);

    /**
     * 修改协议信息
     *
     * @param agreement 协议信息
     * @return 结果
     */
    public int updateAgreement(Agreement agreement);

    /**
     * 删除协议信息
     *
     * @param id 协议信息主键
     * @return 结果
     */
    public int deleteAgreementById(Long id);

    /**
     * 批量删除协议信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAgreementByIds(Long[] ids);


    /**
     * 根据类型查询
     */
    Agreement selectAgreementByType(@Param("type") Integer type);
}

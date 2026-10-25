package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.AgreementMapper;
import com.ruoyi.platform.domain.Agreement;
import com.ruoyi.platform.service.IAgreementService;

/**
 * 协议信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-11
 */
@Service
public class AgreementServiceImpl implements IAgreementService 
{
    @Autowired
    private AgreementMapper agreementMapper;

    /**
     * 查询协议信息
     * 
     * @param id 协议信息主键
     * @return 协议信息
     */
    @Override
    public Agreement selectAgreementById(Long id)
    {
        return agreementMapper.selectAgreementById(id);
    }

    /**
     * 查询协议信息列表
     * 
     * @param agreement 协议信息
     * @return 协议信息
     */
    @Override
    public List<Agreement> selectAgreementList(Agreement agreement)
    {
        return agreementMapper.selectAgreementList(agreement);
    }

    /**
     * 新增协议信息
     * 
     * @param agreement 协议信息
     * @return 结果
     */
    @Override
    public int insertAgreement(Agreement agreement)
    {
        agreement.setCreateTime(DateUtils.getNowDate());
        return agreementMapper.insertAgreement(agreement);
    }

    /**
     * 修改协议信息
     * 
     * @param agreement 协议信息
     * @return 结果
     */
    @Override
    public int updateAgreement(Agreement agreement)
    {
        agreement.setUpdateTime(DateUtils.getNowDate());
        return agreementMapper.updateAgreement(agreement);
    }

    /**
     * 批量删除协议信息
     * 
     * @param ids 需要删除的协议信息主键
     * @return 结果
     */
    @Override
    public int deleteAgreementByIds(Long[] ids)
    {
        return agreementMapper.deleteAgreementByIds(ids);
    }

    /**
     * 删除协议信息信息
     * 
     * @param id 协议信息主键
     * @return 结果
     */
    @Override
    public int deleteAgreementById(Long id)
    {
        return agreementMapper.deleteAgreementById(id);
    }

    @Override
    public Agreement getAgreementByType(Integer type) {
        return agreementMapper.selectAgreementByType(type);
    }
}

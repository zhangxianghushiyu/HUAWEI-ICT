package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.dto.ApplyDestroyListDTO;
import com.ruoyi.platform.vo.ApplyDestroyListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.ApplyDestroyMapper;
import com.ruoyi.platform.domain.ApplyDestroy;
import com.ruoyi.platform.service.IApplyDestroyService;

/**
 * 申请注销Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
@Service
public class ApplyDestroyServiceImpl implements IApplyDestroyService 
{
    @Autowired
    private ApplyDestroyMapper applyDestroyMapper;

    /**
     * 查询申请注销
     * 
     * @param id 申请注销主键
     * @return 申请注销
     */
    @Override
    public ApplyDestroy selectApplyDestroyById(Long id)
    {
        return applyDestroyMapper.selectApplyDestroyById(id);
    }

    /**
     * 查询申请注销列表
     * 
     * @param applyDestroy 申请注销
     * @return 申请注销
     */
    @Override
    public List<ApplyDestroy> selectApplyDestroyList(ApplyDestroy applyDestroy)
    {
        return applyDestroyMapper.selectApplyDestroyList(applyDestroy);
    }

    /**
     * 新增申请注销
     * 
     * @param applyDestroy 申请注销
     * @return 结果
     */
    @Override
    public int insertApplyDestroy(ApplyDestroy applyDestroy)
    {
        applyDestroy.setCreateTime(DateUtils.getNowDate());
        return applyDestroyMapper.insertApplyDestroy(applyDestroy);
    }

    /**
     * 修改申请注销
     * 
     * @param applyDestroy 申请注销
     * @return 结果
     */
    @Override
    public int updateApplyDestroy(ApplyDestroy applyDestroy)
    {
        applyDestroy.setUpdateTime(DateUtils.getNowDate());
        return applyDestroyMapper.updateApplyDestroy(applyDestroy);
    }

    /**
     * 批量删除申请注销
     * 
     * @param ids 需要删除的申请注销主键
     * @return 结果
     */
    @Override
    public int deleteApplyDestroyByIds(Long[] ids)
    {
        return applyDestroyMapper.deleteApplyDestroyByIds(ids);
    }

    /**
     * 删除申请注销信息
     * 
     * @param id 申请注销主键
     * @return 结果
     */
    @Override
    public int deleteApplyDestroyById(Long id)
    {
        return applyDestroyMapper.deleteApplyDestroyById(id);
    }

    @Override
    public List<ApplyDestroyListVO> selectApplyDestroyListVO(ApplyDestroyListDTO dto) {
        return applyDestroyMapper.selectApplyDestroyListVO(dto);
    }
}

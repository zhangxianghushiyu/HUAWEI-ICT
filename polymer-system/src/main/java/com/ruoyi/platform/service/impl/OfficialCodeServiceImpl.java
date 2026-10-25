package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.OfficialCodeMapper;
import com.ruoyi.platform.domain.OfficialCode;
import com.ruoyi.platform.service.IOfficialCodeService;

/**
 * 官方二维码信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-11
 */
@Service
public class OfficialCodeServiceImpl implements IOfficialCodeService 
{
    @Autowired
    private OfficialCodeMapper officialCodeMapper;

    /**
     * 查询官方二维码信息
     * 
     * @param id 官方二维码信息主键
     * @return 官方二维码信息
     */
    @Override
    public OfficialCode selectOfficialCodeById(Long id)
    {
        return officialCodeMapper.selectOfficialCodeById(id);
    }

    /**
     * 查询官方二维码信息列表
     * 
     * @param officialCode 官方二维码信息
     * @return 官方二维码信息
     */
    @Override
    public List<OfficialCode> selectOfficialCodeList(OfficialCode officialCode)
    {
        return officialCodeMapper.selectOfficialCodeList(officialCode);
    }

    /**
     * 新增官方二维码信息
     * 
     * @param officialCode 官方二维码信息
     * @return 结果
     */
    @Override
    public int insertOfficialCode(OfficialCode officialCode)
    {
        officialCode.setCreateTime(DateUtils.getNowDate());
        return officialCodeMapper.insertOfficialCode(officialCode);
    }

    /**
     * 修改官方二维码信息
     * 
     * @param officialCode 官方二维码信息
     * @return 结果
     */
    @Override
    public int updateOfficialCode(OfficialCode officialCode)
    {
        officialCode.setUpdateTime(DateUtils.getNowDate());
        return officialCodeMapper.updateOfficialCode(officialCode);
    }

    /**
     * 批量删除官方二维码信息
     * 
     * @param ids 需要删除的官方二维码信息主键
     * @return 结果
     */
    @Override
    public int deleteOfficialCodeByIds(Long[] ids)
    {
        return officialCodeMapper.deleteOfficialCodeByIds(ids);
    }

    /**
     * 删除官方二维码信息信息
     * 
     * @param id 官方二维码信息主键
     * @return 结果
     */
    @Override
    public int deleteOfficialCodeById(Long id)
    {
        return officialCodeMapper.deleteOfficialCodeById(id);
    }

    @Override
    public OfficialCode getOfficialCodeInfo() {
        return officialCodeMapper.selectOfficialCodeInfo();
    }
}

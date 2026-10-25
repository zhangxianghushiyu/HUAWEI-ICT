package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.OfficialCode;

/**
 * 官方二维码信息Service接口
 * 
 * @author ruoyi
 * @date 2024-11-11
 */
public interface IOfficialCodeService 
{
    /**
     * 查询官方二维码信息
     * 
     * @param id 官方二维码信息主键
     * @return 官方二维码信息
     */
    public OfficialCode selectOfficialCodeById(Long id);

    /**
     * 查询官方二维码信息列表
     * 
     * @param officialCode 官方二维码信息
     * @return 官方二维码信息集合
     */
    public List<OfficialCode> selectOfficialCodeList(OfficialCode officialCode);

    /**
     * 新增官方二维码信息
     * 
     * @param officialCode 官方二维码信息
     * @return 结果
     */
    public int insertOfficialCode(OfficialCode officialCode);

    /**
     * 修改官方二维码信息
     * 
     * @param officialCode 官方二维码信息
     * @return 结果
     */
    public int updateOfficialCode(OfficialCode officialCode);

    /**
     * 批量删除官方二维码信息
     * 
     * @param ids 需要删除的官方二维码信息主键集合
     * @return 结果
     */
    public int deleteOfficialCodeByIds(Long[] ids);

    /**
     * 删除官方二维码信息信息
     * 
     * @param id 官方二维码信息主键
     * @return 结果
     */
    public int deleteOfficialCodeById(Long id);


    /**
     * 查询二维码
     * @return
     */
    OfficialCode getOfficialCodeInfo();
}

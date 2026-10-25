package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.ApplyDestroy;
import com.ruoyi.platform.dto.ApplyDestroyListDTO;
import com.ruoyi.platform.vo.ApplyDestroyListVO;

/**
 * 申请注销Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
public interface ApplyDestroyMapper 
{
    /**
     * 查询申请注销
     * 
     * @param id 申请注销主键
     * @return 申请注销
     */
    public ApplyDestroy selectApplyDestroyById(Long id);

    /**
     * 查询申请注销列表
     * 
     * @param applyDestroy 申请注销
     * @return 申请注销集合
     */
    public List<ApplyDestroy> selectApplyDestroyList(ApplyDestroy applyDestroy);

    /**
     * 新增申请注销
     * 
     * @param applyDestroy 申请注销
     * @return 结果
     */
    public int insertApplyDestroy(ApplyDestroy applyDestroy);

    /**
     * 修改申请注销
     * 
     * @param applyDestroy 申请注销
     * @return 结果
     */
    public int updateApplyDestroy(ApplyDestroy applyDestroy);

    /**
     * 删除申请注销
     * 
     * @param id 申请注销主键
     * @return 结果
     */
    public int deleteApplyDestroyById(Long id);

    /**
     * 批量删除申请注销
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteApplyDestroyByIds(Long[] ids);


    /**
     * 申请注册列表
     * @return
     */
    List<ApplyDestroyListVO> selectApplyDestroyListVO(ApplyDestroyListDTO dto);
}

package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.DramaDetails;
import com.ruoyi.platform.dto.DramaDetailsListDTO;
import com.ruoyi.platform.vo.DramaDetailsListVO;

/**
 * 短剧详细Service接口
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
public interface IDramaDetailsService 
{
    /**
     * 查询短剧详细
     * 
     * @param id 短剧详细主键
     * @return 短剧详细
     */
    public DramaDetails selectDramaDetailsById(Long id);

    /**
     * 查询短剧详细列表
     * 
     * @param dramaDetails 短剧详细
     * @return 短剧详细集合
     */
    public List<DramaDetails> selectDramaDetailsList(DramaDetails dramaDetails);

    /**
     * 查询短剧详细列表
     *
     * @param dramaDetails 短剧详细
     * @return 短剧详细集合
     */
    public List<DramaDetailsListVO> selectDramaDetailsListVO(DramaDetailsListDTO dramaDetails);

    /**
     * 新增短剧详细
     * 
     * @param dramaDetails 短剧详细
     * @return 结果
     */
    public int insertDramaDetails(DramaDetails dramaDetails);

    /**
     * 修改短剧详细
     * 
     * @param dramaDetails 短剧详细
     * @return 结果
     */
    public int updateDramaDetails(DramaDetails dramaDetails);

    /**
     * 批量删除短剧详细
     * 
     * @param ids 需要删除的短剧详细主键集合
     * @return 结果
     */
    public int deleteDramaDetailsByIds(Long[] ids);

    /**
     * 批量下架剧情
     *
     * @param ids 需要删除的视频主键集合
     * @return 结果
     */
    public int undercarriage(Long[] ids);

    /**
     * 批量上架剧情
     *
     * @param ids 需要删除的视频主键集合
     * @return 结果
     */
    public int grounding(Long[] ids);

    /**
     * 删除短剧详细信息
     * 
     * @param id 短剧详细主键
     * @return 结果
     */
    public int deleteDramaDetailsById(Long id);


    /**
     * 查询集数
     * @param dramaId
     * @param gather
     * @return
     */
    DramaDetails selectGather(Integer dramaId,Integer gather);


    /**
     * 导入数据
     *
     * @param dataHandles        数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importDataHandle(List<DramaDetailsListDTO> dataHandles, Boolean isUpdateSupport,
                                   String operName, Long dramaId);

    /**
     * 异步解析所有未解析宽高的视频
     */
    public void refreshVideoSize();
}

package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.DramaCollect;
import com.ruoyi.platform.dto.DramaCollectListDTO;
import com.ruoyi.platform.vo.DramaCollectListVO;

/**
 * 短剧收藏Service接口
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
public interface IDramaCollectService 
{
    /**
     * 查询短剧收藏
     * 
     * @param id 短剧收藏主键
     * @return 短剧收藏
     */
    public DramaCollect selectDramaCollectById(Long id);

    /**
     * 查询短剧收藏列表
     * 
     * @param dramaCollect 短剧收藏
     * @return 短剧收藏集合
     */
    public List<DramaCollect> selectDramaCollectList(DramaCollect dramaCollect);

    /**
     * 新增短剧收藏
     * 
     * @param dramaCollect 短剧收藏
     * @return 结果
     */
    public int insertDramaCollect(DramaCollect dramaCollect);

    /**
     * 修改短剧收藏
     * 
     * @param dramaCollect 短剧收藏
     * @return 结果
     */
    public int updateDramaCollect(DramaCollect dramaCollect);

    /**
     * 批量删除短剧收藏
     * 
     * @param ids 需要删除的短剧收藏主键集合
     * @return 结果
     */
    public int deleteDramaCollectByIds(Long[] ids);

    /**
     * 删除短剧收藏信息
     * 
     * @param id 短剧收藏主键
     * @return 结果
     */
    public int deleteDramaCollectById(Long id);



    /**
     * 查询短剧收藏列表
     *
     * @param collectListDTO 短剧收藏
     * @return 短剧点赞集合
     */
    List<DramaCollectListVO> selectDramaCollectListVO(DramaCollectListDTO collectListDTO);
}

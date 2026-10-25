package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.DramaWatch;
import com.ruoyi.platform.dto.DramaWatchListDTO;
import com.ruoyi.platform.vo.DramaWatchListVO;

/**
 * 观看记录Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-11
 */
public interface DramaWatchMapper 
{
    /**
     * 查询观看记录
     * 
     * @param id 观看记录主键
     * @return 观看记录
     */
    public DramaWatch selectDramaWatchById(Long id);

    /**
     * 查询观看记录列表
     * 
     * @param dramaWatch 观看记录
     * @return 观看记录集合
     */
    public List<DramaWatch> selectDramaWatchList(DramaWatch dramaWatch);

    /**
     * 新增观看记录
     * 
     * @param dramaWatch 观看记录
     * @return 结果
     */
    public int insertDramaWatch(DramaWatch dramaWatch);

    /**
     * 修改观看记录
     * 
     * @param dramaWatch 观看记录
     * @return 结果
     */
    public int updateDramaWatch(DramaWatch dramaWatch);

    /**
     * 删除观看记录
     * 
     * @param id 观看记录主键
     * @return 结果
     */
    public int deleteDramaWatchById(Long id);

    /**
     * 批量删除观看记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDramaWatchByIds(Long[] ids);


    /**
     * 查询短剧观看列表
     *
     * @param dramaLike 短剧点赞
     * @return 短剧点赞集合
     */
    List<DramaWatchListVO> selectDramaWatchListVO(DramaWatchListDTO dramaLike);
}

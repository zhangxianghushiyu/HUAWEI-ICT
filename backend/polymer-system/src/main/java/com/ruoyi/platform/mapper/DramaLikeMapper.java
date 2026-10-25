package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.DramaLike;
import com.ruoyi.platform.dto.DramaLikeListDTO;
import com.ruoyi.platform.vo.DramaLikeListVO;

/**
 * 短剧点赞Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
public interface DramaLikeMapper 
{
    /**
     * 查询短剧点赞
     * 
     * @param id 短剧点赞主键
     * @return 短剧点赞
     */
    public DramaLike selectDramaLikeById(Long id);

    /**
     * 查询短剧点赞列表
     * 
     * @param dramaLike 短剧点赞
     * @return 短剧点赞集合
     */
    public List<DramaLike> selectDramaLikeList(DramaLike dramaLike);

    /**
     * 新增短剧点赞
     * 
     * @param dramaLike 短剧点赞
     * @return 结果
     */
    public int insertDramaLike(DramaLike dramaLike);

    /**
     * 修改短剧点赞
     * 
     * @param dramaLike 短剧点赞
     * @return 结果
     */
    public int updateDramaLike(DramaLike dramaLike);

    /**
     * 删除短剧点赞
     * 
     * @param id 短剧点赞主键
     * @return 结果
     */
    public int deleteDramaLikeById(Long id);

    /**
     * 批量删除短剧点赞
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDramaLikeByIds(Long[] ids);



    /**
     * 查询短剧点赞列表
     *
     * @param dramaLike 短剧点赞
     * @return 短剧点赞集合
     */
     List<DramaLikeListVO> selectDramaLikeListVO(DramaLikeListDTO dramaLike);
}

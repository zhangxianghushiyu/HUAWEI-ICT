package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.DramaLike;
import com.ruoyi.platform.dto.DramaLikeListDTO;
import com.ruoyi.platform.vo.DramaLikeListVO;

/**
 * 短剧点赞Service接口
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
public interface IDramaLikeService 
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
     * 批量删除短剧点赞
     * 
     * @param ids 需要删除的短剧点赞主键集合
     * @return 结果
     */
    public int deleteDramaLikeByIds(Long[] ids);

    /**
     * 删除短剧点赞信息
     * 
     * @param id 短剧点赞主键
     * @return 结果
     */
    public int deleteDramaLikeById(Long id);



    /**
     * 查询短剧点赞列表
     *
     * @param dramaLike 短剧点赞
     * @return 短剧点赞集合
     */
    List<DramaLikeListVO> selectDramaLikeListVO(DramaLikeListDTO dramaLike);
}

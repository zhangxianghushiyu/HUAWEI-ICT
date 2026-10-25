package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.dto.DramaLikeListDTO;
import com.ruoyi.platform.vo.DramaLikeListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.DramaLikeMapper;
import com.ruoyi.platform.domain.DramaLike;
import com.ruoyi.platform.service.IDramaLikeService;

/**
 * 短剧点赞Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
@Service
public class DramaLikeServiceImpl implements IDramaLikeService 
{
    @Autowired
    private DramaLikeMapper dramaLikeMapper;

    /**
     * 查询短剧点赞
     * 
     * @param id 短剧点赞主键
     * @return 短剧点赞
     */
    @Override
    public DramaLike selectDramaLikeById(Long id)
    {
        return dramaLikeMapper.selectDramaLikeById(id);
    }

    /**
     * 查询短剧点赞列表
     * 
     * @param dramaLike 短剧点赞
     * @return 短剧点赞
     */
    @Override
    public List<DramaLike> selectDramaLikeList(DramaLike dramaLike)
    {
        return dramaLikeMapper.selectDramaLikeList(dramaLike);
    }

    /**
     * 新增短剧点赞
     * 
     * @param dramaLike 短剧点赞
     * @return 结果
     */
    @Override
    public int insertDramaLike(DramaLike dramaLike)
    {
        dramaLike.setCreateTime(DateUtils.getNowDate());
        return dramaLikeMapper.insertDramaLike(dramaLike);
    }

    /**
     * 修改短剧点赞
     * 
     * @param dramaLike 短剧点赞
     * @return 结果
     */
    @Override
    public int updateDramaLike(DramaLike dramaLike)
    {
        dramaLike.setUpdateTime(DateUtils.getNowDate());
        return dramaLikeMapper.updateDramaLike(dramaLike);
    }

    /**
     * 批量删除短剧点赞
     * 
     * @param ids 需要删除的短剧点赞主键
     * @return 结果
     */
    @Override
    public int deleteDramaLikeByIds(Long[] ids)
    {
        return dramaLikeMapper.deleteDramaLikeByIds(ids);
    }

    /**
     * 删除短剧点赞信息
     * 
     * @param id 短剧点赞主键
     * @return 结果
     */
    @Override
    public int deleteDramaLikeById(Long id)
    {
        return dramaLikeMapper.deleteDramaLikeById(id);
    }

    @Override
    public List<DramaLikeListVO> selectDramaLikeListVO(DramaLikeListDTO dramaLike) {
        return dramaLikeMapper.selectDramaLikeListVO(dramaLike);
    }
}

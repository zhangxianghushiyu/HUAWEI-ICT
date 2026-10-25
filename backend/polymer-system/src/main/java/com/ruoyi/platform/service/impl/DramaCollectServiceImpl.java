package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.dto.DramaCollectListDTO;
import com.ruoyi.platform.vo.DramaCollectListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.DramaCollectMapper;
import com.ruoyi.platform.domain.DramaCollect;
import com.ruoyi.platform.service.IDramaCollectService;

/**
 * 短剧收藏Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
@Service
public class DramaCollectServiceImpl implements IDramaCollectService 
{
    @Autowired
    private DramaCollectMapper dramaCollectMapper;

    /**
     * 查询短剧收藏
     * 
     * @param id 短剧收藏主键
     * @return 短剧收藏
     */
    @Override
    public DramaCollect selectDramaCollectById(Long id)
    {
        return dramaCollectMapper.selectDramaCollectById(id);
    }

    /**
     * 查询短剧收藏列表
     * 
     * @param dramaCollect 短剧收藏
     * @return 短剧收藏
     */
    @Override
    public List<DramaCollect> selectDramaCollectList(DramaCollect dramaCollect)
    {
        return dramaCollectMapper.selectDramaCollectList(dramaCollect);
    }

    /**
     * 新增短剧收藏
     * 
     * @param dramaCollect 短剧收藏
     * @return 结果
     */
    @Override
    public int insertDramaCollect(DramaCollect dramaCollect)
    {
        dramaCollect.setCreateTime(DateUtils.getNowDate());
        return dramaCollectMapper.insertDramaCollect(dramaCollect);
    }

    /**
     * 修改短剧收藏
     * 
     * @param dramaCollect 短剧收藏
     * @return 结果
     */
    @Override
    public int updateDramaCollect(DramaCollect dramaCollect)
    {
        dramaCollect.setUpdateTime(DateUtils.getNowDate());
        return dramaCollectMapper.updateDramaCollect(dramaCollect);
    }

    /**
     * 批量删除短剧收藏
     * 
     * @param ids 需要删除的短剧收藏主键
     * @return 结果
     */
    @Override
    public int deleteDramaCollectByIds(Long[] ids)
    {
        return dramaCollectMapper.deleteDramaCollectByIds(ids);
    }

    /**
     * 删除短剧收藏信息
     * 
     * @param id 短剧收藏主键
     * @return 结果
     */
    @Override
    public int deleteDramaCollectById(Long id)
    {
        return dramaCollectMapper.deleteDramaCollectById(id);
    }

    @Override
    public List<DramaCollectListVO> selectDramaCollectListVO(DramaCollectListDTO collectListDTO) {
        return dramaCollectMapper.selectDramaCollectListVO(collectListDTO);
    }
}

package com.ruoyi.platform.service.impl;

import java.util.List;
import java.util.Objects;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.DramaDetails;
import com.ruoyi.platform.dto.DramaWatchListDTO;
import com.ruoyi.platform.mapper.DramaDetailsMapper;
import com.ruoyi.platform.vo.DramaWatchListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.DramaWatchMapper;
import com.ruoyi.platform.domain.DramaWatch;
import com.ruoyi.platform.service.IDramaWatchService;

/**
 * 观看记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-11
 */
@Service
public class DramaWatchServiceImpl implements IDramaWatchService {
    @Autowired
    private DramaWatchMapper dramaWatchMapper;
    @Autowired
    private DramaDetailsMapper dramaDetailsMapper;

    /**
     * 查询观看记录
     *
     * @param id 观看记录主键
     * @return 观看记录
     */
    @Override
    public DramaWatch selectDramaWatchById(Long id) {
        return dramaWatchMapper.selectDramaWatchById(id);
    }

    /**
     * 查询观看记录列表
     *
     * @param dramaWatch 观看记录
     * @return 观看记录
     */
    @Override
    public List<DramaWatch> selectDramaWatchList(DramaWatch dramaWatch) {
        return dramaWatchMapper.selectDramaWatchList(dramaWatch);
    }

    /**
     * 新增观看记录
     *
     * @param dramaWatch 观看记录
     * @return 结果
     */
    @Override
    public int insertDramaWatch(DramaWatch dramaWatch) {
        dramaWatch.setCreateTime(DateUtils.getNowDate());
        return dramaWatchMapper.insertDramaWatch(dramaWatch);
    }

    /**
     * 修改观看记录
     *
     * @param dramaWatch 观看记录
     * @return 结果
     */
    @Override
    public int updateDramaWatch(DramaWatch dramaWatch) {
        dramaWatch.setUpdateTime(DateUtils.getNowDate());
        return dramaWatchMapper.updateDramaWatch(dramaWatch);
    }

    /**
     * 批量删除观看记录
     *
     * @param ids 需要删除的观看记录主键
     * @return 结果
     */
    @Override
    public int deleteDramaWatchByIds(Long[] ids) {
        return dramaWatchMapper.deleteDramaWatchByIds(ids);
    }

    /**
     * 删除观看记录信息
     *
     * @param id 观看记录主键
     * @return 结果
     */
    @Override
    public int deleteDramaWatchById(Long id) {
        return dramaWatchMapper.deleteDramaWatchById(id);
    }

    @Override
    public List<DramaWatchListVO> selectDramaWatchListVO(DramaWatchListDTO dramaLike) {
        List<DramaWatchListVO> dramaWatchListVOS = dramaWatchMapper.selectDramaWatchListVO(dramaLike);
        if (Objects.nonNull(dramaWatchListVOS) && !dramaWatchListVOS.isEmpty()) {
            for (DramaWatchListVO dramaWatchListVO : dramaWatchListVOS) {
                //查询第几集
                DramaDetails dramaDetails = dramaDetailsMapper.selectDramaDetailsById(dramaWatchListVO.getDramaDetailsId());
                if (Objects.nonNull(dramaDetails)) {
                    dramaWatchListVO.setGatherStr("第" + dramaDetails.getGather() + "集");
                }
            }
        }
        return dramaWatchListVOS;
    }
}

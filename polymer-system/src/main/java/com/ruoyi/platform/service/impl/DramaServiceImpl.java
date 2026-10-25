package com.ruoyi.platform.service.impl;

import java.util.List;
import java.util.Objects;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.DramaDetails;
import com.ruoyi.platform.domain.DramaUnlock;
import com.ruoyi.platform.mapper.DramaDetailsMapper;
import com.ruoyi.platform.mapper.DramaUnlockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.DramaMapper;
import com.ruoyi.platform.domain.Drama;
import com.ruoyi.platform.service.IDramaService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 短剧信息Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-10
 */
@Service
public class DramaServiceImpl implements IDramaService {
    @Autowired
    private DramaMapper dramaMapper;
    @Autowired
    private DramaUnlockMapper dramaUnlockMapper;
    @Autowired
    private DramaDetailsMapper dramaDetailsMapper;

    /**
     * 查询短剧信息
     *
     * @param id 短剧信息主键
     * @return 短剧信息
     */
    @Override
    public Drama selectDramaById(Long id) {
        return dramaMapper.selectDramaById(id);
    }

    /**
     * 查询短剧信息列表
     *
     * @param drama 短剧信息
     * @return 短剧信息
     */
    @Override
    public List<Drama> selectDramaList(Drama drama) {
        return dramaMapper.selectDramaList(drama);
    }

    /**
     * 新增短剧信息
     *
     * @param drama 短剧信息
     * @return 结果
     */
    @Override
    public int insertDrama(Drama drama) {
        drama.setCreateTime(DateUtils.getNowDate());
        return dramaMapper.insertDrama(drama);
    }

    /**
     * 修改短剧信息
     *
     * @param drama 短剧信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDrama(Drama drama) {
        //查看之前是不是免费
        Drama drama1 = dramaMapper.selectDramaById(drama.getId());
        if (Objects.nonNull(drama1)){
            //修改时是收费
            if (drama.getIsPrice()==1){
                //设置的是收费,查看之前是不是收费
                if (drama1.getIsPrice()==0){
                    //将内部数据按照配置进行修改
                    //查询配置
                    DramaUnlock dramaUnlock = dramaUnlockMapper.selectDramaUnlockInfo();
                    dramaDetailsMapper.updateIsPriceStatus(drama.getId().intValue(),
                            dramaUnlock.getFreeNum());
                }
            }
            //修改时是免费
            if (drama.getIsPrice()==0){
                //设置的是收费,查看之前是不是收费
                if (drama1.getIsPrice()==1){
                    //将内部数据按照配置进行修改
//查询配置
                    DramaUnlock dramaUnlock = dramaUnlockMapper.selectDramaUnlockInfo();
                    dramaDetailsMapper.updateNoPriceStatus(drama.getId().intValue(),
                            dramaUnlock.getFreeNum());
                }
            }
        }
        drama.setUpdateTime(DateUtils.getNowDate());
        return dramaMapper.updateDrama(drama);
    }

    /**
     * 批量删除短剧信息
     *
     * @param ids 需要删除的短剧信息主键
     * @return 结果
     */
    @Override
    public int deleteDramaByIds(Long[] ids) {
        return dramaMapper.deleteDramaByIds(ids);
    }

    @Override
    public int undercarriage(Long[] ids) {
        return dramaMapper.undercarriage(ids);
    }

    @Override
    public int grounding(Long[] ids) {
        return dramaMapper.grounding(ids);
    }

    /**
     * 删除短剧信息信息
     *
     * @param id 短剧信息主键
     * @return 结果
     */
    @Override
    public int deleteDramaById(Long id) {
        return dramaMapper.deleteDramaById(id);
    }

    @Override
    public Integer selectCountByClassifyId(Long classifyId) {
        return dramaMapper.selectCountByClassifyId(classifyId);
    }

    @Override
    public List<Drama> getDramaInfo() {
        return dramaMapper.selectDramaInfo();
    }
}

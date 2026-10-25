package com.ruoyi.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.platform.domain.DramaUnlock;
import com.ruoyi.platform.dto.DramaDetailsListDTO;
import com.ruoyi.platform.mapper.DramaMapper;
import com.ruoyi.platform.mapper.DramaUnlockMapper;
import com.ruoyi.platform.vo.DramaDetailsListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.DramaDetailsMapper;
import com.ruoyi.platform.domain.DramaDetails;
import com.ruoyi.platform.service.IDramaDetailsService;
import com.ruoyi.platform.utils.VideoUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;

/**
 * 短剧详细Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-10
 */
@Service
public class DramaDetailsServiceImpl implements IDramaDetailsService {
    @Autowired
    private DramaDetailsMapper dramaDetailsMapper;
    @Autowired
    private DramaMapper dramaMapper;
    @Autowired
    private DramaUnlockMapper dramaUnlockMapper;
    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 查询短剧详细
     *
     * @param id 短剧详细主键
     * @return 短剧详细
     */
    @Override
    public DramaDetails selectDramaDetailsById(Long id) {
        return dramaDetailsMapper.selectDramaDetailsById(id);
    }

    /**
     * 查询短剧详细列表
     *
     * @param dramaDetails 短剧详细
     * @return 短剧详细
     */
    @Override
    public List<DramaDetails> selectDramaDetailsList(DramaDetails dramaDetails) {
        return dramaDetailsMapper.selectDramaDetailsList(dramaDetails);
    }


    @Override
    public List<DramaDetailsListVO> selectDramaDetailsListVO(DramaDetailsListDTO dramaDetails) {
        return dramaDetailsMapper.selectDramaDetailsListVO(dramaDetails);
    }


    /**
     * 新增短剧详细
     *
     * @param dramaDetails 短剧详细
     * @return 结果
     */
    @Override
    public int insertDramaDetails(DramaDetails dramaDetails) {
        dramaDetails.setCreateTime(DateUtils.getNowDate());
        int rows = dramaDetailsMapper.insertDramaDetails(dramaDetails);
        asyncParseVideoSize(dramaDetails.getId(), dramaDetails.getVideoUrl());
        return rows;
    }

    /**
     * 修改短剧详细
     *
     * @param dramaDetails 短剧详细
     * @return 结果
     */
    @Override
    public int updateDramaDetails(DramaDetails dramaDetails) {
        dramaDetails.setUpdateTime(DateUtils.getNowDate());
        int rows = dramaDetailsMapper.updateDramaDetails(dramaDetails);
        asyncParseVideoSize(dramaDetails.getId(), dramaDetails.getVideoUrl());
        return rows;
    }

    /**
     * 批量删除短剧详细
     *
     * @param ids 需要删除的短剧详细主键
     * @return 结果
     */
    @Override
    public int deleteDramaDetailsByIds(Long[] ids) {
        return dramaDetailsMapper.deleteDramaDetailsByIds(ids);
    }

    @Override
    public int undercarriage(Long[] ids) {
        return dramaDetailsMapper.undercarriage(ids);
    }

    @Override
    public int grounding(Long[] ids) {
        return dramaDetailsMapper.grounding(ids);
    }

    /**
     * 删除短剧详细信息
     *
     * @param id 短剧详细主键
     * @return 结果
     */
    @Override
    public int deleteDramaDetailsById(Long id) {
        return dramaDetailsMapper.deleteDramaDetailsById(id);
    }

    @Override
    public DramaDetails selectGather(Integer dramaId, Integer gather) {
        return dramaDetailsMapper.selectGather(dramaId, gather);
    }

    @Override
    public String importDataHandle(List<DramaDetailsListDTO> dataHandles, Boolean isUpdateSupport,
                                   String operName, Long dramaId) {
        if (StringUtils.isNull(dataHandles) || dataHandles.isEmpty()) {
            throw new ServiceException("导入数据不能为空！");
        }
        if (dataHandles.size() >= 1000) {
            throw new ServiceException("模版数据导入上限！");
        }
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        //查看设置的不免费信息配置
        DramaUnlock dramaUnlock = dramaUnlockMapper.selectDramaUnlockInfo();

        List<DramaDetails> list = new ArrayList<>();
        String now = DateUtil.now();
        for (DramaDetailsListDTO dataHandle : dataHandles) {
            DramaDetails dramaDetails = new DramaDetails();
            BeanUtils.copyProperties(dataHandle, dramaDetails);
            dramaDetails.setDramaId(dramaId);
            //生成点赞量及收藏量随机数
            dramaDetails.setLikeCount(RandomUtil.randomInt(3000, 30000));
            dramaDetails.setCollectCount(RandomUtil.randomInt(3000, 30000));
            if (dataHandle.getGather() > dramaUnlock.getFreeNum()) {
                dramaDetails.setIsPrice(1);
            } else {
                dramaDetails.setIsPrice(0);
            }
            list.add(dramaDetails);
        }
        int result = dramaDetailsMapper.batchInsert(list, now);
        if (result <= 0) {
            failureMsg.insert(0, "很抱歉，导入失败！");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + dataHandles.size() + " 条，数据如下：");
        }
        // 批量导入后异步解析视频宽高
        threadPoolTaskExecutor.execute(() -> {
            List<DramaDetails> nullList = dramaDetailsMapper.selectVideoSizeNullByDramaId(dramaId.intValue());
            if (nullList == null) return;
            for (DramaDetails dd : nullList) {
                int[] size = VideoUtil.getVideoSize(dd.getVideoUrl());
                if (size != null) {
                    dramaDetailsMapper.updateVideoSize(dd.getId(), size[0], size[1]);
                }
            }
        });
        return successMsg.toString();
    }

    /**
     * 异步解析视频宽高并写入数据库（后台录入后调用，不阻塞录入接口）
     */
    private void asyncParseVideoSize(Long id, String videoUrl) {
        if (id == null || videoUrl == null || videoUrl.isEmpty()) {
            return;
        }
        threadPoolTaskExecutor.execute(() -> {
            int[] size = VideoUtil.getVideoSize(videoUrl);
            if (size != null) {
                dramaDetailsMapper.updateVideoSize(id, size[0], size[1]);
            }
        });
    }

    @Override
    public void refreshVideoSize() {
        threadPoolTaskExecutor.execute(() -> {
            List<DramaDetails> nullList = dramaDetailsMapper.selectVideoSizeNullByDramaId(null);
            if (nullList == null) return;
            for (DramaDetails dd : nullList) {
                int[] size = VideoUtil.getVideoSize(dd.getVideoUrl());
                if (size != null) {
                    dramaDetailsMapper.updateVideoSize(dd.getId(), size[0], size[1]);
                }
            }
        });
    }
}

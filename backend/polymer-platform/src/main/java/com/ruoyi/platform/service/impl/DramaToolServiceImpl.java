package com.ruoyi.platform.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.platform.domain.Drama;
import com.ruoyi.platform.domain.DramaDetails;
import com.ruoyi.platform.domain.DramaDetailsTool;
import com.ruoyi.platform.domain.DramaTool;
import com.ruoyi.platform.mapper.DramaDao;
import com.ruoyi.platform.mapper.DramaDetailsDao;
import com.ruoyi.platform.mapper.DramaDetailsToolDao;
import com.ruoyi.platform.mapper.DramaToolDao;
import com.ruoyi.platform.service.DramaToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DramaToolServiceImpl implements DramaToolService {


    @Autowired
    private DramaToolDao dramaToolDao;
    @Autowired
    private DramaDetailsToolDao dramaDetailsToolDao;
    @Autowired
    private DramaDao dramaDao;
    @Autowired
    private DramaDetailsDao dramaDetailsDao;


    @Override
    public void dataTransfer(Integer classifyId, String dramaIdStr) {
        String[] split = dramaIdStr.split(",");
        List<DramaTool> dramaTools = dramaToolDao.selectAll(split);
        log.info("查询出的短剧数量为：{}", dramaTools.size());
        for (DramaTool dramaTool : dramaTools) {
            Date date = new Date();
            log.info("查询短剧ID：{}，短剧名称：{}，下的剧情信息", dramaTool.getId(), dramaTool.getTitle());
            List<DramaDetailsTool> dramaDetailsTools = dramaDetailsToolDao.selectDramaDetailsToolByDramaId(dramaTool.getId());
            if (!dramaDetailsTools.isEmpty()) {
                log.info("查询短剧ID：{}，短剧名称：{}，准备处理数据", dramaTool.getId(), dramaTool.getTitle());
                Drama drama = new Drama();
                BeanUtils.copyProperties(dramaTool, drama);
                drama.setClassifyId(classifyId);
                drama.setCreateTime(date);
                drama.setUpdateTime(null);
                dramaDao.insertSelective(drama);
                log.info("短剧ID：{}，短剧名称：{}，插入外部壳子数据，返回的短剧ID：{}",
                        dramaTool.getId(), dramaTool.getTitle(), drama.getId());
                List<DramaDetails> list = new ArrayList<>();
                for (DramaDetailsTool dramaDetailsTool : dramaDetailsTools) {
                    DramaDetails dramaDetails = new DramaDetails();
                    BeanUtils.copyProperties(dramaDetailsTool, dramaDetails);
                    dramaDetails.setDramaId(drama.getId());
                    dramaDetails.setCreateTime(date);
                    dramaDetails.setUpdateTime(null);
                    //从第几集开始收费
                    if (dramaDetails.getGather() > 3) {
                        dramaDetails.setIsPrice(1);
                    } else {
                        dramaDetails.setIsPrice(0);
                    }
                    list.add(dramaDetails);
//                    dramaDetailsDao.insertSelective(dramaDetails);
//                    log.info("短剧ID：{}，短剧名称：{}，插入单集数据，返回的单集ID：{}",
//                            dramaTool.getId(), dramaTool.getTitle(), dramaDetails.getId());
                }
                dramaDetailsDao.batchInsertSelective(list);
                log.info("短剧ID：{}，短剧名称：{}，批量插入单集数据成功。",
                        dramaTool.getId(), dramaTool.getTitle());
            } else {
                log.info("短剧ID：{}，短剧名称：{}，没有查询到剧情信息", dramaTool.getId(), dramaTool.getTitle());
            }
        }
    }


    @Override
    public void modifyUrl(String oldUrl, String newUrl) {
        List<Drama> dramas = dramaDao.selectDramaAll();
        if (!dramas.isEmpty()) {
            log.info("查询到的短剧主信息个数为：{}", dramas.size());
            for (Drama drama : dramas) {
                log.info(drama.toString());
                if (!StrUtil.isEmpty(drama.getTitleImg())) {
                    String url = drama.getTitleImg();
                    String newStr = url.replace(oldUrl, newUrl);
                    drama.setTitleImg(newStr);
                    dramaDao.updateByPrimaryKeySelective(drama);
                }
            }
        }
        List<DramaDetails> dramaDetails = dramaDetailsDao.selectDramaDetailsAll();
        if (!dramaDetails.isEmpty()) {
            log.info("查询到的短剧详细信息个数为：{}", dramaDetails.size());
            for (DramaDetails dramaDetail : dramaDetails) {
                log.info(dramaDetail.toString());
                if (!StrUtil.isEmpty(dramaDetail.getTitleImg())) {
                    String url1 = dramaDetail.getTitleImg();
                    String newStr1 = url1.replace(oldUrl, newUrl);
                    dramaDetail.setTitleImg(newStr1);
                }
                if (!StrUtil.isEmpty(dramaDetail.getVideoUrl())) {
                    String url2 = dramaDetail.getVideoUrl();
                    String newStr2 = url2.replace(oldUrl, newUrl);
                    dramaDetail.setVideoUrl(newStr2);
                }
                dramaDetailsDao.updateByPrimaryKeySelective(dramaDetail);
            }
        }
    }
}

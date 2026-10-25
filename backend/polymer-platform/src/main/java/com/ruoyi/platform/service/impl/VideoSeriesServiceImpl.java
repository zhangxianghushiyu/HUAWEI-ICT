package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.domain.Video;
import com.ruoyi.platform.domain.VideoPublicize;
import com.ruoyi.platform.domain.VideoSeriesUnlock;
import com.ruoyi.platform.enums.BillSceneTypeEnum;
import com.ruoyi.platform.enums.DelStatusEnum;
import com.ruoyi.platform.enums.UpStatusEnum;
import com.ruoyi.platform.mapper.UserDao;
import com.ruoyi.platform.mapper.VideoCategoryDao;
import com.ruoyi.platform.mapper.VideoDao;
import com.ruoyi.platform.mapper.VideoPublicizeDao;
import com.ruoyi.platform.mapper.VideoSeriesUnlockDao;
import com.ruoyi.platform.service.BillGoldService;
import com.ruoyi.platform.service.UserService;
import com.ruoyi.platform.service.VideoSeriesService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.VideoCategoryVO;
import com.ruoyi.platform.vo.VideoSeriesEpisodeVO;
import com.ruoyi.platform.vo.VideoSeriesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class VideoSeriesServiceImpl implements VideoSeriesService {

    @Autowired
    private VideoDao videoDao;
    @Autowired
    private VideoCategoryDao videoCategoryDao;
    @Autowired
    private VideoPublicizeDao videoPublicizeDao;
    @Autowired
    private VideoSeriesUnlockDao videoSeriesUnlockDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private BillGoldService billGoldService;
    @Autowired
    private HttpServletRequest request;

    @Override
    public List<VideoCategoryVO> getCategoryList() {
        return videoCategoryDao.selectVideoCategoryVO();
    }

    @Override
    public List<VideoSeriesVO> getSeriesList(Integer categoryId) {
        return videoDao.selectSeriesList(categoryId);
    }

    @Override
    public List<VideoSeriesVO> searchSeries(String name) {
        return videoDao.selectSeriesListByName(name);
    }

    @Override
    public List<VideoSeriesEpisodeVO> getEpisodeList(String seriesName) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        VideoSeriesUnlock unlockConfig = videoSeriesUnlockDao.selectVideoSeriesUnlock();
        int freeNum = unlockConfig != null && unlockConfig.getFreeNum() != null ? unlockConfig.getFreeNum() : 30;
        List<VideoSeriesEpisodeVO> episodes = videoDao.selectEpisodeList(seriesName);
        if (episodes != null) {
            for (int i = 0; i < episodes.size(); i++) {
                VideoSeriesEpisodeVO vo = episodes.get(i);
                vo.setUnlockGoldCost(unlockConfig != null && unlockConfig.getMoney() != null
                        ? unlockConfig.getMoney().intValue() : 0);
                // 前 freeNum 集免费，其余集看是否已解锁
                if (i < freeNum) {
                    vo.setIsFree(1);
                } else {
                    VideoPublicize unlock = videoPublicizeDao.selectVideoPublicizeByUserIdAndVideoId(requestUserId, vo.getId());
                    if (Objects.nonNull(unlock)) {
                        vo.setIsUnlocked(1);
                    } else {
                        vo.setVideoUrl("");
                        vo.setContent("");
                        vo.setIsLocked(1);
                    }
                }
            }
        }
        return episodes;
    }

    @Override
    public List<VideoSeriesEpisodeVO> getRandomFeed() {
        return videoDao.selectRandomFeed();
    }

    @Override
    @Transactional
    public void moneyUnlock(Integer videoId) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        User user = userDao.selectByPrimaryKey(requestUserId);
        userService.checkUser(user);
        VideoSeriesUnlock unlockConfig = videoSeriesUnlockDao.selectVideoSeriesUnlock();
        if (user.getGoldCoin().compareTo(unlockConfig.getMoney()) < 0) {
            throw new ApiException("需要：" + unlockConfig.getMoney() + " 金币才可进行解锁！");
        }
        Video video = videoDao.selectByPrimaryKey(videoId);
        if (Objects.isNull(video) || video.getDelStatus().equals(DelStatusEnum.DELETE.getCode()) ||
                video.getStatus().equals(UpStatusEnum.STATUS_0.getCode())) {
            throw new ApiException("视频信息不存在");
        }
        // 已解锁则直接返回
        if (Objects.nonNull(videoPublicizeDao.selectVideoPublicizeByUserIdAndVideoId(requestUserId, videoId))) {
            return;
        }
        Date date = new Date();
        userDao.subGoldCoin(requestUserId, unlockConfig.getMoney());
        VideoPublicize publicize = new VideoPublicize();
        publicize.setUserId(requestUserId);
        publicize.setVideoId(videoId);
        publicize.setUnlock(1);
        publicize.setCreateTime(date);
        videoPublicizeDao.insertSelective(publicize);
        billGoldService.addBillGold(requestUserId, BillSceneTypeEnum.SCENE_2.getCode(),
                "解锁视频：" + video.getName(), unlockConfig.getMoney(), date);
    }
}

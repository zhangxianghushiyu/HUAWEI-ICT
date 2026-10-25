package com.ruoyi.platform.service.impl;

import com.ruoyi.common.enums.DelStatusEnum;
import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.VoteActivity;
import com.ruoyi.platform.domain.VoteOption;
import com.ruoyi.platform.domain.VoteRecord;
import com.ruoyi.platform.mapper.VoteActivityDao;
import com.ruoyi.platform.mapper.VoteOptionDao;
import com.ruoyi.platform.mapper.VoteRecordDao;
import com.ruoyi.platform.service.VoteService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.VoteActivityDetailVO;
import com.ruoyi.platform.vo.VoteActivityListVO;
import com.ruoyi.platform.vo.VoteOptionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteActivityDao voteActivityDao;

    @Autowired
    private VoteOptionDao voteOptionDao;

    @Autowired
    private VoteRecordDao voteRecordDao;

    @Autowired
    private HttpServletRequest request;

    @Override
    public List<VoteActivityListVO> getVoteActivityList() {
        Integer userId = JwtUtil.getRequestUserId(request);
        List<VoteActivity> activities = voteActivityDao.selectActiveList(userId);

        List<VoteActivityListVO> result = new ArrayList<>();
        for (VoteActivity activity : activities) {
            VoteActivityListVO vo = new VoteActivityListVO();
            BeanUtils.copyProperties(activity, vo);

            // 查询用户是否已投票
            VoteRecord record = voteRecordDao.selectByUserIdAndActivityId(userId, activity.getId());
            vo.setHasVoted(record != null);

            result.add(vo);
        }

        return result;
    }

    @Override
    public VoteActivityDetailVO getVoteActivityDetail(Integer activityId) {
        Integer userId = JwtUtil.getRequestUserId(request);

        VoteActivity activity = voteActivityDao.selectDetailById(activityId, userId);
        if (activity == null || DelStatusEnum.STATUS_0.getStatus().equals(activity.getDelStatus())) {
            throw new ApiException("投票活动不存在");
        }

        VoteActivityDetailVO detailVO = new VoteActivityDetailVO();
        BeanUtils.copyProperties(activity, detailVO);

        // 查询选项列表
        List<VoteOption> options = voteOptionDao.selectByActivityId(activityId);
        List<VoteOptionVO> optionVOs = new ArrayList<>();

        int totalVotes = activity.getTotalVotes() != null ? activity.getTotalVotes() : 0;

        for (VoteOption option : options) {
            VoteOptionVO optionVO = new VoteOptionVO();
            BeanUtils.copyProperties(option, optionVO);

            // 计算百分比
            if (totalVotes > 0) {
                BigDecimal percentage = BigDecimal.valueOf(option.getVoteCount())
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(totalVotes), 2, RoundingMode.HALF_UP);
                optionVO.setPercentage(percentage);
            } else {
                optionVO.setPercentage(BigDecimal.ZERO);
            }

            optionVO.setUserSelected(false);
            optionVOs.add(optionVO);
        }

        detailVO.setOptions(optionVOs);

        // 查询用户投票记录
        List<VoteRecord> records = voteRecordDao.selectListByUserIdAndActivityId(userId, activityId);
        if (records != null && !records.isEmpty()) {
            detailVO.setHasVoted(true);
            List<Integer> votedOptionIds = records.stream()
                    .map(VoteRecord::getOptionId)
                    .collect(Collectors.toList());
            detailVO.setUserVotedOptionIds(votedOptionIds);

            // 标记用户已选的选项
            for (VoteOptionVO optionVO : optionVOs) {
                if (votedOptionIds.contains(optionVO.getId())) {
                    optionVO.setUserSelected(true);
                }
            }
        } else {
            detailVO.setHasVoted(false);
            detailVO.setUserVotedOptionIds(new ArrayList<>());
        }

        return detailVO;
    }

    @Override
    @Transactional
    public void vote(Integer activityId, Integer optionId) {
        Integer userId = JwtUtil.getRequestUserId(request);

        // 验证活动
        VoteActivity activity = voteActivityDao.selectByPrimaryKey(activityId);
        if (activity == null || DelStatusEnum.STATUS_0.getStatus().equals(activity.getDelStatus())) {
            throw new ApiException("投票活动不存在");
        }

        // 验证活动状态
        if (activity.getStatus() != 1) {
            throw new ApiException("投票活动未开始或已结束");
        }

        // 验证时间
        Date now = new Date();
        if (activity.getStartTime() != null && now.before(activity.getStartTime())) {
            throw new ApiException("投票活动尚未开始");
        }
        if (activity.getEndTime() != null && now.after(activity.getEndTime())) {
            throw new ApiException("投票活动已结束");
        }

        // 验证选项
        VoteOption option = voteOptionDao.selectByPrimaryKey(optionId);
        if (option == null || !option.getActivityId().equals(activityId)) {
            throw new ApiException("投票选项不存在");
        }

        // 检查是否已投票
        VoteRecord existRecord = voteRecordDao.selectByUserIdAndActivityId(userId, activityId);
        if (existRecord != null) {
            throw new ApiException("您已经投过票了");
        }

        // 创建投票记录
        VoteRecord record = new VoteRecord();
        record.setUserId(userId);
        record.setActivityId(activityId);
        record.setOptionId(optionId);
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        voteRecordDao.insertSelective(record);

        // 更新选项票数
        voteOptionDao.addVoteCount(optionId);

        // 更新活动统计
        voteActivityDao.addTotalVoters(activityId);
        voteActivityDao.addTotalVotes(activityId, 1);
    }

    @Override
    @Transactional
    public void voteMultiple(Integer activityId, List<Integer> optionIds) {
        Integer userId = JwtUtil.getRequestUserId(request);

        if (optionIds == null || optionIds.isEmpty()) {
            throw new ApiException("请选择投票选项");
        }

        // 验证活动
        VoteActivity activity = voteActivityDao.selectByPrimaryKey(activityId);
        if (activity == null || DelStatusEnum.STATUS_0.getStatus().equals(activity.getDelStatus())) {
            throw new ApiException("投票活动不存在");
        }

        // 验证是否多选
        if (activity.getIsMultiple() != 1) {
            throw new ApiException("该投票活动不支持多选");
        }

        // 验证最大可选数
        if (activity.getMaxOptions() != null && optionIds.size() > activity.getMaxOptions()) {
            throw new ApiException("最多只能选择" + activity.getMaxOptions() + "个选项");
        }

        // 验证活动状态
        if (activity.getStatus() != 1) {
            throw new ApiException("投票活动未开始或已结束");
        }

        // 验证时间
        Date now = new Date();
        if (activity.getStartTime() != null && now.before(activity.getStartTime())) {
            throw new ApiException("投票活动尚未开始");
        }
        if (activity.getEndTime() != null && now.after(activity.getEndTime())) {
            throw new ApiException("投票活动已结束");
        }

        // 检查是否已投票
        List<VoteRecord> existRecords = voteRecordDao.selectListByUserIdAndActivityId(userId, activityId);
        if (existRecords != null && !existRecords.isEmpty()) {
            throw new ApiException("您已经投过票了");
        }

        // 验证所有选项
        for (Integer optionId : optionIds) {
            VoteOption option = voteOptionDao.selectByPrimaryKey(optionId);
            if (option == null || !option.getActivityId().equals(activityId)) {
                throw new ApiException("投票选项【" + optionId + "】不存在");
            }
        }

        // 批量创建投票记录
        Date createTime = new Date();
        for (Integer optionId : optionIds) {
            VoteRecord record = new VoteRecord();
            record.setUserId(userId);
            record.setActivityId(activityId);
            record.setOptionId(optionId);
            record.setCreateTime(createTime);
            record.setUpdateTime(createTime);
            voteRecordDao.insertSelective(record);
        }

        // 批量更新选项票数
        voteOptionDao.batchAddVoteCount(optionIds);

        // 更新活动统计
        voteActivityDao.addTotalVoters(activityId);
        voteActivityDao.addTotalVotes(activityId, optionIds.size());
    }

    @Override
    @Transactional
    public void cancelVote(Integer activityId) {
        Integer userId = JwtUtil.getRequestUserId(request);

        // 查询投票记录
        VoteRecord record = voteRecordDao.selectByUserIdAndActivityId(userId, activityId);
        if (record == null) {
            throw new ApiException("您还没有投过票");
        }

        // 删除投票记录
        voteRecordDao.deleteByUserIdAndActivityId(userId, activityId);

        // 减少选项票数
        voteOptionDao.subVoteCount(record.getOptionId());

        // 更新活动统计
        voteActivityDao.subTotalVotes(activityId, 1);
    }

    @Override
    @Transactional
    public void cancelVoteMultiple(Integer activityId) {
        Integer userId = JwtUtil.getRequestUserId(request);

        // 查询投票记录
        List<VoteRecord> records = voteRecordDao.selectListByUserIdAndActivityId(userId, activityId);
        if (records == null || records.isEmpty()) {
            throw new ApiException("您还没有投过票");
        }

        // 获取所有选项ID
        List<Integer> optionIds = records.stream()
                .map(VoteRecord::getOptionId)
                .collect(Collectors.toList());

        // 删除投票记录
        voteRecordDao.deleteByUserIdAndActivityId(userId, activityId);

        // 批量减少选项票数
        voteOptionDao.batchSubVoteCount(optionIds);

        // 更新活动统计
        voteActivityDao.subTotalVotes(activityId, optionIds.size());
    }
}

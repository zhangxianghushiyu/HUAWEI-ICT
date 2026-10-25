package com.ruoyi.platform.controller.platform;

import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.dto.CancelVoteDTO;
import com.ruoyi.platform.dto.VoteDTO;
import com.ruoyi.platform.dto.VoteMultipleDTO;
import com.ruoyi.platform.service.VoteService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.VoteActivityDetailVO;
import com.ruoyi.platform.vo.VoteActivityListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "投票API")
@RestController
@Slf4j
@RequestMapping("/platform/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @ApiOperation(value = "投票活动列表")
    @GetMapping("/list")
    public R<List<VoteActivityListVO>> getVoteActivityList() {
        List<VoteActivityListVO> list = voteService.getVoteActivityList();
        return R.ok(list);
    }

    @ApiOperation(value = "投票活动详情")
    @ApiImplicitParam(name = "activityId", value = "活动ID", required = true)
    @GetMapping("/detail")
    public R<VoteActivityDetailVO> getVoteActivityDetail(@RequestParam(value = "activityId") Integer activityId) {
        VoteActivityDetailVO detail = voteService.getVoteActivityDetail(activityId);
        return R.ok(detail);
    }

    @ApiOperation(value = "投票（单选）")
    @ApiImplicitParam(name = "dto", value = "单选投票参数", required = true)
    @PostMapping("/vote")
    @RepeatSubmit(interval = 1000)
    public R<Void> vote(@RequestBody VoteDTO dto) {
        voteService.vote(dto.getActivityId(), dto.getOptionId());
        return R.ok();
    }

    @ApiOperation(value = "投票（多选）")
    @ApiImplicitParam(name = "dto", value = "多选投票参数", required = true)
    @PostMapping("/voteMultiple")
    @RepeatSubmit(interval = 1000)
    public R<Void> voteMultiple(@RequestBody VoteMultipleDTO dto) {
        voteService.voteMultiple(dto.getActivityId(), dto.getOptionIds());
        return R.ok();
    }

    @ApiOperation(value = "取消投票（单选）")
    @ApiImplicitParam(name = "dto", value = "取消投票参数", required = true)
    @PostMapping("/cancelVote")
    @RepeatSubmit(interval = 1000)
    public R<Void> cancelVote(@RequestBody CancelVoteDTO dto) {
        voteService.cancelVote(dto.getActivityId());
        return R.ok();
    }

    @ApiOperation(value = "取消投票（多选）")
    @ApiImplicitParam(name = "dto", value = "取消投票参数", required = true)
    @PostMapping("/cancelVoteMultiple")
    @RepeatSubmit(interval = 1000)
    public R<Void> cancelVoteMultiple(@RequestBody CancelVoteDTO dto) {
        voteService.cancelVoteMultiple(dto.getActivityId());
        return R.ok();
    }
}

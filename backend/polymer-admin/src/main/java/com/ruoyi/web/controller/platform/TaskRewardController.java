package com.ruoyi.web.controller.platform;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.TaskFollowTypeEnum;
import com.ruoyi.common.enums.UserStatusEnum;
import com.ruoyi.platform.domain.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.platform.domain.TaskReward;
import com.ruoyi.platform.service.ITaskRewardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 任务Controller
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@RestController
@RequestMapping("/platform/taskReward")
public class TaskRewardController extends BaseController {
    @Autowired
    private ITaskRewardService taskRewardService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询任务列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskReward:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskReward taskReward) {
        startPage();
        List<TaskReward> list = taskRewardService.selectTaskRewardList(taskReward);
        return getDataTable(list);
    }

    /**
     * 导出任务列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskReward:export')")
    @Log(title = "任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskReward taskReward) {
        List<TaskReward> list = taskRewardService.selectTaskRewardList(taskReward);
        ExcelUtil<TaskReward> util = new ExcelUtil<TaskReward>(TaskReward.class);
        util.exportExcel(response, list, "任务数据");
    }

    /**
     * 获取任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskReward:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(taskRewardService.selectTaskRewardById(id));
    }

    /**
     * 新增任务
     */
    @PreAuthorize("@ss.hasPermi('platform:taskReward:add')")
    @Log(title = "任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskReward taskReward) {
        return toAjax(taskRewardService.insertTaskReward(taskReward));
    }

    /**
     * 修改任务
     */
    @PreAuthorize("@ss.hasPermi('platform:taskReward:edit')")
    @Log(title = "任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskReward taskReward) {
        if (!taskReward.getFollowType().equals(TaskFollowTypeEnum.TYPE_2.getType()) ||
                !taskReward.getFollowType().equals(TaskFollowTypeEnum.TYPE_3.getType())) {
            if (Objects.isNull(taskReward.getPrice()) || taskReward.getPrice().compareTo(new BigDecimal(0)) <= 0) {
                return AjaxResult.error("金额必须大于0");
            }
        }
        return toAjax(taskRewardService.updateTaskReward(taskReward));
    }

    /**
     * 删除任务
     */
    @PreAuthorize("@ss.hasPermi('platform:taskReward:remove')")
    @Log(title = "任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(taskRewardService.deleteTaskRewardByIds(ids));
    }

    /**
     * 修改任务状态（针对关闭开启操作）
     */
    @GetMapping("/modifyTaskStatusDeactivate")
    public AjaxResult modifyTaskStatusDeactivate(Long id) {
        TaskReward taskReward = taskRewardService.selectTaskRewardById(id);
        if (Objects.nonNull(taskReward)) {
            taskReward.setStatus(taskReward.getStatus() == 0 ? 1 : 0);
            taskRewardService.updateTaskReward(taskReward);
            //清除redis
            redisCache.deleteObject(Constants.TASK_STATUS + taskReward.getFollowType());
        }
        return AjaxResult.success();
    }
}

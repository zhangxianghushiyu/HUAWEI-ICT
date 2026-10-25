package com.ruoyi.web.controller.platform;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.platform.dto.TaskLinkDramaListDTO;
import com.ruoyi.platform.vo.TaskLinkDramaListVO;
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
import com.ruoyi.platform.domain.TaskDailyAppointDramaCoin;
import com.ruoyi.platform.service.ITaskDailyAppointDramaCoinService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 看指定剧领金币配置Controller
 *
 * @author ruoyi
 * @date 2024-11-25
 */
@RestController
@RequestMapping("/platform/taskDailyAppointDramaCoin")
public class TaskDailyAppointDramaCoinController extends BaseController
{
    @Autowired
    private ITaskDailyAppointDramaCoinService taskDailyAppointDramaCoinService;

    /**
     * 查询看指定剧领金币配置列表
     */
//    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoin:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(TaskDailyAppointDramaCoin taskDailyAppointDramaCoin)
//    {
//        startPage();
//        List<TaskDailyAppointDramaCoin> list = taskDailyAppointDramaCoinService.selectTaskDailyAppointDramaCoinList(taskDailyAppointDramaCoin);
//        return getDataTable(list);
//    }
    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoin:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskLinkDramaListDTO taskLinkDramaListDTO)
    {
        startPage();
        List<TaskLinkDramaListVO> list = taskDailyAppointDramaCoinService.selectTaskLinkDramaCoinList(taskLinkDramaListDTO);
        return getDataTable(list);
    }


    /**
     * 导出看指定剧领金币配置列表
     */
//    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoin:export')")
//    @Log(title = "看指定剧领金币配置", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, TaskDailyAppointDramaCoin taskDailyAppointDramaCoin)
//    {
//        List<TaskDailyAppointDramaCoin> list = taskDailyAppointDramaCoinService.selectTaskDailyAppointDramaCoinList(taskDailyAppointDramaCoin);
//        ExcelUtil<TaskDailyAppointDramaCoin> util = new ExcelUtil<TaskDailyAppointDramaCoin>(TaskDailyAppointDramaCoin.class);
//        util.exportExcel(response, list, "看指定剧领金币配置数据");
//    }
    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoin:export')")
    @Log(title = "看指定剧领金币配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskLinkDramaListDTO taskLinkDramaListDTO)
    {
        List<TaskLinkDramaListVO> list = taskDailyAppointDramaCoinService.selectTaskLinkDramaCoinList(taskLinkDramaListDTO);
        ExcelUtil<TaskLinkDramaListVO> util = new ExcelUtil<TaskLinkDramaListVO>(TaskLinkDramaListVO.class);
        util.exportExcel(response, list, "看指定剧领金币配置数据");
    }

    /**
     * 获取看指定剧领金币配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(taskDailyAppointDramaCoinService.selectTaskDailyAppointDramaCoinById(id));
    }

    /**
     * 新增看指定剧领金币配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoin:add')")
    @Log(title = "看指定剧领金币配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskDailyAppointDramaCoin taskDailyAppointDramaCoin)
    {
        TaskLinkDramaListDTO taskLinkDramaListDTO=new TaskLinkDramaListDTO();
        taskLinkDramaListDTO.setDramaId(taskDailyAppointDramaCoin.getDramaId());
        List<TaskLinkDramaListVO> taskLinkDramaListVOS = taskDailyAppointDramaCoinService.
                selectTaskLinkDramaCoinList(taskLinkDramaListDTO);
        if (Objects.nonNull(taskLinkDramaListVOS)&&!taskLinkDramaListVOS.isEmpty()){
            return AjaxResult.error("已存在此剧，无法重复添加");
        }
        //查看目前已经设置的总数量
        List<TaskLinkDramaListVO> taskLinkDramaListVOS1 = taskDailyAppointDramaCoinService.
                selectTaskLinkDramaCoinList(new TaskLinkDramaListDTO());
        if (Objects.nonNull(taskLinkDramaListVOS1)&&taskLinkDramaListVOS1.size()>=3){
            return AjaxResult.error("短剧数量已经上限");
        }
        return toAjax(taskDailyAppointDramaCoinService.insertTaskDailyAppointDramaCoin(taskDailyAppointDramaCoin));
    }

    /**
     * 修改看指定剧领金币配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoin:edit')")
    @Log(title = "看指定剧领金币配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskDailyAppointDramaCoin taskDailyAppointDramaCoin)
    {
        return toAjax(taskDailyAppointDramaCoinService.updateTaskDailyAppointDramaCoin(taskDailyAppointDramaCoin));
    }

    /**
     * 删除看指定剧领金币配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoin:remove')")
    @Log(title = "看指定剧领金币配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taskDailyAppointDramaCoinService.deleteTaskDailyAppointDramaCoinByIds(ids));
    }

    /**
     * 查询短剧
     */
    @PreAuthorize("@ss.hasPermi('platform:taskLinkDramaListVO:query')")
    @GetMapping("/selectDrama")
    public AjaxResult selectDrama(@RequestBody TaskLinkDramaListVO taskLinkDramaListVO){
        return toAjax(taskDailyAppointDramaCoinService.selectTaskLinkDramaIdList(taskLinkDramaListVO));
    }

}

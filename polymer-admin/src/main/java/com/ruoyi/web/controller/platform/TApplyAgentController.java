package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.TApplyAgent;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.service.ITApplyAgentService;
import com.ruoyi.platform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 申请代理Controller
 *
 * @author ruoyi
 * @date 2025-07-02
 */
@RestController
@RequestMapping("/platform/agent")
public class TApplyAgentController extends BaseController {
    @Autowired
    private ITApplyAgentService tApplyAgentService;

    @Autowired
    private IUserService userService;

    /**
     * 查询申请代理列表
     */
    @PreAuthorize("@ss.hasPermi('platform:agent:list')")
    @GetMapping("/list")
    public TableDataInfo list(TApplyAgent tApplyAgent) {
        startPage();
        List<TApplyAgent> list = tApplyAgentService.selectTApplyAgentList(tApplyAgent);
        for (TApplyAgent applyAgent : list) {
            applyAgent.setUser(userService.selectUserById(applyAgent.getUserId()));
        }
        return getDataTable(list);
    }

    /**
     * 导出申请代理列表
     */
    @PreAuthorize("@ss.hasPermi('platform:agent:export')")
    @Log(title = "申请代理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TApplyAgent tApplyAgent) {
        List<TApplyAgent> list = tApplyAgentService.selectTApplyAgentList(tApplyAgent);
        ExcelUtil<TApplyAgent> util = new ExcelUtil<TApplyAgent>(TApplyAgent.class);
        util.exportExcel(response, list, "申请代理数据");
    }

    /**
     * 获取申请代理详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:agent:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        TApplyAgent tApplyAgent = tApplyAgentService.selectTApplyAgentById(id);
        tApplyAgent.setUser(userService.selectUserById(tApplyAgent.getUserId()));
        return AjaxResult.success(tApplyAgent);
    }

    /**
     * 修改申请代理
     */
    @PreAuthorize("@ss.hasPermi('platform:agent:edit')")
    @Log(title = "申请代理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TApplyAgent tApplyAgent) {
        //同意申请
        if (tApplyAgent.getStatus() == 2) {
            //获取用户信息
            User user = userService.selectUserById(tApplyAgent.getUserId());
            if (Objects.nonNull(user)) {
                user.setParentId(0L);
                user.setRoleId(1);
                user.setCommander(1);
                userService.updateUser(user);
            }
        }
        return toAjax(tApplyAgentService.updateTApplyAgent(tApplyAgent));
    }

    /**
     * 删除申请代理
     */
    @PreAuthorize("@ss.hasPermi('platform:agent:remove')")
    @Log(title = "申请代理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tApplyAgentService.deleteTApplyAgentByIds(ids));
    }
}

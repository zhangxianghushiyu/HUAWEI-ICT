package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.UserGrade;
import com.ruoyi.platform.service.IUserGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户阶级记录Controller
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
@RestController
@RequestMapping("/platform/userGrade")
public class UserGradeController extends BaseController
{
    @Autowired
    private IUserGradeService userGradeService;

    /**
     * 查询用户阶级记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:userGrade:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserGrade userGrade)
    {
        startPage();
        List<UserGrade> list = userGradeService.selectUserGradeList(userGrade);
        return getDataTable(list);
    }

    /**
     * 导出用户阶级记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:userGrade:export')")
    @Log(title = "用户阶级记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserGrade userGrade)
    {
        List<UserGrade> list = userGradeService.selectUserGradeList(userGrade);
        ExcelUtil<UserGrade> util = new ExcelUtil<UserGrade>(UserGrade.class);
        util.exportExcel(response, list, "用户阶级记录数据");
    }

    /**
     * 获取用户阶级记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:userGrade:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userGradeService.selectUserGradeById(id));
    }

    /**
     * 新增用户阶级记录
     */
    @PreAuthorize("@ss.hasPermi('platform:userGrade:add')")
    @Log(title = "用户阶级记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserGrade userGrade)
    {
        return toAjax(userGradeService.insertUserGrade(userGrade));
    }

    /**
     * 修改用户阶级记录
     */
    @PreAuthorize("@ss.hasPermi('platform:userGrade:edit')")
    @Log(title = "用户阶级记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserGrade userGrade)
    {
        return toAjax(userGradeService.updateUserGrade(userGrade));
    }

    /**
     * 删除用户阶级记录
     */
    @PreAuthorize("@ss.hasPermi('platform:userGrade:remove')")
    @Log(title = "用户阶级记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userGradeService.deleteUserGradeByIds(ids));
    }
}

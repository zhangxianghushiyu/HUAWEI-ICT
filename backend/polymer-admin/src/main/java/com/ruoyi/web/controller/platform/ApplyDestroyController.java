package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.UserStatusEnum;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.ApplyDestroy;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.dto.ApplyDestroyListDTO;
import com.ruoyi.platform.service.IApplyDestroyService;
import com.ruoyi.platform.service.IUserService;
import com.ruoyi.platform.vo.ApplyDestroyListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 申请注销Controller
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
@RestController
@RequestMapping("/platform/applyDestroy")
public class ApplyDestroyController extends BaseController
{
    @Autowired
    private IApplyDestroyService applyDestroyService;
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询申请注销列表
     */
    @PreAuthorize("@ss.hasPermi('platform:applyDestroy:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApplyDestroyListDTO applyDestroy)
    {
        startPage();
        List<ApplyDestroyListVO> list = applyDestroyService.selectApplyDestroyListVO(applyDestroy);
        return getDataTable(list);
    }

    /**
     * 导出申请注销列表
     */
    @PreAuthorize("@ss.hasPermi('platform:applyDestroy:export')")
    @Log(title = "申请注销", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ApplyDestroy applyDestroy)
    {
        List<ApplyDestroy> list = applyDestroyService.selectApplyDestroyList(applyDestroy);
        ExcelUtil<ApplyDestroy> util = new ExcelUtil<ApplyDestroy>(ApplyDestroy.class);
        util.exportExcel(response, list, "申请注销数据");
    }

    /**
     * 获取申请注销详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:applyDestroy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(applyDestroyService.selectApplyDestroyById(id));
    }

    /**
     * 新增申请注销
     */
    @PreAuthorize("@ss.hasPermi('platform:applyDestroy:add')")
    @Log(title = "申请注销", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ApplyDestroy applyDestroy)
    {
        return toAjax(applyDestroyService.insertApplyDestroy(applyDestroy));
    }

    /**
     * 修改申请注销
     */
    @PreAuthorize("@ss.hasPermi('platform:applyDestroy:edit')")
    @Log(title = "申请注销", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ApplyDestroy applyDestroy)
    {
        return toAjax(applyDestroyService.updateApplyDestroy(applyDestroy));
    }

    /**
     * 删除申请注销
     */
    @PreAuthorize("@ss.hasPermi('platform:applyDestroy:remove')")
    @Log(title = "申请注销", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(applyDestroyService.deleteApplyDestroyByIds(ids));
    }


    /**
     * 修改用户状态（针对注销操作）
     */
    @GetMapping("/modifyUserStatusDestroy")
    @Transactional
    public AjaxResult modifyUserStatusDestroy(Long id,Integer status) {
        ApplyDestroy applyDestroy = applyDestroyService.selectApplyDestroyById(id);
        if (status==1){
            //拒绝
            applyDestroy.setStatus(1);
            applyDestroyService.updateApplyDestroy(applyDestroy);
        }else if (status==2){
            //同意
            User user = userService.selectUserById(applyDestroy.getUserId());
            user.setStatus(UserStatusEnum.STATUS_2.getStatus());
            user.setDestroyTime(new Date());
            //删除redis中的值
            redisCache.deleteObject(Constants.USER_LOGIN_TOKEN + user.getId());
            applyDestroy.setStatus(2);
            applyDestroyService.updateApplyDestroy(applyDestroy);
            userService.updateUser(user);
        }
//        else if (status==3){
//            //恢复
//            User user = userService.selectUserById(applyDestroy.getUserId());
//            userService.modifyRestore(user.getId());
//        }
        return AjaxResult.success();
    }
}

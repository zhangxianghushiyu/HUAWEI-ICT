package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.BillGold;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.dto.BillGoldListDTO;
import com.ruoyi.platform.service.IBillGoldService;
import com.ruoyi.platform.vo.BillGoldListVO;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 金币明细Controller
 *
 * @author ruoyi
 * @date 2024-11-15
 */
@RestController
@RequestMapping("/platform/billGold")
public class BillGoldController extends BaseController {
    @Autowired
    private IBillGoldService billGoldService;
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询金币明细列表
     */
    @PreAuthorize("@ss.hasPermi('platform:billGold:list')")
    @GetMapping("/list")
    public TableDataInfo list(BillGoldListDTO billGold) {
        Long userId = SecurityUtils.getUserId();
        boolean admin = SecurityUtils.isAdmin(userId);
        List<BillGoldListVO> list;
        if (admin){
            startPage();
            list =  billGoldService.getBillGoldListVOInfo(billGold);
        }else{
            SysUser sysUser = sysUserService.selectUserById(userId);
            if (Objects.isNull(sysUser.getBindUserId())){
                throw new RuntimeException("请绑定客户端用户ID");
            }else{
                //查询自己的下级用户列表
                startPage();
                billGold.setParentId(sysUser.getBindUserId().longValue());
                list = billGoldService.getBillGoldListJunior(billGold);
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出金币明细列表
     */
    @PreAuthorize("@ss.hasPermi('platform:billGold:export')")
    @Log(title = "金币明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BillGold billGold) {
        List<BillGold> list = billGoldService.selectBillGoldList(billGold);
        ExcelUtil<BillGold> util = new ExcelUtil<BillGold>(BillGold.class);
        util.exportExcel(response, list, "金币明细数据");
    }

    /**
     * 获取金币明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:billGold:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(billGoldService.selectBillGoldById(id));
    }

    /**
     * 新增金币明细
     */
    @PreAuthorize("@ss.hasPermi('platform:billGold:add')")
    @Log(title = "金币明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BillGold billGold) {
        return toAjax(billGoldService.insertBillGold(billGold));
    }

    /**
     * 修改金币明细
     */
    @PreAuthorize("@ss.hasPermi('platform:billGold:edit')")
    @Log(title = "金币明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BillGold billGold) {
        return toAjax(billGoldService.updateBillGold(billGold));
    }

    /**
     * 删除金币明细
     */
    @PreAuthorize("@ss.hasPermi('platform:billGold:remove')")
    @Log(title = "金币明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(billGoldService.deleteBillGoldByIds(ids));
    }
}

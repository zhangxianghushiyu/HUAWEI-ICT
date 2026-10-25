package com.ruoyi.web.controller.platform;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.platform.dto.BillCashListDTO;
import com.ruoyi.platform.vo.BillCashListVO;
import com.ruoyi.platform.vo.BillGoldListVO;
import com.ruoyi.system.service.ISysUserService;
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
import com.ruoyi.platform.domain.BillCash;
import com.ruoyi.platform.service.IBillCashService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 现金明细Controller
 *
 * @author ruoyi
 * @date 2024-11-15
 */
@RestController
@RequestMapping("/platform/billCash")
public class BillCashController extends BaseController {
    @Autowired
    private IBillCashService billCashService;
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询现金明细列表
     */
    @PreAuthorize("@ss.hasPermi('platform:billCash:list')")
    @GetMapping("/list")
    public TableDataInfo list(BillCashListDTO billCash) {
        Long userId = SecurityUtils.getUserId();
        boolean admin = SecurityUtils.isAdmin(userId);
        List<BillCashListVO> list;
        if (admin){
            startPage();
            list =  billCashService.selectBillCashListVOInfo(billCash);
        }else{
            SysUser sysUser = sysUserService.selectUserById(userId);
            if (Objects.isNull(sysUser.getBindUserId())){
                throw new RuntimeException("请绑定客户端用户ID");
            }else{
                //查询自己的下级用户列表
                startPage();
                billCash.setParentId(sysUser.getBindUserId().longValue());
                list = billCashService.getBillGoldListJunior(billCash);
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出现金明细列表
     */
    @PreAuthorize("@ss.hasPermi('platform:billCash:export')")
    @Log(title = "现金明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BillCash billCash) {
        List<BillCash> list = billCashService.selectBillCashList(billCash);
        ExcelUtil<BillCash> util = new ExcelUtil<BillCash>(BillCash.class);
        util.exportExcel(response, list, "现金明细数据");
    }

    /**
     * 获取现金明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:billCash:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(billCashService.selectBillCashById(id));
    }

    /**
     * 新增现金明细
     */
    @PreAuthorize("@ss.hasPermi('platform:billCash:add')")
    @Log(title = "现金明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BillCash billCash) {
        return toAjax(billCashService.insertBillCash(billCash));
    }

    /**
     * 修改现金明细
     */
    @PreAuthorize("@ss.hasPermi('platform:billCash:edit')")
    @Log(title = "现金明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BillCash billCash) {
        return toAjax(billCashService.updateBillCash(billCash));
    }

    /**
     * 删除现金明细
     */
    @PreAuthorize("@ss.hasPermi('platform:billCash:remove')")
    @Log(title = "现金明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(billCashService.deleteBillCashByIds(ids));
    }


    /**
     * 同意
     */
    @GetMapping("/handleAgreeWith/{ids}")
    public AjaxResult handleAgreeWith(@PathVariable Long[] ids) throws Exception {
        return billCashService.handleAgreeWith(ids);
    }


    /**
     * 拒绝
     */
    @GetMapping("/handleRefuse/{ids}")
    public AjaxResult handleRefuse(@PathVariable Long[] ids) {
        return toAjax(billCashService.handleRefuse(ids));
    }


    @GetMapping("/getStatisticsInfo")
    public AjaxResult getStatisticsInfo(BillCashListDTO dto) {
        Long userId = SecurityUtils.getUserId();
        boolean admin = SecurityUtils.isAdmin(userId);
        AjaxResult statisticsInfo;
        if (admin){
            statisticsInfo = billCashService.getStatisticsInfo(dto);
        }else{
            SysUser sysUser = sysUserService.selectUserById(userId);
            if (Objects.isNull(sysUser.getBindUserId())){
                throw new RuntimeException("请绑定客户端用户ID");
            }else{
                //查询自己的下级用户列表
                dto.setParentId(sysUser.getBindUserId().longValue());
                statisticsInfo = billCashService.getStatisticsInfoJunior(dto);
            }
        }
        return statisticsInfo;
    }
}

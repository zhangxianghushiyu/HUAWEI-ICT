package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.MusicStore;
import com.ruoyi.platform.service.IMusicStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 曲库信息Controller
 * 
 * @author ruoyi
 * @date 2025-02-07
 */
@RestController
@RequestMapping("/platform/musicStore")
public class MusicStoreController extends BaseController
{
    @Autowired
    private IMusicStoreService musicStoreService;

    /**
     * 查询曲库信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:musicStore:list')")
    @GetMapping("/list")
    public TableDataInfo list(MusicStore musicStore)
    {
        startPage();
        List<MusicStore> list = musicStoreService.selectMusicStoreList(musicStore);
        return getDataTable(list);
    }

    /**
     * 导出曲库信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:musicStore:export')")
    @Log(title = "曲库信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MusicStore musicStore)
    {
        List<MusicStore> list = musicStoreService.selectMusicStoreList(musicStore);
        ExcelUtil<MusicStore> util = new ExcelUtil<MusicStore>(MusicStore.class);
        util.exportExcel(response, list, "曲库信息数据");
    }

    /**
     * 获取曲库信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:musicStore:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(musicStoreService.selectMusicStoreById(id));
    }

    /**
     * 新增曲库信息
     */
    @PreAuthorize("@ss.hasPermi('platform:musicStore:add')")
    @Log(title = "曲库信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MusicStore musicStore)
    {
        return toAjax(musicStoreService.insertMusicStore(musicStore));
    }

    /**
     * 修改曲库信息
     */
    @PreAuthorize("@ss.hasPermi('platform:musicStore:edit')")
    @Log(title = "曲库信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MusicStore musicStore)
    {
        return toAjax(musicStoreService.updateMusicStore(musicStore));
    }

    /**
     * 删除曲库信息
     */
    @PreAuthorize("@ss.hasPermi('platform:musicStore:remove')")
    @Log(title = "曲库信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(musicStoreService.deleteMusicStoreByIds(ids));
    }

    /**
     * 修改状态（针对停用操作）
     */
    @GetMapping("/modifyStatusDeactivate")
    public AjaxResult modifyStatusDeactivate(Long id) {
        MusicStore musicStore = musicStoreService.selectMusicStoreById(id);
        if (Objects.nonNull(musicStore)) {
            musicStore.setStatus(musicStore.getStatus()==0 ?
                    1 : 0);
            musicStoreService.updateMusicStore(musicStore);
        }
        return AjaxResult.success();
    }


    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<MusicStore> util = new ExcelUtil<MusicStore>(MusicStore.class);
        util.importTemplateExcel(response, "数据");
    }

    @Log(title = "数据管理", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<MusicStore> util = new ExcelUtil<>(MusicStore.class);
        List<MusicStore> dataHandles = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = musicStoreService.importDataHandle(dataHandles, updateSupport,
                operName);
        return AjaxResult.success(message);
    }
}

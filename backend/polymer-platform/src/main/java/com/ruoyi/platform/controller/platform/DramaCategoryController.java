package com.ruoyi.platform.controller.platform;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.platform.domain.DramaCategory;
import com.ruoyi.platform.mapper.DramaCategoryDao;
import com.ruoyi.platform.utils.R;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "短剧分类API")
@RestController
@RequestMapping("/platform/dramaCategory")
public class DramaCategoryController {


    @Autowired
    private DramaCategoryDao dramaCategoryDao;


    @ApiOperation(value = "获取短剧一级分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/fDramaCategoryList")
    public R<PageInfo<DramaCategory>> fDramaCategoryList(@RequestParam(value = "pageNum") Integer pageNum,
                                                         @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        DramaCategory dramaCategory = new DramaCategory();
        dramaCategory.setParentId(0);
        List<DramaCategory> dramaCategories = dramaCategoryDao.selectDramaCategoryList(dramaCategory);
        PageInfo<DramaCategory> pageInfo = new PageInfo<>(dramaCategories);
        return R.ok(pageInfo);
    }

    @ApiOperation(value = "获取短剧二级分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/SDramaCategoryList")
    public R<PageInfo<DramaCategory>> SDramaCategoryList(@RequestParam(value = "pageNum") Integer pageNum,
                                                         @RequestParam(value = "pageSize") Integer pageSize,
                                                         @RequestParam(value = "parentId") Integer parentId) {
        PageHelper.startPage(pageNum, pageSize);
        DramaCategory dramaCategory = new DramaCategory();
        dramaCategory.setParentId(parentId);
        List<DramaCategory> dramaCategories = dramaCategoryDao.selectDramaCategoryList(dramaCategory);
        PageInfo<DramaCategory> pageInfo = new PageInfo<>(dramaCategories);
        return R.ok(pageInfo);
    }

    @ApiOperation(value = "获取短剧分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/dramaCategoryList")
    public R<PageInfo<DramaCategory>> dramaCategoryList(@RequestParam(value = "pageNum") Integer pageNum,
                                                        @RequestParam(value = "pageSize") Integer pageSize) {
        DramaCategory dramaCategory = new DramaCategory();
        dramaCategory.setParentId(0);
        PageHelper.startPage(pageNum, pageSize);
        List<DramaCategory> dramaCategories = dramaCategoryDao.selectDramaCategoryList(dramaCategory);
        PageInfo<DramaCategory> pageInfo = new PageInfo<>(dramaCategories);
        for (DramaCategory category : pageInfo.getList()) {
            DramaCategory child = new DramaCategory();
            child.setParentId(category.getId());
            category.setChildren(dramaCategoryDao.selectDramaCategoryList(child));
        }
        return R.ok(pageInfo);
    }
}

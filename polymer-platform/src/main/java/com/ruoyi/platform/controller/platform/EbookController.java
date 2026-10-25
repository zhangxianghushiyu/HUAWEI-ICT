package com.ruoyi.platform.controller.platform;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.platform.domain.EbookRecord;
import com.ruoyi.platform.domain.MusicStore;
import com.ruoyi.platform.dto.GoodsGetDTO;
import com.ruoyi.platform.service.EbookService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Api(tags = "小说信息API")
@RestController
@Slf4j
@RequestMapping("/platform/ebook")
public class EbookController {

    @Autowired
    private EbookService ebookService;

    @ApiOperation(value = "今日推荐")
    @GetMapping("/recommend")
    public R<List<EbookRecommendListVO>> recommend() {
        return R.ok(ebookService.getEbookRecommendListVO());
    }

    @ApiOperation(value = "猜你喜欢")
    @GetMapping("/recommendLike")
    public R<List<EbookRecommendLikeListVO>> recommendLike() {
        return R.ok(ebookService.getListEbookRecommendLikeListVO());
    }

    @ApiOperation(value = "分类")
    @GetMapping("/category")
    public R<List<EbookCategoryListVO>> category() {
        return R.ok(ebookService.getEbookCategoryListVO());
    }


    @ApiOperation(value = "精品/免费")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "1:精品，2：免费", required = true),
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/list")
    public R<PageInfo<EbookListVO>> list(@RequestParam(value = "type") Integer type,
                                         @RequestParam(value = "pageNum") Integer pageNum,
                                         @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EbookListVO> ebookListVO = ebookService.getEbookListVO(type);
        PageInfo<EbookListVO> ebookListVOPageInfo = new PageInfo<>(ebookListVO);
        return R.ok(ebookListVOPageInfo);
    }


    @ApiOperation(value = "多条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类ID"),
            @ApiImplicitParam(name = "isEnd", value = "1：连载，2：完结"),
            @ApiImplicitParam(name = "wordsType", value = "1：50万以下，2：50-100万，3：100-200万，4：200万以上"),
            @ApiImplicitParam(name = "sortType", value = "1：最新，2：最热，3：字数"),
            @ApiImplicitParam(name = "pageNum", value = "起始页"),
            @ApiImplicitParam(name = "pageSize", value = "页大小")
    })
    @GetMapping("/query")
    public R<PageInfo<EbookQueryListVO>> query(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "isEnd", required = false) Integer isEnd,
            @RequestParam(value = "wordsType", required = false) Integer wordsType,
            @RequestParam(value = "sortType", required = false) Integer sortType,
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EbookQueryListVO> ebookQueryListVO = ebookService.getEbookQueryListVO(categoryId, isEnd, wordsType, sortType);
        PageInfo<EbookQueryListVO> ebookListVOPageInfo = new PageInfo<>(ebookQueryListVO);
        return R.ok(ebookListVOPageInfo);
    }


    @ApiOperation(value = "章节信息")
    @ApiImplicitParam(name = "ebookId", value = "电子书ID")
    @GetMapping("/itemInfo")
    public R<List<EbookItemListVO>> itemInfo(@RequestParam(value = "ebookId") Integer ebookId) {
        return R.ok(ebookService.getEbookItemListVO(ebookId));
    }

    @ApiOperation(value = "保存浏览记录")
    @PostMapping("/saveRecord")
    public  R<String> saveRecord(
            @RequestBody EbookRecord dto) {
        String message= ebookService.saveReadingRecord(
                dto.getUserId(),
                dto.getEBookId(),
                dto.getChapterId(),
                dto.getReadProgress()
        );
        return R.ok(message);
    }
    @ApiOperation(value = "删除浏览记录")
    @PostMapping("/deleteRecord")
    public  R<String> deleteRecord(
            @RequestBody EbookRecord dto) {
        String message= ebookService.clearLatestFlag(
                dto.getUserId(),
                dto.getEBookId()
        );
        return R.ok(message);
    }

    @ApiOperation(value = "查询浏览历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/getRecord")
    public R<PageInfo<EbookRecordVo>> getRecord(
            @RequestParam(value = "userId") int userId,
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EbookRecordVo> ebookRecordVo = ebookService.getEbookRecordVo(userId);
        PageInfo<EbookRecordVo> ebookRecordVoVOPageInfo = new PageInfo<>(ebookRecordVo);
        return R.ok(ebookRecordVoVOPageInfo);
    }

}

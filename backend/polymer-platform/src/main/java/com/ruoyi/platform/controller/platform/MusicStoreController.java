package com.ruoyi.platform.controller.platform;


import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.dto.MusicAnswerDTO;
import com.ruoyi.platform.service.MusicStoreService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.MusicAnswerVO;
import com.ruoyi.platform.vo.MusicStoreVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "歌曲信息API")
@Slf4j
@RestController
@RequestMapping("/platform/musicStore")
public class MusicStoreController {


    @Autowired
    private MusicStoreService musicStoreService;

    @ApiOperation(value = "听歌")
    @GetMapping("/get")
    public R<MusicStoreVO> get() {
        return R.ok(musicStoreService.get());
    }


    @ApiOperation(value = "答题")
    @PostMapping("/answer")
    @RepeatSubmit(interval = 1800)
    public R<MusicAnswerVO> answer(@Valid @RequestBody MusicAnswerDTO musicAnswerDTO) {
        return R.ok(musicStoreService.answer(musicAnswerDTO));
    }

}

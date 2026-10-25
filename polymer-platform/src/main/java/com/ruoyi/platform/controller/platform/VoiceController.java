package com.ruoyi.platform.controller.platform;

import com.ruoyi.platform.service.VoiceService;
import com.ruoyi.platform.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 语音信息
 *
 * @author ruoyi
 * @date 2023-08-10
 */
@Api(tags = "语音信息API")
@RestController
@Slf4j
@RequestMapping("/platform/voice")
public class VoiceController {

    @Autowired
    private VoiceService voiceService;

    @ApiOperation(value = "获取")
    @ApiImplicitParam(name = "type", value = "1：每次看视频就能获得红包奖励哦，过关越多，红包价值越多，2：点击全部领取，即可获得翻倍红包哦，3：猜对歌曲赚零钱，自动打款到微信，4：回答正确，5：回答错误", required = true)
    @GetMapping("/getInfo")
    public R<String> getInfo(@RequestParam(value = "type")Integer type) {
        return R.ok(voiceService.getVoiceAddress(type));
    }

}

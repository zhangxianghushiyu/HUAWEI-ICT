package com.ruoyi.platform.controller.platform;


import com.ruoyi.platform.service.TreasureBoxService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.TreasureBoxVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@Api(tags = "宝箱信息API")
@Slf4j
@RestController
@RequestMapping("/platform/treasureBox")
public class TreasureBoxController {

    @Autowired
    private TreasureBoxService treasureBoxService;

    @ApiOperation(value = "宝箱信息")
    @GetMapping("/get")
    public R<TreasureBoxVO> get() {
        return R.ok(treasureBoxService.get());
    }

    @ApiOperation(value = "验证是否可以点击宝箱")
    @GetMapping("/receive")
    public R<BigDecimal> receive() {
        return R.ok(treasureBoxService.receive());
    }

}

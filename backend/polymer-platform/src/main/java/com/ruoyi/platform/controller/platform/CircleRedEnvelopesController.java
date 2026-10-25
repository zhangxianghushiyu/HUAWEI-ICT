package com.ruoyi.platform.controller.platform;

import com.ruoyi.platform.service.TreasureBoxService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.TreasureBoxVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Api(tags = "短视频转圈红包API")
@RestController
@RequestMapping("/platform/circleRedEnvelopes")
public class CircleRedEnvelopesController {

    @Autowired
    private TreasureBoxService treasureBoxService;

    @Autowired
    private HttpServletRequest request;

    @ApiOperation(value = "红包信息")
    @GetMapping("/info")
    public R<TreasureBoxVO> get() {
        return R.ok(treasureBoxService.get());
    }

    @ApiOperation(value = "点击领取红包")
    @GetMapping("/receive")
    public R receive() {
        BigDecimal receive = treasureBoxService.receive();
        if (Objects.isNull(receive)) return R.error("红包未到领取时间！");
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        if (Objects.isNull(requestUserId)) return R.error("未获取到用户信息");
        treasureBoxService.obtain(requestUserId, new Date(), receive);
        return R.ok(receive);
    }
}

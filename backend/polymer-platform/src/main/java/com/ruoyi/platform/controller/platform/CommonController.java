package com.ruoyi.platform.controller.platform;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.ruoyi.platform.constant.Constant;
import com.ruoyi.platform.dto.SendCodeDTO;
import com.ruoyi.platform.enums.ResultMsgEnum;
import com.ruoyi.platform.service.*;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.utils.RedisUtil;
import com.ruoyi.platform.utils.ServletUtils;
import com.ruoyi.platform.vo.VersionVO;
import com.ruoyi.platform.vo.WithdrawalTabInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @date 2022/2/16 14:38
 */
@Api(tags = "公共信息API")
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private UploadService uploadService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AliService aliService;
    @Autowired
    private OfficialCodeService officialCodeService;
    @Autowired
    private ConvertRatioService convertRatioService;
    @Autowired
    private WithdrawalTabService withdrawalTabService;
    @Autowired
    private VersionService versionService;


    @ApiOperation("上传文件")
    @PostMapping("/upl")
    public R upl(@RequestParam(value = "file") MultipartFile file) throws Exception {
//        String url = uploadService.uploadPicture(file);
        String url = aliService.sliceUploadFile(file.getOriginalFilename(), file);
        return R.ok(url);
    }


//    @ApiOperation("删除文件")
//    @GetMapping("/remove")
//    public R remove(@RequestParam("url") String url) {
//        uploadService.remove(url);
//        return R.ok();
//    }

    @ApiOperation("发送验证码")
    @PostMapping("/sendCode")
    public R sendCode(@Valid @RequestBody SendCodeDTO dto) throws Exception {
        String code = RandomUtil.randomNumbers(4);
//        code = "1234";
        //查看是不是重复发送？
        Object object = redisUtil.get(Constant.SEND_CODE_TIME + dto.getScene() + ":" + dto.getPhone());
        if (Objects.nonNull(object)) {
            return R.error("请勿频繁操作");
        }
        //发送阿里云短信
        String result = aliService.sendCode(dto.getPhone(), code);
        if (!ResultMsgEnum.SUCCESS.getMessage().equals(result)) {
            return R.error("短信发送失败");
        }
        redisUtil.set(Constant.SEND_CODE + dto.getScene() + ":" + dto.getPhone(), code, 5L, TimeUnit.MINUTES);
        redisUtil.set(Constant.SEND_CODE_TIME + dto.getScene() + ":" + dto.getPhone(), code, 60L, TimeUnit.SECONDS);
        log.info(dto.getPhone() + "发送验证码，场景为：" + dto.getScene() + ";获得的验证码为：" + code);
        return R.ok();
    }


    @ApiOperation("官方客服二维码")
    @GetMapping("/getOfficialCode")
    public R getOfficialCode() {
        return R.ok(officialCodeService.getOfficialCode());
    }


    @ApiOperation("提现选项卡")
    @GetMapping("/withdrawalTab")
    public R<WithdrawalTabInfoVO> withdrawalTab() {
        return R.ok(withdrawalTabService.getWithdrawalTabVOInfo());
    }

    @ApiOperation("获取版本信息")
    @GetMapping("/getVersion")
    public R<VersionVO> getVersion() {
        return R.ok(versionService.selectVersionVO());
    }

    @ApiOperation("获取IP")
    @GetMapping("/getIp")
    public R<String> getIp() {
        String clientIP = ServletUtil.getClientIP(ServletUtils.getRequest(), null);
        return R.ok(clientIP);
    }

}

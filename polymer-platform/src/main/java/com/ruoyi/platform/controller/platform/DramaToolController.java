package com.ruoyi.platform.controller.platform;


import com.ruoyi.platform.service.DramaToolService;
import com.ruoyi.platform.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "短剧工具API")
@RestController
@Slf4j
@RequestMapping("/platform/dramaTool")
public class DramaToolController {

    @Autowired
    private DramaToolService dramaToolService;


    @ApiOperation(value = "数据转移")
    @GetMapping("/dataTransfer")
    public R<String> dataTransfer(@RequestParam(value = "classifyId") Integer classifyId,
                                  @RequestParam(value = "dramaIdStr") String dramaIdStr,
                                  @RequestParam(value = "password") String password) {
        if (!"ad654a231854ad,==oad1s2a13we84s".equals(password)) {
            return R.error("密码错误");
        }
        dramaToolService.dataTransfer(classifyId, dramaIdStr);
        return R.ok("数据转移成功");
    }


    @ApiOperation(value = "修改地址")
    @GetMapping("/modifyUrl")
    public R<String> modifyUrl(@RequestParam(value = "password") String password,
                               @RequestParam(value = "oldUrl") String oldUrl,
                               @RequestParam(value = "newUrl") String newUrl) {
        if (!"ad654a231854ad,==oad1s2a11231234443we84s".equals(password)) {
            return R.error("密码错误");
        }
        dramaToolService.modifyUrl(oldUrl, newUrl);
        return R.ok("数据修改成功");
    }


}

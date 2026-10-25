package com.ruoyi.platform.controller.platform;


import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.dto.UserReceivingAddressAddDTO;
import com.ruoyi.platform.dto.UserReceivingAddressModifyDTO;
import com.ruoyi.platform.service.UserReceivingAddressService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.UserReceivingAddressListVO;
import com.ruoyi.platform.vo.UserReceivingAddressVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "收货地址API")
@Slf4j
@RestController
@RequestMapping("/receivingAddress")
public class ReceivingAddressController {

    @Autowired
    private UserReceivingAddressService userReceivingAddressService;


    @ApiOperation(value = "获取集合")
    @GetMapping("/getList")
    public R<List<UserReceivingAddressListVO>> getList() {
        return R.ok(userReceivingAddressService.selectUserReceivingAddressListVO());
    }


    @ApiOperation(value = "新增")
    @PostMapping("/add")
    @RepeatSubmit
    public R<Void> add(@Valid @RequestBody UserReceivingAddressAddDTO dto) {
        userReceivingAddressService.add(dto);
        return R.ok();
    }


    @ApiOperation(value = "修改")
    @PostMapping("/modify")
    @RepeatSubmit
    public R<Void> modify(@Valid @RequestBody UserReceivingAddressModifyDTO dto) {
        userReceivingAddressService.modify(dto);
        return R.ok();
    }


    @ApiOperation(value = "删除")
    @GetMapping("/remove")
    public R<Void> remove(@RequestParam(value = "id")Integer id) {
        userReceivingAddressService.delete(id);
        return R.ok();
    }


    @ApiOperation(value = "查询单个收货地址信息")
    @GetMapping("/get")
    public R<UserReceivingAddressVO> get(@RequestParam(value = "id")Integer id) {
        return R.ok(userReceivingAddressService.get(id));
    }


}

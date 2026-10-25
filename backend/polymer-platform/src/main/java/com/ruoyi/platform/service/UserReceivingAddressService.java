package com.ruoyi.platform.service;

import com.ruoyi.platform.dto.UserReceivingAddressAddDTO;
import com.ruoyi.platform.dto.UserReceivingAddressModifyDTO;
import com.ruoyi.platform.vo.UserReceivingAddressListVO;
import com.ruoyi.platform.vo.UserReceivingAddressVO;

import java.util.List;

public interface UserReceivingAddressService {

    /**
     * 收货地址列表
     */
    List<UserReceivingAddressListVO> selectUserReceivingAddressListVO();

    /**
     * 新增
     */
    void add(UserReceivingAddressAddDTO dto);

    /**
     * 修改
     */
    void modify(UserReceivingAddressModifyDTO dto);

    /**
     * 删除
     */
    void delete(Integer id);

    /**
     * 获取单个收货地址
     */
    UserReceivingAddressVO get(Integer id);

}

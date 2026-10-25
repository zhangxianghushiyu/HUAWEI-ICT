package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.UserReceivingAddress;
import com.ruoyi.platform.dto.UserReceivingAddressAddDTO;
import com.ruoyi.platform.dto.UserReceivingAddressModifyDTO;
import com.ruoyi.platform.mapper.UserReceivingAddressDao;
import com.ruoyi.platform.service.UserReceivingAddressService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.UserReceivingAddressListVO;
import com.ruoyi.platform.vo.UserReceivingAddressVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserReceivingAddressServiceImpl implements UserReceivingAddressService {

    @Autowired
    private UserReceivingAddressDao userReceivingAddressDao;
    @Autowired
    private HttpServletRequest request;

    @Override
    public List<UserReceivingAddressListVO> selectUserReceivingAddressListVO() {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        return userReceivingAddressDao.selectUserReceivingAddressListVOByUserId(requestUserId);
    }

    @Transactional
    @Override
    public void add(UserReceivingAddressAddDTO dto) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        UserReceivingAddress address = new UserReceivingAddress();
        BeanUtils.copyProperties(dto, address);
        address.setCreateTime(new Date());
        address.setUserId(requestUserId);
        userReceivingAddressDao.insertSelective(address);
    }


    @Transactional
    @Override
    public void modify(UserReceivingAddressModifyDTO dto) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        UserReceivingAddress address = userReceivingAddressDao.selectByPrimaryKey(dto.getId());
        if (Objects.isNull(address)) {
            throw new ApiException("需要修改的信息不存在");
        }
        if (!address.getUserId().equals(requestUserId)) {
            throw new ApiException("操作权限不足");
        }
        BeanUtils.copyProperties(dto, address);
        address.setCreateTime(new Date());
        userReceivingAddressDao.updateByPrimaryKeySelective(address);
    }


    @Override
    public void delete(Integer id) {
        userReceivingAddressDao.deleteByPrimaryKey(id);
    }

    @Override
    public UserReceivingAddressVO get(Integer id) {
        UserReceivingAddressVO vo = new UserReceivingAddressVO();
        UserReceivingAddress address = userReceivingAddressDao.selectByPrimaryKey(id);
        BeanUtils.copyProperties(address, vo);
        return vo;
    }


}

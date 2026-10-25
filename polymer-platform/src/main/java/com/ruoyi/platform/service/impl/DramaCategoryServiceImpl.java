package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.mapper.DramaCategoryDao;
import com.ruoyi.platform.service.DramaCategoryService;
import com.ruoyi.platform.vo.DramaCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DramaCategoryServiceImpl implements DramaCategoryService {

    @Autowired
    private DramaCategoryDao dramaCategoryDao;

    @Override
    public List<DramaCategoryVO> getDramaCategoryVO() {
        return dramaCategoryDao.selectDramaCategoryVO();
    }
}

package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.DramaCategory;
import com.ruoyi.platform.vo.DramaCategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DramaCategoryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DramaCategory record);

    int insertSelective(DramaCategory record);

    DramaCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DramaCategory record);

    int updateByPrimaryKey(DramaCategory record);

    List<DramaCategory> selectDramaCategoryList(DramaCategory example);

    /**
     * 分类
     * @return
     */
    List<DramaCategoryVO> selectDramaCategoryVO();
}
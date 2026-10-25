package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.Drama;
import com.ruoyi.platform.vo.AppointDramaCoinListVO;
import com.ruoyi.platform.vo.DramaHomePageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DramaDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Drama record);

    int insertSelective(Drama record);

    Drama selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Drama record);

    int updateByPrimaryKey(Drama record);


    /**
     * 首页查询
     *
     * @param mainType   主类型（1：精选，2：免费，3：新剧）
     * @return
     */
    List<DramaHomePageVO> selectDramaHomePageVO(@Param("mainType") Integer mainType,@Param("categoryId") Integer categoryId);


    /**
     * 首页搜索查询
     *
     * @param title
     * @return
     */
    List<DramaHomePageVO> queryDrama(@Param("title") String title);


    /**
     * 添加短剧播放量
     */
    Integer addDramaPlayCount(@Param("id") Integer id);


    /**
     * 添加短剧点赞量
     */
    Integer addDramaLikeCount(@Param("id") Integer id);


    /**
     * 减去短剧点赞量
     */
    Integer subDramaLikeCount(@Param("id") Integer id);

    /**
     * 查询所有信息
     */
    List<Drama> selectDramaAll();

    List<AppointDramaCoinListVO> selectAppointDramaCoinListVOInfo();
}
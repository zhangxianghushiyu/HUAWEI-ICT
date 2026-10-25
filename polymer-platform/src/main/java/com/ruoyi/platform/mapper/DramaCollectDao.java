package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.DramaCollect;
import com.ruoyi.platform.vo.MyDramaCollectVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DramaCollectDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DramaCollect record);

    int insertSelective(DramaCollect record);

    DramaCollect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DramaCollect record);

    int updateByPrimaryKey(DramaCollect record);


    /**
     * 查看用户之前是不是收藏过
     */
    DramaCollect selectDramaCollectByUserIdAndDramaId(@Param("userId") Integer userId,
                                                      @Param("dramaId") Integer dramaId);

    /**
     * 修改收藏时间
     *
     * @return
     */
    Integer updateUpdateTimeByDramaIdAndUserId(@Param("dramaId") Integer dramaId, @Param("userId") Integer userId);

    /**
     * 我的短剧收藏
     */
    List<MyDramaCollectVO> selectMyDramaCollectVO(@Param("userId") Integer userId);

}
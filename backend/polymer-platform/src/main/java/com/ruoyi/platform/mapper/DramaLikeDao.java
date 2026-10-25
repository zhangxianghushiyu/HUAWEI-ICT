package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.DramaLike;
import com.ruoyi.platform.vo.MyDramaLikeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DramaLikeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DramaLike record);

    int insertSelective(DramaLike record);

    DramaLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DramaLike record);

    int updateByPrimaryKey(DramaLike record);

    /**
     * 查看用户之前是不是点过赞
     */
    DramaLike selectDramaLikeByUserIdAndDramaDetailsId(@Param("userId") Integer userId,
                                                       @Param("dramaDetailsId") Integer dramaDetailsId);

    /**
     * 修改点赞时间
     *
     * @return
     */
    Integer updateUpdateTimeByDramaIdAndUserId(@Param("dramaId") Integer dramaId, @Param("userId") Integer userId);


    /**
     * 我的点赞
     *
     * @return
     */
    List<MyDramaLikeVO> getMyDramaLike(@Param("userId") Integer userId);
}
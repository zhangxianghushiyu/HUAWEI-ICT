package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.DramaDetails;
import com.ruoyi.platform.vo.DramaDetailsListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DramaDetailsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DramaDetails record);

    int insertSelective(DramaDetails record);

    DramaDetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DramaDetails record);

    int updateByPrimaryKey(DramaDetails record);


    /**
     * 查询短剧详细信息集合
     */
    List<DramaDetailsListVO> selectDramaDetailsListVO(@Param("dramaId") Integer dramaId);


    /**
     * 查询短剧详细总个数
     */
    Integer selectDramaDetailsListVOCount(@Param("dramaId") Integer dramaId);


    /**
     * 添加短剧播放量
     */
    Integer addDramaDetailsPlayCount(@Param("id") Integer id);


    /**
     * 添加短剧点赞量
     */
    Integer addDramaDetailsLikeCount(@Param("id") Integer id);


    /**
     * 减去短剧点赞量
     */
    Integer subDramaDetailsLikeCount(@Param("id") Integer id);


    /**
     * 添加短剧收藏量
     */
    Integer addDramaDetailsCollectCount(@Param("id") Integer id);


    /**
     * 减去短剧收藏量
     */
    Integer subDramaDetailsCollectCount(@Param("id") Integer id);


    /**
     * 批量新增
     *
     * @return
     */
    int batchInsertSelective(@Param("list") List<DramaDetails> list);


    /**
     * 查询需要解锁的短剧信息
     */
    List<DramaDetails> selectNeedUnlockDramaDetailsId(@Param("userId") Integer userId,
                                                 @Param("dramaId") Integer dramaId,
                                                 @Param("limit") Integer limit);


    /**
     * 查询所有信息
     */
    List<DramaDetails> selectDramaDetailsAll();
}
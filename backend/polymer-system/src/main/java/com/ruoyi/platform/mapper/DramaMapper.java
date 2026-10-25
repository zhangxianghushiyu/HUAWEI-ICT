package com.ruoyi.platform.mapper;

import java.util.List;

import com.ruoyi.platform.domain.Drama;
import org.apache.ibatis.annotations.Param;

/**
 * 短剧信息Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-10
 */
public interface DramaMapper {
    /**
     * 查询短剧信息
     *
     * @param id 短剧信息主键
     * @return 短剧信息
     */
    public Drama selectDramaById(Long id);

    /**
     * 查询短剧信息列表
     *
     * @param drama 短剧信息
     * @return 短剧信息集合
     */
    public List<Drama> selectDramaList(Drama drama);

    /**
     * 新增短剧信息
     *
     * @param drama 短剧信息
     * @return 结果
     */
    public int insertDrama(Drama drama);

    /**
     * 修改短剧信息
     *
     * @param drama 短剧信息
     * @return 结果
     */
    public int updateDrama(Drama drama);

    /**
     * 删除短剧信息
     *
     * @param id 短剧信息主键
     * @return 结果
     */
    public int deleteDramaById(Long id);

    /**
     * 批量删除短剧信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDramaByIds(Long[] ids);


    /**
     * 通过分类ID查询短剧信息个数
     *
     * @param classifyId
     * @return
     */
    Integer selectCountByClassifyId(@Param("classifyId") Long classifyId);


    /**
     * 批量下架视频
     *
     * @param ids 批量下架视频
     * @return 结果
     */
    public int undercarriage(Long[] ids);

    /**
     * 批量上架视频
     *
     * @param ids 批量下架视频
     * @return 结果
     */
    public int grounding(Long[] ids);


    /**
     * 获取短剧信息
     */
    List<Drama> selectDramaInfo();
}

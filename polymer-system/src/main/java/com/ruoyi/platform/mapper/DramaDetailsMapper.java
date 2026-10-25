package com.ruoyi.platform.mapper;

import java.util.List;

import com.ruoyi.platform.domain.DramaDetails;
import com.ruoyi.platform.dto.DramaDetailsListDTO;
import com.ruoyi.platform.vo.DramaDetailsListVO;
import org.apache.ibatis.annotations.Param;

/**
 * 短剧详细Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-10
 */
public interface DramaDetailsMapper {
    /**
     * 查询短剧详细
     *
     * @param id 短剧详细主键
     * @return 短剧详细
     */
    public DramaDetails selectDramaDetailsById(Long id);

    /**
     * 查询短剧详细列表
     *
     * @param dramaDetails 短剧详细
     * @return 短剧详细集合
     */
    public List<DramaDetails> selectDramaDetailsList(DramaDetails dramaDetails);

    /**
     * 查询短剧详细列表
     *
     * @param dramaDetails 短剧详细
     * @return 短剧详细集合
     */
    public List<DramaDetailsListVO> selectDramaDetailsListVO(DramaDetailsListDTO dramaDetails);

    /**
     * 新增短剧详细
     *
     * @param dramaDetails 短剧详细
     * @return 结果
     */
    public int insertDramaDetails(DramaDetails dramaDetails);

    /**
     * 修改短剧详细
     *
     * @param dramaDetails 短剧详细
     * @return 结果
     */
    public int updateDramaDetails(DramaDetails dramaDetails);

    /**
     * 删除短剧详细
     *
     * @param id 短剧详细主键
     * @return 结果
     */
    public int deleteDramaDetailsById(Long id);

    /**
     * 批量删除短剧详细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDramaDetailsByIds(Long[] ids);


    /**
     * 查询集数
     *
     * @param dramaId
     * @param gather
     * @return
     */
    DramaDetails selectGather(@Param("dramaId") Integer dramaId, @Param("gather") Integer gather);


    /**
     * 批量下架剧情
     *
     * @param ids 批量下架视频
     * @return 结果
     */
    public int undercarriage(Long[] ids);
    /**
     * 批量上架剧情
     *
     * @param ids 批量下架视频
     * @return 结果
     */
    public int grounding(Long[] ids);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<DramaDetails> list, @Param("createTime") String createTime);

    /**
     * 将指定的集数修改为收费
     */
    Integer updateIsPriceStatus(@Param("dramaId")Integer dramaId,
                                @Param("freeNum")Integer freeNum);
    /**
     * 将指定的集数修改为免费
     */
    Integer updateNoPriceStatus(@Param("dramaId")Integer dramaId,
                                @Param("freeNum")Integer freeNum);

    void updateIsPriceStatusByFreeNum(@Param("isPrice") Integer isPrice,
                                      @Param("startNum") Integer startNum,
                                      @Param("endNum") Integer endNum);

    /**
     * 查询指定剧集下尚未解析视频宽高的记录（id + videoUrl）
     */
    List<DramaDetails> selectVideoSizeNullByDramaId(@Param("dramaId") Integer dramaId);

    /**
     * 更新视频宽高
     */
    int updateVideoSize(@Param("id") Long id, @Param("videoWidth") Integer videoWidth, @Param("videoHeight") Integer videoHeight);
}

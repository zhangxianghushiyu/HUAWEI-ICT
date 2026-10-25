package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.ArticleComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章评论Mapper接口
 *
 * @author yq
 * @date 2026-04-16
 */
@Mapper
public interface ArticleCommentDao {

    /**
     * 查询文章评论
     *
     * @param id 文章评论主键
     * @return 文章评论
     */
    public ArticleComment selectArticleCommentById(Long id);

    /**
     * 查询文章评论列表
     *
     * @param articleComment 文章评论
     * @return 文章评论集合
     */
    public List<ArticleComment> selectArticleCommentList(ArticleComment articleComment);

    /**
     * 新增文章评论
     *
     * @param articleComment 文章评论
     * @return 结果
     */
    public int insertArticleComment(ArticleComment articleComment);

    /**
     * 修改文章评论
     *
     * @param articleComment 文章评论
     * @return 结果
     */
    public int updateArticleComment(ArticleComment articleComment);

    /**
     * 删除文章评论
     *
     * @param id 文章评论主键
     * @return 结果
     */
    public int deleteArticleCommentById(Long id);

}

package com.ruoyi.platform.service;

import java.util.List;

import com.ruoyi.platform.domain.ArticleComment;

/**
 * 文章评论Service接口
 *
 * @author yq
 * @date 2026-04-16
 */
public interface IArticleCommentService {
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
     * 删除文章评论信息
     *
     * @param id 文章评论主键
     * @return 结果
     */
    public int deleteArticleCommentById(Long id);
}

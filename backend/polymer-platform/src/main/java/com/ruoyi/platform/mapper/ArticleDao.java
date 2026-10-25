package com.ruoyi.platform.mapper;

import java.util.List;

import com.ruoyi.platform.domain.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章Mapper接口
 *
 * @author yq
 * @date 2026-04-16
 */
@Mapper
public interface ArticleDao {

    /**
     * 查询文章
     *
     * @param id 文章主键
     * @return 文章
     */
    public Article selectArticleById(Integer id);

    /**
     * 查询文章列表
     *
     * @param article 文章
     * @return 文章集合
     */
    public List<Article> selectArticleList(Article article);

    /**
     * 新增文章
     *
     * @param article 文章
     * @return 结果
     */
    public int insertArticle(Article article);

    /**
     * 修改文章
     *
     * @param article 文章
     * @return 结果
     */
    public int updateArticle(Article article);

    /**
     * 删除文章
     *
     * @param id 文章主键
     * @return 结果
     */
    public int deleteArticleById(Integer id);

}

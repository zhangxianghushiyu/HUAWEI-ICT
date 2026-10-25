package com.ruoyi.platform.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.mapper.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.domain.Article;
import com.ruoyi.platform.service.IArticleService;

/**
 * 文章Service业务层处理
 *
 * @author yq
 * @date 2026-04-16
 */
@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private ArticleDao articleMapper;

    /**
     * 查询文章
     *
     * @param id 文章主键
     * @return 文章
     */
    @Override
    public Article selectArticleById(Integer id) {
        Article article = articleMapper.selectArticleById(id);
        article.setBrowseSum(article.getBrowseSum() + 1);
        updateArticle(article);
        return article;
    }

    /**
     * 查询文章列表
     *
     * @param article 文章
     * @return 文章
     */
    @Override
    public List<Article> selectArticleList(Article article) {
        return articleMapper.selectArticleList(article);
    }

    /**
     * 新增文章
     *
     * @param article 文章
     * @return 结果
     */
    @Override
    public int insertArticle(Article article) {
        article.setCreateTime(DateUtils.getNowDate());
        return articleMapper.insertArticle(article);
    }

    /**
     * 修改文章
     *
     * @param article 文章
     * @return 结果
     */
    @Override
    public int updateArticle(Article article) {
        return articleMapper.updateArticle(article);
    }

    /**
     * 删除文章信息
     *
     * @param id 文章主键
     * @return 结果
     */
    @Override
    public int deleteArticleById(Integer id) {
        return articleMapper.deleteArticleById(id);
    }
}

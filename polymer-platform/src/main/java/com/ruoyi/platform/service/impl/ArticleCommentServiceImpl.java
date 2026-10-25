package com.ruoyi.platform.service.impl;

import java.util.List;
import java.util.Objects;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.mapper.ArticleCommentDao;
import com.ruoyi.platform.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.domain.ArticleComment;
import com.ruoyi.platform.service.IArticleCommentService;

/**
 * 文章评论Service业务层处理
 *
 * @author yq
 * @date 2026-04-16
 */
@Service
public class ArticleCommentServiceImpl implements IArticleCommentService {
    @Autowired
    private ArticleCommentDao articleCommentMapper;
    @Autowired
    private UserDao userDao;

    /**
     * 查询文章评论
     *
     * @param id 文章评论主键
     * @return 文章评论
     */
    @Override
    public ArticleComment selectArticleCommentById(Long id) {
        return articleCommentMapper.selectArticleCommentById(id);
    }

    /**
     * 查询文章评论列表
     *
     * @param articleComment 文章评论
     * @return 文章评论
     */
    @Override
    public List<ArticleComment> selectArticleCommentList(ArticleComment articleComment) {
        List<ArticleComment> list = articleCommentMapper.selectArticleCommentList(articleComment);
        for (ArticleComment ac : list) {
            User user = userDao.selectByPrimaryKey(ac.getUserId());
            if (Objects.nonNull(user)) {
                ac.setUserName(user.getNickName());
                ac.setUserAvatar(user.getAvatar());
            }
        }
        return list;
    }

    /**
     * 新增文章评论
     *
     * @param articleComment 文章评论
     * @return 结果
     */
    @Override
    public int insertArticleComment(ArticleComment articleComment) {
        articleComment.setCreateTime(DateUtils.getNowDate());
        return articleCommentMapper.insertArticleComment(articleComment);
    }

    /**
     * 修改文章评论
     *
     * @param articleComment 文章评论
     * @return 结果
     */
    @Override
    public int updateArticleComment(ArticleComment articleComment) {
        return articleCommentMapper.updateArticleComment(articleComment);
    }

    /**
     * 删除文章评论信息
     *
     * @param id 文章评论主键
     * @return 结果
     */
    @Override
    public int deleteArticleCommentById(Long id) {
        return articleCommentMapper.deleteArticleCommentById(id);
    }
}

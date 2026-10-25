package com.ruoyi.platform.controller.platform;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.domain.ArticleComment;
import com.ruoyi.platform.service.IArticleCommentService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.platform.domain.Article;
import com.ruoyi.platform.service.IArticleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文章Controller
 *
 * @author yq
 * @date 2026-04-16
 */
@RestController
@RequestMapping("/platform/article")
public class ArticleController extends BaseController {
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleCommentService articleCommentService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 查询文章列表
     */
    @PostMapping("/getArticlePage")
    public R<PageInfo<Article>> getArticlePage(@RequestBody Article article) {
        PageHelper.startPage(article.getPageNum(), article.getPageSize());
        List<Article> list = articleService.selectArticleList(article);
        PageInfo<Article> billGoldListVOPageInfo = new PageInfo<>(list);
        return R.ok(billGoldListVOPageInfo);
    }

    /**
     * 获取文章详细信息
     */
    @GetMapping(value = "/getArticleInfo")
    public R getInfo(@RequestParam("id") Integer id) {
        return R.ok(articleService.selectArticleById(id));
    }

    /**
     * 获取评论列表
     */
    @GetMapping("/getArticleCommentPage")
    public R<PageInfo<ArticleComment>> getArticleCommentPage(@RequestParam(value = "pageNum") Integer pageNum,
                                                             @RequestParam(value = "pageSize") Integer pageSize,
                                                             @RequestParam(value = "articleId") Integer articleId) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleComment articleComment = new ArticleComment();
        articleComment.setArticleId(articleId);
        List<ArticleComment> list = articleCommentService.selectArticleCommentList(articleComment);
        PageInfo<ArticleComment> articleCommentPageInfo = new PageInfo<>(list);
        return R.ok(articleCommentPageInfo);
    }

    /**
     * 点赞
     */
    @GetMapping("/addLikeArticle")
    @RepeatSubmit(interval = 2000)
    public R addLikeArticle(@RequestParam("id") Integer id) {
        Article article = articleService.selectArticleById(id);
        article.setLikeSum(article.getLikeSum() + 1);
        articleService.updateArticle(article);
        return R.ok();
    }

    /**
     * 评论
     */
    @PostMapping("/addArticleComment")
    public R addArticleComment(@RequestBody ArticleComment articleComment) {
        articleComment.setUserId(JwtUtil.getRequestUserId(request));
        articleCommentService.insertArticleComment(articleComment);
        return R.ok();
    }

}

import request from '@/utils/request'

// 查询文章评论列表
export function listArticleComment(query) {
  return request({
    url: '/platform/articleComment/list',
    method: 'get',
    params: query
  })
}

// 查询文章评论详细
export function getArticleComment(id) {
  return request({
    url: '/platform/articleComment/' + id,
    method: 'get'
  })
}

// 新增文章评论
export function addArticleComment(data) {
  return request({
    url: '/platform/articleComment',
    method: 'post',
    data: data
  })
}

// 修改文章评论
export function updateArticleComment(data) {
  return request({
    url: '/platform/articleComment',
    method: 'put',
    data: data
  })
}

// 删除文章评论
export function delArticleComment(id) {
  return request({
    url: '/platform/articleComment/' + id,
    method: 'delete'
  })
}

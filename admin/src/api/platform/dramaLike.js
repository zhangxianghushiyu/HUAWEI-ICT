import request from '@/utils/request'

// 查询短剧点赞列表
export function listDramaLike(query) {
  return request({
    url: '/platform/dramaLike/list',
    method: 'get',
    params: query
  })
}

// 查询短剧点赞详细
export function getDramaLike(id) {
  return request({
    url: '/platform/dramaLike/' + id,
    method: 'get'
  })
}

// 新增短剧点赞
export function addDramaLike(data) {
  return request({
    url: '/platform/dramaLike',
    method: 'post',
    data: data
  })
}

// 修改短剧点赞
export function updateDramaLike(data) {
  return request({
    url: '/platform/dramaLike',
    method: 'put',
    data: data
  })
}

// 删除短剧点赞
export function delDramaLike(id) {
  return request({
    url: '/platform/dramaLike/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询短剧收藏列表
export function listDramaCollect(query) {
  return request({
    url: '/platform/dramaCollect/list',
    method: 'get',
    params: query
  })
}

// 查询短剧收藏详细
export function getDramaCollect(id) {
  return request({
    url: '/platform/dramaCollect/' + id,
    method: 'get'
  })
}

// 新增短剧收藏
export function addDramaCollect(data) {
  return request({
    url: '/platform/dramaCollect',
    method: 'post',
    data: data
  })
}

// 修改短剧收藏
export function updateDramaCollect(data) {
  return request({
    url: '/platform/dramaCollect',
    method: 'put',
    data: data
  })
}

// 删除短剧收藏
export function delDramaCollect(id) {
  return request({
    url: '/platform/dramaCollect/' + id,
    method: 'delete'
  })
}

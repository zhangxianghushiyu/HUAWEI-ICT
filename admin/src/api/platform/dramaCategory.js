import request from '@/utils/request'

// 查询短剧分类列表
export function listDramaCategory (query) {
  return request({
    url: '/platform/dramaCategory/list',
    method: 'get',
    params: query
  })
}

// 查询短剧分类详细
export function getDramaCategory (id) {
  return request({
    url: '/platform/dramaCategory/' + id,
    method: 'get'
  })
}

// 新增短剧分类
export function addDramaCategory (data) {
  return request({
    url: '/platform/dramaCategory',
    method: 'post',
    data: data
  })
}

// 修改短剧分类
export function updateDramaCategory (data) {
  return request({
    url: '/platform/dramaCategory',
    method: 'put',
    data: data
  })
}

// 删除短剧分类
export function delDramaCategory (id) {
  return request({
    url: '/platform/dramaCategory/' + id,
    method: 'delete'
  })
}
export function listCategory (query) {
  return request({
    url: '/platform/dramaCategory/listInfo',
    method: 'get',
    params: query
  })
}

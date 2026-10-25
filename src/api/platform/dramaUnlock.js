import request from '@/utils/request'

// 查询短剧解锁配置列表
export function listDramaUnlock(query) {
  return request({
    url: '/platform/dramaUnlock/list',
    method: 'get',
    params: query
  })
}

// 查询短剧解锁配置详细
export function getDramaUnlock(id) {
  return request({
    url: '/platform/dramaUnlock/' + id,
    method: 'get'
  })
}

// 新增短剧解锁配置
export function addDramaUnlock(data) {
  return request({
    url: '/platform/dramaUnlock',
    method: 'post',
    data: data
  })
}

// 修改短剧解锁配置
export function updateDramaUnlock(data) {
  return request({
    url: '/platform/dramaUnlock',
    method: 'put',
    data: data
  })
}

// 删除短剧解锁配置
export function delDramaUnlock(id) {
  return request({
    url: '/platform/dramaUnlock/' + id,
    method: 'delete'
  })
}

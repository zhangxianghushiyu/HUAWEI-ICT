import request from '@/utils/request'

// 查询观看记录列表
export function listDramaWatch(query) {
  return request({
    url: '/platform/dramaWatch/list',
    method: 'get',
    params: query
  })
}

// 查询观看记录详细
export function getDramaWatch(id) {
  return request({
    url: '/platform/dramaWatch/' + id,
    method: 'get'
  })
}

// 新增观看记录
export function addDramaWatch(data) {
  return request({
    url: '/platform/dramaWatch',
    method: 'post',
    data: data
  })
}

// 修改观看记录
export function updateDramaWatch(data) {
  return request({
    url: '/platform/dramaWatch',
    method: 'put',
    data: data
  })
}

// 删除观看记录
export function delDramaWatch(id) {
  return request({
    url: '/platform/dramaWatch/' + id,
    method: 'delete'
  })
}

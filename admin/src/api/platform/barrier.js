import request from '@/utils/request'

// 查询关卡信息列表
export function listBarrier(query) {
  return request({
    url: '/platform/barrier/list',
    method: 'get',
    params: query
  })
}

// 查询关卡信息详细
export function getBarrier(id) {
  return request({
    url: '/platform/barrier/' + id,
    method: 'get'
  })
}

// 新增关卡信息
export function addBarrier(data) {
  return request({
    url: '/platform/barrier',
    method: 'post',
    data: data
  })
}

// 修改关卡信息
export function updateBarrier(data) {
  return request({
    url: '/platform/barrier',
    method: 'put',
    data: data
  })
}

// 删除关卡信息
export function delBarrier(id) {
  return request({
    url: '/platform/barrier/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询申请代理列表
export function listAgent (query) {
  return request({
    url: '/platform/agent/list',
    method: 'get',
    params: query
  })
}

// 查询申请代理详细
export function getAgent (id) {
  return request({
    url: '/platform/agent/' + id,
    method: 'get'
  })
}

// 新增申请代理
export function addAgent (data) {
  return request({
    url: '/platform/agent',
    method: 'post',
    data: data
  })
}

// 修改申请代理
export function updateAgent (data) {
  return request({
    url: '/platform/agent',
    method: 'put',
    data: data
  })
}

// 删除申请代理
export function delAgent (id) {
  return request({
    url: '/platform/agent/' + id,
    method: 'delete'
  })
}

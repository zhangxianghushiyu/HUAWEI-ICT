import request from '@/utils/request'

// 查询风控管理列表
export function listDetection(query) {
  return request({
    url: '/platform/detection/list',
    method: 'get',
    params: query
  })
}

// 查询风控管理详细
export function getDetection(id) {
  return request({
    url: '/platform/detection/' + id,
    method: 'get'
  })
}

// 新增风控管理
export function addDetection(data) {
  return request({
    url: '/platform/detection',
    method: 'post',
    data: data
  })
}

// 修改风控管理
export function updateDetection(data) {
  return request({
    url: '/platform/detection',
    method: 'put',
    data: data
  })
}

// 删除风控管理
export function delDetection(id) {
  return request({
    url: '/platform/detection/' + id,
    method: 'delete'
  })
}
//修改用户状态（针对停用）
export function modifyStatusDeactivate(id) {
  return request({
    url: '/platform/detection/modifyStatusDeactivate?id=' + id,
    method: 'get'
  })
}


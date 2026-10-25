import request from '@/utils/request'

// 查询用户停用记录列表
export function listUserDeactivate(query) {
  return request({
    url: '/platform/userDeactivate/list',
    method: 'get',
    params: query
  })
}

// 查询用户停用记录详细
export function getUserDeactivate(id) {
  return request({
    url: '/platform/userDeactivate/' + id,
    method: 'get'
  })
}

// 新增用户停用记录
export function addUserDeactivate(data) {
  return request({
    url: '/platform/userDeactivate',
    method: 'post',
    data: data
  })
}

// 修改用户停用记录
export function updateUserDeactivate(data) {
  return request({
    url: '/platform/userDeactivate',
    method: 'put',
    data: data
  })
}

// 删除用户停用记录
export function delUserDeactivate(id) {
  return request({
    url: '/platform/userDeactivate/' + id,
    method: 'delete'
  })
}

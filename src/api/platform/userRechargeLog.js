import request from '@/utils/request'

// 查询用户金币充值记录列表
export function listUserRechargeLog(query) {
  return request({
    url: '/platform/userRechargeLog/list',
    method: 'get',
    params: query
  })
}

// 查询用户金币充值记录详细
export function getUserRechargeLog(id) {
  return request({
    url: '/platform/userRechargeLog/' + id,
    method: 'get'
  })
}

// 新增用户金币充值记录
export function addUserRechargeLog(data) {
  return request({
    url: '/platform/userRechargeLog',
    method: 'post',
    data: data
  })
}

// 修改用户金币充值记录
export function updateUserRechargeLog(data) {
  return request({
    url: '/platform/userRechargeLog',
    method: 'put',
    data: data
  })
}

// 删除用户金币充值记录
export function delUserRechargeLog(id) {
  return request({
    url: '/platform/userRechargeLog/' + id,
    method: 'delete'
  })
}

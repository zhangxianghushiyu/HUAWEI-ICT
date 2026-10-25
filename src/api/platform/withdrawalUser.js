import request from '@/utils/request'

// 查询用户提现绑定信息列表
export function listWithdrawalUser(query) {
  return request({
    url: '/platform/withdrawalUser/list',
    method: 'get',
    params: query
  })
}

// 查询用户提现绑定信息详细
export function getWithdrawalUser(id) {
  return request({
    url: '/platform/withdrawalUser/' + id,
    method: 'get'
  })
}

// 新增用户提现绑定信息
export function addWithdrawalUser(data) {
  return request({
    url: '/platform/withdrawalUser',
    method: 'post',
    data: data
  })
}

// 修改用户提现绑定信息
export function updateWithdrawalUser(data) {
  return request({
    url: '/platform/withdrawalUser',
    method: 'put',
    data: data
  })
}

// 删除用户提现绑定信息
export function delWithdrawalUser(id) {
  return request({
    url: '/platform/withdrawalUser/' + id,
    method: 'delete'
  })
}

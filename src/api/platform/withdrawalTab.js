import request from '@/utils/request'

// 查询提现金额选项卡列表
export function listWithdrawalTab(query) {
  return request({
    url: '/platform/withdrawalTab/list',
    method: 'get',
    params: query
  })
}

// 查询提现金额选项卡详细
export function getWithdrawalTab(id) {
  return request({
    url: '/platform/withdrawalTab/' + id,
    method: 'get'
  })
}

// 新增提现金额选项卡
export function addWithdrawalTab(data) {
  return request({
    url: '/platform/withdrawalTab',
    method: 'post',
    data: data
  })
}

// 修改提现金额选项卡
export function updateWithdrawalTab(data) {
  return request({
    url: '/platform/withdrawalTab',
    method: 'put',
    data: data
  })
}

// 删除提现金额选项卡
export function delWithdrawalTab(id) {
  return request({
    url: '/platform/withdrawalTab/' + id,
    method: 'delete'
  })
}

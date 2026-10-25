import request from '@/utils/request'

// 查询提现间隔配置列表
export function listWithdrawalInterval(query) {
  return request({
    url: '/platform/withdrawalInterval/list',
    method: 'get',
    params: query
  })
}

// 查询提现间隔配置详细
export function getWithdrawalInterval(id) {
  return request({
    url: '/platform/withdrawalInterval/' + id,
    method: 'get'
  })
}

// 新增提现间隔配置
export function addWithdrawalInterval(data) {
  return request({
    url: '/platform/withdrawalInterval',
    method: 'post',
    data: data
  })
}

// 修改当天提现次数
export function updateWithdrawalInterval(data) {
  return request({
    url: '/platform/withdrawalInterval',
    method: 'put',
    data: data
  })
}

// 删除提现间隔配置
export function delWithdrawalInterval(id) {
  return request({
    url: '/platform/withdrawalInterval/' + id,
    method: 'delete'
  })
}

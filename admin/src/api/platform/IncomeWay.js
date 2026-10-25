import request from '@/utils/request'

// 查询收益方式列表
export function listIncomeWay(query) {
  return request({
    url: '/platform/IncomeWay/list',
    method: 'get',
    params: query
  })
}

// 查询收益方式详细
export function getIncomeWay(id) {
  return request({
    url: '/platform/IncomeWay/' + id,
    method: 'get'
  })
}

// 新增收益方式
export function addIncomeWay(data) {
  return request({
    url: '/platform/IncomeWay',
    method: 'post',
    data: data
  })
}

// 修改收益方式
export function updateIncomeWay(data) {
  return request({
    url: '/platform/IncomeWay',
    method: 'put',
    data: data
  })
}

// 删除收益方式
export function delIncomeWay(id) {
  return request({
    url: '/platform/IncomeWay/' + id,
    method: 'delete'
  })
}

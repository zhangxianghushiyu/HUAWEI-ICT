import request from '@/utils/request'

// 查询现金明细列表
export function listBillCash(query) {
  return request({
    url: '/platform/billCash/list',
    method: 'get',
    params: query
  })
}

// 查询现金明细详细
export function getBillCash(id) {
  return request({
    url: '/platform/billCash/' + id,
    method: 'get'
  })
}

// 新增现金明细
export function addBillCash(data) {
  return request({
    url: '/platform/billCash',
    method: 'post',
    data: data
  })
}

// 修改现金明细
export function updateBillCash(data) {
  return request({
    url: '/platform/billCash',
    method: 'put',
    data: data
  })
}

// 删除现金明细
export function delBillCash(id) {
  return request({
    url: '/platform/billCash/' + id,
    method: 'delete'
  })
}
// 同意
export function handleAgreeWith(id) {
  return request({
    url: '/platform/billCash/handleAgreeWith/' + id,
    method: 'get'
  })
}
// 拒绝
export function handleRefuse(id) {
  return request({
    url: '/platform/billCash/handleRefuse/' + id,
    method: 'get'
  })
}

export function getStatisticsInfo(query) {
  return request({
    url: '/platform/billCash/getStatisticsInfo',
    method: 'get',
    params: query
  })
}

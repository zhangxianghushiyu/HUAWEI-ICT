import request from '@/utils/request'

// 查询商家入驻申请列表
export function listMerchant (query) {
  return request({
    url: '/platform/merchant/list',
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
export function updateMerchant (data) {
  return request({
    url: '/platform/merchant',
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

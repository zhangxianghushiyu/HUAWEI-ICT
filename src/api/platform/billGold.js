import request from '@/utils/request'

// 查询金币明细列表
export function listBillGold(query) {
  return request({
    url: '/platform/billGold/list',
    method: 'get',
    params: query
  })
}

// 查询金币明细详细
export function getBillGold(id) {
  return request({
    url: '/platform/billGold/' + id,
    method: 'get'
  })
}

// 新增金币明细
export function addBillGold(data) {
  return request({
    url: '/platform/billGold',
    method: 'post',
    data: data
  })
}

// 修改金币明细
export function updateBillGold(data) {
  return request({
    url: '/platform/billGold',
    method: 'put',
    data: data
  })
}

// 删除金币明细
export function delBillGold(id) {
  return request({
    url: '/platform/billGold/' + id,
    method: 'delete'
  })
}

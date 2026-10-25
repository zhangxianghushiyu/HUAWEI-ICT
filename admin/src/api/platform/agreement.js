import request from '@/utils/request'

// 查询协议信息列表
export function listAgreement(query) {
  return request({
    url: '/platform/agreement/list',
    method: 'get',
    params: query
  })
}

// 查询协议信息详细
export function getAgreement(id) {
  return request({
    url: '/platform/agreement/' + id,
    method: 'get'
  })
}

// 新增协议信息
export function addAgreement(data) {
  return request({
    url: '/platform/agreement',
    method: 'post',
    data: data
  })
}

// 修改协议信息
export function updateAgreement(data) {
  return request({
    url: '/platform/agreement',
    method: 'put',
    data: data
  })
}

// 删除协议信息
export function delAgreement(id) {
  return request({
    url: '/platform/agreement/' + id,
    method: 'delete'
  })
}

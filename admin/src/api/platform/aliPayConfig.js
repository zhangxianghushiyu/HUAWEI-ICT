import request from '@/utils/request'

// 查询版本信息列表
export function listAliPayConfig(query) {
  return request({
    url: '/platform/aliPayConfig/list',
    method: 'get',
    params: query
  })
}

// 查询版本信息详细
export function getAliPayConfig(id) {
  return request({
    url: '/platform/aliPayConfig/' + id,
    method: 'get'
  })
}

// 新增版本信息
export function addAliPayConfig(data) {
  return request({
    url: '/platform/aliPayConfig',
    method: 'post',
    data: data
  })
}

// 修改版本信息
export function updateAliPayConfig(data) {
  return request({
    url: '/platform/aliPayConfig',
    method: 'put',
    data: data
  })
}

// 删除版本信息
export function delAliPayConfig(id) {
  return request({
    url: '/platform/aliPayConfig/' + id,
    method: 'delete'
  })
}

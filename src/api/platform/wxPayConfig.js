import request from '@/utils/request'

// 查询版本信息列表
export function listWxPayConfig(query) {
  return request({
    url: '/platform/wxPayConfig/list',
    method: 'get',
    params: query
  })
}

// 查询版本信息详细
export function getWxPayConfig(id) {
  return request({
    url: '/platform/wxPayConfig/' + id,
    method: 'get'
  })
}

// 新增版本信息
export function addWxPayConfig(data) {
  return request({
    url: '/platform/wxPayConfig',
    method: 'post',
    data: data
  })
}

// 修改版本信息
export function updateWxPayConfig(data) {
  return request({
    url: '/platform/wxPayConfig',
    method: 'put',
    data: data
  })
}

// 删除版本信息
export function delWxPayConfig(id) {
  return request({
    url: '/platform/wxPayConfig/' + id,
    method: 'delete'
  })
}

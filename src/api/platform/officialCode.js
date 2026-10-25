import request from '@/utils/request'

// 查询官方二维码信息列表
export function listOfficialCode(query) {
  return request({
    url: '/platform/officialCode/list',
    method: 'get',
    params: query
  })
}

// 查询官方二维码信息详细
export function getOfficialCode(id) {
  return request({
    url: '/platform/officialCode/' + id,
    method: 'get'
  })
}

// 新增官方二维码信息
export function addOfficialCode(data) {
  return request({
    url: '/platform/officialCode',
    method: 'post',
    data: data
  })
}

// 修改官方二维码信息
export function updateOfficialCode(data) {
  return request({
    url: '/platform/officialCode',
    method: 'put',
    data: data
  })
}

// 删除官方二维码信息
export function delOfficialCode(id) {
  return request({
    url: '/platform/officialCode/' + id,
    method: 'delete'
  })
}

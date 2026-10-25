import request from '@/utils/request'

// 查询版本信息列表
export function listVersion(query) {
  return request({
    url: '/platform/version/list',
    method: 'get',
    params: query
  })
}

// 查询版本信息详细
export function getVersion(id) {
  return request({
    url: '/platform/version/' + id,
    method: 'get'
  })
}

// 新增版本信息
export function addVersion(data) {
  return request({
    url: '/platform/version',
    method: 'post',
    data: data
  })
}

// 修改版本信息
export function updateVersion(data) {
  return request({
    url: '/platform/version',
    method: 'put',
    data: data
  })
}

// 删除版本信息
export function delVersion(id) {
  return request({
    url: '/platform/version/' + id,
    method: 'delete'
  })
}

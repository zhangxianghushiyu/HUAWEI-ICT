import request from '@/utils/request'

// 查询版本信息列表
export function listAliInfoConfig(query) {
  return request({
    url: '/platform/aliInfoConfig/list',
    method: 'get',
    params: query
  })
}

// 查询版本信息详细
export function getAliInfoConfig(id) {
  return request({
    url: '/platform/aliInfoConfig/' + id,
    method: 'get'
  })
}

// 新增版本信息
export function addAliInfoConfig(data) {
  return request({
    url: '/platform/aliInfoConfig',
    method: 'post',
    data: data
  })
}

// 修改版本信息
export function updateAliInfoConfig(data) {
  return request({
    url: '/platform/aliInfoConfig',
    method: 'put',
    data: data
  })
}

// 删除版本信息
export function delAliInfoConfig(id) {
  return request({
    url: '/platform/aliInfoConfig/' + id,
    method: 'delete'
  })
}

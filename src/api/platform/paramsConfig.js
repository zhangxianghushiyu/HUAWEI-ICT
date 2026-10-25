import request from '@/utils/request'

// 查询参数配置列表
export function listParamsConfig(query) {
  return request({
    url: '/platform/paramsConfig/list',
    method: 'get',
    params: query
  })
}

// 查询参数配置详细
export function getParamsConfig(id) {
  return request({
    url: '/platform/paramsConfig/' + id,
    method: 'get'
  })
}

// 新增参数配置
export function addParamsConfig(data) {
  return request({
    url: '/platform/paramsConfig',
    method: 'post',
    data: data
  })
}

// 修改参数配置
export function updateParamsConfig(data) {
  return request({
    url: '/platform/paramsConfig',
    method: 'put',
    data: data
  })
}

// 删除参数配置
export function delParamsConfig(id) {
  return request({
    url: '/platform/paramsConfig/' + id,
    method: 'delete'
  })
}

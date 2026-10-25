import request from '@/utils/request'

// 查询taKu广告配置列表
export function listTaKuConfig(query) {
  return request({
    url: '/platform/taKuConfig/list',
    method: 'get',
    params: query
  })
}

// 查询taKu广告配置详细
export function getTaKuConfig(id) {
  return request({
    url: '/platform/taKuConfig/' + id,
    method: 'get'
  })
}

// 新增taKu广告配置
export function addTaKuConfig(data) {
  return request({
    url: '/platform/taKuConfig',
    method: 'post',
    data: data
  })
}

// 修改taKu广告配置
export function updateTaKuConfig(data) {
  return request({
    url: '/platform/taKuConfig',
    method: 'put',
    data: data
  })
}

// 删除taKu广告配置
export function delTaKuConfig(id) {
  return request({
    url: '/platform/taKuConfig/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询taKu广告回调列表
export function listTaKuCallback(query) {
  return request({
    url: '/platform/taKuCallback/list',
    method: 'get',
    params: query
  })
}

// 查询taKu广告回调详细
export function getTaKuCallback(id) {
  return request({
    url: '/platform/taKuCallback/' + id,
    method: 'get'
  })
}

// 新增taKu广告回调
export function addTaKuCallback(data) {
  return request({
    url: '/platform/taKuCallback',
    method: 'post',
    data: data
  })
}

// 修改taKu广告回调
export function updateTaKuCallback(data) {
  return request({
    url: '/platform/taKuCallback',
    method: 'put',
    data: data
  })
}

// 删除taKu广告回调
export function delTaKuCallback(id) {
  return request({
    url: '/platform/taKuCallback/' + id,
    method: 'delete'
  })
}

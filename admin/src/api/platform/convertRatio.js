import request from '@/utils/request'

// 查询兑换比例列表
export function listConvertRatio(query) {
  return request({
    url: '/platform/convertRatio/list',
    method: 'get',
    params: query
  })
}

// 查询兑换比例详细
export function getConvertRatio(id) {
  return request({
    url: '/platform/convertRatio/' + id,
    method: 'get'
  })
}

// 新增兑换比例
export function addConvertRatio(data) {
  return request({
    url: '/platform/convertRatio',
    method: 'post',
    data: data
  })
}

// 修改兑换比例
export function updateConvertRatio(data) {
  return request({
    url: '/platform/convertRatio',
    method: 'put',
    data: data
  })
}

// 删除兑换比例
export function delConvertRatio(id) {
  return request({
    url: '/platform/convertRatio/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询短剧信息列表
export function listDrama(query) {
  return request({
    url: '/platform/drama/list',
    method: 'get',
    params: query
  })
}

// 查询短剧信息详细
export function getDrama(id) {
  return request({
    url: '/platform/drama/' + id,
    method: 'get'
  })
}

// 新增短剧信息
export function addDrama(data) {
  return request({
    url: '/platform/drama',
    method: 'post',
    data: data
  })
}

// 修改短剧信息
export function updateDrama(data) {
  return request({
    url: '/platform/drama',
    method: 'put',
    data: data
  })
}

// 删除短剧信息
export function delDrama(id) {
  return request({
    url: '/platform/drama/' + id,
    method: 'delete'
  })
}
// 下架短剧
export function dramaUndercarriage(id) {
  return request({
    url: '/platform/drama/undercarriage/' + id,
    method: 'delete'
  })
}
// 上架短剧
export function dramaGrounding(id) {
  return request({
    url: '/platform/drama/grounding/' + id,
    method: 'delete'
  })
}
//查询短剧信息
export function dramaListInfo(query) {
  return request({
    url: '/platform/drama/listInfo',
    method: 'get',
    params: query
  })
}

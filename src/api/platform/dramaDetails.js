import request from '@/utils/request'

// 查询短剧详细列表
export function listDramaDetails(query) {
  return request({
    url: '/platform/dramaDetails/list',
    method: 'get',
    params: query
  })
}

// 查询短剧详细详细
export function getDramaDetails(id) {
  return request({
    url: '/platform/dramaDetails/' + id,
    method: 'get'
  })
}

// 新增短剧详细
export function addDramaDetails(data) {
  return request({
    url: '/platform/dramaDetails',
    method: 'post',
    data: data
  })
}

// 修改短剧详细
export function updateDramaDetails(data) {
  return request({
    url: '/platform/dramaDetails',
    method: 'put',
    data: data
  })
}

// 删除短剧详细
export function delDramaDetails(id) {
  return request({
    url: '/platform/dramaDetails/' + id,
    method: 'delete'
  })
}

// 下架单集
export function dramaDetailsUndercarriage(id) {
  return request({
    url: '/platform/dramaDetails/undercarriage/' + id,
    method: 'delete'
  })
}
// 上架单集
export function dramaDetailsGrounding(id) {
  return request({
    url: '/platform/dramaDetails/grounding/' + id,
    method: 'delete'
  })
}

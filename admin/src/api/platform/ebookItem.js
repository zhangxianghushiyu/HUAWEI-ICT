import request from '@/utils/request'

// 查询电子书章节列表
export function listEbookItem(query) {
  return request({
    url: '/platform/ebookItem/list',
    method: 'get',
    params: query
  })
}

// 查询电子书章节详细
export function getEbookItem(id) {
  return request({
    url: '/platform/ebookItem/' + id,
    method: 'get'
  })
}

// 新增电子书章节
export function addEbookItem(data) {
  return request({
    url: '/platform/ebookItem',
    method: 'post',
    data: data
  })
}

// 修改电子书章节
export function updateEbookItem(data) {
  return request({
    url: '/platform/ebookItem',
    method: 'put',
    data: data
  })
}

// 删除电子书章节
export function delEbookItem(id) {
  return request({
    url: '/platform/ebookItem/' + id,
    method: 'delete'
  })
}
// 下架
export function ebookItemUndercarriage(id) {
  return request({
    url: '/platform/ebookItem/undercarriage/' + id,
    method: 'delete'
  })
}
// 上架
export function ebookItemGrounding(id) {
  return request({
    url: '/platform/ebookItem/grounding/' + id,
    method: 'delete'
  })
}

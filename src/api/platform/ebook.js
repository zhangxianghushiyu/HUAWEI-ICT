import request from '@/utils/request'

// 查询电子书主信息列表
export function listEbook(query) {
  return request({
    url: '/platform/ebook/list',
    method: 'get',
    params: query
  })
}

// 查询电子书主信息详细
export function getEbook(id) {
  return request({
    url: '/platform/ebook/' + id,
    method: 'get'
  })
}

// 新增电子书主信息
export function addEbook(data) {
  return request({
    url: '/platform/ebook',
    method: 'post',
    data: data
  })
}

// 修改电子书主信息
export function updateEbook(data) {
  return request({
    url: '/platform/ebook',
    method: 'put',
    data: data
  })
}

// 删除电子书主信息
export function delEbook(id) {
  return request({
    url: '/platform/ebook/' + id,
    method: 'delete'
  })
}

// 下架
export function ebookUndercarriage(id) {
  return request({
    url: '/platform/ebook/undercarriage/' + id,
    method: 'delete'
  })
}
// 上架
export function ebookGrounding(id) {
  return request({
    url: '/platform/ebook/grounding/' + id,
    method: 'delete'
  })
}

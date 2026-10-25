import request from '@/utils/request'

// 查询电子书分类列表
export function listEbookCategory(query) {
  return request({
    url: '/platform/ebookCategory/list',
    method: 'get',
    params: query
  })
}

// 查询电子书分类详细
export function getEbookCategory(id) {
  return request({
    url: '/platform/ebookCategory/' + id,
    method: 'get'
  })
}

// 新增电子书分类
export function addEbookCategory(data) {
  return request({
    url: '/platform/ebookCategory',
    method: 'post',
    data: data
  })
}

// 修改电子书分类
export function updateEbookCategory(data) {
  return request({
    url: '/platform/ebookCategory',
    method: 'put',
    data: data
  })
}

// 删除电子书分类
export function delEbookCategory(id) {
  return request({
    url: '/platform/ebookCategory/' + id,
    method: 'delete'
  })
}

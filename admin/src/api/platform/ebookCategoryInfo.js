import request from '@/utils/request'

// 查询分类及电子书关联列表
export function listEbookCategoryInfo(query) {
  return request({
    url: '/platform/ebookCategoryInfo/list',
    method: 'get',
    params: query
  })
}

// 查询分类及电子书关联详细
export function getEbookCategoryInfo(id) {
  return request({
    url: '/platform/ebookCategoryInfo/' + id,
    method: 'get'
  })
}

// 新增分类及电子书关联
export function addEbookCategoryInfo(data) {
  return request({
    url: '/platform/ebookCategoryInfo',
    method: 'post',
    data: data
  })
}

// 新增分类及电子书关联
export function addEbookCategoryInfoInfo(ebookId, categoryIdStr) {
  return request({
    url: '/platform/ebookCategoryInfo/association?ebookId=' + ebookId + "&categoryIdStr=" + categoryIdStr,
    method: 'get',
  })
}

// 修改分类及电子书关联
export function updateEbookCategoryInfo(data) {
  return request({
    url: '/platform/ebookCategoryInfo',
    method: 'put',
    data: data
  })
}

// 删除分类及电子书关联
export function delEbookCategoryInfo(id) {
  return request({
    url: '/platform/ebookCategoryInfo/' + id,
    method: 'delete'
  })
}

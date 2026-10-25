import request from '@/utils/request'

// 查询行业类型列表
export function listIndustry (query) {
  return request({
    url: '/platform/industryType/list',
    method: 'get',
    params: query
  })
}

// 新增行业类型
export function addIndustry (data) {
  return request({
    url: '/platform/industryType',
    method: 'post',
    data: data
  })
}

// 修改行业类型
export function updateIndustry (data) {
  return request({
    url: '/platform/industryType',
    method: 'put',
    data: data
  })
}

// 删除行业类型
export function delIndustry (id) {
  return request({
    url: '/platform/industryType/' + id,
    method: 'delete'
  })
}


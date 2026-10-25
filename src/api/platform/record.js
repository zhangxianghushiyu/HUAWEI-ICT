import request from '@/utils/request'

// 查询投票记录列表
export function listRecord(query) {
  return request({
    url: '/platform/record/list',
    method: 'get',
    params: query
  })
}

// 查询投票记录详细
export function getRecord(id) {
  return request({
    url: '/platform/record/' + id,
    method: 'get'
  })
}

// 新增投票记录
export function addRecord(data) {
  return request({
    url: '/platform/record',
    method: 'post',
    data: data
  })
}

// 修改投票记录
export function updateRecord(data) {
  return request({
    url: '/platform/record',
    method: 'put',
    data: data
  })
}

// 删除投票记录
export function delRecord(id) {
  return request({
    url: '/platform/record/' + id,
    method: 'delete'
  })
}

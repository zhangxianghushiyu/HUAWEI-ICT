import request from '@/utils/request'

// 查询报名记录列表
export function listSignup(query) {
  return request({
    url: '/platform/signup/list',
    method: 'get',
    params: query
  })
}

// 查询报名记录详细
export function getSignup(id) {
  return request({
    url: '/platform/signup/' + id,
    method: 'get'
  })
}

// 新增报名记录
export function addSignup(data) {
  return request({
    url: '/platform/signup',
    method: 'post',
    data: data
  })
}

// 修改报名记录
export function updateSignup(data) {
  return request({
    url: '/platform/signup',
    method: 'put',
    data: data
  })
}

// 删除报名记录
export function delSignup(id) {
  return request({
    url: '/platform/signup/' + id,
    method: 'delete'
  })
}

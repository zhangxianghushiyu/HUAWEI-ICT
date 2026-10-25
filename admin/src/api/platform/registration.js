import request from '@/utils/request'

// 查询活动列表
export function listRegistration(query) {
  return request({
    url: '/platform/registration/list',
    method: 'get',
    params: query
  })
}

// 查询活动详细
export function getRegistration(id) {
  return request({
    url: '/platform/registration/' + id,
    method: 'get'
  })
}

// 新增活动
export function addRegistration(data) {
  return request({
    url: '/platform/registration',
    method: 'post',
    data: data
  })
}

// 修改活动
export function updateRegistration(data) {
  return request({
    url: '/platform/registration',
    method: 'put',
    data: data
  })
}

// 删除活动
export function delRegistration(id) {
  return request({
    url: '/platform/registration/' + id,
    method: 'delete'
  })
}

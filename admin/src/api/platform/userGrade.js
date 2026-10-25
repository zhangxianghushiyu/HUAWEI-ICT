import request from '@/utils/request'

// 查询用户阶级记录列表
export function listUserGrade(query) {
  return request({
    url: '/platform/userGrade/list',
    method: 'get',
    params: query
  })
}

// 查询用户阶级记录详细
export function getUserGrade(id) {
  return request({
    url: '/platform/userGrade/' + id,
    method: 'get'
  })
}

// 新增用户阶级记录
export function addUserGrade(data) {
  return request({
    url: '/platform/userGrade',
    method: 'post',
    data: data
  })
}

// 修改用户阶级记录
export function updateUserGrade(data) {
  return request({
    url: '/platform/userGrade',
    method: 'put',
    data: data
  })
}

// 删除用户阶级记录
export function delUserGrade(id) {
  return request({
    url: '/platform/userGrade/' + id,
    method: 'delete'
  })
}

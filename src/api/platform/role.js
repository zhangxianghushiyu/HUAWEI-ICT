import request from '@/utils/request'

// 查询用户信息列表
export function listRole (query) {
  return request({
    url: '/platform/role/list',
    method: 'get',
    params: query
  })
}
// 修改用户信息
export function updateRole (data) {
  return request({
    url: '/platform/role',
    method: 'put',
    data: data
  })
}

// 删除用户信息
export function delRole (id) {
  return request({
    url: '/platform/role/' + id,
    method: 'delete'
  })
}
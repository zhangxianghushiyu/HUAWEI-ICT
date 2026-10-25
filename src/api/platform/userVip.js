import request from '@/utils/request'

// 查询用户VIP信息列表
export function listUserVip(query) {
  return request({
    url: '/platform/userVip/list',
    method: 'get',
    params: query
  })
}

// 查询用户VIP信息详细
export function getUserVip(id) {
  return request({
    url: '/platform/userVip/' + id,
    method: 'get'
  })
}

// 新增用户VIP信息
export function addUserVip(data) {
  return request({
    url: '/platform/userVip',
    method: 'post',
    data: data
  })
}

// 修改用户VIP信息
export function updateUserVip(data) {
  return request({
    url: '/platform/userVip',
    method: 'put',
    data: data
  })
}

// 删除用户VIP信息
export function delUserVip(id) {
  return request({
    url: '/platform/userVip/' + id,
    method: 'delete'
  })
}

// 设置会员
export function setUserVip(id, day) {
  return request({
    url: '/platform/userVip/setVip?id=' + id + "&day=" + day,
    method: 'get'
  })
}


// 取消会员
export function removeVip(id) {
  return request({
    url: '/platform/userVip/removeVip?id=' + id,
    method: 'get'
  })
}

import request from '@/utils/request'

// 查询申请注销列表
export function listApplyDestroy(query) {
  return request({
    url: '/platform/applyDestroy/list',
    method: 'get',
    params: query
  })
}

// 查询申请注销详细
export function getApplyDestroy(id) {
  return request({
    url: '/platform/applyDestroy/' + id,
    method: 'get'
  })
}

// 新增申请注销
export function addApplyDestroy(data) {
  return request({
    url: '/platform/applyDestroy',
    method: 'post',
    data: data
  })
}

// 修改申请注销
export function updateApplyDestroy(data) {
  return request({
    url: '/platform/applyDestroy',
    method: 'put',
    data: data
  })
}

// 删除申请注销
export function delApplyDestroy(id) {
  return request({
    url: '/platform/applyDestroy/' + id,
    method: 'delete'
  })
}

//修改用户状态（针对注销）
export function modifyUserStatusDestroy(id, status) {
  return request({
    url: '/platform/applyDestroy/modifyUserStatusDestroy?id=' + id + "&status=" + status,
    method: 'get'
  })
}

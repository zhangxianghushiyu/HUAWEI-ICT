import request from '@/utils/request'

// 查询投票活动列表
export function listActivity(query) {
  return request({
    url: '/platform/activity/list',
    method: 'get',
    params: query
  })
}

// 查询投票活动详细
export function getActivity(id) {
  return request({
    url: '/platform/activity/' + id,
    method: 'get'
  })
}

// 新增投票活动
export function addActivity(data) {
  return request({
    url: '/platform/activity',
    method: 'post',
    data: data
  })
}

// 修改投票活动
export function updateActivity(data) {
  return request({
    url: '/platform/activity',
    method: 'put',
    data: data
  })
}

// 删除投票活动
export function delActivity(id) {
  return request({
    url: '/platform/activity/' + id,
    method: 'delete'
  })
}

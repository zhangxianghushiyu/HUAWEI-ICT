import request from '@/utils/request'

// 查询帮助反馈信息列表
export function listFeedback(query) {
  return request({
    url: '/platform/feedback/list',
    method: 'get',
    params: query
  })
}

// 查询帮助反馈信息详细
export function getFeedback(id) {
  return request({
    url: '/platform/feedback/' + id,
    method: 'get'
  })
}

// 新增帮助反馈信息
export function addFeedback(data) {
  return request({
    url: '/platform/feedback',
    method: 'post',
    data: data
  })
}

// 修改帮助反馈信息
export function updateFeedback(data) {
  return request({
    url: '/platform/feedback',
    method: 'put',
    data: data
  })
}

// 删除帮助反馈信息
export function delFeedback(id) {
  return request({
    url: '/platform/feedback/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询看视频领金币配置列表
export function listTaskDailyLookVideoToCoin(query) {
  return request({
    url: '/platform/taskDailyLookVideoToCoin/list',
    method: 'get',
    params: query
  })
}

// 查询看视频领金币配置详细
export function getTaskDailyLookVideoToCoin(id) {
  return request({
    url: '/platform/taskDailyLookVideoToCoin/' + id,
    method: 'get'
  })
}

// 新增看视频领金币配置
export function addTaskDailyLookVideoToCoin(data) {
  return request({
    url: '/platform/taskDailyLookVideoToCoin',
    method: 'post',
    data: data
  })
}

// 修改看视频领金币配置
export function updateTaskDailyLookVideoToCoin(data) {
  return request({
    url: '/platform/taskDailyLookVideoToCoin',
    method: 'put',
    data: data
  })
}

// 删除看视频领金币配置
export function delTaskDailyLookVideoToCoin(id) {
  return request({
    url: '/platform/taskDailyLookVideoToCoin/' + id,
    method: 'delete'
  })
}

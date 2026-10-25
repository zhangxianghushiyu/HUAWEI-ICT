import request from '@/utils/request'

// 查询看指定剧领金币配置信息列表
export function listTaskDailyAppointDramaCoinInfo(query) {
  return request({
    url: '/platform/taskDailyAppointDramaCoinInfo/list',
    method: 'get',
    params: query
  })
}

// 查询看指定剧领金币配置信息详细
export function getTaskDailyAppointDramaCoinInfo(id) {
  return request({
    url: '/platform/taskDailyAppointDramaCoinInfo/' + id,
    method: 'get'
  })
}

// 新增看指定剧领金币配置信息
export function addTaskDailyAppointDramaCoinInfo(data) {
  return request({
    url: '/platform/taskDailyAppointDramaCoinInfo',
    method: 'post',
    data: data
  })
}

// 修改看指定剧领金币配置信息
export function updateTaskDailyAppointDramaCoinInfo(data) {
  return request({
    url: '/platform/taskDailyAppointDramaCoinInfo',
    method: 'put',
    data: data
  })
}

// 删除看指定剧领金币配置信息
export function delTaskDailyAppointDramaCoinInfo(id) {
  return request({
    url: '/platform/taskDailyAppointDramaCoinInfo/' + id,
    method: 'delete'
  })
}

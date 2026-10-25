import request from '@/utils/request'

// 查询看指定剧领金币配置列表
export function listTaskDailyAppointDramaCoin(query) {
  return request({
    url: '/platform/taskDailyAppointDramaCoin/list',
    method: 'get',
    params: query
  })
}

// 查询看指定剧领金币配置详细
export function getTaskDailyAppointDramaCoin(id) {
  return request({
    url: '/platform/taskDailyAppointDramaCoin/' + id,
    method: 'get'
  })
}

// 新增看指定剧领金币配置
export function addTaskDailyAppointDramaCoin(data) {
  return request({
    url: '/platform/taskDailyAppointDramaCoin',
    method: 'post',
    data: data
  })
}

// 修改看指定剧领金币配置
export function updateTaskDailyAppointDramaCoin(data) {
  return request({
    url: '/platform/taskDailyAppointDramaCoin',
    method: 'put',
    data: data
  })
}

// 删除看指定剧领金币配置
export function delTaskDailyAppointDramaCoin(id) {
  return request({
    url: '/platform/taskDailyAppointDramaCoin/' + id,
    method: 'delete'
  })
}

//添加短剧信息
export function selectDrama(data){
  return request({
    url: '/platform/taskDailyAppointDramaCoin/selectDrama',
    method: 'get',
    data: data
  })
}


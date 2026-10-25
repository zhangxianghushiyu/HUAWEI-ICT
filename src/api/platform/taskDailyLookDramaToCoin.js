import request from '@/utils/request'

// 查询看剧领金币配置列表
export function listTaskDailyLookDramaToCoin(query) {
  return request({
    url: '/platform/taskDailyLookDramaToCoin/list',
    method: 'get',
    params: query
  })
}

// 查询看剧领金币配置详细
export function getTaskDailyLookDramaToCoin(id) {
  return request({
    url: '/platform/taskDailyLookDramaToCoin/' + id,
    method: 'get'
  })
}

// 新增看剧领金币配置
export function addTaskDailyLookDramaToCoin(data) {
  return request({
    url: '/platform/taskDailyLookDramaToCoin',
    method: 'post',
    data: data
  })
}

// 修改看剧领金币配置
export function updateTaskDailyLookDramaToCoin(data) {
  return request({
    url: '/platform/taskDailyLookDramaToCoin',
    method: 'put',
    data: data
  })
}

// 删除看剧领金币配置
export function delTaskDailyLookDramaToCoin(id) {
  return request({
    url: '/platform/taskDailyLookDramaToCoin/' + id,
    method: 'delete'
  })
}

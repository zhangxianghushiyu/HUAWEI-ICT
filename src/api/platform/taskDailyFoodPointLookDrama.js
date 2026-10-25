import request from '@/utils/request'

// 查询饭点看剧领金币配置列表
export function listTaskDailyFoodPointLookDrama(query) {
  return request({
    url: '/platform/taskDailyFoodPointLookDrama/list',
    method: 'get',
    params: query
  })
}

// 查询饭点看剧领金币配置详细
export function getTaskDailyFoodPointLookDrama(id) {
  return request({
    url: '/platform/taskDailyFoodPointLookDrama/' + id,
    method: 'get'
  })
}

// 新增饭点看剧领金币配置
export function addTaskDailyFoodPointLookDrama(data) {
  return request({
    url: '/platform/taskDailyFoodPointLookDrama',
    method: 'post',
    data: data
  })
}

// 修改饭点看剧领金币配置
export function updateTaskDailyFoodPointLookDrama(data) {
  return request({
    url: '/platform/taskDailyFoodPointLookDrama',
    method: 'put',
    data: data
  })
}

// 删除饭点看剧领金币配置
export function delTaskDailyFoodPointLookDrama(id) {
  return request({
    url: '/platform/taskDailyFoodPointLookDrama/' + id,
    method: 'delete'
  })
}

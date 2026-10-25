import request from '@/utils/request'

// 查询每日七日签到配置列表
export function listTaskDailySign(query) {
  return request({
    url: '/platform/taskDailySign/list',
    method: 'get',
    params: query
  })
}

// 查询每日七日签到配置详细
export function getTaskDailySign(id) {
  return request({
    url: '/platform/taskDailySign/' + id,
    method: 'get'
  })
}

// 新增每日七日签到配置
export function addTaskDailySign(data) {
  return request({
    url: '/platform/taskDailySign',
    method: 'post',
    data: data
  })
}

// 修改每日七日签到配置
export function updateTaskDailySign(data) {
  return request({
    url: '/platform/taskDailySign',
    method: 'put',
    data: data
  })
}

// 删除每日七日签到配置
export function delTaskDailySign(id) {
  return request({
    url: '/platform/taskDailySign/' + id,
    method: 'delete'
  })
}

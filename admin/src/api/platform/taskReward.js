import request from '@/utils/request'

// 查询任务列表
export function listTaskReward(query) {
  return request({
    url: '/platform/taskReward/list',
    method: 'get',
    params: query
  })
}

// 查询任务详细
export function getTaskReward(id) {
  return request({
    url: '/platform/taskReward/' + id,
    method: 'get'
  })
}

// 新增任务
export function addTaskReward(data) {
  return request({
    url: '/platform/taskReward',
    method: 'post',
    data: data
  })
}

// 修改任务
export function updateTaskReward(data) {
  return request({
    url: '/platform/taskReward',
    method: 'put',
    data: data
  })
}

// 删除任务
export function delTaskReward(id) {
  return request({
    url: '/platform/taskReward/' + id,
    method: 'delete'
  })
}

//修改任务状态（针对开启关闭）
export function modifyTaskStatusDeactivate(id) {
  return request({
    url: '/platform/taskReward/modifyTaskStatusDeactivate?id=' + id,
    method: 'get'
  })
}

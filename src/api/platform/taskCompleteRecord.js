import request from '@/utils/request'

// 查询任务完成记录列表
export function listTaskCompleteRecord(query) {
  return request({
    url: '/platform/taskCompleteRecord/list',
    method: 'get',
    params: query
  })
}

// 查询任务完成记录详细
export function getTaskCompleteRecord(id) {
  return request({
    url: '/platform/taskCompleteRecord/' + id,
    method: 'get'
  })
}

// 新增任务完成记录
export function addTaskCompleteRecord(data) {
  return request({
    url: '/platform/taskCompleteRecord',
    method: 'post',
    data: data
  })
}

// 修改任务完成记录
export function updateTaskCompleteRecord(data) {
  return request({
    url: '/platform/taskCompleteRecord',
    method: 'put',
    data: data
  })
}

// 删除任务完成记录
export function delTaskCompleteRecord(id) {
  return request({
    url: '/platform/taskCompleteRecord/' + id,
    method: 'delete'
  })
}

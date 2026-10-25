import request from '@/utils/request'

// 查询新人七日签到配置列表
export function listTaskNewPeopleSign(query) {
  return request({
    url: '/platform/taskNewPeopleSign/list',
    method: 'get',
    params: query
  })
}

// 查询新人七日签到配置详细
export function getTaskNewPeopleSign(id) {
  return request({
    url: '/platform/taskNewPeopleSign/' + id,
    method: 'get'
  })
}

// 新增新人七日签到配置
export function addTaskNewPeopleSign(data) {
  return request({
    url: '/platform/taskNewPeopleSign',
    method: 'post',
    data: data
  })
}

// 修改新人七日签到配置
export function updateTaskNewPeopleSign(data) {
  return request({
    url: '/platform/taskNewPeopleSign',
    method: 'put',
    data: data
  })
}

// 删除新人七日签到配置
export function delTaskNewPeopleSign(id) {
  return request({
    url: '/platform/taskNewPeopleSign/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询用户阶级配置列表
export function listGradeConfig(query) {
  return request({
    url: '/platform/gradeConfig/list',
    method: 'get',
    params: query
  })
}

// 查询用户阶级配置详细
export function getGradeConfig(id) {
  return request({
    url: '/platform/gradeConfig/' + id,
    method: 'get'
  })
}

// 新增用户阶级配置
export function addGradeConfig(data) {
  return request({
    url: '/platform/gradeConfig',
    method: 'post',
    data: data
  })
}

// 修改用户阶级配置
export function updateGradeConfig(data) {
  return request({
    url: '/platform/gradeConfig',
    method: 'put',
    data: data
  })
}

// 删除用户阶级配置
export function delGradeConfig(id) {
  return request({
    url: '/platform/gradeConfig/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询新人看剧送现金配置列表
export function listTaskNewPeopleLookDrama(query) {
  return request({
    url: '/platform/taskNewPeopleLookDrama/list',
    method: 'get',
    params: query
  })
}

// 查询新人看剧送现金配置详细
export function getTaskNewPeopleLookDrama(id) {
  return request({
    url: '/platform/taskNewPeopleLookDrama/' + id,
    method: 'get'
  })
}

// 新增新人看剧送现金配置
export function addTaskNewPeopleLookDrama(data) {
  return request({
    url: '/platform/taskNewPeopleLookDrama',
    method: 'post',
    data: data
  })
}

// 修改新人看剧送现金配置
export function updateTaskNewPeopleLookDrama(data) {
  return request({
    url: '/platform/taskNewPeopleLookDrama',
    method: 'put',
    data: data
  })
}

// 删除新人看剧送现金配置
export function delTaskNewPeopleLookDrama(id) {
  return request({
    url: '/platform/taskNewPeopleLookDrama/' + id,
    method: 'delete'
  })
}

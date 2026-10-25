import request from '@/utils/request'

// 查询投票选项列表
export function listOption(query) {
  return request({
    url: '/platform/option/list',
    method: 'get',
    params: query
  })
}

// 查询投票选项详细
export function getOption(id) {
  return request({
    url: '/platform/option/' + id,
    method: 'get'
  })
}

// 新增投票选项
export function addOption(data) {
  return request({
    url: '/platform/option',
    method: 'post',
    data: data
  })
}

// 修改投票选项
export function updateOption(data) {
  return request({
    url: '/platform/option',
    method: 'put',
    data: data
  })
}

// 删除投票选项
export function delOption(id) {
  return request({
    url: '/platform/option/' + id,
    method: 'delete'
  })
}

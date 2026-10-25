import request from '@/utils/request'

// 查询投票列表
export function listVote(query) {
  return request({
    url: '/platform/vote/list',
    method: 'get',
    params: query
  })
}

// 查询投票详细
export function getVote(id) {
  return request({
    url: '/platform/vote/' + id,
    method: 'get'
  })
}

// 新增投票
export function addVote(data) {
  return request({
    url: '/platform/vote',
    method: 'post',
    data: data
  })
}

// 修改投票
export function updateVote(data) {
  return request({
    url: '/platform/vote',
    method: 'put',
    data: data
  })
}

// 删除投票
export function delVote(id) {
  return request({
    url: '/platform/vote/' + id,
    method: 'delete'
  })
}

// 启动投票
export function startVote(id) {
  return request({
    url: '/platform/vote/start/' + id,
    method: 'put'
  })
}

// 结束投票
export function endVote(id) {
  return request({
    url: '/platform/vote/end/' + id,
    method: 'put'
  })
}

import request from '@/utils/request'

// 查询语音信息列表
export function listVoice(query) {
  return request({
    url: '/platform/voice/list',
    method: 'get',
    params: query
  })
}

// 查询语音信息详细
export function getVoice(id) {
  return request({
    url: '/platform/voice/' + id,
    method: 'get'
  })
}

// 新增语音信息
export function addVoice(data) {
  return request({
    url: '/platform/voice',
    method: 'post',
    data: data
  })
}

// 修改语音信息
export function updateVoice(data) {
  return request({
    url: '/platform/voice',
    method: 'put',
    data: data
  })
}

// 删除语音信息
export function delVoice(id) {
  return request({
    url: '/platform/voice/' + id,
    method: 'delete'
  })
}

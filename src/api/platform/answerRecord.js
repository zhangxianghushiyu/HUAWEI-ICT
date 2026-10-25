import request from '@/utils/request'

// 查询答题记录列表
export function listAnswerRecord(query) {
  return request({
    url: '/platform/answerRecord/list',
    method: 'get',
    params: query
  })
}

// 查询答题记录详细
export function getAnswerRecord(id) {
  return request({
    url: '/platform/answerRecord/' + id,
    method: 'get'
  })
}

// 新增答题记录
export function addAnswerRecord(data) {
  return request({
    url: '/platform/answerRecord',
    method: 'post',
    data: data
  })
}

// 修改答题记录
export function updateAnswerRecord(data) {
  return request({
    url: '/platform/answerRecord',
    method: 'put',
    data: data
  })
}

// 删除答题记录
export function delAnswerRecord(id) {
  return request({
    url: '/platform/answerRecord/' + id,
    method: 'delete'
  })
}

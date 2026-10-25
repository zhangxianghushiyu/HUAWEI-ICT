import request from '@/utils/request'

// 查询用户收藏短视频列表
export function listVideoCollect(query) {
  return request({
    url: '/platform/videoCollect/list',
    method: 'get',
    params: query
  })
}

// 查询用户收藏短视频详细
export function getVideoCollect(id) {
  return request({
    url: '/platform/videoCollect/' + id,
    method: 'get'
  })
}

// 新增用户收藏短视频
export function addVideoCollect(data) {
  return request({
    url: '/platform/videoCollect',
    method: 'post',
    data: data
  })
}

// 修改用户收藏短视频
export function updateVideoCollect(data) {
  return request({
    url: '/platform/videoCollect',
    method: 'put',
    data: data
  })
}

// 删除用户收藏短视频
export function delVideoCollect(id) {
  return request({
    url: '/platform/videoCollect/' + id,
    method: 'delete'
  })
}

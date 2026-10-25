import request from '@/utils/request'

// 查询视频点赞列表
export function listVideoLike(query) {
  return request({
    url: '/platform/videoLike/list',
    method: 'get',
    params: query
  })
}

// 查询视频点赞详细
export function getVideoLike(id) {
  return request({
    url: '/platform/videoLike/' + id,
    method: 'get'
  })
}

// 新增视频点赞
export function addVideoLike(data) {
  return request({
    url: '/platform/videoLike',
    method: 'post',
    data: data
  })
}

// 修改视频点赞
export function updateVideoLike(data) {
  return request({
    url: '/platform/videoLike',
    method: 'put',
    data: data
  })
}

// 删除视频点赞
export function delVideoLike(id) {
  return request({
    url: '/platform/videoLike/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询视频分类列表
export function listVideoCategory(query) {
  return request({
    url: '/platform/videoCategory/list',
    method: 'get',
    params: query
  })
}

// 新增视频分类
export function addVideoCategory(data) {
  return request({
    url: '/platform/videoCategory',
    method: 'post',
    data: data
  })
}

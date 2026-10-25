import request from '@/utils/request'

// 查询视频列表
export function listVideo(query) {
  return request({
    url: '/platform/video/list',
    method: 'get',
    params: query
  })
}

// 查询视频详细
export function getVideo(id) {
  return request({
    url: '/platform/video/' + id,
    method: 'get'
  })
}

// 新增视频
export function addVideo(data) {
  return request({
    url: '/platform/video',
    method: 'post',
    data: data
  })
}

// 修改视频
export function updateVideo(data) {
  return request({
    url: '/platform/video',
    method: 'put',
    data: data
  })
}

// 删除视频
export function delVideo(id) {
  return request({
    url: '/platform/video/' + id,
    method: 'delete'
  })
}
// 下架视频
export function undercarriage(id) {
  return request({
    url: '/platform/video/undercarriage/' + id,
    method: 'delete'
  })
}
// 上架视频
export function grounding(id) {
  return request({
    url: '/platform/video/grounding/' + id,
    method: 'delete'
  })
}

// 查询系列名去重列表
export function listSeriesNames(classifyId) {
  return request({
    url: '/platform/video/seriesNames',
    method: 'get',
    params: { classifyId }
  })
}

// 批量上传视频（关联系列与分类）
export function batchAddVideo(data) {
  return request({
    url: '/platform/video/batchAdd',
    method: 'post',
    data: data
  })
}

// 按分类删除视频
export function delVideoByClassify(classifyId) {
  return request({
    url: '/platform/video/delByClassify/' + classifyId,
    method: 'delete'
  })
}

// 按系列删除视频
export function delVideoBySeries(seriesName) {
  return request({
    url: '/platform/video/delBySeries/' + seriesName,
    method: 'delete'
  })
}

// 按分类上架
export function groundingByClassify(classifyId) {
  return request({
    url: '/platform/video/groundingByClassify/' + classifyId,
    method: 'delete'
  })
}

// 按分类下架
export function undercarriageByClassify(classifyId) {
  return request({
    url: '/platform/video/undercarriageByClassify/' + classifyId,
    method: 'delete'
  })
}

// 按系列上架
export function groundingBySeries(seriesName) {
  return request({
    url: '/platform/video/groundingBySeries/' + seriesName,
    method: 'delete'
  })
}

// 按系列下架
export function undercarriageBySeries(seriesName) {
  return request({
    url: '/platform/video/undercarriageBySeries/' + seriesName,
    method: 'delete'
  })
}

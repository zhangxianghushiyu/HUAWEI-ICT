import request from '@/utils/request'

// 查询曲库信息列表
export function listMusicStore(query) {
  return request({
    url: '/platform/musicStore/list',
    method: 'get',
    params: query
  })
}

// 查询曲库信息详细
export function getMusicStore(id) {
  return request({
    url: '/platform/musicStore/' + id,
    method: 'get'
  })
}

// 新增曲库信息
export function addMusicStore(data) {
  return request({
    url: '/platform/musicStore',
    method: 'post',
    data: data
  })
}

// 修改曲库信息
export function updateMusicStore(data) {
  return request({
    url: '/platform/musicStore',
    method: 'put',
    data: data
  })
}

// 删除曲库信息
export function delMusicStore(id) {
  return request({
    url: '/platform/musicStore/' + id,
    method: 'delete'
  })
}

//修改用户状态（针对停用）
export function modifyStatusDeactivate(id) {
  return request({
    url: '/platform/musicStore/modifyStatusDeactivate?id=' + id,
    method: 'get'
  })
}

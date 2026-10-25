import request from '@/utils/request'

// 查询版本信息列表
export function listClientConfig(query) {
  return request({
    url: '/platform/clientConfig/list',
    method: 'get',
    params: query
  })
}

// 查询版本信息详细
export function getClientConfig(id) {
  return request({
    url: '/platform/clientConfig/' + id,
    method: 'get'
  })
}


// 修改版本信息
export function updateClientConfig(data) {
  return request({
    url: '/platform/clientConfig',
    method: 'put',
    data: data
  })
}


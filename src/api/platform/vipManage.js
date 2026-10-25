import request from '@/utils/request'

// 查询会员列表
export function listMember () {
  return request({
    url: '/platform/vipConfig/list/',
    method: 'get',
  })
}
// 查询vip名称列表
export function listVip (type) {
  return request({
    url: '/platform/vipConfig/listNameAndId/' + type,
    method: 'get',
  })
}
// 新增vip配置
export function addVipConfig (data) {
  return request({
    url: '/platform/vipConfig',
    method: 'post',
    data: data
  })
}

// 修改vip配置
export function updateVipConfig (data) {
  return request({
    url: '/platform/vipConfig',
    method: 'put',
    data: data
  })
}

// 删除vip配置
export function delVipConfig (type) {
  return request({
    url: '/platform/vipConfig/' + type,
    method: 'delete'
  })
}

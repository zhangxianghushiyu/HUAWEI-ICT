import request from '@/utils/request'

// 查询用户列表
export function getSignature() {
  return request({
    url: '/common/getStsToken',
    method: 'get',
  })
}
import request from '@/utils/request'

// 查询商城订单列表
export function listGoodsOrder(query) {
  return request({
    url: '/platform/goodsOrder/list',
    method: 'get',
    params: query
  })
}

// 查询商城订单详细
export function getGoodsOrder(id) {
  return request({
    url: '/platform/goodsOrder/' + id,
    method: 'get'
  })
}

// 新增商城订单
export function addGoodsOrder(data) {
  return request({
    url: '/platform/goodsOrder',
    method: 'post',
    data: data
  })
}

// 修改商城订单
export function updateGoodsOrder(data) {
  return request({
    url: '/platform/goodsOrder',
    method: 'put',
    data: data
  })
}

// 删除商城订单
export function delGoodsOrder(id) {
  return request({
    url: '/platform/goodsOrder/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询商品分类列表
export function listGoodsType(query) {
  return request({
    url: '/platform/goodsType/list',
    method: 'get',
    params: query
  })
}

// 查询商品分类详细
export function getGoodsType(id) {
  return request({
    url: '/platform/goodsType/' + id,
    method: 'get'
  })
}

// 新增商品分类
export function addGoodsType(data) {
  return request({
    url: '/platform/goodsType',
    method: 'post',
    data: data
  })
}

// 修改商品分类
export function updateGoodsType(data) {
  return request({
    url: '/platform/goodsType',
    method: 'put',
    data: data
  })
}

// 删除商品分类
export function delGoodsType(id) {
  return request({
    url: '/platform/goodsType/' + id,
    method: 'delete'
  })
}

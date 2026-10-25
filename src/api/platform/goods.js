import request from '@/utils/request'

// 查询商品信息列表
export function listGoods(query) {
  return request({
    url: '/platform/goods/list',
    method: 'get',
    params: query
  })
}

// 查询商品信息详细
export function getGoods(id) {
  return request({
    url: '/platform/goods/' + id,
    method: 'get'
  })
}

// 新增商品信息
export function addGoods(data) {
  return request({
    url: '/platform/goods',
    method: 'post',
    data: data
  })
}

// 修改商品信息
export function updateGoods(data) {
  return request({
    url: '/platform/goods',
    method: 'put',
    data: data
  })
}

// 删除商品信息
export function delGoods(id) {
  return request({
    url: '/platform/goods/' + id,
    method: 'delete'
  })
}

// 下架
export function goodsUndercarriage(id) {
  return request({
    url: '/platform/goods/undercarriage/' + id,
    method: 'delete'
  })
}
// 上架
export function goodsGrounding(id) {
  return request({
    url: '/platform/goods/grounding/' + id,
    method: 'delete'
  })
}

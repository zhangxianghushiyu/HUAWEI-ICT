import request from '@/utils/request'

// 查询宝箱配置列表
export function listTreasureBox(query) {
  return request({
    url: '/platform/treasureBox/list',
    method: 'get',
    params: query
  })
}

// 查询宝箱配置详细
export function getTreasureBox(id) {
  return request({
    url: '/platform/treasureBox/' + id,
    method: 'get'
  })
}

// 新增宝箱配置
export function addTreasureBox(data) {
  return request({
    url: '/platform/treasureBox',
    method: 'post',
    data: data
  })
}

// 修改宝箱配置
export function updateTreasureBox(data) {
  return request({
    url: '/platform/treasureBox',
    method: 'put',
    data: data
  })
}

// 删除宝箱配置
export function delTreasureBox(id) {
  return request({
    url: '/platform/treasureBox/' + id,
    method: 'delete'
  })
}

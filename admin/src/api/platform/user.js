import request from '@/utils/request'

// 查询用户信息列表
export function listUser (query) {
  return request({
    url: '/platform/user/list',
    method: 'get',
    params: query
  })
}

// 查询用户信息详细
export function getUser (id) {
  return request({
    url: '/platform/user/' + id,
    method: 'get'
  })
}

// 新增用户信息
export function addUser (data) {
  return request({
    url: '/platform/user',
    method: 'post',
    data: data
  })
}

// 修改用户信息
export function updateUser (data) {
  return request({
    url: '/platform/user',
    method: 'put',
    data: data
  })
}

// 删除用户信息
export function delUser (id) {
  return request({
    url: '/platform/user/' + id,
    method: 'delete'
  })
}

//修改用户状态（针对停用）
export function modifyUserStatusDeactivate (id) {
  return request({
    url: '/platform/user/modifyUserStatusDeactivate?userId=' + id,
    method: 'get'
  })
}

//修改用户状态（针对停用）
export function setCommanderInfo (id) {
  return request({
    url: '/platform/user/setCommanderInfo?userId=' + id,
    method: 'get'
  })
}

// 我的邀请列表
export function invitationInfo (query) {
  return request({
    url: '/platform/user/invitationInfo',
    method: 'get',
    params: query
  })
}

// 重置用户密码
export function resetPassword (userId, password) {
  return request({
    url: '/platform/user/resetPassword?userId=' + userId + "&password=" + password,
    method: 'get'
  })
}

// 设置代理比例
export function setCommanderValue (userId, value) {
  return request({
    url: '/platform/user/setCommanderValue?userId=' + userId + "&value=" + value,
    method: 'get'
  })
}


// 设置有上级级比例
export function setHaveManagerRatio (userId, value) {
  return request({
    url: '/platform/user/setHaveManagerRatio?userId=' + userId + "&value=" + value,
    method: 'get'
  })
}


// 设置无上级比例
export function setNoManagerRatio (userId, value) {
  return request({
    url: '/platform/user/setNoManagerRatio?userId=' + userId + "&value=" + value,
    method: 'get'
  })
}

// 重置用户邀请码
export function resetInvitationCode (userId, invitationCode) {
  return request({
    url: '/platform/user/resetInvitationCode?userId=' + userId + "&invitationCode=" + invitationCode,
    method: 'get'
  })
}

// 金币充值
export function rechargeGold (userId, invitationCode) {
  return request({
    url: '/platform/user/resetInvitationCode?userId=' + userId + "&invitationCode=" + invitationCode,
    method: 'get'
  })
}


// 设置收益方式
export function setGetType (id, value) {
  return request({
    url: '/platform/user/setGetType?id=' + id + "&value=" + value,
    method: 'get'
  })
}

// 设置会员
export function setWithdrawal (id, value) {
  return request({
    url: '/platform/user/setWithdrawal?id=' + id + "&value=" + value,
    method: 'get'
  })
}


// 设置昵称
export function setUserNickName (id, nickName) {
  return request({
    url: '/platform/user/setUserNickName?id=' + id + "&nickName=" + nickName,
    method: 'get'
  })
}
//设置vip等级
export function resetUserVip (id, vipLevelId, vipLevelName) {
  return request({
    url: '/platform/user/setVipLevel?id=' + id + "&vipLevelId=" + vipLevelId + "&vipLevelName=" + vipLevelName,
    method: 'get'
  })
}

// 查询vip配置
export function getVipConfig () {
  return request({
    url: '/platform/vipConfig/list',
    method: 'get'
  })
}


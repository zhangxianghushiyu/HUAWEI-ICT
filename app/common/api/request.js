import {
	apiUrl
} from './config'
import utils from '@/common/utils.js'
// const {
// 	getSecureHeaders
// } = require('uni-cloud-s2s')

// silent=true：接口失败时不弹全局"操作失败" toast，也不弹 fail 提示框
// 适用于页面自己处理错误的背景请求（如点赞状态拉取），避免打扰用户
function ajax(url, params, method, needLoad = true, silent = false) {
	if (needLoad) {
		uni.showLoading({
			title: '加载中'
		})
	}
	let header = {
		'Unicloud-S2s-Authorization': "CONNECTCODE qiyunguo"
	}

	const token = uni.getStorageSync('Token')
	if (token) {
		header['token'] = `${token}`
	}

	let promise = new Promise(function(resolve, reject) {
		uni.request({
			url: `${apiUrl}${url}`,
			method: method || 'GET',
			data: params,
			header: header,
			success: (res) => {
				if (res.data.code == 401 || res.data.code == 603) {
					utils.showTipAsync(res.data.msg, '提示').then(res => {
						uni.removeStorageSync('Token')
						utils.toPage('/pages/mine/login')
					})
					return
				} else if (res.data.code == 403) {
					utils.toPage('/pages/index/index')
					utils.showTip('您没有对应权限，无法访问', '接口请求异常')
					reject({
						msg: '您没有对应权限，无法访问'
					})
					return
				} else if (res.data.code == 200) {
					if (res.code == 0) {
						reject(res.data)
					} else {
						resolve(res.data)
					}
				} else if (res.data.code == 601) {
					utils.toPage('/pages/mine/login')
				} else {
					if (!silent) {
						uni.showToast({ title: res.data.msg || '操作失败', icon: 'none', duration: 2000 })
					}
					reject(res.data)
				}
			},
			fail: (err) => {
				console.log(err);
				if (!silent) {
					utils.showTip(JSON.stringify(err), '接口请求异常')
				}
				reject(err)
			},
			complete: () => {
				uni.stopPullDownRefresh()
				uni.hideLoading()
			}
		})
	})
	return promise
}
export function request(options) {
	let params = options.data || {}
	ajax(options.url, params, options.method).then(res => {
		options.success && options.success(res)
	}, err => {
		options.fail && options.fail(res)
	}).finally(() => {
		options.complete && options.complete()
	})
}
export function get(url, params) {
	return ajax(url, params, 'get')
}
export function post(url, params) {
	return ajax(url, params, 'post')
}
export function put(url, params) {
	return ajax(url, params, 'put')
}
export function patch(url, params) {
	return ajax(url, params, 'path')
}
export function del(url, params) {
	return ajax(url, params, 'delete')
}
export function hideGet(url, params) {
	return ajax(url, params, 'get', false)
}
export function hidePost(url, params) {
	return ajax(url, params, 'post', false)
}
// 不弹 loading + 不弹全局错误 toast：适合背景增强请求
export function silentGet(url, params) {
	return ajax(url, params, 'get', false, true)
}
export function silentPost(url, params) {
	return ajax(url, params, 'post', false, true)
}

/**
 * 获取投票列表
 */
export function getVoteList() {
  return request({
    url: 'platform/vote/list',
    method: 'get'
  })
}

/**
 * 获取投票详情
 * @param {Number} voteId - 投票ID
 */
export function getVoteDetail(voteId) {
  return request({
    url: 'platform/vote/detail',
    method: 'get',
    params: {
      voteId
    }
  })
}

/**
 * 提交投票
 * @param {Object} data - 投票数据
 * @param {Number} data.voteId - 投票ID
 * @param {String} data.selectedOption - 选择的选项
 */
export function submitVote(data) {
  return request({
    url: 'platform/vote/submit',
    method: 'post',
    params: data
  })
}

/**
 * 获取我的投票记录
 */
export function getMyVoteRecords() {
  return request({
    url: 'platform/vote/myRecords',
    method: 'get'
  })
}

/**
 * 检查是否已投票
 * @param {Number} voteId - 投票ID
 */
export function hasVoted(voteId) {
  return request({
    url: 'platform/vote/hasVoted',
    method: 'get',
    params: {
      voteId
    }
  })
}
/**
 * uni-ad SDK JS 包装器
 * 用 uni-ad 官方 API 包装成与旧 TakuAds 类似的调用接口，减少对页面的侵入
 *
 * 2026-09-05 改动：
 * - 每个 adpid 全局单例，避免多页面 listener 互相覆盖
 * - 自动维护 loaded/loading/showing 状态
 * - 广告关闭/错误后自动重新 load
 * - showRewardedVideoAd 内部等待加载，超时兜底
 */

import privacyConsent from './privacy-consent.js'

function isAppPlus() {
	// 运行时判断：优先用 plus 对象，兼容自定义基座编译标记异常的情况
	return typeof plus !== 'undefined' && plus && typeof plus.os === 'object'
}

function hasPrivacyConsent() {
	try {
		return privacyConsent && privacyConsent.isPrivacyAgreed()
	} catch (e) {
		return false
	}
}

/**
 * 统一选项：兼容旧 Taku 的 placementId / scenarioId / extraData / userId
 * 实际只取 adpid（uni-ad 广告位 ID），其余字段保留但不使用
 */
function normalizeOptions(options) {
	if (typeof options === 'string') {
		return { adpid: options }
	}
	options = options || {}
	return {
		adpid: options.adpid || options.placementId || '',
		userId: options.userId || options.scenarioId || '',
		extraData: options.extraData || ''
	}
}

// 每个 adpid 保留唯一激励视频实例与全局状态
const rewardedState = {}
const SHOW_MAX_WAIT_MS = 8000

function createRewardedVideoAd(adpid) {
	if (typeof uni.createRewardedVideoAd !== 'function') return null
	try {
		return uni.createRewardedVideoAd({ adpid })
	} catch (e) {
		console.error('[UniAds] createRewardedVideoAd error:', e)
		return null
	}
}

function getRewardedState(adpid) {
	return rewardedState[adpid] || null
}

function ensureRewardedState(adpid) {
	let state = rewardedState[adpid]
	if (!state) {
		state = {
			adpid,
			ad: null,
			listener: null,
			handlers: null,
			loaded: false,
			loading: false,
			showing: false,
			loadError: null,
			showQueue: [],
			autoReloadTimer: null
		}
		rewardedState[adpid] = state
	}
	if (!state.ad) {
		state.ad = createRewardedVideoAd(adpid)
		if (state.ad) attachHandlers(state)
	}
	return state
}

function attachHandlers(state) {
	if (!state.ad || state.handlers) return
	const ad = state.ad

	state.handlers = {
		onLoad: () => {
			console.log('[UniAds] 激励视频加载成功 adpid:', state.adpid)
			state.loaded = true
			state.loading = false
			state.loadError = null
			if (state.listener && state.listener.onAdLoaded) {
				try { state.listener.onAdLoaded(state.adpid) } catch (e) {}
			}
			flushShowQueue(state)
		},
		onError: (err) => {
			console.log('[UniAds] 激励视频加载/播放失败 adpid:', state.adpid, err)
			const wasLoading = state.loading
			state.loaded = false
			state.loading = false
			state.loadError = err
			state.showing = false
			if (state.listener && state.listener.onAdFailed) {
				try { state.listener.onAdFailed({ adpid: state.adpid, placementId: state.adpid, error: err }) } catch (e) {}
			}
			rejectShowQueue(state, err)
			// 只有非展示阶段报错才自动重载；展示阶段报错由调用方处理
			if (wasLoading) scheduleAutoReload(state)
		},
		onClose: (res) => {
			console.log('[UniAds] 激励视频关闭 adpid:', state.adpid, res)
			state.showing = false
			state.loaded = false
			const isEnded = res && res.isEnded
			// 先触发 onReward，再触发 onAdClosed，保持与原 Taku 回调时序一致
			if (isEnded && state.listener && state.listener.onReward) {
				try { state.listener.onReward({ adpid: state.adpid, placementId: state.adpid, rewardAmount: 100 }) } catch (e) {}
			}
			if (state.listener && state.listener.onAdClosed) {
				try { state.listener.onAdClosed(state.adpid) } catch (e) {}
			}
			scheduleAutoReload(state)
		}
	}

	ad.onLoad(state.handlers.onLoad)
	ad.onError(state.handlers.onError)
	ad.onClose(state.handlers.onClose)
}

function scheduleAutoReload(state) {
	if (state.autoReloadTimer) clearTimeout(state.autoReloadTimer)
	state.autoReloadTimer = setTimeout(() => {
		if (!state.loaded && !state.loading && !state.showing) {
			loadRewardedAdInternal(state)
		}
	}, 3000)
}

function loadRewardedAdInternal(state) {
	if (!state.ad || state.loading || state.showing) return
	state.loading = true
	state.loaded = false
	state.loadError = null
	try {
		state.ad.load()
	} catch (e) {
		state.loading = false
		if (state.listener && state.listener.onAdFailed) {
			try { state.listener.onAdFailed({ adpid: state.adpid, placementId: state.adpid, error: e }) } catch (err) {}
		}
		scheduleAutoReload(state)
	}
}

function flushShowQueue(state) {
	while (state.showQueue.length > 0) {
		const req = state.showQueue.shift()
		if (req && req.timer) clearTimeout(req.timer)
		if (state.loaded && !state.showing) {
			doShow(state, req.listener)
		} else {
			notifyShowFailed(req.listener, { errCode: -11, errMsg: 'ad not available when flushing queue' })
		}
	}
}

function rejectShowQueue(state, err) {
	while (state.showQueue.length > 0) {
		const req = state.showQueue.shift()
		if (req && req.timer) clearTimeout(req.timer)
		notifyShowFailed(req.listener, err || { errCode: -12, errMsg: 'ad load failed' })
	}
}

function notifyShowFailed(listener, err) {
	if (listener && listener.onAdFailed) {
		try { listener.onAdFailed({ error: err }) } catch (e) {}
	}
}

function doShow(state, listener) {
	if (!state.ad || state.showing) return
	state.showing = true
	state.loaded = false
	if (listener) state.listener = listener
	try {
		if (state.listener && state.listener.onAdShow) {
			try { state.listener.onAdShow(state.adpid) } catch (e) {}
		}
		state.ad.show().catch(err => {
			console.error('[UniAds] 展示激励视频失败 adpid:', state.adpid, err)
			state.showing = false
			if (state.listener && state.listener.onAdFailed) {
				try { state.listener.onAdFailed({ adpid: state.adpid, placementId: state.adpid, error: err }) } catch (e) {}
			}
			scheduleAutoReload(state)
		})
	} catch (e) {
		state.showing = false
		if (state.listener && state.listener.onAdFailed) {
			try { state.listener.onAdFailed({ adpid: state.adpid, placementId: state.adpid, error: e }) } catch (err) {}
		}
		scheduleAutoReload(state)
	}
}

function enqueueShow(state, listener) {
	const req = { listener, timer: null }
	req.timer = setTimeout(() => {
		const idx = state.showQueue.findIndex(r => r === req)
		if (idx >= 0) state.showQueue.splice(idx, 1)
		notifyShowFailed(listener, { errCode: -10, errMsg: 'ad load timeout' })
	}, SHOW_MAX_WAIT_MS)
	state.showQueue.push(req)
}

const UniAds = {
	initialized: true,

	/**
	 * 兼容旧 TakuAds.init(appId, appKey, callback)
	 * uni-ad 不需要显式初始化，直接回调成功
	 */
	init(options, callback) {
		console.log('[UniAds] init 调用（uni-ad 无需 appId/appKey 初始化）:', options)
		if (callback) callback({ code: 0, event: 'success', data: 'uni-ad ready' })
	},

	// ========== 激励视频 ==========
	// 兼容旧方法名
	loadRewardedVideo(placementIdOrOptions, listener) {
		return this.loadRewardedVideoAd(placementIdOrOptions, listener)
	},

	showRewardedVideo(placementIdOrOptions, listener) {
		return this.showRewardedVideoAd(placementIdOrOptions, listener)
	},

	loadRewardedVideoAd(placementIdOrOptions, listener) {
		const opts = normalizeOptions(placementIdOrOptions)
		const adpid = opts.adpid

		if (!adpid) {
			console.warn('[UniAds] 激励视频 adpid 为空')
			if (listener && listener.onAdFailed) {
				try { listener.onAdFailed({ adpid, placementId: adpid, error: { errCode: -1, errMsg: 'adpid empty' } }) } catch (e) {}
			}
			return
		}

		if (!isAppPlus()) {
			console.warn('[UniAds] 激励视频仅在 APP-PLUS 环境可用')
			if (listener && listener.onAdFailed) {
				try { listener.onAdFailed({ adpid, placementId: adpid, error: { errCode: -2, errMsg: 'not app-plus' } }) } catch (e) {}
			}
			return
		}

		// 隐私合规：用户同意隐私政策前，禁止初始化广告 SDK
		if (!hasPrivacyConsent()) {
			console.warn('[UniAds] 隐私政策未同意，禁止初始化广告 SDK')
			if (listener && listener.onAdFailed) {
				try { listener.onAdFailed({ adpid, placementId: adpid, error: { errCode: -3, errMsg: 'privacy not agreed' } }) } catch (e) {}
			}
			return
		}

		if (typeof uni.createRewardedVideoAd !== 'function') {
			console.warn('[UniAds] uni.createRewardedVideoAd 不可用')
			if (listener && listener.onAdFailed) {
				try { listener.onAdFailed({ adpid, placementId: adpid, error: { errCode: -4, errMsg: 'createRewardedVideoAd not available' } }) } catch (e) {}
			}
			return
		}

		console.log('[UniAds] loadRewardedVideoAd adpid:', adpid)
		const state = ensureRewardedState(adpid)
		if (listener) state.listener = listener
		loadRewardedAdInternal(state)
	},

	showRewardedVideoAd(placementIdOrOptions, listener) {
		const opts = normalizeOptions(placementIdOrOptions)
		const adpid = opts.adpid

		if (!adpid) {
			console.warn('[UniAds] 展示激励视频 adpid 为空')
			return
		}

		const state = getRewardedState(adpid)
		if (!state || !state.ad) {
			console.warn('[UniAds] 激励视频未初始化，无法展示 adpid:', adpid)
			notifyShowFailed(listener, { errCode: -5, errMsg: 'ad not initialized' })
			return
		}

		if (listener) state.listener = listener

		console.log('[UniAds] showRewardedVideoAd adpid:', adpid, 'loaded:', state.loaded, 'loading:', state.loading, 'showing:', state.showing)

		if (state.loaded && !state.showing) {
			doShow(state, state.listener)
		} else if (state.showing) {
			console.warn('[UniAds] 激励视频正在展示中 adpid:', adpid)
		} else {
			// 未加载：触发加载并入队等待
			if (!state.loading) loadRewardedAdInternal(state)
			enqueueShow(state, state.listener)
		}
	},

	// ========== 以下类型本次未迁移，保留空实现占位，避免调用报错 ==========
	loadSplashAd() {
		console.warn('[UniAds] 开屏广告尚未迁移到 uni-ad')
	},
	showSplashAd() {
		console.warn('[UniAds] 开屏广告尚未迁移到 uni-ad')
	},
	loadInterstitialAd() {
		console.warn('[UniAds] 插屏广告尚未迁移到 uni-ad')
	},
	showInterstitialAd() {
		console.warn('[UniAds] 插屏广告尚未迁移到 uni-ad')
	},
	showBannerAd() {
		console.warn('[UniAds] Banner 广告尚未迁移到 uni-ad')
	},
	hideBannerAd() {
		console.warn('[UniAds] Banner 广告尚未迁移到 uni-ad')
	},
	showNativeAd() {
		console.warn('[UniAds] 原生广告尚未迁移到 uni-ad')
	},
	hideNativeAd() {
		console.warn('[UniAds] 原生广告尚未迁移到 uni-ad')
	}
}

// 挂载到全局，保持与现有页面代码兼容
if (typeof window !== 'undefined') {
	window.UniAds = UniAds
	console.log('[UniAds] window.UniAds 已挂载')
}

export default UniAds

/**
 * Taku 广告 SDK JS 包装器
 * 把原生插件 TakuAdBridge 包装成页面里使用的 window.TakuAds 全局对象
 * 统一所有广告类型的调用方式
 */

let TakuAdBridge = null

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

function ensureBridge() {
	if (!isAppPlus()) {
		return null
	}
	// 隐私合规（报告4.1 场景2/场景5）：用户同意隐私政策前，禁止初始化/调用广告 SDK
	if (!hasPrivacyConsent()) {
		console.warn('[TakuSDK] 隐私政策未同意，禁止调用广告 SDK')
		return null
	}
	if (!TakuAdBridge) {
		try {
			TakuAdBridge = uni.requireNativePlugin('TakuAdBridge')
			if (!TakuAdBridge) {
				console.warn('[TakuSDK] TakuAdBridge 插件未找到')
			} else {
				console.log('[TakuSDK] TakuAdBridge 插件已获取')
			}
		} catch (e) {
			console.error('[TakuSDK] 获取 TakuAdBridge 失败:', e)
		}
	}
	return TakuAdBridge
}

function wrapListener(listener) {
	return function(res) {
		if (!res || !listener) return
		const event = res.event
		const placementId = res.placementId
		const error = res.error
		// 从原生插件返回的数据中提取奖励金额
		const rewardAmount = res.rewardAmount || res.reward_amount || 1
		switch (event) {
			case 'onAdLoaded':
				if (listener.onAdLoaded) listener.onAdLoaded(placementId)
				break
			case 'onAdFailed':
				if (listener.onAdFailed) listener.onAdFailed({ placementId, error })
				break
			case 'onAdShow':
				if (listener.onAdShow) listener.onAdShow(placementId)
				break
			case 'onAdClicked':
				if (listener.onAdClicked) listener.onAdClicked(placementId)
				break
			case 'onAdClosed':
				if (listener.onAdClosed) listener.onAdClosed(placementId)
				break
			case 'onReward':
				if (listener.onReward) listener.onReward({ placementId, rewardAmount })
				break
			case 'onAdPlayStart':
				if (listener.onAdPlayStart) listener.onAdPlayStart(placementId)
				break
			case 'onAdPlayEnd':
				if (listener.onAdPlayEnd) listener.onAdPlayEnd(placementId)
				break
			default:
				console.log('[TakuSDK] unhandled event:', event, res)
			}
	}
}

let initialized = false

const TakuAds = {
	initialized: false,
	init(options, callback) {
		const bridge = ensureBridge()
		if (!bridge) {
			console.warn('[TakuSDK] TakuAdBridge 仅在 APP-PLUS 环境可用')
			if (callback) callback({ code: -1, event: 'fail', data: 'not app-plus' })
			return
		}
		if (initialized) {
			if (callback) callback({ code: 0, event: 'success', data: 'already initialized' })
			return
		}
		bridge.init(options, (res) => {
			if (res && res.code === 0) {
				initialized = true
				TakuAds.initialized = true
			}
			if (callback) callback(res)
		})
	},

	// ========== 开屏广告 ==========
	loadSplashAd(placementId, listener) {
		const bridge = ensureBridge()
		if (!bridge) return
		bridge.loadSplashAd({ placementId }, wrapListener(listener))
	},

	showSplashAd(placementId) {
		const bridge = ensureBridge()
		if (!bridge) return
		bridge.showSplashAd({ placementId }, function(res) {
			console.log('[TakuSDK] showSplashAd result:', res)
		})
	},

	// ========== 插屏广告 ==========
	loadInterstitialAd(placementIdOrOptions, listener) {
		const bridge = ensureBridge()
		if (!bridge) return
		let options = placementIdOrOptions
		if (typeof placementIdOrOptions === 'string') {
			options = { placementId: placementIdOrOptions }
		}
		console.log('[TakuSDK] loadInterstitialAd options:', JSON.stringify(options))
		bridge.loadInterstitialAd(options, wrapListener(listener))
	},

	showInterstitialAd(placementIdOrOptions) {
		const bridge = ensureBridge()
		if (!bridge) return
		let options = placementIdOrOptions
		if (typeof placementIdOrOptions === 'string') {
			options = { placementId: placementIdOrOptions }
		}
		bridge.showInterstitialAd(options, function(res) {
			console.log('[TakuSDK] showInterstitialAd result:', res)
		})
	},

	// ========== 激励视频 ==========
	// 兼容旧方法名
	loadRewardedVideo(placementId, listener) {
		return this.loadRewardedVideoAd(placementId, listener)
	},

	showRewardedVideo(placementId) {
		return this.showRewardedVideoAd(placementId)
	},

	loadRewardedVideoAd(placementIdOrOptions, listener) {
		const bridge = ensureBridge()
		if (!bridge) return
		let options = placementIdOrOptions
		if (typeof placementIdOrOptions === 'string') {
			options = { placementId: placementIdOrOptions }
		}
		console.log('[TakuSDK] loadRewardedVideoAd options:', JSON.stringify(options))
		bridge.loadRewardedVideoAd(options, wrapListener(listener))
	},

	showRewardedVideoAd(placementIdOrOptions, listener) {
		const bridge = ensureBridge()
		if (!bridge) return
		let options = placementIdOrOptions
		if (typeof placementIdOrOptions === 'string') {
			options = { placementId: placementIdOrOptions }
		}
		bridge.showRewardedVideoAd(options, wrapListener(listener))
	},

	// ========== Banner 广告 ==========
	showBannerAd(options, listener) {
		const bridge = ensureBridge()
		if (!bridge) return
		bridge.showBannerAd(options, wrapListener(listener))
	},

	hideBannerAd(positionKey) {
		const bridge = ensureBridge()
		if (!bridge) return
		bridge.hideBannerAd({ positionKey }, function(res) {
			console.log('[TakuSDK] hideBannerAd result:', res)
		})
	},

	// ========== 原生广告 ==========
	showNativeAd(options, listener) {
		const bridge = ensureBridge()
		if (!bridge) return
		bridge.showNativeAd(options, wrapListener(listener))
	},

	hideNativeAd(positionKey) {
		const bridge = ensureBridge()
		if (!bridge) return
		bridge.hideNativeAd({ positionKey }, function(res) {
			console.log('[TakuSDK] hideNativeAd result:', res)
		})
	}
}

// 挂载到全局，保持与现有页面代码兼容
if (typeof window !== 'undefined') {
	window.TakuAds = TakuAds
	console.log('[TakuSDK] window.TakuAds 已挂载')
}

export default TakuAds
/**
 * 隐私政策同意标志（隐私合规整改：报告4.1 场景2/场景5）
 *
 * 合规要求：用户点击隐私政策「同意」前，APP 及第三方广告 SDK 不得初始化、
 * 不得调用系统 API 收集设备信息。本模块提供统一的同意状态读写入口：
 * - isPrivacyAgreed()   查询用户是否已同意隐私政策
 * - setPrivacyAgreed()  用户点击「同意」后持久化标志
 *
 * 标志存储键：privacyPolicyAgreed（true 表示已同意）
 */

var STORAGE_KEY = 'privacyPolicyAgreed'

function getStorageSyncSafe(key) {
	try {
		if (typeof uni !== 'undefined' && uni && typeof uni.getStorageSync === 'function') {
			return uni.getStorageSync(key)
		}
	} catch (e) {
		console.warn('[PrivacyConsent] getStorageSync 异常:', e)
	}
	return ''
}

function setStorageSyncSafe(key, value) {
	try {
		if (typeof uni !== 'undefined' && uni && typeof uni.setStorageSync === 'function') {
			uni.setStorageSync(key, value)
			return true
		}
	} catch (e) {
		console.warn('[PrivacyConsent] setStorageSync 异常:', e)
	}
	return false
}

/**
 * 用户是否已同意隐私政策
 * @returns {boolean} true=已同意（可初始化广告SDK），false=未同意
 */
function isPrivacyAgreed() {
	// 优先读 DCloud 原生隐私标志（androidPrivacy.json template 模式原生框设置）
	try {
		if (typeof plus !== 'undefined' && plus && plus.runtime &&
			typeof plus.runtime.isAgreePrivacy === 'function') {
			return plus.runtime.isAgreePrivacy()
		}
	} catch (e) {
		console.warn('[PrivacyConsent] isAgreePrivacy 异常:', e)
	}
	// fallback 到本地标志（兼容非 APP 环境 / 历史数据）
	return getStorageSyncSafe(STORAGE_KEY) === true
}

/**
 * 用户点击「同意」后调用：持久化同意标志
 * @returns {boolean} 是否写入成功
 */
function setPrivacyAgreed() {
	// 双写：同步设置 DCloud 原生隐私标志 + 本地 storage 标志
	try {
		if (typeof plus !== 'undefined' && plus && plus.runtime &&
			typeof plus.runtime.agreePrivacy === 'function') {
			plus.runtime.agreePrivacy()
		}
	} catch (e) {
		console.warn('[PrivacyConsent] agreePrivacy 异常:', e)
	}
	return setStorageSyncSafe(STORAGE_KEY, true)
}

module.exports = {
	STORAGE_KEY: STORAGE_KEY,
	isPrivacyAgreed: isPrivacyAgreed,
	setPrivacyAgreed: setPrivacyAgreed
}

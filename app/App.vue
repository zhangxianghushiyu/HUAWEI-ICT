<template>
	<view>
		<!-- 隐私政策/用户协议首次启动弹窗 -->
		<view v-if="showPrivacyPopup" class="privacy-popup">
			<view class="privacy-mask"></view>
			<view class="privacy-box">
				<view class="privacy-title">用户协议与隐私政策</view>
				<view class="privacy-content">
					欢迎您使用红樱桃！我们非常重视您的个人信息和隐私保护。在您使用我们的服务前，请您认真阅读并充分理解
					<text class="privacy-link" @click="openPolicy('1')">《用户协议》</text>
					和
					<text class="privacy-link" @click="openPolicy('2')">《隐私政策》</text>
					。如您同意，请点击"同意"开始使用我们的服务。
				</view>
				<view class="privacy-btns">
					<view class="privacy-btn privacy-btn-cancel" @click="onPrivacyDisagree">不同意</view>
					<view class="privacy-btn privacy-btn-confirm" @click="onPrivacyAgree">同意</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
import utils from './common/utils'
import '@/common/taku-sdk.js'
import '@/common/uniad-sdk.js'
import privacyConsent from './common/privacy-consent.js'
import {
	mapState,
	mapGetters,
	mapActions,
	mapMutations
} from 'vuex'

export default {
    data() {
        return {
            splashAdShown: false,      // 防止重复展示
            splashAdDismissed: false,  // 防止重复跳转
            splashTimer: null,         // 兜底定时器
            showPrivacyPopup: false    // 首次启动隐私协议弹窗
        }
    },
	globalData: {
		seeVodIndex: null,
		vodinfo: {},
		seeVodList: [],
		isFirstTopBanner: true,
		isFirstBottomBanner: true
	},
	onLaunch: function() {
		console.log('App Launch')
		// #ifdef APP-PLUS
		// 隐私合规（报告4.1 场景2/场景5）：用户同意隐私政策前，禁止初始化任何广告 SDK
		if (!privacyConsent.isPrivacyAgreed()) {
			console.log('[Privacy] 用户尚未同意隐私政策，展示协议弹窗')
			this.showPrivacyPopup = true
			return
		}
		// 兜底：无论广告如何，5秒后必须进入主界面
		this.splashTimer = setTimeout(() => {
			this.enterMainPage()
		}, 5000)
		// 延迟初始化，等待 window 对象就绪
		this.$nextTick(() => {
			this.initTakuSplashAd()
		})
		// #endif
	},
	async onShow() {
		console.log('App Show')
		this.$store.dispatch('asyncSetShow', true);
		console.log('[App] onShow after dispatch, $store.state.isShow:', this.$store.state.isShow);
		
		if (this.$store.state.isShow == false) {
			uni.setTabBarItem({
				index: 1,
				text: '公告栏'
			});
		}
		const token = uni.getStorageSync('Token')
		if (token) {
			this.$api.getInfo().then(res => {
				uni.setStorageSync('UserInfo', res.data)
			})
		}
		//获取线上版本信息
		this.$api.getVersion().then(res => {
			uni.setStorageSync('Version', res.data)
		})
		if (uni.getStorageSync('wxChatPage')) return;
	},
	onHide: function() {
		// 加判断：只有安卓APP环境才调用
		// #ifdef APP-PLUS
		if (typeof rh_android !== 'undefined' && rh_android.pauseMusic) {
			rh_android.pauseMusic()
		}
		// #endif
		console.log('App Hide')	
	},
	methods: {
		/**
		 * 初始化并展示 Taku 开屏广告
		 * 使用 uni.requireNativePlugin 直接调用原生插件
		 */
		initTakuSplashAd() {
			// #ifdef APP-PLUS
			// 隐私合规兜底：未同意时拒绝初始化（正常情况下 onLaunch 已拦截）
			if (!privacyConsent.isPrivacyAgreed()) {
				console.warn('[Taku] 隐私政策未同意，跳过开屏广告初始化')
				this.enterMainPage()
				return
			}
			// 直接使用原生插件，不依赖 window.TakuAds
			const TakuAd = uni.requireNativePlugin('TakuAdBridge')
			if (!TakuAd) {
				console.warn('[Taku] TakuAdBridge 插件未找到，跳过开屏广告')
				this.enterMainPage()
				return
			}

			console.log('[Taku] 初始化 SDK...')
			TakuAd.init({
				appId: 'a6a2768f6227eb',
				appKey: 'a6af687f456b60b76907f9a9b8cbbc73f'
			}, (res) => {
				console.log('[Taku] init result:', res)
				if (res && res.code === 0) {
					this.loadAndShowSplashAd(TakuAd)
				} else {
					console.warn('[Taku] init failed:', res)
					this.enterMainPage()
				}
			})
			// #endif
		},

		loadAndShowSplashAd(TakuAd) {
			const placementId = 'b6a2769587c201'
			console.log('[Taku] 加载开屏广告:', placementId)
			
			TakuAd.loadSplashAd({
				placementId: placementId
			}, (res) => {
				const event = res && res.event
				console.log('[Taku] splash event:', event)
				
				if (event === 'onAdLoaded') {
					// 广告加载成功，展示
					if (!this.splashAdShown && !this.splashAdDismissed) {
						this.splashAdShown = true
						TakuAd.showSplashAd({ placementId })
					}
				} else if (event === 'onAdLoadTimeout' || event === 'onNoAdError' || event === 'fail') {
					// 加载失败/超时，进入主界面
					console.warn('[Taku] splash load failed:', event)
					this.enterMainPage()
				}
			})
		},

		enterMainPage() {
			if (this.splashAdDismissed) return

			this.splashAdDismissed = true
			if (this.splashTimer) {
				clearTimeout(this.splashTimer)
				this.splashTimer = null
			}

			console.log('[App] 进入主界面')
		},

		/**
		 * 打开协议/政策详情页
		 * @param {String} type 1=用户协议, 2=隐私政策
		 */
		openPolicy(type) {
			uni.navigateTo({
				url: `/pages/mine/policy?type=${type}`
			})
		},

		/**
		 * 用户点击同意隐私政策/用户协议
		 */
		onPrivacyAgree() {
			privacyConsent.setPrivacyAgreed()
			this.showPrivacyPopup = false
			// 继续开屏广告初始化流程
			this.splashTimer = setTimeout(() => {
				this.enterMainPage()
			}, 5000)
			this.$nextTick(() => {
				this.initTakuSplashAd()
			})
		},

		/**
		 * 用户点击不同意：退出应用
		 */
		onPrivacyDisagree() {
			// #ifdef APP-PLUS
			if (typeof plus !== 'undefined' && plus.runtime && typeof plus.runtime.quit === 'function') {
				plus.runtime.quit()
			} else {
				uni.showToast({ title: '需同意协议后方可使用', icon: 'none' })
			}
			// #endif
			// #ifndef APP-PLUS
			uni.showToast({ title: '需同意协议后方可使用', icon: 'none' })
			// #endif
		}
	}
}
</script>

<style lang="scss">
	@keyframes progross1 {
		to {
			transform: rotate(360deg);
		}
	}

	@keyframes progross2 {
		to {
			transform: rotate(0deg);
		}
	}

	.uni-tabbar .uni-tabbar__icon {
		// width: 99rpx !important;
		// height: 48rpx !important;
	}

	/* #ifndef APP-PLUS-NVUE */
	@import '@/static/css/common.scss';

	::-webkit-scrollbar {
		display: none;
		width: 0 !important;
		height: 0 !important;
		-webkit-appearance: none;
		background: transparent;
	}

	/* 首次启动隐私政策/用户协议弹窗 */
	.privacy-popup {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 10000;
	}

	.privacy-mask {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.6);
	}

	.privacy-box {
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		width: 600rpx;
		background-color: #ffffff;
		border-radius: 24rpx;
		padding: 40rpx;
	}

	.privacy-title {
		font-size: 36rpx;
		font-weight: bold;
		color: #333333;
		text-align: center;
		margin-bottom: 30rpx;
	}

	.privacy-content {
		font-size: 28rpx;
		color: #666666;
		line-height: 1.6;
		margin-bottom: 40rpx;
	}

	.privacy-link {
		color: #FE6100;
	}

	.privacy-btns {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.privacy-btn {
		width: 260rpx;
		height: 80rpx;
		line-height: 80rpx;
		text-align: center;
		border-radius: 40rpx;
		font-size: 30rpx;
	}

	.privacy-btn-cancel {
		color: #999999;
		background-color: #f5f5f5;
	}

	.privacy-btn-confirm {
		color: #ffffff;
		background-color: #FE6100;
	}

	/* #endif */
</style>
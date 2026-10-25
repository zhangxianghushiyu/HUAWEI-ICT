<template>
	<view class="player-page">
		<dy-player-app v-if="videoList.length > 0 && isApp" ref="playerRef" :list="videoList" :start-index="startIndex"
			:is-drama="isDrama" :info="dramaInfo" :auto-play="autoPlay" :loop-play="loopPlay"
			:auto-next="false"
			:show-back="showBack" :show-search="showSearch"
			:show-red-packet="showRedPacket" :red-packet-times="redPacketTimes"
			:show-gold-unlock="showGoldUnlock" :unlock-gold-cost="unlockGoldCost" :unlock-ad-count="5" @change="onVideoChange"
			@ended="onEnded"
			@like="onLike" @collect="onCollect" @share="onShare" @loadMore="onLoadMore" @refresh="onRefresh"
			@unlock="onUnlock" @lockHit="onLockHit"
			@redPacketEnd="onRedPacketEnd" @redPacketReceive="onRedPacketReceive"
			@back="onBack" @search="goToSearch" @taskTime="onTaskTime" @longpress="onLongPress" />

		<dy-player v-else-if="videoList.length > 0" ref="playerRef" :list="videoList" :start-index="startIndex"
			:is-drama="isDrama" :info="dramaInfo" :auto-play="autoPlay" :loop-play="loopPlay"
			:auto-next="false"
			:show-back="showBack" :show-search="showSearch"
			:show-red-packet="showRedPacket" :red-packet-times="redPacketTimes"
			:show-gold-unlock="showGoldUnlock" :unlock-gold-cost="unlockGoldCost" :unlock-ad-count="5" @change="onVideoChange"
			@ended="onEnded"
			@like="onLike" @collect="onCollect" @share="onShare" @loadMore="onLoadMore" @refresh="onRefresh"
			@unlock="onUnlock" @lockHit="onLockHit"
			@redPacketEnd="onRedPacketEnd" @redPacketReceive="onRedPacketReceive"
			@back="onBack" @search="goToSearch" @taskTime="onTaskTime" @longpress="onLongPress" />

		<!-- 金币奖励弹窗 -->
		<uv-popup ref="goldPopup" :closeOnClickOverlay="false" bgColor="none">
			<view class="gold-popup">
				<image class="gold-popup-image" src="@/static/images/change/CollectGoldCoins2.png" mode="scaleToFill" />
				<image class="gold-popup-close" src="~@/static/images/Close.png" mode="widthFix" @click="closeGoldPopup" />
				<view class="gold-popup-num">{{ goldNum }} 金币</view>
			</view>
		</uv-popup>
	</view>
</template>

<script>
	// #ifdef APP-PLUS
	function getTakuAds() {
		return (typeof window !== 'undefined' && window.TakuAds) ? window.TakuAds : null
	}
	// #endif

	import dyPlayer from '@/components/dy-player/dy-player.vue'
	import dyPlayerApp from '@/components/dy-player/dy-player-app.vue'
	import playerMixin from './mixin.js'

	export default {
		type: 'drama',
		mixins: [playerMixin],
		components: {
			dyPlayer,
			dyPlayerApp
		},
		data() {
			return {
				// 短剧解锁广告使用激励视频 adpid（与领金币一致）
				rewardedAdpid: '1819020042',
				// 间隔广告：每5集展示插屏
				interstitialPlacementId: 'b6a27695ae4bad',
				interstitialLoaded: false,

				// 激励视频状态管理
				isRewardedAdLoading: false,

				// 计时器引用（用于清理）
				adRetryTimer: null,
				adLoadTimer: null,

				// 间隔广告状态
				adPendingNextIndex: -1,
				isAdShowing: false,

				// 金币弹窗
				goldNum: 0,

				// 用户状态
				userInfo: {}
			}
		},
		onLoad() {
			this.userInfo = uni.getStorageSync('UserInfo') || {}
		},
		onShow() {
			// 调用 mixin 的 onShow（获取钱包信息等）
			if (playerMixin && playerMixin.onShow) {
				playerMixin.onShow.call(this)
			}
			// #ifdef APP-PLUS
			// 预加载插屏广告，提高第5集触发时的展示成功率
			this.preloadInterstitial()
			// #endif
		},
		onHide() {
			this.clearAdTimers()
		},
		onUnload() {
			this.clearAdTimers()
		},
		methods: {
			// 覆盖 mixin 的 loadDramaList：只加载目标剧集，不混入随机视频，保证选集顺序正确
			loadDramaList() {
				const globalList = getApp().globalData.seeVodList || []
				const globalInfo = getApp().globalData.vodinfo || {}

				if (!this.targetId && globalList.length > 0) {
					this.dramaInfo = globalInfo
					this.videoList = this.formatDramaList(globalList)
					this.startIndex = getApp().globalData.seeVodIndex || this.startIndex
					getApp().globalData.seeVodList = this.videoList
					getApp().globalData.vodinfo = this.dramaInfo
					this.initPlayerRef()
					return
				}

				if (!this.targetId) {
					uni.showToast({ title: '参数错误', icon: 'none' })
					return
				}

				uni.showLoading({ title: '加载中' })
				this.$api.getDetailsList({ dramaId: this.targetId }).then(res => {
					uni.hideLoading()
					const dataList = res.data || []
					const dramaVideos = this.formatDramaList(dataList)
					this.videoList = dramaVideos
					this.dramaInfo = globalInfo.dramaId === this.targetId ? globalInfo : (dramaVideos[0] || {})
					getApp().globalData.seeVodList = dramaVideos
					getApp().globalData.vodinfo = this.dramaInfo
					this.initPlayerRef()
				}).catch(err => {
					uni.hideLoading()
					console.error('[drama] loadDramaList error:', err)
					uni.showToast({ title: '加载失败', icon: 'none' })
				})
			},

			// ================== 间隔广告：每5集展示一次 ==================

			/**
			 * 视频播放结束事件（autoNext=false，需手动控制下一集）
			 */
			onEnded(e) {
				const idx = e.index != null ? e.index : this.$refs.playerRef && this.$refs.playerRef.currentIndex
				if (idx == null) return
				const total = this.videoList.length

				console.log('[drama] ended, index:', idx, 'total:', total)

				// 已是最后一集，不处理
				if (idx >= total - 1) return

				// 每5集（index 4, 9, 14...）展示一次广告
				if ((idx + 1) % 5 === 0) {
					console.log('[drama] 展示间隔广告, index:', idx)
					this.adPendingNextIndex = idx + 1
					this.showIntervalAd()
				} else {
					// 直接播下一集
					this.goNext()
				}
			},

			/**
			 * 展示间隔插屏广告（应用内展示，关闭后继续播放）
			 */
			showIntervalAd() {
				if (this.isAdShowing) return
				this.isAdShowing = true

				// #ifdef APP-PLUS
				const Taku = getTakuAds()
				if (!Taku || !Taku.loadInterstitialAd || !Taku.showInterstitialAd) {
					console.warn('[drama] Taku 插屏广告不可用，直接续播')
					this.isAdShowing = false
					this.goNext()
					return
				}
				// 安全定时器：10秒超时自动重置（防止广告未正常关闭卡住）
				const safetyTimer = setTimeout(() => {
					if (this.isAdShowing) {
						console.log('[drama] 插屏广告超时，重置状态')
						this.isAdShowing = false
						this.adPendingNextIndex = -1
						this.interstitialLoaded = false
						// 广告未完成，尝试继续播放下一集
						this.goNext()
					}
				}, 10000)
				const userInfo = this.userInfo || uni.getStorageSync('UserInfo') || {}
				const options = {
					placementId: this.interstitialPlacementId,
					userId: String(userInfo.id || '')
				}
				const onFinish = () => {
					clearTimeout(safetyTimer)
					this.isAdShowing = false
					this.interstitialLoaded = false
					this.preloadInterstitial()
					this.handleIntervalAdReward()
				}
				if (this.interstitialLoaded) {
					Taku.showInterstitialAd(options, (res) => {
						console.log('[drama] showInterstitialAd result:', res)
						onFinish()
					})
					return
				}
				Taku.loadInterstitialAd(options, {
					onAdLoaded: () => {
						Taku.showInterstitialAd(options, (res) => {
							console.log('[drama] showInterstitialAd result:', res)
							onFinish()
						})
					},
					onAdFailed: () => {
						clearTimeout(safetyTimer)
						this.isAdShowing = false
						this.interstitialLoaded = false
						this.goNext()
					},
					onAdClosed: () => {
						onFinish()
					}
				})
				// #endif

				// #ifndef APP-PLUS
				// 非App端：直接跳过，继续播放
				this.isAdShowing = false
				this.goNext()
				// #endif
			},

			/**
			 * 预加载插屏广告，提高第5集触发时的展示成功率
			 */
			preloadInterstitial() {
				// #ifdef APP-PLUS
				const Taku = getTakuAds()
				if (!Taku || !Taku.loadInterstitialAd) return
				const userInfo = this.userInfo || uni.getStorageSync('UserInfo') || {}
				const options = {
					placementId: this.interstitialPlacementId,
					userId: String(userInfo.id || '')
				}
				Taku.loadInterstitialAd(options, {
					onAdLoaded: () => {
						this.interstitialLoaded = true
					},
					onAdFailed: () => {
						this.interstitialLoaded = false
					}
				})
				// #endif
			},

			/**
			 * 间隔广告看完奖励处理
			 */
			handleIntervalAdReward(res) {
				console.log('[drama] 间隔广告看完，继续播放')
				// 刷新剧集列表（解锁后续集），完成后播下一集
				const id = this.targetId || (this.dramaInfo && this.dramaInfo.dramaId)
				if (!id) {
					this.goNext()
					return
				}
				this.$api.getDetailsList({ dramaId: id }).then(result => {
					const dataList = result.data || []
					const formatted = this.formatDramaList(dataList)
					this.videoList = formatted
					getApp().globalData.seeVodList = formatted
					if (this.$refs.playerRef && typeof this.$refs.playerRef.refreshList === 'function') {
						this.$refs.playerRef.refreshList(formatted, true)
					}
					this.$nextTick(() => {
						this.goNext()
					})
				}).catch(() => {
					this.goNext()
				})
			},

			/**
			 * 调用播放器播下一集
			 */
			goNext() {
				if (this.$refs.playerRef && typeof this.$refs.playerRef.goNext === 'function') {
					this.$refs.playerRef.goNext()
				}
			},

			/**
			 * 处理解锁奖励
			 */
			handleUnlockReward(res) {
				// 显示双倍金币奖励弹窗
				this.goldNum = (res && res.rewardAmount ? res.rewardAmount : 100) * 2
				if (this.$refs.goldPopup) {
					this.$refs.goldPopup.open('center')
				}
			},

			/**
			 * 调用后端发放广告金币奖励（双倍：调用两次，间隔 2.5s 避过 @RepeatSubmit）
			 */
			claimAdReward() {
				console.log('[短剧] 开始发放双倍金币奖励')
				// 第一次 +100
				this.$api.adRewardToCoin().then(res => {
					console.log('[短剧] 第一次广告奖励接口返回:', res)
					if (res.code === 200) {
						// 2.5s 后第二次 +100
						setTimeout(() => {
							this.$api.adRewardToCoin().then(res2 => {
								console.log('[短剧] 第二次广告奖励接口返回:', res2)
								if (res2.code === 200) {
									uni.showToast({ title: '已获得双倍金币奖励', icon: 'success', duration: 1500 })
									this.getWalletInfo()
								}
							}).catch(err2 => {
								console.error('[短剧] 第二次广告奖励接口失败:', err2)
							})
						}, 2500)
					}
				}).catch(err => {
					console.error('[短剧] 第一次广告奖励接口失败:', err)
					uni.showToast({ title: '奖励发放失败，请稍后重试', icon: 'none', duration: 2000 })
				})
			},

			// ========== 视频切换 ==========

			onVideoChange(e) {
				// 调用 mixin 中的公共逻辑
				if (playerMixin && playerMixin.methods && typeof playerMixin.methods.onVideoChange === 'function') {
					playerMixin.methods.onVideoChange.call(this, e)
				}

				const currentIndex = e.index || 0
				console.log('[drama] videoChange, index:', currentIndex)
			},

			/**
			 * 播放器触发锁定（选集面板点击锁定集 / 滑到锁定集）：弹激励视频，看完批量解锁
			 */
			onLockHit(e) {
				console.log('[drama] lockHit:', e)
				const item = e && e.item
				if (!item || !item.dramaDetailsId) return
				this.unlockByAd(item)
			},

			// ========== 计时器清理 ==========

			clearAdTimers() {
				if (this.adRetryTimer) {
					clearTimeout(this.adRetryTimer)
					this.adRetryTimer = null
				}
				if (this.adLoadTimer) {
					clearTimeout(this.adLoadTimer)
					this.adLoadTimer = null
				}
			},

			// ========== 金币弹窗 ==========

			showGoldPopup() {
				this.$refs.goldPopup.open()
			},

			closeGoldPopup() {
				this.$refs.goldPopup.close()
				this.goldNum = 0
			}
		}
	}
</script>

<style lang="scss" scoped>
	.player-page {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: #000000;
		z-index: 1;
	}

	.gold-popup {
		position: relative;
		width: 600rpx;
		height: 650rpx;
	}

	.gold-popup-image {
		width: 600rpx;
		height: 650rpx;
	}

	.gold-popup-close {
		position: absolute;
		top: 0;
		right: 0;
		width: 50rpx;
	}

	.gold-popup-num {
		position: absolute;
		top: 180rpx;
		left: 0;
		width: 100%;
		text-align: center;
		font-size: 45rpx;
		font-weight: bold;
		color: #FFF3BE;
	}
</style>

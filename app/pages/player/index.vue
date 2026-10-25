<template>
	<view class="player-page">
		<!-- 视频播放器区域 - 留出底部导航栏空间 -->
		<view class="player-wrapper">
			<dy-player-app v-if="videoList.length > 0 && isApp" ref="playerRef" :list="videoList" :start-index="startIndex"
				:is-drama="isDrama" :info="dramaInfo" :auto-play="autoPlay" :loop-play="loopPlay" :show-back="showBack"
				:show-search="showSearch" :show-red-packet="false" :show-share="false"
				:show-ent-red-packet="true"
				:ent-red-packet-ready="redPacketReady"
				:ent-red-packet-progress="redPacketProgress"
				:ent-red-packet-remain="redPacketRemainSeconds"
				:show-gold-unlock="showGoldUnlock" :unlock-gold-cost="unlockGoldCost" @change="onVideoChange"
				@timeupdate="onPlayerTimeUpdate" @ended="onPlayerEnded"
				@like="onLike" @collect="onCollect" @share="onShare" @loadMore="onLoadMore" @refresh="onRefresh"
				@unlock="onUnlock" @entRedPacketTap="onRedPacketTap"
				@back="onBack" @search="goToSearch" @taskTime="onTaskTime" @longpress="onLongPress" />

			<dy-player v-if="videoList.length > 0 && !isApp" ref="playerRef" :list="videoList" :start-index="startIndex"
				:is-drama="isDrama" :info="dramaInfo" :auto-play="autoPlay" :loop-play="loopPlay" :show-back="showBack"
				:show-search="showSearch" :show-red-packet="false" :show-share="false"
				:show-gold-unlock="showGoldUnlock" :unlock-gold-cost="unlockGoldCost" @change="onVideoChange"
				@timeupdate="onPlayerTimeUpdate" @ended="onPlayerEnded"
				@like="onLike" @collect="onCollect" @share="onShare" @loadMore="onLoadMore" @refresh="onRefresh"
				@unlock="onUnlock" @redPacketEnd="onRedPacketEnd" @redPacketReceive="onRedPacketReceive"
				@back="onBack" @search="goToSearch" @taskTime="onTaskTime" @longpress="onLongPress">
			</dy-player>
		</view>

		<!-- 金币奖励弹窗 -->
		<uv-popup ref="goldPopup" :closeOnClickOverlay="false" bgColor="none">
			<view class="gold-popup">
				<image class="gold-popup-image" src="@/static/images/change/CollectGoldCoins2.png" mode="scaleToFill" />
				<image class="gold-popup-close" src="~@/static/images/Close.png" mode="widthFix" @click="closeGoldPopup" />
				<view class="gold-popup-num">{{ goldNum }} 金币</view>
			</view>
		</uv-popup>

		<!-- #ifndef APP-PLUS -->
		<!-- 非 App 端：右侧悬浮红包 -->
		<view class="ent-red-packet" :class="{ ready: redPacketReady }" @tap.stop="onRedPacketTap">
			<view class="ent-red-packet-ring-bg"></view>
			<view class="ent-red-packet-progress" :style="progressStyle"></view>
			<view class="ent-red-packet-inner">
				<image class="ent-red-packet-img" src="/static/6.png" mode="aspectFit" />
				<text v-if="!redPacketReady" class="ent-red-packet-count">{{ redPacketRemainSeconds }}</text>
				<text v-else class="ent-red-packet-text">领金币</text>
			</view>
		</view>
		<!-- #endif -->
	</view>
</template>

<script>
	// #ifdef APP-PLUS
	// 直接引入 uni-ad SDK 包装器，不依赖 window 全局挂载时机
	import UniAds from '@/common/uniad-sdk.js'

	function getUniAds() {
		return (UniAds && UniAds.loadRewardedVideoAd) ? UniAds : null
	}
	// #endif

	import dyPlayer from '@/components/dy-player/dy-player.vue'
	import dyPlayerApp from '@/components/dy-player/dy-player-app.vue'
	import playerMixin from './mixin.js'

	export default {
		type: 'video',
		mixins: [playerMixin],
		components: {
			dyPlayer,
			dyPlayerApp
		},
		data() {
			return {
				// ===== uni-ad 激励视频广告配置 =====
				rewardedAdpid: '1819020042',
				isAdShowing: false,
				adDidShow: false,
				adDidReward: false,
				rewardedAdPurpose: '',

				// ===== 全屏激励视频广告（每5个视频触发） =====
				fullScreenAdVideoCount: 0,

				// ===== 右侧悬浮红包 =====
                // 倒计时
				redPacketTargetSeconds: 3,
				redPacketTotalSeconds: 0,
				redPacketCurrentSeconds: 0,
				redPacketLastCurrentTime: 0,
				redPacketLastTimeMs: 0,
				redPacketCurrentVideoId: null,
				redPacketReady: false,
				redPacketProgress: 0,
				redPacketTodayCount: 0,
				redPacketMaxDaily: 10,

				// ===== 金币奖励弹窗 =====
				goldNum: 0,

				// 金币奖励查询状态
				rewardCheckBaseGold: 0,
				rewardCheckGot: false,

				// 进度条样式
				redPacketRemainSeconds: 0,

				userInfo: {}
			}
		},
		computed: {
			progressStyle() {
				return {
					'--progress': this.redPacketProgress
				}
			}
		},
		onLoad(e) {
			this.userInfo = uni.getStorageSync('UserInfo') || {}
			this.redPacketTodayCount = Number(uni.getStorageSync('ent_red_packet_today_count') || 0)
			const today = new Date().toDateString()
			if (uni.getStorageSync('ent_red_packet_today_date') !== today) {
				this.redPacketTodayCount = 0
				uni.setStorageSync('ent_red_packet_today_date', today)
			}
			// 初始化红包倒计时
			this.redPacketRemainSeconds = this.redPacketTargetSeconds
		},
		onShow() {
			// #ifdef APP-PLUS
			// 页面显示时恢复红包原生浮层
			if (this.$refs.playerRef && typeof this.$refs.playerRef.showRedPacketOverlay === 'function') {
				this.$refs.playerRef.showRedPacketOverlay()
			}
			// 预加载激励视频广告
			this.initTakuAds()
			// #endif

			if (typeof rh_android !== 'undefined' && rh_android.pauseMusic) {
				rh_android.pauseMusic()
			}
		},
		onHide() {
			this.settleCurrentVideoWatch()
			// #ifdef APP-PLUS
			// 隐藏红包原生浮层，避免切到其他页面后残留
			if (this.$refs.playerRef && typeof this.$refs.playerRef.hideRedPacketOverlay === 'function') {
				this.$refs.playerRef.hideRedPacketOverlay()
			}
			// #endif
			if (typeof rh_android !== 'undefined' && rh_android.hideTopBanner) {
				rh_android.hideTopBanner()
			}
		},
		onUnload() {
			this.settleCurrentVideoWatch()
			// #ifdef APP-PLUS
			if (this.$refs.playerRef && typeof this.$refs.playerRef.hideRedPacketOverlay === 'function') {
				this.$refs.playerRef.hideRedPacketOverlay()
			}
			// #endif
		},
		methods: {
			// ================== uni-ad 激励视频广告 ==================

			initTakuAds() {
				// #ifndef APP-PLUS
				return
				// #endif
				const UniAds = getUniAds()
				if (!UniAds || !UniAds.loadRewardedVideoAd) {
					console.warn('[娱乐页] UniAds.loadRewardedVideoAd 不可用')
					return
				}
				const userInfo = this.userInfo || uni.getStorageSync('UserInfo') || {}
				UniAds.loadRewardedVideoAd({
					adpid: this.rewardedAdpid,
					extraData: JSON.stringify({ type: '2', value: '3' }),
					userId: String(userInfo.id || '')
				}, this.rewardedAdListener())
			},

			rewardedAdListener() {
				const self = this
				return {
					onAdLoaded: () => {
						console.log('[娱乐页] 激励视频广告预加载成功')
					},
					onAdFailed: (err) => {
						console.log('[娱乐页] 激励视频广告预加载失败', err)
					},
					onAdShow: () => {
						self.isAdShowing = true
						self.adDidShow = true
					},
					onAdClosed: () => {
						const didReward = self.adDidReward
						self.isAdShowing = false
						self.adDidShow = false
						self.adDidReward = false
						if (self.rewardedAdPurpose === 'fullscreen') {
							// 全屏广告：看完奖励金币，关闭后继续刷视频
							if (didReward) {
								uni.showToast({ title: '已获得金币奖励', icon: 'success', duration: 1500 })
								self.claimAdReward()
							}
						} else if (self.rewardedAdPurpose === 'redpacket') {
							// 红包广告：按红包逻辑发放奖励
							if (didReward) {
								self.rewardRedPacket()
								self.claimAdReward()
							} else {
								uni.showToast({ title: '未完整观看视频，无法获得金币', icon: 'none' })
							}
						}
						self.rewardedAdPurpose = ''
					},
					onReward: () => {
						self.adDidReward = true
					},
					onAdClicked: () => {}
				}
			},

			showRewardedVideoAd() {
				// #ifndef APP-PLUS
				return
				// #endif
				if (this.isAdShowing) {
					uni.showToast({ title: '广告正在展示中', icon: 'none' })
					return
				}
				const UniAds = getUniAds()
				if (!UniAds || !UniAds.showRewardedVideoAd) {
					uni.showToast({ title: '广告组件未就绪', icon: 'none' })
					return
				}
				const userInfo = this.userInfo || uni.getStorageSync('UserInfo') || {}
				if (!userInfo.id) {
					uni.showToast({ title: '请先登录', icon: 'none' })
					return
				}
				this.rewardedAdPurpose = 'redpacket'
				this.adDidShow = false
				this.adDidReward = false
				uni.showToast({ title: '广告加载中，请稍候...', icon: 'none', duration: 2000 })
				UniAds.showRewardedVideoAd({
					adpid: this.rewardedAdpid,
					extraData: JSON.stringify({ type: '2', value: '3' }),
					userId: String(userInfo.id || '')
				}, this.rewardedAdListener())
			},

			// ================== 全屏激励视频广告（每5个视频触发） ==================

			showFullScreenRewardedAd() {
				// #ifndef APP-PLUS
				return
				// #endif
				if (this.isAdShowing) {
					console.log('[娱乐页-全屏广告] 广告正在展示，跳过')
					return
				}
				const UniAds = getUniAds()
				if (!UniAds || !UniAds.showRewardedVideoAd) {
					console.warn('[娱乐页-全屏广告] UniAds.showRewardedVideoAd 不可用')
					return
				}
				const userInfo = this.userInfo || uni.getStorageSync('UserInfo') || {}
				if (!userInfo.id) {
					uni.showToast({ title: '请先登录', icon: 'none' })
					return
				}
				this.rewardedAdPurpose = 'fullscreen'
				this.adDidShow = false
				this.adDidReward = false
				uni.showToast({ title: '广告加载中，请稍候...', icon: 'none', duration: 2000 })
				UniAds.showRewardedVideoAd({
					adpid: this.rewardedAdpid,
					extraData: JSON.stringify({ type: '2', value: '3' }),
					userId: String(userInfo.id || '')
				}, this.rewardedAdListener())
			},

			// ================== 计时器清理 ==================

			clearAdTimers() {
				// 新版 SDK 内部管理广告实例，页面侧无需额外清理
			},

			// ================== 右侧悬浮红包 ==================

			/**
			 * 切换视频时结算当前视频已观看时长
			 */
			settleCurrentVideoWatch() {
				if (this.redPacketCurrentVideoId !== null && this.redPacketCurrentSeconds > 0) {
					this.redPacketTotalSeconds = Math.min(
						this.redPacketTargetSeconds,
						this.redPacketTotalSeconds + this.redPacketCurrentSeconds
					)
					console.log('[娱乐页] 结算视频观看时长:', this.redPacketCurrentSeconds.toFixed(2), '累计:', this.redPacketTotalSeconds.toFixed(2))
				}
				this.redPacketCurrentSeconds = 0
				this.redPacketLastCurrentTime = 0
				this.redPacketLastTimeMs = 0
				this.redPacketCurrentVideoId = null
			},

			/**
			 * 获取红包累计有效观看时长
			 */
			getRedPacketTotalSeconds() {
				return Math.min(this.redPacketTargetSeconds, this.redPacketTotalSeconds + this.redPacketCurrentSeconds)
			},

			/**
			 * 视频切换事件 - 全屏广告计数 + 红包计时逻辑
			 * 注意：mixin 中的 onVideoChange 由 Vue 自动合并执行，无需手动调用
			 */
			onVideoChange(e) {
				const currentIndex = e.index || 0
				this.fullScreenAdVideoCount++
				console.log('[娱乐页-全屏广告] 已观看视频数:', this.fullScreenAdVideoCount, '当前索引:', currentIndex)

				if (this.fullScreenAdVideoCount > 0 && this.fullScreenAdVideoCount % 5 === 0) {
					console.log('[娱乐页-全屏广告] 已看5个视频，准备展示全屏广告')
					this.showFullScreenRewardedAd()
				}

				// 切换视频前结算上一个视频的观看时长
				if (!this.redPacketReady && this.redPacketTodayCount < this.redPacketMaxDaily) {
					this.settleCurrentVideoWatch()
				}
			},

			/**
			 * 视频播放时间更新 - 累计有效观看时长
			 */
			onPlayerTimeUpdate(e) {
				// 广告展示、红包已准备好、今日次数已用完、或数据无效时不计时
				if (this.isAdShowing || this.redPacketReady || this.redPacketTodayCount >= this.redPacketMaxDaily || !e) {
					return
				}
				const item = e.item ? e.item : null
				const videoId = item && item.id ? item.id : (typeof e.index === 'number' ? e.index : null)
				if (videoId === null) return

				const currentTime = Number(e.currentTime || 0)
				const now = Date.now()
				// 切换了视频，先结算上一个
				if (this.redPacketCurrentVideoId !== null && this.redPacketCurrentVideoId !== videoId) {
					this.settleCurrentVideoWatch()
				}
				this.redPacketCurrentVideoId = videoId
				// 只累计真实经过的时间，且播放进度要正向推进（拖动进度条不算）
				if (currentTime > this.redPacketLastCurrentTime && this.redPacketLastTimeMs > 0) {
					const realDelta = (now - this.redPacketLastTimeMs) / 1000
					const playDelta = currentTime - this.redPacketLastCurrentTime
					// 取真实时间和播放时间的最小值，且单次最大 1 秒
					const delta = Math.max(0, Math.min(realDelta, playDelta, 1))
					if (delta > 0) {
						this.redPacketCurrentSeconds += delta
						this.updateRedPacketProgress()
					}
				}
				this.redPacketLastCurrentTime = currentTime
				this.redPacketLastTimeMs = now
			},


			/**
			 * 视频自然播放结束 - 结算当前视频
			 */
			onPlayerEnded(e) {
				if (this.redPacketReady || this.redPacketTodayCount >= this.redPacketMaxDaily) return
				console.log('[娱乐页] 视频播放结束:', e && e.index)
				this.settleCurrentVideoWatch()
				this.updateRedPacketProgress()
			},

			/**
			 * 更新红包进度
			 */
			updateRedPacketProgress() {
				const total = this.getRedPacketTotalSeconds()
				this.redPacketProgress = total / this.redPacketTargetSeconds
				this.redPacketRemainSeconds = Math.max(0, Math.ceil(this.redPacketTargetSeconds - total))
				const wasReady = this.redPacketReady
				this.redPacketReady = total >= this.redPacketTargetSeconds
				console.log('[娱乐页] 累计观看', total.toFixed(2), '剩余', this.redPacketRemainSeconds)
				if (!wasReady && this.redPacketReady) {
					console.log('[娱乐页] 累计观看 30 秒，金币可领取')
				}
			},

			onRedPacketTap() {
				if (this.isAdShowing) {
					uni.showToast({ title: '广告正在展示，请稍后', icon: 'none' })
					return
				}
				if (this.redPacketTodayCount >= this.redPacketMaxDaily) {
					uni.showToast({ title: '今日金币次数已用完', icon: 'none' })
					return
				}
				if (!this.redPacketReady) {
					const remain = Math.max(1, Math.ceil(this.redPacketTargetSeconds - this.getRedPacketTotalSeconds()))
					uni.showToast({ title: `再看 ${remain} 秒可领金币`, icon: 'none' })
					return
				}
				uni.showToast({ title: '获得金币', icon: 'none' })
				this.showRewardedVideoAd()
			},

			rewardRedPacket() {
				this.redPacketTotalSeconds = 0
				this.redPacketCurrentSeconds = 0
				this.redPacketCurrentVideoId = null
				this.redPacketLastCurrentTime = 0
				this.redPacketLastTimeMs = 0
				this.redPacketProgress = 0
				this.redPacketReady = false
				this.redPacketRemainSeconds = this.redPacketTargetSeconds
				this.redPacketTodayCount++
				uni.setStorageSync('ent_red_packet_today_count', this.redPacketTodayCount)
				uni.setStorageSync('ent_red_packet_today_date', new Date().toDateString())

				// 查询广告结果并发放金币奖励
				this.queryAdRewardAndShow()
				console.log('[娱乐页] 开始查询广告奖励')
			},

			/**
			 * 查询 uni-ad 广告结果并展示金币奖励
			 */
			queryAdRewardAndShow() {
				console.log('[娱乐页] queryAdRewardAndShow 被调用，token=', !!uni.getStorageSync('Token'))
				const token = uni.getStorageSync('Token')
				if (!token) {
					console.log('[娱乐页] 未登录，显示默认金币奖励')
					this.showGoldReward(100)
					return
				}

				// 记录广告关闭前的金币数，用于判断是否到账
				this.rewardCheckBaseGold = (this.userInfo && this.userInfo.goldCoin) ? Number(this.userInfo.goldCoin) : 0
				this.rewardCheckGot = false
				console.log('[娱乐页] 奖励查询基准金币:', this.rewardCheckBaseGold)

				// 延迟 1.5 秒开始查询，给后端写入数据库留出时间
				setTimeout(() => {
					this.refreshUserInfoAndShow(true)

					// 轮询 20 秒，每 1 秒查一次
					let checkCount = 0
					const maxCheck = 20
					const timer = setInterval(() => {
						checkCount++
						this.refreshUserInfoAndShow(checkCount >= maxCheck)
						if (checkCount >= maxCheck || this.rewardCheckGot) {
							clearInterval(timer)
							if (!this.rewardCheckGot) {
								uni.showToast({ title: '金币发放可能有延迟，请稍后查看', icon: 'none', duration: 2500 })
							}
							console.log('[娱乐页] 广告奖励查询结束，got=', this.rewardCheckGot)
						}
					}, 1000)
				}, 1500)
			},

			/**
			 * 广告关闭后主动领取金币奖励（不依赖服务端回调）
			 */
			claimAdReward() {
				console.log('[娱乐页] 调用广告奖励接口')
				this.$api.adRewardToCoin().then(res => {
					console.log('[娱乐页] 广告奖励接口返回:', res)
					if (res.code === 200) {
						// 奖励已发放，稍后刷新用户信息展示弹窗
						uni.showToast({ title: '奖励已发放', icon: 'success', duration: 1500 })
					}
				}).catch(err => {
					console.error('[娱乐页] 广告奖励接口失败:', err)
					uni.showToast({ title: '奖励发放失败，请稍后重试', icon: 'none', duration: 2000 })
				})
			},

			/**
			 * 刷新用户信息，如果有金币增加则展示奖励弹窗
			 * @param {boolean} isLast 是否是最后一次查询
			 */
			refreshUserInfoAndShow(isLast = false) {
				this.$api.getInfo().then(res => {
					if (res.code === 200 && res.data) {
						const baseGold = this.rewardCheckBaseGold || 0
						const newGold = Number(res.data.goldCoin || 0)
						uni.setStorageSync('UserInfo', res.data)
						this.userInfo = res.data
						if (newGold > baseGold && !this.rewardCheckGot) {
							this.rewardCheckGot = true
							const diff = newGold - baseGold
							this.showGoldReward(diff)
						} else if (isLast && !this.rewardCheckGot) {
							console.log('[娱乐页] 最终查询仍未发现金币增加，base=', baseGold, 'new=', newGold)
						}
					}
				}).catch(err => {
					console.error('[娱乐页] 刷新用户信息失败:', err)
				})
			},

			/**
			 * 展示金币奖励弹窗
			 */
			showGoldReward(goldNum) {
				this.goldNum = goldNum
				this.$refs.goldPopup && this.$refs.goldPopup.open()
				console.log('[娱乐页] 发放金币奖励:', this.goldNum)
			},

			closeGoldPopup() {
				this.$refs.goldPopup && this.$refs.goldPopup.close()
			}
		}
	}
</script>

<style lang="scss" scoped>
	.player-page {
		position: relative;
		width: 100%;
		height: 100vh;
		background-color: #000000;
		display: flex;
		flex-direction: column;
	}

	/* 视频播放器占满剩余空间，底部留出导航栏高度 */
	.player-wrapper {
		flex: 1;
		width: 100%;
		padding-bottom: calc(100rpx + env(safe-area-inset-bottom));
		position: relative;
		overflow: hidden;
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

	/* 🔥 右侧悬浮红包 - 始终显示 */
	.ent-red-packet {
		position: fixed;
		top: 120rpx;
		right: 24rpx;
		z-index: 100;
		width: 132rpx;
		height: 132rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		border-radius: 50%;
		background: rgba(255, 107, 107, 0.35);
		box-shadow: 0 6rpx 20rpx rgba(255, 107, 107, 0.25);
		transition: all 0.2s;
	}

	/* 进度环背景 */
	.ent-red-packet-ring-bg {
		position: absolute;
		top: 4rpx;
		left: 4rpx;
		width: 124rpx;
		height: 124rpx;
		border-radius: 50%;
		background: conic-gradient(
			#FFD9D9 0deg 360deg,
			#FFD9D9 0deg
		);
		-webkit-mask: radial-gradient(transparent 58%, #000 64%);
		mask: radial-gradient(transparent 58%, #000 64%);
		opacity: 0.6;
		pointer-events: none;
	}

	/* 红包进度环（H5 支持 conic-gradient + mask） */
	.ent-red-packet-progress {
		position: absolute;
		top: 4rpx;
		left: 4rpx;
		width: 124rpx;
		height: 124rpx;
		border-radius: 50%;
		background: conic-gradient(
			#FF5252 0deg calc(var(--progress, 0) * 360deg),
			transparent 0deg
		);
		-webkit-mask: radial-gradient(transparent 58%, #000 64%);
		mask: radial-gradient(transparent 58%, #000 64%);
		opacity: 0.95;
		pointer-events: none;
	}

	/* 内圆背景 */
	.ent-red-packet-inner {
		position: relative;
		width: 108rpx;
		height: 108rpx;
		border-radius: 50%;
		background: linear-gradient(135deg, #FF6B6B 0%, #FF8E53 100%);
		box-shadow: inset 0 0 16rpx rgba(255, 255, 255, 0.3);
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		z-index: 1;
	}

	.ent-red-packet.ready {
		background: rgba(255, 107, 107, 0.55);
		box-shadow: 0 8rpx 32rpx rgba(255, 82, 82, 0.55);
		animation: entRedPacketBreathe 1.4s ease-in-out infinite;
	}

	.ent-red-packet.ready .ent-red-packet-progress {
		background: conic-gradient(
			#FFD700 0deg 360deg,
			#FFD700 0deg
		);
		box-shadow: 0 0 20rpx rgba(255, 215, 0, 0.7);
	}

	.ent-red-packet.ready .ent-red-packet-inner {
		background: linear-gradient(135deg, #FF5252 0%, #FF8E53 50%, #FFD700 100%);
		box-shadow: 0 0 24rpx rgba(255, 215, 0, 0.6), inset 0 0 16rpx rgba(255, 255, 255, 0.4);
	}

	.ent-red-packet-img {
		width: 58rpx;
		height: 58rpx;
		z-index: 2;
	}

	.ent-red-packet-count,
	.ent-red-packet-text {
		position: absolute;
		bottom: 16rpx;
		left: 0;
		width: 100%;
		text-align: center;
		font-size: 18rpx;
		color: #fff;
		font-weight: bold;
		text-shadow: 0 1rpx 4rpx rgba(0,0,0,0.3);
		z-index: 2;
	}

	@keyframes entRedPacketBreathe {
		0%, 100% { transform: scale(1); }
		50% { transform: scale(1.08); }
	}
</style>

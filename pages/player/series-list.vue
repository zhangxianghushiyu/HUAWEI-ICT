<template>
	<view class="ent-feed">
		<!-- 视频播放器区域 -->
		<view class="player-wrapper">
			<dy-player-app v-if="videoList.length > 0 && isApp" ref="playerRef"
				:list="videoList" :start-index="startIndex"
				:is-drama="false" :info="{}"
				:auto-play="true" :loop-play="false"
				:show-back="false" :show-search="false" :show-red-packet="false" :show-share="false" :show-report="true"
				:show-ent-red-packet="true"
				:ent-red-packet-ready="redPacketReady"
				:ent-red-packet-progress="redPacketProgress"
				:ent-red-packet-remain="redPacketRemainSeconds"
				@change="onVideoChange" @timeupdate="onPlayerTimeUpdate" @ended="onPlayerEnded"
				@like="onLike" @collect="onCollect" @share="onShare" @report="onReport"
				@loadMore="onLoadMore" @refresh="onRefresh"
				@seriesDetail="onSeriesDetail"
				@entRedPacketTap="onRedPacketTap" />

			<dy-player v-if="videoList.length > 0 && !isApp" ref="playerRef"
				:list="videoList" :start-index="startIndex"
				:is-drama="false" :info="{}"
				:auto-play="true" :loop-play="false"
				:show-back="false" :show-search="false" :show-red-packet="false" :show-share="false" :show-report="true"
				@change="onVideoChange" @timeupdate="onPlayerTimeUpdate" @ended="onPlayerEnded"
				@like="onLike" @collect="onCollect" @share="onShare" @report="onReport"
				@loadMore="onLoadMore" @refresh="onRefresh"
				@seriesDetail="onSeriesDetail" />
		</view>

		<!-- 加载中 -->
		<view v-if="videoList.length === 0" class="loading-state">
			<text class="loading-text">加载中...</text>
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
	import UniAds from '@/common/uniad-sdk.js'
	function getUniAds() {
		return (UniAds && UniAds.loadRewardedVideoAd) ? UniAds : null
	}
	// #endif

	import dyPlayer from '@/components/dy-player/dy-player.vue'
	import dyPlayerApp from '@/components/dy-player/dy-player-app.vue'

	export default {
		components: { dyPlayer, dyPlayerApp },
		data() {
			return {
				isApp: false,
				videoList: [],
				startIndex: 0,
				pageNum: 1,
				pageSize: 10,
				loadingMore: false,
				noMore: false,
				userInfo: {},

				// ===== uni-ad 激励视频广告 =====
				rewardedAdpid: '1819020042',
				isAdShowing: false,
				adDidShow: false,
				adDidReward: false,
				rewardedAdPurpose: '',

				// ===== 全屏激励视频广告（每5个视频触发） =====
				fullScreenAdVideoCount: 0,

				// ===== 右侧悬浮红包 =====
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
				redPacketRemainSeconds: 0,

				// ===== 金币奖励 =====
				goldNum: 0,
				rewardCheckBaseGold: 0,
				rewardCheckGot: false
			}
		},
		computed: {
			progressStyle() {
				return { '--progress': this.redPacketProgress }
			}
		},
		created() {
			// #ifdef APP-PLUS
			this.isApp = true
			// #endif
		},
		onLoad() {
			this.userInfo = uni.getStorageSync('UserInfo') || {}
			this.redPacketTodayCount = Number(uni.getStorageSync('ent_red_packet_today_count') || 0)
			const today = new Date().toDateString()
			if (uni.getStorageSync('ent_red_packet_today_date') !== today) {
				this.redPacketTodayCount = 0
				uni.setStorageSync('ent_red_packet_today_date', today)
			}
			this.redPacketRemainSeconds = this.redPacketTargetSeconds
			this.loadFeed(true)
		},
		onShow() {
			// #ifdef APP-PLUS
			if (this.$refs.playerRef && typeof this.$refs.playerRef.showRedPacketOverlay === 'function') {
				this.$refs.playerRef.showRedPacketOverlay()
			}
			this.initTakuAds()
			// #endif
			if (typeof rh_android !== 'undefined' && rh_android.pauseMusic) {
				rh_android.pauseMusic()
			}
			this.$nextTick(() => {
				if (!this.$refs.playerRef) return
				// Tab 切回时用 resume() 唤醒：不重置 loadingShow / pendingPlay，避免"一闪一闪"
				// resume() 内部会顺带 showGestureOverlay()，无需重复调用
				if (typeof this.$refs.playerRef.resume === 'function') {
					this.$refs.playerRef.resume()
					// 广告/原生 Activity 返回后重建手势层，避免透明浮层盖住 TabBar 拦触摸
					if (typeof this.$refs.playerRef.rebuildGestureView === 'function') {
						this.$refs.playerRef.rebuildGestureView()
					}
				} else {
					this.$refs.playerRef.play()
					if (typeof this.$refs.playerRef.showGestureOverlay === 'function') {
						this.$refs.playerRef.showGestureOverlay()
					}
				}
			})
		},
		onHide() {
			this.settleCurrentVideoWatch()
			// #ifdef APP-PLUS
			// gestureView 全局浮层且会拦截触摸事件，必须 hide 避免影响其它 tab 的滑动
			if (this.$refs.playerRef && typeof this.$refs.playerRef.hideRedPacketOverlay === 'function') {
				this.$refs.playerRef.hideRedPacketOverlay()
			}
			if (this.$refs.playerRef && typeof this.$refs.playerRef.hideGestureOverlay === 'function') {
				this.$refs.playerRef.hideGestureOverlay()
			}
			// #endif
			if (this.$refs.playerRef) this.$refs.playerRef.pause()
			if (typeof rh_android !== 'undefined' && rh_android.hideTopBanner) {
				rh_android.hideTopBanner()
			}
		},
		onUnload() {
			this.settleCurrentVideoWatch()
			if (this.$refs.playerRef) this.$refs.playerRef.pause()
		},
		methods: {
			// ================== 视频数据加载 ==================
			loadFeed(refresh = false) {
				if (refresh) {
					this.pageNum = 1
					this.videoList = []
					this.noMore = false
				}
				if (this.noMore || this.loadingMore) return

				this.loadingMore = true
				this.$api.getVideoSeriesList({
					pageNum: this.pageNum,
					pageSize: this.pageSize
				}).then(res => {
					const series = (res.data && res.data.list) || []
					if (series.length === 0) {
						this.noMore = true
						this.loadingMore = false
						return
					}
					this.noMore = res.data.isLastPage || false

					// 每个系列取第一集（免费集）作为代表视频
					const tasks = series.map(item => this.loadSeriesEpisode(item))
					return Promise.all(tasks).then(videos => {
						this.loadingMore = false
						const valid = videos.filter(Boolean)

						if (refresh) {
							this.videoList = valid
							this.startIndex = 0
							this.initPlayer()
						} else {
							const oldLen = this.videoList.length
							this.videoList = [...this.videoList, ...valid]
							if (oldLen === 0) {
								this.initPlayer()
							} else if (this.$refs.playerRef && typeof this.$refs.playerRef.appendVideos === 'function') {
								this.$refs.playerRef.appendVideos(valid)
							}
						}
						this.pageNum++
					})
				}).catch(err => {
					this.loadingMore = false
					console.error('[series-list] loadFeed error:', err)
				})
			},

			loadSeriesEpisode(series) {
				return this.$api.getVideoSeriesEpisodes({ seriesName: series.seriesName }).then(r => {
					const eps = (r.data || []).filter(e => e.videoUrl)
					if (eps.length === 0) return null
					// 从免费集中随机取一集（isFree=1），若全为锁定则取第一集
					const freeEps = eps.filter(e => e.isFree === 1)
					const pool = freeEps.length > 0 ? freeEps : [eps[0]]
					const ep = pool[Math.floor(Math.random() * pool.length)]
					// 补点赞/收藏字段：走 silent，失败不弹全局 toast，只回退到基础数据
					return this.$api.getVideoInfoSilent({ id: ep.id })
						.then(info => this.formatSeriesEpisode(ep, series, (info && info.data) || {}))
						.catch(() => this.formatSeriesEpisode(ep, series, {}))
				}).catch(() => null)
			},

			formatSeriesEpisode(ep, series, extra) {
				return {
					...extra,
					...ep,
					id: ep.id,
					videoId: ep.id,
					name: ep.name,
					videoUrl: ep.videoUrl,
					titleImg: ep.videoCover || series.cover || '',
					content: ep.content || '',
					// 用系列名占位 dramaId，触发「系列」按钮展示
					dramaId: series.seriesName,
					seriesName: series.seriesName,
					playCount: series.playCount || 0,
					coverShow: true,
					object_fit: 'cover'
				}
			},

			initPlayer() {
				this.$nextTick(() => {
					if (this.$refs.playerRef) {
						this.$refs.playerRef.initPlayer(this.videoList, this.startIndex)
					}
				})
			},

			// ================== 播放器事件 ==================
			onVideoChange(e) {
				// 全屏广告计数：每看满 5 个视频触发一次全屏激励广告
				this.fullScreenAdVideoCount++
				const idx = (e && e.index) || 0
				console.log('[series-list] 已看视频数:', this.fullScreenAdVideoCount, '当前索引:', idx)
				if (this.fullScreenAdVideoCount > 0 && this.fullScreenAdVideoCount % 5 === 0) {
					this.showFullScreenRewardedAd()
				}
			},

			onPlayerEnded(e) {
				if (this.redPacketReady || this.redPacketTodayCount >= this.redPacketMaxDaily) return
				this.settleCurrentVideoWatch()
				this.updateRedPacketProgress()
			},

			onPlayerTimeUpdate(e) {
				if (this.isAdShowing || this.redPacketReady || this.redPacketTodayCount >= this.redPacketMaxDaily || !e) return

				const item = e.item || null
				const videoId = item && item.id ? item.id : (typeof e.index === 'number' ? e.index : null)
				if (videoId === null) return

				const currentTime = Number(e.currentTime || 0)
				const now = Date.now()
				if (this.redPacketCurrentVideoId !== null && this.redPacketCurrentVideoId !== videoId) {
					this.settleCurrentVideoWatch()
				}
				this.redPacketCurrentVideoId = videoId
				if (currentTime > this.redPacketLastCurrentTime && this.redPacketLastTimeMs > 0) {
					const realDelta = (now - this.redPacketLastTimeMs) / 1000
					const playDelta = currentTime - this.redPacketLastCurrentTime
					const delta = Math.max(0, Math.min(realDelta, playDelta, 1))
					if (delta > 0) {
						this.redPacketCurrentSeconds += delta
						this.updateRedPacketProgress()
					}
				}
				this.redPacketLastCurrentTime = currentTime
				this.redPacketLastTimeMs = now
			},

			onLike(item) {
				if (!item.videoId) return
				this.$api.videoLike({ id: item.videoId }).catch(err => {
					console.error('[series-list] like error:', err)
				})
			},

			onCollect(item) {
				if (!item.videoId) return
				this.$api.videoCollect({ id: item.videoId }).catch(err => {
					console.error('[series-list] collect error:', err)
				})
			},

			onShare(item) {
				const versionInfo = uni.getStorageSync('Version') || {}
				if (!versionInfo.appUrl) return
				if (this.isApp && typeof window !== 'undefined' && window.rh_android) {
					try {
						window.rh_android.shareForFriends('红樱桃', '快来跟我一起刷视频领金币吧！', versionInfo.url, versionInfo.appUrl)
					} catch (e) {
						console.error('[series-list] share error:', e)
					}
				}
			},

			/**
			 * 举报视频
			 */
			onReport({ item, reason, remark }) {
				const videoId = item.videoId || item.id
				if (!videoId) {
					uni.showToast({ title: '视频信息异常', icon: 'none' })
					return
				}
				uni.showLoading({ title: '提交中' })
				this.$api.reportVideo({ videoId, reason, remark }).then(() => {
					uni.hideLoading()
					uni.showToast({ title: '举报成功', icon: 'success' })
					this.removeCurrentVideo(item)
				}).catch(err => {
					uni.hideLoading()
					console.error('[series-list] report error:', err)
					uni.showToast({ title: '举报失败，请重试', icon: 'none' })
				})
			},

			/**
			 * 从播放列表移除被举报/删除的视频
			 */
			removeCurrentVideo(item) {
				const id = item.videoId || item.id
				const idx = this.videoList.findIndex(v => (v.videoId || v.id) === id)
				if (idx < 0) return
				const wasCurrent = idx === this.startIndex
				this.videoList.splice(idx, 1)
				if (this.videoList.length === 0) {
					this.startIndex = 0
					this.loadFeed(true)
					return
				}
				if (idx < this.startIndex) {
					this.startIndex = Math.max(0, this.startIndex - 1)
				} else if (wasCurrent) {
					this.startIndex = Math.min(idx, this.videoList.length - 1)
				}
				if (this.$refs.playerRef && typeof this.$refs.playerRef.refreshList === 'function') {
					this.$refs.playerRef.refreshList(this.videoList, true)
				}
			},

			onLoadMore() {
				if (this.loadingMore) return
				if (!this.noMore) {
					this.loadFeed(false)
				} else {
					this.loadRandomFeed()
				}
			},

			onRefresh() {
				this.loadFeed(true)
			},

			// 无限滚动：全部系列刷完后，从 randomFeed 继续追加随机集
			loadRandomFeed() {
				if (this.loadingMore) return
				this.loadingMore = true
				const pageSize = this.pageSize
				this.$api.getVideoSeriesRandomFeed({ pageNum: 1, pageSize }).then(res => {
					const eps = (res.data && res.data.list) || []
					this.loadingMore = false
					if (eps.length === 0) return
					const valid = eps
						.filter(ep => ep.videoUrl)
						.map(ep => this.formatRandomEpisode(ep))
					if (valid.length === 0) return
					if (this.$refs.playerRef && typeof this.$refs.playerRef.appendVideos === 'function') {
						this.$refs.playerRef.appendVideos(valid)
					}
				}).catch(() => {
					this.loadingMore = false
				})
			},

			formatRandomEpisode(ep) {
				return {
					id: ep.id,
					videoId: ep.id,
					name: ep.name,
					videoUrl: ep.videoUrl,
					titleImg: ep.videoCover || '',
					content: ep.content || '',
					dramaId: ep.seriesName,
					seriesName: ep.seriesName,
					playCount: ep.playCount || 0,
					coverShow: true,
					object_fit: 'cover'
				}
			},

			// ================== 系列详情 ==================
			onSeriesDetail(item) {
				const seriesName = item.seriesName || item.dramaId
				if (!seriesName) return
				const coverUrl = item.titleImg || item.videoCover || ''
				const cover = coverUrl ? `&cover=${encodeURIComponent(coverUrl)}` : ''
				uni.navigateTo({
					url: `/pages/player/series?seriesName=${encodeURIComponent(seriesName)}${cover}`
				})
			},

			// ================== 红包计时 ==================
			settleCurrentVideoWatch() {
				if (this.redPacketCurrentVideoId !== null && this.redPacketCurrentSeconds > 0) {
					this.redPacketTotalSeconds = Math.min(
						this.redPacketTargetSeconds,
						this.redPacketTotalSeconds + this.redPacketCurrentSeconds
					)
				}
				this.redPacketCurrentSeconds = 0
				this.redPacketLastCurrentTime = 0
				this.redPacketLastTimeMs = 0
				this.redPacketCurrentVideoId = null
			},

			getRedPacketTotalSeconds() {
				return Math.min(this.redPacketTargetSeconds, this.redPacketTotalSeconds + this.redPacketCurrentSeconds)
			},

			updateRedPacketProgress() {
				const total = this.getRedPacketTotalSeconds()
				this.redPacketProgress = total / this.redPacketTargetSeconds
				this.redPacketRemainSeconds = Math.max(0, Math.ceil(this.redPacketTargetSeconds - total))
				const wasReady = this.redPacketReady
				this.redPacketReady = total >= this.redPacketTargetSeconds
				if (!wasReady && this.redPacketReady) {
					console.log('[series-list] 累计观看达标，金币可领取')
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
				this.queryAdRewardAndShow()
			},

			claimAdReward() {
				this.$api.adRewardToCoin().then(res => {
					if (res.code === 200) {
						uni.showToast({ title: '奖励已发放', icon: 'success', duration: 1500 })
					}
				}).catch(err => {
					console.error('[series-list] 广告奖励接口失败:', err)
				})
			},

			queryAdRewardAndShow() {
				const token = uni.getStorageSync('Token')
				if (!token) {
					this.showGoldReward(100)
					return
				}
				this.rewardCheckBaseGold = (this.userInfo && this.userInfo.goldCoin) ? Number(this.userInfo.goldCoin) : 0
				this.rewardCheckGot = false
				setTimeout(() => {
					this.refreshUserInfoAndShow(true)
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
						}
					}, 1000)
				}, 1500)
			},

			refreshUserInfoAndShow(isLast = false) {
				this.$api.getInfo().then(res => {
					if (res.code === 200 && res.data) {
						const baseGold = this.rewardCheckBaseGold || 0
						const newGold = Number(res.data.goldCoin || 0)
						uni.setStorageSync('UserInfo', res.data)
						this.userInfo = res.data
						if (newGold > baseGold && !this.rewardCheckGot) {
							this.rewardCheckGot = true
							this.showGoldReward(newGold - baseGold)
						}
					}
				}).catch(err => {
					console.error('[series-list] 刷新用户信息失败:', err)
				})
			},

			showGoldReward(goldNum) {
				this.goldNum = goldNum
				this.$refs.goldPopup && this.$refs.goldPopup.open()
			},

			closeGoldPopup() {
				this.$refs.goldPopup && this.$refs.goldPopup.close()
			},

			// ================== uni-ad 广告（App端） ==================
			initTakuAds() {
				// #ifndef APP-PLUS
				return
				// #endif
				const UniAds = getUniAds()
				if (!UniAds || !UniAds.loadRewardedVideoAd) {
					console.warn('[series-list] UniAds 未就绪')
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
						console.log('[series-list] 激励视频预加载成功')
					},
					onAdFailed: (err) => {
						console.log('[series-list] 激励视频预加载失败', err)
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
						if (self.rewardedAdPurpose === 'redpacket' && didReward) {
							self.rewardRedPacket()
							self.claimAdReward()
						} else if (self.rewardedAdPurpose === 'redpacket') {
							uni.showToast({ title: '未完整观看视频，无法获得金币', icon: 'none' })
						} else if (self.rewardedAdPurpose === 'fullscreen' && didReward) {
							uni.showToast({ title: '已获得金币奖励', icon: 'success', duration: 1500 })
							self.claimAdReward()
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

			// ================== 全屏激励视频广告 ==================
			showFullScreenRewardedAd() {
				// #ifndef APP-PLUS
				return
				// #endif
				if (this.isAdShowing) return
				const UniAds = getUniAds()
				if (!UniAds || !UniAds.showRewardedVideoAd) {
					console.warn('[series-list] UniAds.showRewardedVideoAd 不可用')
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
			}
		}
	}
</script>

<style lang="scss" scoped>
	.ent-feed {
		width: 100%;
		height: 100vh;
		background: #000;
		overflow: hidden;
	}

	/* 播放器容器：全屏 + 底部留 TabBar 空间 */
	.player-wrapper {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		padding-bottom: calc(100rpx + env(safe-area-inset-bottom));
	}

	/* 加载状态 */
	.loading-state {
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
	}

	.loading-text {
		font-size: 32rpx;
		color: rgba(255, 255, 255, 0.6);
	}

	/* 金币弹窗 */
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

	/* 右侧悬浮红包 */
	.ent-red-packet {
		position: fixed;
		top: 180rpx;
		right: 24rpx;
		z-index: 200;
		width: 132rpx;
		height: 132rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		border-radius: 50%;
		background: rgba(255, 107, 107, 0.35);
		box-shadow: 0 6rpx 20rpx rgba(255, 107, 107, 0.25);
		transition: all 0.2s;

		&.ready {
			background: rgba(255, 107, 107, 0.55);
			box-shadow: 0 8rpx 32rpx rgba(255, 82, 82, 0.55);
			animation: entRedPacketBreathe 1.4s ease-in-out infinite;
		}
	}

	.ent-red-packet-ring-bg {
		position: absolute;
		top: 4rpx;
		left: 4rpx;
		width: 124rpx;
		height: 124rpx;
		border-radius: 50%;
		background: conic-gradient(#FFD9D9 0deg 360deg, #FFD9D9 0deg);
		-webkit-mask: radial-gradient(transparent 58%, #000 64%);
		mask: radial-gradient(transparent 58%, #000 64%);
		opacity: 0.6;
		pointer-events: none;
	}

	.ent-red-packet-progress {
		position: absolute;
		top: 4rpx;
		left: 4rpx;
		width: 124rpx;
		height: 124rpx;
		border-radius: 50%;
		background: conic-gradient(#FF5252 0deg calc(var(--progress, 0) * 360deg), transparent 0deg);
		-webkit-mask: radial-gradient(transparent 58%, #000 64%);
		mask: radial-gradient(transparent 58%, #000 64%);
		opacity: 0.95;
		pointer-events: none;
	}

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
		text-shadow: 0 1rpx 4rpx rgba(0, 0, 0, 0.3);
		z-index: 2;
	}

	@keyframes entRedPacketBreathe {
		0%,
		100% {
			transform: scale(1);
		}

		50% {
			transform: scale(1.08);
		}
	}
</style>

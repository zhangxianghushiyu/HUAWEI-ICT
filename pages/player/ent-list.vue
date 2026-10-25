<template>
	<view class="ent-feed">
		<!-- 视频播放器区域 -->
		<view class="player-wrapper">
			<dy-player-app v-if="videoList.length > 0 && isApp" ref="playerRef"
				:list="videoList" :start-index="startIndex"
				:is-drama="false" :info="{}"
				:auto-play="true" :loop-play="false"
				:show-back="false" :show-search="false"
				:show-red-packet="false"
				:show-share="false"
				:show-report="true"
				:show-ent-red-packet="true"
				:ent-red-packet-ready="redPacketReady"
				:ent-red-packet-progress="redPacketProgress"
				:ent-red-packet-remain="redPacketRemainSeconds"
				:tab-bar-height="tabBarHeight"
				@change="onVideoChange"
				@timeupdate="onPlayerTimeUpdate" @ended="onPlayerEnded"
				@like="onLike" @collect="onCollect" @share="onShare" @report="onReport"
				@loadMore="onLoadMore" @refresh="onRefresh"
				@seriesDetail="onSeriesDetail"
					@entRedPacketTap="onRedPacketTap"
					:ent-tab-list="tabList"
					:ent-sub-tab-list="subTabList"
					:ent-active-tab-index="activeTabIndex"
					:ent-selected-sub-id="selectedCategoryId"
					@entTabClick="onEntTabClick"
					@entSubTabClick="onEntSubTabClick" />

			<dy-player v-if="videoList.length > 0 && !isApp" ref="playerRef"
				:list="videoList" :start-index="startIndex"
				:is-drama="false" :info="{}"
				:auto-play="true" :loop-play="false"
				:show-back="false" :show-search="false"
				:show-red-packet="false"
				:show-share="false"
				@change="onVideoChange"
				@timeupdate="onPlayerTimeUpdate" @ended="onPlayerEnded"
				@like="onLike" @collect="onCollect" @share="onShare"
				@loadMore="onLoadMore" @refresh="onRefresh"
				@seriesDetail="onSeriesDetail" />
		</view>

		<!-- 顶部分类栏（非App端，App端通过 dy-player-app 的 cover-view 渲染） -->
		<!-- #ifndef APP-PLUS -->
		<view class="top-bar" :style="{ paddingTop: statusBarHeight + 'px' }">
			<scroll-view scroll-x class="tab-scroll">
				<view class="tab-list">
					<view
						v-for="(item, index) in tabList"
						:key="item.id"
						class="tab-item"
						:class="{ active: activeTabIndex === index }"
						@click="onTabClick(item, index)"
					>{{ item.name }}</view>
				</view>
			</scroll-view>
			<scroll-view scroll-x class="subtab-scroll" v-if="subTabList.length > 0">
				<view class="subtab-list">
					<view
						v-for="sub in subTabList"
						:key="sub.id"
						class="subtab-item"
						:class="{ active: selectedCategoryId === sub.id }"
						@click="onSubTabClick(sub)"
					>{{ sub.name }}</view>
				</view>
			</scroll-view>
		</view>
		<!-- #endif -->

		<!-- 加载中 -->
		<view v-if="videoList.length === 0" class="loading-state">
			<text class="loading-text">加载中...</text>
		</view>

		<!-- 金币弹窗 -->
		<uv-popup ref="goldPopup" :closeOnClickOverlay="false" bgColor="none">
			<view class="gold-popup">
				<image class="gold-popup-image" src="@/static/images/change/CollectGoldCoins2.png" mode="scaleToFill" />
				<image class="gold-popup-close" src="~@/static/images/Close.png" mode="widthFix" @click="closeGoldPopup" />
				<view class="gold-popup-num">{{ goldNum }} 金币</view>
			</view>
		</uv-popup>

		<!-- #ifndef APP-PLUS -->
		<!-- 非App端：右侧悬浮红包 -->
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
				statusBarHeight: 0,
				tabBarHeight: 0,

				// 分类
				tabList: [],
				subTabList: [],
				activeTabIndex: 0,
				selectedCategoryId: 0,
				firstTypeId: -1,

				// 视频列表
				videoList: [],
				startIndex: 0,
				pageNum: 1,
				pageSize: 8,
				loadingMore: false,
				noMore: false,

				// 用户
				userInfo: {},

				// ===== uni-ad 激励视频广告 =====
				rewardedAdpid: '1819020042',
				isAdShowing: false,
				rewardedAdPurpose: '',
				adDidShow: false,
				adDidReward: false,

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
				rewardCheckBaseGold: 0,
				rewardCheckGot: false,

				// 金币
				goldNum: 0
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
			const sys = uni.getSystemInfoSync()
			this.statusBarHeight = sys.statusBarHeight || 0
			// TabBar 高度 = 100rpx + 安全区底部，传给 dy-player-app 避免遮挡
			const rpx = val => val * (sys.screenWidth || 375) / 750
			this.tabBarHeight = rpx(100) + ((sys.safeAreaInsets && sys.safeAreaInsets.bottom) || 0)
			this.userInfo = uni.getStorageSync('UserInfo') || {}
			this.redPacketTodayCount = Number(uni.getStorageSync('ent_red_packet_today_count') || 0)
			const today = new Date().toDateString()
			if (uni.getStorageSync('ent_red_packet_today_date') !== today) {
				this.redPacketTodayCount = 0
				uni.setStorageSync('ent_red_packet_today_date', today)
			}
			this.redPacketRemainSeconds = this.redPacketTargetSeconds
		},
		onShow() {
			this.loadCategories()
			// #ifdef APP-PLUS
			if (this.$refs.playerRef && typeof this.$refs.playerRef.showRedPacketOverlay === 'function') {
				this.$refs.playerRef.showRedPacketOverlay()
			}
			if (this.$refs.playerRef && typeof this.$refs.playerRef.showEntTabView === 'function') {
				this.$refs.playerRef.showEntTabView()
			}
			this.initTakuAds()
			// #endif
			if (typeof rh_android !== 'undefined' && rh_android.pauseMusic) {
				rh_android.pauseMusic()
			}
			this.$nextTick(() => {
				if (this.$refs.playerRef) {
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
			if (this.$refs.playerRef && typeof this.$refs.playerRef.hideRedPacketOverlay === 'function') {
				this.$refs.playerRef.hideRedPacketOverlay()
			}
			if (this.$refs.playerRef && typeof this.$refs.playerRef.hideGestureOverlay === 'function') {
				this.$refs.playerRef.hideGestureOverlay()
			}
			if (this.$refs.playerRef && typeof this.$refs.playerRef.hideEntTabView === 'function') {
				this.$refs.playerRef.hideEntTabView()
			}
			// #endif
			if (this.$refs.playerRef) {
				this.$refs.playerRef.pause()
			}
			if (typeof rh_android !== 'undefined' && rh_android.hideTopBanner) {
				rh_android.hideTopBanner()
			}
		},
		onUnload() {
			this.settleCurrentVideoWatch()
			if (this.$refs.playerRef) {
				this.$refs.playerRef.pause()
			}
		},
		methods: {
			// ================== 分类加载 ==================
			loadCategories() {
				this.$api.getShotType({ pageNum: 1, pageSize: 20 }).then(res => {
					const list = (res.data && res.data.list) || []
					this.tabList = list
					if (list.length > 0) {
						this.firstTypeId = list[0].id
						this.activeTabIndex = 0
						this.loadSubCategories(list[0].id)
					}
				}).catch(err => {
					console.error('[ent-feed] loadCategories error:', err)
				})
			},

			loadSubCategories(parentId) {
				this.$api.getShotNextType({ pageNum: 1, pageSize: 20, parentId }).then(res => {
					const list = (res.data && res.data.list) || []
					this.subTabList = list
					this.selectedCategoryId = list.length > 0 ? list[0].id : 0
					this.loadFeed(true)
				}).catch(err => {
					console.error('[ent-feed] loadSubCategories error:', err)
				})
			},

			onTabClick(item, index) {
				if (this.activeTabIndex === index) return
				this.activeTabIndex = index
				this.firstTypeId = item.id
				this.loadSubCategories(item.id)
			},

			onSubTabClick(item) {
				if (this.selectedCategoryId === item.id) return
				this.selectedCategoryId = item.id
				this.loadFeed(true)
			},

			// App端 cover-view 分类 tab 点击（dy-player-app 转发）
			onEntTabClick({ item, index }) {
				this.onTabClick(item, index)
			},

			onEntSubTabClick({ item }) {
				this.onSubTabClick(item)
			},

			// ================== 视频数据加载 ==================
			loadFeed(refresh = false) {
				if (refresh) {
					this.pageNum = 1
					this.videoList = []
					this.noMore = false
				}
				if (this.noMore || this.loadingMore) return

				this.loadingMore = true
				this.$api.homePage({
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					mainType: this.firstTypeId,
					categoryId: this.selectedCategoryId
				}).then(res => {
					const cards = (res.data && res.data.list) || []
					if (cards.length === 0) {
						this.noMore = true
						this.loadingMore = false
						return
					}
					this.noMore = res.data.isLastPage || false

					// 并行加载每部剧的剧集详情，各取一集
					const loadCard = card => {
						return this.$api.getDetailsList({ dramaId: card.dramaId }).then(r => {
							const eps = (r.data || []).filter(e => e.videoUrl)
							if (eps.length === 0) return null
							const ep = eps[Math.floor(Math.random() * eps.length)]
							return {
								...ep,
								dramaId: card.dramaId,
								titleImg: card.titleImg || ep.titleImg,
								name: card.title || ep.title,
								// 附带短剧卡片信息供"系列"按钮使用
								_dramaTitle: card.title,
								_dramaTotalGather: card.totalGather
							}
						}).catch(() => null)
					}
					const tasks = cards.map(loadCard)

					// 首屏加速：首个有效视频就绪即起播，不必等全部请求返回
					if (refresh) {
						Promise.race(tasks).then(first => {
							if (first && this.videoList.length === 0) {
								this.videoList = [first]
								this.startIndex = 0
								this.initPlayer()
							}
						})
					}

					return Promise.all(tasks).then(videos => {
						this.loadingMore = false
						const valid = videos.filter(Boolean)

						if (refresh) {
							// 首次加载：Fisher-Yates 洗牌
							for (let i = valid.length - 1; i > 0; i--) {
								const j = Math.floor(Math.random() * (i + 1));
								[valid[i], valid[j]] = [valid[j], valid[i]]
							}
							const firstId = this.videoList.length > 0 ? this.videoList[0].dramaDetailsId : null
							if (firstId) {
								// 已提前首播：保留首播项，其余去重后追加，避免二次 initPlayer 打断播放
								const rest = valid.filter(v => v.dramaDetailsId !== firstId)
								this.videoList = [this.videoList[0], ...rest]
								if (rest.length && this.$refs.playerRef && typeof this.$refs.playerRef.appendVideos === 'function') {
									this.$refs.playerRef.appendVideos(rest)
								}
							} else {
								this.videoList = valid
								this.startIndex = 0
								this.initPlayer()
							}
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
					console.error('[ent-feed] loadFeed error:', err)
				})
			},

			initPlayer() {
				this.$nextTick(() => {
					if (this.$refs.playerRef) {
						this.$refs.playerRef.initPlayer(this.videoList, this.startIndex)
					}
				})
			},

			// ================== 播放器事件 ==================
			onVideoChange(e) {},

			onLike(item) {
				if (item.dramaDetailsId) {
					this.$api.dramaLike({ dramaDetailsId: item.dramaDetailsId }).catch(err => {
						console.error('[ent-feed] like error:', err)
					})
				}
			},

			onCollect(item) {
				if (item.dramaId) {
					this.$api.dramaCollect({ dramaId: item.dramaId }).catch(err => {
						console.error('[ent-feed] collect error:', err)
					})
				}
			},

			onShare(item) {
				const versionInfo = uni.getStorageSync('Version') || {}
				if (!versionInfo.appUrl) return
				if (typeof window !== 'undefined') {
					window.shareAndroidCallBack = () => {}
				}
				if (this.isApp && typeof window !== 'undefined' && window.rh_android) {
					try {
						window.rh_android.shareForFriends('红樱桃', '快来跟我一起看短剧领金币吧！', versionInfo.url, versionInfo.appUrl)
					} catch (e) {
						console.error('[ent-feed] share error:', e)
					}
				}
			},

			onLoadMore() {
				if (!this.loadingMore && !this.noMore) {
					this.loadFeed(false)
				}
			},

			onRefresh() {
				this.loadFeed(true)
			},

			onSeriesDetail(item) {
				const dramaId = item.dramaId
				if (!dramaId) return
				// 保存当前播放信息到 globalData
				getApp().globalData.vodinfo = {
					dramaId: dramaId,
					title: item._dramaTitle || item.name || '',
					titleImg: item.titleImg || '',
					totalGather: item._dramaTotalGather || 0
				}
				getApp().globalData.seeVodList = []
				uni.navigateTo({
					url: `/pages/player/drama?type=drama&id=${dramaId}&index=0`
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

			onPlayerEnded(e) {
				if (this.redPacketReady || this.redPacketTodayCount >= this.redPacketMaxDaily) return
				this.settleCurrentVideoWatch()
				this.updateRedPacketProgress()
			},

			updateRedPacketProgress() {
				const total = this.getRedPacketTotalSeconds()
				this.redPacketProgress = total / this.redPacketTargetSeconds
				this.redPacketRemainSeconds = Math.max(0, Math.ceil(this.redPacketTargetSeconds - total))
				const wasReady = this.redPacketReady
				this.redPacketReady = total >= this.redPacketTargetSeconds
				if (!wasReady && this.redPacketReady) {
					console.log('[ent-feed] 累计观看达标，金币可领取')
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
				console.log('[ent-feed] 调用广告奖励接口')
				this.$api.adRewardToCoin().then(res => {
					if (res.code === 200) {
						uni.showToast({ title: '奖励已发放', icon: 'success', duration: 1500 })
					}
				}).catch(err => {
					console.error('[ent-feed] 广告奖励接口失败:', err)
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
					console.error('[ent-feed] 刷新用户信息失败:', err)
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
					console.warn('[ent-feed] UniAds 未就绪')
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
						console.log('[ent-feed] 激励视频预加载成功')
					},
					onAdFailed: (err) => {
						console.log('[ent-feed] 激励视频预加载失败', err)
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

/* 顶部分类栏：浮在视频上方 */
.top-bar {
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	z-index: 100;
	background: linear-gradient(to bottom, rgba(0,0,0,0.6) 0%, transparent 100%);
	pointer-events: auto;
}

.tab-scroll, .subtab-scroll {
	white-space: nowrap;
	width: 100%;
}

.tab-list, .subtab-list {
	display: inline-flex;
	padding: 0 16rpx;
}

.tab-item {
	display: inline-block;
	padding: 20rpx 24rpx 10rpx;
	font-size: 30rpx;
	color: rgba(255,255,255,0.7);
	white-space: nowrap;
	position: relative;

	&.active {
		color: #fff;
		font-weight: bold;

		&::after {
			content: '';
			position: absolute;
			bottom: 0;
			left: 50%;
			transform: translateX(-50%);
			width: 36rpx;
			height: 5rpx;
			background: #e84335;
			border-radius: 3rpx;
		}
	}
}

.subtab-item {
	display: inline-block;
	padding: 6rpx 22rpx;
	margin: 0 6rpx;
	font-size: 24rpx;
	color: rgba(255,255,255,0.65);
	border-radius: 30rpx;
	border: 1rpx solid rgba(255,255,255,0.25);
	white-space: nowrap;

	&.active {
		color: #fff;
		background: #e84335;
		border-color: #e84335;
	}
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
	color: rgba(255,255,255,0.6);
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
	top: 4rpx; left: 4rpx;
	width: 124rpx; height: 124rpx;
	border-radius: 50%;
	background: conic-gradient(#FFD9D9 0deg 360deg, #FFD9D9 0deg);
	-webkit-mask: radial-gradient(transparent 58%, #000 64%);
	mask: radial-gradient(transparent 58%, #000 64%);
	opacity: 0.6;
	pointer-events: none;
}

.ent-red-packet-progress {
	position: absolute;
	top: 4rpx; left: 4rpx;
	width: 124rpx; height: 124rpx;
	border-radius: 50%;
	background: conic-gradient(#FF5252 0deg calc(var(--progress, 0) * 360deg), transparent 0deg);
	-webkit-mask: radial-gradient(transparent 58%, #000 64%);
	mask: radial-gradient(transparent 58%, #000 64%);
	opacity: 0.95;
	pointer-events: none;
}

.ent-red-packet-inner {
	position: relative;
	width: 108rpx; height: 108rpx;
	border-radius: 50%;
	background: linear-gradient(135deg, #FF6B6B 0%, #FF8E53 100%);
	box-shadow: inset 0 0 16rpx rgba(255,255,255,0.3);
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	z-index: 1;
}

.ent-red-packet-img {
	width: 58rpx; height: 58rpx;
	z-index: 2;
}

.ent-red-packet-count,
.ent-red-packet-text {
	position: absolute;
	bottom: 16rpx; left: 0;
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

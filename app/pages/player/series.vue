<template>
	<view class="player-page">
		<!-- 骨架层：videoList 加载出来之前用上一页传过来的封面盖住黑屏 -->
		<view v-if="videoList.length === 0" class="skeleton">
			<image v-if="skeletonCover" class="skeleton-cover" :src="skeletonCover" mode="aspectFill" />
			<view class="skeleton-mask"></view>
			<view class="skeleton-info">
				<text class="skeleton-title">{{ seriesName || '' }}</text>
				<text class="skeleton-loading">加载中…</text>
			</view>
		</view>

		<dy-player-app v-if="videoList.length > 0 && isApp" ref="playerRef" :list="videoList" :start-index="startIndex"
			:is-drama="true" :info="seriesInfo" :auto-play="true" :loop-play="false" :auto-next="false"
			:show-back="true" :show-search="false" :show-red-packet="false" :show-share="false"
			:show-gold-unlock="true" :unlock-gold-cost="unlockGoldCost" :unlock-ad-count="5"
			@change="onVideoChange" @ended="onEnded" @like="onLike" @collect="onCollect" @share="onShare"
			@loadMore="onLoadMore" @refresh="onRefresh" @unlock="onUnlock" @lockHit="onLockHit" @back="onBack" />

		<dy-player v-else-if="videoList.length > 0" ref="playerRef" :list="videoList" :start-index="startIndex"
			:is-drama="true" :info="seriesInfo" :auto-play="true" :loop-play="false" :auto-next="false"
			:show-back="true" :show-search="false" :show-red-packet="false" :show-share="false"
			:show-gold-unlock="true" :unlock-gold-cost="unlockGoldCost" :unlock-ad-count="5"
			@change="onVideoChange" @ended="onEnded" @like="onLike" @collect="onCollect" @share="onShare"
			@loadMore="onLoadMore" @refresh="onRefresh" @unlock="onUnlock" @lockHit="onLockHit" @back="onBack" />

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
	import UniAds from '@/common/uniad-sdk.js'
	function getUniAds() {
		return (UniAds && UniAds.showRewardedVideoAd) ? UniAds : null
	}
	function getTakuAds() {
		return (typeof window !== 'undefined' && window.TakuAds) ? window.TakuAds : null
	}
	// #endif

	import dyPlayer from '@/components/dy-player/dy-player.vue'
	import dyPlayerApp from '@/components/dy-player/dy-player-app.vue'

	export default {
		components: { dyPlayer, dyPlayerApp },
		data() {
			return {
				isApp: false,
				seriesName: '',
				seriesInfo: {},
				videoList: [],
				startIndex: 0,
				userInfo: {},
				unlockGoldCost: 0,
				// 骨架层封面：由上一页透传，videoList 就绪前占位，消除黑屏
				skeletonCover: '',
				// 后台增强请求去重（同一集只拉一次点赞/收藏状态）
				enrichedIds: null,
				// uni-ad 激励视频配置
				rewardedAdpid: '1819020042',
				// 间隔广告：每5集展示插屏
				interstitialPlacementId: 'b6a27695ae4bad',
				interstitialLoaded: false,
				adPendingNextIndex: -1,
				isAdShowing: false,
				goldNum: 0
			}
		},
		created() {
			// #ifdef APP-PLUS
			this.isApp = true
			// #endif
			this.enrichedIds = new Set()
		},
		onLoad(option) {
			this.seriesName = option.seriesName ? decodeURIComponent(option.seriesName) : ''
			this.skeletonCover = option.cover ? decodeURIComponent(option.cover) : ''
			this.userInfo = uni.getStorageSync('UserInfo') || {}
			this.seriesInfo = { title: this.seriesName, titleImg: this.skeletonCover }
			this.loadEpisodes()
		},
		onShow() {
			// #ifdef APP-PLUS
			this.initTakuAds()
			// #endif
		},
		onHide() {
			if (this.$refs.playerRef) this.$refs.playerRef.pause()
		},
		onUnload() {
			if (this.$refs.playerRef) this.$refs.playerRef.pause()
		},
		methods: {
			// ================== 数据加载 ==================
			loadEpisodes() {
				if (!this.seriesName) {
					uni.showToast({ title: '参数错误', icon: 'none' })
					return
				}
				uni.showLoading({ title: '加载中' })
				this.$api.getVideoSeriesEpisodes({ seriesName: this.seriesName }).then(res => {
					uni.hideLoading()
					const eps = res.data || []
					this.unlockGoldCost = eps.length > 0 && eps[0].unlockGoldCost ? eps[0].unlockGoldCost : 0
					// 直接用剧集接口数据初始化播放器，点赞/收藏状态改由 onVideoChange 懒加载补齐
					// 好处：省掉 N 次 getVideoInfo 并发请求，也不会因为某集失败而弹「操作失败」
					this.videoList = eps.map((ep, i) => this.formatEpisode(ep, i))
					if (this.videoList.length > 0) {
						this.seriesInfo.titleImg = this.videoList[0].titleImg || this.skeletonCover || ''
						this.initPlayer()
						// 首集的点赞/收藏状态立即懒拉一次，其余靠 onVideoChange 触发
						this.enrichVideoInfo(this.videoList[0].id)
					}
				}).catch(err => {
					uni.hideLoading()
					console.error('[series] loadEpisodes error:', err)
					uni.showToast({ title: '加载失败', icon: 'none' })
				})
			},

			/**
			 * 后台静默拉取某一集的点赞/收藏状态，成功后回填播放器。
			 * 走 silentGet，失败不弹全局 toast。
			 */
			enrichVideoInfo(id) {
				if (!id) return
				if (this.enrichedIds && this.enrichedIds.has(id)) return
				this.enrichedIds && this.enrichedIds.add(id)
				this.$api.getVideoInfoSilent({ id }).then(info => {
					const data = (info && info.data) || null
					if (!data) return
					const patch = {
						likeShow: data.isLike == 1 || data.likeShow === true,
						collectShow: data.isCollect == 1 || data.collectShow === true,
						likeCount: data.likeCount || 0,
						collectCount: data.collectCount || 0
					}
					if (this.$refs.playerRef && typeof this.$refs.playerRef.updateVideoInfo === 'function') {
						this.$refs.playerRef.updateVideoInfo(id, patch)
					}
				}).catch(() => {
					// 静默失败：允许下次重试
					this.enrichedIds && this.enrichedIds.delete(id)
				})
			},

			formatEpisode(ep, index) {
				return {
					...ep,
					id: ep.id,
					videoId: ep.id,
					name: ep.name,
					videoUrl: ep.videoUrl || '',
					titleImg: ep.videoCover || '',
					videoCover: ep.videoCover || '',
					content: ep.content || '',
					gather: ep.episodeNum || (index + 1),
					episodeNum: ep.episodeNum,
					isLocked: ep.isLocked,
					isFree: ep.isFree,
					unlockGoldCost: ep.unlockGoldCost,
					coverShow: true,
					object_fit: 'cover',
					sliderShow: true,
					likeShow: false,
					collectShow: false,
					selectWork: true
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
				const id = e && e.item && (e.item.id || e.item.videoId)
				if (id) this.enrichVideoInfo(id)
			},

			onEnded(e) {
				const idx = e && e.index != null ? e.index : (this.$refs.playerRef && this.$refs.playerRef.currentIndex)
				if (idx == null) return
				const total = this.videoList.length
				// 已是最后一集，不自动续播
				if (idx >= total - 1) return

				// 每5集（index 4, 9, 14...）展示一次插屏广告
				if ((idx + 1) % 5 === 0) {
					console.log('[series] 展示间隔插屏广告, index:', idx)
					this.adPendingNextIndex = idx + 1
					this.showIntervalAd()
				} else {
					this.goNext()
				}
			},

			/**
			 * 间隔插屏广告：应用内展示，关闭后继续播放
			 */
			showIntervalAd() {
				if (this.isAdShowing) return
				this.isAdShowing = true

				// #ifdef APP-PLUS
				const Taku = getTakuAds()
				if (!Taku || !Taku.loadInterstitialAd || !Taku.showInterstitialAd) {
					console.warn('[series] Taku 插屏广告不可用，直接续播')
					this.isAdShowing = false
					this.goNext()
					return
				}
				// 安全定时器：10秒超时自动重置并续播（防止广告未正常关闭卡住）
				const safetyTimer = setTimeout(() => {
					if (this.isAdShowing) {
						console.log('[series] 插屏广告超时，重置状态')
						this.isAdShowing = false
						this.adPendingNextIndex = -1
						this.interstitialLoaded = false
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
					this.goNext()
				}
				if (this.interstitialLoaded) {
					Taku.showInterstitialAd(options, (res) => {
						console.log('[series] showInterstitialAd result:', res)
						onFinish()
					})
					return
				}
				Taku.loadInterstitialAd(options, {
					onAdLoaded: () => {
						Taku.showInterstitialAd(options, (res) => {
							console.log('[series] showInterstitialAd result:', res)
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
			 * 播放下一集
			 */
			goNext() {
				if (this.$refs.playerRef && typeof this.$refs.playerRef.goNext === 'function') {
					this.$refs.playerRef.goNext()
				}
			},

			onLike(item) {},
			onCollect(item) {},
			onShare(item) {},
			onLoadMore() {},
			onRefresh() {
				this.loadEpisodes()
			},

			// ================== 解锁 ==================
			onUnlock({ type, item }) {
				if (type === 'ad') {
					this.unlockByAd(item)
				} else if (type === 'gold') {
					this.unlockByGold(item)
				}
			},

			onLockHit({ index, item }) {
				this.unlockByAd(item)
			},

			unlockByAd(item) {
				if (!item || !item.videoId) return
				if (!this.userInfo.id) {
					uni.showToast({ title: '请先登录', icon: 'none' })
					return
				}
				// #ifdef APP-PLUS
				this.showUnlockAd(item.videoId)
				// #endif
				// #ifndef APP-PLUS
				this.reloadEpisodes()
				// #endif
			},

			showUnlockAd(videoId) {
				// #ifndef APP-PLUS
				return
				// #endif
				if (this.isAdShowing) return
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
				this.isAdShowing = true
				let didReward = false
				const extraData = JSON.stringify({ type: '5', value: String(videoId) })
				uni.showToast({ title: '广告加载中，请稍候...', icon: 'none', duration: 2000 })
				UniAds.showRewardedVideoAd({
					adpid: this.rewardedAdpid,
					extraData: extraData,
					userId: String(userInfo.id || '')
				}, {
					onAdShow: () => {},
					onAdClosed: () => {
						this.isAdShowing = false
						// 延迟刷新，等待后端回调解锁
						setTimeout(() => this.reloadEpisodes(), 2000)
					},
					onReward: () => {
						didReward = true
						// uni-ad 没有服务端验证回调，前端上报解锁
						this.$api.uniAdCallback({
							extraData: extraData,
							adpid: this.rewardedAdpid,
							requestId: `${userInfo.id || 0}_${Date.now()}_${Math.floor(Math.random() * 1000000)}`,
							timestamp: Date.now()
						}).then(apiRes => {
							console.log('[series] uni-ad 解锁回调结果:', apiRes)
						}).catch(err => {
							console.error('[series] uni-ad 解锁回调失败:', err)
						})
					},
					onAdFailed: (err) => {
						this.isAdShowing = false
						console.error('[series] 解锁激励视频失败:', err)
						uni.showToast({ title: '广告加载失败，请重试', icon: 'none' })
					},
					onAdClicked: () => {}
				})
			},

			unlockByGold(item) {
				if (!item || !item.videoId) return
				this.$api.videoSeriesMoneyUnlock({ videoId: item.videoId }).then(res => {
					uni.showToast({ title: '解锁成功', icon: 'none' })
					this.reloadEpisodes()
				}).catch(err => {
					console.error('[series] unlockByGold error:', err)
				})
			},

			reloadEpisodes() {
				this.$api.getVideoSeriesEpisodes({ seriesName: this.seriesName }).then(res => {
					const eps = res.data || []
					const list = eps.map((ep, i) => this.formatEpisode(ep, i))
					this.videoList = list
					if (this.$refs.playerRef && typeof this.$refs.playerRef.refreshList === 'function') {
						this.$refs.playerRef.refreshList(list, true)
					} else {
						this.initPlayer()
					}
				}).catch(err => {
					console.error('[series] reloadEpisodes error:', err)
				})
			},

			// ================== uni-ad 激励视频 ==================
			initTakuAds() {
				// #ifndef APP-PLUS
				return
				// #endif
				const UniAds = getUniAds()
				if (UniAds && UniAds.loadRewardedVideoAd) {
					const userInfo = this.userInfo || uni.getStorageSync('UserInfo') || {}
					UniAds.loadRewardedVideoAd({
						adpid: this.rewardedAdpid,
						extraData: JSON.stringify({ type: '5', value: '' }),
						userId: String(userInfo.id || '')
					}, {
						onAdLoaded: () => console.log('[series] 激励视频预加载成功'),
						onAdFailed: (err) => console.log('[series] 激励视频预加载失败', err),
						onAdShow: () => {},
						onAdClosed: () => {},
						onReward: () => {},
						onAdClicked: () => {}
					})
				}
				// 同时预加载插屏，提高第5集广告展示成功率
				this.preloadInterstitial()
			},

			// ================== 返回 ==================
			onBack() {
				uni.navigateBack({
					fail: () => uni.switchTab({ url: '/pages/travel/index' })
				})
			},

			closeGoldPopup() {
				this.$refs.goldPopup && this.$refs.goldPopup.close()
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

	/* 骨架层：接口回来前用封面 + 标题占位，替代原本纯黑等待 */
	.skeleton {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 2;
		overflow: hidden;
	}

	.skeleton-cover {
		width: 100%;
		height: 100%;
	}

	.skeleton-mask {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background: linear-gradient(180deg, rgba(0, 0, 0, 0.3) 0%, rgba(0, 0, 0, 0.6) 100%);
	}

	.skeleton-info {
		position: absolute;
		left: 40rpx;
		right: 40rpx;
		bottom: 180rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		gap: 16rpx;
	}

	.skeleton-title {
		font-size: 34rpx;
		color: #ffffff;
		font-weight: bold;
		text-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.6);
		max-width: 100%;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.skeleton-loading {
		font-size: 26rpx;
		color: rgba(255, 255, 255, 0.8);
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

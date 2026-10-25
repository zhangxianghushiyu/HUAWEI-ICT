<template>
	<view class="collect-list">
		<!-- 顶部：搜索 + 分类 -->
		<view class="top">
			<view class="search-row">
				<view class="search-input">
					<uv-search v-model="searchKeyword" bgColor="#ffffff" inputAlign="center" height="35"
						:showAction="false" searchIconColor="#F14835" animation @search="onSearch" />
				</view>
				<text v-if="isSearching" class="search-cancel" @tap="onCancelSearch">取消</text>
			</view>

			<scroll-view v-if="!isSearching" scroll-x class="tab-scroll">
				<view class="tab-list">
					<view class="tab-item" :class="{ active: selectedCategoryId === 0 }" @click="onCategoryClick({ classifyId: 0, name: '全部' })">
						全部
					</view>
					<view class="tab-item" v-for="(item, index) in categoryList" :key="item.classifyId"
						:class="{ active: selectedCategoryId === item.classifyId }"
						@click="onCategoryClick(item)">
						{{ item.name }}
					</view>
				</view>
			</scroll-view>
		</view>

		<!-- 系列网格 -->
		<view class="list">
			<view class="list-item" v-for="(item, index) in seriesList" :key="index" @click="onSeriesClick(item)">
				<view class="item-box">
					<image class="item-img" :src="item.cover" mode="aspectFill"></image>
					<view class="item-count">{{ item.episodeCount }}集</view>
					<view v-if="$store.state.isShow" class="play-count">
						<image class="play-img" src="@/static/images/play2.png"></image>
						{{ item.playCount < 10000 ? item.playCount : (item.playCount / 10000).toFixed(0) + '万+' }}
					</view>
				</view>
				<view class="item-text">{{ item.seriesName }}</view>
			</view>
		</view>
		<view v-if="seriesList.length === 0 && !loading" class="empty">
			<text>暂无内容</text>
		</view>
		<uv-load-more :status="moreStatus" />

		<!-- #ifdef APP-PLUS -->
		<view class="float-coin-btn" @click="onFloatCoinClick">
			<text class="float-coin-text">领金币</text>
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

	export default {
		data() {
			return {
				categoryList: [],
				selectedCategoryId: 0,
				searchKeyword: '',
				isSearching: false,
				seriesList: [],
				pageNum: 1,
				pageSize: 15,
				moreStatus: 'loadmore',
				loading: false,

				// #ifdef APP-PLUS
				// 悬浮领金币广告
				rewardedAdpid: '1819020042',
				isAdShowing: false,
				adDidReward: false,
				adDidShow: false
				// #endif
			}
		},
		onLoad() {
			// #ifdef APP-PLUS
			this.initTakuAds()
			// #endif
		},
		onShow() {
			// 只有首次进入（列表为空）才自动加载，避免切换 tab 时闪屏刷新
			if (this.categoryList.length === 0) {
				this.loadCategories()
			}
			if (this.seriesList.length === 0) {
				this.getList(true)
			}
		},
		onReachBottom() {
			if (this.moreStatus != 'nomore') this.getList(false)
		},
		onPullDownRefresh() {
			this.getList(true)
		},
		methods: {
			loadCategories() {
				this.$api.getVideoSeriesCategories().then(res => {
					this.categoryList = res.data || []
				}).catch(err => {
					console.error('[collect-list] loadCategories error:', err)
				})
			},
			onCategoryClick(item) {
				if (this.selectedCategoryId === item.classifyId) return
				this.selectedCategoryId = item.classifyId
				this.getList(true)
			},
			onSearch() {
				const kw = (this.searchKeyword || '').trim()
				if (!kw) {
					uni.showToast({ title: '请输入搜索内容', icon: 'none' })
					return
				}
				this.isSearching = true
				this.getList(true)
			},
			onCancelSearch() {
				this.searchKeyword = ''
				this.isSearching = false
				this.getList(true)
			},
			getList(refresh = false) {
				if (refresh) {
					this.pageNum = 1
					this.seriesList = []
					this.moreStatus = 'loadmore'
				}
				if (this.moreStatus == 'loading') return
				this.loading = true
				this.moreStatus = 'loading'

				const request = this.isSearching
					? this.$api.searchVideoSeries({ name: this.searchKeyword.trim() })
					: this.$api.getVideoSeriesList({
						pageNum: this.pageNum,
						pageSize: this.pageSize,
						categoryId: this.selectedCategoryId || null
					})

				request.then(res => {
					this.loading = false
					if (this.isSearching) {
						// 搜索返回完整列表，不分页
						this.seriesList = res.data || []
						this.moreStatus = 'nomore'
					} else {
						const list = (res.data && res.data.list) || []
						this.seriesList = this.seriesList.concat(list)
						this.moreStatus = (res.data && res.data.isLastPage) ? 'nomore' : 'loadmore'
						this.pageNum++
					}
				}).catch(err => {
					this.loading = false
					this.moreStatus = 'loadmore'
					console.error('[collect-list] getList error:', err)
				})
			},
			onSeriesClick(item) {
				if (!item.seriesName) return
				const cover = item.cover ? `&cover=${encodeURIComponent(item.cover)}` : ''
				uni.navigateTo({
					url: `/pages/player/series?seriesName=${encodeURIComponent(item.seriesName)}${cover}`
				})
			},

			// #ifdef APP-PLUS
			// 悬浮领金币：uni-ad 激励视频
			initTakuAds() {
				const UniAds = getUniAds()
				if (!UniAds || !UniAds.loadRewardedVideoAd) return
				const userInfo = uni.getStorageSync('UserInfo') || {}
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
						console.log('[collect-list] 激励视频预加载成功')
					},
					onAdFailed: (err) => {
						console.log('[collect-list] 激励视频预加载失败', err)
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
						if (didReward) {
							self.claimAdReward()
						}
					},
					onReward: () => {
						self.adDidReward = true
					},
					onAdClicked: () => {}
				}
			},

			onFloatCoinClick() {
				if (this.isAdShowing) {
					uni.showToast({ title: '广告正在展示，请稍后', icon: 'none' })
					return
				}
				const UniAds = getUniAds()
				if (!UniAds || !UniAds.showRewardedVideoAd) {
					uni.showToast({ title: '广告组件未就绪', icon: 'none' })
					return
				}
				const userInfo = uni.getStorageSync('UserInfo') || {}
				if (!userInfo.id) {
					uni.showToast({ title: '请先登录', icon: 'none' })
					return
				}
				this.adDidReward = false
				this.adDidShow = false
				uni.showToast({ title: '广告加载中，请稍候...', icon: 'none', duration: 2000 })
				UniAds.showRewardedVideoAd({
					adpid: this.rewardedAdpid,
					extraData: JSON.stringify({ type: '2', value: '3' }),
					userId: String(userInfo.id || '')
				}, this.rewardedAdListener())
			},

			claimAdReward() {
				this.$api.adRewardToCoin().then(res => {
					console.log('[collect-list] 广告奖励接口返回:', res)
					if (res.code === 200) {
						uni.showToast({ title: '+100 金币', icon: 'none' })
					}
				}).catch(err => {
					console.error('[collect-list] 广告奖励接口失败:', err)
					uni.showToast({ title: '奖励发放失败，请稍后重试', icon: 'none' })
				})
			}
			// #endif
		}
	}
</script>

<style lang="scss" scoped>
	.collect-list {
		background: url('/static/images/bg.png');
		background-size: cover;
		min-height: 100vh;
		padding-bottom: 40rpx;
	}

	.top {
		position: sticky;
		top: 0;
		z-index: 99;
		padding: 16rpx 20rpx;
		background: url('/static/images/bg.png');
		background-size: cover;

		.search-row {
			display: flex;
			align-items: center;

			.search-input {
				flex: 1;
			}

			.search-cancel {
				margin-left: 16rpx;
				font-size: 28rpx;
				color: #5D3A1A;
			}
		}

		.tab-scroll {
			white-space: nowrap;
			margin-top: 12rpx;
		}

		.tab-list {
			display: inline-flex;
		}

		.tab-item {
			padding: 10rpx 28rpx;
			margin-right: 16rpx;
			font-size: 26rpx;
			color: #5D3A1A;
			background: #FFFDF9;
			border-radius: 32rpx;
			border: 1px solid rgba(200, 130, 60, 0.1);

			&.active {
				color: #FFFFFF;
				background: #E8783B;
				border-color: #E8783B;
			}
		}
	}

	.list {
		display: flex;
		flex-wrap: wrap;
		padding: 2vw;

		.list-item {
			width: calc(96vw / 3);
			padding: 10rpx;

			.item-box {
				position: relative;
				border-radius: 32rpx;
				box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

				.item-img {
					width: 100%;
					height: 300rpx;
					border-radius: 32rpx;
					background-color: #f0f0f0;
				}

				.item-count {
					position: absolute;
					right: 12rpx;
					bottom: 12rpx;
					padding: 2rpx 14rpx;
					font-size: 20rpx;
					color: #FFFFFF;
					background: rgba(0, 0, 0, 0.55);
					border-radius: 16rpx;
				}

				.play-count {
					position: absolute;
					bottom: 12rpx;
					left: 12rpx;
					display: flex;
					align-items: center;
					font-weight: 500;
					font-size: 23rpx;
					color: #FFFFFF;

					.play-img {
						width: 16rpx;
						height: 21rpx;
						margin-right: 8rpx;
					}
				}
			}

			.item-text {
				line-height: 2;
				font-size: 28rpx;
				width: 100%;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
				color: #5D3A1A;
			}
		}
	}

	.empty {
		padding: 80rpx 0;
		text-align: center;
		color: #8B6B4A;
		font-size: 28rpx;
	}

	.float-coin-btn {
		position: fixed;
		right: 30rpx;
		bottom: 180rpx;
		width: 110rpx;
		height: 110rpx;
		border-radius: 50%;
		background: linear-gradient(135deg, #FFD700, #FFA500);
		box-shadow: 0 8rpx 24rpx rgba(255, 165, 0, 0.4);
		display: flex;
		align-items: center;
		justify-content: center;
		z-index: 100;
	}

	.float-coin-text {
		font-size: 24rpx;
		color: #FFFFFF;
		font-weight: bold;
		line-height: 1.2;
		text-align: center;
	}
</style>

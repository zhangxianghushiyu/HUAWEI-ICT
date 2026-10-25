<template>
	<view class="content">
		<view class="top">
			<view class="topView">
								<view class="topView-item1">
					<uv-tabs v-if="isShowTabs" :list="tabList" @click="tabClick" :lineColor="`url(${tabBg}) 100% 100%`"
						lineHeight="8" :activeStyle="{
								fontWeight: 'bold',
								fontSize:'20px'
							}" :inactiveStyle="{
								color: '#606266',
								fontSize:'18px'
							}" itemStyle="padding-left: 30rpx; padding-right: 30rpx;padding-bottom:15rpx;padding-top:15rpx"
						:current="activeIndex" @change="changeTab2" />
				</view>
				<view class="view" @click="toPage('/pages/index/query')">
					<uv-search placeholder="搜索短剧" inputAlign="center" height="35" :showAction="false"
						searchIconColor="#F14835" animation disabled />
				</view>
			</view>
			<scroll-view scroll-x style="white-space: nowrap;">
				<view class="subTab">
					<view class="subTab-item" v-for="(item,index) in subTabList"
						:class="{active:categorySelected == item.id}" :key="index" @click="subTabClick(item,index)">
						{{item.name}}
					</view>
				</view>
			</scroll-view>
		</view>
		<pc-turns v-if="lunboList.length>0&&isShowLunbo" :data="lunboList" @achieve="selection" />
		<view class="list">
			<view class="list-item" v-for="(item,index) in displayList" :key="index"
				@click="handleItemClick(item, index)">
				<!-- 原生广告 -->
				<template v-if="item.isAd">
					<view class="item-box">
						<view style="background:#f0f0f0; width:100%; height:300rpx; border-radius:32rpx; display:flex; align-items:center; justify-content:center;">
							<text style="color:#999; font-size:24rpx;">广告位</text>
						</view>
					</view>
					<view class="item-text ad-text">广告</view>
				</template>
				<!-- 短剧卡片 -->
				<template v-else>
					<view class="item-box">
						<image class="item-img" :src="item.titleImg" mode="scaleToFill"></image>
						<view v-show="tabList[tabIndex] && tabList[tabIndex].value == 3" class="tag-top">
							{{index +1}}
						</view>
						<view v-show="$store.state.isShow" class="play-count">
							<image class="play-img" src="@/static/images/play2.png"></image>
							{{ item.playCount < 10000 ? item.playCount : (item.playCount / 10000).toFixed(0) + '万+'}}
						</view>
					</view>
					<view class="item-text">{{item.title}}</view>
				</template>
			</view>
		</view>
		<uv-load-more :status="moreStatus" />
		<!-- 筛选页面 -->
		<view class="filter" v-if="isShowFilter">
			<view class="filter-bg">
				<view class="filter-bg-title">
					<span>筛选</span>
					<uni-icons @tap="isShowFilter=false" style="margin-left: 250rpx;" type="down" size="22"></uni-icons>
				</view>
				<view class="filter-body">
					<scroll-view scroll-y="true" class="filter-body-scroll">
						<view class="filter-item" v-for="(item,index) in videoTypes">
							<view class="filter-item-body">
								<view class="filter-item-body1">
									<span>{{item.name}}</span>
								</view>
								<view @tap="chooseNextType(index,item,item2)"
									:class="categorySelected==item2.id?'filter-item-body3':'filter-item-body2'"
									v-for="item2 in item.children">
									<span>{{item2.name}}</span>
								</view>
							</view>
						</view>
					</scroll-view>
				</view>
				<view class="filter-btns">
					<view class="filter-btns-cancel" @tap="isShowFilter=false">
					</view>
					<view class="filter-btns-confirm" @tap="chooseNextType2()">
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	const deviceInfo = uni.getSystemInfoSync()
	import BaseHeader from '@/components/BaseHeader.vue'
	import tabBg from '@/static/images/change/tabBg.png'
	import privacyConsent from '@/common/privacy-consent.js'

	export default {
		components: {
			BaseHeader
		},
		data() {
			return {
				moreStatus: 'loadmore',
				tabIndex: 0,
				isShowFilter: false,
				tabList: [],
				lunboList: [],
				firstType: -1,
				activeIndex: 0,
				secondType: -1,
				isShowLunbo: true,
				subTabIndex: 0,
				subTabList: [],
				tabBg: tabBg,
				param: {
					pageNum: 1,
					pageSize: 15,
				},
				dataList: [],
				userInfo: {},
				videoTypes: [],
				versionInfo: {},
				isShowTabs: false,
				categorySelected: 0,
				displayList: [],
				adInsertIndex: 5,
				takuInitialized: false
			}
		},
		onLoad() {
			this.userInfo = uni.getStorageSync('UserInfo')
			this.versionInfo = uni.getStorageSync('Version')
		},
		onShow() {
			this.isShowTabs = false
			setTimeout(() => {
				this.activeIndex = 0
				this.isShowTabs = true
			}, 100)
			uni.setStorageSync('wxChatPage', false);
			this.getCategory()

			// 初始化 Taku SDK（只初始化一次）
			if (!this.takuInitialized) {
				this.initTakuSDK()
				this.takuInitialized = true
			}

			// #ifdef APP-PLUS
			if (typeof rh_android !== 'undefined' && rh_android.pauseMusic) {
				if (typeof rh_android !== 'undefined' && rh_android.pauseMusic) {
					rh_android.pauseMusic();
				}
			}
			// #endif
		},
		onHide() {
			uni.setStorageSync('wxChatPage', false);
		},
		onReachBottom() {
			if (this.moreStatus != 'nomore')
				this.getList()
		},
		onPullDownRefresh() {
			this.getList(true)
		},
		methods: {
			
			/**
			 * 在列表中插入原生广告占位
			 */
			insertAdIntoList() {
				if (!this.dataList || this.dataList.length === 0) return

				this.displayList = [...this.dataList]
				const insertIndex = this.adInsertIndex
				if (this.displayList.length > insertIndex && !this.displayList[insertIndex].isAd) {
					this.displayList.splice(insertIndex, 0, { isAd: true })
				}
			},

			/**
			 * 处理列表项点击（广告点击由原生组件内部处理）
			 */
			handleItemClick(item, index) {
				if (item.isAd) {
					return
				}
				this.toPage('/pages/index/video-see?pageNum=', item)
			},

			/**
			 * 原生广告事件回调
			 */
			onNativeAdLoaded() {
				console.log('首页原生广告加载成功')
			},
			onNativeAdFailed(err) {
				console.log('首页原生广告加载失败', err)
			},
			onNativeAdClick(index) {
				console.log('首页原生广告被点击，index=', index)
			},

			// ===== 原有方法 =====

			changeTab2(e) {
				this.firstType = e.id
			},
			chooseNextType(index, item, item2) {
				this.categorySelected = item2.id
				uni.setStorageSync('firstType', item.id)
				uni.setStorageSync('secondType', item2.id)
				uni.setStorageSync('chooseIndex', index)
			},
			goBack() {
				// #ifdef H5
				window.location.href = '/#/pages/look-at/index';
				// #endif
				// #ifdef APP-PLUS || MP-WEIXIN
				uni.reLaunch({
					url: '/pages/look-at/indexx',
					fail: () => {
						uni.navigateTo({
							url: '/pages/look-at/index'
						});
					}
				});
				// #endif
			},
			chooseNextType2() {
				this.firstType = uni.getStorageSync('firstType')
				this.secondType = uni.getStorageSync('secondType')
				this.isShowFilter = false
				this.activeIndex = uni.getStorageSync('chooseIndex')
				this.getCategory3(this.firstType)
			},
			getAllTypes() {
				this.$api.getAllTypes({
					pageNum: 1,
					pageSize: 10,
				}).then(res => {
					this.videoTypes = res.data.list
				})
			},
			openFilter() {
				this.getAllTypes()
				this.isShowFilter = true
			},
			selection(e) {
				this.toPage('/pages/index/video-see?pageNum=', e)
			},
			tabClick(e) {
				this.tabIndex = e.index
				this.getCategory2(e.id)
				if (e.value == 1) {
					this.isShowLunbo = true
				} else {
					this.isShowLunbo = false
				}
			},
			subTabClick(item, index) {
				this.categorySelected = item.id
				this.secondType = item.id
				this.subTabIndex = index
				this.getList(true)
			},
			toPage(url, item = null) {
				if (url == '/pages/index/video-see?pageNum=') {
					if (!this.$store.state.isShow) {
						this.$utils.previewImage(0, [item.titleImg])
						return
					}
					if (item.isVip == 1 && this.userInfo.isVip == 0) {
						this.$utils.toPage('/pages/mine/createVip')
						return
					}

					// 跳转到短剧独立播放器页（带返回按钮）
					const index = item.watchGather ? (item.watchGather - 1) : 0
					const playerUrl = `/pages/player/drama?type=drama&id=${item.dramaId}&index=${index}`
					// 保留 globalData 兼容旧逻辑
					getApp().globalData.vodinfo = item
					this.$utils.toPage(playerUrl)
					return
				}
				this.$utils.toPage(url)
			},
			getCategory() {
				this.$api.getShotType({
					pageNum: 1,
					pageSize: 10
				}).then(res => {
					this.tabList = res.data.list
					this.firstType = this.tabList[0].id
					this.getCategory2(this.tabList[0].id)
				})
			},
			getCategory2(id) {
				this.$api.getShotNextType({
					pageNum: 1,
					pageSize: 10,
					parentId: id
				}).then(res => {
					this.subTabList = res.data.list
					this.secondType = this.subTabList[0].id
					this.categorySelected = this.secondType
					this.getList(true)
				})
			},
			getCategory3(id) {
				this.$api.getShotNextType({
					pageNum: 1,
					pageSize: 10,
					parentId: id
				}).then(res => {
					this.subTabList = res.data.list
					this.categorySelected = this.secondType
					this.getList(true)
				})
			},
			getList(refresh = false) {
				if (refresh) {
					this.param.pageNum = 1
					this.dataList = []
					this.displayList = []
				}
				this.moreStatus = 'loading'
				const param = {
					pageNum: this.param.pageNum,
					pageSize: this.param.pageSize,
					mainType: this.firstType,
					categoryId: this.secondType
				}
				this.$api.homePage(param).then(res => {
					this.lunboList = []
					for (let i = 0; i < 3 && i < res.data.list.length; i++) {
						this.lunboList.push({
							src: res.data.list[i].titleImg,
							dramaId: res.data.list[i].dramaId,
							watchGather: res.data.list[i].watchGather
						})
					}
					this.dataList = this.dataList.concat(res.data.list)
					// 更新展示列表并重新插入广告
					this.insertAdIntoList()
					if (res.data.isLastPage) this.moreStatus = 'nomore'
					else this.moreStatus = 'loadmore'
					this.param.pageNum++
				})
			},
			/**
			 * 初始化 Taku SDK
			 */
			initTakuSDK() {
				// 隐私合规（报告4.1 场景2/场景5）：未同意隐私政策前不初始化广告 SDK
				if (!privacyConsent.isPrivacyAgreed()) {
					console.warn('[Taku] 隐私政策未同意，跳过 SDK 初始化')
					return
				}
				try {
					const TakuAd = uni.requireNativePlugin('TakuAdBridge')
					if (!TakuAd) {
						console.warn('[Taku] TakuAdBridge 插件未找到')
						return
					}
					TakuAd.init({
						appId: 'a6a2768f6227eb',
						appKey: 'a6af687f456b60b76907f9a9b8cbbc73f'
					}, (res) => {
						console.log('[Taku] init result:', res)
					})
				} catch (e) {
					console.error('[Taku] 初始化异常:', e)
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		background: url('/static/images/bg.png');
		background-size: cover;
		max-width: 100vw;
		overflow-x: hidden;
	}

	.top {
		position: sticky;
		top: 0;
		z-index: 999;
		background: url('/static/images/bg.png');
	}

	.body {
		position: absolute;
		left: 0;
		right: 0;
		bottom: 10rpx;
		top: 120rpx;

		.body-item {
			height: 100%;
		}
	}

	.topView {
		width: 100%;
		display: flex;
		align-items: center;
	}

	.topView-item1 {
		width: 90%;
	}

	.view {
		padding: 30rpx;
	}

	.subTab {
		width: calc(100vw - 30rpx);
		display: flex;
		align-items: center;
		color: #5D3A1A;

		.subTab-item {
			width: 94rpx;
			height: 60rpx;
			background: #FFFDF9;
			border-radius: 32rpx;
			border: 2px solid rgba(200,130,60,0.1);
			margin: 15rpx 10rpx;
			text-align: center;
			line-height: 50rpx;

			&.active {
				color: #E8783B;
				background: #FFFDF9;
				border-radius: 32rpx;
				border: 2px solid #E8783B;
			}
		}

		.subTab-right {
			width: 60rpx;
			height: 60rpx;
			background: #FFFDF9;
			border-radius: 32rpx;
			text-align: center;
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
				}

				.play-count {
					position: absolute;
					bottom: 10rpx;
					display: flex;
					align-items: center;
					left: 10rpx;
					font-weight: 500;
					font-size: 23rpx;
					color: #FFFFFF;

					.play-img {
						width: 16rpx;
						height: 21rpx;
						margin-right: 10rpx;
					}
				}

				.tag-top {
					position: absolute;
					top: 0;
					right: 0;
					color: #FFFFFF;
					font-size: 20rpx;
					background-color: #E8783B;
					border-radius: 10rpx;
					text-align: center;
					width: 50rpx;
					height: 50rpx;
					line-height: 50rpx;
					font-size: 25rpx;
				}

				.tag-vip {
					position: absolute;
					top: 0;
					right: 0;
					color: #5D3A1A;
					font-size: 20rpx;
					background-color: #FFFDF9;
					border-radius: 10rpx;
					text-align: center;
					width: 120rpx;
					height: 50rpx;
					line-height: 50rpx;
					font-size: 25rpx;
				}

				.ad-tag-badge {
					position: absolute;
					top: 10rpx;
					right: 10rpx;
					background-color: rgba(0,0,0,0.6);
					color: #fff;
					font-size: 20rpx;
					padding: 4rpx 12rpx;
					border-radius: 20rpx;
					z-index: 10;
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

			.ad-text {
				color: #E8783B;
			}
		}
	}

	.filter {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 99999;
		background: rgba(0, 0, 0, 0.5);

		.filter-bg {
			width: 750rpx;
			height: 952rpx;
			position: absolute;
			bottom: 0;
			background: #FFFDF9;
			border-radius: 32rpx 32rpx 0rpx 0rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.filter-bg-title {
				display: flex;
				align-items: center;
				font-weight: 700;
				font-size: 34rpx;
				color: #5D3A1A;
				margin: 20rpx;
				margin-left: 340rpx;
			}

			.filter-body {
				position: absolute;
				top: 100rpx;
				left: 0;
				right: 0;
				bottom: 130rpx;

				.filter-body-scroll {
					position: relative;
					height: 98%;

					.filter-item {
						display: flex;
						margin-top: 20rpx;
						flex-direction: column;

						.filter-item-body {
							display: flex;
							flex-wrap: wrap;
							align-items: center;

							.filter-item-body1 {
								width: 100%;
								margin-left: 30rpx;
								font-weight: 700;
								font-size: 26rpx;
								color: #5D3A1A;
							}

							.filter-item-body2 {
								width: 212rpx;
								height: 84rpx;
								margin-left: 30rpx;
								margin-top: 33rpx;
								text-align: center;
								line-height: 84rpx;
								background: #FFFDF9;
								border-radius: 32rpx;
								border: 2px solid rgba(200,130,60,0.1);
							}

							.filter-item-body3 {
								width: 212rpx;
								height: 84rpx;
								margin-left: 30rpx;
								margin-top: 33rpx;
								text-align: center;
								line-height: 84rpx;
								background: linear-gradient(135deg, #E8783B, #F4A460);
								border-radius: 32rpx;
								color: #fff;
								border: 2px solid rgba(200,130,60,0.1);
							}
						}
					}
				}
			}

			.filter-btns {
				position: absolute;
				bottom: 26rpx;
				left: 0;
				right: 0;
				display: flex;
				justify-content: space-around;
				align-items: center;

				.filter-btns-cancel {
					width: 335rpx;
					height: 98rpx;
					background: url('/static/images/change/filter-cancel.png');
					background-size: cover;
				}

				.filter-btns-confirm {
					width: 332rpx;
					height: 96rpx;
					background: url('/static/images/change/filter-confirm.png');
					background-size: cover;
				}
			}
		}
	}
</style>
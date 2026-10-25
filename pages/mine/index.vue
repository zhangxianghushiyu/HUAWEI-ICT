<template>
	<view>
		<BaseHeader bgImageUrl="/static/mine/mineBg1.png">
			<view class="head-title">我的</view>
		</BaseHeader>
		<view class="mine">
			<image class="mine-bg" src="/static/mine/mineBg.png" mode="widthFix" />
			<!-- 占位置 -->
			<!-- #ifdef MP-WEIXIN -->
			<view class="" style="height: 10rpx;"></view>
			<!-- #endif -->
			<!-- #ifndef MP-WEIXIN -->
			<view class="" style="height: 20rpx;"></view>
			<!-- #endif -->
			<!-- 个人区域 -->
			<view v-if="!isLogin" class="mine-me" @click="toPage('/pages/mine/login')">
				<view class="mine-me-left">
					<image style="width: 120rpx;height: 120rpx;margin-right: 16rpx;"
						src="@/static/images/change/unLogin.png" mode="scaleToFill"></image>
					<view class="">
						<view style="font-weight: bold;font-size: 36rpx;color: #333333;margin-bottom: 16rpx;">点击登录
						</view>
					</view>
				</view>
				<image class="mine-me-right" src="@/static/images/left-arr.png" mode="scaleToFill"></image>
			</view>
			<view v-else class="mine-me">
				<view class="mine-me-left">
					<image style="width: 120rpx;height: 120rpx;margin-right: 16rpx; border-radius: 50%;"
						:src="userInfo.avatar" mode="scaleToFill">
					</image>
					<view class="">
						<view style="font-weight: bold;font-size: 36rpx;color: #333333;margin-bottom: 16rpx;">
							{{userInfo.nickName}}
						</view>
						<view style="font-weight: 500;font-size: 24rpx;color: #666;">ID：{{userInfo.id}}</view>
					</view>
				</view>
				<image @click="toPage('/pages/mine/edit-user')" class="mine-me-right" src="@/static/mine/9.png"
					mode="scaleToFill" />
			</view>
			<!-- vip -->
			<view v-if="$store.state.isShow" class="mine-vip">
				<view class="mine-vip-top">
					<view class="mine-vip-top-left">
						<image src="/static/mine/10.png" mode=""></image>
						<span>我的推广</span>
					</view>
					<view class="mine-vip-top-right" @tap="toPromotion()">
						<image src="/static/mine/8.png" mode=""></image>
					</view>
				</view>
				<view class="mine-vip-bottom">
					<view class="mine-vip-bottom-item">
						<span>{{userInfo.sumJuniorCount}}</span>
						<span style="font-weight: 500;font-size: 22rpx;">总人数</span>
					</view>
					<view class="mine-vip-bottom-item">
						<span>{{userInfo.todayGetMoney}}</span>
						<span style="font-weight: 500;font-size: 22rpx;">今日收益</span>
					</view>
					<view class="mine-vip-bottom-item">
						<span>{{userInfo.todayJuniorCount}}</span>
						<span style="font-weight: 500;font-size: 22rpx;">今日新增</span>
					</view>
					<view class="mine-vip-bottom-item">
						<span>{{userInfo.yesterdayJuniorCount}}</span>
						<span style="font-weight: 500;font-size: 22rpx;">昨日新增</span>
					</view>
				</view>
			</view>
			<!-- 占位置 -->
			<view class="" style="height: 36rpx;"></view>
			<!-- 占位置 -->
			<view class="" style="height: 40rpx;"></view>
			<!-- three icon -->
			<view v-if="$store.state.isShow" class="three-icon" @click="toPage('/pages/mine/shop')">
			</view>

			<!-- 占位置 -->
			<view class="" style="height: 36rpx;"></view>
			<!--  福利钱包 -->
			<view class="mine-lookbox2" style="height: 228rpx;">
				<view class="mine-lookbox2-item" style="margin-left: 50rpx;">
					<view class="mine-lookbox2-item-top" @tap="toUrl()">
						<span>金币余额(币）</span>
						<image src="/static/right.png" mode=""></image>
					</view>
					<span @tap="openExchange()"
						style="font-weight: bold;font-size: 46rpx;color: #333333;">{{userInfo.goldCoin}}</span>
					<span @tap="openExchange()" style="font-weight: 500;font-size: 20rpx;color: #CFAD9D;">金币兑换为现金</span>
				</view>
				<view class="number-box-line" style="margin-left: 90rpx;">
				</view>
				<view class="mine-lookbox2-item" style="margin-left: 90rpx;" @tap="toUrl()">
					<view class="mine-lookbox2-item-top">
						<span>现金余额(元）</span>
						<image src="/static/right.png" mode=""></image>
					</view>
					<span style="font-weight: bold;font-size: 46rpx;color: #333333;">{{userInfo.cash}}</span>
					<span style="font-weight: 500;font-size: 20rpx;color: #CFAD9D;">近一天已有5.5万+人成功提现!</span>
				</view>
				<image @tap="toWithin()" class="within" src="/static/3.png" mode=""></image>
			</view>
			<!-- 占位置 -->
			<view class="" style="height: 23rpx;"></view>
			<view class="four-icon">
				<view class="mine-more-top">
					<view style="font-weight: bold;font-size: 30rpx;color: #333333;">更多服务</view>
				</view>
				<view class="mine-more-body">
					<view v-if="$store.state.isShow" class="four-icon-box" @click="toPage('/pages/game/weChat')">
						<image style="width: 46rpx;height: 46rpx;border-radius: 8rpx;margin-bottom: 12rpx;"
							src="@/static/mine/5.png" mode="scaleToFill">
						</image>
						<view class="" style="font-weight: 500;font-size: 24rpx;color: #333333;">红包群</view>
					</view>
					<view v-if="$store.state.isShow" class="four-icon-box"
						@click="toPage('/pages/mine/helpAndFeedback')">
						<image style="width: 44rpx;height: 44rpx;border-radius: 8rpx;margin-bottom: 12rpx;"
							src="@/static/mine/11.png" mode="scaleToFill">
						</image>
						<view class="" style="font-weight: 500;font-size: 24rpx;color: #333333;">反馈建议</view>
					</view>
					<view class="four-icon-box" @click="toPage('/pages/mine/settings')">
						<image style="width: 46rpx;height: 46rpx;border-radius: 8rpx;margin-bottom: 12rpx;"
							src="@/static/setting.png" mode="scaleToFill">
						</image>
						<view class="" style="font-weight: 500;font-size: 24rpx;color: #333333;">设置</view>
					</view>
					<view class="four-icon-box" @click="toPage('/pages/mine/customer')">
						<image style="width: 46rpx;height: 46rpx;border-radius: 8rpx;margin-bottom: 12rpx;"
							src="@/static/mine/7.png" mode="scaleToFill">
						</image>
						<view class="" style="font-weight: 500;font-size: 24rpx;color: #333333;">我的客服</view>
					</view>
					<view class="four-icon-box" @click="toPage('/pages/mine/address')">
						<image style="width: 46rpx;height: 46rpx;border-radius: 8rpx;margin-bottom: 12rpx;"
							src="@/static/mine/3.png" mode="scaleToFill">
						</image>
						<view class="" style="font-weight: 500;font-size: 24rpx;color: #333333;">地址管理</view>
					</view>
				</view>
			</view>
				<!-- ===== Banner 广告 ===== -->
				<view class="banner-ad-container">
					<view style="width:100%;height:160rpx;border-radius:32rpx;background:#f0f0f0;display:flex;align-items:center;justify-content:center;">
						<text style="color:#999;font-size:24rpx;">Banner 广告位</text>
					</view>
				</view>
		</view>
		<!-- 金币兑换现金   -->
		<uv-popup ref="exchange" mode="center" round="16rpx">
			<view style="width: 580rpx;background: #ffffff;padding: 20rpx;">
				<view class="exchange-info-image" @tap="closeExchange()">
					<image style="width: 21rpx;" src="~@/static/images/poupClose.png" mode="widthFix" alt="" />
				</view>
				<view class="exchange-info-text">
					金币比例兑换
				</view>
				<view class="exchange-info-text2">
					金币兑换现金比例为{{version.convertRatio}} : 1
				</view>
				<view class="exchange-info-input">
					<input v-model="exchangeCoinNum" type="number" placeholder="请输入金币数量" />
				</view>
				<view class="exchange-info-button" @tap="exchangeCoinBtn()">
					确定
				</view>
			</view>
		</uv-popup>
	</view>
</template>

<script>
	import watermark from '@/components/jm-watermark/jm-watermark.vue';
	import BaseHeader from '@/components/BaseHeader.vue'
	import bgTop from '@/static/images/change/mineBg1.png'
	import mineBg from '@/static/images/change/mineBg.png'
	import vipBox from '@/static/images/change/vipBox.png'
	import {
		adInformation
	} from '@/common/api/config.js'
	export default {
		components: {
			BaseHeader,
			watermark
		},
		data() {
			return {
				bgTop: bgTop,
				mineBg: mineBg,
				vipBox: vipBox,
				recordList: [],
				userInfo: {},
				version: {},
				isLogin: false,
				exchangeCoinNum: '',
				isSelected: 0,
				isMoreRecords: false,
				queryParam: {
					pageNum: 1,
					pageSize: 10,
				},
				param: {
					pageNum: 1,
					pageSize: 10,
				},
				collectList: [],
				appreciateList: [],
				collectTabIndex: 0,
				appreciateTabIndex: 0,
				sytext: '版权所有2025©徐州胖老施科技',
				adInformation: adInformation,
			}
		},
		onLoad() {},
		onReachBottom() {
			if (!this.isMoreRecords) return
			if (this.isSelected == 0) {
				this.getRecord()
			}
			if (this.isSelected == 1 && this.collectTabIndex == 0) {
				this.getCollectList()
			}
			if (this.isSelected == 1 && this.collectTabIndex == 1) {
				this.getCollectList2()
			}
			if (this.isSelected == 2 && this.appreciateTabIndex == 0) {
				this.getLikeList()
			}
			if (this.isSelected == 2 && this.appreciateTabIndex == 1) {
				this.getLikeList2()
			}
		},
		onShow() {
			uni.setStorageSync('wxChatPage', false);
			this.version = uni.getStorageSync('Version')
			const token = uni.getStorageSync('Token')
			if (token) {
				this.isLogin = true
				this.$api.getInfo().then(res => {
					uni.setStorageSync('UserInfo', res.data)
					this.userInfo = res.data
				})
				this.getRecord()
			}
			if (typeof rh_android !== 'undefined' && rh_android.pauseMusic) {
				rh_android.pauseMusic();
			}
		},
		onHide() {
			this.isMoreRecords = false
			this.isSelected = 0
			this.queryParam.pageNum = 1
			this.param.pageNum = 1
			uni.setStorageSync('wxChatPage', false);
		},
		computed: {
			// isLogin: function() {
			// 	const token = uni.getStorageSync('Token');
			// 	if (token) return true
			// 	return false
			// }
		},
		methods: {
			// ===== 原有方法 =====

			toPromotion() {
				//4普通用户  1代理  2团长  3团员
				if (this.userInfo.roleId == 4) {
					uni.navigateTo({
						url: '/pages/promotion/index'
					})
				}
				if (this.userInfo.roleId == 1) {
					uni.navigateTo({
						url: '/pages/promotion/index2'
					})
				}
				if (this.userInfo.roleId == 2 || this.userInfo.roleId == 3) {
					uni.navigateTo({
						url: '/pages/promotion/index3'
					})
				}
			},
			// 短剧收藏列表
			getCollectList() {
				this.$api.getCollectDramaList(this.queryParam).then(res => {
					if (res.data.list.length >= 10) {
						this.queryParam.pageNum++
						this.isMoreRecords = true
						this.collectList = this.collectList.concat(res.data.list)
					} else {
						if (this.queryParam.pageNum == 1) {
							this.collectList = res.data.list
						} else {
							this.collectList = this.collectList.concat(res.data.list)
						}
						this.isMoreRecords = false
					}
				})
			},
			//短剧点赞列表
			getLikeList() {
				this.$api.getMyLikeDramaList(this.queryParam).then(res => {
					if (res.data.list.length >= 10) {
						this.queryParam.pageNum++
						this.isMoreRecords = true
						this.appreciateList = this.appreciateList.concat(res.data.list)
					} else {
						if (this.queryParam.pageNum == 1) {
							this.appreciateList = res.data.list
						} else {
							this.appreciateList = this.appreciateList.concat(res.data.list)
						}
						this.isMoreRecords = false
					}
				})
			},
			//看看收藏列表
			getCollectList2() {
				this.$api.getCollectVideoList(this.queryParam).then(res => {
					if (res.data.list.length >= 10) {
						this.queryParam.pageNum++
						this.isMoreRecords = true
						this.collectList = this.collectList.concat(res.data.list)
					} else {
						if (this.queryParam.pageNum == 1) {
							this.collectList = res.data.list
						} else {
							this.collectList = this.collectList.concat(res.data.list)
						}
						this.isMoreRecords = false
					}
				})
			},
			//看看点赞列表
			getLikeList2() {
				this.$api.getMyLikeVideoList(this.queryParam).then(res => {
					if (res.data.list.length >= 10) {
						this.queryParam.pageNum++
						this.isMoreRecords = true
						this.appreciateList = this.appreciateList.concat(res.data.list)
					} else {
						if (this.queryParam.pageNum == 1) {
							this.appreciateList = res.data.list
						} else {
							this.appreciateList = this.appreciateList.concat(res.data.list)
						}
					}
				})
			},
			changeTitle(index) {
				this.collectTabIndex = 0
				this.appreciateTabIndex = 0
				this.isSelected = index
				this.queryParam.pageNum = 1
				if (index == 0) {
					this.recordList = []
					this.getRecord()
				} else if (index == 1) {
					this.getCollectList()
				} else {
					this.getLikeList()
				}
			},
			changeTitle3(index) {
				this.collectTabIndex = index
				if (index == 0) {
					this.$api.getCollectDramaList(this.param).then(res => {
						if (res.data.list.length >= 10) {
							this.param.pageNum++
							this.isMoreRecords = true
							this.collectList = this.collectList.concat(res.data.list)
						} else {
							this.collectList = res.data.list
							this.isMoreRecords = false
						}
					})
				} else {
					this.$api.getCollectVideoList(this.param).then(res => {
						if (res.data.list.length >= 10) {
							this.param.pageNum++
							this.isMoreRecords = true
							this.collectList = this.collectList.concat(res.data.list)
						} else {
							this.collectList = res.data.list
							this.isMoreRecords = false
						}
					})
				}
			},
			changeTitle4(index) {
				this.appreciateTabIndex = index
				if (index == 0) {
					this.$api.getMyLikeDramaList(this.param).then(res => {
						if (res.data.list.length >= 10) {
							this.param.pageNum++
							this.isMoreRecords = true
							this.appreciateList = this.collectList.concat(res.data.list)
						} else {
							this.appreciateList = res.data.list
							this.isMoreRecords = false
						}
					})
				} else {
					this.$api.getMyLikeVideoList(this.param).then(res => {
						if (res.data.list.length >= 10) {
							this.param.pageNum++
							this.isMoreRecords = true
							this.appreciateList = this.appreciateList.concat(res.data.list)
						} else {
							this.appreciateList = res.data.list
							this.isMoreRecords = false
						}
					})
				}
			},
			toInviteFriend() {
				uni.navigateTo({
					url: '/pages/mine/inviteFriend'
				})
			},
			//金币兑换现金
			exchangeCoinBtn() {
				if (this.exchangeCoinNum >= this.version.convertRatio && this.exchangeCoinNum % 10 == 0) {
					this.$api.goldCoinToCash({
						goldCoin: this.exchangeCoinNum
					}).then(res => {
						if (res.code == 200) {
							uni.showToast({
								title: '兑换成功',
								icon: 'none',
								duration: 2000
							})
							this.exchangeCoinNum = ''
							this.getUserInfo()
							this.$refs.popup.close()
						}
					})
				} else {
					uni.showToast({
						title: '兑换金额不能小于比例值且是10的倍数',
						icon: 'none',
						duration: 2000
					})
				}
			},
			openExchange() {
				this.$refs.exchange.open()
			},
			closeExchange() {
				this.$refs.exchange.close()
				this.exchangeCoinNum = ''
			},
			toUrl() {
				uni.navigateTo({
					url: '/pages/benefit/withdrawal-detail'
				})
			},
			toWithin() {
				uni.navigateTo({
					url: '/pages/benefit/withdrawal'
				})
			},
			toPage(url, item) {
				if (url == '/pages/index/video-see?pageNum=') {
					//如果开关未开启
					if (!this.$store.state.isShow) {
						this.$utils.previewImage(0, [item.titleImg])
						return
					}

					//如果需要vip但用户不是vip
					if (item.isVip == 1 && this.userInfo.isVip == 0) {
						this.$utils.toPage('/pages/mine/createVip')
						return
					}

					//获取视频信息
					this.$api.getDetailsList({
						dramaId: item.dramaId
					}).then(res => {
						const dataList = res.data
						dataList.filter(item => {
							/** 参数数据自行拼接  */
							item.coverShow = true //是否显示视频封面，vue 小程序端不播放会显示视频，可以不用显示封面，App不播放不会显示视频，就需要封面了
							item.object_fit = 'cover' //视频的显示类型 contain cover
							item.sliderShow = true //是否显示进度条
							item.likeShow = item.isLike == 0 ? false : true //是否点赞
							item.collectShow = item.isCollect == 0 ? false : true //是否收藏
							item.selectWork = true //是否短剧
						})

						const index = dataList.map(x => x.gather).indexOf(item.watchGather)

						getApp().globalData.seeVodIndex = index;
						getApp().globalData.seeVodList = dataList;
						getApp().globalData.vodinfo = item;
						this.$utils.toPage(url + index)
					})
					return
				}

				this.$utils.toPage(url)
			},
			getRecord() {
				this.$api.getRecordList(this.queryParam).then(res => {
					if (res.data.list.length >= 10) {
						this.queryParam.pageNum++
						this.isMoreRecords = true
						this.recordList = this.recordList.concat(res.data.list)
					} else {
						this.recordList = res.data.list
						this.isMoreRecords = false
					}
				})
			}
		}
	}
</script>

<style>
	page {
		background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%) !important;
	}
</style>

<style lang="scss" scoped>
	.head-title {
		width: 100%;
		font-family: PingFang SC;
		font-weight: bold;
		font-size: 32rpx;
		color: #000;
		text-align: center;
		position: relative;

		.helder-back {
			position: absolute;
			top: 0;
			left: 20rpx;
		}
	}

	.mine {
		position: relative;
		padding: 20rpx;
		z-index: 7;

		&-bg {
			width: 100%;
			position: absolute;
			top: 0;
			left: 0;
			z-index: -1;
		}

		&-me {
			padding: 0 30rpx;
			box-sizing: border-box;
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-bottom: 40rpx;

			&-left {
				display: flex;
				align-items: center;
			}

			&-right {
				width: 40rpx;
				height: 44rpx;
			}
		}

		&-lookbox {
			width: 690rpx;
			// height: 408rpx;
			background: #FFFFFF;
			border-radius: 16rpx;
			margin: 0 auto;
			overflow: hidden;

			&-top {
				padding: 32rpx 36rpx;
				box-sizing: border-box;
				display: flex;
				align-items: center;
				justify-content: space-between;
			}
		}

		&-vip {
			width: 702rpx;
			height: 268rpx;
			display: flex;
			flex-wrap: wrap;
			background-size: 100% 100%;
			margin: 0 auto;
			display: flex;
			margin-bottom: 6rpx;
			background: #FFFFFF;
			border-radius: 18rpx;

			.mine-vip-top {
				width: 100%;
				height: 80rpx;
				display: flex;
				justify-content: space-between;
				align-items: center;
				border-radius: 18rpx 18rpx 0 0;
				background: #444444;

				.mine-vip-top-left {
					margin-left: 25rpx;
					display: flex;
					align-items: center;
					font-weight: bold;
					font-size: 30rpx;
					color: #FFDFBD;

					image {
						width: 39rpx;
						height: 29rpx;
					}
				}

				.mine-vip-top-right {
					margin-right: 25rpx;
					display: flex;
					align-items: center;

					image {
						width: 160rpx;
						height: 54rpx;
					}
				}
			}

			.mine-vip-bottom {
				width: 100%;
				display: flex;
				justify-content: space-around;
				font-weight: bold;
				font-size: 42rpx;
				color: #333333;

				.mine-vip-bottom-item {
					display: flex;
					align-items: center;
					flex-direction: column;
				}
			}
		}

		.mine-lookbox2 {
			width: 690rpx;
			height: 228rpx;
			position: relative;
			background: #fff;
			margin: 0 auto;
			overflow: hidden;
			border-radius: 32rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
			border: 2px solid #FFFFFF;
			display: flex;
			align-items: center;

			.mine-lookbox2-item {
				display: flex;
				flex-direction: column;

				.mine-lookbox2-item-top {
					width: 200rpx;
					display: flex;
					align-items: center;
					font-weight: 500;
					font-size: 24rpx;
					color: #666666;

					image {
						width: 14rpx;
						height: 22rpx;
					}
				}
			}

			.within {
				position: absolute;
				right: 0rpx;
				top: 0rpx;
				width: 121rpx;
				height: 39rpx;
			}
		}
	}

	/* ===== Banner 广告样式 ===== */
	.banner-ad-container {
		width: 690rpx;
		margin: 20rpx auto;
		border-radius: 32rpx;
		overflow: hidden;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
		
		.banner-ad-wrapper {
			position: relative;
			width: 100%;
			background: #FFFFFF;
			
			.banner-ad-image {
				width: 100%;
				height: 160rpx;
				display: block;
			}
			
			.banner-ad-tag {
				position: absolute;
				top: 12rpx;
				right: 12rpx;
				background: rgba(0,0,0,0.5);
				color: #fff;
				font-size: 20rpx;
				padding: 4rpx 16rpx;
				border-radius: 20rpx;
			}
		}
	}

	.scroll-view_H1 {
		width: 654rpx;
		margin-left: 36rpx;
		white-space: nowrap;
		// overflow: auto;
	}

	.scroll-view-item_H1 {
		display: inline-block;
		width: 160rpx;
		margin-right: 16rpx;

	}

	.scroll-view-item_H1-name {
		width: 100%;
		overflow-x: hidden;
		text-overflow: ellipsis;
	}

	.three-icon {
		width: 702rpx;
		height: 165rpx;
		background: url('/static/mine/6.png');
		background-size: cover;
		margin: 0 auto;
	}

	.four-icon {
		width: 690rpx;
		background: #FFFFFF;
		border-radius: 16rpx;
		margin: 0 auto;
		padding: 32rpx 0;
		display: flex;
		flex-wrap: wrap;
		flex-direction: column;

		.mine-more-top {
			padding: 20rpx;
		}

		.mine-more-body {
			display: flex;
			flex-wrap: wrap;
		}

	}

	.four-icon2 {
		width: 690rpx;
		min-height: 650rpx;
		background: #FFFFFF;
		border-radius: 16rpx;
		margin: 0 auto;
		padding: 32rpx 0;
		display: flex;
		flex-wrap: wrap;
		flex-direction: column;

		.four-icon2-title {
			font-size: 28rpx;
			color: #999999;
			margin: 20rpx 0 20rpx 0;

			span {
				margin-left: 70rpx;
			}
		}

		.four-icon2-title2 {
			font-size: 32rpx;
			color: #000;
		}

		.browseRecords {
			.browseRecords-title {
				margin: 0 0 0 20rpx;
				display: flex;
				align-items: center;

				.browseRecords-title-item {
					width: 124rpx;
					height: 56rpx;
					font-size: 28rpx;
					color: #FE8100;
					text-align: center;
					line-height: 56rpx;
					background: rgba(254, 129, 0, 0.2);
					border-radius: 200rpx 200rpx 200rpx 200rpx;
				}

				.browseRecords-title-item2 {
					width: 124rpx;
					height: 56rpx;
					font-size: 28rpx;
					color: #000;
					text-align: center;
					line-height: 56rpx;
					background: #F5F5F5;
					border-radius: 200rpx 200rpx 200rpx 200rpx;
				}
			}

			.browseRecords-body {
				display: flex;
				flex-wrap: wrap;
				margin: 20rpx auto 0 auto;

				.browseRecords-body-item {
					display: flex;
					margin: 40rpx 10rpx 0 10rpx;
					flex-direction: column;

					.browseRecords-body-item-span1 {
						display: inline-block;
						width: 200rpx;
						overflow: hidden;
						text-overflow: ellipsis;
						white-space: nowrap;
						font-size: 28rpx;
						color: #000000;
					}

					.browseRecords-body-item-span2 {
						display: inline-block;
						width: 200rpx;
						overflow: hidden;
						text-overflow: ellipsis;
						white-space: nowrap;
					}

					span {
						font-size: 24rpx;
						color: #949494;
					}

					image {
						width: 210rpx;
						height: 298rpx;
						border-radius: 24rpx 24rpx 24rpx 24rpx;
					}
				}
			}
		}

		.collect {
			.collect-title {
				margin: 0 0 0 20rpx;
				display: flex;
				align-items: center;

				.collect-title-item {
					width: 124rpx;
					height: 56rpx;
					font-size: 28rpx;
					color: #FE8100;
					text-align: center;
					line-height: 56rpx;
					background: rgba(254, 129, 0, 0.2);
					border-radius: 200rpx 200rpx 200rpx 200rpx;
				}

				.collect-title-item2 {
					width: 124rpx;
					height: 56rpx;
					font-size: 28rpx;
					color: #000;
					text-align: center;
					line-height: 56rpx;
					background: #F5F5F5;
					border-radius: 200rpx 200rpx 200rpx 200rpx;
				}
			}

			.collect-body {
				display: flex;
				flex-wrap: wrap;
				margin: 20rpx auto 0 auto;

				.collect-body-item {
					display: flex;
					margin: 40rpx 10rpx 0 10rpx;
					flex-direction: column;

					.collect-body-item-span1 {
						display: inline-block;
						width: 200rpx;
						overflow: hidden;
						text-overflow: ellipsis;
						white-space: nowrap;
						font-size: 28rpx;
						color: #000000;
					}

					span {
						font-size: 24rpx;
						color: #949494;
					}

					image {
						width: 210rpx;
						height: 298rpx;
						border-radius: 24rpx 24rpx 24rpx 24rpx;
					}
				}
			}
		}
	}

	.three-icon-box {
		width: calc(100% /3);
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}

	.four-icon-box {
		width: 25%;
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		margin: 20rpx 0;
	}

	.number-box {
		display: flex;
		align-items: center;
		justify-content: center;

		&-line {
			width: 2rpx;
			height: 78rpx;
			background: #EDEDED;
			margin: 0 10 0 0rpx;
		}

		&-item {
			display: flex;
			align-items: center;
			justify-content: center;
			flex-direction: column;
			font-weight: bold;
			font-size: 42rpx;
			color: #333333;
		}
	}

	.exchange-info-image {
		width: 45rpx;
		height: 45rpx;
		margin-left: 92%;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.exchange-info-text {
		font-weight: bold;
		font-size: 34rpx;
		color: #333333;
		text-align: center;
	}

	.exchange-info-text2 {
		font-weight: 500;
		font-size: 26rpx;
		color: #666666;
		text-align: center;
		padding-top: 30rpx;
	}

	.exchange-info-input {
		display: flex;
		justify-content: center;
		padding-top: 20rpx;

		input {
			width: 528rpx;
			height: 64rpx;
			text-align: center;
			background: #ECECEC;
			border-radius: 16rpx;
		}
	}

	.exchange-info-button {
		margin: 35rpx auto;
		border-radius: 40rpx;
		width: 250rpx;
		height: 88rpx;
		background: $uni-theme;
		color: #FFFFFF;
		text-align: center;
		line-height: 88rpx;
	}
</style>
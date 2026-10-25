<template>
	<view class="content">
		<view class="top">
			<image class="avatar" :src="userInfo.avatar" mode=""></image>
			<view class="top-info">
				<view class="top-info-item1">
					<span>{{userInfo.nickName}}</span>
					<image class="identity" src="/static/promotion/11.png" mode=""></image>
				</view>
				<!-- 	<view class="top-info-item2">
					<span>所属代理：吃嘛嘛香</span>
				</view> -->
			</view>
		</view>
		<view class="middle">
			<view class="middle-bg">
				<view class="middle-item1">
					<span>推广收益</span>
					<span style="font-size: 22rpx;color: #999999;margin-left: 10rpx;">团队总人数:
						{{inComeData.teamNums}}</span>
				</view>
				<view class="middle-item2">
					<view class="middle-item2-left">
						<span style="font-size: 26rpx;color: #999999;">今日收益(币)</span>
						<span style="font-size: 56rpx;color: #000000;">{{inComeData.dayGains}}</span>
					</view>
					<view class="middle-item2-right">
						<span style="font-size: 26rpx;color: #999999;">本月收益(币)</span>
						<span style="font-size: 56rpx;color: #000000;">{{inComeData.monthEarnings}}</span>
					</view>
				</view>
				<view class="middle-item3">
					<view class="middle-item3-left">
						<span style="font-size: 26rpx;color: #999999;">上月收益(币)</span>
						<span style="font-size: 56rpx;color: #000000;">{{inComeData.lastMonthEarnings}}</span>
					</view>
					<view class="middle-item3-right">
						<span style="font-size: 26rpx;color: #999999;">累计收益(币)</span>
						<span style="font-size: 56rpx;color: #000000;">{{inComeData.totalGains}}</span>
					</view>
				</view>
			</view>
			<view class="middle-bg2"></view>
		</view>
		<view class="middle2">
			<view class="middle2-item">
				<span style="font-size: 26rpx;color: #999999;">金币余额(币)</span>
				<span style="font-size: 56rpx;color: #000000;">{{userInfo.goldCoin}}</span>
				<view @tap="goPage(0)" class="middle2-item-btn">
				</view>
			</view>
			<view class="middle2-item">
				<span style="font-size: 26rpx;color: #999999;">现金余额(元)</span>
				<span style="font-size: 56rpx;color: #000000;">{{userInfo.cash}}</span>
				<view @tap="goPage(1)" class="middle2-item-btn2">
				</view>
			</view>
		</view>
		<view class="bottom">
			<view class="bottom-body">
				<span class="bottom-body-title">常用工具</span>
				<view class="bottom-items">
					<!-- 			<view class="bottom-item" @tap="toPage('/pages/promotion/inviteFriends')">
						<image src="/static/promotion/1.png" mode=""></image>
						<span>邀请团长</span>
					</view> -->
					<!-- 	<view class="bottom-item" @tap="toPage('/pages/promotion/transactionRecord')">
						<image src="/static/promotion/3.png" mode=""></image>
						<span>数据统计</span>
					</view> -->
					<view class="bottom-item" @tap="toPage('/pages/promotion/inviteFriends')">
						<image src="/static/promotion/15.png" mode=""></image>
						<span>邀请好友</span>
					</view>
					<view class="bottom-item" @tap="toPage('/pages/promotion/transactionRecord')">
						<image src="/static/promotion/4.png" mode=""></image>
						<span>交易记录</span>
					</view>
					<view class="bottom-item" @tap="toPage('/pages/promotion/teamMembers')">
						<image src="/static/promotion/2.png" mode=""></image>
						<span>团队成员</span>
					</view>
					<!-- 			<view class="bottom-item" @tap="toPage('/pages/promotion/transactionRecord')">
						<image src="/static/promotion/5.png" mode=""></image>
						<span>团队管理</span>
					</view> -->
					<!-- 	<view class="bottom-item" @tap="toPage('/pages/promotion/transactionRecord')">
						<image src="/static/promotion/13.png" mode=""></image>
						<span>团长审核</span>
					</view> -->
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userInfo: {},
				inComeData: {}
			};
		},
		onShow() {
			this.getUserInfo()
			this.getIncomeInfo()
		},
		methods: {
			//获取用户信息
			getUserInfo() {
				this.$api.getInfo().then(res => {
					this.userInfo = res.data
				})
			},
			//获取收益信息
			getIncomeInfo() {
				this.$api.incomeInfo().then(res => {
					this.inComeData = res.data
				})
			},
			toPage(url) {
				uni.navigateTo({
					url: url
				})
			},
			goPage(index) {
				if (index == 0) {
					uni.switchTab({
						url: '/pages/benefit/index'
					})
				} else {
					uni.navigateTo({
						url: '/pages/benefit/withdrawal'
					})
				}

			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
	}

	.content {
		width: 100vw;
		height: 100vh;
		overflow: hidden;

		.top {
			display: flex;
			align-items: center;
			font-size: 34rpx;
			color: #5D3A1A;
			font-weight: 700;
			margin: 30rpx;

			.top-info {
				display: flex;
				flex-direction: column;

				.top-info-item1 {
					display: flex;
					align-items: center;
				}

				.top-info-item2 {
					font-size: 26rpx;
					color: #8B6B4A;
				}
			}

			span {
				margin-left: 18rpx;
			}

			.avatar {
				width: 100rpx;
				height: 100rpx;
				border-radius: 50%;
				border: 2rpx solid #FFFFFF;
			}

			.identity {
				width: 108rpx;
				height: 40rpx;
				margin-left: 18rpx;
			}
		}

		.middle {
			width: 690rpx;
			height: 398rpx;
			position: relative;
			margin: auto;

			.middle-bg {
				width: 100%;
				height: 100%;
				position: absolute;
				background: url('/static/promotion/6.png');
				background-size: cover;
				z-index: 999;
				display: flex;
				flex-direction: column;
				justify-content: space-around;

				.middle-item1 {
					font-size: 32rpx;
					color: #000000;
					margin-left: 20rpx;
				}

				.middle-item2 {
					display: flex;
					align-items: center;
					margin-left: 20rpx;

					.middle-item2-left {
						display: flex;
						align-items: center;
						flex-direction: column;
					}

					.middle-item2-right {
						display: flex;
						margin-left: 160rpx;
						align-items: center;
						flex-direction: column;
					}

					.middle-item2-btn {
						width: 140rpx;
						height: 60rpx;
						margin-top: 35rpx;
						background: url('/static/promotion/7.png');
						background-size: cover;
					}
				}

				.middle-item3 {
					display: flex;
					align-items: center;
					margin-left: 20rpx;

					.middle-item3-left {
						display: flex;
						align-items: center;
						flex-direction: column;
					}

					.middle-item3-right {
						display: flex;
						margin-left: 160rpx;
						align-items: center;
						flex-direction: column;
					}

					.middle-item3-btn {
						width: 140rpx;
						height: 60rpx;
						margin-top: 35rpx;
						background: url('/static/promotion/8.png');
						background-size: cover;
					}
				}
			}

			.middle-bg2 {
				position: absolute;
				width: 294rpx;
				height: 200rpx;
				top: -65rpx;
				right: 20rpx;
				background: url('/static/promotion/9.png');
				background-size: cover;
				z-index: 9;
			}
		}

		.middle2 {
			width: 730rpx;
			height: 288rpx;
			margin: 20rpx auto;
			background: url('/static/15.png');
			background-size: cover;
			display: flex;
			align-items: center;
			justify-content: space-around;

			.middle2-item {
				display: flex;
				flex-direction: column;
				align-items: center;

				.middle2-item-btn {
					width: 140rpx;
					height: 60rpx;
					background: url('/static/promotion/7.png');
					background-size: cover;
				}

				.middle2-item-btn2 {
					width: 140rpx;
					height: 60rpx;
					background: url('/static/promotion/8.png');
					background-size: cover;
				}
			}
		}

		.bottom {
			width: 750rpx;
			height: 658rpx;
			position: absolute;
			bottom: 0;
			background: #FFF8F0;
			border-radius: 40rpx 40rpx 0rpx 0rpx;

			.bottom-body {
				width: 690rpx;
				height: 424rpx;
				margin: 32rpx auto;
				background: #FFFFFF;
				border-radius: 32rpx;
				box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
				font-size: 32rpx;
				color: #5D3A1A;
				font-weight: 700;
				overflow: hidden;

				.bottom-body-title {
					display: block;
					margin: 40rpx 0 0 50rpx;
				}

				.bottom-items {
					display: flex;
					align-items: center;
					flex-wrap: wrap;

					.bottom-item {
						display: flex;
						margin-left: 60rpx;
						margin-top: 40rpx;
						flex-direction: column;
						align-items: center;
						font-size: 26rpx;
						color: #666666;

						image {
							width: 64rpx;
							height: 64rpx;
						}
					}
				}
			}
		}
	}
</style>
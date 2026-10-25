<template>
	<view class="content">
		<view class="top">
			<uni-icons type="left" size="22"></uni-icons>
			<span style="font-size: 34rpx;">数据统计</span>
			<span style="font-size: 28rpx;">交易记录</span>
		</view>
		<view class="middle">
			<view class="middle-left">
				<view class="middle-left-time">
					<image src="/static/promotion/27.png" mode=""></image>
					<span>2025/6/24 14:25:20 星期三</span>
				</view>
				<span style="margin-top: 46rpx;">今日收益</span>
				<span style="font-size: 64rpx;margin-top: 14rpx;">7,108</span>
				<view class="middle-left-bottom">
					<span>个人收益 2,589</span>
					<span style="margin-left: 38rpx;">团队分佣 2,259</span>
				</view>
			</view>
			<image class="middle-gold" src="/static/promotion/28.png" mode=""></image>
		</view>
		<view class="bottom">
			<view class="bottom-left">
				<view class="bottom-left-item">
					<span style="font-size: 26rpx;">今日新增人数</span>
					<span style="font-size: 40rpx;">20</span>
				</view>
				<view class="bottom-left-item">
					<span style="font-size: 26rpx;">昨日收益(币)</span>
					<span style="font-size: 40rpx;">20</span>
				</view>
				<view class="bottom-left-item">
					<span style="font-size: 26rpx;">已兑换金币(币)</span>
					<span style="font-size: 40rpx;">20</span>
				</view>
				<view class="bottom-left-item">
					<span style="font-size: 26rpx;">已提现现金(元)</span>
					<span style="font-size: 40rpx;">20</span>
				</view>
			</view>
			<view class="bottom-right">
				<view class="bottom-right-item">
					<span style="font-size: 26rpx;">昨日新增人数</span>
					<span style="font-size: 40rpx;">20</span>
				</view>
				<view class="bottom-right-item">
					<span style="font-size: 26rpx;">累计收益(币)</span>
					<span style="font-size: 40rpx;">20</span>
				</view>
				<view class="bottom-right-item">
					<span style="font-size: 26rpx;">金币余额(币)</span>
					<span style="font-size: 40rpx;">20</span>
				</view>
				<view class="bottom-right-item">
					<span style="font-size: 26rpx;">现金余额(元)</span>
					<span style="font-size: 40rpx;">20</span>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			};
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		width: 100vw;
		height: 100vh;
		overflow: hidden;
		width: 750rpx;
		height: 100vh;
		background: linear-gradient(180deg, #FDF6EE 0%, #FDE7C1 100%);

		.top {
			width: 95%;
			margin: 40rpx auto;
			display: flex;
			justify-content: space-between;
			align-items: center;
		}

		.middle {
			position: relative;

			.middle-left {
				display: flex;
				margin-left: 34rpx;
				margin-top: 150rpx;
				flex-direction: column;

				.middle-left-time {
					display: flex;
					align-items: center;
					font-size: 28rpx;

					image {
						width: 28rpx;
						height: 28rpx;
					}
				}

				.middle-left-bottom {
					display: flex;
					align-items: center;
					font-size: 24rpx;
					margin-top: 14rpx;
				}
			}

			.middle-gold {
				position: absolute;
				width: 268rpx;
				height: 238rpx;
				right:0;
				top:0;
			}
		}

		.bottom {
			width: 750rpx;
			height: 1098rpx;
			position: absolute;
			bottom: 0;
			display: flex;
			background: #FFFFFF;
			border-radius: 40rpx 40rpx 0rpx 0rpx;

			.bottom-left {
				display: flex;
				margin-left: 52rpx;
				margin-top: 48rpx;
				flex-direction: column;

				.bottom-left-item {
					display: flex;
					margin-top: 48rpx;
					flex-direction: column;
				}
			}

			.bottom-right {
				display: flex;
				margin-left: 174rpx;
				margin-top: 48rpx;
				flex-direction: column;

				.bottom-right-item {
					display: flex;
					margin-top: 48rpx;
					flex-direction: column;
				}
			}
		}
	}
</style>
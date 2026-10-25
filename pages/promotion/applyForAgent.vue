<template>
	<view class="content">
		<view class="top"></view>
		<view class="middle">
			<view class="middle-item">
				<image style="width: 74rpx;height: 128rpx;" src="/static/promotion/17.png" mode=""></image>
				<view class="middle-item-right">
					<span style="font-size: 30rpx;">普通用户</span>
					<view style="display: flex;align-items: center;">
						<span style="font-size: 24rpx;color: #999999;">无推广资格，好友观看广告时，不产生广告分成</span>
						<image style="width: 28rpx;height: 28rpx;" src="/static/20.png" mode=""></image>
					</view>
				</view>
			</view>
			<view class="middle-item">
				<image style="width: 74rpx;height: 128rpx;" src="/static/promotion/18.png" mode=""></image>
				<view class="middle-item-right">
					<span style="font-size: 30rpx;">代理商</span>
					<view style="display: flex;align-items: center;">
						<span style="font-size: 24rpx;color: #999999;">推广权限：可分享专属邀请码，发展多个团队</span>
						<image style="width: 28rpx;height: 28rpx;" src="/static/20.png" mode=""></image>
					</view>
					<view style="display: flex;align-items: center;">
						<span style="font-size: 24rpx;color: #999999;">分佣收益：获得所有团队长及成员广告观看分成</span>
						<image style="width: 28rpx;height: 28rpx;" src="/static/20.png" mode=""></image>
					</view>
					<view style="display: flex;align-items: center;">
						<span style="font-size: 24rpx;color: #999999;">团队管理：查看团队及成员数据和收益明细</span>
						<image style="width: 28rpx;height: 28rpx;" src="/static/20.png" mode=""></image>
					</view>
				</view>
			</view>
		</view>
		<view @tap="toPage()" class="btn">
			<span>申请代理商</span>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			};
		},
		onShow() {
			console.log(4515);
			this.dsadas()
		},
		methods: {
			dsadas() {
				console.log(1121);
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		overflow: hidden;
	}

	.content {
		.top {
			width: 476rpx;
			height: 280rpx;
			margin: 68rpx auto;
			background: url('/static/promotion/19.png');
			background-size: cover;
		}

		.middle {
			width: 708rpx;
			height: 398rpx;
			margin: auto;
			display: flex;
			align-items: center;
			justify-content: space-evenly;
			flex-direction: column;
			background: #FFF8F0;
			border-radius: 32rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.middle-item {
				display: flex;
				align-items: center;

				.middle-item-right {
					display: flex;
					margin-left: 24rpx;
					flex-direction: column;
				}
			}
		}

		.btn {
			position: absolute;
			bottom: 68rpx;
			width: 494rpx;
			height: 100rpx;
			left: 128rpx;
			font-size: 30rpx;
			color: #FFFFFF;
			text-align: center;
			line-height: 100rpx;
			background: linear-gradient(135deg, #E8783B, #F4A460);
			border-radius: 200rpx;
		}
	}
</style>
<template>
	<view class="content">
		<view class="top"></view>
		<view class="body">
			<span style="font-size: 32rpx;margin-top: 40rpx;">团长邀请码:151521</span>
			<image src="/static/promotion/23.png" mode=""></image>
			<view class="body-btn">
				<span>复制邀请码</span>
			</view>
		</view>
		<view class="bottom">
			<span style="font-size: 32rpx;margin-left: 260rpx;">如何邀请？</span>
			<view class="bottom-item">
				<image src="/static/promotion/24.png" mode=""></image>
				<span style="margin-left: 10rpx;">复制团长邀请码</span>
			</view>
			<span style="color:#666666">复制团长邀请码 点击「分享邀请码」→「保存图片」</span>
			<view class="bottom-item">
				<image src="/static/promotion/24.png" mode=""></image>
				<span style="margin-left: 10rpx;">分享给团长</span>
			</view>
			<span style="color:#666666">好友通过你的二维码下载进入APP，点击「我的」→ 「我的推广」→「绑定邀请码」填写邀请码</span>
			<view class="bottom-item">
				<image src="/static/promotion/24.png" mode=""></image>
				<span style="margin-left: 10rpx;">审核团队</span>
			</view>
			<span style="color:#666666">好友填写邀请码后，您通过「我的推广」→「团长 审核」页面审核团长，通过后好友成功晋级团长。</span>
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
	page {
		overflow: hidden;
	}

	.content {
		.top {
			width: 523rpx;
			height: 318rpx;
			margin: 68rpx auto;
			background: url('/static/promotion/22.png');
			background-size: cover;
		}

		.body {
			display: flex;
			align-items: center;
			flex-direction: column;

			image {
				width: 347rpx;
				height: 20rpx;
				margin: 6rpx 0 6rpx 0;
			}

			.body-btn {
				width: 474rpx;
				height: 100rpx;
				font-size: 32rpx;
				margin-top: 50rpx;
				color: #FFFFFF;
				text-align: center;
				line-height: 100rpx;
				background: linear-gradient(135deg, #E8783B, #F4A460);
				border-radius: 32rpx;
			}
		}

		.bottom {
			width: 708rpx;
			height: 486rpx;
			margin: 100rpx auto;
			display: flex;
			padding: 30rpx;
			justify-content: space-evenly;
			flex-direction: column;
			background: #FFF8F0;
			border-radius: 32rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.bottom-item {
				display: flex;
				align-items: center;

				image {
					width: 32rpx;
					height: 32rpx;
				}
			}
		}
	}
</style>
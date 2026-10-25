<template>
		<view>
			<view class="logo">
				<image class="logo-img" src="@/static/images/change/Logo.png" mode="widthFix"></image>
				<view class="logo-version">{{version.versionNo}}</view>
			</view>
			<view class="line" @click="$utils.toPage('/pages/mine/policy?type=' + 1)">
				<view class="left">用户手册</view>
				<view class="right">
					<image class="btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view>
			<view  class="line" @click="$utils.toPage('/pages/mine/policy?type=' + 2)">
				<view class="left">隐私政策</view>
				<view class="right">
					<image class="btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view>
			<view  v-if="$store.state.isShow" class="line" @click="$utils.toPage('/pages/mine/policy?type=' + 3)">
				<view class="left">收集个人信息明示清单</view>
				<view class="right">
					<image class="btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view>
			<view  v-if="$store.state.isShow" class="line" @click="$utils.toPage('/pages/mine/policy?type=' + 4)">
				<view class="left">个人信息第三方共享清单</view>
				<view class="right">
					<image class="btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view>
			<view  v-if="$store.state.isShow" class="line" @click="$utils.toPage('/pages/mine/policy?type=' + 5)">
				<view class="left">儿童个人信息保护规则</view>
				<view class="right">
					<image class="btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view>
			<view  v-if="$store.state.isShow" class="line" @click="$utils.toPage('/pages/mine/policy?type=' + 6)">
				<view class="left">青少年文明公约</view>
				<view class="right">
					<image class="btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view>
		</view>
	</template>

	<script>
		export default {
			data() {
				return {
					userInfo: {},
					version: '',
				}
			},
			onLoad() {
				this.userInfo = uni.getStorageSync('UserInfo')
				this.getVersion()
			},
			methods: {
			}
		}
	</script>


	<style lang="scss" scoped>
		.logo {
			padding: 40rpx;
			text-align: center;
			background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);

			&-img {
				width: 200rpx;
				margin: 10rpx;
			}

			&-version {
				font-size: 30rpx;
				color: #8B6B4A;
			}
		}

		.line {
			display: flex;
			align-items: center;
			justify-content: space-between;
			padding: 30rpx;
			background-color: #FFFDF9;
			border-bottom: 1px rgba(200, 130, 60, 0.1) solid;
			border-radius: 30rpx;
			margin: 16rpx 24rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.left {
				font-size: 34rpx;
				color: #5D3A1A;
				font-weight: 700;
			}

			.right {
				display: flex;
				align-items: center;

				.img {
					width: 100rpx;
					height: 100rpx;
					border-radius: 30rpx;
				}

				.text {
					font-size: 32rpx;
					color: #8B6B4A;
					text-align: right;
				}

				.btn {
					width: 15rpx;
					margin-left: 10rpx;
				}
			}
		}

		.btn {
			margin-top: 20rpx;
			position: relative;
			z-index: 1;
			width: 100%;
			height: 110rpx;
			line-height: 110rpx;
			text-align: center;

			&-bg {
				position: absolute;
				left: 0;
				top: 0;
				width: inherit;
				height: inherit;
				z-index: -1
			}

			&-text {
				font-size: 35rpx;
				color: #fff;
				font-weight: bold;
			}
		}
	</style>
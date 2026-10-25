<template>
	<view class="content">
		<view class="top">
			<view class="top-item">
				<span>姓名*</span>
				<input type="text" maxlength="10" placeholder="请填写真实姓名" />
			</view>
			<view class="top-line"></view>
			<view class="top-item">
				<span>手机号*</span>
				<input type="number" maxlength="11" placeholder="请填写联系手机" />
			</view>
		</view>
		<view class="middle">
			<view class="middle-title">
				<span>身份证件信息</span>
			</view>
			<view class="middle-body">
				<image style="margin-left: 36rpx;" src="/static/promotion/35.png" mode=""></image>
				<image style="margin-left: 20rpx;" src="/static/promotion/36.png" mode=""></image>
			</view>
		</view>
		<view class="bottom">
			<view class="bottom-item">
				<span>公司名称</span>
				<input type="text" placeholder="请填写营业执照上的公司名称" />
			</view>
			<view class="bottom-line"></view>
			<view class="bottom-title">
				<span>营业执照</span>
			</view>
			<view class="bottom-picture">
				<image src="/static/promotion/37.png" mode=""></image>
			</view>
		</view>
		<view class="tips">
			<span style="margin-left: -320rpx;">代理费用：
				<span style="color: #FD4234;">￥0</span> 元  （限时免费）</span>
		</view>
		<view class="transferRecords">
			<span>上传转账截图</span>
			<image src="/static/promotion/38.png" mode=""></image>
		</view>
		<view class="bottom-btn">
			<span>提交申请</span>
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
		background: #FFF8F0;
	}

	.content {
		width: 100vw;
		display: flex;
		flex-direction: column;
		align-items: center;

		.top {
			width: 708rpx;
			height: 240rpx;
			display: flex;
			flex-direction: column;
			justify-content: space-evenly;
			margin-top: 40rpx;
			background: #FFFFFF;
			border-radius: 32rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.top-item {
				width: 90%;
				margin: auto;
				display: flex;
				align-items: center;
				justify-content: space-between;

				span {
					font-size: 28rpx;
				}

				input {
					width: 200rpx;
					font-weight: 400;
				}
			}

			.top-line {
				width: 637rpx;
				height: 2rpx;
				background: #EFEFEF;
			}
		}

		.middle {
			width: 708rpx;
			height: 312rpx;
			display: flex;
			justify-content: space-evenly;
			flex-direction: column;
			margin-top: 20rpx;
			background: #FFFFFF;
			border-radius: 32rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.middle-title {
				margin-left: 36rpx;

				span {
					font-size: 28rpx;
				}
			}

			.middle-body {
				display: flex;
				align-items: center;

				image {
					width: 308rpx;
					height: 186rpx;
				}
			}
		}

		.bottom {
			width: 708rpx;
			height: 456rpx;
			margin-top: 20rpx;
			background: #FFFFFF;
			display: flex;
			justify-content: space-evenly;
			flex-direction: column;
			border-radius: 32rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			view {
				width: 90%;
				margin: auto;
			}

			.bottom-item {
				margin-top: 40rpx;
				display: flex;
				justify-content: space-between;
				align-items: center;

				input {
					width: 330rpx;
				}
			}

			.bottom-line {
				width: 637rpx;
				height: 2rpx;
				background: #EFEFEF;
			}

			.bottom-title {
				font-size: 28rpx;
			}

			.bottom-picture {
				image {
					width: 308rpx;
					height: 186rpx;
				}
			}
		}

		.tips {
			margin-top: 32rpx;
			font-size: 28rpx;
			color: #5D3A1A;
		}

		.transferRecords {
			width: 708rpx;
			height: 120rpx;
			display: flex;
			padding: 0 30rpx 0 30rpx;
			justify-content: space-between;
			align-items: center;
			margin-top: 22rpx;
			background: #FFFFFF;
			border-radius: 32rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			image {
				width: 52rpx;
				height: 52rpx;
			}
		}

		.bottom-btn {
			width: 660rpx;
			height: 100rpx;
			margin-top: 80rpx;
			margin-bottom: 60rpx;
			font-size: 30rpx;
			color: #FFFFFF;
			text-align: center;
			line-height: 100rpx;
			background: linear-gradient(135deg, #E8783B, #F4A460);
			border-radius: 200rpx;
		}
	}
</style>
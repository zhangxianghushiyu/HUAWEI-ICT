<template>
	<view class="content">
		<view class="line"></view>
		<view class="body">
			<scroll-view scroll-y="true" style="height: 100%;">
                <view class="body-item" v-for="(item, index) in 10" :key="index">
					<view class="body-item-body">
						<view class="body-item-left">
							<span style="font-size: 30rpx;">团队A</span>
							<span style="font-size: 24rpx;color: #999999;">团队人数：196</span>
							<span style="font-size: 24rpx;color: #999999;">创建时间：2025-05-09 14:23:01</span>
						</view>
						<view class="body-item-right">
							<span>编辑</span>
						</view>
					</view>
					<view class="body-item-line">

					</view>
				</view>
			</scroll-view>
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
		width: 100vw;

		.line {
			width: 100%;
			height: 2rpx;
			margin-top: 20rpx;
			background: #EFEFEF;
		}

		.body {
			position: absolute;
			left: 0;
			right: 0;
			bottom: 0;
			top: 30rpx;

			.body-item {
				width: 95%;
				margin: 28rpx auto;
				display: flex;
				flex-direction: column;
				justify-content: space-between;

				.body-item-body {
					display: flex;
					justify-content: space-between;

					.body-item-left {
						display: flex;
						flex-direction: column;
					}

					.body-item-right {
						font-size: 24rpx;
						color: #E8783B;
					}
				}

				.body-item-line {
					width: 100%;
					height: 2rpx;
					margin-top: 20rpx;
					background: #EFEFEF;
				}
			}
		}
	}
</style>
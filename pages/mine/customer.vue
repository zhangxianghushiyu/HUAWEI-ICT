<template>
		<view>
			<view>
				<BaseHeader bgImageUrl="/static/images/change/contact-ervice.png">
					<view class="head-title">联系客服
						<view class="helder-back">
							<uni-icons color="#fff" type="left" size="25" @click="leftClick" />
						</view>
					</view>
				</BaseHeader>
			</view>
			<text class="text1">客服微信</text>
			<view class="index">
				<image src="@/static/images/change/contact-ervice1.png" mode="scaleToFill"
					:style="{height:`calc( 100vh - ${headerInfo.topNum + headerInfo.contentNum}px )`}"></image>
				<view class="qr-code">
					<image :src="code" mode="scaleToFill" style="width: 200px;height: 200px;"></image>
					<!-- <uv-qrcode ref="qrcode" size="250px" :value="code" /> -->
					<text class="text">咨询请添加官方客服微信</text>
				</view>
			</view>
		</view>
	</template>

	<script>
		import BaseHeader from '@/components/BaseHeader.vue'
		export default {
			components: {
				BaseHeader
			},
			data() {
				return {
					headerInfo: {},
					code: ''
				}
			},
			onLoad() {
				this.headerInfo = uni.getStorageSync('HeaderInfo');
				this.getCode()
			},
			methods: {
				leftClick() {
					uni.navigateBack()
				},
				getCode() {
					this.$api.getOfficialCode().then(res => {
						this.code = res.data
					})
				}
			}
		}
	</script>

	<style lang="scss" scoped>
		.head-title {
			width: 100%;
			font-family: PingFang SC;
			font-weight: bold;
			font-size: 32rpx;
			color: #fff;
			text-align: center;
			position: relative;

			.helder-back {
				position: absolute;
				top: 0;
				left: 20rpx;
			}
		}

		// 背景
		.index {
			position: relative;

			image {
				width: 100%;
			}

			.qr-code {
				position: absolute;
				top: 650rpx;
				left: 0;
				width: 100%;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;


				.text {
					margin-top: 100rpx;
					font-weight: 500;
					font-size: 28rpx;
					color: #5D3A1A;
				}
			}
		}

		.text1 {
			position: absolute;
			top: 630rpx;
			left: 50%;
			transform: translateX(-50%);
			font-weight: bold;
			font-size: 28rpx;
			color: #E8783B;
			z-index: 99999;
		}
	</style>
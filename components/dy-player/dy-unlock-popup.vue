<template>
	<view v-if="visible" class="dy-unlock-mask">
		<view class="dy-unlock">
			<image v-if="rewardNum" class="dy-unlock-bg" src="/static/images/jiesuo2.png" mode="aspectFit" />
			<image v-else class="dy-unlock-bg" src="/static/images/jiesuo1.png" mode="aspectFit" />

			<text v-if="rewardNum" class="dy-unlock-num">{{ rewardNum }} 金币</text>

			<view v-if="!rewardNum" class="dy-unlock-btns">
				<view class="dy-unlock-btn dy-unlock-btn-ad" @click="onAdUnlock">
					<image src="/static/images/1.png" mode="aspectFit" />
					<view class="dy-unlock-btn-text">
						<text class="dy-unlock-btn-title">看广告解锁</text>
						<text class="dy-unlock-btn-desc">观看小视频解锁 {{ unlockCount }} 集</text>
					</view>
				</view>

				<view v-if="showGold" class="dy-unlock-btn dy-unlock-btn-gold" @click="onGoldUnlock">
					<image src="/static/images/2.png" mode="aspectFit" />
					<view class="dy-unlock-btn-text">
						<text class="dy-unlock-btn-title">金币解锁</text>
						<text class="dy-unlock-btn-desc">使用 {{ goldCost }} 金币解锁本集</text>
					</view>
				</view>
			</view>

			<image class="dy-unlock-close" src="/static/images/jiesuoClose.png" mode="aspectFit" @click="onClose" />
			<image v-if="rewardNum" class="dy-unlock-confirm" src="/static/images/jiesuoBtn.png" mode="aspectFit" @click="onClose" />
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			visible: {
				type: Boolean,
				default: false
			},
			rewardNum: {
				type: [Number, String],
				default: ''
			},
			unlockCount: {
				type: [Number, String],
				default: 1
			},
			goldCost: {
				type: [Number, String],
				default: 0
			},
			showGold: {
				type: Boolean,
				default: false
			}
		},
		methods: {
			onAdUnlock() {
				this.$emit('unlock', { type: 'ad' })
			},
			onGoldUnlock() {
				this.$emit('unlock', { type: 'gold' })
			},
			onClose() {
				this.$emit('close')
			}
		}
	}
</script>

<style lang="scss" scoped>
	.dy-unlock-mask {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 9999;
		background-color: rgba(0, 0, 0, 0.6);
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.dy-unlock {
		position: relative;
		width: 600rpx;
		height: 650rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.dy-unlock-bg {
		position: absolute;
		width: 600rpx;
		height: 650rpx;
	}

	.dy-unlock-num {
		position: absolute;
		top: 180rpx;
		left: 0;
		width: 100%;
		text-align: center;
		font-size: 45rpx;
		font-weight: bold;
		color: #FFF3BE;
		z-index: 2;
	}

	.dy-unlock-btns {
		position: absolute;
		bottom: 80rpx;
		left: 80rpx;
		right: 80rpx;
		z-index: 2;
	}

	.dy-unlock-btn {
		display: flex;
		align-items: center;
		background-color: #FFF3EB;
		border-radius: 53rpx;
		padding: 20rpx;
		margin-bottom: 20rpx;

		image {
			width: 52rpx;
			height: 52rpx;
			margin-left: 40rpx;
		}
	}

	.dy-unlock-btn-text {
		display: flex;
		flex-direction: column;
		margin-left: 20rpx;
	}

	.dy-unlock-btn-title {
		font-size: 26rpx;
		font-weight: bold;
		color: #333333;
	}

	.dy-unlock-btn-desc {
		font-size: 22rpx;
		color: #666666;
		margin-top: 4rpx;
	}

	.dy-unlock-close {
		position: absolute;
		top: 0;
		right: 0;
		width: 50rpx;
		height: 50rpx;
		z-index: 3;
	}

	.dy-unlock-confirm {
		position: absolute;
		bottom: 35rpx;
		width: 321rpx;
		height: 83rpx;
		z-index: 3;
	}
</style>

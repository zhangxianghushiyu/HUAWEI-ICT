<template>
	<view class="dy-progress" :style="{ opacity: visible ? 1 : 0 }">
		<!-- 拖动时大时间显示 -->
		<view v-if="dragging" class="dy-progress-time-drag">
			<text>{{ formatSeconds(sliderTime) }}</text>
			<text class="dy-progress-split">/</text>
			<text class="dy-progress-time-total">{{ formatSeconds(videoTime) }}</text>
		</view>

		<!-- 进度条 -->
		<view class="dy-progress-bar" @touchstart="onTouchStart" @touchmove="onTouchMove" @touchend="onTouchEnd">
			<view class="dy-progress-track"></view>
			<view class="dy-progress-fill" :style="{ width: progressPercent + '%' }"></view>
			<view class="dy-progress-thumb" :style="{ left: progressPercent + '%' }"></view>
		</view>

		<!-- 时间 -->
		<view class="dy-progress-time">
			<text>{{ formatSeconds(currentTime) }}</text>
			<text class="dy-progress-split">/</text>
			<text class="dy-progress-time-total">{{ formatSeconds(videoTime) }}</text>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			currentTime: {
				type: Number,
				default: 0
			},
			videoTime: {
				type: Number,
				default: 0
			},
			visible: {
				type: Boolean,
				default: true
			}
		},
		data() {
			return {
				dragging: false,
				sliderTime: 0,
				startX: 0,
				barWidth: 0
			}
		},
		computed: {
			progressPercent() {
				const duration = this.videoTime || 0
				if (duration <= 0) return 0
				const current = this.dragging ? this.sliderTime : (this.currentTime || 0)
				return Math.min(100, Math.max(0, (current / duration) * 100))
			}
		},
		methods: {
			formatSeconds(seconds) {
				if (!seconds || seconds < 0) return '00:00'
				const h = Math.floor(seconds / 3600)
				const m = Math.floor((seconds % 3600) / 60)
				const s = Math.floor(seconds % 60)
				const mm = m < 10 ? '0' + m : m
				const ss = s < 10 ? '0' + s : s
				if (h > 0) {
					const hh = h < 10 ? '0' + h : h
					return `${hh}:${mm}:${ss}`
				}
				return `${mm}:${ss}`
			},

			getBarRect() {
				return new Promise((resolve) => {
					const query = uni.createSelectorQuery().in(this)
					query.select('.dy-progress-bar').boundingClientRect(rect => {
						resolve(rect || { width: 0, left: 0 })
					}).exec()
				})
			},

			async onTouchStart(e) {
				const rect = await this.getBarRect()
				this.barWidth = rect.width
				this.startX = rect.left
				this.dragging = true
				this.updateSliderTime(e)
			},

			async onTouchMove(e) {
				if (!this.dragging) return
				this.updateSliderTime(e)
			},

			async onTouchEnd(e) {
				if (!this.dragging) return
				this.updateSliderTime(e)
				this.dragging = false
				this.$emit('seek', this.sliderTime)
			},

			updateSliderTime(e) {
				const clientX = e.changedTouches[0].clientX
				const offsetX = clientX - this.startX
				const ratio = Math.min(1, Math.max(0, offsetX / this.barWidth))
				const duration = this.videoTime || 0
				this.sliderTime = ratio * duration
			}
		}
	}
</script>

<style lang="scss" scoped>
	.dy-progress {
		position: absolute;
		left: 0;
		right: 0;
		bottom: 40rpx;
		z-index: 20;
		padding: 0 30rpx;
		transition: opacity 0.3s ease;
	}

	.dy-progress-time-drag {
		position: absolute;
		left: 50%;
		bottom: 70rpx;
		transform: translateX(-50%);
		font-size: 60rpx;
		color: #ffffff;
		font-weight: bold;
		text-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.5);
	}

	.dy-progress-split {
		margin: 0 10rpx;
		opacity: 0.6;
		font-size: 24rpx;
	}

	.dy-progress-time-total {
		opacity: 0.6;
	}

	.dy-progress-bar {
		position: relative;
		height: 40rpx;
		display: flex;
		align-items: center;
	}

	.dy-progress-track {
		position: absolute;
		left: 0;
		right: 0;
		height: 4rpx;
		background-color: rgba(255, 255, 255, 0.3);
		border-radius: 4rpx;
	}

	.dy-progress-fill {
		position: absolute;
		left: 0;
		height: 4rpx;
		background-color: #ffffff;
		border-radius: 4rpx;
	}

	.dy-progress-thumb {
		position: absolute;
		width: 16rpx;
		height: 16rpx;
		margin-left: -8rpx;
		background-color: #ffffff;
		border-radius: 50%;
	}

	.dy-progress-time {
		margin-top: 8rpx;
		font-size: 22rpx;
		color: #ffffff;
		text-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.3);
	}
</style>

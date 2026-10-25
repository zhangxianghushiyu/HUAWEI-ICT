<template>
	<view v-if="times > -1" class="dy-red-packet" @click="onTap">
		<image class="dy-red-packet-img" src="/static/6.png" mode="aspectFit" />
		<text v-if="isEnd" class="dy-red-packet-text">立即领取</text>
		<text v-else class="dy-red-packet-time">{{ remainText }}</text>
	</view>
</template>

<script>
	export default {
		props: {
			times: {
				type: Number,
				default: -1
			}
		},
		data() {
			return {
				isEnd: false,
				remainMs: 0,
				timer: null
			}
		},
		computed: {
			remainText() {
				if (this.remainMs <= 0) return '00:00'
				const totalSeconds = Math.ceil(this.remainMs / 1000)
				const m = Math.floor(totalSeconds / 60)
				const s = totalSeconds % 60
				return `${m < 10 ? '0' + m : m}:${s < 10 ? '0' + s : s}`
			}
		},
		watch: {
			times: {
				immediate: true,
				handler(newVal) {
					this.reset(newVal)
				}
			}
		},
		beforeDestroy() {
			this.clearTimer()
		},
		methods: {
			reset(ms) {
				this.clearTimer()
				this.isEnd = false
				if (ms <= 0) {
					this.remainMs = 0
					this.isEnd = true
					return
				}
				this.remainMs = ms
				this.timer = setInterval(() => {
					this.remainMs -= 100
					if (this.remainMs <= 0) {
						this.remainMs = 0
						this.isEnd = true
						this.clearTimer()
						this.$emit('end')
					}
				}, 100)
			},

			clearTimer() {
				if (this.timer) {
					clearInterval(this.timer)
					this.timer = null
				}
			},

			onTap() {
				if (this.isEnd) {
					this.$emit('receive')
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.dy-red-packet {
		position: absolute;
		top: 12vh;
		right: 20rpx;
		z-index: 30;
		width: 120rpx;
		height: 120rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.dy-red-packet-img {
		width: 120rpx;
		height: 120rpx;
	}

	.dy-red-packet-text {
		position: absolute;
		left: 15rpx;
		bottom: 15rpx;
		font-size: 22rpx;
		color: #fff;
		z-index: 31;
	}

	.dy-red-packet-time {
		position: absolute;
		left: 0;
		bottom: 15rpx;
		width: 100%;
		text-align: center;
		font-size: 22rpx;
		color: #fff;
		z-index: 31;
	}
</style>

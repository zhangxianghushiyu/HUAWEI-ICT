<template>
	<view class="dy-menu">
		<!-- 倍速 -->
		<view class="dy-menu-item" @tap.stop="handleRate" @touchstart.stop="onTouchStop" @touchend.stop="onTouchStop">
			<view class="dy-menu-icon">
				<image class="dy-menu-img" src="/static/juhe/2.png" mode="aspectFit" />
			</view>
			<text class="dy-menu-text">倍速</text>
		</view>

		<!-- 点赞 -->
		<view class="dy-menu-item" @tap.stop="handleLike" @touchstart.stop="onTouchStop" @touchend.stop="onTouchStop">
			<view class="dy-menu-icon" :class="{ 'dy-menu-icon-active': likeeffect }">
				<image v-if="currentItem.likeShow" class="dy-menu-img" src="/static/icon/selectTaoxin.png" mode="aspectFit" />
				<image v-else class="dy-menu-img" src="/static/icon/taoxin.png" mode="aspectFit" />
			</view>
			<text class="dy-menu-text">{{ formatCount(currentItem.likeCount) }}</text>
		</view>

		<!-- 收藏 -->
		<view class="dy-menu-item" @tap.stop="handleCollect" @touchstart.stop="onTouchStop" @touchend.stop="onTouchStop">
			<view class="dy-menu-icon" :class="{ 'dy-menu-icon-active': collecteffect }">
				<image v-if="currentItem.collectShow" class="dy-menu-img" src="/static/icon/selectStar.png" mode="aspectFit" />
				<image v-else class="dy-menu-img" src="/static/icon/star.png" mode="aspectFit" />
			</view>
			<text class="dy-menu-text">{{ formatCount(currentItem.collectCount) }}</text>
		</view>

		<!-- 分享 -->
		<view v-if="showShare" class="dy-menu-item" @tap.stop="handleShare" @touchstart.stop="onTouchStop" @touchend.stop="onTouchStop">
			<view class="dy-menu-icon">
				<image class="dy-menu-img" src="/static/icon/ward.png" mode="aspectFit" />
			</view>
			<text class="dy-menu-text">分享</text>
		</view>

		<!-- 举报 -->
		<view v-if="showReport" class="dy-menu-item" @tap.stop="handleReport" @touchstart.stop="onTouchStop" @touchend.stop="onTouchStop">
			<view class="dy-menu-icon dy-menu-icon-text">⚠️</view>
			<text class="dy-menu-text">举报</text>
		</view>

		<!-- 短剧：选集 -->
		<view v-if="isDrama" class="dy-menu-item" @tap.stop="handleEpisode" @touchstart.stop="onTouchStop" @touchend.stop="onTouchStop">
			<view class="dy-menu-icon">
				<image class="dy-menu-img" src="/static/icon/selectedWorks.png" mode="aspectFit" />
			</view>
			<text class="dy-menu-text">选集</text>
		</view>

		<!-- 娱乐刷流：看系列 -->
		<view v-if="!isDrama && currentItem.dramaId" class="dy-menu-item" @tap.stop="handleSeriesDetail" @touchstart.stop="onTouchStop" @touchend.stop="onTouchStop">
			<view class="dy-menu-icon">
				<image class="dy-menu-img" src="/static/icon/selectedWorks.png" mode="aspectFit" />
			</view>
			<text class="dy-menu-text">系列</text>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			item: {
				type: Object,
				default: () => ({})
			},
			isDrama: {
				type: Boolean,
				default: false
			},
			showShare: {
				type: Boolean,
				default: true
			},
			showReport: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				likeeffect: false,
				collecteffect: false,
				likeTimer: null,
				collectTimer: null
			}
		},
		computed: {
			currentItem() {
				return this.item || {}
			}
		},
		watch: {
			item() {
				this.likeeffect = false
				this.collecteffect = false
			}
		},
		methods: {
			onTouchStop() {},

			formatCount(count) {
				const n = Number(count) || 0
				if (n >= 10000) {
					return (n / 10000).toFixed(0) + '万+'
				}
				return n
			},

			// 点赞（key: 0 双击点赞，1 单击点赞）
			handleLike() {
				console.log('[dy-menu] handleLike', this.currentItem.videoIndex)
				const obj = { ...this.currentItem }
				obj.likeShow = !obj.likeShow
				obj.likeCount = Number(obj.likeCount) || 0
				if (obj.likeShow) {
					obj.likeCount++
				} else {
					obj.likeCount = Math.max(0, obj.likeCount - 1)
				}
				this.triggerEffect('likeeffect')
				this.$emit('action', { type: 'like', item: obj })
			},

			doubleLike() {
				if (this.currentItem.likeShow) return
				const obj = { ...this.currentItem }
				obj.likeShow = true
				obj.likeCount = (Number(obj.likeCount) || 0) + 1
				this.triggerEffect('likeeffect')
				this.$emit('action', { type: 'like', item: obj })
			},

			handleCollect() {
				console.log('[dy-menu] handleCollect', this.currentItem.videoIndex)
				const obj = { ...this.currentItem }
				obj.collectShow = !obj.collectShow
				obj.collectCount = Number(obj.collectCount) || 0
				if (obj.collectShow) {
					obj.collectCount++
				} else {
					obj.collectCount = Math.max(0, obj.collectCount - 1)
				}
				this.triggerEffect('collecteffect')
				this.$emit('action', { type: 'collect', item: obj })
			},

			handleShare() {
				this.$emit('action', { type: 'share', item: this.currentItem })
			},

			handleReport() {
				this.$emit('action', { type: 'report', item: this.currentItem })
			},

			handleEpisode() {
				this.$emit('action', { type: 'episode', item: this.currentItem })
			},

			handleRate() {
				this.$emit('action', { type: 'rate', item: this.currentItem })
			},

			handleSeriesDetail() {
				this.$emit('action', { type: 'seriesDetail', item: this.currentItem })
			},

			triggerEffect(key) {
				this[key] = false
				this.$nextTick(() => {
					this[key] = true
					const timerKey = key === 'likeeffect' ? 'likeTimer' : 'collectTimer'
					clearTimeout(this[timerKey])
					this[timerKey] = setTimeout(() => {
						this[key] = false
					}, 500)
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.dy-menu {
		position: absolute;
		right: 20rpx;
		bottom: 220rpx;
		z-index: 20;
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.dy-menu-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-bottom: 36rpx;
	}

	.dy-menu-icon {
		width: 84rpx;
		height: 84rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.dy-menu-icon-active {
		animation: likeBlink 0.5s ease-in-out;
	}

	.dy-menu-img {
		width: 70rpx;
		height: 70rpx;
	}

	.dy-menu-icon-text {
		font-size: 40rpx;
	}

	.dy-menu-text {
		margin-top: 6rpx;
		font-size: 24rpx;
		color: #ffffff;
		text-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.3);
	}

	@keyframes likeBlink {
		0% {
			transform: scale(1);
		}

		50% {
			transform: scale(1.3);
		}

		100% {
			transform: scale(1);
		}
	}
</style>

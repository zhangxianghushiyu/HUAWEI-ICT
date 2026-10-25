<template>
	<view v-if="visible" class="dy-episode-mask" @click="onClose">
		<view class="dy-episode" @click.stop>
			<view class="dy-episode-header">
				<uv-tabs :list="tabList" :activeStyle="{ color: '#000', fontWeight: 'bold' }"
					:inactiveStyle="{ color: '#999' }" lineColor="#FE6100" @click="onTabClick" />
				<uni-icons type="down" size="22" color="#333" @click="onClose" />
			</view>

			<view v-if="tabIndex === 0" class="dy-episode-body">
				<view class="dy-episode-info">
					<image class="dy-episode-poster" :src="info.titleImg || ''" mode="aspectFill" />
					<view class="dy-episode-info-right">
						<text class="dy-episode-title">{{ info.title || '' }}</text>
						<text class="dy-episode-count">共 {{ list.length }} 集</text>
					</view>
				</view>
				<scroll-view scroll-y class="dy-episode-list">
					<view class="dy-episode-grid">
						<view v-for="(item, index) in list" :key="index" class="dy-episode-item"
							:class="{ 'dy-episode-current': currentIndex === index, 'dy-episode-locked': !item.videoUrl }"
							@click="onSelect(index)">
							<text v-if="item.videoUrl">{{ index + 1 }}</text>
							<image v-else src="/static/icon/lock.png" mode="widthFix" class="dy-episode-lock" />
							<image v-if="currentIndex === index" src="/static/images/change/playing.png" mode="widthFix"
								class="dy-episode-playing" />
						</view>
					</view>
				</scroll-view>
			</view>

			<view v-else class="dy-episode-synopsis">
				<text>{{ info.details || info.content || '暂无简介' }}</text>
			</view>
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
			info: {
				type: Object,
				default: () => ({})
			},
			list: {
				type: Array,
				default: () => []
			},
			currentIndex: {
				type: Number,
				default: 0
			}
		},
		data() {
			return {
				tabIndex: 0,
				tabList: [{
						name: '选集'
					},
					{
						name: '简介'
					}
				]
			}
		},
		watch: {
			visible(val) {
				if (val) this.tabIndex = 0
			}
		},
		methods: {
			onTabClick(e) {
				this.tabIndex = e.index
			},
			onSelect(index) {
				this.$emit('select', index)
			},
			onClose() {
				this.$emit('close')
			}
		}
	}
</script>

<style lang="scss" scoped>
	.dy-episode-mask {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 999;
		background-color: rgba(0, 0, 0, 0.4);
	}

	.dy-episode {
		position: absolute;
		left: 0;
		right: 0;
		bottom: 0;
		height: 70vh;
		background-color: #ffffff;
		border-radius: 40rpx 40rpx 0 0;
		padding: 30rpx;
		padding-bottom: constant(safe-area-inset-bottom);
		padding-bottom: env(safe-area-inset-bottom);
	}

	.dy-episode-header {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-bottom: 20rpx;
	}

	.dy-episode-body {
		display: flex;
		flex-direction: column;
		height: calc(100% - 80rpx);
	}

	.dy-episode-info {
		display: flex;
		align-items: center;
		margin-bottom: 20rpx;
	}

	.dy-episode-poster {
		width: 144rpx;
		height: 190rpx;
		border-radius: 16rpx;
		margin-right: 20rpx;
	}

	.dy-episode-info-right {
		display: flex;
		flex-direction: column;
		justify-content: space-around;
		height: 190rpx;
	}

	.dy-episode-title {
		font-size: 32rpx;
		font-weight: bold;
		color: #333333;
	}

	.dy-episode-count {
		font-size: 24rpx;
		color: #999999;
	}

	.dy-episode-list {
		flex: 1;
		/* 允许 flex 子项收缩到内容以下，保证集数多时在面板内部滚动而不是溢出 */
		min-height: 0;
	}

	.dy-episode-grid {
		display: flex;
		flex-wrap: wrap;
	}

	.dy-episode-item {
		width: 98rpx;
		height: 98rpx;
		line-height: 98rpx;
		text-align: center;
		margin: 16rpx;
		font-size: 30rpx;
		color: #999999;
		background-color: #f5f5f5;
		border-radius: 16rpx;
		position: relative;
	}

	.dy-episode-current {
		color: #000000;
		background-color: #ffe4d0;
	}

	.dy-episode-locked {
		background-color: #eeeeee;
	}

	.dy-episode-lock {
		width: 30rpx;
		vertical-align: middle;
	}

	.dy-episode-playing {
		position: absolute;
		width: 30rpx;
		right: 8rpx;
		top: 8rpx;
	}

	.dy-episode-synopsis {
		padding: 20rpx;
		font-size: 28rpx;
		color: #333333;
		line-height: 1.6;
	}
</style>

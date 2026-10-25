<template>
	<view class="activity-list-container">
		<!-- 顶部标题 -->
		<view class="header">
			<!-- 返回主页按钮 -->
			<view class="home-btn" @click="goToHome">
				<text class="home-text">返回</text>
			</view>
			
			<text class="title">活动报名</text>
			<text class="subtitle">精彩活动，等你来参加</text>
		</view>

		<!-- 活动列表 -->
		<scroll-view scroll-y class="activity-list" @scrolltolower="loadMore">
			<view v-if="loading && activityList.length === 0" class="loading-box">
				<text>加载中...</text>
			</view>

			<view v-else-if="activityList.length === 0" class="empty-box">
				<image src="/static/empty.png" mode="aspectFit" class="empty-img"></image>
				<text class="empty-text">暂无活动</text>
			</view>

			<view v-else>
				<view 
					v-for="(item, index) in activityList" 
					:key="item.id" 
					class="activity-item"
					@click="goToDetail(item.id)"
				>
					<!-- 封面图 -->
					<view class="activity-cover">
						<image 
							:src="item.coverImage || `https://picsum.photos/750/400?random=${index}`" 
							mode="aspectFill" 
							class="cover-img"
						></image>
						<!-- 状态标签 -->
						<view class="status-tag" :class="'status-' + item.status">
							{{ getStatusText(item.status) }}
						</view>
					</view>

					<!-- 内容区 -->
					<view class="activity-content">
						<text class="activity-title">{{ item.title }}</text>
						<text class="activity-desc" v-if="item.description">{{ item.description }}</text>
						
						<view class="activity-info">
							<view class="info-item">
								<text class="info-icon">📍</text>
								<text class="info-text">{{ item.location || '待定' }}</text>
							</view>
							<view class="info-item">
								<text class="info-icon">👥</text>
								<text class="info-text">{{ item.signupCount || 0 }}人已报名</text>
							</view>
						</view>

						<view class="activity-footer">
							<text class="time-text">{{ formatTime(item.startTime) }} - {{ formatTime(item.endTime) }}</text>
							<view v-if="item.hasSignedUp" class="signed-badge">
								<text>已报名</text>
							</view>
						</view>
					</view>
				</view>

				<!-- 加载更多提示 -->
				<view v-if="loading" class="loading-more">
					<text>加载中...</text>
				</view>
				<view v-else-if="noMore" class="no-more">
					<text>没有更多了</text>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
import api from '@/common/api/apiService.js'

export default {
	data() {
		return {
			activityList: [],
			loading: false,
			noMore: false,
			pageNum: 1,
			pageSize: 10
		}
	},
	onLoad() {
		this.loadActivityList()
	},
	onShow() {
		// 页面显示时刷新列表
		this.refreshList()
	},
	methods: {
		// 返回主页
		goToHome() {
			uni.switchTab({
				url: '/pages/articel/index',
				fail: (err) => {
					console.error('返回主页失败:', err)
					uni.showToast({
						title: '跳转失败，请重试',
						icon: 'none'
					})
				}
			})
		},
		
		// 加载活动列表
		async loadActivityList() {
			if (this.loading) return
			
			this.loading = true
			try {
				const res = await api.getActivityList({
					pageNum: this.pageNum,
					pageSize: this.pageSize
				})
				
				if (res.code === 200) {
					const newList = res.data?.list || []
					
					if (this.pageNum === 1) {
						this.activityList = newList
					} else {
						this.activityList = [...this.activityList, ...newList]
					}
					
					// 判断是否还有更多数据
					if (newList.length < this.pageSize) {
						this.noMore = true
					}
				} else {
					uni.showToast({
						title: res.msg || '加载失败',
						icon: 'none'
					})
				}
			} catch (error) {
				console.error('加载活动列表失败:', error)
				uni.showToast({
					title: '网络错误',
					icon: 'none'
				})
			} finally {
				this.loading = false
			}
		},

		// 刷新列表
		refreshList() {
			this.pageNum = 1
			this.noMore = false
			this.loadActivityList()
		},

		// 加载更多
		loadMore() {
			if (this.noMore || this.loading) return
			this.pageNum++
			this.loadActivityList()
		},

		// 跳转到详情页
		goToDetail(activityId) {
			uni.navigateTo({
				url: `/pages/activity/detail?id=${activityId}`
			})
		},

		// 获取状态文本
		getStatusText(status) {
			const statusMap = {
				0: '未开始',
				1: '进行中',
				2: '已结束'
			}
			return statusMap[status] || '未知'
		},

		// 格式化时间
		formatTime(timeStr) {
			if (!timeStr) return ''
			const date = new Date(timeStr)
			const month = date.getMonth() + 1
			const day = date.getDate()
			const hours = date.getHours().toString().padStart(2, '0')
			const minutes = date.getMinutes().toString().padStart(2, '0')
			return `${month}月${day}日 ${hours}:${minutes}`
		}
	}
}
</script>

<style lang="scss" scoped>
.activity-list-container {
	min-height: 100vh;
	background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
	position: relative;
}

.header {
	padding: 40rpx 30rpx 30rpx;
	background: linear-gradient(135deg, #E8783B, #F4A460);
	position: relative;

	.title {
		display: block;
		font-size: 48rpx;
		font-weight: bold;
		color: #ffffff;
		margin-bottom: 10rpx;
	}

	.subtitle {
		display: block;
		font-size: 28rpx;
		color: rgba(255, 255, 255, 0.9);
	}
}

/* 返回按钮样式 */
.home-btn {
	position: absolute;
	top: 40rpx;
	right: 30rpx;
	z-index: 999;
	min-width: 120rpx;
	height: 60rpx;
	background: rgba(255, 255, 255, 0.2);
	border-radius: 30rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
	backdrop-filter: blur(20rpx);
	-webkit-backdrop-filter: blur(20rpx);
	border: 1rpx solid rgba(255, 255, 255, 0.3);
	padding: 0 30rpx;
	
	.home-text {
		font-size: 28rpx;
		color: #ffffff;
		font-weight: 500;
		letter-spacing: 2rpx;
	}
	
	&:active {
		transform: scale(0.95);
		opacity: 0.8;
		background: rgba(255, 255, 255, 0.3);
	}
}

.activity-list {
	height: calc(100vh - 200rpx);
	padding: 20rpx;
}

.loading-box,
.empty-box {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding: 100rpx 0;
	
	.empty-img {
		width: 300rpx;
		height: 300rpx;
		margin-bottom: 30rpx;
	}
	
	.empty-text {
		font-size: 28rpx;
		color: #999;
	}
}

.activity-item {
	background: #ffffff;
	border-radius: 32rpx;
	margin-bottom: 20rpx;
	overflow: hidden;
	box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
	transition: all 0.3s;

	&:active {
		transform: scale(0.98);
	}
}

.activity-cover {
	position: relative;
	width: 100%;
	height: 300rpx;
	
	.cover-img {
		width: 100%;
		height: 100%;
	}
	
	.status-tag {
		position: absolute;
		top: 20rpx;
		right: 20rpx;
		padding: 8rpx 20rpx;
		border-radius: 30rpx;
		font-size: 24rpx;
		color: #ffffff;
		
		&.status-0 {
			background: rgba(0, 0, 0, 0.5);
		}
		
		&.status-1 {
			background: linear-gradient(135deg, #E8783B, #F4A460);
		}
		
		&.status-2 {
			background: rgba(153, 153, 153, 0.8);
		}
	}
}

.activity-content {
	padding: 24rpx;
}

.activity-title {
	display: block;
	font-size: 32rpx;
	font-weight: 700;
	color: #5D3A1A;
	margin-bottom: 12rpx;
	line-height: 1.4;
}

.activity-desc {
	display: block;
	font-size: 26rpx;
	color: #8B6B4A;
	margin-bottom: 16rpx;
	line-height: 1.5;
}

.activity-info {
	display: flex;
	gap: 24rpx;
	margin-bottom: 16rpx;
	
	.info-item {
		display: flex;
		align-items: center;
		gap: 6rpx;
		
		.info-icon {
			font-size: 28rpx;
		}
		
		.info-text {
			font-size: 24rpx;
			color: #999;
		}
	}
}

.activity-footer {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding-top: 16rpx;
	border-top: 1rpx solid #f0f0f0;
	
	.time-text {
		font-size: 24rpx;
		color: #999;
		flex: 1;
	}
	
	.signed-badge {
		padding: 6rpx 16rpx;
		background: linear-gradient(135deg, #E8783B, #F4A460);
		border-radius: 20rpx;
		
		text {
			font-size: 22rpx;
			color: #ffffff;
		}
	}
}

.loading-more,
.no-more {
	text-align: center;
	padding: 30rpx 0;
	
	text {
		font-size: 26rpx;
		color: #999;
	}
}
</style>

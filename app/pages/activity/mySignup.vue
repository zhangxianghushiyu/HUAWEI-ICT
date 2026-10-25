<template>
	<view class="my-signup-container">
		<!-- 顶部标题 -->
		<view class="header">
			<view class="back-btn" @click="goBack">
				<text class="back-icon">←</text>
			</view>
			<text class="title">我的报名</text>
			<text class="subtitle">我参与的活动</text>
		</view>

		<!-- 报名列表 -->
		<scroll-view scroll-y class="signup-list" @scrolltolower="loadMore">
			<view v-if="loading && signupList.length === 0" class="loading-box">
				<text>加载中...</text>
			</view>

			<view v-else-if="signupList.length === 0" class="empty-box">
				<image src="/static/empty.png" mode="aspectFit" class="empty-img"></image>
				<text class="empty-text">暂无报名记录</text>
				<text class="empty-tip">快去报名参加活动吧~</text>
			</view>

			<view v-else>
				<view 
					v-for="(item, index) in signupList" 
					:key="item.signupId" 
					class="signup-item"
					@click="goToDetail(item.activityId)"
				>
					<!-- 封面图 -->
					<view class="item-cover">
						<image 
							:src="item.activityCoverImage || '/static/default-cover.png'" 
							mode="aspectFill" 
							class="cover-img"
						></image>
						<!-- 报名状态标签 -->
						<view class="status-tag" :class="item.signupStatus === 0 ? 'signed' : 'cancelled'">
							{{ item.signupStatus === 0 ? '已报名' : '已取消' }}
						</view>
					</view>

					<!-- 内容区 -->
					<view class="item-content">
						<text class="item-title">{{ item.activityTitle }}</text>
						
						<view class="item-info">
							<view class="info-row">
								<text class="info-icon">📅</text>
								<text class="info-text">{{ formatTime(item.activityStartTime) }}</text>
							</view>
							<view class="info-row">
								<text class="info-icon">📍</text>
								<text class="info-text">{{ item.activityLocation || '待定' }}</text>
							</view>
						</view>

						<view class="item-footer">
							<text class="signup-time">报名时间：{{ formatTime(item.signupTime) }}</text>
							<view 
								v-if="item.signupStatus === 0" 
								class="cancel-btn"
								@click.stop="handleCancel(item.activityId)"
							>
								<text>取消报名</text>
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
			signupList: [],
			loading: false,
			noMore: false,
			pageNum: 1,
			pageSize: 10
		}
	},
	onLoad() {
		this.loadSignupList()
	},
	onShow() {
		// 页面显示时刷新列表
		this.refreshList()
	},
	methods: {
		// 返回上一页
		goBack() {
			uni.navigateBack()
		},
		
		// 加载报名列表
		async loadSignupList() {
			if (this.loading) return
			
			this.loading = true
			try {
				const res = await api.getMySignupList({
					pageNum: this.pageNum,
					pageSize: this.pageSize
				})
				
				if (res.code === 200) {
					const newList = res.data?.list || []
					
					if (this.pageNum === 1) {
						this.signupList = newList
					} else {
						this.signupList = [...this.signupList, ...newList]
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
				console.error('加载报名列表失败:', error)
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
			this.loadSignupList()
		},

		// 加载更多
		loadMore() {
			if (this.noMore || this.loading) return
			this.pageNum++
			this.loadSignupList()
		},

		// 跳转到活动详情
		goToDetail(activityId) {
			uni.navigateTo({
				url: `/pages/activity/detail?id=${activityId}`
			})
		},

		// 取消报名
		async handleCancel(activityId) {
			uni.showModal({
				title: '取消报名',
				content: '确定要取消报名吗？',
				success: async (modalRes) => {
					if (modalRes.confirm) {
						uni.showLoading({
							title: '取消中...'
						})
						
						try {
							const res = await api.cancelSignup({
								activityId: activityId
							})
							
							uni.hideLoading()
							
							if (res.code === 200) {
								uni.showToast({
									title: '已取消',
									icon: 'success'
								})
								// 刷新列表
								this.refreshList()
							} else {
								uni.showToast({
									title: res.msg || '取消失败',
									icon: 'none'
								})
							}
						} catch (error) {
							uni.hideLoading()
							console.error('取消报名失败:', error)
							uni.showToast({
								title: '网络错误',
								icon: 'none'
							})
						}
					}
				}
			})
		},

		// 格式化时间
		formatTime(timeStr) {
			if (!timeStr) return ''
			const date = new Date(timeStr)
			const year = date.getFullYear()
			const month = (date.getMonth() + 1).toString().padStart(2, '0')
			const day = date.getDate().toString().padStart(2, '0')
			const hours = date.getHours().toString().padStart(2, '0')
			const minutes = date.getMinutes().toString().padStart(2, '0')
			return `${year}-${month}-${day} ${hours}:${minutes}`
		}
	}
}
</script>

<style lang="scss" scoped>
.my-signup-container {
	min-height: 100vh;
	background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
}

.header {
	padding: 40rpx 30rpx 30rpx;
	background: linear-gradient(135deg, #E8783B, #F4A460);
	position: relative;
	
	.back-btn {
		position: absolute;
		top: 40rpx;
		left: 30rpx;
		width: 60rpx;
		height: 60rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		
		.back-icon {
			font-size: 48rpx;
			color: #ffffff;
		}
	}
	
	.title {
		display: block;
		font-size: 48rpx;
		font-weight: 700;
		color: #ffffff;
		margin-bottom: 10rpx;
	}
	
	.subtitle {
		display: block;
		font-size: 28rpx;
		color: rgba(255, 255, 255, 0.9);
	}
}

.signup-list {
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
		margin-bottom: 10rpx;
	}
	
	.empty-tip {
		font-size: 24rpx;
		color: #ccc;
	}
}

.signup-item {
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

.item-cover {
	position: relative;
	width: 100%;
	height: 250rpx;
	
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
		
		&.signed {
			background: linear-gradient(135deg, #E8783B, #F4A460);
		}
		
		&.cancelled {
			background: rgba(153, 153, 153, 0.8);
		}
	}
}

.item-content {
	padding: 24rpx;
}

.item-title {
	display: block;
	font-size: 32rpx;
	font-weight: 700;
	color: #5D3A1A;
	margin-bottom: 16rpx;
	line-height: 1.4;
}

.item-info {
	margin-bottom: 16rpx;
	
	.info-row {
		display: flex;
		align-items: center;
		padding: 8rpx 0;
		
		.info-icon {
			font-size: 28rpx;
			margin-right: 8rpx;
		}
		
		.info-text {
			font-size: 24rpx;
			color: #8B6B4A;
		}
	}
}

.item-footer {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding-top: 16rpx;
	border-top: 1rpx solid #f0f0f0;
	
	.signup-time {
		font-size: 22rpx;
		color: #999;
		flex: 1;
	}
	
	.cancel-btn {
		padding: 8rpx 20rpx;
		border: 1rpx solid #E8783B;
		border-radius: 30rpx;
		
		text {
			font-size: 24rpx;
			color: #E8783B;
		}
		
		&:active {
			background: #FFF5EC;
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

<template>
	<view class="detail-container">
		<!-- 顶部品牌 Banner（与首页一致，并整合返回按钮） -->
		<view class="top-banner">
			<image src="/static/liuyueliu/liuyueliulogo.jpeg" mode="aspectFill" class="banner-img"></image>
			<view class="banner-overlay">
				<text class="banner-title">六月六星野营地</text>
				<text class="banner-subtitle">
					<text class="tag">贵安新区</text>
					<text class="tag">田园露营</text>
					<text class="tag">布依风情</text>
				</text>
			</view>
		</view>

		<!-- 滚动内容区（无顶部留白） -->
		<scroll-view scroll-y class="content-scroll">

			<!-- 活动信息卡片 -->
			<view class="info-card">
				<text class="activity-title">{{ activityDetail.title }}</text>
				
				<view class="status-row">
					<view class="status-badge" :class="'status-' + activityDetail.status">
						{{ getStatusText(activityDetail.status) }}
					</view>
					<view class="signup-count">
						<text class="count-num">{{ activityDetail.signupCount || 0 }}</text>
						<text class="count-label">人已报名</text>
					</view>
				</view>

				<view class="info-list">
					<view class="info-row">
						<text class="info-label">📅 活动时间</text>
						<text class="info-value">{{ formatTime(activityDetail.startTime) }} - {{ formatTime(activityDetail.endTime) }}</text>
					</view>
					<view class="info-row">
						<text class="info-label">📍 活动地点</text>
						<text class="info-value">{{ activityDetail.location || '待定' }}</text>
					</view>
					<view class="info-row">
						<text class="info-label">🏢 主办方</text>
						<text class="info-value">{{ activityDetail.organizer || '待定' }}</text>
					</view>
					<view class="info-row" v-if="activityDetail.contactPhone">
						<text class="info-label">📞 联系电话</text>
						<text class="info-value">{{ activityDetail.contactPhone }}</text>
					</view>
				</view>
			</view>

			<!-- 活动描述 -->
			<view class="desc-card">
				<text class="card-title">活动介绍</text>
				<text class="desc-text">{{ activityDetail.description || '暂无详细介绍' }}</text>
			</view>

			<!-- 我的报名状态卡片（仅已报名时显示） -->
			<view class="my-signup-card" v-if="activityDetail.hasSignedUp">
				<text class="card-title">我的报名状态</text>
				<view class="signup-status">
					<text class="success-icon">✓</text>
					<text class="success-text">您已报名成功</text>
				</view>
				<view class="signup-time" v-if="activityDetail.signupTime">
					<text>报名时间：{{ formatTime(activityDetail.signupTime) }}</text>
				</view>
			</view>

			<!-- 底部占位 -->
			<view class="bottom-placeholder"></view>
		</scroll-view>

		<!-- 底部操作栏 -->
		<view class="bottom-bar">
			<!-- 未报名状态：显示报名按钮 -->
			<view 
				v-if="!activityDetail.hasSignedUp" 
				class="signup-btn" 
				:class="{ disabled: !canSignup }"
				@click="handleSignup"
			>
				<text class="btn-text">{{ getSignupButtonText() }}</text>
			</view>
			<!-- 已报名状态：显示“去查看我的报名”按钮 -->
			<view 
				v-else 
				class="signup-btn"
				@click="goToMySignup"
			>
				<text class="btn-text">去查看我的报名</text>
			</view>
		</view>
	</view>
</template>

<script>
import api from '@/common/api/apiService.js'

export default {
	data() {
		return {
			activityId: null,
			activityDetail: {},
			loading: false
		}
	},
	computed: {
		// 是否可以报名（活动未结束即可报名）
		canSignup() {
			if (!this.activityDetail.status) return false
			// 活动已结束不能报名
			if (this.activityDetail.status === 2) return false
			return true
		}
	},
	onLoad(options) {
		if (options.id) {
			this.activityId = options.id
			this.loadActivityDetail()
		}
	},
	methods: {
		// 返回上一页
		goBack() {
			uni.navigateBack()
		},
		
		// 加载活动详情
		async loadActivityDetail() {
			this.loading = true
			try {
				const res = await api.getActivityDetail({
					activityId: this.activityId
				})
				
				if (res.code === 200) {
					this.activityDetail = res.data || {}
				} else {
					uni.showToast({
						title: res.msg || '加载失败',
						icon: 'none'
					})
					setTimeout(() => {
						uni.navigateBack()
					}, 1500)
				}
			} catch (error) {
				console.error('加载活动详情失败:', error)
				uni.showToast({
					title: '网络错误',
					icon: 'none'
				})
			} finally {
				this.loading = false
			}
		},

		// 报名活动
		async handleSignup() {
			if (!this.canSignup) {
				uni.showToast({
					title: '该活动无法报名',
					icon: 'none'
				})
				return
			}

			uni.showModal({
				title: '确认报名',
				content: '确定要报名参加此活动吗？',
				success: async (modalRes) => {
					if (modalRes.confirm) {
						uni.showLoading({
							title: '报名中...'
						})
						
						try {
							const res = await api.signupActivity({
								activityId: this.activityId
							})
							
							uni.hideLoading()
							
							if (res.code === 200) {
								uni.showToast({
									title: '报名成功',
									icon: 'success'
								})
								// 刷新详情，更新报名状态
								this.loadActivityDetail()
							} else {
								uni.showToast({
									title: res.msg || '报名失败',
									icon: 'none'
								})
							}
						} catch (error) {
							uni.hideLoading()
							console.error('报名失败:', error)
							uni.showToast({
								title: '网络错误',
								icon: 'none'
							})
						}
					}
				}
			})
		},

		// 跳转到我的报名页面
		goToMySignup() {
			uni.navigateTo({
				url: '/pages/activity/mySignup'
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
			const year = date.getFullYear()
			const month = (date.getMonth() + 1).toString().padStart(2, '0')
			const day = date.getDate().toString().padStart(2, '0')
			const hours = date.getHours().toString().padStart(2, '0')
			const minutes = date.getMinutes().toString().padStart(2, '0')
			return `${year}-${month}-${day} ${hours}:${minutes}`
		},

		// 获取报名按钮文本（根据活动状态变化）
		getSignupButtonText() {
			if (this.activityDetail.status === 2) {
				return '活动已结束'
			}
			return '立即报名'
		}
	}
}
</script>

<style lang="scss" scoped>
.detail-container {
	min-height: 100vh;
	background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
	position: relative;
}

/* ========== 顶部 Banner（与首页完全一致） ========== */
.top-banner {
	width: 100%;
	height: 400rpx;
	position: relative;
	overflow: hidden;
	
	&::after {
		content: '';
		position: absolute;
		left: 0;
		bottom: 0;
		right: 0;
		height: 60%;
		background: linear-gradient(to top, rgba(0,0,0,0.5) 0%, transparent 100%);
		pointer-events: none;
	}
}

.banner-img {
	width: 100%;
	height: 100%;
	display: block;
}

.banner-overlay {
	position: absolute;
	left: 0;
	bottom: 0;
	right: 0;
	padding: 40rpx 30rpx;
	z-index: 2;
}

.banner-title {
	font-size: 44rpx;
	font-weight: 700;
	color: #fff;
	display: block;
	margin-bottom: 16rpx;
	text-shadow: 0 2rpx 8rpx rgba(0,0,0,0.3);
	letter-spacing: 2rpx;
}

.banner-subtitle {
	display: flex;
	gap: 12rpx;
	
	.tag {
		font-size: 22rpx;
		color: rgba(255,255,255,0.9);
		background: rgba(255,255,255,0.2);
		backdrop-filter: blur(10rpx);
		padding: 6rpx 16rpx;
		border-radius: 32rpx;
		border: 1rpx solid rgba(255,255,255,0.15);
	}
}


/* 滚动内容区：因为 Banner 占位，无需额外 margin-top */
.content-scroll {
	height: calc(100vh - 400rpx);
	background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
}

/* 封面图 */
.cover-section {
	width: 100%;
	height: 400rpx;
	
	.cover-image {
		width: 100%;
		height: 100%;
	}
}

.info-card {
	background: #ffffff;
	margin: 20rpx;
	padding: 30rpx;
	border-radius: 32rpx;
	box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
	
	.activity-title {
		display: block;
		font-size: 36rpx;
		font-weight: 700;
		color: #5D3A1A;
		margin-bottom: 20rpx;
		line-height: 1.4;
	}
	
	.status-row {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 24rpx;
		
		.status-badge {
			padding: 8rpx 20rpx;
			border-radius: 30rpx;
			font-size: 24rpx;
			color: #ffffff;
			
			&.status-0 {
				background: #999;
			}
			
			&.status-1 {
				background: linear-gradient(135deg, #E8783B, #F4A460);
			}
			
			&.status-2 {
				background: #ccc;
			}
		}
		
		.signup-count {
			display: flex;
			align-items: baseline;
			
			.count-num {
				font-size: 36rpx;
				font-weight: 700;
				color: #E8783B;
				margin-right: 8rpx;
			}
			
			.count-label {
				font-size: 24rpx;
				color: #999;
			}
		}
	}
	
	.info-list {
		.info-row {
			display: flex;
			padding: 16rpx 0;
			border-bottom: 1rpx solid #f0f0f0;
			
			&:last-child {
				border-bottom: none;
			}
			
			.info-label {
				font-size: 26rpx;
				color: #8B6B4A;
				min-width: 180rpx;
			}
			
			.info-value {
				font-size: 26rpx;
				color: #5D3A1A;
				flex: 1;
			}
		}
	}
}

.desc-card {
	background: #ffffff;
	margin: 20rpx;
	padding: 30rpx;
	border-radius: 32rpx;
	box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
	
	.card-title {
		display: block;
		font-size: 30rpx;
		font-weight: 700;
		color: #5D3A1A;
		margin-bottom: 20rpx;
	}
	
	.desc-text {
		display: block;
		font-size: 28rpx;
		color: #8B6B4A;
		line-height: 1.8;
	}
}

.my-signup-card {
	background: #ffffff;
	margin: 20rpx;
	padding: 30rpx;
	border-radius: 32rpx;
	box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
	
	.card-title {
		display: block;
		font-size: 30rpx;
		font-weight: 700;
		color: #5D3A1A;
		margin-bottom: 20rpx;
	}
	
	.signup-status {
		display: flex;
		align-items: center;
		padding: 20rpx;
		background: linear-gradient(135deg, #FFF5EC, #FFEBD6);
		border-radius: 12rpx;
		
		.success-icon {
			font-size: 40rpx;
			color: #E8783B;
			margin-right: 12rpx;
		}
		
		.success-text {
			font-size: 28rpx;
			color: #8B6B4A;
			font-weight: 500;
		}
	}
	
	.signup-time {
		margin-top: 16rpx;
		text {
			font-size: 24rpx;
			color: #999;
		}
	}
}

.bottom-placeholder {
	height: 120rpx;
}

.bottom-bar {
	position: fixed;
	bottom: 0;
	left: 0;
	right: 0;
	height: 100rpx;
	background: #ffffff;
	box-shadow: 0 -4rpx 12rpx rgba(0, 0, 0, 0.08);
	display: flex;
	align-items: center;
	padding: 0 30rpx;
	
	.signup-btn {
		width: 100%;
		height: 80rpx;
		background: linear-gradient(135deg, #E8783B, #F4A460);
		border-radius: 40rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		box-shadow: 0 8rpx 16rpx rgba(200, 130, 60, 0.25);
		
		&.disabled {
			background: #ccc;
			box-shadow: none;
		}
		
		.btn-text {
			font-size: 32rpx;
			color: #ffffff;
			font-weight: 700;
		}
		
		&:active:not(.disabled) {
			transform: scale(0.98);
		}
	}
}
</style>
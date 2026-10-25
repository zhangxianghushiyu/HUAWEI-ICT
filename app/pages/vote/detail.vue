<template>
	<view class="vote-detail-container">
		<!-- 返回按钮 -->
		<view class="back-btn" @click="goBack">
			<text class="back-icon">‹</text>
		</view>

		<!-- 头部信息 -->
		<view class="header-section">
			<image :src="voteDetail.coverImage || '/static/default-vote.jpg'" mode="aspectFill" class="cover-img">
			</image>
			<view class="header-content">
				<text class="title">{{ voteDetail.title }}</text>
				<text class="desc" v-if="voteDetail.description">{{ voteDetail.description }}</text>
				<view class="meta-info">
					<text class="meta-item">👥 {{ voteDetail.totalVoters || 0 }}人参与</text>
					<text class="meta-item">📊 {{ voteDetail.totalVotes || 0 }}票</text>
					<text class="meta-item">{{ voteDetail.isMultiple ? '☑️ 多选' : '⭕ 单选' }}</text>
				</view>
				<view class="time-info">
					<text class="time-text">{{ formatTime(voteDetail.startTime) }} -
						{{ formatTime(voteDetail.endTime) }}</text>
				</view>
			</view>
		</view>

		<!-- 选项列表 -->
		<view class="options-section">
			<view class="section-title">投票选项</view>

			<view v-for="(option, index) in voteDetail.options" :key="option.id" class="option-item"
				:class="{ 'selected': selectedOptions.includes(option.id), 'disabled': hasVoted }"
				@click="toggleOption(option.id)">
				<!-- 选项图片 -->
				<view v-if="option.optionImage" class="option-image">
					<image :src="option.optionImage" mode="aspectFill" class="opt-img"></image>
				</view>

				<!-- 选项内容 -->
				<view class="option-content">
					<view class="option-header">
						<view class="option-left">
							<!-- 选择框 -->
							<view class="checkbox" :class="{ 'checked': selectedOptions.includes(option.id) }">
								<text v-if="selectedOptions.includes(option.id)">✓</text>
							</view>
							<text class="option-text">{{ filterHtml(option.optionContent) }}</text>
						</view>
						<text class="vote-count">{{ option.voteCount || 0 }}票</text>
					</view>

					<!-- 进度条和百分比 -->
					<view v-if="hasVoted" class="progress-section">
						<view class="progress-bar">
							<view class="progress-fill" :style="{ width: (option.percentage || 0) + '%' }"></view>
						</view>
						<text class="percentage">{{ option.percentage || 0 }}%</text>
					</view>
				</view>
			</view>
		</view>

		<!-- 底部操作栏 -->
		<view class="footer-action">
			<view v-if="!hasVoted" class="action-buttons">
				<button class="submit-btn" :disabled="selectedOptions.length === 0 || submitting" @click="submitVote">
					{{ submitting ? '提交中...' : '提交投票' }}
				</button>
			</view>
			<view v-else class="voted-actions">
				<button class="to-center-btn" @click="goToVoteCenter">前往投票中心</button>
			</view>
		</view>

		<!-- 提示弹窗 -->
		<uni-popup ref="tipPopup" type="dialog">
			<uni-popup-dialog mode="base" :title="tipTitle" :content="tipContent"
				@confirm="handleTipConfirm"></uni-popup-dialog>
		</uni-popup>
	</view>
</template>

<script>
import uniPopup from '@dcloudio/uni-ui/lib/uni-popup/uni-popup.vue'
import uniPopupDialog from '@dcloudio/uni-ui/lib/uni-popup-dialog/uni-popup-dialog.vue'
import api from '@/common/api/apiService.js'

export default {
  components: {
    uniPopup,
    uniPopupDialog
  },
  data() {
    return {
      activityId: null,
      voteDetail: {
        options: []
      },
      selectedOptions: [],
      hasVoted: false,
      submitting: false,
      tipTitle: '',
      tipContent: ''
    }
  },
  onLoad(options) {
    if (options.id) {
      this.activityId = options.id
      this.loadVoteDetail()
    }
  },
  methods: {
    filterHtml(str) {
      if (!str) return ''
      return str.replace(/<[^>]+>/g, '')
    },
    // 返回上一页（跳转到 tabbar 页面）
    goBack() {
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
    // 前往投票中心
    goToVoteCenter() {
      uni.redirectTo({
        url: '/pages/vote/list',
        fail: (err) => {
          console.error('跳转投票中心失败:', err)
          uni.showToast({
            title: '跳转失败，请重试',
            icon: 'none'
          })
        }
      })
    },
    // 加载投票详情
    async loadVoteDetail() {
      uni.showLoading({ title: '加载中...' })
      try {
        const res = await api.getVoteDetail({ activityId: this.activityId })
        if (res.code === 200) {
          this.voteDetail = res.data
          this.hasVoted = res.data.hasVoted || false
          this.selectedOptions = res.data.userVotedOptionIds || []
        } else {
          uni.showToast({ title: res.msg || '加载失败', icon: 'none' })
        }
      } catch (error) {
        console.error('加载投票详情失败:', error)
        uni.showToast({ title: '网络错误', icon: 'none' })
      } finally {
        uni.hideLoading()
      }
    },
    // 切换选项
    toggleOption(optionId) {
      if (this.hasVoted) {
        uni.showToast({ title: '您已经投过票了', icon: 'none' })
        return
      }
      const index = this.selectedOptions.indexOf(optionId)
      if (index > -1) {
        this.selectedOptions.splice(index, 1)
      } else {
        if (this.voteDetail.isMultiple && this.voteDetail.maxOptions) {
          if (this.selectedOptions.length >= this.voteDetail.maxOptions) {
            uni.showToast({ title: `最多只能选择${this.voteDetail.maxOptions}个选项`, icon: 'none' })
            return
          }
        } else if (!this.voteDetail.isMultiple) {
          this.selectedOptions = [optionId]
          return
        }
        this.selectedOptions.push(optionId)
      }
    },
    // 提交投票
    async submitVote() {
      if (this.selectedOptions.length === 0) {
        uni.showToast({ title: '请至少选择一个选项', icon: 'none' })
        return
      }
      this.submitting = true
      try {
        let res
        if (this.voteDetail.isMultiple) {
          res = await api.voteMultiple(this.activityId, this.selectedOptions)
        } else {
          res = await api.vote({ activityId: this.activityId, optionId: this.selectedOptions[0] })
        }
        if (res.code === 200) {
          uni.showToast({ title: '投票成功', icon: 'success' })
          setTimeout(() => { this.loadVoteDetail() }, 1000)
        } else {
          uni.showToast({ title: res.msg || '投票失败', icon: 'none' })
        }
      } catch (error) {
        console.error('投票失败:', error)
        uni.showToast({ title: '网络错误', icon: 'none' })
      } finally {
        this.submitting = false
      }
    },
    // 确认取消投票（保留，虽然当前UI没有调用）
    confirmCancel() {
      this.tipTitle = '提示'
      this.tipContent = '确定要取消投票吗？'
      this.$refs.tipPopup.open()
    },
    // 处理弹窗确认（保留）
    async handleTipConfirm() {
      try {
        let res
        if (this.voteDetail.isMultiple) {
          res = await api.cancelVoteMultiple({ activityId: this.activityId })
        } else {
          res = await api.cancelVote({ activityId: this.activityId })
        }
        if (res.code === 200) {
          uni.showToast({ title: '取消成功', icon: 'success' })
          setTimeout(() => { this.loadVoteDetail() }, 1000)
        } else {
          uni.showToast({ title: res.msg || '取消失败', icon: 'none' })
        }
      } catch (error) {
        console.error('取消投票失败:', error)
        uni.showToast({ title: '网络错误', icon: 'none' })
      }
    },
    // 格式化时间
    formatTime(timeStr) {
      if (!timeStr) return ''
      const date = new Date(timeStr)
      const year = date.getFullYear()
      const month = date.getMonth() + 1
      const day = date.getDate()
      return `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`
    }
  }
}
</script>

<style lang="scss" scoped>
.vote-detail-container {
  min-height: 100vh;
  background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
  padding-bottom: 120rpx;
  position: relative;
}

.back-btn {
  position: fixed;
  top: 40rpx;
  left: 30rpx;
  z-index: 999;
  width: 80rpx;
  height: 80rpx;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(20rpx);
  -webkit-backdrop-filter: blur(20rpx);

  .back-icon {
    font-size: 50rpx;
    color: #5D3A1A;
    font-weight: 700;
    margin-top: -8rpx;
    margin-left: -4rpx;
  }

  &:active {
    transform: scale(0.95);
    opacity: 0.8;
  }
}

.header-section {
  position: relative;
  z-index: 1;

  .cover-img {
    width: 100%;
    height: 400rpx;
  }

  .header-content {
    padding: 30rpx;
    background: #ffffff;
    margin-top: -40rpx;
    border-radius: 30rpx 30rpx 0 0;
    position: relative;

    .title {
      display: block;
      font-size: 40rpx;
      font-weight: 700;
      color: #5D3A1A;
      margin-bottom: 16rpx;
      line-height: 1.4;
    }

    .desc {
      display: block;
      font-size: 28rpx;
      color: #8B6B4A;
      margin-bottom: 20rpx;
      line-height: 1.6;
    }

    .meta-info {
      display: flex;
      gap: 30rpx;
      margin-bottom: 16rpx;

      .meta-item {
        font-size: 26rpx;
        color: #999;
      }
    }

    .time-info .time-text {
      font-size: 24rpx;
      color: #999;
    }
  }
}

.options-section {
  padding: 30rpx;
  background: #ffffff;
  margin-top: 20rpx;

  .section-title {
    font-size: 32rpx;
    font-weight: 700;
    color: #5D3A1A;
    margin-bottom: 24rpx;
  }
}

.option-item {
  background: #f8f9fa;
  border-radius: 16rpx;
  margin-bottom: 20rpx;
  overflow: hidden;
  transition: all 0.3s;

  &.selected {
    background: linear-gradient(135deg, rgba(232, 120, 59, 0.1) 0%, rgba(244, 164, 96, 0.1) 100%);
    border: 2rpx solid #E8783B;
  }

  &.disabled {
    opacity: 0.7;
  }

  &:active:not(.disabled) {
    transform: scale(0.98);
  }
}

.option-image {
  width: 100%;
  height: 300rpx;

  .opt-img {
    width: 100%;
    height: 100%;
  }
}

.option-content {
  padding: 24rpx;
}

.option-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16rpx;

  .option-left {
    display: flex;
    align-items: center;
    flex: 1;

    .checkbox {
      width: 44rpx;
      height: 44rpx;
      border: 2rpx solid #ddd;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 16rpx;
      transition: all 0.3s;

      &.checked {
        background: linear-gradient(135deg, #E8783B, #F4A460);
        border-color: #E8783B;

        text {
          color: #ffffff;
          font-size: 28rpx;
        }
      }
    }

    .option-text {
      font-size: 30rpx;
      color: #5D3A1A;
      flex: 1;
    }
  }

  .vote-count {
    font-size: 26rpx;
    color: #999;
  }
}

.progress-section {
  display: flex;
  align-items: center;
  gap: 16rpx;

  .progress-bar {
    flex: 1;
    height: 12rpx;
    background: #e0e0e0;
    border-radius: 6rpx;
    overflow: hidden;

    .progress-fill {
      height: 100%;
      background: linear-gradient(90deg, #E8783B, #F4A460);
      border-radius: 6rpx;
      transition: width 0.5s ease;
    }
  }

  .percentage {
    font-size: 26rpx;
    color: #E8783B;
    font-weight: 700;
    min-width: 80rpx;
    text-align: right;
  }
}

.footer-action {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: #ffffff;
  padding: 20rpx 30rpx;
  box-shadow: 0 -4rpx 12rpx rgba(0, 0, 0, 0.08);

  .action-buttons .submit-btn {
    width: 100%;
    height: 88rpx;
    line-height: 88rpx;
    background: linear-gradient(135deg, #E8783B, #F4A460);
    color: #ffffff;
    font-size: 32rpx;
    font-weight: 700;
    border-radius: 44rpx;
    border: none;

    &[disabled] {
      opacity: 0.5;
    }
  }

  .voted-actions .to-center-btn {
    width: 100%;
    height: 88rpx;
    line-height: 88rpx;
    background: linear-gradient(135deg, #E8783B, #F4A460);
    color: #ffffff;
    font-size: 32rpx;
    font-weight: 700;
    border-radius: 44rpx;
    border: none;
  }
}
</style>
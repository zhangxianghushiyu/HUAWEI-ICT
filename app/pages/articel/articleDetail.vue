<template>
	<view class="content">
		<view class="content-html" v-html="htmlContent">

		</view>
		<view class="content-menu">
			<view @tap="addLike()" class="content-menu-item" style="margin-left: 40rpx;">
				<image src="/static/new/21.png" mode=""></image>
				<span>{{articleDetail.likeSum}}</span>
			</view>
			<view @tap="openPl()" class="content-menu-item" style="margin-left: 20rpx;">
				<image src="/static/new/22.png" mode=""></image>
				<span>写评论</span>
			</view>
		</view>
		<view class="commentList">
			<scroll-view style="height: 100%;" scroll-y="true">
				<view v-for="item in commentList" class="commentList-item">
					<view style="display: flex;align-items: center;">
						<image :src="item.userAvatar" mode=""></image>
						<span>{{item.userName}}</span>
					</view>
					<span style="margin-left: 20rpx;margin-top: 10rpx;">{{item.content}}</span>
				</view>
				<view style="width: 100%;height: 40rpx;">
					
				</view>
			</scroll-view>
		</view>
		<view v-if="showPopup" class="popup-mask" @tap="onMaskClick" @touchmove.stop.prevent>
			<view class="popup-container" @tap.stop>
				<!-- 弹窗头部：标题 + 关闭按钮 -->
				<view class="popup-header">
					<text class="popup-title">输入内容</text>
					<view class="popup-close" @tap="closePopup">
						<text class="close-icon">×</text>
					</view>
				</view>

				<!-- 弹窗主体：文本输入框 -->
				<view class="popup-body">
					<input class="popup-input" type="text" placeholder="请输入文本内容" v-model="inputValue"
						:focus="inputFocus" confirm-type="done" @confirm="onConfirm" />
				</view>

				<!-- 弹窗底部：确定按钮 -->
				<view class="popup-footer">
					<button class="confirm-btn" @tap="onConfirm">确 定</button>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				articleId: '',
				articleDetail: {},
				htmlContent: '',
				isClick: false,
				commentList: [],
				isShowPl: false,
				params: {
					pageNum: 1,
					pageSize: 10,
					articleId: ''
				},
				params2: {
					content: '',
					articleId: ''
				},
				showPopup: false, // 控制弹窗显隐
				inputValue: '', // 输入框绑定值
				resultText: '', // 显示确认后的结果
				inputFocus: false // 控制输入框自动获得焦点（提升体验）
			};
		},
		onShow() {
			this.articleId = uni.getStorageSync('articleId')
			this.params.articleId = uni.getStorageSync('articleId')
			this.params2.articleId = uni.getStorageSync('articleId')
			this.getDetail()
			this.getArticleCommentPage()
		},
		onLoad() {

		},
		methods: {
			openPl() {
				this.showPopup = true;
				this.inputValue = '';
				this.$nextTick(() => {
					this.inputFocus = true;
				});
				uni.hideKeyboard();
			},
			closePopup() {
				this.showPopup = false;
				this.inputFocus = false;
				uni.hideKeyboard();
			},
			onMaskClick() {
				this.closePopup();
			},
			onConfirm() {
				// 隐藏键盘，提升操作完成后的体验
				uni.hideKeyboard();

				// 非空校验（根据需求可调整）
				const content = this.inputValue.trim();
				if (!content) {
					uni.showToast({
						title: '请输入内容',
						icon: 'none',
						duration: 1500
					});
					return;
				}

				// 保存用户输入的内容
				this.resultText = content;
				this.params2.content = content
				// 确定后关闭弹窗
				this.closePopup();
				this.$api.addArticleComment(this.params2).then(res => {
					this.getArticleCommentPage()
				})
			},
			//获取评论列表
			getArticleCommentPage() {
				this.$api.getArticleCommentPage(this.params).then(res => {
					console.log(res);
					this.commentList = res.data.list
					// for (let i = 0; i < 50; i++) {
					// 	this.commentList.push(res.data.list[0])
					// }
				})
			},
			//获取文章详细
			getDetail() {
				this.$api.getArticleInfo({
					id: this.articleId
				}).then(res => {
					this.articleDetail = res.data
					this.htmlContent = this.processRichText(res.data.articleContent)
				})
			},
			addLike() {
				if (this.isClick) {
					uni.showToast({
						title: '您已经点赞',
						icon: 'none'
					})
					return
				}
				this.$api.addLikeArticle({
					id: this.articleDetail.id
				}).then(res => {
					this.isClick = true
					uni.showToast({
						title: '点赞成功',
						icon: 'none'
					})
					setTimeout(() => {
						this.getDetail()
					}, 500)
				})
			},
			processRichText(html) {
				if (!html) return ''
				// 1. 处理图片
				let processed = html
					// 移除 width/height 属性
					.replace(/(width|height)\s*=\s*["'][^"']*["']/gi, '')
					// 移除内联样式中的宽高设置
					.replace(/style\s*=\s*["'][^"']*["']/gi, (match) => {
						return match
							.replace(/(width|height)\s*:\s*[^;]+;?/gi, '')
							.replace(/(max-width|max-height)\s*:\s*[^;]+;?/gi, '')
					})
					// 为所有 img 添加自适应样式
					.replace(/<img/gi,
						'<img style="max-width:100% !important;height:auto !important;display:block !important;"')
				// 2. 处理表格
				processed = processed.replace(/<table/gi,
					'<table style="max-width:100% !important;table-layout:fixed !important;"')
				// 3. 处理其他可能的大尺寸元素
				processed = processed.replace(/<div/gi, '<div style="max-width:100% !important;"')
				return processed
			},
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		position: fixed;
		left: 0;
		top: 0;
		right: 0;
		bottom: 0;
		display: flex;
		flex-direction: column;
		background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);

		.content-html {
			width: 100%;
			margin-top: 100rpx;
		}

		.content-menu {
			display: flex;
			align-items: center;
			position: relative;
			margin-top: 40rpx;

			.content-menu-item {
				display: flex;
				flex-direction: column;
				align-items: center;

				image {
					width: 40rpx;
					height: 40rpx;
				}
			}
		}

		.commentList {
			flex: 1;
			/* 占据剩余所有空间 */
			overflow: hidden;
			margin-top: 20rpx;
			margin-left: 20rpx;
			margin-right: 20rpx;

			.commentList-item {
				display: flex;
				flex-direction: column;
				margin-top: 40rpx;

				span {
					color: #8B6B4A;
				}

				image {
					width: 80rpx;
					height: 80rpx;
					border-radius: 50%;
				}
			}
		}
	}

	.popup-mask {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0, 0, 0, 0.5);
		display: flex;
		align-items: center;
		justify-content: center;
		z-index: 1000;
		/* 平滑过渡 */
		animation: fadeIn 0.2s ease-out;
	}

	/* 弹窗卡片容器 */
	.popup-container {
		width: 600rpx;
		max-width: 85%;
		background-color: #ffffff;
		border-radius: 36rpx;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
		overflow: hidden;
		animation: scaleIn 0.2s ease-out;
	}

	/* 弹窗头部 */
	.popup-header {
		position: relative;
		padding: 32rpx 32rpx 20rpx 32rpx;
		text-align: center;
		border-bottom: 1rpx solid #f0f0f0;
	}

	.popup-title {
		font-size: 36rpx;
		font-weight: 600;
		color: #5D3A1A;
		letter-spacing: 1rpx;
	}

	/* 关闭按钮 (右上角) */
	.popup-close {
		position: absolute;
		right: 24rpx;
		top: 24rpx;
		width: 56rpx;
		height: 56rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		border-radius: 50%;
		background-color: #FFF0E5;
		transition: background-color 0.2s;
	}

	.popup-close:active {
		background-color: #e9ecef;
	}

	.close-icon {
		font-size: 48rpx;
		font-weight: 300;
		color: #E8783B;
		line-height: 1;
		margin-top: -4rpx;
	}

	/* 弹窗主体 (输入框区域) */
	.popup-body {
		padding: 48rpx 40rpx 32rpx 40rpx;
	}

	.popup-input {
		width: 100%;
		height: 88rpx;
		background-color: #FFFDF9;
		border-radius: 16rpx;
		padding: 0 24rpx;
		font-size: 32rpx;
		color: #1f2e3d;
		border: 1rpx solid rgba(200, 130, 60, 0.15);
		transition: all 0.2s;
		box-sizing: border-box;
	}

	.popup-input:focus {
		border-color: #E8783B;
		background-color: #ffffff;
		box-shadow: 0 0 0 2rpx rgba(232, 120, 59, 0.15);
	}

	/* 弹窗底部 (确定按钮) */
	.popup-footer {
		padding: 0 40rpx 48rpx 40rpx;
	}

	.confirm-btn {
		width: 100%;
		background: linear-gradient(135deg, #E8783B, #F4A460);
		color: #ffffff;
		border-radius: 60rpx;
		font-size: 32rpx;
		font-weight: 600;
		height: 88rpx;
		line-height: 60rpx;
		border: none;
		box-shadow: 0 4rpx 12rpx rgba(232, 120, 59, 0.3);
		transition: opacity 0.2s, transform 0.1s;
	}

	.confirm-btn:active {
		opacity: 0.85;
		transform: scale(0.98);
	}

	/* 简单动画 */
	@keyframes fadeIn {
		from {
			opacity: 0;
		}

		to {
			opacity: 1;
		}
	}

	@keyframes scaleIn {
		from {
			opacity: 0;
			transform: scale(0.95);
		}

		to {
			opacity: 1;
			transform: scale(1);
		}
	}
</style>
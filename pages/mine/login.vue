<template>
	<view class="content">
		<view class="title"></view>
		<view class="login-card">
			<view class="login-header">
				<text class="login-header-title">欢迎登录</text>
				<text class="login-header-sub">账号密码登录</text>
			</view>

			<view class="form-item">
				<text class="form-label">账号</text>
				<view class="form-input-wrap">
					<input
						v-model="phone"
						type="text"
						maxlength="20"
						placeholder="请输入您的账号"
						placeholder-class="input-placeholder"
						class="form-input"
					/>
				</view>
			</view>

			<view class="form-item">
				<text class="form-label">密码</text>
				<view class="form-input-wrap">
					<input
						v-model="passWord"
						type="password"
						placeholder="请输入密码"
						maxlength="20"
						placeholder-class="input-placeholder"
						class="form-input"
					/>
				</view>
			</view>

			<view @tap="toLogin2()" class="login-btn">
				<text>登 录</text>
			</view>

			<view class="login-actions">
				<text class="register-link">
					还没账号？<text @tap="toPage('/pages/mine/register-account')" class="link">去注册</text>
				</text>
			</view>

			<view class="agreement">
				<zb-tooltip ref="tooltip" content="请先同意勾选协议" placement="top">
					<image
						@click="changeSelect"
						v-if="!isSelect"
						src="@/static/images/cli-gray.png"
						mode="scaleToFill"
						class="agree-icon"
					/>
					<image
						@click="changeSelect"
						v-else
						src="@/static/images/change/cli-select.png"
						mode="scaleToFill"
						class="agree-icon"
					/>
				</zb-tooltip>
				<text class="agree-text">
					<text class="agree-tip">我已阅读并同意</text>
					<text @click="$utils.toPage('/pages/mine/policy?type=' + 1)" class="agree-link">《用户手册》</text>
					<text @click="$utils.toPage('/pages/mine/policy?type=' + 2)" class="agree-link">《隐私政策》</text>
				</text>
			</view>
		</view>

		<!-- 微信登录功能暂时关闭，后续再开通 -->
		<!--
		<view class="divider">
			<view class="divider-line"></view>
			<text class="divider-text">更多登录方式</text>
			<view class="divider-line"></view>
		</view>

		<view class="wx-login">
			<view class="wx-login-btn" @tap="wxLogin()">
				<image src="/static/images/wx2.png" mode="" class="wx-icon"></image>
			</view>
			<text class="wx-text">微信登录</text>
		</view>
		-->

	</view>
</template>

<script>
	export default {
		data() {
			return {
				isSelect: false,
				phone: '',
				passWord: ''
			};
		},
		onLoad(e) {
            // 微信登录功能暂时关闭，后续再开通
            // if (typeof window !== 'undefined') {
            //     window.wxLoginAndroidCallBack = this.wxLoginAndroidCallBack;
            // }
		},
		methods: {
			/*
			wxLoginAndroidCallBack(wxCode, deviceId) {
				if (wxCode) {
					this.$api
						.wxLogin({
							code: wxCode,
						})
						.then((res) => {
							uni.setStorageSync('Token', res.data.token);
							uni.setStorageSync('UserInfo', res.data);
							window.rh_android.loginSucess(res.data.token)
							uni.switchTab({
								url: '/pages/index/index'
							})
						})
				}
			},
			*/
			//账号密码登录
			toLogin2() {
				if (!this.isSelect) {
					return
				}
				if (!this.phone) {
					this.$utils.showTip('请输入正确的账号', '不正确')
					return
				}
				if (!this.passWord) {
					this.$utils.showTip('请输入正确的密码', '不正确')
					return
				}
				this.$api.accountLogin({
					userName: this.phone,
					password: this.passWord
				}).then(res => {
					uni.setStorageSync('Token', res.data.token)
					uni.setStorageSync('UserInfo', res.data)
					uni.switchTab({
						url: '/pages/mine/index'
					})
					if (window.rh_android) {
						window.rh_android.loginSucess(res.data.token)
					}
				})
			},
			changeSelect() {
				this.isSelect = !this.isSelect
				this.$refs.tooltip.changeShow(this.isSelect)
			},
			// 微信登录功能暂时关闭，后续再开通
			/*
			wxLogin() {
				if (this.isSelect) {
					rh_android.wxLogin();
				} else {
					uni.showToast({
						title: '请同意用户协议和隐私政策',
						icon: 'none',
						duration: 2000
					})
				}
			},
			*/
			//跳转页面
			toPage(url) {
				console.log(url);
				uni.navigateTo({
					url: url
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		width: 100vw;
		min-height: 100vh;
		display: flex;
		align-items: center;
		flex-direction: column;
		background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
		padding-bottom: 60rpx;

		.title {
			width: 404rpx;
			height: 150rpx;
			margin: 120rpx auto 0 auto;
			background: url('/static/new/19.png');
			background-size: cover;
		}

		/* ===== 登录卡片 ===== */
		.login-card {
			width: 680rpx;
			background: #FFFFFF;
			border-radius: 40rpx;
			padding: 50rpx 40rpx 40rpx 40rpx;
			margin-top: 80rpx;
			box-shadow: 0 8rpx 32rpx rgba(200, 130, 60, 0.1);
			display: flex;
			flex-direction: column;
		}

		.login-header {
			display: flex;
			flex-direction: column;
			margin-bottom: 40rpx;

			.login-header-title {
				font-size: 44rpx;
				font-weight: 600;
				color: #333333;
			}

			.login-header-sub {
				font-size: 26rpx;
				color: #999999;
				margin-top: 10rpx;
			}
		}

		/* ===== 表单 ===== */
		.form-item {
			display: flex;
			flex-direction: column;
			margin-bottom: 30rpx;

			.form-label {
				font-size: 28rpx;
				color: #666666;
				margin-bottom: 14rpx;
				margin-left: 10rpx;
			}

			.form-input-wrap {
				width: 100%;
				height: 96rpx;
				background: #F9F5F0;
				border-radius: 24rpx;
				display: flex;
				align-items: center;
				padding: 0 28rpx;
				box-sizing: border-box;
				border: 2rpx solid transparent;
				transition: border-color 0.2s;
			}

			.form-input-wrap:focus-within {
				border-color: #E8783B;
				background: #FFFFFF;
			}

			.form-input {
				flex: 1;
				font-size: 30rpx;
				color: #333333;
				height: 100%;
			}

			.input-placeholder {
				color: #BBBBBB;
				font-size: 28rpx;
			}
		}

		/* ===== 登录按钮 ===== */
		.login-btn {
			width: 100%;
			height: 96rpx;
			margin-top: 20rpx;
			background: linear-gradient(90deg, #E8783B, #F4A460);
			border-radius: 48rpx;
			display: flex;
			align-items: center;
			justify-content: center;
			box-shadow: 0 8rpx 20rpx rgba(232, 120, 59, 0.3);

			text {
				font-size: 32rpx;
				color: #FFFFFF;
				font-weight: 500;
				letter-spacing: 6rpx;
			}
		}

		.login-btn:active {
			opacity: 0.9;
			transform: scale(0.98);
		}

		/* ===== 注册链接 ===== */
		.login-actions {
			display: flex;
			justify-content: flex-end;
			margin-top: 28rpx;

			.register-link {
				font-size: 26rpx;
				color: #999999;
			}

			.link {
				color: #E8783B;
				font-weight: 500;
			}
		}

		/* ===== 协议勾选 ===== */
		.agreement {
			display: flex;
			align-items: center;
			margin-top: 36rpx;
			padding: 0 10rpx;

			.agree-icon {
				width: 28rpx;
				height: 28rpx;
				flex-shrink: 0;
				margin-right: 10rpx;
			}

			.agree-text {
				font-size: 24rpx;
				color: #666666;
			}

			.agree-tip {
				color: #999999;
			}

			.agree-link {
				color: #E8783B;
			}
		}

		/* ===== 分割线 ===== */
		.divider {
			width: 680rpx;
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-top: 50rpx;

			.divider-line {
				flex: 1;
				height: 1rpx;
				background: linear-gradient(90deg, transparent, #D4C4B5, transparent);
			}

			.divider-text {
				font-size: 24rpx;
				color: #B8977A;
				padding: 0 20rpx;
				white-space: nowrap;
			}
		}

		/* ===== 微信登录 ===== */
		.wx-login {
			display: flex;
			flex-direction: column;
			align-items: center;
			margin-top: 36rpx;

			.wx-login-btn {
				width: 100rpx;
				height: 100rpx;
				border-radius: 50%;
				background: #FFFFFF;
				display: flex;
				align-items: center;
				justify-content: center;
				box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.08);
			}

			.wx-icon {
				width: 56rpx;
				height: 56rpx;
			}

			.wx-text {
				font-size: 24rpx;
				color: #666666;
				margin-top: 14rpx;
			}
		}
	}

</style>

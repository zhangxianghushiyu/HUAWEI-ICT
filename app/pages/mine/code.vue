<template>
	<view class="">
		<!-- 占位置 -->
		<view class="" style="height: 80rpx;"></view>

		<!-- title -->
		<view class="title">输入验证码</view>
		<!-- tip -->
		<view class="tip">验证码已发送至 {{maskPhoneNumber}}</view>

		<!-- 占位置 -->
		<view class="" style="height: 144rpx;"></view>

		<view class="" style="display: flex;justify-content: center">
			<yi-code @onComplete="complete" :focus="false" :maxlength="4"></yi-code>
		</view>

		<!-- 占位置 -->
		<view class="" style="height: 52rpx;"></view>

		<!-- tip -->
		<view class="tip" @click="start">{{tips}}</view>
		<uv-code ref="code" @change="codeChange"></uv-code>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				phone: '',
				tips: ''
			}
		},
		onLoad(e) {
			this.phone = e.phone
			this.getCode()

		},
		computed: {
			maskPhoneNumber: function() {
				let str = this.phone
				if (typeof str !== 'string') {
					str = this.phone.toString()
				}
				return str.substring(0, 3) + '****' + str.substring(7, 11)
			}
		},
		methods: {
			//输入验证码
			complete(code) {
				this.$api.login({
					phone: this.phone,
					scene: 1,
					code
				}).then(res => {
					console.log(res.data.token, 123);
					if (window.rh_android) {
						window.rh_android.loginSucess(res.data.token)
					}


					uni.setStorageSync('Token', res.data.token)
					uni.setStorageSync('UserInfo', res.data)
					this.$refs.code.reset();

					if (res.data.isRegister == 1) {
						this.$utils.showModalAsync("检测到您是首次登录，密码已重置为123456", "提示").then(res => {
							this.$utils.toPage('/pages/index/index')
						})
						return
					}

					//支付开关
					// this.$api.switchManage({
					// 	type: 1
					// }).then(res => {
					// 	uni.setStorageSync('Pay_Switch', res.data.status == 1 ? true : false)

					// 	this.$utils.toPage('/pages/index/index')
					// })
				})
			},
			leftClick() {
				uni.navigateBack()
			},
			codeChange(text) {
				this.tips = text;
			},
			start() {
				this.getCode()
			},
			getCode() {
				this.$api.sendCode({
					phone: this.phone,
					scene: 1
				}).then(res => {
					console.log(res);
					this.$refs.code.start();
				}).catch(err => {
					console.log(err);
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.title {
		text-align: center;
		font-weight: 700;
		font-size: 50rpx;
		color: #5D3A1A;
		margin-bottom: 40rpx;
	}

	.tip {
		text-align: center;
		font-weight: 500;
		font-size: 24rpx;
		color: #8B6B4A;
	}
</style>
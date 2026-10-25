<template>
	<view>
		<!-- 内容区域 -->
		<view class="wrapper">
			<input type="password" class="wrapper-input" v-model="oldPassWord" placeholder="原密码" />
			<!-- 占位置 -->
			<view class="" style="height: 36rpx;"></view>
			<input type="password" class="wrapper-input" v-model="passWord" placeholder="设置密码" />
			<!-- 占位置 -->
			<view class="" style="height: 36rpx;"></view>
			<input type="password" class="wrapper-input" v-model="passWord2" placeholder="确认密码" />
			<!-- 占位置 -->
			<view class="" style="height: 36rpx;"></view>
			<!-- 提交按钮 -->
			<button hover-class="button-hover" type="theme" @click="submit" size="long">确认重置</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				oldPassWord: '',
				passWord: '',
				passWord2: '',
			}
		},
		onLoad() {

		},
		methods: {
			submit() {
				if (!this.oldPassWord) {
					this.$utils.showTip('请输入原密码')
					return
				}
				if (!this.passWord) {
					this.$utils.showTip('请输入密码')
					return
				}
				if (!this.passWord2) {
					this.$utils.showTip('请二次确认密码')
					return
				}
				if (this.passWord != this.passWord2) {
					this.$utils.showTip('确认密码有误，请重新输入')
					return
				}

				this.$api.modifyPassword({
					newPassword: this.passWord,
					oldPassword: this.oldPassWord
				}).then(res => {
					uni.clearStorageSync()
					this.$utils.showTipAsync('变更成功，请重新登录','提示').then(res=>{
						this.$utils.toPage('/pages/mine/login')
					})
				})
			},

		}
	}
</script>

<style lang="scss" scoped>
	.wrapper {
		padding: 40rpx 65rpx;
		box-sizing: border-box;

		&-input {
			width: 100%;
			height: 94rpx;
			background: #FFFDF9;
			border-radius: 47rpx;
			padding-left: 49rpx;
			font-size: 28rpx;
			color: #5D3A1A;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
		}


		&-text {
			position: fixed;
			bottom: 40rpx;
			left: 0;
			width: 100%;
			display: flex;
			align-items: center;
			justify-content: center;

			&-box {
				font-weight: 400;
				font-size: 24rpx;
				color: #8B6B4A;
				line-height: 48rpx;
				margin-left: 12rpx;
			}
		}
	}
</style>
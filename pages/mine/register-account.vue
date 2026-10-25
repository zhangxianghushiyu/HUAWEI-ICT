<template>
	<view>
		<!-- 内容区域 -->
		<view class="wrapper">
			<input class="wrapper-input" v-model="phone" placeholder="设置账号" />
			<!-- 占位置 -->
			<view class="" style="height: 36rpx;"></view>
			<input type="password" class="wrapper-input" v-model="passWord" placeholder="设置密码" />
			<!-- 占位置 -->
			<view class="" style="height: 36rpx;"></view>
			<input type="password" class="wrapper-input" v-model="passWord2" placeholder="确认密码" />
			<!-- 占位置 -->
			<view class="" style="height: 36rpx;"></view>
			<input class="wrapper-input" v-model="code" placeholder="邀请码(选填)" />
			<!-- 占位置 -->
			<view class="" style="height: 36rpx;"></view>
			<!-- 提交按钮 -->
			<button hover-class="button-hover" type="theme" @click="submit" size="long">确认</button>
			<!-- 占位置 -->
			<view class="" style="height: 44rpx;"></view>
			<view class="wrapper-text">
				<zb-tooltip ref="tooltip" content="请先同意勾选协议"  placement="bottom">

					<image @click="changeSelect" v-if="!isSelect" style="width: 30rpx;height: 30rpx;"
						src="@/static/images/cli-gray.png" mode="scaleToFill">
					</image>
					<image @click="changeSelect" v-else style="width:30rpx;height: 30rpx;"
						src="@/static/images/change/cli-select.png" mode="scaleToFill">
					</image>
				</zb-tooltip>
				<view class="wrapper-text-box">
					我已阅读并同意
					<text class="select" @click="$utils.toPage('/pages/mine/policy?type=' + 1)">用户手册</text>
					和
					<text class="select" @click="$utils.toPage('/pages/mine/policy?type=' + 2)">隐私政策</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				phone: '',
				passWord: '',
				passWord2: '',
				code: '',
				isSelect: false,
			}
		},
		onLoad() {

		},
		methods: {
			changeSelect() {
				this.isSelect = !this.isSelect
				this.$refs.tooltip.changeShow(this.isSelect)
			},
			submit() {
				if (!this.isSelect) {
					return
				}

				if (!this.phone) {
					this.$utils.showTip('请输入账号')
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

				this.$api.registerAccount({
					invitationCode: this.code,
					password: this.passWord,
					userName: this.phone
				}).then(res => {
					this.$utils.showTipAsync('注册成功，前往登录','提示').then(res=>{
						this.$utils.toPage('/pages/mine/login?phone=' + this.phone)
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

	.select {
		color: #E8783B;
	}
</style>
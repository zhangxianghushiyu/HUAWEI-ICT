<template>
	<view class="view">
		<view class="box">
			<view class="line border-b">
				<view>申请金额</view>
				<input type="number" v-model="money" placeholder="请输入申请金额" />
			</view>
			<view class="sub ">
				<view>可提现金额：{{userInfo.cash}}</view>
			</view>
		</view>
		<!-- 占位置 -->
		<view class="" style="height: 50rpx;"> </view>

		<button hover-class="button-hover" type="theme" @click="submit" size="long">确认提交</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				money: '',
				userInfo: ''
			}
		},
		onLoad() {
			this.userInfo = uni.getStorageSync('UserInfo')
		},
		methods: {
			submit() {
				if (!this.money) {
					this.$utils.showTip('请输入申请金额', '提示')
					return
				}
				if (this.money > this.userInfo.cash) {
					this.$utils.showTip('申请金额不能大于可提现金额', '提示')
					return
				}

				this.$api.withdrawalCommander({
					money:this.money
				}).then(res => {
					this.$utils.showTipAsync("申请成功", '成功').then(res => {
						uni.navigateBack()
					})
				})
			}
		}
	}
</script>

<style>
	page {
		background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
	}
</style>

<style lang="scss">
	.view {
		padding: 20rpx;

		.box {
			background-color: #FFFDF9;
			padding: 20rpx 40rpx;
			border-radius: 30rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.line {
				display: flex;
				align-items: center;
				justify-content: space-between;
				font-size: 30rpx;
				color: #5D3A1A;
				line-height: 3;

				input {
					flex: 1;
					text-align: right;
					margin-left: 20rpx;
					color: #8B6B4A;
				}
			}

			.sub {
				line-height: 2;
				color: #8B6B4A;
				text-align: right;
			}
		}
	}

	.border-b {
		border-bottom: 1px solid #FFEBD6;
	}
</style>
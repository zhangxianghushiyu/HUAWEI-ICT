<template>
	<view class="view">
		<view class="box">
			<view class="line border-b">
				<view>姓名</view>
				<input type="text" v-model="submitData.name" placeholder="请填写姓名" />
			</view>
			<view class="line border-b">
				<view>手机号</view>
				<input type="text" v-model="submitData.phone" placeholder="请填写手机号码" />
			</view>
			<view class="line border-b">
				<view>所在城市</view>
				<input type="text" v-model="submitData.city" placeholder="请填写所在城市" />
			</view>
			<view class="line ">
				<view>收货地址</view>
				<input type="text" v-model="submitData.address" placeholder="请填写收货地址" />
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
				submitData: {
					id: '',
					address: '',
					city: '',
					name: '',
					phone: ''
				}

			};
		},
		onLoad(e) {
			if (e.info) {
				this.submitData = JSON.parse(e.info)
			}
		},
		methods: {
			submit() {
				if (!this.submitData.name) {
					this.$utils.showTip("请输入姓名", '提示')
					return
				}

				if (!this.$utils.verifyPhone(this.submitData.phone)) {
					this.$utils.showTip("手机号格式不正确", '提示')
					return
				}

				if (!this.submitData.city) {
					this.$utils.showTip("请输入所在城市", '提示')
					return
				}

				if (!this.submitData.address) {
					this.$utils.showTip("请输入收货地址", '提示')
					return
				}
				if (this.submitData.id) {
					this.$api.editAddress(this.submitData).then(res => {
						this.$utils.showTipAsync("修改成功", '成功').then(res => {
							uni.navigateBack()
						})
					})
				} else {
					this.$api.addAddress(this.submitData).then(res => {
						this.$utils.showTipAsync("新增成功", '成功').then(res => {
							uni.navigateBack()
						})
					})
				}


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
				line-height: 4;

				input {
					flex: 1;
					margin-left: 20rpx;
					color: #8B6B4A;
				}
			}
		}
	}

	.border-b {
		border-bottom: 1px solid #FFEBD6;
	}
</style>
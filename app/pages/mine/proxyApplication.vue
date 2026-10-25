<template>
	<view class="view">
		<view class="box">
			<view class="line border-b">
				<view>公司名字</view>
				<input type="text" v-model="submitData.companyName" placeholder="请填写公司名称" />
			</view>
			<view class="line border-b">
				<view>手机号码</view>
				<input type="text" v-model="submitData.phone" placeholder="请填写手机号码" />
			</view>
			<view class="line border-b">
				<view>营业执照（{{businessLicense ? 1 :0}}/1）</view>
				<view class="img">
					<image v-if="businessLicense" class="img-image" :src="businessLicense" @click="upload"
						mode="scaleToFill"></image>
					<view v-else class="img-add" @click="upload(1)">
						<image src="@/static/images/up-img.png" mode="widthFix" style="width: 42rpx;height: 37rpx;" />
					</view>
				</view>
			</view>
			<view class="line ">
				<view>办公场地（{{officeSpace.length}}/2）</view>
				<view class="img">
					<image class="img-image" :src="item" v-for="(item,index) in officeSpace" :key="index"
						mode="scaleToFill" @click="delFn(index)">
					</image>
					<view class="img-add" v-if="officeSpace.length < 2" @click="upload(2)">
						<image src="@/static/images/up-img.png" mode="widthFix" style="width: 42rpx;height: 37rpx;" />
					</view>
				</view>
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
				businessLicense: '',
				officeSpace: [],
				submitData: {
					companyName: '',
					businessLicense: '',
					officePictures1: '',
					officePictures2: '',
					phone: ''
				}
			};
		},
		methods: {
			upload(type) {
				console.log(type);
				if (type == 2 && this.officeSpace.length >= 2) {
					this.$utils.showTip('办公场地最多上传2张', '错误')
					return
				}

				uni.chooseImage({
					count: 1,
					success: async (res) => {
						res.tempFilePaths.forEach(async it => {
							const fileUrl = await this.$utils.upLoadFile(it)

							if (fileUrl.code != 200) {
								this.$utils.showTip(fileUrl.msg, '上传失败')
								return
							}

							if (type == 1) this.businessLicense = fileUrl.data
							else this.officeSpace.push(fileUrl.data)
						})
					}

				})
			},
			delFn(index) {
				this.officeSpace.splice(index, 1)
			},
			submit() {
				if (!this.submitData.companyName) {
					this.$utils.showTip("公司名称不可为空", '提示')
					return
				}

				if (!this.$utils.verifyPhone(this.submitData.phone)) {
					this.$utils.showTip("手机号格式不正确", '提示')
					return
				}

				if (!this.businessLicense) {
					this.$utils.showTip("营业执照不可为空", '提示')
					return
				}

				if (this.officeSpace.length != 2) {
					this.$utils.showTip("办公场地最少且只能传两张", '提示')
					return
				}

				this.submitData.businessLicense = this.businessLicense
				this.submitData.officePictures1 = this.officeSpace[0]
				this.submitData.officePictures2 = this.officeSpace[1]

				this.$api.commanderApply(this.submitData).then(res => {
					this.$utils.showTipAsync("提交成功，请等待审核", '成功').then(res => {
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
				line-height: 4;

				input {
					flex: 1;
					text-align: right;
					margin-left: 20rpx;
					color: #8B6B4A;
				}
			}
		}
	}

	.border-b {
		border-bottom: 1px solid #FFEBD6;
	}

	.img {
		padding: 0 20rpx;

		display: flex;
		align-items: center;
		flex-wrap: wrap;

		&-image {
			margin: 10rpx;
			width: 100rpx;
			height: 100rpx;
		}

		&-add {
			margin: 10rpx;
			width: 100rpx;
			height: 100rpx;
			background: #FFFDF9;
			border-radius: 12rpx;
			display: flex;
			align-items: center;
			justify-content: center;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
		}
	}
</style>
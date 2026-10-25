<template>
		<view class="help">
			<uv-navbar placeholder title="帮助与反馈" :titleStyle="{fontWeight: 'bold'}" @leftClick="leftClick"></uv-navbar>
			<!-- 占位置 -->
			<view class="" style="height: 40rpx;"> </view>

			<!-- 内容区域 -->
			<view class="help-wrapper">
				<textarea class="help-wrapper-textarea" placeholder-style="color:#666666;font-size:24rpx"
					placeholder="请输入您要反馈的问题（5~500字以内）" v-model="question" />
				<!-- 占位置 -->
				<view class="" style="height: 34rpx;"> </view>

				<view class="help-wrapper-img">
					<view class="help-wrapper-img-top">
						请提供问题的截图或照片（选填）
					</view>
					<view class="help-wrapper-img-bottom">
						<image class="help-wrapper-img-bottom-image" v-for="(item,index) in fileList" :key="index"
							:src="item" mode="scaleToFill"></image>

						<view class="help-wrapper-img-bottom-add" @click="upload">
							<image src="@/static/images/up-img.png" mode="widthFix" style="width: 42rpx;height: 37rpx;" />
						</view>
					</view>
				</view>
				<!-- 占位置 -->
				<view class="" style="height: 34rpx;"> </view>

				<view class="help-wrapper-phone">
					<view class="">
						联系方式
					</view>

					<input class="help-wrapper-phone-inputt" v-model="phone" placeholder="请输入手机号" />
				</view>

				<!-- 占位置 -->
				<view class="" style="height: 50rpx;"> </view>

				<button hover-class="button-hover" type="theme" @click="submit" size="long">确认提交</button>

			</view>
		</view>
	</template>

	<script>
		export default {
			data() {
				return {
					phone: '',
					fileList: [],
					question: ''
				}
			},
			methods: {
				leftClick() {
					uni.navigateBack()
				},
				upload() {
					if (this.fileList.length >= 5) {
						this.$utils.showTip('最多上传五张', '错误')
						return
					}

					uni.chooseImage({
						count: 9,
						success: async (res) => {
							console.log(res);

							res.tempFilePaths.forEach(async it => {
								const fileUrl = await this.$utils.upLoadFile(it)

								if (fileUrl.code != 200) {
									this.$utils.showTip(fileUrl.msg, '上传失败')
									return
								}

								this.fileList.push(fileUrl.data)
							})


						}
					})
				},
				submit() {
					if (!this.question) {
						this.$utils.showTip('请输入反馈问题', '错误')
						return
					}

					if (!this.$utils.hitPhone(this.phone)) {
						this.showTip('手机号错误', '错误')
						return
					}

					this.$api.feedBack({
						content: this.question,
						imgUrls: this.fileList,
						phone: this.phone
					}).then(res => {
						this.$utils.showTipAsync('提交成功', '提示').then(res => {
							uni.navigateBack()
						})
					})
				},

			}
		}
	</script>

	<style lang="scss" scoped>
		.help {
			&-wrapper {
				padding: 0 24rpx;

				&-textarea {
					width: 100%;
					height: 284rpx;
					background-color: #FFFDF9;
					border-radius: 30rpx;
					padding: 24rpx;
					box-sizing: border-box;
					border: 1px solid rgba(200, 130, 60, 0.1);
					box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
					color: #5D3A1A;
				}

				&-img {
					width: 100%;
					background: #FFFDF9;
					border-radius: 30rpx;
					padding: 7rpx;
					box-sizing: border-box;
					border: 1px solid rgba(200, 130, 60, 0.1);
					box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

					&-top {
						padding: 21rpx 16rpx 29rpx;
						border-bottom: 1rpx solid rgba(200, 130, 60, 0.1);
						font-weight: 500;
						font-size: 24rpx;
						color: #8B6B4A;
					}

					&-bottom {
						padding: 0 20rpx;

						display: flex;
						align-items: center;
						flex-wrap: wrap;

						&-image {
							margin: 10rpx;
							width: 114rpx;
							height: 114rpx;
							border-radius: 12rpx;
						}

						&-add {
							margin: 10rpx;
							width: 114rpx;
							height: 114rpx;
							background: rgba(232, 120, 59, 0.08);
							border-radius: 30rpx;
							display: flex;
							align-items: center;
							justify-content: center;
							border: 1px dashed rgba(232, 120, 59, 0.25);
						}
					}
				}

				&-phone {
					width: 100%;
					height: 88rpx;
					background: #FFFDF9;
					border-radius: 30rpx;
					padding: 0 23rpx;
					box-sizing: border-box;
					display: flex;
					align-items: center;
					justify-content: space-between;
					font-weight: 500;
					font-size: 24rpx;
					color: #8B6B4A;
					border: 1px solid rgba(200, 130, 60, 0.1);
					box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

					&-inputt {
						text-align: right;
						color: #5D3A1A;
					}
				}

				&-btn {
					width: 100%;
					height: 107rpx;
					background: linear-gradient(135deg, #E8783B, #F4A460);
					border-radius: 54rpx;
					text-align: center;
					line-height: 107rpx;
					font-size: 32rpx;
					color: #fff;
					box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.15);
				}
			}
		}

		.btn-hover {
			opacity: 0.9;
		}
	</style>
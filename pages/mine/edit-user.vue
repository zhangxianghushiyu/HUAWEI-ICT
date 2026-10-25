<template>
		<view>
			<view class="line">
				<view class="left">头像</view>
				<view class="right" @click="editAvatar">
					<image class="img" :src="userInfo.avatar" mode="scaleToFill" />
					<image class="right-btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view>
			<view class="line">
				<view class="left">名字</view>
				<view class="right">
					<view class="text">
						<input type="text" v-model="userInfo.nickName" />
					</view>
					<image class="right-btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view>
			<view class="line">
				<view class="left">账号</view>
				<view class="right">
					<view class="text">
						{{userInfo.userName}}
					</view>
				</view>
			</view>
			<view class="bottom-box">
				<button hover-class="button-hover" type="theme" @click="submit" size="long">确认修改</button>
			</view>
		</view>
	</template>

	<script>
		export default {
			data() {
				return {
					userInfo: {},
					permissionNum: 1
				}
			},
			onLoad() {
				this.userInfo = uni.getStorageSync('UserInfo')
				window.checkPermissionCallBack = this.checkPermissionCallBack;
			},
			methods: {
				toPage(url) {
					this.$utils.toPage(url)
				},
				checkPermissionCallBack(num) {
					this.permissionNum = num
				},
				//上传图片
				editAvatar() {
					if (this.permissionNum == 0) {
						uni.chooseImage({
							count: 1,
							success: async (res) => {
								const fileUrl = await this.$utils.upLoadFile(res.tempFilePaths[0])

								if (fileUrl.code != 200) {
									this.$utils.showTip(fileUrl.msg, '上传失败')
									return
								}

								this.userInfo.avatar = fileUrl.data
							}
						})
					} else {
						window.rh_android.checkPermission()
					}
				},
				submit() {
					this.$api.userModify({
						avatar: this.userInfo.avatar,
						nickName: this.userInfo.nickName
					}).then(res => {
						this.$utils.showTipAsync('设置成功', '提示').then(res => {
							uni.navigateBack()
						})
					})
				},


			}
		}
	</script>

	<style>
		page {
			background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
		}
	</style>

	<style lang="scss" scoped>
		.line {
			display: flex;
			align-items: center;
			justify-content: space-between;
			padding: 30rpx;
			background-color: #FFFDF9;
			border-bottom: 1px rgba(200, 130, 60, 0.1) solid;
			border-radius: 30rpx;
			margin: 16rpx 24rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.left {
				font-size: 34rpx;
				color: #5D3A1A;
				font-weight: 700;
			}

			.right {
				display: flex;
				margin-left: auto;
				align-items: center;

				.img {
					width: 100rpx;
					height: 100rpx;
					border-radius: 30rpx;
				}

				.text {
					font-size: 30rpx;
					text-align: right;
					color: #8B6B4A;
				}

				&-btn {
					width: 15rpx;
					margin-left: 10rpx;
				}
			}
		}

		.btn {
			margin-top: 20rpx;
			position: relative;
			z-index: 1;
			width: 100%;
			height: 110rpx;
			line-height: 110rpx;
			text-align: center;

			&-bg {
				position: absolute;
				left: 0;
				top: 0;
				width: inherit;
				height: inherit;
				z-index: -1
			}

			&-text {
				font-size: 35rpx;
				color: #fff;
				font-weight: bold;
			}
		}
	</style>
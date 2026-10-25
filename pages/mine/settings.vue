<template>
		<view>

			<view class="line" @click="updateVersion">
				<view class="left">检查更新</view>
				<view class="right">
					<view class="text">{{version.versionNo}}</view>
				</view>
			</view>
	<!-- 		<view class="line" @click="clearCache">
				<view class="left">清理缓存</view>
				<view class="right">
					<image class="btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view> -->
			<view class="line" @click="$utils.toPage('/pages/mine/about-us')">
				<view class="left">关于我们</view>
				<view class="right">
					<image class="btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view>
			<view class="line" @click="logOut">
				<view class="left">退出登录</view>
				<view class="right">
					<image class="btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view>
			<view class="line" @click="$utils.toPage('/pages/mine/edit-account')">
				<view class="left">变更密码</view>
				<view class="right">
					<image class="btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view>
			<view class="line" @click="$utils.toPage('/pages/mine/policy?type=' + 7)">
				<view class="left">注销账号</view>
				<view class="right">
					<image class="btn" src="@/static/images/Right.png" mode="widthFix"></image>
				</view>
			</view>
		</view>
	</template>

	<script>
		export default {
			data() {
				return {
					userInfo: {},
					version: '',
				}
			},
			onLoad() {
				this.userInfo = uni.getStorageSync('UserInfo')
				this.getVersion()
			},
			methods: {
				toPage(url) {
					this.$utils.toPage(url)
				},
				getVersion() {
					this.$api.getVersion().then(res => {
						this.version = res.data
					})
				},
				// 清理缓存
				clearCache() {
					// #ifndef MP-WEIXIN
					let that = this;
					let os = plus.os.name;
					if (os == 'Android') {
						let main = plus.android.runtimeMainActivity();
						let sdRoot = main.getCacheDir();
						let files = plus.android.invoke(sdRoot, "listFiles");
						let len = files.length;
						for (let i = 0; i < len; i++) {
							let filePath = '' + files[i]; // 没有找到合适的方法获取路径，这样写可以转成文件路径
							plus.io.resolveLocalFileSystemURL(filePath, function(entry) {
								if (entry.isDirectory) {
									entry.removeRecursively(function(entry) { //递归删除其下的所有文件及子目录
										uni.showToast({
											title: '缓存清理完成',
											duration: 2000
										});
										that.formatSize(); // 重新计算缓存
									}, function(e) {
										console.log(e.message)
									});
								} else {
									entry.remove();
								}
							}, function(e) {
								console.log('文件路径读取失败')
							});
						}
					} else { // ios
						plus.cache.clear(function() {
							uni.showToast({
								title: '缓存清理完成',
								duration: 2000
							});
							that.formatSize();
						});
					}
					// #endif

					// #ifdef MP-WEIXIN
					uni.showLoading({
						title: '清理中'
					})
					setTimeout(() => {
						uni.hideLoading()
						this.$utils.showTip('清理成功')
					}, 2000)
					// #endif
				},
				//退出登录
				logOut() {
					uni.clearStorage();
					this.$utils.toPage('/pages/mine/index')
				},
				// 检查版本更新
				updateVersion() {
					this.$utils.showTip('已是最新版本', '检查更新');
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
				align-items: center;

				.img {
					width: 100rpx;
					height: 100rpx;
					border-radius: 30rpx;
				}

				.text {
					font-size: 32rpx;
					color: #8B6B4A;
					text-align: right;
				}

				.btn {
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
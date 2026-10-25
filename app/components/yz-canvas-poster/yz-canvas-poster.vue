<template>
	<view class="content" v-show="ifShow" @click="changeIfShow">

		<!-- 长按 @longpress="canvasGet" -->
		<canvas class="canvas-img" :style="{'width':canvasWidth+'px','height':canvasHeight+'px'}"
			canvas-id="firstCanvas" id="firstCanvas"></canvas>

		<view class="bottom">
			<image class="top-text" src="/static/images/shareTopText.png" mode="widthFix"></image>

			<view class="btn">
				<image @click="shareFriends" class="btn-item" src="/static/images/shareWeChatFriends.png"
					mode="widthFix"></image>
				<image @click="shareMoments" class="btn-item" src="/static/images/shareMoments.png" mode="widthFix">
				</image>
				<image @click="canvasGet" class="btn-item" src="/static/images/savePicture.png" mode="widthFix"></image>
			</view>

		</view>
	</view>
</template>

<script>
	import QRCode from 'qrcode';
	import {
		register
	} from '@/common/api/config.js'
	export default {
		name: 'yz-canvas-poster',
		data() {
			return {
				canvasPath: '',
				context: null,
				windowWidth: 0, //屏幕可用宽度
				windowHeight: 0, //屏幕可用高度
				canvasWidth: 0, //图片宽度
				canvasHeight: 0, //图片高度
				qrCodeUrl: ''
			}
		},
		props: {
			ifShow: {
				type: Boolean,
				default: false
			},
			code: {
				type: String,
				default: ''
			},
			imageBg: {
				type: String,
				default: ''
			},
			myCanvasSize: {
				type: Array,
				default: () => { //props传递数据的时候，默认值如果是数组或者对象，应该使用一个函数返回默认值
					return [0.8, 16 / 9] //[0]为画布占屏幕宽度的比例，[1]为背景画布长宽比
				}
			},
			// imageHead: {
			// 	type: Array,
			// 	default: () => { //props传递数据的时候，默认值如果是数组或者对象，应该使用一个函数返回默认值
			// 		return []
			// 	}
			// },
			imageBody: {
				type: Array,
				default: () => { //props传递数据的时候，默认值如果是数组或者对象，应该使用一个函数返回默认值
					return []
				}
			},
			textHead: {
				type: Array,
				default: () => { //props传递数据的时候，默认值如果是数组或者对象，应该使用一个函数返回默认值
					return []
				}
			}

		},
		created() {
			const res = uni.getSystemInfoSync();
			// console.log('SystemInf', res);
			this.windowHeight = res.windowHeight;
			this.windowWidth = res.windowWidth;
			this.canvasWidth = this.windowWidth * this.myCanvasSize[0];
			this.canvasHeight = this.canvasWidth * this.myCanvasSize[1];


		},
		methods: {
			// 生成二维码
			async generateQRCode() {
				try {
					// 生成二维码并转换为Data URL
					// const url = await QRCode.toDataURL('https://hyt-polymer-h5-re.hongyingtao.asia/hyt.apk', {
					// 	width: 500, // 二维码宽度
					// 	margin: 0
					// });
					// const appUrl = uni.getStorageSync('Version').appUrl
					// const url = await QRCode.toDataURL(appUrl, {
					// 	width: 500, // 二维码宽度
					// 	margin: 0
					// });
					this.qrCodeUrl = '/static/download.png';
				} catch (err) {
					console.error('生成二维码失败:', err);
				}
			},
			async init() {
				// 生成二维码
				await this.generateQRCode();

				this.context = uni.createCanvasContext('firstCanvas', this);
				this.context.drawImage(this.imageBg, 0, 0, this.canvasWidth, this.canvasHeight)
				// this.context.drawImage(this.imageHead[0], this.imageHead[1], this.imageHead[2], this.imageHead[3], this
				// 	.imageHead[4])
				this.context.drawImage(this.qrCodeUrl, this.imageBody[1], this.imageBody[2], this.imageBody[3], this
					.imageBody[4])
				this.context.setFontSize(this.textHead[4])
				this.context.setTextBaseline('middle')
				this.context.setFillStyle(this.textHead[3])
				this.context.fillText(this.textHead[0], this.textHead[1], this.textHead[2])
				this.context.draw();
				console.log('绘制完成');
			},
			changeIfShow() {
				//触发一个更新事件
				this.$emit('update:ifShow', false);
				console.log('隐藏canvas');
			},
			canvasGet() {
				console.log('下载canvas')
				var that = this;
				uni.canvasToTempFilePath({
					canvasId: 'firstCanvas',
					success: function(res) {
						// 在H5平台下，tempFilePath 为 base64
						that.canvasPath = res.tempFilePath;
						that.$utils.showModalAsync("确定将图片保存到本地", "提示").then(res => {
							// uni.saveImageToPhotosAlbum({
							// 	filePath: this.canvasPath,
							// 	success: function() {
							// 		console.log('save success');
							// 		this.changeIfShow();
							// 		uni.showToast({
							// 			title: '保存成功',
							// 			duration: 2000
							// 		});
							// 	}
							// });

							const path = that.canvasPath.split(',')[1]
							console.log(path);
							window.rh_android.guardarImagen(path);
						})
					},
					fail: () => {
						console.log('下载失败')
					},
				}, that)

			},
			shareFriends() {
				var that = this;
				uni.canvasToTempFilePath({
					canvasId: 'firstCanvas',
					success: function(res) {
						// uni.share({
						// 	provider: 'weixin',
						// 	scene: 'WXSceneSession',
						// 	type: 2,
						// 	imageUrl: res.tempFilePath,
						// 	success: function(res) {
						// 		console.log('success:' + JSON.stringify(res));
						// 	},
						// 	fail: function(err) {
						// 		console.log('fail:' + JSON.stringify(err));
						// 	}
						// });
						const path = res.tempFilePath.split(',')[1]
						console.log(path);
						window.rh_android.shareImgForFriends(path)
					},
					fail: () => {
						console.log('失败')
					},
				}, that)

			},
			shareMoments() {
				console.log('下载canvas')
				var that = this;
				uni.canvasToTempFilePath({
					canvasId: 'firstCanvas',
					success: function(res) {
						// uni.share({
						// 	provider: 'weixin',
						// 	scene: 'WXSenceTimeline',
						// 	type: 2,
						// 	imageUrl: res.tempFilePath,
						// 	success: function(res) {
						// 		console.log('success:' + JSON.stringify(res));
						// 	},
						// 	fail: function(err) {
						// 		console.log('fail:' + JSON.stringify(err));
						// 	}
						// });
						const path = res.tempFilePath.split(',')[1]
						console.log(path);
						window.rh_android.shareImgForCircleFriends(path)
					},
					fail: () => {
						console.log('失败')
					},
				}, that)

			}


		}
	}
</script>

<style>
	.content {
		position: fixed;
		top: 0;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		background-color: rgba(0, 0, 0, 0.7);
		padding-top: 30rpx;
		width: 100vw;
		height: 100vh;
		z-index: 200;
		/* 设置显示层级 */
	}

	.bottom {
		width: 100vw;
		text-align: center;
	}

	.top-text {
		width: 300rpx;
		margin: 30rpx 0;
	}

	.btn {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 0 60rpx;

	}

	.btn-item {
		width: 100rpx;
	}
</style>
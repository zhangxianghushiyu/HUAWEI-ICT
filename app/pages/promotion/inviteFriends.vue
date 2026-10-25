<template>
	<view class="main">
		<view class="top"></view>
		<view class="myInviteCode">
			<span>我的邀请码：{{userInfo.invitationCode}}</span>
		</view>
		<view class="shareBtn" @tap="shareBtn()">
			<span>分享邀请码</span>
		</view>
		<view class="inviteRecord">
			<view class="inviteRecord-body">
				<scroll-view scroll-y="true" style="height: 100%;">
					<view class="inviteRecord-body-item" v-for="item in recordList">
						<span>{{item.nickName}}</span>
						<span>{{item.invitationTime}}</span>
					</view>
				</scroll-view>
			</view>
		</view>
		<view class="activityRules">
			<view class="activityRules-body">
				<span>1.点击上方按钮生成邀请海报，把海报分享给朋友。</span>
				<span>2.新用户扫码下载并进入APP后，看广告得收益，你和新用户都会获得金币，系统自动发放。</span>
				<span>3.如果新用户不在您的邀请成功记录中，请让新用户通过【我的】-【我的推广】进入本页面，下滑到页面底部，输入您的邀请码取奖励。</span>
				<span style="color:#FD4234">请注意：普通用户无分享收益，请先成为代理商或团长，分享后才能享受好友收益。</span>
			</view>
			<view class="activityRules-tips">
				<span>该活动最终解释权归平台所有</span>
			</view>
		</view>
		<view class="fillInCode">
			<view class="fillInCode-input">
				<input v-model="inviteCode" type="text" maxlength="20" placeholder="请填写邀请码" />
			</view>
			<view class="fillInCode-btn" @tap="bindCode()">
				<span>提交</span>
			</view>
		</view>
		<view class="back" @tap="back()">
			<uni-icons type="left" size="24"></uni-icons>
		</view>
		<yz-canvas-poster ref="mycanvas" :myCanvasSize="myCanvasSize" :ifShow.sync="ifShow" :imageBg="imageBg"
			:imageBody="imageBody" :textHead="textHead">
		</yz-canvas-poster>
	</view>
</template>

<script>
	import QRCode from 'qrcode';
	export default {
		data() {
			return {
				recordList: [],
				inviteCode: '',
				userInfo: uni.getStorageSync('UserInfo'),
				ifShow: false,
				myCanvasSize: [0.8, 16 / 9], //[0]为画布占屏幕宽度的比例，[1]为背景画布长宽比
				imageBg: '',
				imageBody: [],
				textHead: [],
				canvasHight: 0,
				canvasWidth: 0,
				downloadQR: '',
				isShowInvite: false
			};
		},
		onLoad() {
			this.getRecords()
			const version = uni.getStorageSync('Version')
			this.isShowInvite = this.userInfo.roleId == 4 ? true : false
			// if (version) {
			// 	this.downloadQR = version.url
			// 	console.log(this.downloadQR);
			// }
			const that = this
			const text = `https://hyt-polymer-h5-re.hongyingtao.asia/hyt.apk`
			QRCode.toDataURL(text, function(error, url) {
				if (error) {
					console.error(error);
				} else {
					that.downloadQR = url
				}
			});
		},
		methods: {
			back() {
				uni.navigateBack()
			},
			//获取邀请记录
			getRecords() {
				this.$api.getMyInvitationList().then(res => {
					this.recordList = res.data
				})
			},
			//分享
			shareBtn() {
				//获取画布的大小尺寸
				this.canvasHight = this.$refs.mycanvas.canvasHeight;
				this.canvasWidth = this.$refs.mycanvas.canvasWidth;

				// 如下，以canvasHight、canvasWidth为单位进行计算，达到动态布局以适应不同尺寸的手机
				this.imageBg = '/static/images/change/poster.png';
				this.imageBody = [this.downloadQR, this.canvasWidth * 0.5 - 75, this.canvasHight * 0.5 - 75, 150, 153];

				this.textHead = ['我的邀请码：' + this.userInfo.invitationCode, this.canvasWidth * 0.5 - 120, this.canvasHight *
					0.96, '#ffffff', '18'
				];

				// 显示画布
				this.ifShow = true;

				//绘制画布
				let tempTimeOut = setTimeout(() => {
					this.$refs.mycanvas.init();
					clearTimeout(tempTimeOut)
				}, 50);
			},
			//绑定邀请码
			bindCode() {
				if (this.inviteCode) {
					this.$api.setInvitationCode({
						invitationCode: this.inviteCode
					}).then(res => {
						this.inviteCode = ''
						uni.showToast({
							title: '绑定成功',
							icon: 'none'
						})
						setTimeout(() => {
							uni.navigateBack()
						}, 1000)
					})
				} else {
					uni.showToast({
						title: '请填写邀请码',
						icon: 'none'
					})
				}

			}
		}
	}
</script>

<style lang="scss" scoped>
	.main {
		width: 100vw;
		min-height: 100vh;
		display: flex;
		align-items: center;
		flex-direction: column;
		background: linear-gradient(180deg, #FDA18B 0%, #FEF3CF 47%);

		.top {
			width: 589rpx;
			height: 348rpx;
			margin-top: 100rpx;
			background: url('/static/promotion/31.png');
			background-size: cover;
		}

		.myInviteCode {
			width: 326rpx;
			height: 64rpx;
			font-size: 28rpx;
			color: #5D3A1A;
			text-align: center;
			line-height: 64rpx;
			background: linear-gradient(90deg, #F4A460 0%, rgba(244, 164, 96, 0.3) 100%);
			border-radius: 200rpx 200rpx 200rpx 200rpx;
		}

		.shareBtn {
			width: 356rpx;
			height: 126rpx;
			margin-top: 30rpx;
			text-align: center;
			font-size: 36rpx;
			color: #FFFFFF;
			line-height: 126rpx;
			background: linear-gradient(135deg, #E8783B, #F4A460);
			border-radius: 200rpx;
		}

		.inviteRecord {
			width: 690rpx;
			height: 488rpx;
			position: relative;
			margin-top: 60rpx;
			background: url('/static/promotion/32.png');
			background-size: cover;

			.inviteRecord-body {
				position: absolute;
				width: 90%;
				height: 55%;
				left: 5%;
				top: 40%;

				.inviteRecord-body-item {
					display: flex;
					margin-top: 28rpx;
					font-size: 26rpx;
					align-items: center;
					justify-content: space-between;
				}
			}
		}

		.activityRules {
			width: 690rpx;
			height: 658rpx;
			position: relative;
			margin-top: 20rpx;
			margin-bottom: 50rpx;
			background: url('/static/promotion/33.png');
			background-size: cover;

			.activityRules-body {
				position: absolute;
				width: 90%;
				height: 60%;
				left: 5%;
				top: 25%;
				font-size: 26rpx;

				span {
					margin-top: 20rpx;
				}

				display: flex;
				flex-direction: column;
			}

			.activityRules-tips {
				font-size: 26rpx;
				color: #999999;
				position: absolute;
				left: 50%;
				width: 400rpx;
				bottom: 15rpx;
				transform: translateX(-50%);
			}
		}

		.fillInCode {
			width: 690rpx;
			height: 444rpx;
			position: relative;
			margin: 20rpx auto 80rpx auto;
			background: url('/static/promotion/39.png');
			background-size: cover;

			.fillInCode-input {
				width: 620rpx;
				height: 100rpx;
				position: absolute;
				left: 35rpx;
				top: 180rpx;
				background: #FFF8F0;
				border-radius: 32rpx;
				box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
				display: flex;
				align-items: center;

				input {
					margin-left: 40rpx;
				}
			}

			.fillInCode-btn {
				width: 374rpx;
				height: 100rpx;
				position: absolute;
				left: 158rpx;
				bottom: 20rpx;
				font-size: 32rpx;
				color: #FFFFFF;
				text-align: center;
				line-height: 100rpx;
				background: linear-gradient(135deg, #E8783B, #F4A460);
				border-radius: 200rpx;
			}
		}

		.back {
			position: absolute;
			left: 40rpx;
			top: 40rpx;
		}
	}
</style>
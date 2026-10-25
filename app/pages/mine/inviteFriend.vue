<template>
		<view>
			<BaseHeader bgImageUrl="/static/images/change/yaoqinghaibao.png">
				<view class="head-title">邀请好友
					<view class="helder-back">
						<uni-icons color="#fff" type="left" size="25" @click="leftClick" />
					</view>
				</view>
			</BaseHeader>
			<view>
				<view class="bg-top" :style="{backgroundImage:`url(${topBg})`}">
					<view class="bg-top-code">
						<view class="bg-top-code-top">
							<text class="bg-top-code-top-info"
								@click="$utils.copyText(userInfo.invitationCode)">邀请码：{{userInfo.invitationCode}}</text>
							<image @click="canvasShow" class="bg-top-code-top-btn" src="@/static/images/fuzhiyaoqingma.png"
								mode="widthFix">
							</image>
						</view>
						<view class="bg-top-code-bottom">
							<input class="bg-top-code-bottom-input" placeholder="请输入邀请码" placeholder-style="color:#E8783B"
								v-model="text" @confirm="invite" />
						</view>
					</view>
					<view
						style="position: absolute;top:780rpx;left:60rpx;font-weight: bold;font-size: 28rpx;color: #E8783B;">
						<span>活动规则：</span>
					</view>
					<view
						style="position: absolute;top:820rpx;left:60rpx;right:10rpx; font-size: 28rpx;color: #5D3A1A;line-height: 51rpx;">
						<span>1.点击上方按钮生成邀请海报，把海报分享给朋友。
						</span>
					</view>
					<view
						style="position: absolute;top:870rpx;left:60rpx;right:50rpx;font-size: 28rpx;color: #5D3A1A;line-height: 35rpx;">
						<span>2.新用户扫码下载并进入APP后，你和新用户都会获得3天VIP，系统自动发放。</span>
					</view>
					<view
						style="position: absolute;top:950rpx;left:60rpx;right:50rpx;font-size: 28rpx;color: #5D3A1A;line-height: 35rpx;">
						<span> 3.如果新用户不在您的邀请成功记录中，请让新用户通过【我的】-【邀请好友】进入本页面，下滑到页面底部，输入您的邀请码取奖励。</span>
					</view>
				</view>

				<view class="bg-center" :style="{backgroundImage:`url(${centerBg})`}">
					<view v-html="policy" />
				</view>
				<view class="bg-bottom1" :style="{backgroundImage:`url(${bottomBg1})`}"></view>
				<view class="bg-bottom2" :style="{backgroundImage:`url(${bottomBg2})`}">
					<view class="noData" v-if="list.length == 0">
						暂时没有数据
					</view>
					<scroll-view v-else class="bg-bottom2-scroll" scroll-y="true">
						<view class="bg-bottom2-scroll-list">
							<view class="bg-bottom2-scroll-list-item" v-for="(item,index) in list" :key="index">
								<view class="date">{{item.nickName}}</view>
								<view class="name">{{item.invitationTime}}</view>
							</view>
						</view>
					</scroll-view>
				</view>
			</view>
			<yz-canvas-poster ref="mycanvas" :myCanvasSize="myCanvasSize" :ifShow.sync="ifShow" :imageBg="imageBg"
				:imageBody="imageBody" :textHead="textHead" :code="userInfo.invitationCode">
			</yz-canvas-poster>
		</view>
	</template>

	<script>
		import BaseHeader from '@/components/BaseHeader.vue'
		export default {
			components: {
				BaseHeader
			},
			data() {
				return {
					userInfo: {},
					text: '',
					list: [],
					policy: '',
					ifShow: false,
					myCanvasSize: [0.8, 16 / 9], //[0]为画布占屏幕宽度的比例，[1]为背景画布长宽比
					imageBg: '',
					imageBody: [],
					textHead: [],
					canvasHight: 0,
					canvasWidth: 0,
					topBg: '/static/images/change/yaoqinghaibao2.png',
					centerBg: '/static/images/change/yaoqinghaibao3.png',
					bottomBg1: '/static/images/change/yaoqinghaibao4.png',
					bottomBg2: '/static/images/change/yaoqinghaibao5.png',
					downloadQR: ''
				}
			},
			onLoad() {
				this.userInfo = uni.getStorageSync('UserInfo');
				this.getList()
				console.log(this.userInfo);
				this.getPolicy()

				const version = uni.getStorageSync('Version')

				if (version) {
					this.downloadQR = version.appUrl
				}
			},
			methods: {
				canvasShow() {
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
				leftClick() {
					uni.navigateBack()
				},
				invite(e) {
					this.$api.setInvitationCode({
						invitationCode: this.text
					}).then(res => {
						this.$utils.showTip('邀请成功', '成功')
					})
				},
				getList() {
					this.$api.getMyInvitationList().then(res => {
						this.list = res.data
					})
				},
				//获取规则
				getPolicy() {
					this.$api.getAgreement({
						type: 10
					}).then(res => {
						this.policy = res.data.content
					})
				}


			}
		}
	</script>

	<style lang="scss" scoped>
		.head-title {
			width: 100%;
			font-family: PingFang SC;
			font-weight: bold;
			font-size: 32rpx;
			color: #fff;
			text-align: center;
			position: relative;

			.helder-back {
				position: absolute;
				top: 0;
				left: 20rpx;
			}

		}

		.bg-top {
			width: 100%;
			background-size: 100% 100%;
			height: 800rpx;
			position: relative;

			&-code {
				position: absolute;
				bottom: 30rpx;
				left: 0rpx;
				width: 100%;
				padding: 0 80rpx;

				&-top {
					display: flex;
					align-items: center;
					margin-bottom: 10rpx;
					padding: 0 20rpx;

					&-info {
						flex: 1;
					}

					&-btn {
						width: 200rpx;
					}
				}

				&-bottom {
					background-color: $uni-sub-theme;
					padding: 10rpx 30rpx;
					border-radius: 30rpx;

					&-input {
						width: 100%;
						color: $uni-theme;
					}


				}
			}
		}

		.bg-center {
			width: 100%;
			background-size: 100% 100%;
			padding: 20rpx 80rpx;
			min-height: 300rpx;

		}

		.bg-bottom1 {
			width: 100%;
			background-size: 100% 100%;
			height: 120rpx;

		}

		.bg-bottom2 {
			width: 100%;
			background-size: 100% 100%;
			min-height: 500rpx;
			padding: 30rpx 60rpx;

			.noData {
				text-align: center;
				color: #8B6B4A;
			}

			&-scroll {
				height: 350rpx;

				&-list {
					width: 100%;

					&-item {
						display: flex;
						align-items: center;
						justify-content: space-between;

					}
				}
			}


		}
	</style>
<template>
		<view class="">
			<uv-navbar title="开通会员" :titleStyle="{fontWeight: 'bold'}" @leftClick="leftClick"></uv-navbar>
			<view class="vip-bg" :style="{backgroundImage:`url(${vipBg})`,height: 320 + topNum*2 +'rpx'}">
				<!-- 占位置 -->
				<!-- #ifdef MP-WEIXIN -->
				<view :style="{'height':`${topNum + contentNum}px`}"></view>
				<!-- #endif -->
				<!-- #ifndef MP-WEIXIN -->
				<view :style="{'height':`${topNum + 44}px`}"></view>
				<!-- #endif -->
				<!-- #ifdef MP-WEIXIN -->
				<!-- 占位置 -->
				<view class="" style="height: 40rpx;"></view>
				<!-- #endif -->
				<!-- #ifndef MP-WEIXIN -->
				<!-- 占位置 -->
				<view class="" style="height: 20rpx;"></view>
				<!-- #endif -->

				<view class="mine-me">
					<view class="mine-me-left">
						<image style="width: 120rpx;height: 120rpx;margin-right: 16rpx;"
							src="@/static/images/change/unLogin.png" mode="scaleToFill"></image>
						<view style="margin-right: auto;">
							<view style="font-weight: bold;font-size: 36rpx;color: #333333;margin-bottom: 12rpx;">
								{{userInfo.nickName}}
							</view>
							<view style="font-weight: 500;font-size: 24rpx;color: #666;">ID：{{userInfo.id}}</view>
						</view>
						<view @click="$utils.toPage('/pages/mine/recharge-record')" style="color:#7B6035;font-weight: 500;">
							消费记录
						</view>
					</view>
					<!-- <image class="mine-me-right" src="@/static/images/left-arr.png" mode="scaleToFill"></image> -->
				</view>
				<view v-if="userInfo.isVip" class="mine-me-vip">
					vip
				</view>
			</view>
			<!-- 选项卡 -->
			<view class="select-card">
				<scroll-view class="scroll-view_H1" scroll-x="true" scroll-left="0">
					<view class="scroll-view-item_H1" @click="selectIndex = index"
						:class="[selectIndex == index ? 'scroll-view-active':'']" v-for="(item,index) in list"
						:key="item.name">
						<!-- 占位置 -->
						<view style="height: 46rpx;"></view>
						<view style="font-size: 30rpx;font-weight: 500;text-align: center;">
							{{item.day}}天
						</view>
						<view
							style="display: flex;align-items: flex-end;justify-content: center;margin-top:38rpx;margin-bottom: 26rpx;">
							<view style="margin-right:2rpx;font-size: 32rpx;">￥</view>
							<view style="font-size: 60rpx;line-height: 1;">{{item.price}}</view>
						</view>
						<view style="font-size: 24rpx;text-align: center;text-decoration: line-through;color: #B09061;">
							￥{{item.oldPrice}}
						</view>
					</view>
				</scroll-view>
			</view>
			<!-- 权益 -->
			<image src="../../static/images/quanyi.png" style="width: 100%;height: 271rpx;display: block;"
				mode="scaleToFill">
			</image>
			<!-- line -->
			<view class="line"></view>
			<!-- 微信支付宝 -->
			<view class="pay-card">
				<view class="pay-card-item" @click="changeItem(item)" v-for="item in payList" :key="item.name">
					<view class="" style="display: flex;align-items: center;">
						<image :src="item.img" style="width: 38rpx;height: 38rpx;display: block;margin-right: 16rpx;"
							mode=""></image>
						<view class="" style="color: #fff;font-size: 30rpx;">{{item.name}}</view>
					</view>
					<image v-if="item.isCheck == true" src="/static/images/change/cli-select.png"
						style="width: 30rpx;height: 30rpx;display: block;" mode="scaleToFill"></image>
					<image v-else src="/static/images/cli-gray.png" style="width: 30rpx;height: 30rpx;display: block;"
						mode=""></image>
				</view>
			</view>
			<!-- 提交按钮 -->
			<button hover-class="button-hover" type="theme" @click="submit"
				size="long">{{userInfo.isVip?'续费会员':'确认开通'}}</button>
			<!-- 占位置 -->
			<view class="" style="height: 27rpx;"></view>
			<view class="wrapper-text">
				<image @click="changeSelect" v-if="!isSelect" style="width: 30rpx;height: 30rpx;"
					src="@/static/images/cli-gray.png" mode="scaleToFill">
				</image>
				<image @click="changeSelect" v-else style="width: 30rpx;height: 30rpx;"
					src="@/static/images/change/cli-select.png" mode="scaleToFill">
				</image>
				<view class="wrapper-text-box">
					开通前请阅读<text style="text-decoration: underline;"
						@click="$utils.toPage('/pages/mine/policy?type=' + 9)">《会员服务协议》</text>(含自动续费条款)
				</view>
			</view>
		</view>
	</template>

	<script>
		export default {
			data() {
				return {
					isSelect: false,
					vipBg: '/static/images/change/vip-bg.png',
					topNum: 0,
					contentNum: 0,
					selectIndex: 0,
					list: [],
					userInfo: {},

					payList: [{
							isCheck: true,
							name: '微信',
							img: '/static/images/weixin.png',
						},
						{
							isCheck: false,
							name: '支付宝',
							img: '/static/images/zhifubao.png',
						},
					],
					isSelect: false,
					modalText: ''
				}
			},
			onLoad() {
				let systemInfo = uni.getSystemInfoSync();
				this.topNum = systemInfo.statusBarHeight;
				// #ifdef MP-WEIXIN
				let menuButtonInfo = uni.getMenuButtonBoundingClientRect();
				this.contentNum = menuButtonInfo.height + (menuButtonInfo.top - this.topNum) * 2;
				// #endif

				this.getList()
				this.userInfo = uni.getStorageSync('UserInfo')

				window.alipayOpenMmbershipPaymentAndroidCallBack = this.alipayOpenMmbershipPaymentAndroidCallBack

			},
			methods: {
				leftClick() {
					uni.navigateBack()
				},
				changeItem(item) {
					this.payList.forEach(v => {
						v.isCheck = false
					})
					item.isCheck = true
				},
				changeSelect() {
					this.isSelect = !this.isSelect
				},
				getList() {
					this.$api.getVipList().then(res => {
						this.list = res.data
					})
				},
				submit() {
					if (!this.isSelect) {
						this.$utils.showModalAsync("是否阅读并同意会员服务协议", "提示").then(res => {
							this.isSelect = true
							this.openMemberShip()
						})
						return
					}
					this.openMemberShip()
				},
				//开通会员
				openMemberShip() {
					const that = this
					const typeItem = that.payList.filter(it => {
						return it.isCheck == true
					})[0]
					console.log(typeItem.name == "微信" ? 1 : 2, that.list[that.selectIndex].vipId);
					that.$api.createVipOrder({
						payType: typeItem.name == "微信" ? 1 : 2,
						vipId: that.list[that.selectIndex].vipId
					}).then(res => {
						if (typeItem.name == "微信") {
							window.window.rh_android.wxOpenMmbershipPayment(res.data.orderNo)
						}
						if (typeItem.name == '支付宝') {
							window.window.rh_android.alipayOpenMmbershipPayment(res.data.orderNo)
						}
					})
				},
				alipayOpenMmbershipPaymentAndroidCallBack(type, callback) {
					console.log(`调用返回 type=${type},callback=${callback}`);
					// 查询回调
					if (type == 1) {
						this.$api.getPayInfoByOrderNo({
							orderNo: callback
						}).then(e => {
							this.$utils.showTipAsync(`会员有效期： ${e.data.startTime}至${e.data.endTime}`, '激活成功').then(
								res => {
									uni.navigateBack()
								})
						})
					} else {
						this.$utils.showTip(callback, '提示')
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

	<style lang="scss" scoped>
		.vip-bg {
			width: 100%;

			background-size: 100% 100%;
		}

		 .uv-status-bar {
			background-color: transparent !important;
		}

		 .uv-navbar__content {
			background-color: transparent !important;
		}

		.mine-me {
			padding: 0 65rpx;
			box-sizing: border-box;
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-bottom: 4rpx;
		}

		.mine-me-left {
			width: 100%;
			display: flex;
			align-items: center;
		}

		.mine-me-right {
			width: 16rpx;
			height: 28rpx;
		}

		.mine-me-vip {
			width: 64rpx;
			height: 30rpx;
			background: linear-gradient(135deg, #E8783B, #F4A460);
			border-radius: 4rpx;
			font-weight: bold;
			font-size: 22rpx;
			color: #FFFFFF;
			text-align: center;
			line-height: 28rpx;
			margin-left: 195rpx;
		}

		.select-card {
			padding-top: 55rpx;
			padding-bottom: 29rpx;
			padding-left: 38rpx;
			border-top: 1rpx dashed rgba(200, 130, 60, 0.2);
			border-bottom: 1rpx dashed rgba(200, 130, 60, 0.2);
		}

		.line {
			border-bottom: 1rpx dashed rgba(200, 130, 60, 0.2);
		}

		.scroll-view_H1 {
			width: 100%;
			white-space: nowrap;
			// overflow: auto;
		}

		.scroll-view-item_H1 {
			display: inline-block;
			width: 204rpx;
			height: 286rpx;
			border-radius: 30rpx;
			border: 3px solid rgba(200, 130, 60, 0.15);
			margin-right: 18rpx;
			color: #5D3A1A;
			background-color: #FFFDF9;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
		}

		.scroll-view-active {
			display: inline-block;
			width: 204rpx;
			height: 286rpx;
			border-radius: 30rpx;
			border: 3rpx solid #E8783B;
			margin-right: 18rpx;
			color: #E8783B;
			background-color: rgba(232, 120, 59, 0.1);
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.1);
		}

		.pay-card {
			padding: 37rpx 30rpx 57rpx;
			box-sizing: border-box;
			display: flex;
			align-items: center;
			justify-content: space-between;

			&-item {
				width: 336rpx;
				height: 84rpx;
				background: rgba(232, 120, 59, 0.15);
				border-radius: 30rpx;
				border: 2rpx solid rgba(232, 120, 59, 0.2);
				display: flex;
				align-items: center;
				justify-content: space-between;
				padding: 23rpx 20rpx;
				box-sizing: border-box;
				box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
			}
		}


		.wrapper {
			&-text {
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
	</style>
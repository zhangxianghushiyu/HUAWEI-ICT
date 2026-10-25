<template>
	<view>
		<view class="balance">
			<view class="balance-title">现金余额(元)</view>
			<view class="balance-price">{{userInfo.cash}}</view>
			<view class="balance-sub-title">近一天已有5.5万+人成功提现!</view>
			<view class="detail">
				<text @click="toPage('/pages/benefit/withdrawal-detail')"> 账户明细</text>
			</view>
		</view>
		<view class="withdrawal">
			<view v-for="(item,index) in withdrawalData" :key="index" v-if="item.info.length>0">
				<view class="withdrawal-title">
					<image class="withdrawal-title-image" :src="item.icon" mode="widthFix" />
					<text class="withdrawal-title-text">{{item.title}}</text>
					<text class="withdrawal-title-tag">{{item.describe}}</text>
					<view v-if="item.bind == 0" @click="bindWx(index)" class="withdrawal-title-bind">
						<text>{{item.rightBtn}}</text>
						<image class="withdrawal-title-bind-img" src="@/static/images/Right.png" mode="widthFix">
						</image>
					</view>
				</view>
				<view class="withdrawal-info">
					<view class="withdrawal-info-box" v-for="(it,key) in item.info" :key="key"
						@click="activePrice(index,key)">
						<view class="withdrawal-info-box-item" :class="{active:index == wayIndex && key == priceIndex}">
							<view v-if="it.id != 0" class="withdrawal-info-box-item-price">
								<text>{{it.amount}}</text>
								<text class="withdrawal-info-box-item-price-unit">元</text>
							</view>
			<!-- 				<view v-else class="withdrawal-info-box-item-price2"
								style="font-size: 42rpx;padding-top: 0;">
								<text>全部提现</text>
							</view>
							<view v-show="it.autoAccount == 1" class="withdrawal-info-box-item-tag">无需审核</view> -->
							<!-- <view v-show="it.describe" class="withdrawal-info-box-item-describe">{{it.describe}}</view> -->
						</view>
					</view>
				</view>
			</view>
			<view class="withdrawal-tip">
				<view class="withdrawal-tip-title">温馨提示</view>
				<view class="withdrawal-tip-line">1.每日23:00至次日11:00为系统维护时间，提现可能延迟到账。</view>
				<view class="withdrawal-tip-line">2.2.无需审核立即到账，需要审核的48小时内到账。</view>
				<view class="withdrawal-tip-line">3.高峰期、系统升级期，用户可能存在短时间无法兑换的情况。平台将尽最大努力及时恢复兑换功能，但无需因此承担任何责任</view>
				<view class="withdrawal-tip-line">4.余额记录和提现记录，可在右上角账户明细中查看。</view>
				<view class="withdrawal-tip-line">5.如果您使用时有任何疑问，欢迎在个人中心-意见反馈内发起反馈。</view>
			</view>
		</view>
		<view v-if="priceIndex != -1" class="bottom-box" style="padding: 20rpx;">
			<button hover-class="button-hover" type="theme" @click="apply" size="long">立即提现</button>
		</view>
		<view class="bindZfbBox" v-if="isShowZfbBox">
			<view class="zfbBox">
				<view class="zfb-title">
					<span>绑定支付宝</span>
					<uni-icons @tap="isShowZfbBox=false" type="closeempty" size="22"></uni-icons>
				</view>
				<view class="zfbBox-item">
					<span>支付宝姓名</span>
					<view class="zfbBox-item-input">
						<input maxlength="15" v-model="zfbName" type="text" placeholder="请输入支付宝姓名" />
					</view>
				</view>
				<view class="zfbBox-item">
					<span>支付宝账号</span>
					<view class="zfbBox-item-input">
						<input maxlength="28" v-model="zfbUserName" type="text" placeholder="请输入支付宝账号" />
					</view>
				</view>
				<view class="zfbBox-btn" @tap="bindZfb()">
					<span>确定</span>
				</view>
			</view>
		</view>
		<!-- 绑定支付宝 -->
		<view class="bindZfb" v-if="isShowPop==0">
			<view class="bindZfb-box">
				<view class="bindZfb-box-item">
					<span>填写支付宝信息</span>
				</view>
				<view class="bindZfb-box-item2">
					<input v-model="zfbName" type="text" placeholder="请填写姓名" />
				</view>
				<view class="bindZfb-box-item2">
					<input v-model="zfbUserName" type="text" placeholder="填写支付宝账号" />
				</view>
				<view @tap="confirmBindZfb()" class="bindZfb-box-item3">
					<span>确定</span>
				</view>
				<!-- <image @tap="closeBindZfb()" class="bindZfb-close" src="/static/close.png" mode=""></image> -->
			</view>
		</view>
		<!-- 实名认证 -->
		<view class="realName" v-if="isShowPop==1">
			<view class="realName-box">
				<span style="font-size: 44rpx;color: #4A1D02;font-weight: bold;margin-top: 40rpx;">实名认证</span>
				<view class="realName-box-item" style="margin-top: 60rpx;">
					<input v-model="realName.name" type="text" placeholder="请填写姓名" />
				</view>
				<view class="realName-box-item">
					<input v-model="realName.idCard" type="text" placeholder="填写身份证号" />
				</view>
				<view class="realName-box-item">
					<input v-model="realName.phone" type="number" maxlength="11" placeholder="填写手机号" />
				</view>
				<view class="realName-box-scroll">
					<scroll-view scroll-y="true" style="height: 100%;">
						<view class="">
							<span style="">【提现实名认证提醒】</span>
						</view>
						<view class="">
							<span style="color: #999999;">
								根据《非银行支付机构网络支付业务管理办法》及反洗钱规定，每月提现金额超过500元，则提现功能需完成实名认证后方可使用。</span>
						</view>
					</scroll-view>
				</view>
				<view class="realName-box-btns">
					<image @tap="cancelRealName()" src="/static/10.png" mode=""></image>
					<image @tap="confirmRealName()" src="/static/11.png" mode=""></image>
				</view>
				<!-- <image @tap="cancelRealName()" class="realName-box-close" src="/static/close.png" mode=""></image> -->
			</view>
		</view>
		<!-- 校验结果 -->
		<view class="checkResult" v-if="isShowPop==2">
			<view class="checkResult-box" v-if="false">
				<span style="font-size: 44rpx;color: #4A1D02;">校验成功</span>
				<image src="/static/new/19.png" mode=""></image>
				<span style="font-size: 28rpx;color: #926139;">核验成功等待打款</span>
				<!-- <image @tap="isShowPop=-1" class="checkResult-box-close" src="/static/close.png" mode=""></image> -->
			</view>
			<view class="checkResult-box" v-else>
				<span style="font-size: 44rpx;color: #4A1D02;">校验失败</span>
				<image src="/static/new/20.png" mode=""></image>
				<span style="font-size: 28rpx;color: #926139;">请仔细核对基本信息</span>
				<!-- <image @tap="isShowPop=-1" class="checkResult-box-close" src="/static/close.png" mode=""></image> -->
			</view>
		</view>
	</view>
</template>

<script>
	import {
		alipayAppId
	} from '@/common/api/config.js'
	export default {

		data() {
			return {
				isShowZfbBox: false,
				zfbName: '',
				isShowPop: -1,
				realName: {
					name: '',
					idCard: '',
					phone: ''
				},
				zfbUserName: '',
				userInfo: {},
				wayIndex: -1,
				priceIndex: -1,
				withdrawalData: [{
						icon: '/static/images/WeChat.png',
						title: '微信提现',
						describe: '提现秒到账',
						rightBtn: '绑定微信账号',
						bind: 0,
						info: []
					},
					{
						icon: '/static/images/Alipay.png',
						title: '支付宝提现',
						describe: '提现秒到账',
						rightBtn: '绑定支付宝账号',
						bind: 0,
						info: [],
					},
				]

			}
		},
		onLoad() {
			this.getUserInfo()
			this.isBindaccount()
			this.zfbName = uni.getStorageSync('UserInfo').zfbName
			this.zfbUserName = uni.getStorageSync('UserInfo').zfbUserName
			window.wxLoginAndroidCallBack = this.wxLoginAndroidCallBack;
			window.alipayLoginAndroidCallBack = this.alipayLoginAndroidCallBack;
			// window.wxConfirmReceiptCallBack = this.wxConfirmReceiptCallBack;
		},
		methods: {
			confirmBindZfb() {
				document.body.style.overflow = 'auto';
				if (!this.zfbName) {
					uni.showToast({
						title: '请输入支付宝姓名',
						icon: 'none',
						duration: 2000
					})
					return
				}
				if (!this.zfbUserName) {
					uni.showToast({
						title: '请输入支付宝账号',
						icon: 'none',
						duration: 2000
					})
					return
				}
				this.$api.bindZfb({
					zfbName: this.zfbName,
					zfbUserName: this.zfbUserName,
				}).then(res => {
					this.getUserInfo()
					this.isShowPop = -1
					uni.showToast({
						title: '绑定支付宝成功',
						icon: 'none',
						duration: 2000
					})
				})
			},
			closeBindZfb() {
				this.isShowPop = -1
				document.body.style.overflow = 'auto';
			},
			bindZfb() {
				if (!this.zfbName) {
					uni.showToast({
						title: '请输入支付宝姓名',
						icon: 'none',
						duration: 2000
					})
					return
				}
				if (!this.zfbUserName) {
					uni.showToast({
						title: '请输入支付宝账号',
						icon: 'none',
						duration: 2000
					})
					return
				}
				this.$api.bindZfb({
					zfbName: this.zfbName,
					zfbUserName: this.zfbUserName,
				}).then(res => {
					this.getUserInfo()
					this.isShowZfbBox = false
					uni.showToast({
						title: '绑定支付宝成功',
						icon: 'none',
						duration: 2000
					})
				})
			},
			toPage(url) {
				this.$utils.toPage(url)
			},
			activePrice(index, key) {
				this.wayIndex = index
				this.priceIndex = key
			},
			getData() {
				this.$api.withdrawalTab({}).then(res => {
					this.withdrawalData[0].info = res.data.weiWithdrawalTab
					this.withdrawalData[1].info = res.data.aliWithdrawalTab
					// this.withdrawalData[0].info.unshift({
					// 	id: 0,
					// 	amount: this.userInfo.cash,
					// 	autoAccount: 1
					// })
					// this.withdrawalData[1].info.unshift({
					// 	id: 0,
					// 	amount: this.userInfo.cash,
					// 	autoAccount: 1
					// })
				})
			},
			getUserInfo() {
				this.$api.getInfo().then(res => {
					uni.setStorageSync('UserInfo', res.data)
					// this.withdrawalData[0].bind = res.data.wxOpenId ? 1 : 0
					// this.withdrawalData[1].bind = res.data.zfbName ? 1 : 0
					this.zfbName = uni.getStorageSync('UserInfo').zfbName
					this.zfbUserName = uni.getStorageSync('UserInfo').zfbUserName
					this.userInfo = res.data
					this.getData()
				})
			},
			bindWx(index) {
				const that = this
				if (index == 0) {
					// uni.login({
					// 	provider: 'weixin',
					// 	onlyAuthorize: true,
					// 	success: function(res) {
					// 		if (res.code) {
					// 			console.log(res.code);
					// 			that.$api.bindWxWithdrawalUser({
					// 				code: res.code
					// 			}).then(r => {
					// 				that.$utils.showTip('绑定成功', '提示')
					// 				that.isBindaccount()
					// 			})
					// 		} else {
					// 			console.log(err);
					// 		}
					// 	},
					// 	fail: function(err) {
					// 		console.log(err);
					// 	}
					// });

					window.rh_android.wxLogin()

				}


				if (index == 1) {
					// const hyyAlipay = uni.requireNativePlugin("Hyy-Alipay");
					// hyyAlipay.openAuthScheme({
					// 		appId: alipayAppId, // 支付宝分配给开发者的应用 ID
					// 		scheme: "", // manifest.json -> "App常用其他设置" -> "UrlSchemes"里设置
					// 	},
					// 	(res) => {
					// 		if (res.auth_code) {
					// 			console.log(res.auth_code);

					// 		} else {
					// 			console.log(err);
					// 		}
					// 	}
					// );
					console.log('绑定支付宝');
					// this.isShowPop = 0
					// document.body.style.overflow = 'hidden';
					window.rh_android.alipayLogin()
					// this.isShowZfbBox = true
				}

			},
			cancelRealName() {
				this.isShowPop = -1
				document.body.style.overflow = 'auto';
			},
			confirmRealName() {
				if (!this.realName.name) {
					uni.showToast({
						title: '请填写姓名',
						icon: 'none',
						duration: 2000
					})
					return
				}
				if (!this.realName.idCard) {
					uni.showToast({
						title: '请填写身份证号',
						icon: 'none',
						duration: 2000
					})
					return
				}
				if (this.realName.phone.length != 11) {
					uni.showToast({
						title: '请填写手机号',
						icon: 'none',
						duration: 2000
					})
					return
				}
				this.isShowPop = -1
				document.body.style.overflow = 'auto';
				this.$api.realNames({
					openid: this.userInfo.zfbUserName,
					name: this.realName.name,
					phone: this.realName.phone,
					idCard: this.realName.idCard
				}).then(res => {
					console.log(res);
				})
			},
			wxLoginAndroidCallBack(code) {
				this.$api.bindWxWithdrawalUser({
					code: code
				}).then(r => {
					this.$utils.showTip('绑定成功', '提示')
					this.isBindaccount()
				})

				return 'wxLoginAndroidCallBack调用成功'
			},
			alipayLoginAndroidCallBack(code) {
				this.$api.bindAliWithdrawalUser({
					code: code
				}).then(res => {
					console.log(res);
					this.$utils.showTip('绑定成功', '提示')
					this.isBindaccount()
				})

				return 'alipayLoginAndroidCallBack调用成功'
			},
			//查询时候已经绑定提现
			isBindaccount() {
				this.$api.withdrawalUser().then(res => {
					this.withdrawalData[0].bind = res.data.wxIsBind
					this.withdrawalData[1].bind = res.data.aliIsBind
				})
			},
			//申请提现
			apply() {
				const cash1 = uni.getStorageSync('UserInfo').cash
				const cash2 = this.withdrawalData[this.wayIndex].info[this.priceIndex].amount
				const withdrawalTabId = this.withdrawalData[this.wayIndex].info[this.priceIndex].id
				if (cash1 >= cash2) {
					if (withdrawalTabId != 0) {
						this.$api.applyWithdrawal({
							withdrawalTabId: this.withdrawalData[this.wayIndex].info[this.priceIndex].id,
							price: '',
							withdrawalType: this.wayIndex + 1
						}).then(res => {
							this.$utils.showModalAsync("申请成功", "提示").then(e => {
								// uni.navigateBack()
								this.getUserInfo()
							})
						})
					} else {
						this.$api.applyWithdrawal({
							withdrawalTabId: 0,
							price: cash1,
							withdrawalType: this.wayIndex + 1
						}).then(res => {
							this.$utils.showModalAsync("申请成功", "提示").then(e => {
								// uni.navigateBack()
								this.getUserInfo()
							})
						})
					}

				} else {
					this.$utils.showTip('现金不足', '提示')
				}
			},
			// //微信申请提现回调
			// wxConfirmReceiptCallBack() {
			// 	//防止被别的接口阻断
			// 	setTimeout(()=>{
			// 		uni.showLoading({
			// 			title: '获取结果，请稍等...'
			// 		})

			// 		setTimeout(()=>{
			// 			uni.hideLoading()
			// 			uni.navigateBack()
			// 		},4000)
			// 	},1000)
			// }

		}
	}
</script>

<style>
	page {
		background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
	}
</style>

<style lang="scss" scoped>
	.balance {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 50rpx;
		background-image: linear-gradient(to bottom, #FFF6E4, #FFFFFF, #FFFFFF);

		.detail {
			color: #8B6B4A;
			text-align: right;
			padding: 10rpx 30rpx;
		}

		&-title {
			font-size: 30rpx;
			line-height: 2;
			color: #5D3A1A;
		}

		&-price {
			font-size: 60rpx;
			font-weight: 700;
			line-height: 2;
			color: #5D3A1A;
		}

		&-sub-title {
			font-size: 25rpx;
			line-height: 2;
			color: #8B6B4A;
		}
	}

	.withdrawal {

		width: 100%;
		background-color: #FFFDF9;
		padding: 40rpx;
		border-radius: 32rpx;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

		&-title {
			display: flex;
			align-items: center;
			margin-bottom: 10rpx;

			&-image {
				width: 50rpx;
				margin: 10rpx;
			}

			&-text {
				font-size: 35rpx;
				color: #5D3A1A;
				font-weight: 700;
			}

			&-tag {
				padding: 3rpx 5rpx;
				margin: 0 10rpx;
				background: linear-gradient(135deg, #E8783B, #F4A460);
				color: #fff;
				font-size: 28rpx;
				border-radius: 2px;
			}

			&-bind {
				margin-left: auto;
				display: flex;
				align-items: center;
				font-size: 30rpx;
				color: #E8783B;

				&-img {
					width: 15rpx;
					margin-left: 5rpx;

				}
			}
		}

		&-info {
			display: flex;
			align-items: center;
			flex-wrap: wrap;
			width: 100%;

			&-box {
				width: calc(100% / 3);
				padding: 10rpx;
				position: relative;

				&-item {
					background-color: #FFFDF9;
					height: 200rpx;
					line-height: 200rpx;
					text-align: center;
					border-radius: 32rpx;
					position: relative;
					border: 1px solid rgba(200,130,60,0.1);

					&.active {
						border: #E8783B solid 1px;
						background-color: rgba(232,120,59,0.06);
					}



					&-tag {
						position: absolute;
						top: 0;
						right: 0;
						border-radius: 10rpx;
						color: #fff;
						background: linear-gradient(135deg, #E8783B, #F4A460);
						height: 40rpx;
						line-height: 40rpx;
						font-size: 20rpx;
						padding: 0 10rpx;

					}

					&-tag2 {
						position: absolute;
						top: 40rpx;
						left: 50rpx;
						border-radius: 10rpx;
						color: #000;
						height: 40rpx;
						line-height: 40rpx;
						font-size: 20rpx;
						padding: 0 10rpx;
					}


					&-describe {
						position: absolute;
						bottom: 0;
						left: 0;
						width: 100%;
						color: #E8783B;
						height: 70rpx;
						line-height: 70rpx;
					}

					&-price {
						font-size: 60rpx;
						color: #5D3A1A;
						font-weight: 700;

						&-unit {
							font-size: 30rpx;
							font-weight: normal;
						}
					}

					&-price2 {
						font-size: 60rpx;
						font-weight: 700;
						padding-top: 20rpx;

						&-unit {
							font-size: 30rpx;
							font-weight: normal;
						}
					}
				}
			}
		}

		&-tip {
			color: #8B6B4A;
			padding-bottom: 120rpx;

			&-title {
				font-size: 40rpx;
				line-height: 2;
			}

			&-line {
				font-size: 34rpx;
			}
		}

	}

	.bindZfbBox {
		width: 100vw;
		height: 100vh;
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background: rgba(0, 0, 0, 0.5);

		.zfbBox {
			width: 634rpx;
			height: 452rpx;
			background: #FFFDF9;
			border-radius: 32rpx;
			margin: 30vh auto;
			display: flex;
			align-items: center;
			justify-content: space-around;
			flex-direction: column;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.zfb-title {
				width: 50%;
				margin-left: 35%;
				display: flex;
				justify-content: space-between;
				align-items: center;
				font-weight: 700;
				color: #5D3A1A;
			}

			.zfbBox-item {
				display: flex;
				align-items: center;

				.zfbBox-item-input {
					width: 456rpx;
					height: 58rpx;
					background: #FFFDF9;
					border-radius: 32rpx;
					border: 2px solid rgba(200,130,60,0.1);

					input {
						height: 100%;
						padding-left: 10rpx;
					}
				}
			}

			.zfbBox-btn {
				width: 350rpx;
				height: 88rpx;
				background: linear-gradient(135deg, #E8783B, #F4A460);
				font-size: 36rpx;
				border-radius: 42rpx;
				color: #fff;
				text-align: center;
				line-height: 88rpx;
			}
		}
	}

	.bindZfb {
		width: 100vw;
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background: rgba(0, 0, 0, 0.5);

		.bindZfb-box {
			width: 555rpx;
			height: 513rpx;
			position: relative;
			display: flex;
			flex-direction: column;
			margin: 40% auto;
			align-items: center;
			background: linear-gradient(180deg, #F4A460 0%, #FFFDF9 50%, #FFFDF9 100%);
			border-radius: 32rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.bindZfb-box-item {
				font-size: 44rpx;
				color: #5D3A1A;
				font-weight: 700;
				margin-top: 20rpx;
			}

			.bindZfb-box-item2 {
				width: 412rpx;
				height: 80rpx;
				display: flex;
				align-items: center;
				margin-top: 30rpx;
				background: #FFFDF9;
				border-radius: 32rpx;
				border: 1px solid rgba(200,130,60,0.1);

				input {
					margin-left: 10rpx;
				}
			}

			.bindZfb-box-item3 {
				margin-top: 40rpx;
				width: 422rpx;
				height: 101rpx;
				font-size: 36rpx;
				color: #FFFFFF;
				text-align: center;
				line-height: 101rpx;
				background: linear-gradient(135deg, #E8783B, #F4A460);
				border-radius: 32rpx 32rpx 32rpx 32rpx;
			}

			.bindZfb-close {
				width: 28rpx;
				height: 28rpx;
				position: absolute;
				right: 30rpx;
				top: 30rpx;
				// background: url('/static/close.png');
				background-size: cover;
			}
		}
	}

	.realName {
		width: 100vw;
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background: rgba(0, 0, 0, 0.5);

		.realName-box {
			width: 555rpx;
			height: 903rpx;
			position: relative;
			display: flex;
			align-items: center;
			flex-direction: column;
			margin: 40% auto;
			background: linear-gradient(180deg, #F4A460 0%, #FFFDF9 50%, #FFFDF9 100%);
			border-radius: 32rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.realName-box-item {
				width: 412rpx;
				height: 80rpx;
				font-size: 28rpx;
				margin-top: 35rpx;
				display: flex;
				align-items: center;
				background: #FFFDF9;
				border-radius: 32rpx;
				border: 1px solid rgba(200,130,60,0.1);

				input {
					margin-left: 15rpx;
				}
			}

			.realName-box-scroll {
				position: absolute;
				font-size: 28rpx;
				color: #5D3A1A;
				left: 35rpx;
				right: 35rpx;
				bottom: 150rpx;
				height: 250rpx;
			}

			.realName-box-btns {
				position: absolute;
				bottom: 20rpx;
				left: 0;
				right: 0;
				display: flex;
				align-items: center;
				justify-content: space-around;

				image {
					width: 213rpx;
					height: 100rpx;
				}
			}

			.realName-box-close {
				width: 28rpx;
				height: 28rpx;
				position: absolute;
				right: 30rpx;
				top: 30rpx;
				// background: url('/static/close.png');
				background-size: cover;
			}
		}
	}

	.checkResult {
		width: 100vw;
		height: 100vh;
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background: rgba(0, 0, 0, 0.5);

		.checkResult-box {
			width: 555rpx;
			height: 360rpx;
			position: relative;
			display: flex;
			align-items: center;
			justify-content: space-around;
			flex-direction: column;
			margin: 20vh auto;
			background: linear-gradient(180deg, #F4A460 0%, #FFFDF9 50%, #FFFDF9 100%);
			border-radius: 32rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			image {
				width: 116rpx;
				height: 116rpx;
			}

			.checkResult-box-close {
				width: 28rpx;
				height: 28rpx;
				position: absolute;
				right: 30rpx;
				top: 30rpx;
				// background: url('/static/close.png');
				background-size: cover;
			}
		}
	}
</style>
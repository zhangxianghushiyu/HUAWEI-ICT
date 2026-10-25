<template>
	<view class="view">
		<view class="view-box">
			<view class="view-box-top">
				<image class="view-box-top-img" mode="scaleToFill" :src="data.img" />
				<view class="view-box-top-info">
					<view class="view-box-top-info-title">{{data.name}}</view>
					<view class="view-box-top-info-content">{{data.text}}</view>
				</view>
			</view>
			<view class="view-box-line">
				<view>商品名称：</view>
				<view>{{data.name}}</view>
			</view>
			<view class="view-box-line">
				<view>商品金额：</view>
				<view>{{data.price}}</view>
			</view>
			<view class="view-box-line">
				<view>支付方式：</view>
				<view class="pay-card">
					<view class="pay-card-item" @click="changeItem(item)" v-for="item in payList" :key="item.name">
						<view class="" style="display: flex;align-items: center;">
							<image :src="item.img"
								style="width: 38rpx;height: 38rpx;display: block;margin-right: 16rpx;" mode=""></image>
							<view class="" style="font-size: 30rpx;">{{item.name}}</view>
						</view>
						<image v-if="item.isCheck == true" src="/static/images/change/cli-select.png"
							style="width: 30rpx;height: 30rpx;display: block;" mode="scaleToFill"></image>
						<image v-else src="/static/images/cli-gray.png"
							style="width: 30rpx;height: 30rpx;display: block;" mode=""></image>
					</view>
				</view>
			</view>

		</view>
		<view class="bottom-box">
			<button hover-class="button-hover" type="theme" @click="buy" size="long">立即支付</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				data: {},
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
				payIndex: 0
			}
		},
		onLoad() {
			this.data = uni.getStorageSync('Buy_Drama')
			uni.removeStorageSync('Buy_Drama')

			window.alipayBuyDramaPaymentAndroidCallBack = this.alipayBuyDramaPaymentAndroidCallBack
		},
		methods: {
			buy() {
				const that = this

				const typeItem = that.payList.filter(it => {
					return it.isCheck == true
				})[0]

				that.$api.createDramaOrder({
					dramaDetailsId: that.data.detailId,
					dramaId: that.data.id,
					payDramaType: that.data.type,
					payType: typeItem.name == '微信' ? 1 : 2,
				}).then(res => {
					if (typeItem.name == '微信') {

					}

					if (typeItem.name == '支付宝') {

						window.window.rh_android.alipayBuyDramaPayment(res.data.orderNo)

						// that.$api.aliCreateOrder({
						// 	orderNo: res.data.orderNo
						// }).then(result => {
						// 	that.$utils.alipayPay(result.data).then(r => {
						// 		that.$utils.showTip(r)
						// 		uni.navigateBack()
						// 	}).catch(e => {
						// 		that.$utils.showTip(e)
						// 	})
						// })


					}
				})
			},
			alipayBuyDramaPaymentAndroidCallBack(type, callback) {
				if (type == "1") {
					console.log(`调用返回 type=${type},callback=${callback},dramaId=${this.data.id}`);
					//获取视频信息
					this.$api.getDetailsList({
						dramaId: this.data.id
					}).then(res => {
						console.log('获取最新信息成功');
						const dataList = res.data
						dataList.filter(item => {
							/** 参数数据自行拼接  */
							item.coverShow = true //是否显示视频封面，vue 小程序端不播放会显示视频，可以不用显示封面，App不播放不会显示视频，就需要封面了
							item.object_fit = 'cover' //视频的显示类型 contain cover
							item.sliderShow = true //是否显示进度条
							item.likeShow = item.isLike == 0 ? false : true //是否点赞
							item.collectShow = item.isCollect == 0 ? false : true //是否收藏
							item.selectWork = true //是否短剧
						})

						getApp().globalData.seeVodList = dataList;

						uni.navigateBack()
					})



				} else {
					this.$utils.showTip(callback, '提示')
				}
			},
			changeItem(item) {
				this.payList.forEach(v => {
					v.isCheck = false
				})
				item.isCheck = true
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
	.view {
		padding: 30rpx;

		&-box {
			width: 100%;
			padding: 30rpx;
			background-color: #FFFDF9;
			border-radius: 32rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			&-top {
				display: flex;

				&-img {
					width: 200rpx;
					height: 250rpx;
					border-radius: 32rpx;
					margin-right: 20rpx;
				}

				&-info {
					flex: 1;
					padding: 10rpx 0;

					&-title {
						font-size: 35rpx;
						font-weight: 700;
						color: #5D3A1A;
						margin-bottom: 20rpx;
					}

					&-content {
						font-size: 30rpx;
						color: #8B6B4A;
					}
				}
			}

			&-line {
				color: #5D3A1A;
				display: flex;
				align-items: center;
				justify-content: space-between;
				font-size: 30rpx;
				padding: 20rpx 0;
				border-top: 1px solid rgba(200,130,60,0.1);

				.pay-card {
					box-sizing: border-box;
					display: flex;
					align-items: center;
					justify-content: space-between;

					&-item {
						width: 230rpx;
						height: 84rpx;
						border-radius: 32rpx;
						display: flex;
						align-items: center;
						justify-content: space-between;
						padding: 23rpx 20rpx;
						box-sizing: border-box;
						border: 1px solid rgba(200,130,60,0.1);
					}
				}
			}
		}
	}
</style>
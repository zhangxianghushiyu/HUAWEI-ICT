<template>
	<view>
		<view class="top">
			<BaseHeader bgImageUrl="/static/images/change/DetailBg1.png">
				<view class="head-title">账户明细
					<view class="helder-back">
						<uni-icons color="#000" type="left" size="25" @click="leftClick" />
					</view>
				</view>
			</BaseHeader>
			<view class="mine">
				<image class="mine-bg" src="@/static/images/change/DetailBg2.png" mode="widthFix" />
				<view class="money">
					<view class="money-info">
						<view class="money-info-item">
							<view class="money-info-item-title">
								金币余额(币）
							</view>
							<view class="money-info-item-price">
								{{userInfo.goldCoin}}
							</view>

						</view>
						<view class="money-info-item">
							<view class="money-info-item-title">
								现金余额(元）
							</view>
							<view class="money-info-item-price">
								{{userInfo.cash}}
							</view>

						</view>
					</view>
				</view>
				<!-- 		<view class="mine-tip">
					金币次日自动兑换现金，汇率约{{version.convertRatio}}:1
				</view> -->
				<uv-tabs :current="tabIndex" :list="list" @click="click" lineColor="#333333"
					:scrollable="false"></uv-tabs>
			</view>
		</view>
		<view class="line" v-for="(item,index) in dataList" :key="index" @click="withdrawal(item)">
			<view class="line-left">
				<view class="line-left-title">
					<text>{{item.describe}}</text>
					<image v-if="item.status == 0" class="line-left-title-tag" src="@/static/images/Examine.png"
						mode="heightFix" />
					<image v-if="item.status == 1" class="line-left-title-tag" src="@/static/images/PaidAlready.png"
						mode="heightFix" />
					<image v-if="item.status == 2" class="line-left-title-tag" src="@/static/images/Rejected.png"
						mode="heightFix" />
					<image v-if="item.status == 3 && item.method == 1" class="line-left-title-tag"
						src="@/static/images/MakePayment.png" mode="heightFix" />
				</view>
				<view class="line-left-date">{{item.createTime}}</view>
			</view>
			<view class="line-right red">{{item.scene ==1 ?'+':'-'}}{{item.money}}</view>
		</view>

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
				tabIndex: 0,
				list: [{
						name: '金币明细',
					},
					{
						name: '现金明细',
					},
				],
				userInfo: {},
				param: {
					pageNum: 1,
					pageSize: 15,
				},
				dataList: [],
				moreStatus: 'loadmore', //loading  nomore
				version: {}
			}
		},
		onLoad(e) {
			if (e.index) this.tabIndex = e.index
			this.userInfo = uni.getStorageSync('UserInfo')
			this.version = uni.getStorageSync('Version')
			// this.getList(true)

			this.wxConfirmReceiptCallBack()

			window.wxConfirmReceiptCallBack = this.wxConfirmReceiptCallBack;
		},
		onReachBottom() {
			if (this.moreStatus != 'nomore')
				this.getList()
		},
		methods: {
			leftClick() {
				uni.navigateBack()
			},
			click(e) {
				this.tabIndex = e.index
				this.getList(true)
			},
			getList(refresh = false) {
				if (refresh) {
					this.param.pageNum = 1
					this.dataList = []
				}

				this.moreStatus = 'loading'

				const param = {
					pageNum: this.param.pageNum,
					pageSize: this.param.pageSize,
				}

				const api = this.tabIndex == 0 ? 'getBillGoldList' : 'getBillCashList'

				this.$api[api](param).then(res => {
					this.dataList = this.dataList.concat(res.data.list)
					if (res.data.isLastPage) this.moreStatus = 'nomore'
					else this.moreStatus = 'loadmore'
					this.param.pageNum++
				})
			},
			withdrawal(item) {
				if (item.status != 3 && item.method != 1) return
				console.log(item);
				window.rh_android.WxConfirmReceipt(item.mchId, item.packageInfo)
			},
			//微信申请提现回调
			wxConfirmReceiptCallBack() {
				this.getList(true)
				return
				//防止被别的接口阻断
				setTimeout(() => {
					uni.showLoading({
						title: '获取结果...'
					})

					setTimeout(() => {
						uni.hideLoading()
						this.getList(true)
					}, 4000)
				}, 1000)

			}
		}
	}
</script>

<style lang="scss" scoped>
	.top {
		position: sticky;
		top: 0;
		background-color: #FFFDF9;
		z-index: 999;
	}

	.head-title {
		width: 100%;
		font-family: PingFang SC;
		font-weight: 700;
		font-size: 32rpx;
		color: #5D3A1A;
		text-align: center;
		position: relative;

		.helder-back {
			position: absolute;
			top: 0;
			left: 20rpx;
		}
	}

	.mine {
		position: relative;
		padding: 30rpx;


		&-bg {
			width: 100%;
			position: absolute;
			top: 0;
			left: 0;
			z-index: -1;
		}

		&-tip {
			font-size: 25rpx;
			color: #8B6B4A;
			line-height: 3;
		}


	}

	.money {
		position: relative;
		width: 100%;
		border-radius: 32rpx;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

		&-info {
			width: inherit;
			height: inherit;
			background-color: #FFFDF9;
			border-radius: 32rpx;
			display: flex;
			position: relative;

			&::after {
				content: '';
				position: absolute;
				top: 30%;
				right: 50%;
				width: 1upx;
				height: 100rpx;
				background-color: rgba(200,130,60,0.1);
			}

			&-item {
				flex: 1;
				padding: 20rpx 40rpx;
				display: flex;
				flex-direction: column;
				justify-content: space-between;

				&-title {
					display: flex;
					align-items: center;
					color: #8B6B4A;
				}

				&-price {
					font-weight: 700;
					font-size: 60rpx;
					color: #5D3A1A;
				}
			}
		}
	}

	.line {
		padding: 30rpx 20rpx;
		display: flex;
		align-items: center;

		&-left {
			flex: 1;

			&-title {
				font-size: 35rpx;
				font-weight: 700;
				display: flex;
				align-items: center;
				margin-bottom: 10rpx;
				color: #5D3A1A;

				&-tag {
					height: 35rpx;
					margin-left: 10rpx;
				}
			}

			&-date {
				font-size: 28rpx;
				color: #8B6B4A;
			}
		}

		&-right {
			font-size: 30rpx;
			font-weight: bold;


			&.red {
				color: #E8783B;
			}

		}
	}
</style>
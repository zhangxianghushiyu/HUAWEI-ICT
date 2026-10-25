<template>
		<view class="view">
			<view class="item" v-for="(item,index) in dataList" :key="index">
				<view class="top">
					<view class="order">订单号：{{item.orderNo}}</view>
					<view v-if="item.status == 0 " class="type">待发货</view>
					<view v-if="item.status == 1 " class="type">已发货</view>
					<view v-if="item.status == 2 " class="type">已收货</view>
				</view>
				<view class="center">
					<image class="img" :src="item.goodsUrl" mode="scaleToFill" />
					<view class="content">
						<view class="title">{{item.goodsName}}</view>
						<view class="number">x {{item.payCount}}</view>
						<view class="money">
							<image class="money-img" src="/static/images/change/leaf.png" mode="scaleToFill" />
							{{item.payMoney}}
						</view>
					</view>
				</view>
				<view class="bottom" v-if="item.status == 1">
					<!-- <image class="btn" src="/static/images/change/deleteOrder.png" mode="widthFix" /> -->
					<image @click="submit(item)" class="btn" src="/static/images/change/confirmHarvest.png" mode="widthFix" />
				</view>
			</view>
			<uv-load-more :status="moreStatus" />
		</view>
	</template>

	<script>
		export default {
			data() {
				return {
					param: {
						pageNum: 1,
						pageSize: 15,
					},
					dataList: [],
					moreStatus: 'loadmore', //loading  nomore
				}
			},
			onLoad() {
				this.getList(true)
			},
			onReachBottom() {
				if (this.moreStatus != 'nomore')
					this.getList()
			},
			onPullDownRefresh() {
				this.getList(true)
			},
			methods: {
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

					this.$api.goodsOrderList(param).then(res => {
						this.dataList = this.dataList.concat(res.data.list)
						if (res.data.isLastPage) this.moreStatus = 'nomore'
						else this.moreStatus = 'loadmore'
						this.param.pageNum++
					})
				},
				submit(item){
					this.$utils.showModalAsync('一但收货后无法取消，是否确认收货','提示').then(res=>{
						this.$api.takeOver({id:item.id}).then(result=>{
							this.$utils.showTipAsync("收货成功", '成功').then(res => {
								this.getList(true)
							})
						})
					})
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
		.view {
			padding: 20rpx;
		}

		.item {
			margin-bottom: 20rpx;
			background-color: #FFFDF9;
			padding: 20rpx;
			border-radius: 30rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.top {
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin-bottom: 20rpx;

				.order {
					color: #8B6B4A;
				}

				.type {
					font-size: 28rpx;
					color: #E8783B;
					font-weight: 700;
				}

			}

			.center {
				display: flex;
				align-items: center;
				margin-bottom: 20rpx;

				.img {
					width: 200rpx;
					height: 200rpx;
					margin-right: 20rpx;
					border-radius: 16rpx;
				}

				.content {
					flex: 1;
					height: 200rpx;
					display: flex;
					flex-direction: column;
					justify-content: space-between;
					padding: 20rpx 0;

					.title {
						font-size: 30rpx;
						color: #5D3A1A;
						font-weight: 700;
					}

					.number {
						color: #8B6B4A;
					}

					.money {
						display: flex;
						align-items: center;
						color: #E8783B;
						font-size: 35rpx;
						font-weight: bold;

						&-img {
							width: 50rpx;
							height: 50rpx;
							margin-right: 10rpx;
						}
					}
				}
			}

			.bottom {
				display: flex;
				justify-content: flex-end;

				.btn {
					width: 160rpx;
				}
			}
		}
	</style>
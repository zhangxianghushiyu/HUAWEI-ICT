<template>
	<view class="content">
		<view class="title">
			<uni-icons @tap="back()" type="left" size="24"></uni-icons>
			<span>交易记录</span>
		</view>
		<view class="body">
			<uv-tabs :list="list1" lineWidth="30" :activeStyle="{
            color: '#000000',
            fontWeight: 'bold',
            transform: 'scale(1.05)'
        }" :inactiveStyle="{
            color: '#9F9F9F',
            transform: 'scale(1)'
        }" lineColor="#000000" itemStyle="padding-left: 120rpx; padding-right: 120rpx; height: 80rpx;"
				@click="click"></uv-tabs>
			<view class="body-item1" v-show="showIndex==0">
				<scroll-view scroll-y="true" style="height: 100%;">
					<view class="body-item1-item" v-for="item in cashList">
						<view
							style="width: 90%;margin: auto; display: flex;align-items: center;justify-content: space-between;">
							<view class="body-item1-item-left">
								<span style="font-size: 30rpx;">提现</span>
								<span style="font-size: 24rpx;color: #999999;">{{item.createTime}}</span>
							</view>
							<view class="body-item1-item-left">
								<span style="font-size: 30rpx;">{{item.money}}</span>
								<span
									style="font-size: 24rpx;color: #999999;">{{item.status==0?'审核中':item.status==1?'已打款':item.status==2?'已驳回':'已通过'}}</span>
							</view>
						</view>
						<view class="body-item1-item-line"></view>
					</view>
				</scroll-view>
			</view>
			<view class="body-item1" v-show="showIndex==1">
				<scroll-view scroll-y="true" style="height: 100%;" @scrolltolower='getMore()'>
					<view class="body-item1-item" v-for="item in goldList">
						<view
							style="width: 90%;margin: auto; display: flex;align-items: center;justify-content: space-between;">
							<view class="body-item1-item-left">
								<span style="font-size: 30rpx;">{{item.describe}}</span>
								<span style="font-size: 24rpx;color: #999999;">{{item.createTime}}</span>
							</view>
							<view class="body-item1-item-left">
								<span style="font-size: 30rpx;">{{item.money}}</span>
								<span style="font-size: 24rpx;color: #2684FF;">兑换成功</span>
							</view>
						</view>
						<view class="body-item1-item-line"></view>
					</view>
				</scroll-view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list1: [{
					name: '提现明细',
				}, {
					name: '兑换记录',
				}],
				queryParam: {
					pageNum: 1,
					pageSize: 10
				},
				isHasMore: false,
				cashList: [],
				goldList: [],
				showIndex: 0,
			};
		},
		onLoad() {
			this.getCashList()
		},
		methods: {
			getMore() {
				if (this.isHasMore) {
					if (this.showIndex == 0) {
						this.getCashList()
					} else {
						this.getGoldList()
					}
				}

			},
			back() {
				uni.navigateBack()
			},
			//tab切换
			click(e) {
				this.showIndex = e.index
				this.cashList = []
				this.goldList = []
				this.queryParam.pageNum = 1
				this.isHasMore = false
				if (e.index == 0) {
					this.getCashList()
				} else {
					this.getGoldList()
				}
			},
			//现金明细
			getCashList() {
				this.$api.getBillCashList(this.queryParam).then(res => {
					this.cashList = this.cashList.concat(res.data.list)
					if (res.data.list.length >= 10) {
						this.isHasMore = true
						this.queryParam.pageNum++
					} else {
						this.isHasMore = false
					}
				})
			},
			//金币明细
			getGoldList() {
				this.$api.getBillGoldList(this.queryParam).then(res => {
					this.goldList = this.goldList.concat(res.data.list)
					if (res.data.list.length >= 10) {
						this.isHasMore = true
						this.queryParam.pageNum++
					} else {
						this.isHasMore = false
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		width: 100vw;
		height: 100vh;
		overflow: hidden;
		background: linear-gradient(180deg, #FDF6EE 0%, #FDE7C1 100%);

		.title {
			margin: 40rpx;
			display: flex;
			align-items: center;

			span {
				font-size: 34rpx;
				color: #5D3A1A;
				font-weight: 700;
				margin-left: 220rpx;
			}
		}

		.body {
			width: 750rpx;
			position: absolute;
			left: 0;
			right: 0;
			bottom: 0;
			top: 200rpx;
			background: #FFFFFF;
			border-radius: 40rpx 40rpx 0rpx 0rpx;

			.body-item1 {
				position: absolute;
				bottom: 0;
				top: 6%;
				display: flex;
				flex-direction: column;

				.body-item1-item {
					margin: 20rpx auto;
					display: flex;
					flex-direction: column;
					align-items: center;

					.body-item1-item-line {
						width: 690rpx;
						height: 2rpx;
						margin-top: 24rpx;
						background: #EFEFEF;
					}

					.body-item1-item-left {
						display: flex;
						margin-top: 24rpx;
						flex-direction: column;

						span {
							margin-top: 8rpx;
						}
					}
				}
			}
		}
	}
</style>
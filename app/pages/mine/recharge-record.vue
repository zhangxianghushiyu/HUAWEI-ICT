<template>
	<view class="view">
		<view class="box" v-for="(item,index) in dataList" :key="index">
			<view class="line">
				<view class="title">
					<image src="/static/images/Recharge.png" mode="widthFix"
						style="width: 50rpx;margin-right: 10rpx;" />
					<view>购买会员</view>
				</view>
				<view class="status">{{item.payStatus ==2?'已支付':'未支付'}}</view>
			</view>
			<view class="line">
				<view class="date">{{item.createTime}}</view>
				<view class="number">
					+{{item.money}}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				moreStatus: 'loadmore', //loading  nomore
				param: {
					pageNum: 1,
					pageSize: 15,
				},
				dataList: [],
			}
		},
		onLoad() {
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

				this.$api.getBayVipRecord(param).then(res => {
					this.dataList = this.dataList.concat(res.data.list)
					if (res.data.isLastPage) this.moreStatus = 'nomore'
					else this.moreStatus = 'loadmore'
					this.param.pageNum++
				})
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

		.box {
			background-color: #FFFDF9;
			padding: 20rpx;
			border-radius: 30rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
			margin-bottom: 20rpx;

			.line {
				display: flex;
				align-items: center;
				line-height: 2;

				justify-content: space-between;

				.title {
					font-size: 30rpx;
					font-weight: 700;
					color: #5D3A1A;
					display: flex;
					align-items: center;
				}

				.status {
					color: #8B6B4A;
				}

				.date {
					color: #8B6B4A;
				}

				.number {
					font-size: 30rpx;
					color: #E8783B;
					font-weight: 700;
				}
			}

		}
	}
</style>
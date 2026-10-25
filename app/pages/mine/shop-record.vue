<template>
		<view class="view">
			<view class="item" v-for="(item,index) in dataList" :key="index">
				<view class="title">{{item.describe}}</view>
				<view class="sub">
					<view>{{item.createTime}}</view>
					<view v-if="item.scene == 1" class="number red">+{{item.money}}</view>
					<view v-else class="number ">-{{item.money}}</view>
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

					this.$api.billLeaf(param).then(res => {
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

	<style scoped lang="scss">
		.view {
			padding: 20rpx;
		}

		.item {
			margin-bottom: 20rpx;
			background-color: #FFFDF9;
			padding: 30rpx;
			border-radius: 30rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			.title {
				font-size: 30rpx;
				margin-bottom: 30rpx;
				font-weight: 700;
				color: #5D3A1A;
			}

			.sub {
				display: flex;
				align-items: center;
				justify-content: space-between;
				color: #8B6B4A;
				font-size: 28rpx;

				.number {
					font-size: 40rpx;
					font-weight: bold;

					&.red {
						color: #E8783B;
					}

					&.gray {
						color: #5D3A1A;
					}
				}
			}
		}
	</style>
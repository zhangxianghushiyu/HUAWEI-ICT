<template>
	<view>
		<view class="top">
		<!-- 	<uni-icons @tap="back()" style="position: fixed;left:10rpx;top:20rpx;color:#000;z-index: 999;" type="left"
				size="30"></uni-icons> -->
			<BaseHeader bgImageUrl="/static/images/change/ShopBg1.png">
				<view class="head-title">金币商城
					<!-- 		<view class="helder-back">
						<uni-icons color="#000" type="left" size="25" @click="leftClick" />
					</view> -->
				</view>
			</BaseHeader>
			<view class="mine">
				<image class="mine-bg" src="/static/images/change/ShopBg2.png" mode="scaleToFill" />
				<view class="mine-title">当前金币</view>
				<view class="mine-number">{{userInfo.goldCoin}}</view>
				<view class="mine-sub">
					<view class="mine-sub-order" @click="$utils.toPage('/pages/mine/shop-order')">我的订单
						<image style="width: 18rpx;height: 26rpx;margin-left: 10rpx;"
							src="@/static/images/change/ShopRright1.png" mode="scaleToFill">
						</image>
					</view>
				</view>
			</view>
			<view class="searchBox">
				<view class="searchBox-left">
					<image src="/static/10.png" mode=""></image>
					<input v-model="param.goodsName" type="text" placeholder="请输入搜索内容" />
				</view>
				<view class="searchBox-right" @tap="searchBtn()">
					<span>搜索</span>
				</view>
			</view>
			<!-- <uv-tabs :list=" [{name: '全部'}]" lineColor="#F4BC44" lineHeight="5"></uv-tabs> -->
		</view>
		<view class="shopList">
			<view class="shopList-left">
				<scroll-view scroll-y="true" style="height: 100%;">
					<view @tap="chooseTypes(item,index)" class="shopList-left-item"
						:class="{'shopList-left-item-selected':typesIndex==index}" v-for="(item,index) in typesList">
						<span>{{item.typeName}}</span>
					</view>
				</scroll-view>
			</view>
			<view class="shopList-right">
				<scroll-view scroll-y="true" style="height: 100%;">
					<view class="shopList-right-item" v-for="(item,index) in dataList">
						<view class="shopList-right-item1">
							<image :src="item.url" mode=""></image>
						</view>
						<view class="shopList-right-item2">
							<span style="font-size: 26rpx;color: #2D2D2D;">{{item.name}}</span>
							<!-- <span style="font-size: 21rpx;color: #999999;">金黄大菠萝，超配百香果</span> -->
							<view style="display: flex;align-items: center;">
								<image style="width: 36rpx;height: 36rpx;" src="/static/11.png" mode=""></image>
								<span
									style="font-size: 26rpx;color: #2D2D2D;margin-left: 8rpx;">{{item.leafPrice}}</span>
							</view>
						</view>
						<view class="shopList-right-item3" @tap="toAddress(item)">
							<image src="/static/12.png" mode=""></image>
						</view>
					</view>
				</scroll-view>
			</view>
		</view>
		<view class="list" v-if="false">
			<view class="list-item" v-for="(item,index) in dataList" :key="index">
				<image class="img" :src="item.url" mode="scaleToFill" />
				<view class="list-item-top">
					<view class="title">{{item.name}}</view>
					<view class="number">
						<image @click="changeNumber(0,item)" class="number-change"
							src="/static/images/change/subtractNumber.png" mode="scaleToFill" />
						<view>{{item.quantity || 0}}</view>
						<image @click="changeNumber(1,item)" class="number-change"
							src="/static/images/change/addNumber.png" mode="scaleToFill" />
					</view>
				</view>
				<view class="list-item-bottom">
					<view class="money">
						<image class="money-img" src="/static/images/change/leaf.png" mode="scaleToFill" />
						{{item.leafPrice}}
					</view>
					<image @click="toAddress(item)" class="exchange" src="/static/images/change/exchange.png"
						mode="widthFix" />
				</view>
			</view>
		</view>
		<!-- <uv-load-more :status="moreStatus" /> -->
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
				param: {
					pageNum: 1,
					pageSize: 15,
					goodsType: '',
					goodsName: ''
				},
				dataList: [],
				typesIndex: 0,
				typesList: [],
				moreStatus: 'loadmore', //loading  nomore
				selectedItem: null,
			}
		},
		onLoad() {
			this.userInfo = uni.getStorageSync('UserInfo')
			this.getGoodsTypes()
		},
		onShow() {
			const address = uni.getStorageSync('selectAddress')
			if (address && this.selectedItem) {
				uni.removeStorageSync('selectAddress')
				this.submit(address)
			}
		},
		onReachBottom() {
			console.log('加载更多');
			if (this.moreStatus != 'nomore')
				this.getList()
		},
		onPullDownRefresh() {
			this.getList(true)
		},
		methods: {
			back() {
				uni.reLaunch({
					url: '/pages/mine/index'
				})
			},
			//获取商品分类
			getGoodsTypes() {
				this.$api.getGoodsTypes().then(res => {
					this.typesList = res.data
					this.param.goodsType = res.data[0].id
					this.getList(true)
				})
			},
			searchBtn() {
				if (!this.param.goodsName) {
					uni.showToast({
						title: '请输入搜索内容',
						icon: 'none',
					})
					return
				}
				this.param.goodsType = ''
				this.getList(true)
			},
			//选择分类
			chooseTypes(item, index) {
				this.param.goodsName = ''
				this.typesIndex = index
				this.param.pageNum = 1
				this.param.goodsType = item.id
				this.getList(true)

			},
			changeNumber(type, item) {
				if (type == 0 && item.quantity > 0) item.quantity--
				if (type == 1) item.quantity++
			},
			toAddress(item) {
				console.log(item);
				// if (item.quantity == 0) {
				// 	this.$utils.showTip('兑换数量不能为零', '提示')
				// 	return
				// }
				this.selectedItem = item
				this.$utils.toPage('/pages/mine/address?select=yes')
			},
			leftClick() {
				uni.navigateBack()
			},
			submit(address) {
				this.$api.buyGoods({
					addressId: address.id,
					count: 1,
					goodsId: this.selectedItem.id,
				}).then(res => {
					this.selectedItem = null
					this.$utils.showTipAsync("购买成功", '成功').then(res => {
						this.getUserInfo()
						this.getList(true)
					})
				})
			},
			getList(refresh = false) {
				if (refresh) {
					this.param.pageNum = 1
					this.dataList = []
				}
				this.moreStatus = 'loading'
				this.$api.getGoods(this.param).then(res => {
					this.dataList = this.dataList.concat(res.data.list.map(it => {
						return {
							id: it.id,
							goodsType: it.goodsType,
							leafPrice: it.leafPrice,
							name: it.name,
							url: it.url,
						}
					}))
					if (res.data.isLastPage) this.moreStatus = 'nomore'
					else this.moreStatus = 'loadmore'
					this.param.pageNum++
					if (this.dataList.length > 0) {
						const goodsType = this.dataList[0].goodsType
						const index = this.typesList.findIndex(res => res.id == goodsType)
						this.typesIndex = index
					}
				})
			},
			getUserInfo() {
				this.$api.getInfo().then(res => {
					uni.setStorageSync('UserInfo', res.data)
					this.userInfo = res.data
				})
			}
		}
	}
</script>


<style>
	page {
		background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%) !important;
	}
</style>

<style lang="scss" scoped>
	.top {
		position: sticky;
		top: 0;
		background-color: transparent;
		z-index: 999;
	}

	.head-title {
		width: 100%;
		font-family: PingFang SC;
		font-weight: bold;
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
		width: 100%;
		height: 300rpx;
		position: relative;
		display: flex;
		justify-content: space-between;
		flex-direction: column;
		padding: 20rpx 30rpx;
		padding-top: 70rpx;

		&-title {
			font-size: 40rpx;
		}

		&-number {
			color: #9B571E;
			font-size: 50rpx;
			font-weight: bold;
		}

		&-sub {
			display: flex;
			align-items: center;
			justify-content: space-between;

			&-record {
				display: flex;
				align-items: center;
				color: #999999;
			}

			&-order {
				display: flex;
				align-items: center;
				color: #9B571E;
				font-size: 30rpx;
				font-weight: bold;
			}
		}

		&-bg {
			width: inherit;
			height: inherit;
			position: absolute;
			left: 0;
			top: 0;
			z-index: -1;
		}
	}

	.searchBox {
		width: 700rpx;
		height: 68rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin: 32rpx auto;
		background: #FFFFFF;
		border: 1rpx solid rgba(200,130,60,0.1);
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
		border-radius: 200rpx 200rpx 200rpx 200rpx;

		.searchBox-left {
			display: flex;
			align-items: center;
			font-size: 28rpx;
			color: #999999;
			margin-left: 28rpx;

			image {
				width: 30rpx;
				height: 30rpx;
				margin-right: 24rpx;
			}
		}

		.searchBox-right {
			color: #E8783B;
			margin-right: 30rpx;
		}
	}

	.shopList {
		display: flex;
		position: absolute;
		top: 32%;
		left: 0;
		right: 0;
		bottom: 0;

		.shopList-left {
			width: 168rpx;
			height: 100%;
			background: #FFFDF9;

			.shopList-left-item {
				width: 100%;
				height: 100rpx;
				font-size: 23rpx;
				color: #8B6B4A;
				background: #FFFDF9;
				text-align: center;
				line-height: 100rpx;
			}

			.shopList-left-item-selected {
				width: 100%;
				height: 100rpx;
				font-size: 23rpx;
				color: #5D3A1A;
				font-weight: 700;
				background: #FFFFFF;
				text-align: center;
				line-height: 100rpx;
				border-left: 4rpx solid #E8783B;
			}
		}

		.shopList-right {
			position: absolute;
			left: 168rpx;
			right: 0rpx;
			height: 100%;

			.shopList-right-item {
				width: 100%;
				height: 150rpx;
				margin-top: 32rpx;
				display: flex;
				justify-content: space-around;

				.shopList-right-item1 {
					image {
						width: 150rpx;
						height: 150rpx;
						border-radius: 20rpx;
					}
				}

				.shopList-right-item2 {
					width: 200rpx;
					display: flex;
					flex-direction: column;
					justify-content: space-around;
				}

				.shopList-right-item3 {
					display: flex;
					align-items: flex-end;

					image {
						width: 84rpx;
						height: 48rpx;
					}
				}
			}
		}
	}

	.list {
		padding: 30rpx;
		display: flex;
		flex-wrap: wrap;
		align-items: center;
		justify-content: space-between;

		&-item {
			width: calc(98% / 2);
			margin-bottom: 20rpx;
			border-radius: 20rpx;
			background-color: #fff;
			padding: 20rpx;

			.img {
				width: 100%;
				height: 300rpx;
				margin-bottom: 10rpx;
			}

			&-top {
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin-bottom: 20rpx;

				.title {
					font-size: 30rpx;
				}

				.number {
					display: flex;
					font-size: 30rpx;
					align-items: center;

					view {
						margin: 0 20rpx;
					}

					&-change {
						width: 40rpx;
						height: 40rpx;
					}
				}
			}

			&-bottom {

				display: flex;
				align-items: center;
				justify-content: space-between;

				.money {
					display: flex;
					align-items: center;
					color: #A70A08;
					font-size: 35rpx;
					font-weight: bold;

					&-img {
						width: 50rpx;
						height: 50rpx;
						margin-right: 10rpx;
					}
				}

				.exchange {
					width: 100rpx;
				}
			}
		}
	}
</style>
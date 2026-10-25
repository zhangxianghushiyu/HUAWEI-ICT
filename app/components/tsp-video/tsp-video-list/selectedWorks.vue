<template>
	<view>
		<view class="view">
			<view class="view-box">
				<!-- 		<view class="view-box-top">
					<image class="view-box-top-img" mode="scaleToFill" :src="data.info.titleImg" />
					<view class="view-box-top-info">
						<view class="view-box-top-info-title">{{data.info.title}}</view>
					</view>

					<image @click="tapCollect" v-if="data.info.isCollect" class="view-box-top-btn"
						src="/static/images/change/collectBtn2.png" mode="widthFix"></image>
					<image @click="tapCollect" class="view-box-top-btn" v-else
						src="/static/images/change/collectBtn1.png" mode="widthFix"></image>
				</view>
				<uv-tabs :list="tabList" @click="click" lineColor="#333333"></uv-tabs> -->
				<view class="view-box-info">
					<view v-if="tabIndex == 0" class="list-box">
						<view class="list-box-item" v-for="(item,index) in data.list" :key="index"
							@click="worksFn(index)">
							<view class="list-box-item-text" :class="{active:item.playType == 1}">
								<text v-if="item.videoUrl">{{item.gather}}</text>
								<image v-else src="/static/images/lock.png" mode="widthFix" style="width: 30rpx;" />
								<!-- <image v-if="item.isPrice" class="list-box-item-text-tag"
									src="/static/images/change/payTag.png" mode="widthFix"></image> -->
								<image v-if="item.playType == 1" class="list-box-item-text-playing"
									src="/static/images/change/playing.png" mode="widthFix"></image>
							</view>
						</view>
					</view>
					<view v-if="tabIndex == 1">
						{{data.info.details}}
					</view>
				</view>
			</view>

		</view>
		<!-- 	<view class="bottom-box">
			<button hover-class="button-hover" type="theme" size="long" @click="bayAll">购买全集</button>
		</view> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				data: {},
				tabIndex: 0,
				tabList: [{
						name: '选集',
					},
					{
						name: '简介',
					}
				],
			}
		},
		onLoad() {
			console.log(22111);
		},
		created() {
			console.log(22111);
			console.log(22111);
		},
		onShow() {
			this.data = {
				info: getApp().globalData.vodinfo,
				list: getApp().globalData.seeVodList
			}


			//处理数据
			this.data.list.forEach((it, key) => {
				if (key > this.data.index) it.playType = 0
				else if (key == this.data.index) it.playType = 1
				else it.playType = 2
			})
		},
		methods: {
			//收藏
			tapCollect() {
				this.$api.dramaCollect({
					dramaId: this.data.info.dramaId
				}).then(res => {
					this.data.info.isCollect = this.data.info.isCollect == 1 ? 0 : 1
				}).catch()
			},
			click(item) {
				this.tabIndex = item.index
			},
			worksFn(index) {
				getApp().globalData.seeVodIndex = index
				uni.navigateBack()
			},
			//购买全集
			bayAll(item) {
				const data = {
					name: `${this.data.info.title} 全集`,
					price: this.data.info.price,
					img: this.data.info.titleImg,
					detailId: '',
					id: this.data.info.dramaId,
					text: this.data.info.details,
					type: 2
				}

				uni.setStorageSync('Buy_Drama', data)

				this.$utils.toPage('/pages/index/buy-drama')
			},
		}
	}
</script>

<style>
	page {
		width: 100%;
		height: 100%;
		/* background-color: #f5f5f5; */
		background: red;
	}
</style>

<style lang="scss" scoped>
	.view {
		padding: 30rpx;
		padding-bottom: 150rpx;

		&-box {
			width: 100%;
			padding: 30rpx;
			background-color: #fff;

			&-top {
				display: flex;
				align-items: center;
				margin-bottom: 20rpx;

				&-img {
					width: 150rpx;
					height: 220rpx;
					border-radius: 10rpx;
					margin-right: 20rpx;
				}

				&-info {
					flex: 1;
					padding: 10rpx 0;

					&-title {
						font-size: 30rpx;
						font-weight: bold;
						margin-bottom: 20rpx;
					}
				}

				&-btn {
					width: 150rpx;
				}
			}

			&-info {
				padding: 20rpx;
			}


		}
	}

	.list-box {
		display: flex;
		flex-wrap: wrap;

		&-item {
			width: calc(100% / 5);
			padding: 10rpx;
			font-size: 30rpx;
			text-align: center;

			&-text {
				background-color: #eee;
				border-radius: 10rpx;
				height: 100rpx;
				line-height: 100rpx;
				position: relative;

				&-tag {
					width: 50rpx;
					position: absolute;
					top: 0;
					right: 0;
				}

				&-playing {
					width: 30rpx;
					position: absolute;
					left: 10rpx;
					bottom: 10rpx;
				}

			}

		}
	}

	.active {
		background-color: $uni-sub-theme;
	}
</style>
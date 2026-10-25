<template>
		<view class="">
			<!-- 占位置 -->
			<view class="" style="height: 40rpx;"> </view>
			<uv-tabs :list="tabList" @click="click" lineColor="#E8783B" :scrollable="false"></uv-tabs>
			<!-- 占位置 -->
			<view class="" style="height: 30rpx;"> </view>
			<view class="list" v-if="tabIndex == 0">
				<view class="list-item" v-for="(item,index) in dataList" :key="index"
					@click="toPage('/pages/index/video-see?pageNum=',item)">
					<image class="list-item-img" :src="item.titleImg" mode="scaleToFill">
					</image>
					<view class="list-item-text">{{item.title}}</view>
				</view>
				<!-- 占位置 -->
				<view class="" style="width: 220rpx;height: 1rpx;"></view>
				<view class="" style="width: 220rpx;height: 1rpx;"></view>
			</view>
			<view class="list" v-else>
				<view class="list-item" v-for="(item,index) in dataList" :key="index"
					@click="toPage('/pages/look-at/index?id=' + item.videoId)">
					<image class="list-item-img" :src="item.videoCover" mode="scaleToFill">
					</image>
					<view class="list-item-text">{{item.name}}</view>
				</view>
				<!-- 占位置 -->
				<view class="" style="width: 220rpx;height: 1rpx;"></view>
				<view class="" style="width: 220rpx;height: 1rpx;"></view>
			</view>
			<uv-load-more :status="moreStatus" />
		</view>
	</template>

	<script>
		export default {
			data() {
				return {
					tabIndex: 0,
					tabList: [{
							name: '短剧',
						},
						{
							name: '看看',
						},
					],
					param: {
						pageNum: 1,
						pageSize: 15,
					},
					dataList: [],
				}
			},
			onLoad(option) {
				this.getList(true)
			},
			onReachBottom() {
				if (this.moreStatus != 'nomore')
					this.getList()
			},
			methods: {
				leftClick() {
					uni.navigateBack()
				},
				click(item) {
					this.tabIndex = item.index
					console.log(this.tabIndex);
					this.getList(true)
				},
				toPage(url, item = null) {
					console.log(url);

					if (this.tabIndex == 0) {
						//如果开关未开启
						if (!this.$store.state.isShow) {
							this.$utils.previewImage(0, [item.titleImg])
							return
						}

						//如果需要vip但用户不是vip
						if (item.isVip == 1 && this.userInfo.isVip == 0) {
							this.$utils.toPage('/pages/mine/createVip')
							return
						}

						//获取视频信息
						this.$api.getDetailsList({
							dramaId: item.dramaId
						}).then(res => {
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

							const index = dataList.map(x => x.gather).indexOf(item.watchGather)

							getApp().globalData.seeVodIndex = index;
							getApp().globalData.seeVodList = dataList;
							getApp().globalData.vodinfo = item;
							this.$utils.toPage(url + index)
						})
						return
					}
					this.$utils.toPage(url)
				},
				getList(refresh = false) {
					if (refresh) {
						this.param.pageNum = 1
						this.dataList = []
					}

					this.moreStatus = 'loading'

					if (this.tabIndex == 0)
						this.$api.getMyLikeDramaList(this.param).then(res => {
							this.dataList = this.dataList.concat(res.data.list)
							if (res.data.isLastPage) this.moreStatus = 'nomore'
							else this.moreStatus = 'loadmore'
							this.param.pageNum++
						})
					else
						this.$api.getMyLikeVideoList(this.param).then(res => {
							this.dataList = this.dataList.concat(res.data.list)
							if (res.data.isLastPage) this.moreStatus = 'nomore'
							else this.moreStatus = 'loadmore'
							this.param.pageNum++
						})
				}
			}
		}
	</script>

	<style lang="scss" scoped>
		.list {
			display: flex;
			align-items: center;
			justify-content: space-between;
			flex-wrap: wrap;
			padding: 0 30rpx;

			&-item {
				width: 220rpx;
				margin-bottom: 30rpx;

				&-img {
					height: 312rpx;
					width: 100%;
					border-radius: 30rpx;
					box-sizing: border-box;
					margin-bottom: 16rpx;
					box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
				}

				&-text {
					width: 220rpx;
					font-weight: 700;
					font-size: 26rpx;
					color: #5D3A1A;
					overflow-x: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}
			}
		}
	</style>
<template>
		<view class="">
			<!-- 混合用法 -->
			<uni-swipe-action ref="swipe">
				<uni-swipe-action-item :right-options="options" v-for="(item,index) in dataList" :key="index" autoClose
					@click="onClick(item)">
					<view class="line">
						<view class="line-left" @click="toPage('/pages/index/video-see?pageNum=',item)">
							<image class="cover" :src="item.titleImg" mode="scaleToFill" />
							<text class="title">{{item.title}}</text>
						</view>
						<view class="line-right" @click="changeCollect(index)">
							<image class="star" v-if="item.isCollect == 1" src="@/static/images/star.png"
								mode="widthFix" />
							<image v-else class="star" src="@/static/images/star-gray.png" mode="widthFix" />
						</view>
					</view>
				</uni-swipe-action-item>
			</uni-swipe-action>
			<uv-load-more :status="moreStatus" />
		</view>
	</template>

	<script>
		export default {
			data() {
				return {
					options: [{
						text: '删除',
						style: {
							backgroundColor: '#dd524d'
						}
					}],
					param: {
						pageNum: 1,
						pageSize: 15,
					},
					dataList: [],
					moreStatus: 'loadmore', //loading  nomore
					callBackParam:{}
				}
			},
			onLoad(option) {
				this.getList(true)
			},
			methods: {
				onClick(item) {
					this.callBackParam = item
					this.$utils.showModalAsync("是否删除此条数据", "提示").then(res => {
						this.$api.removeDramaRecord({
							id: this.callBackParam.lookId
						}).then(res => {
							this.$utils.showTip('删除成功', '成功')
							this.$refs.swipe.closeAll()
							this.getList(true)
						})
					})
				},

				changeCollect(index) {
					this.$api.dramaCollect({
						dramaId: this.dataList[index].dramaId
					}).then(res => {
						this.dataList[index].isCollect = !this.dataList[index].isCollect
					})
				},
				toPage(url, item = null) {
					if (url == '/pages/index/video-see?pageNum=') {
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

					this.$api.getRecordList(param).then(res => {
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
		.line {
			display: flex;
			align-items: center;
			padding: 30rpx;
			background-color: #FFFDF9;
			border-radius: 30rpx;
			margin: 16rpx 24rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

			&-left {
				flex: 1;
				display: flex;
				align-items: center;

				.cover {
					width: 150rpx;
					height: 200rpx;
					margin-right: 20rpx;
					border-radius: 16rpx;
				}

				.title {
					flex: 1;
					font-size: 35rpx;
					font-weight: 700;
					color: #5D3A1A;
				}
			}

			&-right {
				.star {
					width: 40rpx;
				}
			}
		}
	</style>
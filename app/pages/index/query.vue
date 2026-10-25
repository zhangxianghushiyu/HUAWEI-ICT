<template>
	<view class="content">
		<view class="view">
			<uni-icons @tap="toBack()" type="back" size="26"></uni-icons>
			<uv-search bgColor='#fff' v-model="searchContent" inputAlign="center" height="35" :showAction="false"
				searchIconColor="#F14835" animation focus @search="search" />
			<span @tap="search()">搜索</span>
		</view>
		<view class="list" v-if="!isShowRecord">
			<view class="list-item" v-for="(item,index) in dataList" :key="index"
				@click="toPage('/pages/index/video-see?pageNum=',item)">
				<view class="item-box">
					<image class="item-img" :src="item.titleImg" mode="scaleToFill"></image>
					<view class="play-count">
						<image class="play-img" src="@/static/images/play.png" mode="scaleToFill"></image>
						{{item.playCount}}播放
					</view>
				</view>
				<view class="item-text">{{item.title}}</view>
				<view style="font-weight: 500;font-size: 22rpx;color: #999999;">
					共{{item.sumEpisodes}}集
				</view>
			</view>
		</view>
		<view class="recordList" v-if="isShowRecord">
			<view class="recordList-title">
				<span>浏览记录</span>
			</view>
			<scroll-view scroll-y="true" style="height: 100%;">
				<view class="recordList-item" v-for="(item,index) in recordsList">
					<view class="recordList-item-left">
						<image :src="item.titleImg" mode=""></image>
					</view>
					<view class="recordList-item-right">
						<span
							style="margin: 20rpx; font-weight: bold;font-size: 28rpx;color: #000000;">{{item.title}}</span>
						<span
							style="margin: 20rpx;font-size: 24rpx;color: #999999;line-height: 44rpx;">{{item.details}}</span>
					</view>
				</view>
			</scroll-view>
		</view>
		<view class="noResult" v-if="isShowNoResult">
			<image src="/static/juhe/1.png" mode=""></image>
			<span>暂无搜索记录~</span>
		</view>
		<uv-load-more v-show="dataList.length>0&&!isShowRecord" :status="moreStatus" />
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
				isShowNoResult: false,
				isShowRecord: true,
				moreStatus: 'loadmore', //loading  nomore
				param: {
					pageNum: 1,
					pageSize: 15,
					isLastPage: false,
					endRow: 0
				},
				dataList: [],
				title: '',
				searchContent: '',
				userInfo: {},
				versionInfo: {},
				recordsList: [],
				queryBody: {
					pageNum: 1,
					pageSize: 10
				}
			}
		},
		onLoad() {
			this.userInfo = uni.getStorageSync('UserInfo')
			this.versionInfo = uni.getStorageSync('Version')
			this.getRecords()
		},
		onReachBottom() {
			if (this.moreStatus != 'nomore')
				this.getList()
		},
		methods: {
			//获取浏览记录
			getRecords() {
				this.$api.getRecordList(this.queryBody).then(res => {
					this.recordsList = res.data.list
				})
			},
			toBack() {
				uni.navigateBack()
			},
			toPage(url, item) {
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

				this.$utils.toPage(url)
			},
			search(e) {
				this.isShowRecord = false
				this.title = this.searchContent
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
					title: this.title
				}

				this.$api.dramaQuery(param).then(res => {
					this.dataList = this.dataList.concat(res.data.list)
					if (res.data.list.length <= 0) {
						this.isShowNoResult = true
					} else {
						this.isShowNoResult = false
					}
					if (res.data.isLastPage) this.moreStatus = 'nomore'
					else this.moreStatus = 'loadmore'
					this.param.pageNum++
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		width: 100vw;
		overflow: hidden;
		background: url('/static/images/bg.png');
		background-size: cover;
	}

	.noResult {
		display: flex;
		margin: auto;
		flex-direction: column;
		align-items: center;
		font-weight: 500;
		font-size: 28rpx;
		color: #8B6B4A;

		image {
			width: 334rpx;
			height: 271rpx;
		}

		span {
			margin-top: 50rpx;
		}
	}

	.recordList {
		display: flex;
		flex-direction: column;
		flex-wrap: wrap;
		padding: 3vw;
		margin-top: 20rpx;
		overflow-y: auto;

		.recordList-title {
			font-weight: 700;
			font-size: 28rpx;
			color: #5D3A1A;
		}

		.recordList-item {
			display: flex;
			margin-top: 30rpx;

			.recordList-item-left {
				image {
					width: 180rpx;
					height: 240rpx;
					border-radius: 18rpx;
				}
			}

			.recordList-item-right {
				display: flex;
				flex-direction: column;
			}
		}
	}

	.view {
		margin-top: 80rpx;
		font-weight: 700;
		font-size: 28rpx;
		color: #5D3A1A;
		display: flex;
		align-items: center;
		justify-content: space-around;

		::v-deep.uv-search {
			display: flex;
			flex: none;
			width: 500rpx;
		}
	}

	.subTab {
		width: calc(100vw - 30rpx);
		display: flex;
		align-items: center;
		color: #5D3A1A;

		.subTab-item {
			border-radius: 50rpx;
			padding: 10rpx 20rpx;
			background-color: #FFFDF9;
			margin: 15rpx 10rpx;
			border: 1px solid rgba(200,130,60,0.1);

			&.active {
				color: #E8783B;
				background-color: rgba(232,120,59,0.08);
			}
		}
	}

	.list {
		display: flex;
		flex-wrap: wrap;
		padding: 2vw;
		margin-top: 100rpx;

		.list-item {
			width: calc(96vw / 3);
			padding: 10rpx;

			.item-box {
				position: relative;
				border-radius: 32rpx;
				box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

				.item-img {
					width: 100%;
					height: 250rpx;
					border-radius: 32rpx;
				}

				.play-count {
					position: absolute;
					bottom: 10rpx;
					right: 10rpx;
					color: #FFFFFF;
					font-size: 20rpx;


					.play-img {
						width: 15rpx;
						height: 15rpx;
					}
				}


			}

			.item-text {
				line-height: 2;
				font-size: 28rpx;
				width: 100%;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
				color: #5D3A1A;
			}
		}
	}
</style>
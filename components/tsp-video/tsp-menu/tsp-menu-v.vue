<template>
	<view class="menuPage">
		<!-- 底部标题 -->
		<view class="footTitle"
			:class="[vodIndex == index?(sliderDrag?'vodMenu-bright1':(moveOpacity?'vodMenu-bright2':'vodMenu-bright0')):'']">
			<view v-if="item.selectWork">
				<view style="display: flex;align-items: center;"><text class="foot-cont">{{item.name}}</text>
					<image v-if="item.isEnd==1" style="width: 76rpx;height: 29rpx; margin-left: 10rpx;"
						src="/static/juhe/6.png" mode="">
					</image>
				</view>
				<!-- <view><text class="foot-name">{{`第${item.gather}集`}}</text></view> -->
				<view><text class="foot-cont">{{item.content}}</text></view>
			</view>
			<view v-else>
				<view><text class="foot-name">{{item.name}}</text></view>
				<view><text class="foot-cont">{{item.content}}</text></view>
			</view>

		</view>
		<!-- 右侧操作栏 -->
		<view class="menuBox">
			<view class="vodMenu"
				:class="[vodIndex == index?(sliderDrag?'vodMenu-bright1':(moveOpacity?'vodMenu-bright2':'vodMenu-bright0')):'']">
				<!-- 头像 -->
				<!-- <view class="menu-avatar">
					<image src="/static/icon/touxiang.jpg" mode="" class="avatar-image" @click="JumpBtn(1)"></image>
					<view class="follow" @click="followBtn(index)" v-if="!item.followReally"
						:class="{followHide:followShow==2}">
						<image src="/static/icon/gou.png" mode="" class="follow-guanzhu guanzhu-gou"
							v-if="followShow == 1 || followShow == 2"></image>
						<image src="/static/icon/guanzhu.png" mode="" class="follow-guanzhu"
							v-if="followShow==null || followShow==0" :class="{followActive:followShow==0}"></image>
					</view>
				</view> -->
				<!-- 点赞 -->
				<view class="fabulous" @click="JumpBtn(2)" style="bottom: 540rpx;">
					<view class="fabulous-image" :class="likeeMeans" @click="fabulousBtn(index,1)"
						style="position: relative;">
						<image src="/static/icon/selectTaoxin.png" mode="" class="fabulous-image" v-if="item.likeShow">
						</image>
						<image src="/static/icon/taoxin.png" mode="" class="fabulous-image" v-else></image>
						<view class="like-pellet" :class="[likeeffect?'like-pellet-active':'']"></view>
					</view>
					<view class="fabulous-num">
						{{item.likeCount > 10000 ? (item.likeCount / 10000 ).toFixed(0)+ '万+'  :item.likeCount}}
					</view>
				</view>
				<!-- 收藏 -->
				<view class="fabulous" @click="JumpBtn(6)" style="bottom:380rpx;">
					<view class="fabulous-image" :class="collecteMeans" @click="fabulousBtn(index,2)"
						style="position: relative;">
						<image src="/static/icon/selectStar.png" mode="" class="fabulous-image" v-if="item.collectShow">
						</image>
						<image src="/static/icon/star.png" mode="" class="fabulous-image" v-else></image>
						<view class="collect-pellet" :class="[collecteffect?'like-pellet-active':'']"></view>
					</view>
					<view class="fabulous-num">
						{{item.collectCount > 10000 ? (item.collectCount / 10000 ).toFixed(0)+ '万+'  :item.collectCount}}
					</view>
				</view>
				<!-- 评论 -->
				<view v-if="isShow" class="fabulous" style="bottom: 240rpx;" @click="JumpBtn(3)">
					<view class="fabulous-image">
						<image src="/static/icon/pinlun.png" mode="" class="fabulous-image"></image>
					</view>
					<view class="fabulous-num">{{discussNum}}</view>
				</view>
				<!-- 分享 -->
				<view v-if="isShow" class="fabulous" @click="JumpBtn(4)" style="bottom: 100rpx;">
					<view class="fabulous-image" @click="fabulousBtn(index,3)">
						<image src="/static/icon/ward.png" mode="" class="fabulous-image"></image>
					</view>
					<view class="fabulous-num">分享</view>
				</view>
				<view v-if="!isShow" class="fabulous" @click="JumpBtn(4)" style="bottom: 200rpx;">
					<view class="fabulous-image" @click="fabulousBtn(index,3)">
						<image src="/static/icon/ward.png" mode="" class="fabulous-image"></image>
					</view>
					<view class="fabulous-num">分享</view>
				</view>

				<!-- 2倍速
				<!-- 			<view class="fabulous" @click="JumpBtn(8)">
					<view class="fabulous-image" :class="speedeMeans" @click="fabulousBtn(index,4)"
						style="position: relative;">
						<image src="/static/icon/selectAccelerate.png" mode="" class="fabulous-image"
							v-if="item.isSpeed">
						</image>
						<image src="/static/icon/accelerate.png" mode="" class="fabulous-image" v-else></image>
						<view class="speed-pellet" :class="[speedeffect?'like-pellet-active':'']"></view>
					</view>
					<view class="fabulous-num">2倍速</view>
				</view> -->

			</view>
		</view>
		<!-- 旋转头像 -->
		<!-- <view class="avatarMenu" v-if="item.rotateImgShow"
			:class="[vodIndex == index?(sliderDrag?'vodMenu-bright1':(moveOpacity?'vodMenu-bright2':'vodMenu-bright0')):'']">
			<view class="rotate-avatar k-paused" :class="[item.vodPaly && palyCartoon?'k-running':'']"
				@click="JumpBtn(5)">
				<image src="/static/icon/touxiang.jpg" mode="" class="rotate-image"></image>
			</view>
		</view> -->
		<!-- 选集 -->
		<!-- 		<view class="avatarMenu" v-if="item.selectWork"
			:class="[vodIndex == index?(sliderDrag?'vodMenu-bright1':(moveOpacity?'vodMenu-bright2':'vodMenu-bright0')):'']">
			<view class="rotate-avatar k-paused" :class="[item.vodPaly && palyCartoon?'k-running':'']"
				@click="JumpBtn(7)">
				<image src="/static/icon/selectedWorks.png" mode="widthFix" class="selected-works-image"></image>
			</view>
		</view> -->
		<!-- 选集 -->
		<view class="chooseNum" @tap="openChoose()" v-if="!isShow">
			<view class="chooseNum-item">
				<image style="width: 26rpx;height: 24rpx;" src="/static/juhe/5.png" mode=""></image>
				<span>观看完整视频·共{{totalNum}}集</span>
			</view>
			<view class="chooseNum-item">
				<uni-icons type="up" size="22" color="#fff"></uni-icons>
			</view>
		</view>
		<view class="chooseVideo" v-if="isShowChooseVideo">
			<view class="chooseVideoTitle">
				<uv-tabs :activeStyle="{
		    color: '#000',
		    fontWeight: 'bold',
		    transform: 'scale(1.05)'
		}" :inactiveStyle="{
		    color: '#000',
		    transform: 'scale(1)'
		}" lineColor="#FE6100" :list="tabList" @click="clicktabs"></uv-tabs>
				<uni-icons @tap="closeChooseVideo()" type="down" size="22" color="#000"></uni-icons>
			</view>
			<view class="chooseVideoBody">
				<view class="videoIntroduce" v-show="tabListSelected==1">
					<view class="videoIntroduce-item1">
						<image style="width: 144rpx;height:190rpx;border-radius: 16rpx;" :src="item.titleImg" mode="">
						</image>
						<view
							style="display: flex;margin-left: 20rpx; flex-direction: column;height: 190rpx;justify-content: space-around">
							<span style="font-weight: bold;font-size: 32rpx;color: #333333;">{{item.name}}</span>
							<span style="font-weight: 500;font-size: 24rpx;color: #999999;">共{{totalNum}}集</span>
						</view>
					</view>
					<view class="videoIntroduce-item2">
						<span>{{item.content}}</span>
					</view>
				</view>
				<view class="list-box" v-show="tabListSelected==0">
					<view class="list-box-item"
						:class="item2.videoUrl?(item.gather==index+1?'list-box-item3':'list-box-item2'):''"
						v-for="(item2,index) in data.list" :key="index" @click="worksFn(item,index)">
						<span>{{index+1}}</span>
					</view>
				</view>
			</view>
		</view>
		<view class="comments" v-if="isShowComments">
			<view class="comments-title">
				<span>1.7万条评论</span>
			</view>
			<image @tap="isShowComments=false" class="comments-close" src="/static/8.png" mode=""></image>
			<view class="comments-body">
				<scroll-view scroll-y="true" class="comments-body-scroll">
					<view class="comments-body-item">
						<view class="comments-body-item-main" v-for="item in commentListData">
							<view class="comments-body-item-main-left">
								<image :src="item.avatar" mode=""></image>
							</view>
							<view class="comments-body-item-main-right">
								<span style="font-size: 28rpx;color: #B1B0B5;">{{item.name}}</span>
								<span style="font-size: 32rpx;color: #000000;">{{item.content}}</span>
								<span style="font-size: 28rpx;color: #B1B0B5;margin-top: 20rpx;">{{item.time}} ·
									{{item.location}}
									<span style="font-size: 28rpx;color: #666666; margin-left: 32rpx;">回复</span></span>
								<view class="moreComments" @tap="openMoreComments()" v-if="!isShowMoreConmments">
									<span>......展开{{item.list.length}}条回复</span>
									<uni-icons type="down" size="22"></uni-icons>
								</view>
								<view class="moreCommentsData" v-for="item2 in item.list" v-if="isShowMoreConmments">
									<view class="moreCommentsData-left">
										<image :src="item2.avatar" mode=""></image>
									</view>
									<view class="moreCommentsData-right">
										<span style="font-size: 28rpx;color: #B1B0B5;">{{item2.name}}</span>
										<span style="font-size: 32rpx;color: #000000;">{{item2.content}}</span>
										<span style="font-size: 28rpx;color: #B1B0B5;margin-top: 20rpx;">{{item2.time}}
											· {{item2.location}}</span>
									</view>
								</view>
							</view>
						</view>
					</view>
				</scroll-view>
			</view>
			<view class="comments-bottom">
				<view class="comments-bottom-body">
					<image src="/static/9.png" mode=""></image>
					<span>说说你的看法</span>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			//下标索引
			index: {
				type: Number,
				default: 0
			},
			//当前swiper的下标
			vodIndex: {
				type: Number,
				default: 0
			},
			//当前播放的视频下标
			vodCurIndex: {
				type: Number,
				default: 0
			},
			//当前视频的整个对象
			item: {
				type: Object,
				default: () => {
					return {}
				}
			},
			//数据总数
			discussNum: {
				type: Number,
				default: 0
			},
			//是否在拖动进度
			sliderDrag: {
				type: Boolean,
				default: false
			},
			//是否透明
			moveOpacity: {
				type: Boolean,
				default: false
			},
			//是否播放旋转头像
			palyCartoon: {
				type: Boolean,
				default: false
			},

		},
		data() {
			return {
				data: {},
				commentsList: [],
				isShowComments: false,
				isShowMoreConmments: false,
				fabulousShow: false,
				followShow: null,
				followOpen: false,
				fabuTimeOut: null,
				totalNum: getApp().globalData.seeVodList.length,
				likeeffect: null, //点赞动效判断
				collecteffect: null, //收藏动效判断
				speedeffect: null, //倍速动效判断
				isShowChooseVideo: false,
				tabList: [{
						name: '选集',
					},
					{
						name: '简介',
					}
				],
				tabListSelected: 0,
				isShow: false,
				commentListData: [{
					content: '中国人有自己的超级碗',
					time: '2025-6-26 15:28:08',
					name: '叶青玉',
					location: '江苏',
					avatar: '/static/avatars/5.jpg',
					list: [{
							content: '中国人有自己的超级碗',
							time: '2025-6-26 15:28:08',
							avatar: '/static/avatars/5.jpg',
							name: '叶青玉',
							location: '江苏',
						},
						{
							content: '中国人有自己的超级碗',
							time: '2025-6-26 15:28:08',
							avatar: '/static/avatars/5.jpg',
							name: '叶青玉',
							location: '江苏',
						}, {
							content: '中国人有自己的超级碗',
							time: '2025-6-26 15:28:08',
							avatar: '/static/avatars/5.jpg',
							name: '叶青玉',
							location: '江苏',
						}
					]
				}]
			}
		},
		computed: {
			likeeMeans() {
				return this.likeeffect == true ? 'fabulous-taoxin' : this.likeeffect == false ? 'cancel-taoxin' : ''
			},
			collecteMeans() {
				return this.collecteffect == true ? 'fabulous-taoxin' : this.collecteffect == false ? 'cancel-taoxin' : ''
			},
			speedeMeans() {
				return this.speedeffect == true ? 'fabulous-taoxin' : this.speedeffect == false ? 'cancel-taoxin' : ''
			},

		},
		created() {
			this.isShow = uni.getStorageSync('lookAt')
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
		onShow() {
			this.isShow = uni.getStorageSync('lookAt')
		},
		methods: {
			//打开更多评论
			openMoreComments() {
				this.isShowMoreConmments = true
			},
			worksFn(item, index) {
				uni.setStorageSync('currentVideo', item.gather)
				getApp().globalData.seeVodIndex = index
				uni.reLaunch({
					url: '/pages/index/video-see'
				})
			},
			openChoose() {
				uni.$emit('touch', true)
				this.isShowChooseVideo = true
			},
			closeChooseVideo() {
				this.isShowChooseVideo = false
				this.tabListSelected = 0
				uni.$emit('touch', false)
			},
			clicktabs(e) {
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
				this.tabListSelected = e.index
			},
			shareAndroidCallBack() {
				this.fabulousBtn(this.index, 3)
			},
			/* 视频点赞收藏动效  key：0双击点赞 1 点赞 2 收藏 3 分享按钮 */
			fabulousBtn(index, key) {
				if (this.item.coverOpacity) return
				let obj = Object.assign({}, this.item)
				if (key == 0) {
					if (!obj.likeShow) {
						obj.likeCount++
						obj.likeShow = true
						this.likeeffect = obj.likeShow
					}
				}

				if (key == 1) {
					obj.likeShow = !obj.likeShow
					if (obj.likeShow) obj.likeCount++
					else obj.likeCount--
					this.likeeffect = obj.likeShow
				}
				if (key == 2) {
					obj.collectShow = !obj.collectShow
					if (obj.collectShow) obj.collectCount++
					else obj.collectCount--
					this.collecteffect = obj.collectShow
				}

				if (key == 3) {
					obj.shareType = obj.shareType == true ? false : true

					window.shareAndroidCallBack = this.shareAndroidCallBack
				}

				if (key == 4) {
					obj.isSpeed = obj.isSpeed == true ? false : true
					this.speedeffect = obj.isSpeed
				}

				this.$emit('handleInfo', obj, key) //点赞成功
			},

			/* 关注动效 */
			followBtn(index) {
				if (this.followOpen) return
				this.followOpen = true
				let obj = Object.assign({}, this.item)
				obj.followReally = true
				this.followShow = 0
				setTimeout(() => {
					this.followShow = 1
					setTimeout(() => {
						this.followShow = 2
						setTimeout(() => {
							this.followOpen = false
							this.followShow = null
							this.$emit('handleInfo', obj) //关注成功
						}, 300)
					}, 100)
				}, 500)
			},
			/* 点击右侧菜单选项 1头像 2点赞 3评论 4转发 5旋转头像 6 收藏 */
			JumpBtn(index) {
				switch (index) {
					case 1:
						console.log('点击头像')
						uni.navigateTo({
							url: '/pages/details/details-v'
						})
						break;
					case 2:
						console.log('点击点赞')
						break;
					case 3:
						console.log('点击3评论')
						this.isShowComments = true
						break;
					case 4:
						console.log('点击4转发')
						break;
					case 5:
						console.log('5旋转头像')
						break;
					case 6:
						console.log('收藏视频')
						break;
					case 7:
						console.log('视频选集')
						uni.navigateTo({
							url: '/pages/index/selectedWorks'
						})
						break;
					case 8:
						console.log('2倍速')
						break;
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.menuPage {
		position: absolute;
		width: 100%;
		height: 100%;
		top: 0;
		left: 0;
	}

	/* 右边菜单栏 */
	.menuBox {
		position: absolute;
		right: 10rpx;
		width: 115rpx;
		height: 100%;
		z-index: 8;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.avatarMenu {
		position: absolute;
		bottom: 20px;
		right: 20rpx;
		width: 115rpx;
		z-index: 9999999;
		display: flex;
		align-items: center;
		flex-direction: column;
	}

	.chooseNum {
		position: absolute;
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding-left: 20rpx;
		padding-right: 20rpx;
		bottom: 20px;
		right: 30rpx;
		left: 30rpx;
		width: 690rpx;
		height: 80rpx;
		background: #333333;
		border-radius: 20rpx;
		z-index: 999999;
	}

	.chooseNum-item {
		display: flex;
		align-items: center;
	}

	.vodMenu {
		margin-top: 100rpx;
		width: 115rpx;
		/* bottom: 400rpx; */
		display: flex;
		align-items: center;
		flex-direction: column;
	}

	.vodMenu-bright0 {
		opacity: 1;
		transition: all 0.3s linear;
	}

	.vodMenu-bright1 {
		opacity: 0;
		transition: all 0.3s linear;
	}

	.vodMenu-bright2 {
		opacity: 0.2;
		transition: all 0.3s linear;
	}

	.menu-avatar {
		position: relative;
		width: 115rpx;
		height: 115rpx;
		border-radius: 50%;
		background-color: #FFFFFF;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.avatar-image {
		width: 100rpx;
		height: 100rpx;
		border-radius: 50%;
	}

	.follow {
		position: absolute;
		bottom: -20rpx;
		width: 40rpx;
		height: 40rpx;
		background-color: #FFFFFF;
		border-radius: 50%;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.follow:active {
		transform: scale(0.7);
		transition: all 0.3s linear;
	}

	.follow-guanzhu {
		width: 40rpx;
		height: 40rpx;
	}

	.guanzhu-gou {
		width: 30rpx;
		height: 30rpx;
	}

	.followActive {
		transform: rotate(180deg);
		transition: all 0.5s linear;
	}

	.followHide {
		transform: scale(0);
		transition: all 0.8s linear;
	}

	.fabulous {
		position: absolute;
		width: 100rpx;
		bottom: 220rpx;
		display: flex;
		align-items: center;
		flex-direction: column;
	}

	.fabulous-image {
		width: 70rpx;
		height: 70rpx;
	}

	.selected-works-image {
		width: 100rpx;
	}

	.fabulous-num {
		width: 100rpx;
		font-size: 28rpx;
		color: #FFFFFF;
		text-align: center;
		margin-top: 10rpx;
	}

	.rotate-avatar {
		width: 95rpx;
		height: 95rpx;
		border-radius: 95rpx;
		/* background-color: #333333; */
		display: flex;
		align-items: center;
		justify-content: center;
		animation: rotateName 5s linear infinite;
	}

	.rotate-image {
		width: 55rpx;
		height: 55rpx;
		border-radius: 55rpx;
	}

	@keyframes rotateName {
		0% {
			transform: rotate(0deg);
		}

		100% {
			transform: rotate(360deg);
		}
	}

	.like-pellet {
		width: 5rpx;
		height: 5rpx;
		background-color: transparent;
		border-radius: 50%;
		/* 绝对定位 居中 */
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%) scale(0);
		/* 设置各个方向的阴影 */
		box-shadow: 0 -45rpx 0 #FF0000,
			0 48rpx 0 #FF0000,
			-50rpx 0 0 #FF0000,
			50rpx 0 0 #FF0000,
			-35rpx -35rpx 0 #FF0000,
			35rpx -35rpx 0 #FF0000,
			35rpx 35rpx 0 #FF0000,
			-35rpx 35rpx 0 #FF0000;
	}

	.collect-pellet {
		width: 5rpx;
		height: 5rpx;
		background-color: transparent;
		border-radius: 50%;
		/* 绝对定位 居中 */
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%) scale(0);
		/* 设置各个方向的阴影 */
		box-shadow: 0 -45rpx 0 #feb432,
			0 48rpx 0 #feb432,
			-50rpx 0 0 #feb432,
			50rpx 0 0 #feb432,
			-35rpx -35rpx 0 #feb432,
			35rpx -35rpx 0 #feb432,
			35rpx 35rpx 0 #feb432,
			-35rpx 35rpx 0 #feb432;
	}

	.speed-pellet {
		width: 5rpx;
		height: 5rpx;
		background-color: transparent;
		border-radius: 50%;
		/* 绝对定位 居中 */
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%) scale(0);
		/* 设置各个方向的阴影 */
		box-shadow: 0 -45rpx 0 #fff,
			0 48rpx 0 #fff,
			-50rpx 0 0 #fff,
			50rpx 0 0 #fff,
			-35rpx -35rpx 0 #fff,
			35rpx -35rpx 0 #fff,
			35rpx 35rpx 0 #fff,
			-35rpx 35rpx 0 #fff;
	}

	.like-pellet-active {
		animation: likeBlink 0.5s ease-in-out forwards;
	}

	/* 小圆点闪出的动画 */
	@keyframes likeBlink {
		0% {
			transform: translate(-50%, -50%) scale(0.5);
			opacity: 0.8;
		}

		50% {
			transform: translate(-50%, -50%) scale(1);
			opacity: 1;
		}

		100% {
			transform: translate(-50%, -50%) scale(1.1);
			opacity: 0;
		}
	}

	/* 底部标题部分 */
	.footTitle {
		position: absolute;
		bottom: 150rpx;
		left: 0;
		z-index: 999999;
		width: 500rpx;
		margin-left: 30rpx;
	}

	.foot-name {
		font-size: 33rpx;
		color: #FFFFFF;
		font-weight: bold;
	}

	.foot-cont {
		font-size: 30rpx;
		color: #FFFFFF;
	}

	.foot-primary {
		margin-top: 10rpx;
		font-size: 25rpx;
		color: #FFFFFF;
	}

	.k-paused {
		animation-play-state: paused;
	}

	.k-running {
		animation-play-state: running;
	}

	.fabulous-taoxin {
		animation: likeName 0.5s linear 1;
	}

	@keyframes likeName {
		0% {
			transform: scale(1);
		}

		15% {
			transform: scale(0);
		}

		30% {
			transform: scale(0.5);
		}

		50% {
			transform: scale(1);
		}

		70% {
			transform: scale(1.2);
		}

		80% {
			transform: scale(1);
		}

		90% {
			transform: scale(1.1);
		}

		100% {
			transform: scale(1);
		}
	}

	.cancel-taoxin {
		animation: likeCancel 0.2s linear 1;
	}

	@keyframes likeCancel {
		0% {
			transform: scale(1);
		}

		50% {
			transform: scale(0.7);
		}

		100% {
			transform: scale(1);
		}
	}

	.chooseVideo {
		position: absolute;
		bottom: 0rpx;
		left: 0rpx;
		right: 0rpx;
		height: 930rpx;
		background: #FFFFFF;
		z-index: 999999999;
		border-radius: 50rpx 50rpx 0rpx 0rpx;
	}

	.chooseVideoTitle {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding-right: 40rpx;
		padding-left: 20rpx;
	}

	.chooseVideoBody {
		position: relative;
	}

	.videoIntroduce {
		position: absolute;
		display: flex;
		flex-direction: column;
		margin: 40rpx 0 0 40rpx;
	}

	.videoIntroduce-item1 {
		display: flex;
		align-items: center;
	}

	.videoIntroduce-item2 {
		margin-top: 40rpx;
		font-size: 28rpx;
		color: #333333;
		line-height: 44rpx;
	}

	.list-box {
		width: 95%;
		height: 800rpx;
		overflow-y: auto;
		margin: auto;
		display: flex;
		flex-wrap: wrap;


		&-item {
			font-size: 30rpx;
			margin: 20rpx;
			text-align: center;
			width: 98rpx;
			height: 98rpx;
			line-height: 98rpx;
			color: #999999;
			background: url('/static/juhe/11.png');
			background-size: cover;
		}

		&-item2 {
			font-size: 30rpx;
			margin: 20rpx;
			text-align: center;
			width: 98rpx;
			height: 98rpx;
			color: #000;
			line-height: 98rpx;
			background: url('/static/juhe/10.png');
			background-size: cover;
		}

		&-item3 {
			font-size: 30rpx;
			margin: 20rpx;
			text-align: center;
			width: 98rpx;
			height: 98rpx;
			color: #000;
			line-height: 98rpx;
			background: url('/static/juhe/9.png');
			background-size: cover;
		}
	}

	.comments {
		width: 750rpx;
		height: 998rpx;
		position: fixed;
		bottom: 0;
		z-index: 9999999;
		background: #FFFFFF;
		border-radius: 20rpx 20rpx 0rpx 0rpx;

		.comments-title {
			margin: 76rpx auto 0 auto;
			text-align: center;
			font-size: 24rpx;
			color: #000000;
		}

		.comments-close {
			width: 52rpx;
			height: 52rpx;
			position: absolute;
			top: 28rpx;
			right: 28rpx;
		}

		.comments-body {
			width: 95%;
			margin: 20rpx auto;
			height: 680rpx;

			.comments-body-item {
				display: flex;
				flex-direction: column;

				.comments-body-item-main {
					display: flex;
					margin-left: 90rpx;
					margin-top: 40rpx;

					.comments-body-item-main-left {
						image {
							width: 96rpx;
							height: 96rpx;
							border-radius: 50%;
						}
					}

					.comments-body-item-main-right {
						margin-left: 24rpx;
						display: flex;
						flex-direction: column;

						.moreComments {
							margin-top: 18rpx;
							display: flex;
							align-items: center;
							font-size: 28rpx;
							color: #666666;

							span {
								margin-right: 10rpx;
							}
						}

						.moreCommentsData {
							display: flex;
							margin-top: 30rpx;

							.moreCommentsData-left {
								image {
									width: 44rpx;
									height: 44rpx;
									border-radius: 50%;
								}
							}

							.moreCommentsData-right {
								margin-left: 24rpx;
								display: flex;
								flex-direction: column;
							}
						}
					}
				}
			}

			.comments-body-scroll {
				height: 100%;
			}
		}

		.comments-bottom {
			width: 750rpx;
			height: 174rpx;
			position: absolute;
			bottom: 0;
			background: #FFFFFF;
			border-radius: 0rpx 0rpx 0rpx 0rpx;
			border: 2rpx solid #EFEFEF;

			.comments-bottom-body {
				width: 700rpx;
				height: 80rpx;
				margin: 47rpx auto;
				display: flex;
				align-items: center;
				background: #F3F3F3;
				border-radius: 20rpx 20rpx 20rpx 20rpx;

				image {
					width: 32rpx;
					height: 32rpx;
					margin-left: 78rpx;
				}

				span {
					font-size: 32rpx;
					color: #999999;
					margin-left: 8rpx;
				}
			}
		}
	}
</style>
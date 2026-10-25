<template>
	<view class="main">
		<view class="title">
			<view class="item"></view>
			<view class="itemRight" v-show="isShowFloatAnimation2">
				<view class="item1-bg">
					<span style="margin-top: 10rpx">
						再通过{{ songsInfoData.redNeedBarrier }}关，
						<span style="color: #e33636">红包比例提升</span>
					</span>
				</view>
			</view>
			<view class="item2">
				<view class="title-bg" v-if="false">
					<!-- <image style="width: 54rpx; height: 63rpx" src="/static/index/wallet.png"></image> -->
					<span style="font-size: 40rpx">
						{{ userInfoData.cash }}
						<span style="font-size: 30rpx">元</span>
					</span>
					<!-- <image @tap="withPage(1)" style="width: 110rpx; height: 66rpx" src="/static/index/4.png"></image> -->
				</view>
				<!-- <image v-if="false" @tap="chooseBar(1)" style="width: 112rpx;margin-left: 50rpx;"
					src="/static/index/9.png" mode="widthFix"></image> -->
				<!-- <image @tap="isShowSetting = true" style="width: 62rpx" src="/static/index/setting.png" mode="widthFix"></image> -->
				<view class="float-number" v-if="false">
					<view class="floating-text2" :style="{
							transform: `translateY(${getedRedMoney.translateY}rpx)`,
							opacity: getedRedMoney.opacity
						}">
						+{{ getedRedMoney.text }}元
					</view>
				</view>
				<view class="broadcast" v-show="broadCastData" v-if="false">
					<image style="width: 50rpx; height: 50rpx; border-radius: 50%" :src="broadCastData.avatar" mode="">
					</image>
					<span style="display: flex; align-items: center; justify-content: center; text-align: center">
						恭喜
						<span
							style="color: #74d84a; display: inline-block; max-width: 150rpx; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
							{{ broadCastData.nickName }}
						</span>
						<span>支付宝提现</span>
						<span style="color: #ede126">{{ broadCastData.money }}元</span>
					</span>
				</view>
			</view>
		</view>
		<!-- 广告条数 -->
		<view class="adsRecord" v-if="false">
			<view class="adsRecord-item">
				<span>今日广告条数:{{ adsNums.num1 }}</span>
			</view>
			<view class="adsRecord-item">
				<span>历史广告条数:{{ adsNums.num2 }}</span>
			</view>
		</view>
		<view class="body">
			<view class="level" @tap="dfwedwq()">
				<span>第{{ songsInfoData.barrier }}关</span>
			</view>
			<view class="progress">
				<view class="progress2"
					:style="{ width: (songsInfoData.oldNumber / songsInfoData.number) * 100 + '%' }"></view>
				<span class="progress-name">{{ songsInfoData.oldNumber }}/{{ songsInfoData.number }}</span>
				<view class="progress-wx">
					<!-- <image style="width: 62rpx" src="/static/juhe/16.png" mode="widthFix"></image> -->
				</view>
			</view>
			<view class="filmBg"></view>
			<view class="filmLeft"></view>
			<!-- <view @tap="answerFinish()" v-if="isShowProp == 1" class="body-item"></view> -->
		</view>
		<view class="bottom">
			<view class="bottom-title">请猜出这首歌的名字</view>
			<view @tap="chooseAnswer(index)" v-for="(item, index) in answersData" class="bottom-item"
				:class="{ itemRight: answerSelected == index }">
				<span>{{ item.name }}</span>
<!-- 				<image v-if="answerSelected == index && isAnswer && item.isRight" class="bottom-item-icon"
					src="/static/index/right.png" mode=""></image> -->
<!-- 				<image v-if="answerSelected == index && isAnswer && !item.isRight" class="bottom-item-icon"
					src="/static/index/wrong.png" mode=""></image> -->
			</view>
			<!-- 中间的微信图标容器 -->
			<view class="center-container">
				<image v-for="(icon, index) in wxIcons" :key="icon.id" :src="icon.src" class="floating-wx-icon"
					:class="{ show: icon.isVisible, flying: icon.isFlying }" :style="{
						left: icon.left + 'rpx',
						top: icon.top + 'rpx',
						transitionDelay: icon.isFlying ? index * 100 + 'ms' : '0ms',
						transform: getTransform(icon),
						opacity: icon.isVisible && !icon.arrived ? 1 : 0
					}"></image>
			</view>
		</view>
		<!-- 排行榜 -->
		<view v-if="isShowRanking" class="ranking-list">
			<view class="ranking-list-bg">
<!-- 				<image @tap="isShowRanking = false" class="ranking-list-close" src="/static/login/close.png"
					mode="widthFix"></image> -->
				<view class="rank-title">
					<span style="padding-left: 20rpx">排行榜</span>
					<span>玩家</span>
					<span>闯关数</span>
					<span style="padding-right: 20rpx">提现</span>
				</view>
				<view class="ranking-body">
					<scroll-view scroll-y="true" style="height: 750rpx">
						<view class="ranking-body-item"
							:class="index == 0 ? 'bodyItem1' : index == 1 ? 'bodyItem2' : index == 2 ? 'bodyItem3' : ''"
							v-for="(item, index) in rankingData.rankingListVOS" :key="index">
							<span v-if="index > 2"
								style="width: 44rpx; font-size: 26rpx; text-align: center; color: #9c7a69; margin-left: 50rpx">{{ index + 1 }}</span>
<!-- 							<image v-if="index == 0" style="width: 44rpx; height: 54rpx; margin-left: 50rpx"
								src="/static/ranking/1.png"></image>
							<image v-if="index == 1" style="width: 44rpx; height: 54rpx; margin-left: 50rpx"
								src="/static/ranking/2.png"></image>
							<image v-if="index == 2" style="width: 44rpx; height: 54rpx; margin-left: 50rpx"
								src="/static/ranking/3.png"></image> -->
							<image style="width: 50rpx; height: 50rpx; margin-left: 60rpx" :src="item.avatar"></image>
							<span
								style="margin-left: 10rpx; width: 100rpx; overflow: hidden; text-overflow: ellipsis; white-space: nowrap">
								{{ item.nickName }}
							</span>
							<span style="position: absolute; left: 350rpx">{{ item.barrier }}</span>
							<span style="position: absolute; right: 40rpx">{{ item.money }}元</span>
						</view>
					</scroll-view>
					<view class="my-ranking">
						<view class="my-ranking-title">我的排名</view>
						<view class="my-ranking-item">
							<span style="font-size: 26rpx; color: #9c7a69">{{ rankingData.rankingMyVO.index }}</span>
							<image style="width: 50rpx; height: 50rpx" :src="userInfoData.wxAvatar"></image>
							<span>{{ rankingData.rankingMyVO.nickName }}</span>
							<span>{{ rankingData.rankingMyVO.barrier }}</span>
							<span>{{ rankingData.rankingMyVO.money }}元</span>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 设置 -->
		<view v-if="isShowSetting" class="setting">
			<view class="setting-bg">
				<view class="setting-close">
					<!-- <image @tap="isShowSetting = false" src="/static/setting/8.png" mode=""></image> -->
				</view>
				<view class="setting-info">
					<image class="setting-info-avatarr" :src="userInfoData.wxAvatar" mode=""></image>
					<view class="setting-info-item">
						<span style="font-weight: bold; font-size: 26rpx">{{ userInfoData.wxNickName }}</span>
						<span>等级：{{ userInfoData.grade }}级</span>
						<span>ID:{{ userInfoData.id }}(已实名认证)</span>
					</view>
					<!-- <image @tap="isShowPop(7)" class="setting-mylevel" src="/static/setting/3.png" mode=""></image> -->
				</view>
				<view class="setting-item1">
					<view>
						<span style="width: 120rpx">音效</span>
						<view @tap="changeMusic()" style="display: flex">
<!-- 							<image class="setting-item1-image1"
								:src="settingSwitch.music ? '/static/setting/2.png' : '/static/setting/1.png'"
								mode="widthFix"></image>
							<image :class="settingSwitch.music ? 'setting-item1-image-on' : 'setting-item1-image-off'"
								class="setting-item1-image2" src="/static/setting/4.png" mode="widthFix"></image> -->
						</view>
					</view>
					<view>
						<span style="width: 160rpx">个性化</span>
						<view @tap="settingSwitch.personal = !settingSwitch.personal">
<!-- 							<image class="setting-item1-image3"
								:src="settingSwitch.personal ? '/static/setting/2.png' : '/static/setting/1.png'"
								mode="widthFix"></image>
							<image
								:class="settingSwitch.personal ? 'setting-item1-image-on2' : 'setting-item1-image-off2'"
								class="setting-item1-image4" src="/static/setting/5.png" mode="widthFix"></image> -->
						</view>
					</view>
				</view>
				<view class="setting-item2">
					<view v-for="(item, index) in settingMenu" :key="index">
						<span @tap="isShowPop(index)">{{ item }}</span>
					</view>
				</view>
			</view>
		</view>
		<!-- 我的等级 -->
		<view v-if="isShowMyLevel" class="myLevel">
			<view class="myLevel-bg">
<!-- 				<image @tap="isShowPop(8)" class="myLevel-bg-close" src="/static/mylevel/1.png" mode=""></image> -->
				<view class="myLevel-bg-main">
					<span style="margin-top: 30rpx">我的等级：{{ userInfoData.grade }}级</span>
					<span>闯关越多，升级越快</span>
					<progress style="width: 200rpx; margin-top: 20rpx" backgroundColor="#9DA7B0"
						:percent="(userInfoData.oldBarrier / userInfoData.needBarrier) * 100" activeColor="#FFC728"
						stroke-width="12" />
				</view>
				<view @tap="isShowPop(8)" class="myLevel-bg-btn"></view>
			</view>
		</view>
		<!-- 关于我们 -->
		<view v-if="isShowAboutus" class="aboutus">
			<view class="aboutus-bg">
<!-- 				<image @tap="isShowPop(6)" class="aboutus-bg-close" src="/static/mylevel/1.png" mode=""></image> -->
				<image style="width: 156rpx; margin-top: 160rpx" :src="aboutUsData.url" mode="widthFix"></image>
				<span style="font-weight: bold; font-size: 30rpx">{{ aboutUsData.name }}</span>
				<span>当前渠道：{{ aboutUsData.channel }}</span>
				<span>版本号：{{ aboutUsData.version }}</span>
				<span>{{ aboutUsData.record }}</span>
				<view class="aboutus-bottom">
					<span>{{ aboutUsData.content }}</span>
				</view>
			</view>
		</view>
		<!-- 用户协议和隐私政策 -->
		<view v-if="isAgree" class="pop">
			<view class="popBg" :class="showIndex == 0 ? 'popBg1' : showIndex == 2 ? 'popBg2' : ''">
				<view @tap="isShowPop(9)" class="closeBtn"></view>
				<view class="popContent">
					<view v-html="agreementData.content"></view>
				</view>
			</view>
		</view>
		<!-- 帮助中心 -->
		<view v-if="isShowHelp" class="helpCenter">
			<view class="helpBg">
<!-- 				<image @tap="isShowPop(10)" class="closeBtn" src="/static/mylevel/1.png" mode=""></image>
				<image class="help-title" src="/static/help/3.png" mode=""></image> -->
				<view class="help-menu">
					<view @tap="chooseHelp(index)" class="help-menu-item"
						:class="{ helpSelected: helpSelected == index }" v-for="(item, index) in helpMenus"
						:key="index">
						<span>{{ item }}</span>
					</view>
				</view>
				<scroll-view scroll-y="true" style="height: 480rpx">
					<view class="help-questions" v-for="(item, index) in helpQuestions" :key="index">
						<view class="help-questions-title">
							<span>{{ item.title }}</span>
						</view>
						<uni-collapse class="help-questions-content">
							<uni-collapse-item :show-animation="true" :title="item1.problem"
								v-for="(item1, index1) in item.data" :key="index1">
								<text>{{ item1.answer }}</text>
							</uni-collapse-item>
						</uni-collapse>
					</view>
				</scroll-view>
			</view>
		</view>
		<!-- 幸运奖励 -->
		<view v-if="isShowReward" class="reward">
			<view class="redWalletPop-title">
				<span>恭喜您获得红包</span>
			</view>
			<view class="redWalletPop-title2">
				<span>恭喜发财 大吉大利</span>
			</view>
			<view @tap="noMoney()" class="redWalletPop-bg"></view>
			<view @tap="closeReward()" class="redWalletPop-close"></view>
		</view>
		<!-- 通关奖励 -->
		<view v-if="isShowPass" class="reward">
			<view class="redWalletPop-title">
				<span>恭喜您获得红包</span>
			</view>
			<view class="redWalletPop-title2">
				<span>恭喜发财 大吉大利</span>
			</view>
			<view @tap="noMoney()" class="redWalletPop-bg"></view>
			<view @tap="closeReward()" class="redWalletPop-close"></view>
		</view>
		<!-- 红包奖励 -->
		<view v-if="isShowRedWalletReward" class="redWalletRewardPop">
			<view class="redWalletRewardPop-bg">
				<view class="redWalletRewardPop-body">
					<view class="body-title">
						<span>恭喜您获得红包</span>
					</view>
					<view class="body-money">
						<span>
							{{ getedRedMoney2 }}
							<span style="font-size: 50rpx">金币</span>
						</span>
					</view>
				</view>
			</view>
			<view @tap="receiveMoney()" class="redWalletRewardPop-close"></view>
		</view>
		<!-- 前1-2关通关引导 -->
		<view v-if="isShowGuidePass" class="guidePass">
			<view @tap="toWith()" class="guidePass-bg">
				<view class="guidePass-money">
					<span>
						{{ answerResponse.needWithdrawalMoney || userInfoData.needWithdrawalMoney }}
						<span style="font-size: 40rpx">元</span>
					</span>
				</view>
			</view>
		</view>
		<!-- 指引 -->
		<view v-if="isShowGuide" class="guide">
			<view @tap="nextGuide(0)" v-if="guideIndex == 0" class="guide1">
<!-- 				<image style="width: 750rpx; margin-top: 650rpx" src="/static/guide/3.png" mode="widthFix"></image> -->
				<!-- <image style="width: 470rpx; margin: 180rpx auto" src="/static/guide/1.png" mode="widthFix"></image> -->
				<!-- <image style="width: 184rpx; position: absolute; left: 480rpx; top: 1000rpx" src="/static/guide/2.png" -->
					mode="widthFix"></image>
			</view>
			<view @tap="nextGuide(1)" v-if="guideIndex == 1" class="guide1">
				<view class="guide1-bottom">
					<view class="guide1-bottom-item1">
						<span>请猜出这首歌的名字</span>
					</view>
					<view class="guide1-bottom-item2">
						<span>寂寞边界</span>
					</view>
					<view class="guide1-bottom-item3">
						<span>飞云之下</span>
					</view>
				</view>
				<!-- <image style="width: 184rpx; position: absolute; left: 480rpx; bottom: 130rpx" src="/static/guide/2.png" -->
					mode="widthFix"></image>
			</view>
			<view @tap="nextGuide(2)" v-if="guideIndex == 2" class="guide1">
				<view class="guide1-bottom2"></view>
			</view>
		</view>
		<view class="showLoading" v-if="isShowLoading">
			<uv-loading-icon :vertical="true" color="#fff" text="加载中" size="36"></uv-loading-icon>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				isShowLoading: false,
				userInfoData: {},
				isClick: false,
				broadCastDataList: {},
				broadCastData: '',
				broadCastIndex: 0,
				broadCastTimer: null,
				agreementType: -1,
				agreementData: '',
				songsInfoData: {},
				isAnswer: false,
				answersData: [],
				helpData: '',
				isDouble: false,
				aboutUsData: {},
				luckyReward: {},
				answerResponse: {},
				passLevelData: {},
				isShowProp: -1,
				isShowRedWalletReward: false,
				rewardTimer: null,
				settingSwitch: {
					music: false,
					personal: false
				},
				isShowNumAdd: false,
				isShowGuidePass: false,
				answerSelected: -1,
				isShowPass: false, //是否显示通关奖励
				isShowRanking: false, //是否显示排行榜
				isShowSetting: false, //是否显示设置
				isShowMyLevel: false, //是否显示我的等级
				isShowAboutus: false, //是否显示关于我们
				isAgree: false, //是否显示用户协议或隐私政策
				showIndex: -1,
				isShowFloatAnimation1: false,
				isShowFloatAnimation2: false,
				isShowFloatAnimationTimer: null,
				isAnswered: false,
				isShowGuide: false, //是否显示指引
				guideIndex: 0, //指引索引
				isShowHelp: false, //是否显示帮助中心
				helpSelected: 0, //帮助中心选择值
				isShowReward: false, //是否显示幸运奖励
				rankingData: {},
				adsNums: {
					num1: 0,
					num2: 0
				},
				getedWxMoney: {
					text: '',
					translateY: 0,
					opacity: 0
				},
				getedWxMoney2: null,
				getedRedMoney: {
					text: '',
					translateY: 0,
					opacity: 0
				},
				getedRedMoney2: null,
				wxIcons: [],
				isAnimating: false,
				areaWidth: 300,
				areaHeight: 300,
				iconSize: 40,
				iconCount: 10,
				idCounter: 0,
				// 散落的固定位置数组
				fixedPositions: [{
						x: 50,
						y: 65
					},
					{
						x: 180,
						y: 45
					},
					{
						x: 240,
						y: 120
					},
					{
						x: 60,
						y: 180
					},
					{
						x: 150,
						y: 90
					},
					{
						x: 200,
						y: 200
					},
					{
						x: 90,
						y: 240
					},
					{
						x: 120,
						y: 150
					},
					{
						x: 220,
						y: 160
					},
					{
						x: 30,
						y: 120
					}
				],
				isChoosed: false,
				watchAdnums: 0,
				chapingTimer: null,
				cahpingSecond: 30,
				isWatching: false,
				helpMenus: ['充值问题', '提现问题', '登录问题', '账号问题', '产品问题', '其他问题'],
				settingMenu: ['用户协议', '关于我们', '隐私政策', '帮助中心', '退出登录', '账号注销'],
				helpQuestions: [],
			};
		},
		onLoad() {
			const pages = window.location.href;
			uni.setStorageSync('isSuccess', false)
			window.goldCoinAndroidCallBack = this.goldCoinAndroidCallBack;
			window.adsCloseBack = this.adsCloseBack;
			window.onInterstitialAdClose = this.onInterstitialAdClose;
			window.musicCallBack = this.musicCallBack;
			let that = this
			uni.addInterceptor('switchTab', {
				invoke(e) {
					if (pages.includes('pages/game/index')) {
						if (uni.getStorageSync('isSuccess')) {
							uni.setStorageSync('toPage', null)
							return true
						} else {
							uni.setStorageSync('toPage', e.url)
							that.isShowLoading = true
							return false
						}
					}
				},
			})
		},
		mounted() {
			this.initIcons();
			// rh_android.showBanner();
			// setTimeout(() => {
			// 	rh_android.showInterstitialAd();
			// }, 10000);
		},
		onShow() {
			window.playMusicCallback = this.playMusicCallback
			uni.setStorageSync('wxChatPage', false);
			this.getUserInfo();
			this.getSongsInfo();
			if (uni.getStorageSync('Token')) {
				rh_android.showTopBanner(getApp().globalData.isFirstTopBanner, '#ffffff')
				window.onTopBannerShow = this.onTopBannerShow
			}

			// this.getAdsNums();
			// tabbar页面跳转前进行拦截
		},
		onHide() {
			uni.setStorageSync('wxChatPage', true);
			rh_android.hideTopBanner()
			if (uni.getStorageSync('isSuccess')) {
				// rh_android.pauseMusic();
				if (typeof rh_android !== 'undefined' && rh_android.pauseMusic) {
				        rh_android.pauseMusic();
				    }

			}
		},
		methods: {
			musicCallBack(id) {
				uni.setStorageSync('adsId', id);
			},
			playMusicCallback() {
				uni.setStorageSync('isSuccess', true)
				this.isShowLoading = false
				if (uni.getStorageSync('toPage')) {
					uni.switchTab({
						url: uni.getStorageSync('toPage')
					})
				}
				console.log(`音乐播放回调${this.isSuccess}`);
			},
			onTopBannerShow() {
				getApp().globalData.isFirstTopBanner = false
			},
			getAdsNums() {
				this.$api.queryAsdNums().then((res) => {
					this.adsNums.num1 = res.data.todayCount;
					this.adsNums.num2 = res.data.historyCount;
				});
			},
			onInterstitialAdClose() {
				if (this.chapingTimer) {
					clearInterval(this.chapingTimer);
					this.chapingTimer = null;
					this.chapingTimer = setInterval(() => {
						this.cahpingSecond--;
						if (this.cahpingSecond <= 0) {
							this.cahpingSecond = 30;
							if (!this.isWatching) {
								rh_android.showInterstitialAd();
								clearInterval(this.chapingTimer);
								this.chapingTimer = null;
							}
						}
					}, 1000);
				} else {
					this.chapingTimer = setInterval(() => {
						this.cahpingSecond--;
						if (this.cahpingSecond <= 0) {
							this.cahpingSecond = 30;
							if (!this.isWatching) {
								rh_android.showInterstitialAd();
								clearInterval(this.chapingTimer);
								this.chapingTimer = null;
							}
						}
					}, 1000);
				}
			},
			//获取用户信息
			getUserInfo() {
				// uni.setStorageSync(
				// 	'Token',
				// 	'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcmVhdGVfdGltZSI6MTc0MDk5MDg1NzQxNCwiZXhwaXJlX3RpbWUiOjE3NDM1ODI4NTc0MTQsImlkIjoxMDEwMDAwMDA4Mn0.79DvR2e0tq2gGrou_FrSqUgE6z3sRjGgKRTTym25dCc'
				// );
				this.$api.getInfo().then((res) => {
					this.userInfoData = res.data;
					uni.setStorageSync('userInfo', res.data);
					if (this.userInfoData.needWithdrawalBarrier) {
						// this.isShowGuidePass = true;
					}
				});
			},
			// 初始化微信图标
			initIcons() {
				this.wxIcons = this.fixedPositions.slice(0, this.iconCount).map((pos, index) => {
					this.idCounter++;
					return {
						id: this.idCounter,
						src: '/static/index/wallet.png',
						left: pos.x,
						top: pos.y,
						isVisible: false,
						isFlying: false,
						arrived: false
					};
				});
			},
			getTransform(icon) {
				if (!icon.isFlying) return 'translate(0, 0)';
				const containerRect = document.querySelector('.center-container').getBoundingClientRect();
				let targetX = 120 - (containerRect.left + icon.left);
				const targetY = -300 - (containerRect.top + icon.top);
				return `translate(${targetX}rpx, ${targetY}rpx)`;
			},
			// 微信图标飞行动画
			async wxFlyAnimation(wxMoney, redMoney) {
				if (this.isAnimating) return;
				this.isAnimating = true;
				this.initIcons();
				this.wxIcons.forEach((icon) => {
					icon.isVisible = true;
				});
				await this.sleep(1000);
				for (let i = 0; i < this.wxIcons.length; i++) {
					const icon = this.wxIcons[i];
					icon.isFlying = true;
					icon.isVisible = false;
					// 设置到达状态的延迟
					setTimeout(() => {
						icon.arrived = true;
						let arrivedCount = this.wxIcons.filter((res) => res.arrived == true).length;
						if (arrivedCount > 9) {
							this.startAnimation(wxMoney, redMoney);
						}
					}, i * 50 + 500); // 根据飞行时间调整延迟
				}
				await this.sleep(this.wxIcons.length * 100);
				this.isAnimating = false;
			},
			receiveMoney() {
				// this.wxFlyAnimation(0, this.getedRedMoney2);
				this.isShowRedWalletReward = false;
			},
			//动画延迟
			sleep(ms) {
				return new Promise((resolve) => setTimeout(resolve, ms)).catch((err) => {
					console.log(err);
				});
			},
			//文字浮动动画
			startAnimation(wxMoney, redMoney) {
				// 动画参数
				const duration = 2000; // 持续时间
				const distance = 100; // 上升距离
				const duration2 = 2000; // 持续时间
				const distance2 = 100; // 上升距离
				let newText;
				let newText2;
				// 开始动画
				const startTime = Date.now();
				if (wxMoney > 0) {
					newText = {
						text: wxMoney,
						translateY: 0,
						opacity: 1
					};
					this.getedWxMoney = newText;
					const animate = () => {
						const currentTime = Date.now();
						const progress = (currentTime - startTime) / duration;
						if (progress < 1) {
							// 更新位置和透明度
							this.getedWxMoney.translateY = -distance * progress;
							this.getedWxMoney.opacity = 1 - progress;
							requestAnimationFrame(animate);
						} else {
							// 动画结束，移除这个文字
							this.getedWxMoney.text = '';
						}
					};
					requestAnimationFrame(animate);
				}
				if (redMoney > 0) {
					newText2 = {
						text: redMoney,
						translateY: 0,
						opacity: 1
					};
					this.getedRedMoney = newText2;
					const animate2 = () => {
						const currentTime2 = Date.now();
						const progress2 = (currentTime2 - startTime) / duration2;
						if (progress2 < 1) {
							// 更新位置和透明度
							this.getedRedMoney.translateY = -distance2 * progress2;
							this.getedRedMoney.opacity = 1 - progress2;
							requestAnimationFrame(animate2);
						} else {
							// 动画结束，移除这个文字
							this.getedRedMoney.text = '';
						}
					};
					requestAnimationFrame(animate2);
				}
			},
			//音乐开关切换
			changeMusic() {
				this.settingSwitch.music = !this.settingSwitch.music;
				if (this.settingSwitch.music) {
					// rh_android.pauseMusic();
					if (typeof rh_android !== 'undefined' && rh_android.pauseMusic) {
					        rh_android.pauseMusic();
					    }

				} else {
					// rh_android.continueMusic();
					if (typeof rh_android !== 'undefined' && rh_android.pauseMusic) {
					        rh_android.pauseMusic();
					    }

				}
			},
			closeReward() {
				this.isShowPass = false;
				this.isShowReward = false;
				this.getSongsInfo();
			},
			//获取广播信息
			getBroadcast() {
				this.$api.broadcast().then((res) => {
					this.broadCastDataList = res.data;
					if (this.broadCastTimer) {
						clearInterval(this.broadCastTimer);
					} else {
						this.broadCastTimer = setInterval(() => {
							this.broadCastData = this.broadCastDataList[this.broadCastIndex];
							if (this.broadCastIndex <= this.broadCastDataList.length - 2) {
								this.broadCastIndex += 1;
							} else {
								this.broadCastIndex = 0;
							}
						}, 8000);
					}
				});
			},
			//获取歌曲信息
			getSongsInfo() {
				this.$api.songsInfo().then((res) => {
					this.isShowReward = false;
					this.answersData = [];
					this.answerResponse = {};
					this.isAnswer = false;
					this.answerSelected = -1;
					this.songsInfoData = res.data;
					this.isShowFloatAnimation1 = false;
					this.isShowFloatAnimation2 = false;
					if (!uni.getStorageSync('barrier')) {
						uni.setStorageSync('barrier', res.data.barrier);
					} else {
						if (res.data.barrier > uni.getStorageSync('barrier')) {
							this.watchAdnums = 0;
							uni.setStorageSync('barrier', res.data.barrier);
						}
					}
					if (this.isShowFloatAnimationTimer) {
						clearInterval(this.isShowFloatAnimationTimer);
						this.isShowFloatAnimationTimer = null;
						let time = 0;
						this.isShowFloatAnimationTimer = setInterval(() => {
							time++;
							if (time % 2 == 0) {
								if (res.data.wxNeedMoney || res.data.wxNeedBarrier) {
									if (res.data.redNeedBarrier) {
										if (time % 4 == 0) {
											this.isShowFloatAnimation1 = false;
											this.isShowFloatAnimation2 = true;
										} else {
											this.isShowFloatAnimation1 = true;
											this.isShowFloatAnimation2 = false;
										}
										return;
									} else {
										this.isShowFloatAnimation1 = true;
										return;
									}
								} else {
									if (res.data.redNeedBarrier) {
										this.isShowFloatAnimation2 = true;
									}
								}
							}
						}, 1000);
					} else {
						let time = 0;
						this.isShowFloatAnimationTimer = setInterval(() => {
							time++;
							if (time % 2 == 0) {
								if (res.data.wxNeedMoney || res.data.wxNeedBarrier) {
									if (res.data.redNeedBarrier) {
										if (time % 4 == 0) {
											this.isShowFloatAnimation1 = false;
											this.isShowFloatAnimation2 = true;
										} else {
											this.isShowFloatAnimation1 = true;
											this.isShowFloatAnimation2 = false;
										}
										return;
									} else {
										this.isShowFloatAnimation1 = true;
										return;
									}
								} else {
									if (res.data.redNeedBarrier) {
										this.isShowFloatAnimation2 = true;
									}
								}
							}
						}, 1000);
					}
					uni.setStorageSync('isSuccess', false)
					rh_android.playMusic(this.songsInfoData.url);
					uni.setStorageSync('musicUrl', this.songsInfoData.url);
					this.answersData.push({
						name: res.data.option1,
						isRight: false
					});
					this.answersData.push({
						name: res.data.option2,
						isRight: false
					});
					if (res.data.barrier > 2) {
						this.isShowGuide = false;
					}
					if (res.data.barrier == 1 && res.data.oldNumber <= 0 && !uni.getStorageSync('answered')) {
						// this.isShowGuide = true;
					}
				});
			},
			//不要钱
			noMoney() {
				if (!this.isClick) {
					this.isClick = true;
					//通关奖励
					if (this.isShowPass) {
						this.watchAds(1);
						this.isClick = false;
					}
					//幸运奖励
					if (this.isShowReward) {
						this.watchAds(2);
						this.isClick = false;
					}
				}
			},
			//选择答案
			chooseAnswer(index) {
				if (!this.isChoosed) {
					this.isChoosed = true;
					setTimeout(() => {
						this.isChoosed = false;
					}, 2000);
					if (Object.keys(this.answerResponse).length > 0) {
						return;
					}
					const answer = index == 0 ? this.songsInfoData.option1 : this.songsInfoData.option2;
					this.$api
						.songsAnswer({
							answer: answer,
							barrier: this.songsInfoData.barrier,
							id: this.songsInfoData.id
						})
						.then((res) => {
							this.isAnswer = true;
							uni.setStorageSync('answered', 'true');
							this.answerSelected = index;
							this.answerResponse = res.data;
							if (this.rewardTimer) {
								clearTimeout(this.rewardTimer);
								this.rewardTimer = null;
							}
							if (res.data.barrier > 2) {
								this.isShowProp = res.data.resultCode;
							}
							this.rewardTimer = setTimeout(() => {
								this.isShowProp = -1;
							}, 10000);
							if (this.answersData[0].name == answer) {
								this.answersData[0].isRight = res.data.resultCode == 1 ? true : false;
								this.answersData[1].isRight = !this.answersData[0].isRight;
							} else {
								this.answersData[1].isRight = res.data.resultCode == 1 ? true : false;
								this.answersData[0].isRight = !this.answersData[1].isRight;
							}
							if (res.data.rewardType == 3) {
								this.getedWxMoney2 = res.data.getWxMoney;
								this.getedRedMoney2 = res.data.getRedMoney;
								if (res.data.getRedMoney > 0) {
									this.wxFlyAnimation(0, res.data.getRedMoney);
								}
							}
							if (res.data.resultCode == 0) {
								this.$api
									.playSound({
										type: 5
									})
									.then((res) => {
										rh_android.playVoice(res.data);
									});
							} else {
								this.$api
									.playSound({
										type: 4
									})
									.then((res) => {
										rh_android.playVoice(res.data);
									});
							}
							//幸运奖励
							if (res.data.rewardType == 2) {
								this.luckyReward = res.data;
								this.isShowReward = true;
								//获取语音信息
								this.$api
									.playSound({
										type: 2
									})
									.then((res) => {
										rh_android.playVoice(res.data);
									});
							}
							// 通关奖励
							if (res.data.rewardType == 1 && res.data.barrier > 2) {
								this.passLevelData = res.data;
								this.isShowPass = true;
							}
							this.getUserInfo();
							if (this.isShowReward || this.isShowPass) {
								return;
							} else {
								setTimeout(() => {
									this.getSongsInfo();
								}, 1000);
							}
						});
				}
			},
			//引导去提现
			toWith() {
				uni.navigateTo({
					url: `/pages/index/withdrawal?money=${this.userInfoData.needWithdrawalMoney}&level=${this.userInfoData.needWithdrawalBarrier}`
				});
			},
			//领取通关奖励
			getPassReward() {
				this.$api
					.passLevel({
						barrier: this.passLevelData.barrier
					})
					.then((res) => {
						this.getedWxMoney2 = null;
						this.getedRedMoney2 = this.passLevelData.wxMin;
						this.wxFlyAnimation(0, this.getedRedMoney2);
						this.isShowPass = false;
						this.isShowReward = false;
						this.answersData = [];
						this.answerResponse = {};
						this.isAnswer = false;
						this.getSongsInfo();
						uni.showToast({
							title: '领取成功',
							icon: 'none',
							duration: 2000
						});
					});
			},
			//获取协议信息
			getAgreement() {
				this.$api
					.agreement({
						type: this.agreementType
					})
					.then((res) => {
						this.agreementData = res.data;
					});
			},
			//帮助中心信息
			getHelpCenter() {
				this.$api.helpCenter().then((res) => {
					this.helpData = res.data;
					this.helpQuestions[0] = {
						title: '充值问题',
						data: res.data.recharge
					};
				});
			},
			//排行榜信息
			getRankingList() {
				this.$api.rankingList().then((res) => {
					this.rankingData = res.data;
					this.isShowRanking = true;
				});
			},
			//打开排行榜
			openRanking() {
				this.getRankingList();
			},
			//提现/红包页面
			withPage(index) {
				if (index == 0) {
					uni.navigateTo({
						url: '/pages/index/withdrawal'
					});
				} else {
					uni.navigateTo({
						url: '/pages/index/redWallet'
					});
				}
			},
			//双倍奖励
			answerFinish() {
				// this.isShowReward = true;
				// this.isDouble = true;
			},
			chooseHelp(index) {
				this.helpSelected = index;
				const title = index == 0 ? '充值问题' : index == 1 ? '提现问题' : index == 2 ? '登录问题' : index == 3 ? '账号问题' :
					index == 4 ? '产品问题' : '其他问题';
				this.helpQuestions[0] = {
					title: title,
					data: index == 0 ?
						this.helpData.recharge : index == 1 ?
						this.helpData.withdrawal : index == 2 ?
						this.helpData.login : index == 3 ?
						this.helpData.account : index == 4 ?
						this.helpData.product : this.helpData.other
				};
			},
			chooseBar(index) {
				if (index == 1) {
					// rh_android.pauseMusic();
					clearInterval(this.chapingTimer);
					this.chapingTimer = null;
					uni.navigateTo({
						url: '/pages/game/weChat'
					});
				}
			},
			nextGuide(index) {
				if (index < 2) {
					this.guideIndex = index + 1;
				} else {
					this.isShowGuide = false;
				}
			},
			//观看广告
			watchAds(index) {
				this.$api.checkAdsNums().then((res) => {
					if (res.msg == 'success') {
						this.isWatching = true;
						// rh_android.pauseMusic();
						if (typeof rh_android !== 'undefined' && rh_android.pauseMusic) {
						        rh_android.pauseMusic();
						    }

						rh_android.showAd(this.userInfoData.id.toString(), JSON.stringify({
							type: 2,
							value: index
						}), 4);
					} else {
						uni.showToast({
							title: '广告准备中',
							icon: 'none',
							duration: 2000
						});
					}
				});
			},
			//广告关闭
			adsCloseBack() {
				this.isWatching = false;
				if (uni.getStorageSync('adsId')) {
					this.$api
						.adResult({
							transId: uni.getStorageSync('adsId')
						})
						.then((res) => {
							// this.getAdsNums();
							uni.setStorageSync('adsId', null);
							this.getedRedMoney2 = res.data.money;
							this.getUserInfo();
							this.isShowPass = false;
							this.isDouble = false;
							this.isShowReward = false;
							this.answersData = [];
							this.answerResponse = {};
							this.isAnswer = false;
							this.getSongsInfo();
							this.isShowRedWalletReward = true;
						});
				} else {
					this.getUserInfo();
					this.isDouble = false;
					this.isShowPass = false;
					this.isShowReward = false;
					this.answersData = [];
					this.answerResponse = {};
					this.isAnswer = false;
					this.getSongsInfo();
				}
			},
			//关于我们
			getAboutUs() {
				this.$api.aboutUs().then((res) => {
					this.aboutUsData = res.data;
				});
			},
			//显示弹窗
			isShowPop(index) {
				this.isShowSetting = false;
				switch (index) {
					//用户协议
					case 0:
						this.isAgree = true;
						this.showIndex = 0;
						this.agreementType = 1;
						this.getAgreement();
						break;
						//关于我们
					case 1:
						this.getAboutUs();
						this.isShowAboutus = true;
						break;
						//隐私政策
					case 2:
						this.isAgree = true;
						this.showIndex = 2;
						this.agreementType = 2;
						this.getAgreement();
						break;
						//帮助中心
					case 3:
						this.isShowHelp = true;
						this.getHelpCenter();
						break;
						//退出登录
					case 4:
						uni.showModal({
							title: '温馨提示',
							content: '确定退出登录？',
							showCancel: true,
							success: (res) => {
								if (res.confirm) {
									// rh_android.pauseMusic();
									if (typeof rh_android !== 'undefined' && rh_android.pauseMusic) {
									        rh_android.pauseMusic();
									    }

									rh_android.hideBanner();
									clearInterval(this.chapingTimer);
									this.chapingTimer = null;
									uni.clearStorageSync();
									uni.reLaunch({
										url: '/pages/login/login'
									});
								} else {
									this.isShowSetting = true;
								}
							}
						});
						break;
						//账号注销
					case 5:
						uni.showModal({
							title: '温馨提示',
							content: '确定注销账号吗？',
							showCancel: true,
							success: (res) => {
								if (res.confirm) {
									this.$api.cancelAccount().then((res) => {
										rh_android.pauseMusic();
										rh_android.hideBanner();
										clearInterval(this.chapingTimer);
										this.chapingTimer = null;
										uni.clearStorageSync();
										uni.reLaunch({
											url: '/pages/login/login'
										});
									});
								} else {
									this.isShowSetting = true;
								}
							}
						});
						break;
						//关闭关于我们
					case 6:
						this.isShowAboutus = false;
						this.isShowSetting = true;
						break;
						//我的等级
					case 7:
						this.isShowMyLevel = true;
						break;
						//关闭我的等级
					case 8:
						this.isShowMyLevel = false;
						this.isShowSetting = true;
						break;
						//关闭用户协议隐私政策
					case 9:
						this.isAgree = false;
						this.isShowSetting = true;
						break;
						//关闭帮助中心
					case 10:
						this.isShowHelp = false;
						this.isShowSetting = true;
						break;
				}
			}
		}
	};
</script>

<style lang="scss" scoped>
	.showLoading {
		position: fixed;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		background: rgba(0, 0, 0, 0.5);
		z-index: 999999;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.main {
		width: 100vw;
		height: 100vh;
		position: absolute;
		overflow: hidden;
		// background: url('/static/index/bg2.png');
		background-size: cover;

		.title {
			display: flex;
			flex-direction: column;
			margin-top: 10rpx;
			font-size: 25rpx;
			color: #ffffff;

			.item {
				display: flex;
				position: relative;
				left: 20rpx;
			}

			.item1 {
				position: absolute;
				top: 140rpx;
				left: 80rpx;
				animation: moveUpDown 2s infinite alternate;

				.item1-bg {
					width: 276rpx;
					height: 49rpx;
					font-size: 22rpx;
					display: flex;
					justify-content: center;
					align-items: center;
					font-weight: bold;
					color: #000;
					text-align: center;
					position: absolute;
					// background: url('/static/index/5.png');
					background-size: cover;
				}
			}

			@keyframes moveUpDown {

				0%,
				100% {
					transform: translateY(0);
				}

				50% {
					transform: translateY(6rpx);
					/* 修改这个值以改变移动距离 */
				}
			}

			.itemRight {
				position: absolute;
				top: 140rpx;
				left: 90rpx;
				animation: moveUpDown 2s infinite alternate;

				.item1-bg {
					width: 276rpx;
					height: 49rpx;
					font-size: 22rpx;
					display: flex;
					justify-content: center;
					align-items: center;
					font-weight: bold;
					color: #000;
					text-align: center;
					position: absolute;
					// background: url('/static/index/5.png');
					background-size: cover;
				}
			}

			.item2 {
				display: flex;
				width: 95%;
				will-change: transform;
				margin: 80rpx auto 0 auto;
				align-items: center;
				justify-content: space-between;

				.title-bg {
					width: 350rpx;
					height: 52rpx;
					margin-right: 20rpx;
					display: flex;
					text-align: center;
					justify-content: space-between;
					align-items: center;
					background: #E8783B;
				}

				.float-number {
					position: fixed;
					left: 0;
					top: 0;
					overflow: hidden;
					width: 100%;
					height: 100%;
					pointer-events: none;
					font-size: 36rpx;
					font-weight: bold;

					.floating-text {
						position: absolute;
						font-size: 16px;
						font-weight: bold;
						color: #FFB347;
						left: 150rpx;
						top: 85rpx;
						pointer-events: none;
						/* 防止文字影响点击事件 */
					}

					.floating-text2 {
						position: absolute;
						font-size: 16px;
						font-weight: bold;
						color: #e80904;
						left: 250rpx;
						width: 100rpx;
						top: 50rpx;
						min-width: 200rpx;
						pointer-events: none;
						/* 防止文字影响点击事件 */
					}
				}

				.broadcast {
					width: 480rpx;
					height: 40rpx;
					display: flex;
					text-align: center;
					position: absolute;
					align-items: center;
					font-size: 24rpx;
					color: #ffffff;
					background: rgba(0, 0, 0, 0.6);
					border-radius: 20rpx;
					animation: marquee 8s linear infinite;
				}

				@keyframes marquee {
					0% {
						transform: translateX(750rpx);
					}

					100% {
						transform: translateX(-100%);
					}
				}
			}
		}

		.adsRecord {
			display: flex;
			width: 80%;
			font-size: 28rpx;
			justify-content: space-between;
			margin: 20rpx auto;

			.adsRecord-item {}
		}

		.body {
			width: 691rpx;
			height: 506rpx;
			margin: 15vh auto 0 auto;
			position: relative;
			// background: url('/static/index/bodyBg.png');
			background-size: cover;
			text-align: center;

			.level {
				padding-top: 12rpx;
				font-size: 36rpx;
				color: #000000;
			}

			.filmBg {
				width: 302rpx;
				height: 302rpx;
				position: relative;
				left: 28%;
				top: 30rpx;
				// background: url('/static/index/filmBg.png');
				background-size: cover;
				transform-origin: center center;
				animation: rotate 4s linear infinite;
				/* 应用动画 */
			}

			@keyframes rotate {
				from {
					transform: rotate(0deg);
					/* 从0度开始 */
				}

				to {
					transform: rotate(360deg);
					/* 到360度结束 */
				}
			}

			.filmLeft {
				width: 145rpx;
				height: 235rpx;
				position: absolute;
				right: 50rpx;
				top: 180rpx;
				transform: translateX(-50%);
				// background: url('/static/index/filmLeft.png');
				background-size: cover;
			}

			.progress {
				width: 510rpx;
				height: 30rpx;
				position: relative;
				display: flex;
				align-items: center;
				margin: 30rpx auto;
				background: #fff;
				border-radius: 15rpx;

				.progress-name {
					font-size: 20rpx;
					color: #000;
					position: absolute;
					left: 50%;
					transform: translateX(-50%);
				}

				.progress-wx {
					position: absolute;
					left: 100%;
				}

				.progress2 {
					width: 510rpx;
					height: 30rpx;
					background: #FFC728;
					border-radius: 15rpx;
				}
			}

			.body-item {
				width: 214rpx;
				height: 90rpx;
				position: absolute;
				right: 0rpx;
				bottom: 0rpx;
				// background: url('/static/index/10.png');
				background-size: cover;
			}
		}

		.bottom {
			margin: 8vh auto 2vh auto;
			text-align: center;

			.bottom-title {
				font-size: 26rpx;
				color: #ffffff;
			}

			.bottom-item {
				width: 596rpx;
				height: 140rpx;
				display: flex;
				align-items: center;
				justify-content: center;
				margin: 2vh auto 10rpx auto;
				// background: url('/static/index/1.png');
				background-size: cover;
				font-size: 40rpx;
				color: #000;

				.bottom-item-icon {
					position: absolute;
					width: 56rpx;
					height: 56rpx;
					right: 120rpx;
				}
			}

			.itemRight {
				// background: url('/static/index/2.png');
				background-size: cover;
			}

			.center-container {
				position: absolute;
				left: 50%;
				bottom: 22.5%;
				will-change: transform;
				pointer-events: none;
				transform: translateX(-50%);
				width: 300rpx;
				height: 300rpx;

				.floating-wx-icon {
					position: absolute;
					width: 70rpx;
					height: 70rpx;
					opacity: 0;
					transition: all 0.5s ease-in-out;
					will-change: transform, opacity;
				}

				.floating-wx-icon.show {
					opacity: 1;
				}

				.floating-wx-icon.flying {
					transition: all 0.8s cubic-bezier(0.4, 0, 0.2, 1);
				}
			}
		}

		.ranking-list {
			width: 100vw;
			height: 100vh;
			position: absolute;
			top: 0;
			background: rgba(0, 0, 0, 0.5);

			.ranking-list-bg {
				width: 644rpx;
				height: 1270rpx;
				overflow: hidden;
				position: relative;
				margin: 120rpx auto;
				// background: url('/static/ranking/rankBg.png');
				background-size: cover;

				.ranking-list-close {
					position: absolute;
					right: 0rpx;
					top: 60rpx;
					width: 56rpx;
				}

				.rank-title {
					width: 536rpx;
					height: 23rpx;
					font-size: 18rpx;
					color: #ffffff;
					display: flex;
					position: absolute;
					top: 260rpx;
					left: 50%;
					transform: translateX(-50%);
					justify-content: space-between;
					background: #c8b1a5;
					border-radius: 12rpx;
				}

				.ranking-body {
					width: 576rpx;
					height: 928rpx;
					margin: 300rpx auto;
					background: #f6ebe5;
					border-radius: 22rpx;
					border: 4px solid #c7b4a9;

					.ranking-body-item {
						width: 550rpx;
						height: 102rpx;
						display: flex;
						align-items: center;
						font-size: 20rpx;
						color: #000;
						margin: 20rpx auto;
						// background: url('/static/ranking/6.png');
						background-size: cover;
					}

					.bodyItem1 {
						// background: url('/static/ranking/9.png');
						background-size: cover;
					}

					.bodyItem2 {
						// background: url('/static/ranking/8.png');
						background-size: cover;
					}

					.bodyItem3 {
						// background: url('/static/ranking/7.png');
						background-size: cover;
					}

					.my-ranking {
						.my-ranking-title {
							width: 536rpx;
							height: 23rpx;
							font-size: 18rpx;
							color: #ffffff;
							text-align: center;
							margin: 15rpx auto;
							background: #c8b1a5;
							border-radius: 12rpx;
						}

						.my-ranking-item {
							width: 550rpx;
							height: 102rpx;
							font-size: 20rpx;
							color: #ffffff;
							display: flex;
							justify-content: space-around;
							align-items: center;
							margin: 20rpx auto;
							// background: url('/static/ranking/5.png');
							background-size: cover;
						}
					}
				}
			}
		}

		.setting {
			width: 100vw;
			height: 100vh;
			position: absolute;
			top: 0;
			background: rgba(0, 0, 0, 0.5);

			.setting-bg {
				width: 643rpx;
				height: 577rpx;
				font-weight: bold;
				font-size: 26rpx;
				color: #333333;
				position: relative;
				margin: 400rpx auto;
				// background: url('/static/setting/bg.png');
				background-size: cover;

				.setting-close {
					position: absolute;
					right: 20rpx;
					top: 30rpx;

					image {
						width: 56rpx;
						height: 56rpx;
					}
				}

				.setting-info {
					width: 526rpx;
					height: 134rpx;
					position: absolute;
					top: 150rpx;
					left: 50%;
					transform: translateX(-50%);
					// background: url('/static/setting/6.png');
					// background-size: cover;

					.setting-info-avatarr {
						width: 85rpx;
						height: 85rpx;
						border-radius: 16rpx;
						position: absolute;
						top: 28rpx;
						left: 28rpx;
					}

					.setting-info-item {
						display: flex;
						position: absolute;
						top: 20rpx;
						left: 130rpx;
						width: 400rpx;
						font-weight: bold;
						font-size: 20rpx;
						flex-direction: column;

						span {
							margin-top: 6rpx;
						}
					}

					.setting-mylevel {
						width: 112rpx;
						height: 37rpx;
						right: 24rpx;
						top: 15rpx;
						position: absolute;
					}
				}

				.setting-item1 {
					position: absolute;
					top: 350rpx;
					left: 50%;
					transform: translateX(-50%);
					display: flex;
					align-items: center;

					.setting-item1-image1 {
						position: relative;
						width: 122rpx;
					}

					.setting-item1-image2 {
						position: relative;
						width: 47rpx;
						left: 0rpx;
					}

					.setting-item1-image3 {
						position: relative;
						width: 122rpx;
						left: 26rpx;
					}

					.setting-item1-image4 {
						position: relative;
						width: 47rpx;
					}

					.setting-item1-image-on {
						position: relative;
						left: -40rpx;
					}

					.setting-item1-image-off {
						position: relative;
						left: -140rpx;
					}

					.setting-item1-image-on2 {
						position: relative;
						left: -15rpx;
					}

					.setting-item1-image-off2 {
						position: relative;
						left: -120rpx;
					}

					view {
						width: 250rpx;
						display: flex;
						align-items: center;
					}
				}

				.setting-item2 {
					display: flex;
					width: 600rpx;
					position: absolute;
					top: 410rpx;
					left: 57%;
					transform: translateX(-50%);
					flex-wrap: wrap;
					font-weight: bold;
					font-size: 26rpx;
					color: #333333;
					text-decoration-line: underline;

					view {
						width: 30%;
						margin-top: 20rpx;
						margin-right: 3%;
					}
				}
			}
		}

		.myLevel {
			width: 100vw;
			height: 100vh;
			position: absolute;
			top: 0;
			background: rgba(0, 0, 0, 0.5);

			.myLevel-bg {
				width: 643rpx;
				height: 556rpx;
				overflow: hidden;
				position: relative;
				margin: 400rpx auto;
				// background: url('/static/mylevel/4.png');
				background-size: cover;

				.myLevel-bg-close {
					position: absolute;
					right: 20rpx;
					top: 20rpx;
					width: 56rpx;
					height: 56rpx;
				}

				.myLevel-bg-main {
					width: 526rpx;
					height: 181rpx;
					display: flex;
					flex-direction: column;
					font-weight: bold;
					font-size: 26rpx;
					align-items: center;
					margin: 150rpx auto 0 auto;
					// background: url('/static/mylevel/3.png');
					background-size: cover;

					.uni-progress-bar {
						border-radius: 22rpx;
					}

					.uni-progress-inner-bar {
						border-radius: 22rpx;
					}
				}

				.myLevel-bg-btn {
					width: 363rpx;
					height: 100rpx;
					margin: 50rpx auto;
					// background: url('/static/mylevel/2.png');
					background-size: cover;
				}
			}
		}

		.aboutus {
			width: 100vw;
			height: 100vh;
			position: absolute;
			top: 0;
			background: rgba(0, 0, 0, 0.5);

			.aboutus-bg {
				width: 641rpx;
				height: 677rpx;
				position: relative;
				display: flex;
				flex-direction: column;
				align-items: center;
				font-size: 22rpx;
				color: #000000;
				line-height: 46rpx;
				margin: 300rpx auto;
				// background: url('/static/aboutus/bg.png');
				background-size: cover;

				.aboutus-bg-close {
					position: absolute;
					width: 56rpx;
					height: 56rpx;
					right: 20rpx;
					top: 20rpx;
				}

				.aboutus-bottom {
					width: 95%;
					margin: auto;
					font-size: 22rpx;
					color: #ffffff;
				}
			}
		}

		.pop {
			width: 100vw;
			height: 100vh;
			position: absolute;
			top: 0;
			background: rgba(0, 0, 0, 0.5);

			.popBg {
				width: 643rpx;
				height: 971rpx;
				position: relative;
				margin: 300rpx auto 0 auto;

				.closeBtn {
					width: 56rpx;
					height: 56rpx;
					position: absolute;
					right: 31rpx;
					top: 24rpx;
					// background: url('/static/login/close.png');
					background-size: cover;
				}

				.popContent {
					width: 90%;
					position: absolute;
					overflow-y: auto;
					left: 5%;
					right: 5%;
					top: 15%;
					bottom: 5%;
				}
			}

			.popBg1 {
				// background: url('/static/login/bg1.png');
				background-size: cover;
			}

			.popBg2 {
				// background: url('/static/login/bg2.png');
				background-size: cover;
			}
		}

		.helpCenter {
			width: 100vw;
			height: 100vh;
			position: absolute;
			top: 0;
			background: rgba(0, 0, 0, 0.5);

			.helpBg {
				width: 641rpx;
				height: 1008rpx;
				position: relative;
				margin: 300rpx auto 0 auto;
				// background: url('/static/help/bg.png');
				background-size: cover;

				.closeBtn {
					width: 56rpx;
					height: 56rpx;
					position: absolute;
					right: 31rpx;
					top: 24rpx;
					// background: url('/static/login/close.png');
					background-size: cover;
				}

				.help-title {
					width: 628rpx;
					height: 208rpx;
					margin: 96rpx 6rpx 0 6rpx;
					// background: url('/static/help/3.png');
					background-size: cover;
				}

				.help-menu {
					display: flex;
					justify-content: center;
					flex-wrap: wrap;
					font-size: 20rpx;
					margin-left: 30rpx;

					.help-menu-item {
						width: 158rpx;
						height: 42rpx;
						text-align: center;
						line-height: 42rpx;
						color: #000;
						margin-top: 40rpx;
						margin-right: 40rpx;
						background: #f5f5f5;
						border-radius: 21rpx;
					}

					.helpSelected {
						width: 158rpx;
						height: 42rpx;
						color: #fff;
						background: #E8783B;
						border-radius: 21rpx;
					}
				}

				.help-questions {
					display: flex;
					flex-direction: column;

					.help-questions-title {
						width: 628rpx;
						height: 56rpx;
						font-size: 26rpx;
						text-align: center;
						line-height: 56rpx;
						margin: 20rpx auto 0 auto;
						background: #FFEBD6;
					}

					.help-questions-content {
						width: 600rpx;
						font-size: 20rpx;
						margin: auto;
					}
				}
			}
		}

		.reward {
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 999;
			overflow: hidden;
			background: rgba(0, 0, 0, 0.5);

			.redWalletPop-title {
				display: flex;
				position: relative;
				top: 30vh;
				justify-content: center;
				align-items: center;
				font-weight: 400;
				z-index: 9999;
				font-size: 30rpx;
				color: #ffe7b7;
			}

			.redWalletPop-title2 {
				display: flex;
				font-weight: 400;
				font-size: 38rpx;
				color: #ffe7b7;
				position: absolute;
				left: 50%;
				top: 40vh;
				z-index: 9999;
				transform: translateX(-50%);
			}

			.redWalletPop-bg {
				width: 546rpx;
				height: 830rpx;
				position: relative;
				margin: 15vh auto 0 auto;
				// background: url('/static/2.png');
				background-size: cover;
			}

			.redWalletPop-close {
				width: 64rpx;
				height: 64rpx;
				position: relative;
				margin: 5vh auto;
				// background: url('/static/1.png');
				background-size: cover;
			}
		}

		.redWalletRewardPop {
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 999;
			background: rgba(0, 0, 0, 0.5);

			.redWalletRewardPop-bg {
				width: 600rpx;
				height: 871rpx;
				position: relative;
				margin: 15vh auto 0 auto;
				// background: url('/static/4.png');
				background-size: cover;

				.redWalletRewardPop-body {
					display: flex;
					flex-direction: column;
					align-items: center;

					.body-title {
						margin-top: 150rpx;
						display: flex;
						align-items: center;
						font-weight: 400;
						font-size: 30rpx;
						color: #333333;
					}

					.body-money {
						font-weight: bold;
						font-size: 81rpx;
						color: #fe302a;
					}
				}
			}

			.redWalletRewardPop-close {
				width: 428rpx;
				height: 126rpx;
				position: relative;
				margin: -10vh auto 0 auto;
				// background: url('/static/5.png');
				background-size: cover;
			}
		}

		.guidePass {
			width: 100vw;
			height: 100vh;
			position: absolute;
			top: 0;
			background: rgba(0, 0, 0, 0.5);

			.guidePass-bg {
				width: 567rpx;
				height: 840rpx;
				position: relative;
				margin: 200rpx auto;
				// background: url('/static/5.png');
				background-size: cover;

				.guidePass-money {
					position: absolute;
					left: 50%;
					transform: translateX(-50%);
					bottom: 260rpx;
					font-weight: bold;
					font-size: 68rpx;
					color: #ffe12f;
				}
			}
		}

		.guide {
			width: 100vw;
			height: 100vh;
			position: absolute;
			top: 0;
			background: rgba(0, 0, 0, 0.5);

			.guide1 {
				width: 100vw;
				height: 100vh;
				display: flex;
				flex-direction: column;

				.guide1-bottom {
					width: 688rpx;
					height: 420rpx;
					display: flex;
					flex-direction: column;
					margin: 40rpx auto 0 auto;
					position: absolute;
					bottom: 220rpx;
					left: 50%;
					transform: translateX(-50%);
					// background: url('/static/guide/4.png');
					background-size: cover;

					.guide1-bottom-item1 {
						margin: auto;
						font-size: 26rpx;
						color: #000;
					}

					.guide1-bottom-item2 {
						width: 596rpx;
						height: 140rpx;
						display: flex;
						margin: auto;
						justify-content: center;
						align-items: center;
						// background: url('/static/guide/5.png');
						background-size: cover;
						font-size: 40rpx;
						color: #000;
					}

					.guide1-bottom-item3 {
						width: 596rpx;
						height: 140rpx;
						display: flex;
						margin: auto;
						align-items: center;
						justify-content: center;
						color: #000;
						font-size: 40rpx;
						// background: url('/static/guide/6.png');
						background-size: cover;
					}
				}

				.guide1-bottom2 {
					width: 567rpx;
					height: 840rpx;
					margin: 310rpx auto;
					// background: url('/static/guide/7.png');
					background-size: cover;
				}
			}
		}

		.bottom-bar {
			position: absolute;
			display: flex;
			left: 0;
			right: 0;
			bottom: 100rpx;

			.bottom-bar-body {
				width: 60%;
				display: flex;
				justify-content: space-between;
				margin: auto;
			}
		}
	}
</style>
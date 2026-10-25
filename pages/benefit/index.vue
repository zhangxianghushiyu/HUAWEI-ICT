<template>
	<page-meta :page-style="'overflow:'+(show?'hidden':'visible')"></page-meta>
	<view style="padding-bottom: 200rpx;">
		<BaseHeader bgImageUrl="/static/images/change/benefitBG1.png">
			<view class="head-title">福利</view>
		</BaseHeader>
		<view class="mine">
			<image class="mine-bg" src="/static/images/change/benefitBG2.png" mode="widthFix" />
			<!-- 规则 -->
			<image class="mine-rule" @click="$utils.toPage('/pages/mine/policy?type=' + 8)"
				src="~@/static/images/change/Rule.png" mode="widthFix" />
			<!-- 宝箱 -->
			<!-- 			<view v-if="$store.state.isShow && isLogin" class="mine-drag">
				<view class="drag" @click="receiveBox(1)">
					<image class="drag-img" src="~@/static/images/Drag.png" mode="widthFix"></image>
					<view v-if="boxDown == 0" class="drag-time">可领取</view>
					<view v-else class="drag-time">{{boxDown}}秒</view>
				</view>
			</view> -->
			<view class="money">
				<image class="money-tag" v-if="$store.state.isShow" @click="toPage('/pages/benefit/withdrawal')"
					src="~@/static/images/change/GoWithdrawal.png" mode="widthFix" />
				<view v-if="isLogin" class="money-info">
					<view class="money-info-item">
						<view class="money-info-item-title" @click="toPage('/pages/benefit/withdrawal-detail')">
							金币余额(币）
							<image style="width: 15rpx;" src="~@/static/images/Right.png" mode="widthFix" />
						</view>
						<view class="money-info-item-price" @tap="openExchange">
							{{userInfo.goldCoin}}
						</view>
						<view v-if="$store.state.isShow" class="money-info-item-tip">
							点击金币兑换为现金
						</view>
					</view>
					<view class="money-info-item">
						<view class="money-info-item-title" @click="toPage('/pages/benefit/withdrawal-detail?index=1')">
							现金余额(元）
							<image style="width: 15rpx;" src="~@/static/images/Right.png" mode="widthFix" />
						</view>
						<view class="money-info-item-price">
							{{userInfo.cash}}
						</view>
						<view v-if="$store.state.isShow" class="money-info-item-tip">
							近一天已有5.5万+人成功提现!
						</view>
					</view>
				</view>
				<image v-else @click="toPage('/pages/mine/login')" class="money-img"
					src="~@/static/images/change/BenefitLogin.png" mode="scaleToFill" />
			</view>

			<image v-if="$store.state.isShow" style="width: 100%;margin-top: 20rpx;"
				src="~@/static/images/change/BenefitBanner.png" mode="widthFix" />

			<view class="task" v-if="isLogin">
				<!-- 见面领现金 -->
				<view v-if="newPeopleTask.meetToReceiveCashVO" class="task-info">
					<view class="task-info-box">
						<view class="task-info-box-title">
							<text>{{newPeopleTask.meetToReceiveCashVO.title}}</text>
							<text class="price">{{newPeopleTask.meetToReceiveCashVO.price}}元</text>
							<image class="red-envelope" src="~@/static/images/change/RedPacket.png" mode="widthFix">
							</image>
							<text class="sub-price">+{{newPeopleTask.meetToReceiveCashVO.price}}元</text>
						</view>
						<view class="task-info-box-sub-title">{{newPeopleTask.meetToReceiveCashVO.describe}}</view>
					</view>
					<image src="~@/static/images/change/ReceiveBtn.png" @click="receive('meetToReceiveCash')"
						class="task-info-btn" mode="widthFix"></image>
				</view>

				<!-- 新人七日签到 -->
				<view class="task-info" v-if="newPeopleTask.newcomersSignInForSevenDaysVO">
					<view class="task-info-box">
						<view class="task-info-box-title">
							<text>{{newPeopleTask.newcomersSignInForSevenDaysVO.title}}</text>
						</view>
						<view class="task-info-box-sub-title">
							{{newPeopleTask.newcomersSignInForSevenDaysVO.describe}}
						</view>
					</view>
					<image v-if="newPeopleTask.newcomersSignInForSevenDaysVO.isSign"
						src="~@/static/images/change/receivedAlready.png" class="task-info-btn" mode="widthFix">
					</image>
					<image v-else src="~@/static/images/change/ReceiveBtn.png" @tap="newSign" class="task-info-btn"
						mode="widthFix">
					</image>
				</view>
				<view class="task-sign-in" v-if="newPeopleTask.newcomersSignInForSevenDaysVO">
					<view class="task-sign-in-item"
						v-for="(item,index) in newPeopleTask.newcomersSignInForSevenDaysVO.newcomersSignInForSevenDaysDetailVOS"
						:key="index">
						<view class="task-sign-in-item-box">
							<view v-if="item.isSign" class="task-sign-in-item-box-bg">
								<image src="@/static/images/change/SignInBG1.png" class="task-sign-in-item-box-bg"
									mode="scaleToFill " />
								<view class="task-sign-in-item-box-text">{{item.price}}</view>
							</view>
							<view v-else class="task-sign-in-item-box-bg">
								<image src="~@/static/images/change/SignInBG2.png" class="task-sign-in-item-box-bg"
									mode="scaleToFill" />
								<view class="task-sign-in-item-box-text2">{{item.price}}</view>
							</view>
						</view>
						<view class="task-sign-in-item-text"> 第{{item.dayStr}}</view>
					</view>
				</view>
				<view v-if="$store.state.isShow">
					<!-- 体现一元 -->
					<view class="task-info" v-if="newPeopleTask.withdrawOneYuanVO">
						<view class="task-info-box">
							<view class="task-info-box-title">
								<text>{{newPeopleTask.withdrawOneYuanVO.title}}</text>
							</view>
							<view class="task-info-box-sub-title">{{newPeopleTask.withdrawOneYuanVO.describe}}</view>
						</view>
						<image src="~@/static/images/change/GoWithdrawalBtn.png"
							@click="toPage('/pages/benefit/withdrawal')" class="task-info-btn" mode="widthFix"></image>
					</view>
					<!-- 新人看剧领金币 -->
					<view class="task-info" v-if="newPeopleTask.newPeopleLookToDramasCashVO">
						<view class="task-info-box">
							<view class="task-info-box-title">
								<text>{{newPeopleTask.newPeopleLookToDramasCashVO.title}}</text>
								<image class="red-envelope" src="~@/static/images/change/RedPacket.png" mode="widthFix">
								</image>
								<text class="sub-price">+{{newPeopleTask.newPeopleLookToDramasCashVO.price}}元</text>
							</view>
							<view class="task-info-box-sub-title">{{newPeopleTask.newPeopleLookToDramasCashVO.describe}}
							</view>
						</view>
						<image @tap="toPage('/pages/index/index')" src="~@/static/images/change/GoWatchDramaBtn.png"
							class="task-info-btn" mode="widthFix"></image>
					</view>
					<view class="task-steps" v-if="newPeopleTask.newPeopleLookToDramasCashVO && taskShow">
						<uv-steps :current="newPeopleLookIndex" activeIcon="checkmark-circle-fill"
							inactiveIcon="checkmark-circle" activeColor="#F14835" inactiveColor=" #FFECEA">
							<uv-steps-item
								v-for="(item,index) in newPeopleTask.newPeopleLookToDramasCashVO.newPeopleLookDramaDetailVOS">
								<template v-slot:title>
									<text
										style="padding: 10rpx; color:#F14835; background-color:  #FFECEA;font-size: 22rpx;">可领{{item.price}}元</text>
								</template>
								<template v-slot:desc>
									<text style="font-size: 20rpx;">{{item.dayStr}}</text>
								</template>
							</uv-steps-item>
						</uv-steps>
					</view>
				</view>
			</view>

			<view class="task" v-if="isLogin && $store.state.isShow">
				<view class="task-title">
					<image class="task-title-image" src="~@/static/images/change/Task.png" mode="widthFix"></image>
					每日任务
				</view>
				<view class="task-info" v-if="dailyTaskList.foodPointLookDrama">
					<view class="task-info-box">
						<view class="task-info-box-title">
							<text>{{dailyTaskList.foodPointLookDrama.title}}</text>
							<image class="gold-coin" src="~@/static/images/change/GoldCoin.png" mode="widthFix"></image>
							<text class="sub-price">+{{dailyTaskList.foodPointLookDrama.price}}</text>
						</view>
						<view class="task-info-box-sub-title">{{dailyTaskList.foodPointLookDrama.describe}}</view>
					</view>
					<image @tap="$utils.toPage('/pages/index/index')" src="~@/static/images/change/GoReceiveBtn.png"
						class="task-info-btn" mode="widthFix"></image>
				</view>
				<view class="task-info" v-if="dailyTaskList.dailySignVO">
					<view class="task-info-box">
						<view class="task-info-box-title">
							<text>{{dailyTaskList.dailySignVO.title}}</text>
							<image class="gold-coin" src="~@/static/images/change/GoldCoin.png" mode="widthFix"></image>
							<text class="sub-price">+{{dailyTaskList.dailySignVO.price}}</text>
						</view>
						<view class="task-info-box-sub-title">{{dailyTaskList.dailySignVO.describe}}</view>
					</view>
					<image @tap="showPopup('signInPopup',dailyTaskList.dailySignVO.dailySignInForSevenDaysDetailVOS)"
						src="~@/static/images/change/SignInBtn.png" class="task-info-btn" mode="widthFix"></image>
				</view>
				<view class="task-info" v-if="dailyTaskList.lookDramaToCoinVO">
					<view class="task-info-box">
						<view class="task-info-box-title">
							<text>{{dailyTaskList.lookDramaToCoinVO.title}}</text>
							<image class="gold-coin" src="~@/static/images/change/GoldCoin.png" mode="widthFix"></image>
							<text class="sub-price">+{{dailyTaskList.lookDramaToCoinVO.price}}</text>
						</view>
						<view class="task-info-box-sub-title">{{dailyTaskList.lookDramaToCoinVO.describe}}</view>
					</view>
					<image @tap="toPage('/pages/index/index')" src="/static/images/change/GoWatchBtn.png"
						class="task-info-btn" mode="widthFix"></image>
				</view>
				<view class="task-steps" v-if="dailyTaskList.lookDramaToCoinVO && taskShow">
					<uv-steps :current="dailyLookIndex" activeIcon="checkmark-circle-fill"
						inactiveIcon="checkmark-circle" activeColor="#F14835" inactiveColor=" #FFECEA">
						<uv-steps-item v-for="(item,index) in dailyTaskList.lookDramaToCoinVO.lookDramaToCoinDetailVOS">
							<template v-slot:title>
								<text
									style="padding: 10rpx; color:#F14835; background-color:  #FFECEA;font-size: 22rpx;">可领{{item.price}}金币</text>
							</template>
							<template v-slot:desc>
								<text style="font-size: 20rpx;">{{item.minuteStr}}</text>
							</template>
						</uv-steps-item>
					</uv-steps>
				</view>
				<view class="task-info" v-if="dailyTaskList.lookVideoToCoinVO ">
					<view class="task-info-box">
						<view class="task-info-box-title">
							<text>{{dailyTaskList.lookVideoToCoinVO.title}}</text>
							<image class="gold-coin" src="~@/static/images/change/GoldCoin.png" mode="widthFix"></image>
							<text class="sub-price">+{{dailyTaskList.lookVideoToCoinVO.price}}</text>
						</view>
						<view class="task-info-box-sub-title">{{dailyTaskList.lookVideoToCoinVO.describe}}</view>
					</view>

					<image @click="lookBox" src="/static/images/change/GoWatchBtn.png" class="task-info-btn"
						mode="widthFix">
					</image>

				</view>
				<view class="task-progress" v-if="dailyTaskList.lookVideoToCoinVO ">
					<view class="task-progress-info">
						<uv-line-progress
							:percentage=" (100 / dailyTaskList.lookVideoToCoinVO.needCount * dailyTaskList.lookVideoToCoinVO.count).toFixed(0)  "
							:showText="false" height="3" activeColor="#F14835" inactiveColor=" #FFECEA" />
					</view>
					<view class="task-progress-text">
						{{dailyTaskList.lookVideoToCoinVO.count}}/{{dailyTaskList.lookVideoToCoinVO.needCount}}次
					</view>
				</view>
				<view class="task-info" v-if="dailyTaskList.shareToCoinVO ">
					<view class="task-info-box">
						<view class="task-info-box-title">
							<text>{{dailyTaskList.shareToCoinVO.title}}</text>
							<image class="gold-coin" src="~@/static/images/change/GoldCoin.png" mode="widthFix"></image>
							<text class="sub-price">+{{dailyTaskList.shareToCoinVO.price}}</text>
						</view>
						<view class="task-info-box-sub-title">{{dailyTaskList.shareToCoinVO.describe}}</view>
					</view>
					<image src="/static/images/change/GoShare.png" @click="shareInfo" class="task-info-btn"
						mode="widthFix"></image>
				</view>
				<view class="task-info" v-if="dailyTaskList.appointDramaCoinVO ">
					<view class="task-info-box">
						<view class="task-info-box-title">
							<text>{{dailyTaskList.appointDramaCoinVO.title}}</text>
							<image class="gold-coin" src="~@/static/images/change/GoldCoin.png" mode="widthFix"></image>
							<text class="sub-price">+{{dailyTaskList.appointDramaCoinVO.price}}</text>
						</view>
						<view class="task-info-box-sub-title">
							<text>今日已观看：</text>
							<text style="color:#F14835;">{{0}}分钟</text>
						</view>
					</view>
					<image src="~@/static/images/change/GoWatchBtn.png"
						@click="toDetail('/pages/index/video-see?pageNum=',dailyTaskList.appointDramaCoinVO.appointDramaCoinListVOS[0])"
						class="task-info-btn" mode="widthFix"></image>
				</view>
				<view class="task-voide" v-if="dailyTaskList.appointDramaCoinVO && taskShow">
					<view class="task-voide-item"
						v-for="(item,index) in dailyTaskList.appointDramaCoinVO.appointDramaCoinListVOS" :key="index"
						@click="toDetail('/pages/index/video-see?pageNum=',item)">
						<image class="task-voide-item-img" :src="item.titleImg" mode="scaleToFill"></image>
						<view class="task-voide-item-text">{{item.title}}</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 金币兑换现金   -->
		<uv-popup ref="exchange" mode="center" round="16rpx">
			<view style="width: 580rpx;background: #ffffff;padding: 20rpx;">
				<view class="exchange-info-image" @tap="closeExchange()">
					<image style="width: 21rpx;" src="~@/static/images/poupClose.png" mode="widthFix" alt="" />
				</view>
				<view class="exchange-info-text">
					金币比例兑换
				</view>
				<view class="exchange-info-text2">
					金币兑换现金比例为{{version.convertRatio}} : 1
				</view>
				<view class="exchange-info-input">
					<input v-model="exchangeCoinNum" type="number" placeholder="请输入金币数量" />
				</view>
				<view class="exchange-info-button" @tap="exchangeCoinBtn()">
					确定
				</view>
			</view>
		</uv-popup>

		<!-- 领取金币弹出层  :closeOnClickOverlay="false" -->
		<uv-popup ref="goldCoinsPopup" :closeOnClickOverlay="false" bgColor="none">
			<view class="gold-coins-popup">
				<image class="gold-coins-popup-image" src="@/static/images/change/CollectGoldCoins.png"
					mode="scaleToFill" />
				<image class="gold-coins-popup-close" @click="closePopup('goldCoinsPopup')"
					src="~@/static/images/Close.png" mode="widthFix" />
				<view class="gold-coins-popup-gold">
					{{gold}} 金币
				</view>
				<view class="gold-coins-popup-btn" @click="receiveBox(5)">
					<view class="gold-coins-popup-btn-text">看小视频最高领取3300金币</view>
				</view>
			</view>
		</uv-popup>

		<!-- 领取金币弹出层  :closeOnClickOverlay="false" -->
		<uv-popup ref="goldCoinsPopup2" :closeOnClickOverlay="false" bgColor="none">
			<view class="gold-coins-popup">
				<image class="gold-coins-popup-image" src="@/static/images/change/CollectGoldCoins2.png"
					mode="scaleToFill" />
				<image class="gold-coins-popup-close" @click="closePopup('goldCoinsPopup2')"
					src="~@/static/images/Close.png" mode="widthFix" />
				<view class="gold-coins-popup-gold2">
					{{gold}} 金币
				</view>
			</view>
		</uv-popup>

		<!-- 签到弹出层 -->
		<uv-popup ref="signInPopup" :closeOnClickOverlay="false" bgColor="none">
			<view class="sign-in-popup" v-if="popupInfo.length > 0">
				<image class="sign-in-popup-image" src="~@/static/images/change/SginBg.png" mode="scaleToFill" />
				<image class="sign-in-popup-close" @click="closePopup('signInPopup')" src="~@/static/images/Close.png"
					mode="widthFix" />
				<view class="sign-in-popup-box">
					<!-- <view class="sign-in-popup-box-title"> 今日签到 </view> -->
					<!-- <view class="sign-in-popup-box-sub-title "> 明日签到可再得330金币 </view> -->
					<view class="sign-in-popup-box-line ">
						<view class="sign-in-popup-box-line-item" @tap="dailySignIn()">
							<image class="sign-in-popup-box-line-item-bg"
								:src="popupInfo[0].isSign?'/static/images/change/SginDayBg.png':'/static/images/change/SginDayBg2.png'"
								mode="scaleToFill" />
							<view class="sign-in-popup-box-line-item-points">{{popupInfo[0].price}}</view>
							<view class="sign-in-popup-box-line-item-day">第{{popupInfo[0].dayStr}}</view>
						</view>
						<view class="sign-in-popup-box-line-item" @tap="dailySignIn()">
							<image class="sign-in-popup-box-line-item-bg"
								:src="popupInfo[1].isSign?'/static/images/change/SginDayBg.png':'/static/images/change/SginDayBg2.png'"
								mode="scaleToFill" />
							<view class="sign-in-popup-box-line-item-points">{{popupInfo[1].price}}</view>
							<view class="sign-in-popup-box-line-item-day">第{{popupInfo[1].dayStr}}</view>
						</view>
						<view class="sign-in-popup-box-line-item" @tap="dailySignIn()">
							<image class="sign-in-popup-box-line-item-bg"
								:src="popupInfo[2].isSign?'/static/images/change/SginDayBg.png':'/static/images/change/SginDayBg2.png'"
								mode="scaleToFill" />
							<view class="sign-in-popup-box-line-item-points">{{popupInfo[2].price}}</view>
							<view class="sign-in-popup-box-line-item-day">第{{popupInfo[2].dayStr}}</view>
						</view>
						<view class="sign-in-popup-box-line-item" @tap="dailySignIn()">
							<image class="sign-in-popup-box-line-item-bg"
								:src="popupInfo[3].isSign?'/static/images/change/SginDayBg.png':'/static/images/change/SginDayBg2.png'"
								mode="scaleToFill" />
							<view class="sign-in-popup-box-line-item-points">{{popupInfo[3].price}}</view>
							<view class="sign-in-popup-box-line-item-day">第{{popupInfo[3].dayStr}}</view>
						</view>
					</view>
					<view class="sign-in-popup-box-line ">
						<view class="sign-in-popup-box-line-item" @tap="dailySignIn()">
							<image class="sign-in-popup-box-line-item-bg"
								:src="popupInfo[4].isSign?'/static/images/change/SginDayBg.png':'/static/images/change/SginDayBg2.png'"
								mode="scaleToFill" />
							<view class="sign-in-popup-box-line-item-points">{{popupInfo[4].price}}</view>
							<view class="sign-in-popup-box-line-item-day">第{{popupInfo[4].dayStr}}</view>
						</view>
						<view class="sign-in-popup-box-line-item" @tap="dailySignIn()">
							<image class="sign-in-popup-box-line-item-bg"
								:src="popupInfo[5].isSign?'/static/images/change/SginDayBg.png':'/static/images/change/SginDayBg2.png'"
								mode="scaleToFill" />
							<view class="sign-in-popup-box-line-item-points">{{popupInfo[5].price}}</view>
							<view class="sign-in-popup-box-line-item-day">第{{popupInfo[5].dayStr}}</view>
						</view>
						<view class="sign-in-popup-box-line-item" @tap="dailySignIn()">
							<image class="sign-in-popup-box-line-item-bg"
								:src="popupInfo[6].isSign?'/static/images/change/SginDayBg.png':'/static/images/change/SginDayBg2.png'"
								mode="scaleToFill" />
							<view class="sign-in-popup-box-line-item-points">{{popupInfo[6].price}}</view>
							<view class="sign-in-popup-box-line-item-day">第{{popupInfo[6].dayStr}}</view>
						</view>
					</view>
					<view class="sign-in-popup-box-btn">

						<button
							v-if="$store.state.isShow && dailyTaskList.dailySignVO.dailySignInForSevenDaysDetailVOS[dailySignIndex].isSign == 1"
							type="theme" size="long" @click="signBox">看小视频最高领取3300金币</button>

						<button v-else @click="dailySignIn" type="theme" size="long">立即签到</button>
					</view>
				</view>
			</view>
		</uv-popup>
	</view>
</template>

<script>
	import BaseHeader from '@/components/BaseHeader.vue'
	import benefitBG from '@/static/images/change/benefitBG2.png'

	export default {
		components: {
			BaseHeader,
		},
		data() {
			return {
				show: false,
				exchangeCoinNum: '', //兑换金币
				// exchangeCoinProportion: '', //兑换比例
				newPeopleLookIndex: -1, //新人看剧进度
				dailyLookIndex: -1, //每日看剧及进度
				benefitBG: benefitBG,
				newPeopleTask: {}, //新人任务
				dailyTaskList: {}, //日常任务
				userInfo: {},
				popupInfo: [],
				dailySignIndex: 0,
				taskShow: false,
				boxInfo: {},
				boxDown: 0,
				timer: null,
				gold: 0,
				boxIndex: 0,
				version: {}
			}
		},
		onLoad() {
			this.version = uni.getStorageSync('Version')

			window.goldCoinAndroidCallBack = this.goldCoinAndroidCallBack;

		},
		onShow() {
			if (this.isLogin) {
				this.getUserInfo()
				this.getTask()
				this.getDailyTaskList()
				this.getBox()
			}
		},
		computed: {
			isLogin: function() {
				const token = uni.getStorageSync('Token');
				if (token) return true
				return false
			},

		},
		methods: {
			change(e) {},
			//金币兑换现金
			exchangeCoinBtn() {
				if (this.exchangeCoinNum >= this.version.convertRatio && this.exchangeCoinNum % 100 == 0) {
					this.$api.goldCoinToCash({
						goldCoin: this.exchangeCoinNum
					}).then(res => {
						if (res.code == 200) {
							uni.showToast({
								title: '兑换成功',
								icon: 'none',
								duration: 2000
							})
							this.exchangeCoinNum = ''
							this.getUserInfo()
							this.$refs.popup.close()
						}
					})
				} else {
					uni.showToast({
						title: '兑换金额不能小于比例值且是100的倍数',
						icon: 'none',
						duration: 2000
					})
				}
			},
			toPage(url, popupName = '') {
				if (popupName) this.closePopup(popupName)
				this.$utils.toPage(url)
			},
			toDetail(url, item) {
				console.log(item);
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
			},
			shareInfo() {
				window.rh_android.shareForFriends("红樱桃", "快来跟我一起看短剧领金币吧！", this.version.url, this.version.appUrl)
			},
			//每日签到弹出
			showPopup(popupName, data) {
				this.$refs[popupName].open('center');
				if (data) this.popupInfo = data
				this.show = true
			},
			closePopup(popupName) {
				this.$refs[popupName].close();
				this.popupInfo = []
				this.show = false
				this.gold = 0
				this.boxIndex = 0
			},
			//获取新人任务
			getTask() {
				this.$api.getnewPeopleTask().then(res => {
					this.newPeopleTask = res.data
					//处理新人看剧领金币当前是第几天
					if (this.newPeopleTask.newPeopleLookToDramasCashVO) {
						this.newPeopleLookIndex = -1
						this.newPeopleTask.newPeopleLookToDramasCashVO.newPeopleLookDramaDetailVOS.forEach(it => {
							if (it.isReceive) this.newPeopleLookIndex++
						})
					}
					setTimeout(() => {
						this.taskShow = true
					}, 1000)

					console.log(res.data);


				})
			},
			//获取每日任务
			getDailyTaskList() {
				this.$api.getdailyTask().then(res => {
					this.dailyTaskList = res.data
					console.log(res.data);

					//处理看剧领金币当前是第几天
					if (this.dailyTaskList.lookDramaToCoinVO) {
						this.dailyLookIndex = -1
						this.dailyTaskList.lookDramaToCoinVO.lookDramaToCoinDetailVOS.forEach(it => {
							if (it.isReceive) this.dailyLookIndex++
						})
					}

					//处理当前签到是第几天
					if (this.dailyTaskList.dailySignVO) {
						this.dailySignIndex = 0
						this.dailyTaskList.dailySignVO.dailySignInForSevenDaysDetailVOS.forEach(it => {
							if (it.isSign) this.dailySignIndex++
						})

						console.log('当前签到', this.dailySignIndex);
					}

				})

			},
			//领取
			receive(task) {
				//见面领取现金
				if (task == 'meetToReceiveCash') {
					this.$api.meetToReceiveCash().then(res => {
						this.getTask()
						this.getUserInfo()
					})
				}
			},
			//新人签到领金币
			newSign() {
				if (!this.newPeopleTask.newcomersSignInForSevenDaysVO.isSign) {
					this.$api.newcomersSignToGoldCoins().then(res => {
						if (res.code == 200) {
							this.getTask()
							this.getUserInfo()
							this.$utils.showTip('签到成功', '提示')
						}
					})
				} else {
					this.$utils.showTip('今日已签到，请勿重复签到', '提示')
				}
			},
			//每日签到
			dailySignIn() {
				this.$api.dailySignToGoldCoins().then(res => {
					if (res.code == 200) {
						this.$utils.showTip("签到成功", "提示")
						this.dailyTaskList.dailySignVO.dailySignInForSevenDaysDetailVOS[this
								.dailySignIndex]
							.isSign = 1
					}
				})
			},

			getUserInfo() {
				this.$api.getInfo().then(res => {
					uni.setStorageSync('UserInfo', res.data)
					this.userInfo = res.data
				})
			},
			//打开兑换现金弹窗
			openExchange() {
				this.$refs.exchange.open()
				// this.$api.getConvertRatio().then(res => {
				// 	if (res.code == 200) {
				// 		this.exchangeCoinProportion = res.data
				// 	}
				// })
			},
			closeExchange() {
				this.$refs.exchange.close()
				this.exchangeCoinNum = ''
			},
			//获取宝箱信息
			getBox() {
				this.$api.getBoxInfo().then(res => {
					this.boxInfo = res.data
					this.boxDown = 0
					if (this.boxInfo.time) {
						const currentTime = new Date();
						const specifiedTime = new Date(this.boxInfo.time);

						if (currentTime >= specifiedTime) return

						const timeDifferenceInMilliseconds = specifiedTime.getTime() - currentTime.getTime();

						if (timeDifferenceInMilliseconds < 0) {
							return
						}

						this.boxDown = (timeDifferenceInMilliseconds / 1000).toFixed(0);

						//计时器
						clearInterval(this.timer)
						this.timer = setInterval(() => {
							this.boxDown -= 1;
							if (this.boxDown <= 0) {
								clearInterval(this.timer)
							}
						}, 1000);

					}

				})
			},
			//领取宝箱
			receiveBox(index) {
				if (this.boxDown != 0) {
					this.$utils.showTip('点击太频繁，请稍后重试', '提示')
					return
				}

				// const index = this.boxIndex == 1 ? 5 : 1

				this.$refs.goldCoinsPopup.close();

				this.$api.checkAdsNums().then(res => {
					window.rh_android.showAd(this.userInfo.id, JSON.stringify({
						type: 1,
						value: 1
					}), 3)
				})



				if (this.boxIndex == 1) {
					console.log('第二次开宝箱');
					this.boxIndex = 0
				} else {
					console.log('第一次开宝箱');
					this.boxIndex = 1
				}
			},
			//登录看小视频
			signBox() {

				this.closePopup('signInPopup')
				this.$api.checkAdsNums().then(res => {
					//开启视频
					window.rh_android.showAd(this.userInfo.id, JSON.stringify({
						type: 1,
						value: 3
					}), 2)
				})
			},
			//看小视频
			lookBox() {
				//开启视频
				this.$api.checkAdsNums().then(res => {
					window.rh_android.showAd(this.userInfo.id, JSON.stringify({
						type: 1,
						value: 2
					}), 2)
				})
			},
			goldCoinAndroidCallBack(callback) {
				uni.showLoading({
					title: '获取结果。。。'
				})
				setTimeout(() => {
					uni.hideLoading()
					console.log("金币回调", callback);
					// 广告回调
					this.$api.taKuResultByTransId({
						transId: callback
					}).then(res => {
						if (res.data) {
							this.gold = res.data.money
							if (this.boxIndex == 1) {
								this.showPopup('goldCoinsPopup')
							} else {
								this.showPopup('goldCoinsPopup2')
							}
							this.getTask()
							this.getDailyTaskList()
						}
					})

					return 'goldCoinAndroidCallBack调用成功'
				}, 1000)
			}

		},
	}
</script>

<style>
	page {
		background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
	}
</style>

<style lang="scss" scoped>
	.head-title {
		width: 100%;
		font-family: PingFang SC;
		font-weight: 700;
		font-size: 32rpx;
		color: #5D3A1A;
		text-align: center;
	}

	.mine-rule {
		position: fixed;
		bottom: 10%;
		right: 0;
		z-index: 9999;
		width: 20px;
	}

	.mine-drag {
		position: fixed;
		// #ifdef H5
		bottom: 9%;
		// #endif

		// #ifndef H5
		bottom: 3%;
		// #endif



		left: 20rpx;
		z-index: 9999;

		.drag {
			position: relative;

			&-img {
				width: 180rpx;
			}

			&-time {
				position: absolute;
				bottom: 13rpx;
				left: 0;
				width: 100%;
				text-align: center;
				color: #fff;
				font-size: 30rpx;
			}
		}


	}


	.mine {
		position: relative;
		padding: 20rpx;


		&-bg {
			width: 100%;
			position: absolute;
			top: 0;
			left: 0;
		}


	}

	.money {
		position: relative;
		width: 100%;
		height: 250rpx;
		border-radius: 32rpx;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

		&-tag {
			position: absolute;
			top: 0;
			right: 0;
			width: 130rpx;
			z-index: 1;
		}

		&-img {
			border-radius: 32rpx;
			width: inherit;
			height: inherit;
		}

		&-info {
			width: inherit;
			height: inherit;
			background-image: linear-gradient(to bottom, #FFF6E4, #FFFFFF, #FFFFFF);
			border-radius: 32rpx;
			display: flex;
			padding: 20rpx;
			position: relative;

			&::after {
				content: '';
				position: absolute;
				top: 30%;
				right: 50%;
				width: 1upx;
				height: 100rpx;
				background-color: rgba(200,130,60,0.1);
			}

			&-item {
				flex: 1;
				padding: 30rpx;
				display: flex;
				flex-direction: column;
				justify-content: space-between;

				&-title {
					display: flex;
					align-items: center;
				}

				&-price {
					font-weight: bold;
					font-size: 60rpx;
					color: #5D3A1A;
				}

				&-tip {
					font-size: 20rpx;
					color: #8B6B4A;
				}
			}
		}
	}

	.exchange-info-image {
		width: 45rpx;
		height: 45rpx;
		margin-left: 92%;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.exchange-info-text {
		font-weight: 700;
		font-size: 34rpx;
		color: #5D3A1A;
		text-align: center;
	}

	.exchange-info-text2 {
		font-weight: 500;
		font-size: 26rpx;
		color: #8B6B4A;
		text-align: center;
		padding-top: 30rpx;
	}

	.exchange-info-input {
		display: flex;
		justify-content: center;
		padding-top: 20rpx;

		input {
			width: 528rpx;
			height: 64rpx;
			text-align: center;
			background: #FFFDF9;
			border-radius: 32rpx;
			border: 1px solid rgba(200,130,60,0.1);
		}
	}

	.exchange-info-button {
		margin: 35rpx auto;
		border-radius: 40rpx;
		width: 250rpx;
		height: 88rpx;
		background: linear-gradient(135deg, #E8783B, #F4A460);
		color: #FFFFFF;
		text-align: center;
		line-height: 88rpx;
	}

	.task {
		background-color: #FFFDF9;
		border-radius: 32rpx;
		padding: 10rpx 40rpx;
		margin: 20rpx 0;
		position: relative;
		z-index: 99;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

		&-title {
			display: flex;
			align-items: center;
			font-size: 35rpx;
			color: #E8783B;

			&-image {
				width: 40rpx;
				margin-right: 10rpx;
			}
		}


		&-info {
			display: flex;
			align-items: center;
			margin: 20rpx 0;

			&-box {
				flex: 1;

				&-title {
					line-height: 2;
					display: flex;
					align-items: center;
					font-weight: 700;
					font-size: 35rpx;
					color: #5D3A1A;

					.red-envelope {
						width: 40rpx;
						margin: 0 10rpx;
					}

					.gold-coin {
						width: 40rpx;
						margin: 0 10rpx;
					}

					.price {
						color: #E8783B;
					}

					.sub-price {
						color: #E8783B;
						font-size: 30rpx;
					}
				}

				&-sub-title {
					font-size: 28rpx;
					color: #8B6B4A;
				}
			}

			&-btn {
				width: 150rpx;
			}
		}

		&-sign-in {
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin: 20rpx 0;

			&-item {
				width: 80rpx;

				&-box {

					height: 90rpx;
					position: relative;

					&-bg {
						width: 100%;
						height: inherit;
						position: absolute;
						top: 0;
						left: 0;
					}

					&-text {
						width: 100%;
						text-align: center;
						padding-top: 3px;
						color: #FFF;
						position: absolute;
					}

					&-text2 {
						width: 100%;
						text-align: center;
						padding-top: 5px;
						color: #E8783B;
						position: absolute;
					}
				}

				&-text {
					color: #8B6B4A;
					font-size: 22rpx;
					margin-top: 10rpx;
					padding: 0 10rpx;
				}
			}
		}

		&-steps {
			width: 100%;
			margin: 20rpx 0;
		}

		&-progress {
			margin: 20rpx 0;
			display: flex;
			align-items: center;

			&-info {
				width: 50%;
				margin-right: 10rpx;

			}

			&-text {
				color: #8B6B4A;
				font-size: 28rpx;
			}
		}

		&-voide {
			width: 100%;
			display: flex;
			align-items: center;
			justify-content: space-between;

			&-item {
				width: calc(100% /3);
				padding: 10rpx;

				&-img {
					width: 100%;
					height: 250rpx;
					border-radius: 10rpx;
				}

				&-text {
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}
			}
		}

	}

	.gold-coins-popup {
		position: relative;

		&-image {
			width: 600rpx;
			height: 650rpx;
		}

		&-gold {
			position: absolute;
			top: 150rpx;
			left: 0;
			width: 100%;
			text-align: center;
			color: #FFF3BE;
			font-size: 45rpx;
			font-weight: bold;
		}

		&-gold2 {
			position: absolute;
			top: 180rpx;
			left: 0;
			width: 100%;
			text-align: center;
			color: #FFF3BE;
			font-size: 45rpx;
			font-weight: bold;
		}

		&-btn {
			position: absolute;
			bottom: 35rpx;
			left: 0;
			width: 100%;
			text-align: center;

			&-text {
				font-size: 35rpx;
				color: #fff;
				font-weight: bold;
			}
		}

		&-close {
			position: absolute;
			top: 0;
			right: 0;
			width: 50rpx;
		}
	}

	.sign-in-popup {
		position: relative;

		&-image {
			width: 650rpx;
			height: 800rpx;
		}

		&-close {
			position: absolute;
			top: 20rpx;
			right: 20rpx;
			width: 50rpx;
			z-index: 1;
		}

		&-box {
			position: absolute;
			top: 180rpx;
			left: 0;
			width: 100%;
			padding: 40rpx;

			&-title {
				margin-top: 20rpx;
				width: 100%;
				text-align: center;
				font-size: 35rpx;
				font-weight: 700;
				color: #5D3A1A;
			}

			&-sub-title {
				margin-top: 10rpx;
				width: 100%;
				text-align: center;
			}


			&-line {
				display: flex;
				align-items: center;
				justify-content: center;

				&-item {
					width: 110rpx;
					height: 170rpx;
					position: relative;
					margin: 10rpx;
					z-index: 1;
					text-align: center;
					display: flex;
					flex-direction: column;
					align-items: center;
					justify-content: space-between;
					padding: 10rpx;

					&-bg {
						position: absolute;
						left: 0;
						top: 0;
						width: inherit;
						height: inherit;
						z-index: -1
					}

					&-points {
						color: #E8783B;
					}

					&-day {
						color: #E8783B;
					}
				}

			}

			&-btn {
				margin-top: 20rpx;
				position: relative;
				z-index: 1;
				width: 100%;
				height: 90rpx;
				line-height: 90rpx;
				text-align: center;

				&-bg {
					position: absolute;
					left: 0;
					top: 0;
					width: inherit;
					height: inherit;
					z-index: -1
				}

				&-text {
					font-size: 35rpx;
					color: #fff;
					font-weight: bold;
				}
			}
		}
	}
</style>
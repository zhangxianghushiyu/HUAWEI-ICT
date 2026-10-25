<template>
	<view class="page">
		<!-- <view :style="{height:videoStyle.statusBarHeight }"></view> -->
		<view class="contentBox" v-if="contentShow " :style="{width:videoStyle.width,height:videoStyle.subHeight }">
			<swiper :disable-touch="isCanTauch" :style="{width:videoStyle.width,height:videoStyle.subHeight }" vertical
				:circular="circular" :duration="durationNum" @change="changeSwiper" @animationfinish="swiperVod"
				class="swiperBox" :current="currentIndex">
				<swiper-item v-for="(item,index) in vodList" :key="index" class="box">
					<view v-if="$store.state.isShow"
						:style="{opacity:shadeNum > 2 ?'0':'1',width:videoStyle.width,height:videoStyle.height }">
						<view class="vodPlayer">
							<video :ref="'myVideo'+index" :style="{width:videoStyle.width,height:videoStyle.height }"
								:src="item.videoUrl" :controls="true" :enable-progress-gesture="false"
								:show-loading="false" :show-play-btn="false" :show-center-play-btn="false"
								:show-fullscreen-btn="false" :vslide-gesture-in-fullscreen="false"
								:poster="item.titleImg" :show-progress="false" :object-fit="item.object_fit"
								:http-cache="true" :loop="loopPlay"
								:muted="(index == vodIndex && !muteSetup) ? false : true" :id="'myVideo'+index+swId"
								@play="startPlay(index)" @waiting="bufferVod(index)"
								@timeupdate="timeupdateVod($event,index)" @error="errVod($event,index)"
								@ended="endedVod(index)" @loadedmetadata="loadedMetadataVod($event,index)">
							</video>
						</view>
						<!-- 视频封面 -->
						<view class="vodPause-img" v-if="item.coverShow ">
							<image :src="item.titleImg" :mode="item.object_fit == 'cover'?'aspectFill':'widthFix'"
								class="cover-img"
								:style="{opacity:item.coverOpacity?1:0,width:videoStyle.width,height:item.object_fit == 'cover'?videoStyle.height:0}">
							</image>
						</view>

						<!-- 分享界面 -->
						<view class="share-img" v-if="item.shareType">
							<view class="share-title">
								<span>分享</span>
								<uni-icons @tap="item.shareType=false" type="closeempty" size="22"
									color="#000"></uni-icons>
							</view>
							<view class="share-img-info">
								<view class="share-img-info-item">
									<image @click="share(2)" class="share-img-item" src="/static/images/share1.png"
										mode="scaleToFill" />
									<span>朋友圈</span>
								</view>
								<view class="share-img-info-item" style="margin-left: 120rpx;">
									<image @click="share(1)" class="share-img-item" src="/static/images/share2.png"
										mode="scaleToFill" />
									<span>微信</span>
								</view>

							</view>
						</view>

						<!-- 购买界面 -->
						<view class="box-img" v-if=" item.coverShow  && !item.videoUrl&&!isShowReward">
							<image :src="item.titleImg" :mode="item.object_fit == 'cover'?'aspectFill':'widthFix'"
								class="cover-img"
								:style="{opacity:item.coverOpacity?1:0,width:videoStyle.width,height:item.object_fit == 'cover'?videoStyle.height:0}">
							</image>
							<view class="box-img-info">
								<view class="box-img-info-title">精彩剧情等你来解锁</view>
								<uni-icons @tap="backUrl()" style="position: absolute;top:45rpx;right:30rpx"
									type="closeempty" size="22"></uni-icons>
								<view class="box-img-info-btn" @click="lookAd(item,index)">
									<image src="/static/images/1.png" mode=""></image>
									<view
										style="display: flex;flex-direction: column;align-items: flex-start;margin-left: 20rpx;">
										<span style="font-weight: bold;font-size: 26rpx;color: #333333;">看广告解锁</span>
										<span
											style="font-weight: 500;font-size: 22rpx;color: #666666;">观看小视频解锁{{item.unlockCount}}集</span>
									</view>
								</view>
								<!-- <view class="box-img-info-btn" @click="bayThis(item,index)">购买本集解锁</view> -->
								<!-- <view class="box-img-info-btn" @click="toMember(item,index)">开通VIP解锁全部短剧</view> -->
								<view v-if="false" class="box-img-info-btn" @click="lookAd2(item,index)">
									<image src="/static/images/2.png" mode=""></image>
									<view
										style="display: flex;flex-direction: column;align-items: flex-start;margin-left: 20rpx;">
										<span style="font-weight: bold;font-size: 26rpx;color: #333333;">金币解锁</span>
										<span
											style="font-weight: 500;font-size: 22rpx;color: #666666;">使用{{userInfo.dramaUnlockMoney}}金币解锁{{item.unlockCount}}集</span>
									</view>
								</view>
							</view>
						</view>
						<!-- 解锁后的弹窗 -->
						<view class="showReward" v-if="isShowReward">
							<view v-if="showRewardGold" class="showRewardGold">
								<image @tap="closeReward()" class="showRewardBtn" src="/static/images/jiesuoBtn.png"
									mode="">
								</image>
								<image @tap="closeReward()" class="showRewardClose" src="/static/images/jiesuoClose.png"
									mode=""></image>
								<span class="rewardNum">{{showRewardNum}}</span>
							</view>
							<view v-else class="showRewardGold2">
								<image @tap="closeReward()" class="showRewardBtn" src="/static/images/jiesuoBtn.png"
									mode="">
								</image>
								<image @tap="closeReward()" class="showRewardClose" src="/static/images/jiesuoClose.png"
									mode=""></image>
							</view>
						</view>
						<!-- 暂停图标 -->
						<view class="vodPause-img" v-if="item.pauseShow">
							<image src="/static/icon/vod-play.png" class="vodPauseImage"></image>
						</view>
						<view @click="handClick($event,index)" @longpress="longpress(item)" class="vodPlayView"
							:style="videoStyle" @touchstart="vodViewStart($event)" @touchmove="vodViewMove($event)"
							@touchend="vodViewEnd($event)"></view>
						<!-- 底部标题、右侧操作栏 -->
						<videoMenu :ref="'menuRef'+index" :vodIndex="vodIndex" :vodCurIndex="vodCurIndex" :item="item"
							:discussNum="totalPlayList.length" :index="index" :sliderDrag="sliderDrag"
							:moveOpacity="moveOpacity" :palyCartoon="palyCartoon" @handleInfo="handleInfo"></videoMenu>
						<view v-if="!isShow"
							style="z-index: 999;letter-spacing: 8rpx; font-weight: bold;font-size: 32rpx;color: #FFFFFF;position: absolute;top:20rpx;left:100rpx">
							<!-- <span>第{{item.gather}}集</span> -->
						</view>
						<view @tap="openRateBox()"
							style="z-index: 999;display: flex;align-items: center;  position: absolute;top:50px;right:40rpx">
							<image style="width: 38rpx;height: 38rpx;" src="/static/juhe/2.png" mode=""></image>
							<span
								style="font-weight: bold; margin-left: 10rpx; font-size: 26rpx;color: #FFFFFF;">倍速</span>
						</view>
					</view>
					<!-- 底层图标 -->
					<view class="vodLayer" v-if="$store.state.isShow">
						<image src="/static/icon/sound.png" mode="" class="vodLayer-img"></image>
					</view>
					<view v-else
						:style="{opacity:shadeNum > 2 ?'0':'1',width:videoStyle.width,height:videoStyle.height }">
						<image :src="item.videoCover" mode="scaleToFill"
							:style="{width:videoStyle.width,height:videoStyle.height }"></image>
					</view>
				</swiper-item>
			</swiper>
			<!-- 遮罩层，防止滑动过快 -->
			<swiper class="shade-swiper"
				:style="{width:videoStyle.width,height:videoStyle.height,pointerEvents:showShade?'auto':'none',opacity:showShade?'1':'0'}"
				vertical>
				<swiper-item v-for="(item,index) in 1" :key="index" class="box">
					<!-- 底层图标 -->
					<view class="vodLayer">
						<image src="/static/icon/sound.png" mode="" class="vodLayer-img"></image>
					</view>
				</swiper-item>
			</swiper>
			<!-- 底部加载动画 -->
			<view class="pullup" v-if="loadShow">
				<view style="width: 32rpx;height: 32rpx;margin-right: 30rpx;">
					<view class="k-ball7a" style="width: 20rpx;height: 20rpx;"></view>
					<view class="k-ball7b" style="width: 20rpx;height: 20rpx;"></view>
					<view class="k-ball7c" style="width: 20rpx;height: 20rpx;"></view>
					<view class="k-ball7d" style="width: 20rpx;height: 20rpx;"></view>
				</view>
			</view>
			<view class="flooter" :style="{opacity:moveOpacity ? 0 : 1,bottom:speedBottom+'px'}"
				v-if="vodList.length > 0 && beforeVodInfo.sliderShow">
				<!-- 滑动进度条显示时间 -->
				<view class="sliderData" v-if="sliderDrag">
					<view class="slider-text">
						<text>{{formatSeconds(sliderTime)}}</text>
						<text style="font-size: 30rpx;margin: 0 10rpx;">/</text>
						<text style="opacity: 0.5;">{{formatSeconds(videoTime)}}</text>
					</view>
				</view>
				<!-- 进度条 -->
				<view class="sliderBox" :style="{opacity:shadeNum > 2 ?'0':'1'}" @touchmove.stop="touchmoveSlider"
					@touchstart.stop="touchmoveSlider" @touchend.stop="touchendSlider" v-if="false">
					<view class="slider-item" :style="{height: brightSlider?'18px':'2px'}">
						<!-- 底层 -->
						<view class="slider-box" :class="[brightSlider?'slider-box-active':'']"></view>
						<!-- 滑块 -->
						<!-- <view class="slider-block" :class="[brightSlider?'slider-block-active':'']" :style="{transform:`translateX(${sliderProgress}px)`}"></view> -->
						<!-- 滑动层 -->
						<view class="slider-load" :class="[brightSlider?'slider-load-active':'']"
							:style="{transform: `translateX(${sliderProgress > 0 ? sliderProgress - screenWidth : -screenWidth}px)`}">
						</view>
					</view>
				</view>
				<view v-else :style="{width:screenWidth+'px',height:'18px'}"></view>
				<!-- loading加载动画进度 -->
				<view class="loadSlider" v-if="vodList.length > 0 && beforeVodInfo.loadingShow">
					<view :style="{opacity:brightSlider ? 0 : 1}">
						<view class="loading-slider" ref="loadSlider" :style="{width:screenWidth+'px'}"></view>
					</view>
				</view>
			</view>
		</view>
		<!-- 刷新 -->
		<view class="refresh" v-if="refreshShow">
			<view style="height: 140rpx;"></view>
			<!--  :style="{opacity:refreshOpacity,transform: 'translateY('+refreshclientY+'px)'}" -->
			<view class="refreshBox" :style="{opacity:refreshOpacity}">
				<view style="width: 32rpx;height: 32rpx;"></view>
				<view>下拉刷新内容</view>
				<view style="width: 32rpx;height: 32rpx;margin-right: 30rpx;">
					<view class="k-ball7a k-paused" style="width: 20rpx;height: 20rpx;"
						:class="[refreshOpen?'k-running':'']"></view>
					<view class="k-ball7b k-paused" style="width: 20rpx;height: 20rpx;"
						:class="[refreshOpen?'k-running':'']"></view>
					<view class="k-ball7c k-paused" style="width: 20rpx;height: 20rpx;"
						:class="[refreshOpen?'k-running':'']"></view>
					<view class="k-ball7d k-paused" style="width: 20rpx;height: 20rpx;"
						:class="[refreshOpen?'k-running':'']"></view>
				</view>
			</view>
		</view>
		<!-- 点赞桃心 -->
		<view class="doubleImage" v-for="(item,index) in likeList" :key="index" :ref="item.id+'Ref'"
			:class="[item.className]" :style="{width: item.width,height:item.height,top:item.top,left:item.left}">
			<image src="/static/icon/selectTaoxin.png" v-if="item.isShow"
				:style="{width: item.width,height:item.height,transform: `rotate(${item.rotate})`}" />
		</view>
		<!-- 播放速率设置 -->
		<view class="rateBox" v-if="isShowRateBox">
			<view class="rateBox-title">
				<uni-icons @tap="isShowRateBox=false" style="margin-left: 75rpx;" type="down" size="22"></uni-icons>
				<span style="margin-left: 240rpx;">倍速</span>
			</view>
			<view class="rateBox-body">
				<view @tap="chooseRate(index)" class="rateBox-item" v-for="(item,index) in rateList">
					<span :class="{rateBoxItemSelected:defaultRateIndex==index}">{{item}}x{{index==1?'默认':''}}</span>
					<image v-show="defaultRateIndex==index" src="/static/juhe/8.png" mode=""></image>
				</view>
			</view>
		</view>
		<!-- 宝箱 -->
		<view class="mine-drag" v-if="false">
			<view class="drag" @click="receiveBox(1)">
				<image class="drag-img" src="/static/juhe/14.png" mode="widthFix"></image>
				<view v-if="boxDown == 0" class="drag-time">可领取</view>
				<view v-else class="drag-time">{{boxDown}}秒</view>
			</view>
		</view>
	</view>
</template>
<script>
	import videoJs from './video-v-encipher.js'
	import videoMenu from '../tsp-menu/tsp-menu-v.vue'
	export default {
		components: {
			videoMenu
		},
		mixins: [videoJs],
	}
</script>

<style lang="scss">
	.page {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		overflow: hidden;
	}

	.contentBox {
		position: relative;
	}

	.shade-swiper {
		position: absolute;
		pointer-events: none;
		opacity: 0;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 99;
	}

	.swiperBox {
		position: relative;
		z-index: 2;
	}

	.box {
		position: relative;
	}

	.vodLayer {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 1;
		background-color: #000000;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.vodLayer-img {
		width: 150rpx;
		height: 150rpx;
		opacity: 0.8;
	}

	.cover-img {
		width: 750rpx;
		height: auto;
	}


	.vodPlayer {
		position: absolute;
		top: 0;
		left: 0;
		z-index: 2;
		width: 100%;
		height: 100%;
	}

	.vodPlayView {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.1);
		z-index: 7;
	}

	.vodPause-img {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.1);
		z-index: 4;
		display: flex;
		align-items: center;
		justify-content: center;
		overflow: hidden;
	}

	.box-img {
		position: absolute;
		position: relative;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.1);
		z-index: 999;
		display: flex;
		align-items: center;
		justify-content: center;
		overflow: hidden;
	}

	.showReward {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 999999;
		overflow: hidden;
	}

	.showRewardGold {
		width: 750rpx;
		height: 531rpx;
		background: url('/static/images/jiesuo2.png');
		background-size: cover;
		display: flex;
		top: 20%;
		flex-direction: column;
		align-items: center;
		position: relative;
	}

	.rewardNum {
		font-size: 56rpx;
		color: #E8DE3A;
		top: 63%;
		position: absolute;
	}

	.showRewardGold2 {
		width: 750rpx;
		height: 531rpx;
		background: url('/static/images/jiesuo1.png');
		background-size: cover;
		display: flex;
		top: 20%;
		flex-direction: column;
		align-items: center;
		position: relative;
	}

	.showRewardBtn {
		width: 321rpx;
		height: 83rpx;
		top: 90%;
		position: absolute;
	}

	.showRewardClose {
		width: 42rpx;
		height: 42rpx;
		top: 20%;
		right: 15%;
		position: absolute;
	}

	.box-img-info {
		position: absolute;
		width: 550rpx;
		background-color: #F8F8F8;
		border-radius: 20rpx;
		padding: 50rpx;
	}

	.share-img {
		position: absolute;
		height: 400rpx;
		left: 0;
		right: 0;
		bottom: 0rpx;
		z-index: 999999999;
		background: #FFFFFF;
		border-radius: 50rpx 50rpx 0rpx 0rpx;
	}

	.share-title {
		width: 50%;
		margin-top: 20rpx;
		margin-left: 45%;
		font-weight: bold;
		font-size: 32rpx;
		color: #333333;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.share-img-info {
		display: flex;
		align-items: center;
		justify-content: space-between;
		width: 300rpx;
		border-radius: 30rpx;
		padding: 90rpx;
	}

	.share-img-info-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		font-weight: 500;
		font-size: 22rpx;
		color: #333333;
	}

	.share-img-item {
		width: 80rpx;
		height: 80rpx;
	}

	.box-img-info-title {
		line-height: 2;
		color: #000;
		font-size: 28rpx;
		font-weight: bold;
		text-align: center;
	}

	.box-img-info-btn {
		width: 100%;
		height: 106rpx;
		background: #FFF3EB;
		border-radius: 53rpx;
		padding: 20rpx;
		margin-top: 20rpx;
		font-size: 25rpx;
		text-align: center;
		display: flex;

		image {
			width: 52rpx;
			height: 52rpx;
			margin-left: 40rpx;
		}
	}

	.vodPauseImage {
		width: 134rpx;
		height: 134rpx;
		opacity: 0.35;
		animation: vodPauseName 0.1s linear forwards;
	}

	@keyframes vodPauseName {
		0% {
			transform: scale(2);
			opacity: 0;
		}

		100% {
			transform: scale(1);
			opacity: 0.35;
		}
	}

	/* loading动画 */
	.loadingView {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 100;
		display: flex;
		align-items: center;
		justify-content: center;
		background-color: #000000;
	}

	.k-ball7a {
		border: 0;
		margin: 0;
		width: 25rpx;
		height: 25rpx;
		position: absolute;
		border-radius: 50%;
		animation: k-loadingO 2s ease infinite;
		background: #4CD964;
		animation-delay: -1.5s
	}

	.k-ball7b {
		border: 0;
		margin: 0;
		width: 25rpx;
		height: 25rpx;
		position: absolute;
		border-radius: 50%;
		animation: k-loadingO 2s ease infinite;
		background: #F63D3A;
		animation-delay: -1s
	}

	.k-ball7c {
		border: 0;
		margin: 0;
		width: 25rpx;
		height: 25rpx;
		position: absolute;
		border-radius: 50%;
		animation: k-loadingO 2s ease infinite;
		background: #FDA543;
		animation-delay: -0.5s
	}

	.k-ball7d {
		border: 0;
		margin: 0;
		width: 25rpx;
		height: 25rpx;
		position: absolute;
		border-radius: 50%;
		animation: k-loadingO 2s ease infinite;
		background: #5C6BC0
	}

	.k-paused {
		animation-play-state: paused;
	}

	.k-running {
		animation-play-state: running;
	}

	@keyframes k-loadingO {

		0%,
		100% {
			transform: translate(0)
		}

		25% {
			transform: translate(160%)
		}

		50% {
			transform: translate(160%, 160%)
		}

		75% {
			transform: translate(0, 160%)
		}
	}

	/* 进度条 */
	.flooter {
		position: absolute;
		bottom: 0;
		left: 0;
		z-index: 1100;
		transition: all 0.3s linear;
	}

	.sliderBox {
		position: relative;
		min-height: 18px;
		width: 750rpx;
	}

	.slider-item {
		width: 750rpx;
		min-height: 2px;
		position: absolute;
		left: 0;
		bottom: 0;
		overflow: hidden;
	}

	.slider-box {
		height: 2px;
		background-color: #C8C7CC;
		opacity: 0.2;
		border-radius: 2px;
	}

	.slider-block {
		position: absolute;
		width: 5px;
		height: 5px;
		border-radius: 5px;
		background-color: #FFFFFF;
		top: -1px;
		left: 0;
		z-index: 3;
		opacity: 0.5;
	}

	.slider-load {
		position: absolute;
		width: 100%;
		bottom: 0;
		height: 2px;
		background-color: #FFFFFF;
		opacity: 0.4;
		z-index: 3;
		border-radius: 2px;
		transition: all 0.02s linear;
	}

	.slider-box-active {
		position: absolute;
		width: 100%;
		bottom: 0;
		height: 7px;
		background-color: #C8C7CC;
		opacity: 0.4;
		border-radius: 7px;
		transition: height 0.2s linear;
	}

	.slider-block-active {
		position: absolute;
		top: 8px;
		width: 15px;
		height: 15px;
		border-radius: 15px;
		opacity: 1;
		left: -3px;
		background-color: #FFFFFF;
	}

	.slider-load-active {
		width: 100%;
		opacity: 1;
		height: 7px;
		border-radius: 7px;
		transition: height 0.2s linear;
	}

	.sliderData {
		width: 750rpx;
		height: 200rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: center;
		color: #FFFFFF;
		font-size: 50rpx;
		font-weight: bold;
	}

	.slider-text {
		flex-direction: row;
		display: flex;
		align-items: center;
	}

	/* 下拉刷新 */
	.refresh {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		z-index: 15;
	}

	.refreshBox {
		height: 100rpx;
		color: #FFFFFF;
		font-size: 33rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-between;
		padding: 0 30rpx;
		/* transform: translateY(-30px); */
		transition: all 0.01s linear;
		opacity: 0;
	}

	.pullup {
		position: absolute;
		left: 0;
		bottom: 0;
		z-index: 1;
		height: 200rpx;
		opacity: 1;
		width: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.loadSlider {
		position: absolute;
		bottom: 0px;
		width: 750rpx;
		height: 2px;
		align-items: center;
	}

	.loading-slider {
		width: 750rpx;
		height: 2px;
		background-color: #FFFFFF;
		opacity: 0.6;
		animation: sliderName 0.4s linear infinite;
	}

	@keyframes sliderName {
		0% {
			opacity: 0.9;
			transform: scale(0.4);
		}

		100% {
			opacity: 0.1;
			transform: scale(1);
		}
	}

	.doubleImage {
		pointer-events: none;
		position: absolute;
		top: 0;
		left: 0;
		z-index: 20;
		/* width: 170rpx;
		height: 170rpx; */
		animation: doubleName 0.3s linear forwards;
		transform: scale(0);
		opacity: 0;
	}

	@keyframes doubleName {
		0% {
			transform: scale(1.6);
			opacity: 0.8;
		}

		50% {
			transform: scale(1);
			opacity: 1;
		}

		75% {
			transform: scale(1.02);
			opacity: 1;
		}

		100% {
			transform: scale(1);
			opacity: 1;
		}
	}

	.doubleImageRemove {
		animation: doubleRemove 0.4s linear forwards;
	}

	@keyframes doubleRemove {
		0% {
			transform: scale(1) translateY(0rpx);
			opacity: 1;
		}

		50% {
			transform: scale(1.35) translateY(-50rpx);
			opacity: 1;
		}

		100% {
			transform: scale(1.8) translateY(-100rpx);
			opacity: 0;
		}
	}

	.rateBox {
		position: absolute;
		bottom: 0;
		width: 750rpx;
		height: 730rpx;
		z-index: 9999;
		background: #FFFFFF;
		border-radius: 50rpx 50rpx 0rpx 0rpx;

		.rateBox-title {
			display: flex;
			margin-top: 20rpx;
			align-items: center;
			font-weight: bold;
			font-size: 32rpx;
			color: #333333;
		}

		.rateBox-body {
			position: absolute;
			bottom: 10rpx;
			top: 150rpx;
			left: 20rpx;
			right: 20rpx;
			display: flex;
			justify-content: space-around;
			flex-direction: column;

			.rateBox-item {
				width: 85%;
				margin: auto;
				display: flex;
				align-items: center;
				justify-content: space-between;
				font-weight: bold;
				font-size: 28rpx;
				color: #333333;

				image {
					width: 32rpx;
					height: 22rpx;
				}

				.rateBoxItemSelected {
					font-weight: bold;
					font-size: 28rpx;
					color: #FE6100;
				}
			}
		}
	}

	.mine-drag {
		position: fixed;
		top: 500rpx;
		left: 30rpx;
		z-index: 9999;

		.drag {
			position: relative;

			&-img {
				width: 96rpx;
				height: 107rpx;
			}

			&-time {
				position: absolute;
				bottom: 13rpx;
				left: 0;
				width: 100%;
				text-align: center;
				font-weight: bold;
				font-size: 18rpx;
				color: #783A00;
			}
		}


	}
</style>
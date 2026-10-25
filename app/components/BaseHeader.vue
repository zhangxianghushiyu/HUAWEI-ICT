<template>
	<view>
		<view v-if="isLabel">
			<view :style="{'height':`${topNum + contentNum}px`}"></view>
			<view class="regular" :style="colorObject">
				<image v-if="bgImageUrl!=''" class="bg-img" :style="{'height':`${topNum + contentNum}px`}"
					:src="bgImageUrl" mode="scaleToFill"></image>
				<view :style="{'height':`${topNum}px`}"></view>
				<view class=" slot " :style="{'height':`${contentNum}px`,'lineHeight':`${contentNum}px`}">
					<slot></slot>
				</view>
			</view>
		</view>

		<!-- #ifndef MP-WEIXIN -->
		<view v-else style="height: calc(var(--status-bar-height);" />
		<!-- #endif -->

		<!-- #ifdef MP-WEIXIN -->
		<view v-else>
			<view :style="{'height':`${topNum + contentNum}px`}"></view>
			<view class="regular" :style="colorObject">
				<image v-if="bgImageUrl!=''" class="bg-img" :style="{'height':`${topNum + contentNum}px`}"
					:src="bgImageUrl" mode="scaleToFill"></image>
				<view :style="{'height':`${topNum}px`}"></view>
				<view class=" slot " :style="{'height':`${contentNum}px`,'lineHeight':`${contentNum}px`}">
					<slot></slot>
				</view>
			</view>
		</view>
		<!-- #endif -->
	</view>
</template>

<script>
	export default {
		props: {
			//样式
			colorObject: {
				type: Object,
				default: () => ({})
			},
			//背景图片
			bgImageUrl: {
				type: String,
				default: () => ('')
			},
			//是否带label
			isLabel: {
				type: Boolean,
				default: () => (true)
			},
		},
		data() {
			return {
				topNum: 0, // 状态栏高度
				contentNum: 0, // 导航栏高度
				leftNum: 0, // 导航栏右边距
				rightNum: 0, // 胶囊宽度 + 边距
				unread: 0,
			}
		},
		created() {


			let systemInfo = uni.getSystemInfoSync();
			this.topNum = systemInfo.statusBarHeight;



			// #ifndef MP-WEIXIN
			this.contentNum = 40
			this.leftNum = 0
			this.rightNum = 0
			// #endif
			// #ifdef MP-WEIXIN
			let menuButtonInfo = uni.getMenuButtonBoundingClientRect();
			this.contentNum = menuButtonInfo.height + (menuButtonInfo.top - this.topNum) * 2;
			this.leftNum = systemInfo.screenWidth - menuButtonInfo.right;
			this.rightNum = menuButtonInfo.width + (this.leftNum * 2);
			// #endif

			const data = {
				contentNum: this.contentNum,
				leftNum: this.leftNum,
				rightNum: this.rightNum,
				topNum: this.topNum
			}

			uni.setStorageSync('HeaderInfo', data)
		},

		methods: {
			back() {
				uni.navigateBack()
			},
			// 防报错
			toJSON() {}
		}
	}
</script>

<style lang="scss" scoped>
	.regular {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		color: #fff;
		background: #fff;
		z-index: 100;

		.bg-img {
			position: absolute;
			width: 100%;
			z-index: -1;
		}

		.slot {
			display: flex;
			align-items: center;
			justify-content: center;

			z-index: 1;
		}
	}
</style>
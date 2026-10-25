<template>
	<view class="main" @touchmove.stop.prevent="moveScroll">
		<image @tap="back()" class="back" src="/static/zhuanpan/back.png" mode=""></image>
		<view class="title">
			<span style="font-weight: bold;font-size: 80rpx;color: #A44205;">锦鲤转盘</span>
			<span style="font-weight: 400;font-size: 32rpx;color: #A44205;">Lucky wheel</span>
		</view>
		<view class="body">
			<view class="zhuanpan-item" v-for="(item,index) in prizes" :style="getItemStyle(index)">
				<text class="prize-text">{{item.name}}</text>
			</view>
			<image id="pointer" class="pointer" :style="pointerStyle" src="/static/zhuanpan/4.png" mode="">
			</image>
			<view class="nums">
				<span style="font-size: 24rpx;">剩余</span>
				<span style="font-size: 36rpx;">{{nums}}</span>
			</view>
		</view>
		<view class="bottom">
			<image @tap="clickItem(0)" class="left" src="/static/zhuanpan/2.png" mode=""></image>
			<image @tap="clickItem(1)" class="middle" src="/static/zhuanpan/1.png" mode=""></image>
			<image @tap="clickItem(2)" class="right" src="/static/zhuanpan/3.png" mode=""></image>
		</view>
		<view class="popBox" v-if="isShowPop">
			<view class="noNums" v-if="showNoNums">
				<view class="noNums-item1"></view>
				<view class="noNums-item2">
					<span>前往任务中心获取</span>
				</view>
				<image class="noNums-close" src="/static/zhuanpan/close.png" mode=""></image>
			</view>
			<view class="award" v-if="showAward">
				<view class="award-body">
					<span style="margin-top: 82rpx;">恭喜获得</span>
					<span>天猫精灵印象</span>
					<image src="/static/2.png" mode=""></image>
					<view class="award-body-btn">
						<span>领取奖品</span>
					</view>
				</view>
				<image class="award-close" src="/static/zhuanpan/close.png" mode=""></image>
			</view>
		</view>
		<view class="nameList">
			<view class="nameList-title">
				<span>中奖名单</span>
			</view>
			<view class="nameList-list">
				<span>欢乐兔子****</span>
				<span>抽中了 888 元红包</span>
			</view>
			<view class="nameList-list">
				<span>欢乐兔子****</span>
				<span>抽中了 888 元红包</span>
			</view>
			<view class="nameList-list">
				<span>欢乐兔子****</span>
				<span>抽中了 888 元红包</span>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				prizes: [],
				nums: 0,
				isShowPop: true,
				showNoNums: false,
				showAward: true,
				rotating: false,
				rotateDeg: 0,
				resultIndex: 0,
				resetting: false,
				winingListData: [{
					name: ''
				}]
			};
		},
		onLoad() {
			this.getUserNums()
			this.getAwards()
			// this.getWinningList()
		},
		computed: {
			pointerStyle() {
				return {
					transform: `translate(-50%, -50%) rotate(${this.rotateDeg}deg)`,
					transition: this.resetting ?
						'transform 0.5s ease-out' : (this.rotating ? 'transform 4s cubic-bezier(0.17, 0.67, 0.21, 0.99)' :
							'none')
				}
			}
		},
		methods: {
			//获取用户抽奖次数
			getUserNums() {
				this.$api.userTurnableNums().then(res => {
					this.nums = res.data.num
				})
			},
			//获取奖品列表
			getAwards() {
				this.$api.awardsList().then(res => {
					for (let item of res.data) {
						this.prizes.push({
							id: item.id,
							name: item.name
						})
					}
				})
			},
			//获取中奖名单
			getWinningList() {
				this.$api.winningList({
					pageNum: 1,
					pageSize: 10
				}).then(res => {
					console.log(res);
				})
			},
			moveScroll() {

			},
			getItemStyle(index) {
				const angle = 360 / this.prizes.length;
				return {
					transform: `rotate(${angle * index}deg)`,
					'--item-angle': `${angle}deg`
				}
			},
			//返回
			back() {
				uni.navigateBack()
			},
			clickItem(index) {
				if (index == 1) {
					if (this.nums >= 1) {
						this.$api.turntableResult().then(res => {
							if (this.rotating || this.resetting) return;
							this.resetting = true;
							this.rotateDeg = 0;
							const index = this.prizes.findIndex(item => item.id == res.data.id)
							// 重置完成后开始旋转
							setTimeout(() => {
								this.resetting = false;
								this.start(index)
								this.getUserNums()
							}, 500);
							// const img = document.getElementById('pointer');
							// img.style.transform = 'rotate(0deg)';

						})
					} else {
						this.isShowPop = true
						this.showNoNums = true
					}
				}
			},
			//开始
			start(index) {
				if (this.rotating) return;
				this.rotating = true;
				// 随机选择一个奖品
				// this.resultIndex = Math.floor(Math.random() * this.prizes.length);
				this.resultIndex = index
				// 计算需要旋转的角度 (多转几圈再停在目标位置)
				const anglePerItem = 360 / this.prizes.length;
				const targetRotate = 360 * 8 + (anglePerItem * this.resultIndex + anglePerItem / 2);
				this.rotateDeg = targetRotate;
				// 旋转结束后重置状态
				setTimeout(() => {
					this.rotating = false;
					uni.showToast({
						title: `恭喜获得: ${this.prizes[this.resultIndex].name}`,
						icon: 'none'
					});
				}, 4000);
			}
		}
	}
</script>

<style lang="scss" scoped>
	.main {
		width: 100vw;
		height: 100vh;
		position: fixed;
		top: 0;
		left: 0;
		bottom: 0;
		right: 0;
		overflow: hidden;
		background: url('/static/bg.png');
		background-size: cover;

		.back {
			width: 52rpx;
			height: 52rpx;
			position: absolute;
			left: 30rpx;
			top: 60rpx;
		}

		.title {
			display: flex;
			flex-direction: column;
			align-items: center;
			margin-top: 30rpx;
		}

		.body {
			width: 647rpx;
			height: 647rpx;
			z-index: 999;
			position: absolute;
			left: 51rpx;
			top: 200rpx;
			background: url('/static/zhuanpan/5.png');
			background-size: cover;

			.zhuanpan-item {
				position: absolute;
				width: 50%;
				height: 50%;
				left: 50%;
				top: 0;
				transform-origin: left bottom;
				display: flex;
				align-items: center;
				box-sizing: border-box;
				padding-left: 30px;

				.prize-text {
					font-size: 32rpx;
					color: #E8783B;
					position: absolute;
					top: 90rpx;
					left: 50rpx;
					transform: rotate(calc(var(--item-angle) / 2));
				}
			}

			.pointer {
				width: 164rpx;
				height: 213rpx;
				position: absolute;
				top: 50%;
				left: 50%;
				transform: translate(-50%, -50%);
				transform-origin: 50% 60%;
			}

			.nums {
				display: flex;
				flex-direction: column;
				align-items: center;
				position: relative;
				top: 310rpx;
				z-index: 99999;
				color: #fff
			}
		}

		.bottom {
			width: 750rpx;
			height: 1236rpx;
			top: 200rpx;
			left: 0rpx;
			position: absolute;
			background: url('/static/zhuanpan/6.png');
			background-size: cover;

			.left {
				width: 122rpx;
				height: 122rpx;
				position: relative;
				left: 60rpx;
				top: 710rpx;
				background: url('/static/zhuanpan/2.png');
				background-size: cover;
			}

			.middle {
				width: 224rpx;
				height: 224rpx;
				position: relative;
				left: 140rpx;
				top: 680rpx;
				background: url('/static/zhuanpan/1.png');
				background-size: cover;
			}

			.right {
				width: 122rpx;
				height: 122rpx;
				position: relative;
				left: 220rpx;
				top: 710rpx;
				background: url('/static/zhuanpan/3.png');
				background-size: cover;
			}
		}

		.popBox {
			width: 100vw;
			height: 100vh;
			overflow: hidden;
			position: fixed;
			top: 0;
			z-index: 9999;
			background: rgba(0, 0, 0, 0.5);

			.noNums {
				width: 580rpx;
				height: 356rpx;
				position: relative;
				display: flex;
				flex-direction: column;
				align-items: center;
				margin-left: 85rpx;
				margin-top: 70%;
				background: linear-gradient(180deg, #FFFDF9 0%, #FFF0E0 100%);
				border-radius: 40rpx 40rpx 40rpx 40rpx;

				.noNums-item1 {
					width: 382rpx;
					height: 91rpx;
					margin-top: 80rpx;
					background: url('/static/zhuanpan/7.png');
					background-size: cover;
				}

				.noNums-item2 {
					width: 502rpx;
					height: 96rpx;
					margin-top: 48rpx;
					text-align: center;
					font-size: 32rpx;
					color: #FFFFFF;
					line-height: 96rpx;
					background: linear-gradient(135deg, #E8783B, #F4A460);
					border-radius: 200rpx 200rpx 200rpx 200rpx;
				}

				.noNums-close {
					position: absolute;
					top: 30rpx;
					right: 30rpx;
					width: 52rpx;
					height: 52rpx;
				}
			}

			.award {
				width: 570rpx;
				height: 759rpx;
				margin-left: 85rpx;
				margin-top: 50%;
				position: relative;
				background: linear-gradient(180deg, #FFFDF9 0%, #FFF0E0 100%);
				border-radius: 40rpx 40rpx 40rpx 40rpx;

				.award-body {
					display: flex;
					flex-direction: column;
					align-items: center;
					font-size: 32rpx;
					color: #5D3A1A;

					image {
						width: 470rpx;
						height: 325rpx;
						margin-top: 70rpx;
					}

					.award-body-btn {
						width: 434rpx;
						height: 92rpx;
						margin-top: 52rpx;
						text-align: center;
						line-height: 92rpx;
						font-size: 32rpx;
						color: #FFFFFF;
						background: linear-gradient(135deg, #E8783B, #F4A460);
						border-radius: 200rpx 200rpx 200rpx 200rpx;
					}
				}

				.award-close {
					position: absolute;
					top: 30rpx;
					right: 30rpx;
					width: 52rpx;
					height: 52rpx;
				}
			}
		}

		.nameList {
			position: absolute;
			bottom: 0rpx;
			left: 60rpx;
			right: 60rpx;
			height: 14vh;
			display: flex;
			flex-direction: column;
			align-items: center;

			.nameList-title {
				font-size: 30rpx;
				color: #A44205;
			}

			.nameList-list {
				font-size: 28rpx;
				color: #A44205;
			}
		}
	}
</style>
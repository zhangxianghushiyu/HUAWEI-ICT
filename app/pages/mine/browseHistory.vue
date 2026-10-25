<template>
	<view class="browse">
		<uv-navbar placeholder title="浏览记录" :titleStyle="{fontWeight: 'bold'}" :rightText="rightText" @rightClick="rightClick"
			@leftClick="leftClick"></uv-navbar>
		<!-- 占位置 -->
		<view class="" style="height: 40rpx;"> </view>
		<view class="browse-list" v-for="(item,index) in list" :key="index">
			<view class="" style="display: flex;width: 80%;">
				<image :src="item.img" class="browse-img" mode="scaleToFill"></image>
				<view class="browse-text">{{item.name}}</view>
			</view>
			<view v-if="rightText == '编辑'" class="" style="font-size: 26rpx;color: #666666;text-align: center;">
				<image src="/static/images/star.png" style="width: 40rpx;height: 40rpx;" mode="scaleToFill"></image>
				<view class="" style="">收藏</view>
			</view>
			<view class="" v-else>
				<image style="width: 42rpx;height: 42rpx;" src="@/static/images/cli-gray.png" mode="scaleToFill">
				</image>
			</view>
		</view>

		<view class="browse-bottom" v-if="rightText == '退出'">
			<view class="browse-bottom-item">全选</view>
			<view class="browse-bottom-item" @click="open">删除</view>
		</view>

		<uv-popup ref="popup">
			<view class="popup-box">
				<view class="popup-box-text">确认要删除这条记录吗?此操作无法撤销</view>
				<view style="display: flex;align-items: center;justify-content: center;">
					<view class="popup-box-left" hover-class="btn-hover" @click="close">取消</view>
					<view class="" style="width:20rpx"></view>
					<view class="popup-box-right" hover-class="btn-hover">确定</view>
				</view>
			</view>
		</uv-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				rightText: '编辑',
				list: []
			}
		},
		onShow() {

		},
		methods: {
			leftClick() {
				uni.navigateBack()
			},
			rightClick() {
				if (this.rightText == '编辑') {
					this.rightText = '退出'
				} else {
					this.rightText = '编辑'
				}
			},
			open() {
				this.$refs.popup.open('center');
			},
			close(){
				this.$refs.popup.close();
			}
		}
	}
</script>

<style lang="scss" scoped>
	.browse {
		padding: 0 32rpx;
		box-sizing: border-box;
		position: relative;

		&-list {
			margin-bottom: 40rpx;
			display: flex;
			align-items: center;
			justify-content: space-between;
		}

		&-img {
			width: 148rpx;
			height: 212rpx;
			background: #E8783B;
			border-radius: 8rpx;
			margin-right: 27rpx;
		}

		&-text {
			margin-top: 55rpx;
			font-weight: 700;
			font-size: 30rpx;
			color: #5D3A1A;
		}

		&-bottom {
			position: fixed;
			bottom: 0;
			left: 0;
			width: 100%;
			height: 98rpx;
			background: #FFFDF9;
			display: flex;
			align-items: center;

			&-item {
				width: 50%;
				text-align: center;
				line-height: 98rpx;
				font-weight: 700;
				font-size: 30rpx;
				color: #5D3A1A;
			}
		}
	}

	/deep/ .uv-popup__content{
		background-color: transparent !important;
	}

	.popup-box{
		width: 580rpx;
		height: 330rpx;
		background: #FFFDF9;
		border-radius: 30rpx;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
		padding: 71rpx 31rpx 33rpx;
		&-text{
			width: 355rpx;
			margin: 0 auto 44rpx;
			text-align: center;
			font-weight: 700;
			font-size: 34rpx;
			color: #5D3A1A;
		}
		&-left{
			width: 250rpx;
			height: 88rpx;
			background: #FFFDF9;
			border-radius: 44rpx;
			border: 1px solid #E8783B;

			font-weight: 500;
			font-size: 30rpx;
			color: #E8783B;
			text-align: center;
			line-height: 88rpx;
		}
		&-right{
			text-align: center;
			line-height: 88rpx;
			width: 250rpx;
			height: 88rpx;
			background: linear-gradient(135deg, #E8783B, #F4A460);
			border-radius: 44rpx;
			font-weight: 500;
			font-size: 30rpx;
			color: #fff;
		}

	}

	.btn-hover{
		opacity: 0.8;
	}
</style>
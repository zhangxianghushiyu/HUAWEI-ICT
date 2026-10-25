<template>
	<view class="content">
		<view class="line"></view>
		<view class="fillInCode" v-if="isShowFillIn">
			<span style="font-size: 40rpx;margin-top: 118rpx;">填写好友邀请码</span>
			<view class="fillInCode-bg">
				<input maxlength="20" type="text" value="" v-model="inviteCode" placeholder="请输入邀请码" />
			</view>
			<view class="fillInCode-btn">
				<span>提交</span>
			</view>
		</view>
		<view class="bindingStatus" v-if="isShowBingdingStatus">
			<image :src="isSuccess?'/static/promotion/29.png':'/static/promotion/30.png'" mode=""></image>
			<span style="font-size: 40rpx;color: #70B442; margin-top: 68rpx;"
				:style="{'color':isSuccess?'#70B442':'#FC4444'}">{{isSuccess?'审核成功':'审核失败'}}</span>
			<span style="font-size: 30rpx;color: #999999;margin-top: 24rpx;">{{isSuccess?'恭喜您！':'代理商拒绝您的团长申请'}}</span>
			<span v-if="isSuccess" style="font-size: 30rpx;color: #999999;">您已成功晋级团队长</span>
			<view class="bindingStatus-btn">
				<span>{{isSuccess?'管理团队':'返回'}}</span>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				isShowFillIn: false,
				isSuccess: false,
				isShowBingdingStatus: true,
				inviteCode: ''
			};
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		width: 100vw;

		.line {
			width: 100%;
			height: 2rpx;
			margin-top: 20rpx;
			background: #EFEFEF;
		}

		.fillInCode {
			display: flex;
			flex-direction: column;
			align-items: center;

			.fillInCode-bg {
				width: 620rpx;
				height: 100rpx;
				margin-top: 40rpx;
				background: #FFF8F0;
				border-radius: 32rpx;
				box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
				display: flex;
				align-items: center;

				input {
					font-size: 28rpx;
					color: #8B6B4A;
					margin-left: 40rpx;
				}
			}

			.fillInCode-btn {
				width: 374rpx;
				height: 100rpx;
				margin-top: 58rpx;
				font-size: 32rpx;
				color: #FFFFFF;
				text-align: center;
				line-height: 100rpx;
				background: linear-gradient(135deg, #E8783B, #F4A460);
				border-radius: 32rpx;
			}
		}

		.bindingStatus {
			display: flex;
			flex-direction: column;
			align-items: center;

			image {
				width: 160rpx;
				height: 160rpx;
				margin-top: 120rpx;
			}

			.bindingStatus-btn {
				width: 520rpx;
				height: 100rpx;
				font-size: 32rpx;
				margin-top: 94rpx;
				color: #E8783B;
				text-align: center;
				line-height: 100rpx;
				border-radius: 32rpx;
				border: 2rpx solid #E8783B;
			}
		}
	}
</style>
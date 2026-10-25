<template>
	<view class="content">
		<view class="title">
			<uni-icons @tap="back()" type="left" size="22"></uni-icons>
			<span style="font-size: 34rpx;color: #000000;">团队成员</span>
			<view class="title-right">
				<!-- <span style="font-size: 28rpx;color: #000000;margin-right: 16rpx;">全部</span> -->
				<!-- <uni-icons type="down" size="16"></uni-icons> -->
				<uni-data-select v-model="memberName" :localdata="selectList" @change="change"></uni-data-select>
			</view>
		</view>
		<view class="line"></view>
		<view class="body">
			<scroll-view scroll-y="true" style="height: 100%;" @scrolltolower='getMore()'>
				<view class="body-item" v-for="item in memberList">
					<image class="body-item-avatar" :src="item.avatar" mode=""></image>
					<view class="body-item-body">
						<view class="body-item-body-top">
							<span>{{item.nickName}}</span>
							<image style="height: 40rpx;margin-left: 10rpx;"
								:src="item.roleId==2?'/static/promotion/12.png':'/static/promotion/41.png'"
								mode="heightFix"></image>
						</view>
						<span style="font-size: 24rpx;color: #999999;">所属团队：{{memberName}}</span>
						<span style="font-size: 24rpx;color: #999999;">加入时间：{{timestampToDate(item.createTime)}}</span>
						<view class="line2">

						</view>
					</view>
				</view>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userId: '',
				isHasMore: false,
				memberName: '全部',
				memberList: [],
				selectList: []
			};
		},
		onShow() {
			this.getTeamMembers()
		},
		methods: {
			//加载更多
			getMore() {

			},
			//返回
			back(){
				uni.navigateBack()
			},
			//时间戳转换
			timestampToDate(timestamp) {
				const date = new Date(timestamp);
				const year = date.getFullYear();
				const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份是从0开始的
				const day = String(date.getDate()).padStart(2, '0');
				const hours = String(date.getHours()).padStart(2, '0');
				const minutes = String(date.getMinutes()).padStart(2, '0');
				const seconds = String(date.getSeconds()).padStart(2, '0');
				return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
			},
			change(e) {
				this.userId = e
				if (this.userId) {
					this.$api.getTeamMembers({
						userId: this.userId
					}).then(res => {
						this.memberList = res.data.bUser
					})
				} else {
					this.$api.getTeamMembers().then(res => {
						this.memberList = res.data.bUser
						this.memberName = '全部'
					})
				}
			},
			getTeamMembers() {
				this.$api.getTeamMembers().then(res => {
					this.selectList = []
					this.memberList = res.data.bUser
					this.memberName = res.data.aUser[0].nickName
					for (let item of res.data.aUser) {
						this.selectList.push({
							value: item.id,
							text: item.nickName
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		width: 100vw;
		height: 100vh;
		overflow: hidden;

		.title {
			display: flex;
			width: 95%;
			margin: 60rpx auto;
			justify-content: space-between;
			align-items: center;

			.title-right {
				display: flex;
				width: 200rpx;
				align-items: center;
				margin-right: 0rpx;
			}
		}

		.line {
			width: 750rpx;
			height: 2rpx;
			background: #EFEFEF;
		}

		.body {
			position: absolute;
			left: 0;
			right: 0;
			bottom: 0;
			top: 180rpx;

			.body-item {
				margin: 48rpx 0 0 34rpx;
				display: flex;
				align-items: center;

				.body-item-avatar {
					width: 96rpx;
					height: 96rpx;
					border-radius: 50%;
				}

				.body-item-body {
					display: flex;
					margin-left: 20rpx;
					flex-direction: column;

					.body-item-body-top {
						display: flex;
						align-items: center;

						span {
							font-size: 34rpx;
							color: #5D3A1A;
							font-weight: 700;
						}
					}

					.line2 {
						margin-top: 20rpx;
						width: 550rpx;
						height: 2rpx;
						background: #EFEFEF;
					}
				}
			}
		}
	}
</style>
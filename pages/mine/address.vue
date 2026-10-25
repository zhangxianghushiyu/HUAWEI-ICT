<template>
	<view class="view">
		<view class="box" v-for="(item,index) in list" @click="itemFn(item)">
			<view class="title">{{item.name}} {{item.phone}}</view>
			<view class="address">{{item.city}} {{item.address}}</view>
			<view class="btn" v-if="!select">
				<view @click.stop="$utils.toPage(`/pages/mine/addAddress?info=${JSON.stringify(item)}`)">编辑</view>
				<view @click.stop="delFn(item)">删除</view>
			</view>
		</view>
		<uv-load-more status="nomore" />
		<view class="bottom-box" >
			<button hover-class="button-hover" type="theme" @click="$utils.toPage(`/pages/mine/addAddress`)"
				size="long">新增地址</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: [],
				select: false
			}
		},
		onLoad(e) {
			if (e.select == 'yes') {
				this.select = true
			}
		},
		onShow() {
			this.getList()
		},
		methods: {
			delFn(item) {
				this.$utils.showModalAsync("是否删除此条数据", "提示").then(res => {
					this.$api.delAddress({
						id: item.id
					}).then(res => {
						this.$utils.showTip('删除成功', '成功')
						this.getList()
					})
				})
			},
			itemFn(item){
				if(!this.select) return

				uni.setStorageSync('selectAddress',item)
				uni.navigateBack()
			},
			getList() {
				this.$api.getAddress().then(res => {
					this.list = res.data
				})
			}
		}
	}
</script>

<style>
	page {
		background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
	}
</style>

<style lang="scss" scoped>
	.view {
		padding: 20rpx;
		padding-bottom: 110rpx;

		.box {
			background-color: #FFFDF9;
			padding: 20rpx 40rpx;
			border-radius: 30rpx;
			box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
			margin-bottom: 20rpx;

			.title {
				font-size: 30rpx;
				font-weight: 700;
				color: #5D3A1A;
				margin-bottom: 20rpx;
			}

			.address {
				color: #8B6B4A;
			}

			.btn {
				margin-top: 20rpx;
				padding-top: 20rpx;
				border-top: #FFEBD6 1px solid;
				display: flex;
				justify-content: flex-end;

				view {
					margin-left: 20rpx;
					color: #E8783B;
				}
			}
		}
	}
</style>
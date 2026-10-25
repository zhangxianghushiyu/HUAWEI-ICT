<template>
	<view style="padding: 30rpx;">
		<view style="padding-bottom: 170rpx;" v-html="data" />

		<view v-if="type == 7" class="bottom-box">
			<view style="width: 100%;">
				<view class="wrapper-text">
					<image @click="changeSelect" v-if="!isSelect" style="width: 30rpx;height: 30rpx;"
						src="@/static/images/cli-gray.png" mode="scaleToFill">
					</image>
					<image @click="changeSelect" v-else style="width: 30rpx;height: 30rpx;"
						src="@/static/images/change/cli-select.png" mode="scaleToFill">
					</image>
					<view class="wrapper-text-box">
						我已阅读并同意以上注销须知
					</view>
				</view>
				<!-- 提交按钮 -->
				<button hover-class="button-hover" v-if="countDown == 0" type="theme" @click="cancelAccount" size="long">申请注销</button>
				<button hover-class="button-hover" v-else disabled type="theme" size="long">申请注销{{countDown}}s</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {

		data() {
			return {
				data: '',
				type: '',
				isSelect: false,
				countDown:60
			}
		},
		onLoad(e) {
			let title = ''
			this.type = e.type
			switch (e.type) {
				case '1':
					title = '用户手册'
					break;
				case '2':
					title = '隐私政策'
					break;
				case '3':
					title = '收集个人信息明示清单'
					break;
				case '4':
					title = '个人信息第三方共享清单'
					break;
				case '5':
					title = '儿童个人信息保护规则'
					break;
				case '6':
					title = '青少年文明公约'
					break;
				case '7':
					title = '注销协议'
					break;
				case '8':
					title = '福利规则'
					break;
				case '9':
					title = '会员服务协议'
					break;
				default:
					title = '政策'
					break;
			}

			uni.setNavigationBarTitle({
				title: title
			})

			this.getData(e.type)

			if (e.type) this.startDown()
		},
		methods: {
			getData(type) {
				this.$api.getAgreement({
					type
				}).then(res => {
					this.data = res.data.content
				})
			},
			changeSelect() {
				this.isSelect = !this.isSelect
			},
			startDown() {
				console.log('开始倒计时');
				let timer = setInterval(() => {
					this.countDown--
					if (this.countDown == 0) {
						clearInterval(timer)
					}
				}, 1000)
			},
			//注销账号
			cancelAccount() {
				if (!this.isSelect) {
					this.$refs.modalCancel.open();
					this.$utils.showModalAsync('我已阅读并同意以上注销须知',"提示").then(res=>{
						this.isSelect = true
						this.cancelAccount();
					})
					return
				}

				this.$api.applyDestroy().then(res => {
					this.$utils.showTipAsync('操作成功','提示').then(res=>{
						uni.navigateBack()
					})
				})
			},

		}
	}
</script>

<style lang="scss" scoped>
	.wrapper {
		&-text {
			display: flex;
			align-items: center;
			justify-content: center;

			&-box {
				font-weight: 400;
				font-size: 24rpx;
				color: #8B6B4A;
				line-height: 48rpx;
				margin-left: 12rpx;
			}
		}
	}

	.sub-btn-hover {
		opacity: 0.9;
	}
</style>
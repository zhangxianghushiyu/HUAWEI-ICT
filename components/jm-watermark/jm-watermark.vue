<template>
	<view>
		<view class="copyRightBox">
			<view class="font" ref='sy' v-for="(item,index) in 30" :key='index'>
				{{sytext}}
			</view>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {};
		},
		props: {
			//载入的标签数据
			sytext: String
		},
		mounted() {}
	}
</script>
<style>
	.copyRightBox {
		overflow: hidden;
		width: 100vw;
		height: 100vh;
		pointer-events: none;
		position: fixed;
		top: 20px;
		z-index: 99999;
	}
	.font {
		float: left;
		transform: rotate(-30deg);
		margin-top: 220upx;
		margin-left: 100upx;
		font-size: 34upx;
		color: rgba(100, 0, 0, 0.2);
	}
</style>

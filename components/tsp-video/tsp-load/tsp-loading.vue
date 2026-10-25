<template>
	<view>
		<!-- #ifndef APP-NVUE -->
		<!-- <view class="loadingView">
			<view class="k-ball7a" ></view>
			<view class="k-ball7b" ></view>
			<view class="k-ball7c" ></view>
			<view class="k-ball7d" ></view>
		</view> -->
		<view class="loadingView">
			<view class="load-box">
				<view class="load-item load-item1" style="background-color: #F63D3A;"></view>
				<view class="load-item load-item2" style="background-color: #4CD964;"></view>
				<view class="load-item load-item3" style="background-color: #5C6BC0;"></view>
			</view>
		</view>
		<!-- #endif -->
		<!-- #ifdef APP-NVUE -->
		<view class="loadingView">
			<view class="loader" ref="loader"></view>
		</view>
		<!-- #endif -->
	</view>
</template>

<script>
	// #ifdef APP-NVUE
	const animation = uni.requireNativePlugin('animation')
	// #endif
	export default{
		data(){
			return {
				
			}
		},
		created() {
			// #ifdef APP-NVUE
			this.rotateAvatar1();
			// #endif
		},
		methods:{
			/* nvue  loading动画 */
			rotateAvatar1(){
				setTimeout(()=>{
					let testEl = this.$refs['loader']
					animation.transition(testEl, {
					  styles: {
						  transform:'rotate(0)',
					  },
					  duration: 10, //ms
					  timingFunction: 'linear',
					  delay: 0 //ms
					},()=>{
						this.rotateAvatar2();
					})
				},50)
			},
			rotateAvatar2(){
				let testEl = this.$refs['loader']
				animation.transition(testEl, {
				  styles: {
					  transform:'rotate(360)',
				  },
				  duration: 30000, //ms
				  timingFunction: 'linear',
				  delay: 0 //ms
				},()=>{
					this.rotateAvatar1();
			  })
			},
		}
	}
</script>

<style>
	/* #ifndef APP-NVUE */
	/* loading动画 */
	.loadingView{
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 100;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.k-ball7a {
		border:0;
		margin:0;
		width:25rpx;
		height:25rpx;
		position:absolute;
		border-radius:50%;
		animation:k-loadingO 2s ease infinite;
		background:#4CD964;
		animation-delay:-1.5s
	}
	.k-ball7b {
		border:0;
		margin:0;
		width:25rpx;
		height:25rpx;
		position:absolute;
		border-radius:50%;
		animation:k-loadingO 2s ease infinite;
		background:#F63D3A;
		animation-delay:-1s
	}
	.k-ball7c {
		border:0;
		margin:0;
		width:25rpx;
		height:25rpx;
		position:absolute;
		border-radius:50%;
		animation:k-loadingO 2s ease infinite;
		background:#FDA543;
		animation-delay:-0.5s
	}
	.k-ball7d {
		border:0;
		margin:0;
		width:25rpx;
		height:25rpx;
		position:absolute;
		border-radius:50%;
		animation:k-loadingO 2s ease infinite;
		background:#5C6BC0
	}
	.k-paused{
		animation-play-state: paused;
	}
	.k-running{
		animation-play-state: running;
	}
	@keyframes k-loadingO {
		0%,100% {
			transform:translate(0)
		}
		25% {
			transform:translate(160%)
		}
		50% {
			transform:translate(160%,160%)
		}
		75% {
			transform:translate(0,160%)
		}
	}
	.load-box{
		display: flex;
		align-items: center;
		flex-direction: row;
	}
	.load-item{
		width: 35rpx;
		height: 35rpx;
		border-radius: 35rpx;
		background-color: #000000;
		margin: 0 5rpx;
		/* #ifndef APP-NVUE */
		-webkit-animation: bouncedelay 1.4s infinite ease-in-out;
		animation: bouncedelay 1.4s infinite ease-in-out;
		-webkit-animation-fill-mode: both;
		animation-fill-mode: both;
		/* #endif */
	}
	/* loading动画2 */
	.load-item1 {
	  -webkit-animation-delay: -0.32s;
	  animation-delay: -0.32s;
	}
	.load-item2 {
	  -webkit-animation-delay: -0.16s;
	  animation-delay: -0.16s;
	}
	@-webkit-keyframes bouncedelay {
	  0%, 80%, 100% { -webkit-transform: scale(0.0) }
	  40% { -webkit-transform: scale(1.0) }
	}
	@keyframes bouncedelay {
	  0%, 80%, 100% {
	    transform: scale(0.0);
	    -webkit-transform: scale(0.0);
	  } 40% {
	    transform: scale(1.0);
	    -webkit-transform: scale(1.0);
	  }
	}
	/* #endif */
	.loader{
		border: 16rpx solid #f3f3f3;
		border-radius: 50%;
		border-top: 16rpx solid #FDA543;
		width: 100rpx;
		height: 100rpx;
		/* #ifndef APP-NVUE */
		-webkit-animation: spin 2s linear infinite;
		animation: spin 2s linear infinite;
		/* #endif */
	}
	/* #ifndef APP-NVUE */
	@-webkit-keyframes spin {
	  0% { -webkit-transform: rotate(0deg); }
	  100% { -webkit-transform: rotate(360deg); }
	}
	
	@keyframes spin {
	  0% { transform: rotate(0deg); }
	  100% { transform: rotate(360deg); }
	}
	/* #endif */
</style>

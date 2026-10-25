#### 使用说明
---

下载后直接使用，支持自定义内容。

###### 使用方式

```
下载后可直接使用，插件符合uni_modules规则
```

```
vue2示例
<countDown ref="down" :widths='300' :breadth='30' activeColor='#01B862' defaultColor='#EDF0F0' isScale	:times.sync='times' @endTime='endTime'>
  <view>自定义内容</view>
</countDown>

vue3示例
<countDown ref="down" :widths='300' :breadth='30' activeColor='#01B862' defaultColor='#EDF0F0' isScale v-model:times='times' :progress_time='progress_time' @endTime='endTime'>
	<view >自定义内容</view>
</countDown>

把动画放到App.vue就支持app和h5
@keyframes progross1 {
	to {
		transform: rotate(360deg);
	}
}
@keyframes progross2 {
	to {
		transform: rotate(0deg);
	}
}
```

#### 参数说明
---

| 参数					| 类型			| 默认值		| 描述																					|
| ---						| ---			| ---			| ---																				|
| times					| Number	| 10000		| 时间 (单位ms)															|
| progress_time	| Number	| 0				| 指定时间开始 (单位ms)；注：不能大于times     |
| widths				| Number	| 200			| 圆环的总体大小 (单位rpx)										|
| breadth				| Number	| 30			| 圆环中间区域的大小 (单位rpx)									|
| activeColor		| String	| #01B862	| 圆环中间区域倒计时的颜色											|
| defaultColor	| String	| #EDF0F0	| 圆环中间区域默认的背景颜色										|
| bgColor				| String	| #FFF		| 圆环自定义区域的背景颜色											|
| isScale				| Boolean	| false		| 是否显示圆环刻度														|
| scaleNun			| Number	| 40			| 圆环刻度个数																|
| scaleAngle		| Number	| 6				| 圆环刻度角度值															|
| is_reversal		| Boolean	| false		| 是否反转进度条															|


#### Event 事件
---

|  事件名		| 说明						| 回调参数			|
| ---				| ---						| ---					|
| @pauseNum	| 倒计时暂停			|			剩余时间	|
| @endTime	| 倒计时结束事件	|							|

##### vue3示例

```
<template>
	<view class="">
		<view class="nav">
			<countDown ref="down" :widths='300' :breadth='40' activeColor='#01B862' defaultColor='#EDF0F0'
				v-model:times='times' :progress_time='progress_time'  @endTime='endTime' @pauseNum='pauseNum'>
				<view class="">自定义内容{{(times/1000).toFixed(0)}}</view>
			</countDown>
		</view>

		<view class="btn">
			<view class="btna" @click="startCounting">
				<view class="btn_name">开始</view>
			</view>

			<view class="btna" @click="pause">
				<view class="btn_name">暂停</view>
			</view>

			<view class="btna" @click="goOn">
				<view class="btn_name">继续</view>
			</view>

			<view class="btna" @click="getReset">
				<view class="btn_name">重置</view>
			</view>
		</view>
	</view>
</template>

<script lang="ts" setup>
	import { ref } from 'vue'

	const times = ref(10000);
	const progress_time = ref(3000);
	const down = ref();

	//开始事件
	const startCounting = () => {
		down.value.start()
	};

	//暂停
	const pause = () => {
		down.value.pause()
	};

	//暂停时返回剩余时间
	const pauseNum = (e ?: any) => {
		console.log('剩余时间：', e);
	};

	//继续
	const goOn = () => {
		down.value.goOn()
	};

	//结束
	const endTime = () => {
		console.log('结束');
	};

	//重置
	const getReset = () => {
		down.value.reset()
	};
</script>

<style lang="scss" scoped>
	.nav {
		margin: 146upx 0 200upx 0;
		display: flex;
		justify-content: center;
	}
	
	.content {
		font-size: 26upx;
		color: #333;
	}

	.btn {
		display: flex;
		align-items: center;
		justify-content: space-around;

		.btna {
			width: 160upx;
			height: 64upx;
			background: #06D368;
			border-radius: 50upx;
			display: flex;
			justify-content: center;
			align-items: center;

			.btn_name {
				font-size: 32upx;
				font-weight: 600;
				color: #FFFFFF;
			}
		}
	}
</style>

```

##### vue2示例

```
<template>
	<view class="">
		<view class="nav">
			<countDown ref="down" :widths='300' :breadth='40' activeColor='#01B862' defaultColor='#EDF0F0' :times.sync='times'
				@endTime='endTime' @pauseNum='pauseNum'>
				<view class="">自定义内容{{(times/1000).toFixed(0)}}</view>
			</countDown>
		</view>

		<view class="btn">
			<view class="btna" @click="startCounting">
				<view class="btn_name">开始</view>
			</view>

			<view class="btna" @click="pause">
				<view class="btn_name">暂停</view>
			</view>

			<view class="btna" @click="goOn">
				<view class="btn_name">继续</view>
			</view>

			<view class="btna" @click="getReset">
				<view class="btn_name">重置</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				times: 10000,
			}
		},
		methods: {
			startCounting() { //开始事件
				this.$refs.down.start()
			},
			pause() { //暂停
				this.$refs.down.pause()
			},
			pauseNum(e) { //暂停时返回剩余时间
				console.log('剩余时间：', e);
			},
			goOn() { //继续
				this.$refs.down.goOn()
			},
			endTime() { //结束
				console.log('结束');
			},
			getReset() { //重置
				this.$refs.down.reset()
			},
		}
	}
</script>

<style lang="scss" scoped>
	.nav {
		margin: 146upx 0 200upx 0;
		display: flex;
		justify-content: center;
	}
	
	.content {
		font-size: 26upx;
		color: #333;
	}

	.btn {
		display: flex;
		align-items: center;
		justify-content: space-around;

		.btna {
			width: 160upx;
			height: 64upx;
			background: #06D368;
			border-radius: 50upx;
			display: flex;
			justify-content: center;
			align-items: center;

			.btn_name {
				font-size: 32upx;
				font-weight: 600;
				color: #FFFFFF;
			}
		}
	}
</style>

```




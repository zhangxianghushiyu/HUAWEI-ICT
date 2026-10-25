## 使用方式
安装到components文件夹，支持[easycom](https://uniapp.dcloud.io/collocation/pages?id=easycom)组件模式。

## 平台兼容性
兼容APP及微信小程序，其他平台未测试。由于本插件所使用API:uni.saveImageToPhotosAlbum不兼容H5，故H5端不支持长按下载

## 属性说明
|属性名			|类型	|说明				|
|--				|--		|--					|
|ifShow.sync	|Boolean|是否显示海报图		|
|myCanvasSize	|Array	|设置海报图的大小	|
|imageBg		|String	|背景图片			|
|imageHead		|Array	|内置图片一			|
|imageBody		|Array	|内置图片二			|
|textHead		|String	|内置文字			|

## 事件说明
插件内置**<font color=#FF0000 >长按保存</font>**到本地相册，无需自定义事件

## 示例代码
**<font color=#FF0000 >重要：图片地址需自行换成可访问图片地址</font>**
``` javascript
<template>
	<view>
		<button hover-class="button-hover" class="btn" type="default" @click="canvasShow">点击显示</button>
		<yz-canvas-poster ref="mycanvas" :myCanvasSize="myCanvasSize" :ifShow.sync="ifShow" :imageBg="imageBg"
			:imageHead="imageHead" :imageBody="imageBody" :textHead="textHead">
		</yz-canvas-poster>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				ifShow: false,
				myCanvasSize: [0.8, 16 / 9], //[0]为画布占屏幕宽度的比例，[1]为背景画布长宽比
				imageBg: '', 
				imageHead: [],
				imageBody: [],
				textHead: [],
				canvasHight: 0,
				canvasWidth: 0
			}
		},
		methods: {
			canvasShow() {
				//获取画布的大小尺寸
				this.canvasHight = this.$refs.mycanvas.canvasHeight;
				this.canvasWidth = this.$refs.mycanvas.canvasWidth;

                // 如下，以canvasHight、canvasWidth为单位进行计算，达到动态布局以适应不同尺寸的手机
				this.imageBg = '../../static/bg.jpg';
				this.imageHead = ['../../static/logo.png', this.canvasWidth * 0.16 - 20, this.canvasHight * 0.08 - 20, 40,40];
				this.imageBody = ['../../static/myQR.jpg', this.canvasWidth * 0.5 - 60, this.canvasHight * 0.48, 120, 120];
				this.textHead = ['myCanvas', this.canvasWidth * 0.16 + 28, this.canvasHight * 0.08, '#ffffff', '20'];

                // 显示画布
				this.ifShow = true;

                //绘制画布
				let tempTimeOut = setTimeout(() => {
					this.$refs.mycanvas.init();
					clearTimeout(tempTimeOut)
				}, 50);

			}
		}
	}
</script>

<style>
	.btn {
		margin: 24rpx;
	}
</style>

	
	
```

#### 如下示例代码数组项分别为:

``` javascript
	myCanvasSize: [0.8, 16 / 9]
```
0. 画布占屏幕宽度的比例
1. 背景画布的长宽比


``` javascript
	imageHead: ['../../static/logo.png', 50, 20, 40, 40]
```
0. 图片地址
1. 图像的左上角在目标canvas上 X 轴的位置
2. 图像的左上角在目标canvas上 Y 轴的位置
3. 在目标画布上绘制图像的宽度
4. 在目标画布上绘制图像的高度

#### 如下示例代码数组项分别为:
``` javascript
	textHead: ['myCanvas', 100, 40, '#ffffff', '20']
```
0. 文字内容
1. 绘制文本的左上角x坐标位置
2. 绘制文本的左上角y坐标位置
3. 文字颜色
4. 文字字号(以文字middle为基准线)

# <font color=#FF0000 >canvas踩坑</font>
1. uni-app中的canvas的width、height的值依赖于父元素的宽高。canvas的父级如果被隐藏。canvas的width、height则都为0。
2. 当父元素出现，canvas的高度也就有了。但是canvas并不会绘制显示。解决方法：可以将绘制方法写在异步方法中，同时要注意组件的生命周期，确保实例挂载成功之后再执行。
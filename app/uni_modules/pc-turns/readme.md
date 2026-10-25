# 插件使用说明
## 特别说明
* 卡片形式可滑动组件

## 参数说明
参数 | 类型 | 必填 | 说明 | 默认值
:--- | :--- | :--- | :--- | :---
data | Array | 是 | 数据来源，**对象数组** | -
width | String / Number | 否 | 卡片宽度，未传值固定为全屏宽度，单位固定为“rpx”。**勿传单位** | -
height | String / Number | 否 | 卡片高度，单位固定为“rpx”。**勿传单位** | 400
keys | String | 否 | 数据来源里卡片显示图片地址的键名，**如未存在传入键值或为src的键值则不显示该条数据** | src
delay | String / Number | 否 | 默认值4，以整数形式自动取整，自动切换的时间间隔，单位秒(s)。**传0视为取消自动切换** | 4

## 方法说明
方法名称 | 说明 | 返回值类型
:--- | :--- | :---
achieve | 用户点击最上方卡片时触发，返回参数为该条对象数据。 | object

## 使用示例
```
<template>
	<view>
		<pc-turns :data="data" @achieve="achieve" />
	</view>
</template>

<script>
	export default {
		data(){
			return{
				data:[{src:'图片路径地址'}],
			}
		},
		methods:{
			achieve(open){
				// open = {src:'图片路径地址'}
				console.log(open)
			}
		}
	}
</script>
```
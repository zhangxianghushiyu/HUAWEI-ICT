# pc-carousel

## 参数说明
| 参数 | 类型 | 必填 | 说明 | 默认值 |
:--- | :--- | :--- | :--- | :--- |
| data | Array | 是 | 数据来源 | - |
| field | String | 是 | 数据来源中需要显示的图片字段 | - |
| icon | String | 否 | 图片加载失败时现实的图标，[uni-icons v3.1.1](https://uniapp.dcloud.net.cn/component/uniui/uni-icons.html#%E4%BB%8B%E7%BB%8D) | image |
| size | String | 否 | 图标大小，于uni-icon的size属性同理 | 40 |
| mode | String | 否 | <br>**multiple：**5张图片排列；<br>**single：**3张图片排列。 | multiple |

## 方法说明
方法名称 | 说明 | 返回值类型
:--- | :--- | :---
selection | 点击顶部图片返回的数据源数据 | Object

## 使用示例
```
<template>
	<view>
		<pc-carousel :data="datalist" field="img" @selection="selection"></pc-carousel>
	</view>
</template>

<script>
	export default {
		data(){
			return{
				datalist:[
					{name:'图片1',img:'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg'},
					{name:'图片2',img:'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg'},
					{name:'图片3',img:'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg'},
					{name:'图片4',img:'https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg'},
					{name:'图片5',img:'https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg'},
					{name:'图片6',img:'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg'},
					{name:'图片7',img:'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg'},
					{name:'图片8',img:'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg'},
					{name:'图片9',img:'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'}
				]
			}
		},
		methods:{
			selection(e){
				console.log(e)
			}
		}
	}
</script>
```
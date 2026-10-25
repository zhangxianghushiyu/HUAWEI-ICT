<template>
	<view class="pc-carousel" @touchstart="starts" @touchend="ends">
		<view v-for="(item,index) in imagelist" 
			:key="item.class" 
			:class="item.class" 
			class="carousel-block" 
			@click="changeBlock(item)"
			:style="{ left:item.left,top:item.top,zIndex:item.zIndex,width:item.width,height:item.height,transition:item.transition+'s all' }">
			<view class="image-block" :style="{backgroundImage:'url('+item.field+')'}"></view>
			<view class="error-block">
				<uni-icons :type="icon" :size="size" color="#909399"></uni-icons>
			</view>
		</view>
	</view>
</template>
<script>
	export default{
		name:'pc-carousel',
		props:{
			data:{
				type:Array,
				default:()=>[]
			},
			field:{
				type:String,
				default:''
			},
			icon:{
				type:String,
				default:'image'
			},
			size:{
				type:[Number,String],
				default:'40'
			},
			mode:{
				type:String,
				default:'multiple'
			}
		},
		data(){
			return{
				datalist:[],
				imagelist:[],
				ismove: 0,
				count: {sta:0,end:0},
				style_multiple:[
					{left: '10rpx',top: '90rpx',height: 'calc(100% - 180rpx)',width: 'calc(50% - 160rpx)',zIndex: 10,transition:0.2},
					{left: 'calc(12.5% - 2.5rpx)',top: '50rpx',height: 'calc(100% - 100rpx)',width: 'calc(50% - 80rpx)',zIndex: 15,transition:0.4},
					{left: 'calc(25% - 5rpx)',top: '10rpx',height: 'calc(100% - 20rpx)',width: 'calc(50% - 10rpx)',zIndex: 20,transition:0.5},
					{left: 'calc(37.5% + 77.5rpx)',top: '50rpx',height: 'calc(100% - 100rpx)',width: 'calc(50% - 80rpx)',zIndex: 15,transition:0.4},
					{left: 'calc(50% + 150rpx)',top: '90rpx',height: 'calc(100% - 180rpx)',width: 'calc(50% - 160rpx)',zIndex: 10,transition:0.2}
				],
				style_single:[
					{left: '10rpx',top: '50rpx',height: 'calc(100% - 100rpx)',width: 'calc(50% - 80rpx)',zIndex: 15,transition:0.1},
					{left: 'calc(25% - 5rpx)',top: '10rpx',height: 'calc(100% - 20rpx)',width: 'calc(50% - 10rpx)',zIndex: 20,transition:0.3},
					{left: 'calc(50% + 70rpx)',top: '50rpx',height: 'calc(100% - 100rpx)',width: 'calc(50% - 80rpx)',zIndex: 15,transition:0.1}
				]
			}
		},
		created() {
			this.getDatalist()
		},
		methods:{
			getDatalist(){
				if(this.data){
					this.datalist = JSON.parse(JSON.stringify(this.data))
					let num = this.mode=='multiple'?5:this.mode=='single'?3:0
					let diff = num - this.data.length
					if(diff > 0){
						for(let i=0;i<diff;i++){
							let obj = {}
							obj[this.field] = ''
							this.datalist.push(obj)
						}
					}
					this.getImagelist()
				}
			},
			getImagelist(){
				if(this.mode=='multiple'){
					this.imagelist = JSON.parse(JSON.stringify(this.style_multiple))
					this.count.sta = 0
					this.count.end = 4
				}else if(this.mode=='single'){
					this.imagelist = JSON.parse(JSON.stringify(this.style_single))
					this.count.sta = 0
					this.count.end = 2
				}
				for(let i=0;i<this.datalist.length;i++){
					if(this.imagelist[i]){
						this.imagelist[i].field = this.datalist[i][this.field]
						this.imagelist[i].isIndex = i
						this.imagelist[i].isToor = i
					}else{
						break;
					}
				}
			},
			LeftSliding(){
				let list = this.mode=='multiple'?this.style_multiple:this.mode=='single'?this.style_single:[]
				this.imagelist.forEach(item=>{
					let toor = (item.isToor-1)<0?this.imagelist.length-1:item.isToor-1
					item.isToor = toor
					item.transition = list[toor].transition
					item.left = list[toor].left
					item.top = list[toor].top
					item.width = list[toor].width
					item.height = list[toor].height
					item.zIndex = list[toor].zIndex
					if((toor==4 && this.mode=='multiple') || (toor==2 && this.mode=='single')){
						this.count.end = (this.count.end+1)>this.datalist.length-1?0:this.count.end+1
						item.isIndex = this.count.end
						item.field = this.datalist[this.count.end][this.field]
					}
					if(toor==0){
						this.count.sta = item.isIndex
					}
				})
			},
			RightSliding(){
				let list = this.mode=='multiple'?this.style_multiple:this.mode=='single'?this.style_single:[]
				this.imagelist.forEach(item=>{
					let toor = (item.isToor+1)>this.imagelist.length-1?0:item.isToor+1
					item.isToor = toor
					item.transition = list[toor].transition
					item.left = list[toor].left
					item.top = list[toor].top
					item.width = list[toor].width
					item.height = list[toor].height
					item.zIndex = list[toor].zIndex
					if(toor==0){
						this.count.sta = (this.count.sta-1)<0?this.datalist.length-1:this.count.sta-1
						item.isIndex = this.count.sta
						item.field = this.datalist[this.count.sta][this.field]
					}
					if((toor==4 && this.mode=='multiple') || (toor==2 && this.mode=='single')){
						this.count.end = item.isIndex
					}
				})
			},
			changeBlock(item){
				let toor = item.isToor
				if(this.mode=='multiple'){
					if(toor===0 || toor==="0"){
						this.RightSliding()
						setTimeout(()=>{
							this.RightSliding()
						},100)
					}else if(toor==1){
						this.RightSliding()
					}else if(toor==2){
						this.$emit('selection',this.datalist[item.isIndex])
					}else if(toor==3){
						this.LeftSliding()
					}else if(toor==4){
						this.LeftSliding()
						setTimeout(()=>{
							this.LeftSliding()
						},100)
					}
				}else if(this.mode=='single'){
					if(toor===0 || toor==="0"){
						this.RightSliding()
					}else if(toor==1){
						this.$emit('selection',this.datalist[item.isIndex])
					}else if(toor==2){
						this.LeftSliding()
					}
				}
			},
			starts(e){
				this.ismove = e.touches[0].pageX
			},
			ends(e){
				let pageX = e.changedTouches[0].pageX
				let nums = this.ismove - pageX
				if(nums > 50){
					this.LeftSliding()
				}else if(nums < -50){
					this.RightSliding()
				}
			}
		}
	}
</script>
<style lang="scss" scoped>
	.pc-carousel{
		width: 100%;
		height: 100%;
		min-height: 200px;
		position: relative;
		overflow: hidden;
		.carousel-block{
			position: absolute;
			overflow: hidden;
			border-radius: 10upx;
			overflow: hidden;
			box-shadow: 0 0 5px #0000001a;
			.image-block{
				position: absolute;
				left: 0;
				top: 0;
				width: 100%;
				height: 100%;
				background-size: 100% 100%;
				z-index: 5;
			}
			.error-block{
				position: absolute;
				left: 0;
				top: 0;
				width: 100%;
				height: 100%;
				background-color: #f5f7fa;
				display: flex;
				align-items: center;
				justify-content: center;
				z-index: 1;
			}
		}
	}
</style>

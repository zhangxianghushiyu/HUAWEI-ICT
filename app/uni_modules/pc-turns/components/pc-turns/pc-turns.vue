<template>
	<view class="pc_turns" :style="{width:width?width+'rpx':'100vw',height:height+'rpx'}" @touchstart="handleStart" @touchmove="handleMove" @touchend="handleEnd">
		<view class="subgraph" v-for="(item,index) in showlist" :key="index"
		:style="'background-image:url('+item.src+');left:'+item.left+'%;z-index:'+item.z_index+';transform: scale('+item.scale+');height:'+height+'rpx;'"
		@click="outputreturn(item)"></view>
	</view>
</template>

<script>
	export default{
		props:{
			data:{
				type:Array,
				default:()=>[]
			},
			keys:{
				type:String,
				default:'src'
			},
			delay:{
				type:[String,Number],
				default:4
			},
			width:{
				type:[String,Number],
				default:''
			},
			height:{
				type:[String,Number],
				default:400
			}
		},
		data(){
			return{
				db_multis:false,
				isIndex:0,
				copyindex:0,
				delaytimes:4,
				interval:null,
				isTouchMove:false,
				pageX:0,
				imglist:[],
				showlist:[{src:'',left:'27.5',z_index:'100',keys:0,scale:1},{src:'',left:'52',z_index:'90',keys:0,scale:1},{src:'',left:'3',z_index:'90',keys:0,scale:1}],
			}
		},
		created() {
			this.initialization()
			this.delaytimes = Math.floor(this.delay)
			if(this.data.length>1 && this.delaytimes>0){
				this.interval = setInterval(()=>{
					this.revolve()
				},Number(this.delaytimes)*1000)
			}
		},
		watch:{
			isTouchMove(){
				if(this.isTouchMove){
					document.body.style.cssText = 'overflow: hidden;'
				}else{
					document.body.style.cssText = 'overflow: auto;'
				}
			}
		},
		methods:{
			initialization(){
				this.isIndex = 0
				this.imglist = []
				this.data.forEach((item,index)=>{
					if(this.keys && item[this.keys]){
						let obj = {src:item[this.keys]}
						this.imglist.push(obj)
					}else if(this.keys && item.src){
						let obj = {src:item.src}
						this.imglist.push(obj)
					}
				})
				if(this.imglist[0]){
					this.showlist[0].src = this.imglist[0].src
					this.showlist[0].keys = 0
					this.isIndex=0
				}
				if(this.imglist[1]){
					this.showlist[1].src = this.imglist[1].src
					this.showlist[1].keys = 1
					this.showlist[1].scale = 0.9
					this.isIndex=1
				}
				if(this.imglist.length==3){
					this.showlist[2].src = this.imglist[2].src
					this.showlist[2].keys = 2
					this.showlist[2].scale = 0.9
					this.isIndex=2
				}else if(this.imglist.length>3){
					this.showlist[2].src = this.imglist[this.imglist.length-1].src
					this.showlist[2].keys = this.imglist.length-1
					this.copyindex = this.imglist.length-1
					this.showlist[2].scale = 0.9
					this.db_multis = true
				}
			},
			revolve(){
				if(this.imglist.length < 2)return;
				this.showlist.forEach(item=>{
					if(item.left==3){
						if(this.db_multis){
							if(this.isIndex == this.imglist.length-1){
								this.isIndex=0
							}else{
								this.isIndex++
							}
							item.keys = this.isIndex
							item.src = this.imglist[this.isIndex].src
						}
						if(this.imglist.length > 2){
							item.left = 52
						}
					}else if(item.left==52){
						item.z_index = 100
						item.left = 27.5
						item.scale = 1
					}else if(item.left==27.5){
						this.copyindex = item.keys
						item.z_index = 90
						if(this.imglist.length==2){
							item.left = 52
						}else{
							item.left = 3
						}
						item.scale = 0.9
					}
				})
			},
			giveback(){
				if(this.imglist.length < 2)return;
				if(this.imglist.length==2){
					this.revolve()
					return
				}
				this.showlist.forEach(item=>{
					if(item.left==3){
						item.z_index = 100
						item.left = 27.5
						item.scale = 1
					}else if(item.left==52){
						if(this.db_multis){
							if(this.copyindex==0){
								this.copyindex = this.imglist.length-1
							}else{
								this.copyindex--
							}
							item.keys = this.copyindex
							item.src = this.imglist[this.copyindex].src
						}
						item.z_index = 90
						item.left = 3
					}else if(item.left==27.5){
						this.isIndex = item.keys
						item.z_index = 90
						item.left = 52
						item.scale = 0.9
					}
				})
			},
			outputreturn(item){
				clearInterval(this.interval);
				if(item.left==3){
					this.giveback()
				}else if(item.left==52){
					this.revolve()
				}else{
					let obj = JSON.parse(JSON.stringify(this.data[item.keys]))
					this.$emit('achieve',obj)
				}
				if(this.data.length>1 && this.delaytimes>0){
					this.interval = setInterval(()=>{
						this.revolve()
					},Number(this.delaytimes)*1000)
				}
			},
			handleStart(e){
				this.pageX = e.changedTouches[0].pageX;
			},
			handleMove(e){
				if(this.imglist.length < 1)return;
				clearInterval(this.interval);
				e.preventDefault();
				this.isTouchMove = true;
			},
			handleEnd(e){
				if(!this.isTouchMove || this.imglist.length < 1)return;
				this.isTouchMove = false;
				let touch = e.changedTouches[0].pageX;
				if(this.pageX > touch && this.pageX-touch > 50){
					this.revolve()
				}else if(this.pageX < touch && touch-this.pageX > 50){
					this.giveback()
				}
				if(this.data.length>1 && this.delaytimes>0){
					this.interval = setInterval(()=>{
						this.revolve()
					},Number(this.delaytimes)*1000)
				}
			},
		}
	}
</script>

<style lang="scss" scoped>
	.pc_turns{
		position: relative;
		z-index: 1;
		display: flex;
		align-items: center;
		.subgraph{
			width: 45%;
			border-radius: 20upx;
			transition: 0.8s all;
			background-size: 100% 100%;
			position: absolute;
			color: #FFFFFF;
			font-size: 100upx;
			font-weight: bold;
		}
	}
</style>

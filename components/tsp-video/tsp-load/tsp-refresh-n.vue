<template>
    <!-- #ifdef APP-NVUE -->
    <refresh class="refresh"  @refresh="onrefresh" @pullingdown="onpullingdown" :display="refreshing ? 'show' : 'hide'" :style="{backgroundColor:bgStyle}">
        <div class="refresh-view">
            <image class="refresh-icon" :src="refreshIcon" :style="{width: (refreshing || pulling) ? 0: '70rpx',color:fontColor}"
                :class="{'refresh-icon-active': refreshFlag}"></image>
            <uniloadmore v-if="refreshing" class="loading-icon" status="loading" :contentText="loadingMoreText" :style="{color:fontColor}"></uniloadmore>
            <text class="loading-text" :style="{color:fontColor}">{{refreshText}}</text>
        </div>
    </refresh>
    <!-- #endif -->
    <!-- #ifndef APP-NVUE -->
    <view ref="uni-refresh" class="uni-refresh" v-show="isShow">
        <slot />
    </view>
    <!-- #endif -->
</template>

<script>
	import uniloadmore from './tsp-load-more.vue'
    export default {
		components:{
			uniloadmore
		},
        name: 'UniRefresh',
        props: {
			refreshIcon:{ //箭头图标
				type:String,
				default:"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAMAAABg3Am1AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAB5QTFRFcHBw3Nzct7e39vb2ycnJioqK7e3tpqam29vb////D8oK7wAAAAp0Uk5T////////////ALLMLM8AAABxSURBVHja7JVBDoAgDASrjqj//7CJBi90iyYeOHTPMwmFZrHjYyyFYYUy1bwUZqtJIYVxhf1a6u0R7iUvWsCcrEtwJHp8MwMdvh2amHduiZD3rpWId9+BgPd7Cc2LIkPyqvlQvKxKBJ//Qwq/CacAAwDUv0a0YuKhzgAAAABJRU5ErkJggg=="
			},
			bgStyle: {  //下拉背景颜色
			    type: [String],
			    default: "#ffffff"
			},
			fontColor:{ //字体颜色
				type: [String],
				default: "#999999"
			}
        },
        data() {
            return {
				refreshing:false,//控制是否显示下拉刷新
                pulling: false, //控制是否设置箭头图标的宽
				refreshFlag:false, //控制是否给箭头图标添加旋转动画
				refreshText:'下拉可以刷新', //下拉提示文本
				loadingMoreText: {
					contentdown: '',
					contentrefresh: '',
					contentnomore: ''
				},
            }
        },
        computed: {
            isShow() {
                if (this.display === "show" || this.pulling === true) {
                    return true;
                }
                return false;
            }
        },
        created() {},
        methods: {
            onchange(value) {
                this.pulling = value;
            },
            onrefresh(e) { //下拉刷新释放刷新
				this.pulling = true;
				this.refreshing = true;
				this.refreshText = "正在刷新...";
                this.$emit("refresh", e);
            },
            onpullingdown(e) { //下拉刷新触发
            	this.pulling = false;
            	if (Math.abs(e.pullingDistance) > Math.abs(e.viewHeight)) {
            		this.refreshFlag = true;
            		this.refreshText = "释放立即刷新";
            	} else {
            		this.refreshFlag = false;
            		this.refreshText = "下拉可以刷新";
            	}
				this.$emit('onpullingdown',e)
            },
			//关闭下拉刷新
			closeRefresh(){ 
				this.refreshing = false; 
				this.refreshText = "已刷新"; 
			}
        }
    }
</script>

<style>
    .uni-refresh {
        height: 0;
        overflow: hidden;
    }
	.refresh {
		justify-content: center;
	}
	
	.refresh-view {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		width: 750rpx;
		height: 64px;
		flex-direction: row;
		flex-wrap: nowrap;
		align-items: center;
		justify-content: center;
	}
	
	.refresh-icon {
		width: 32px;
		height: 32px;
		transition-duration: .5s;
		transition-property: transform;
		transform: rotate(0deg);
		transform-origin: 15px 15px;
	}
	
	.refresh-icon-active {
		transform: rotate(180deg);
	}
	
	.loading-icon {
		width: 28px;
		height: 28px;
		margin-right: 5px;
		color: gray;
	}
	
	.loading-text {
		margin-left: 2px;
		font-size: 16px;
		color: #999999;
	}
	
	.loading-more {
		align-items: center;
		justify-content: center;
		padding-top: 14px;
		padding-bottom: 14px;
		text-align: center;
	}
	
	.loading-more-text {
		font-size: 28upx;
		color: #999;
	}
</style>

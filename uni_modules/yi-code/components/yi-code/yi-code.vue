<template>
	<view class="yi-code" :style="'width: '+width+'rpx;'">
		<view class="yi-code-show" :style="'width: '+width+'rpx;'">
			<block v-for="i in numberArr">
				<view
					:class="'yi-code-show-item' + ((codes.length === i && isFocus) ? ' yi-code-show-active' : '')" 
					:style="type !== 'block' ? (type === 'line' ? 'border-top: 0; border-left: 0; border-right: 0; border-radius: 0; border-width: 2px;' : 'border-style: dashed;') : ''"
				> 
				{{showVal(codes[i])}}
				</view>
			</block>
		</view>
		<view class="yi-code-hide" :style="'width: '+(width * 2)+'rpx;left: -'+ width +'rpx;'">
			<input v-model="value" :style="'width: '+(width * 2)+'rpx;'" :focus="focus" @focus="onFocus" @blur="onBlur" :type="inputType" @input="onChange" :maxlength="maxlength"/>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			/**
			 * @description 宽度 rpx
			 */
			width: {
				type: Number,
				default: 500,
			},
			/**
			 * @description 是否自动聚焦
			 */
			focus: {
				type: Boolean,
				default: true,
			},
			/**
			 * @description 隐藏字符（密码效果）
			 */
			hide: {
				type: String,
				default: '',
			},
			/**
			 * @description 验证码长度
			 */
			maxlength: {
				type: Number,
				default: 6
			},
			/**
			 * @description 样式类型 block 方框, dashed 虚线方框，line 线
			 */
			type: {
				type: String,
				default: 'block'
			},
			/**
			 * @description text | number 
			 */
			inputType: {
				type: String,
				default: 'number'
			},
		},
		created() {
			let arr = [];
			for(let i = 0; i < this.$props.maxlength; i++){
				arr.push(i);
			}
			this.numberArr = arr;
		},
		data(){
			return {
				numberArr: [], // 
				codes: [],
				value: '',
				isFocus: false
			}
		},
		methods: {
			clear(){
				this.codes = []
				this.value = ''
			},
			onFocus(){
				this.isFocus = true
			},
			onBlur(){
				this.isFocus = false
			},
			showVal(v){
				return v ? (this.hide || v) : '';
			},
			onChange(e){
				let str = e.detail.value || e.target.value
				if(str){
					this.codes = String(str).split('');
					this.$emit('onChange', str);
					if(this.codes.length === this.$props.maxlength){
						this.$emit('onComplete', str);
					}
				}else{
					this.codes = [];
					this.$emit('onChange', '');
				}
			}
		}
	}
</script>

<style lang="scss">
	.yi-code {
		position: relative;
		overflow: hidden;
		text-align: unset;

		.yi-code-show {
			display: flex;
			justify-content: space-between;
			align-items: center;

			.yi-code-show-item {
				box-sizing: border-box;
				width: 98rpx;
				height: 98rpx;
				// border: 1px solid #777;
				background: #F7F8FA;
				border-radius: 8rpx;
				display: flex;
				align-items: center;
				justify-content: center;
				
				font-weight: bold;
				font-size: 50rpx;
				color: #333333;
			}

			.yi-code-show-active {
				border-color: #ff5500;
				border-width: 2px;
				// animation: myfirst 600ms infinite;
				// @keyframes myfirst
				// {
				// 	0%   {opacity: 0.1}
				// 	100% {opacity: 1}
				// }
			}
		}
		
		.yi-code-hide{
			position: absolute;
			z-index: 99;
			left: 0;
			top: 0;
			height: 80rpx;
			opacity: 0;
			text-align: unset;
			
			input{
				height: 80rpx;
				text-align: unset;
			}
		}
	}
	
	
</style>
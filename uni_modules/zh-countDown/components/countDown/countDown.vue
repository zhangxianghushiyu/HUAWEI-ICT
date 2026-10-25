<template>
	<view class="">
		<view class="circle" :style="[circle]">
			<view class="left" :style="[size]">
				<view class="left-circle" :style="[
						{'transform': rotatea},
						{'animation':is_reversal?stycircle2:stycircle1},
						{'animation-delay':is_reversal?assignDelay:animationDelay },
						{'animation-play-state':pause_text},
						{'-webkit-animation-play-state': pause_text},
						size,
						leftCircle]">
				</view>
			</view>
			<view class="right" :style="[size]">
				<view class="right-circle" :style="[
						{'transform':is_reversal?rotatea:rotateb},
						{'animation':stycircle2},
						{'animation-delay': is_reversal?animationDelay:assignDelay},
						{'animation-play-state':pause_text},
						{'-webkit-animation-play-state': pause_text},
						size,
						rightcircle]">
				</view>
			</view>
			<view class="inner" :style="[inner]">
				<slot></slot>
			</view>
			<view class="circle_line" :style="[scale]" v-if="isScale"></view>
		</view>
	</view>
</template>

<!-- #ifdef VUE3 -->
<!-- vue3 -->
<script lang="ts" setup>
	/*
		 * countDown 圆环计时器，不使用canvas,仅css实现,圆环中间支持自定义添加内容
		 * @property {Number} times 时间 (单位毫秒)
		 * @property {Number} progress_time 指定从什么时间开始 (单位毫秒);注意：不能大于times
		 * @property {Number} widths 圆环的总体大小 (单位upx)
		 * @property {Number} breadth 圆环中间区域的大小 (单位upx)
		 * @property {String} activeColor 圆环中间区域的背景色
		 * @property {String} defaultColor 圆环中间区域默认的背景颜色
		 * @property {String} bgColor 圆环自定义区域的背景颜色
		 * @property {Boolean} isScale 是否显示圆环刻度
		 * @property {Number} scaleNun 圆环刻度个数
		 * @property {Number} scaleAngle 圆环刻度角度值
		 * @property {Boolean} is_reversal 是否反转
		 */
	import { computed, nextTick, ref, onBeforeMount, defineProps, watch } from 'vue'

	const props = defineProps({
		times: {
			type: Number,
			default: () => 10000
		},
		progress_time: {
			type: Number,
			default: () => 0
		},
		widths: {
			type: Number,
			default: () => 200
		},
		breadth: {
			type: Number,
			default: () => 30
		},
		activeColor: {
			type: String,
			default: () => '#01B862'
		},
		defaultColor: {
			type: String,
			default: () => '#EDF0F0'
		},
		bgColor: {
			type: String,
			default: () => '#fff'
		},
		isScale: {
			type: Boolean,
			default: () => false,
		},
		scaleNun: {
			type: Number,
			default: () => 40
		},
		scaleAngle: {
			type: Number,
			default: () => 6
		},
		is_reversal: {
			type: Boolean,
			default: () => false
		},
	});

	const stycircle1 = ref({})
	const stycircle2 = ref({})
	const animationDelay = ref('0ms') //延迟动画时间
	const assignDelay = ref('0ms') //延迟动画时间-跳过时间直接执行
	const timer = ref(null)
	const timeAll = ref(0) //时间
	const pause_text = ref('paused') //暂停
	const finish = ref(false) //结束标识 true结束 false未开始
	const is_continue = ref(true) //暂停标识 true可以继续 false暂停
	const rotatea = ref('rotate(180deg)')
	const rotateb = ref('rotate(-180deg)')
	const setTimeStart = ref(null)
	const timereset = ref(null)
	const timereseta = ref(null)

	const emit = defineEmits(['update:times', 'endTime', 'pauseNum']);

	timeAll.value = JSON.parse(JSON.stringify(props.times))

	onBeforeMount(() => { //销毁后
		clearInterval(timer.value)
	})

	//开始事件
	const start = () => {
		clearTimeout(setTimeStart.value)
		setTimeStart.value = setTimeout(() => {
			setCountDown()
		}, 100)
	};
	//设置
	const setCountDown = (e : any) => {
		nextTick(() => {
			console.log(1515);
			if (timeAll.value < 1000) {
				// uni.showToast({
				// 	title: '时间最少1s',
				// 	icon: 'none',
				// 	duration: 1500,
				// 	mask: true
				// });
			} else {
				if (finish.value) return
				clearInterval(timer.value)
				finish.value = true
				pause_text.value = 'running'
				setIn()
			}
		})
	}
	const setProgressTime = (e : any) => { //设置指定时间
		nextTick(() => {
			if (props.progress_time < 0) {
				// uni.showToast({
				// 	title: '指定时间不能为负数',
				// 	icon: 'none',
				// 	duration: 1500,
				// 	mask: true
				// });
			} else if (props.progress_time > timeAll.value) {
				// uni.showToast({
				// 	title: '指定时间不能大于总时间',
				// 	icon: 'none',
				// 	duration: 1500,
				// 	mask: true
				// });
			} else {
				resetData()
			}
		})
	}
	//计时器
	const setIn = (e ?: Boolean) => {
		clearInterval(timer.value)
		timer.value = setInterval(() => {
			let times_num = props.times - 100
			emit('update:times', times_num)
			if (e) {
				pause_text.value = 'running'
			}
			if (props.times <= 100) {
				finish.value = true
				is_continue.value = true
				setTimeout(() => {
					pause_text.value = 'paused'
					emit('endTime');
					emit('update:times', timeAll.value)
				}, 100)
				clearInterval(timer.value)
			}
		}, 100)
	}

	//暂停
	const pause = () => {
		if (!finish.value || pause_text.value == 'paused') return
		pause_text.value = 'paused'
		is_continue.value = false
		clearInterval(timer.value)
		emit('pauseNum', props.times)
	}

	//继续
	const goOn = () => {
		if (is_continue.value) return
		is_continue.value = true
		setIn(true)
	}

	//重置
	const reset = () => {
		clearTimeout(timereset.value)
		timereset.value = setTimeout(() => {
			stycircle2.value = 'none'
			stycircle1.value = 'none'
			assignDelay.value = ''
			animationDelay.value = ''
			pause_text.value = 'paused'
			clearInterval(timer.value)
			finish.value = false
			is_continue.value = true
			emit('update:times', timeAll.value)

			clearTimeout(timereseta.value)
			timereseta.value = setTimeout(() => {
				resetData()
			}, 50)
		}, 100)
	}

	const resetData = () => { //重置动画数据
		assignDelay.value = `-${props.progress_time}ms`
		let time = Number((props.times / 2).toFixed(2))
		stycircle2.value = `progross2 ${time}ms linear forwards paused`
		animationDelay.value = `${time - props.progress_time}ms`
		stycircle1.value = `progross1 ${time}ms linear forwards paused`
		pause_text.value = 'paused'
		let times_nums = props.times - props.progress_time
		emit('update:times', times_nums)
	}

	//计算属性
	const circle = computed(() => {
		const { widths, defaultColor } = props
		return {
			width: `${widths}rpx`,
			height: `${widths}rpx`,
			background: defaultColor
		}
	})

	const size = computed(() => {
		const { widths } = props
		return {
			width: `${widths / 2 + 2}rpx`,
			height: `${widths + 2}rpx`
		}
	})

	const leftCircle = computed(() => {
		const { widths, activeColor } = props
		return {
			borderTopLeftRadius: `${widths}rpx`,
			borderBottomLeftRadius: `${widths}rpx`,
			background: activeColor
		}
	})

	const rightcircle = computed(() => {
		const { widths, activeColor } = props
		return {
			borderTopRightRadius: `${widths}rpx`,
			borderBottomRightRadius: `${widths}rpx`,
			background: activeColor
		}
	})

	const inner = computed(() => {
		const { widths, breadth, bgColor } = props
		return {
			width: `${widths - breadth}rpx`,
			height: `${widths - breadth}rpx`,
			background: `${bgColor}`,
			border: `2rpx solid ${bgColor}`
		}
	})

	const scale = computed(() => {
		const { defaultColor, scaleNun, scaleAngle } = props
		return {
			background: `repeating-conic-gradient(${defaultColor} 0 ${scaleAngle}deg, rgba(0, 0, 0, 0) 0deg ${360 / scaleNun}deg)`
		}
	})


	// 监听
	watch(() => props.progress_time, (news, old) => {
		setProgressTime()
	}, {
		deep: true,
		immediate: true
	})

	defineExpose({
		start,
		pause,
		goOn,
		reset,
	})
</script>
<!-- #endif -->

<!-- #ifdef VUE2 -->
<!-- vue2 -->
<script>
	/*
	 * countDown 圆环计时器，不使用canvas,仅css实现,圆环中间支持自定义添加内容
	 * @property {Number} times 时间 (单位毫秒)
	 * @property {Number} progress_time 指定从什么时间开始 (单位毫秒);注意：不能大于times
	 * @property {Number} widths 圆环的总体大小 (单位upx)
	 * @property {Number} breadth 圆环中间区域的大小 (单位upx)
	 * @property {String} activeColor 圆环中间区域的背景色
	 * @property {String} defaultColor 圆环中间区域默认的背景颜色
	 * @property {String} bgColor 圆环自定义区域的背景颜色
	 * @property {Boolean} isScale 是否显示圆环刻度
	 * @property {Number} scaleNun 圆环刻度个数
	 * @property {Number} scaleAngle 圆环刻度角度值
	 * @property {Boolean} is_reversal 是否反转
	 */
	export default {
		props: {
			times: {
				type: Number,
				default: () => 10000
			},
			progress_time: {
				type: Number,
				default: () => 0
			},
			widths: {
				type: Number,
				default: () => 200
			},
			breadth: {
				type: Number,
				default: () => 30
			},
			activeColor: {
				type: String,
				default: () => '#01B862'
			},
			defaultColor: {
				type: String,
				default: () => '#EDF0F0'
			},
			bgColor: {
				type: String,
				default: () => '#fff'
			},
			isScale: {
				type: Boolean,
				default: () => false,
			},
			scaleNun: {
				type: Number,
				default: () => 40
			},
			scaleAngle: {
				type: Number,
				default: () => 6
			},
			is_reversal: {
				type: Boolean,
				default: () => false
			},
		},
		data() {
			return {
				stycircle1: {},
				stycircle2: {},
				animationDelay: 0, //延迟动画时间
				assignDelay: 0, //延迟动画时间-跳过时间直接执行
				timer: null,
				timeAll: 0, //时间
				pause_text: 'paused', //暂停
				finish: false, //结束标识 true结束 false未开始
				is_continue: true, //结束标识 true可以继续 false已继续
				rotatea: 'rotate(180deg)',
				rotateb: 'rotate(-180deg)'
			}
		},
		beforeDestroy() { //销毁后
			clearInterval(this.timer)
		},
		created() {
			this.timeAll = Number(JSON.parse(JSON.stringify(this.times)))
		},

		methods: {
			start() { //开始事件
				clearTimeout(this.setTimeStart)
				this.setTimeStart = setTimeout(() => {
					this.setCountDown()
				}, 100)
			},
			setCountDown() { //设置
				this.$nextTick(() => {
					if (this.timeAll < 1000) {
						// uni.showToast({
						// 	title: '时间最少1s',
						// 	icon: 'none',
						// 	duration: 1500,
						// 	mask: true
						// });
					} else {
						if (this.finish) return
						clearInterval(this.timer)
						this.finish = true
						this.pause_text = 'running'
						this.setIn()
					}
				})
			},
			setProgressTime() { //设置指定时间
				this.$nextTick(() => {
					if (this.progress_time < 0) {
						// uni.showToast({
						// 	title: '指定时间不能为负数',
						// 	icon: 'none',
						// 	duration: 1500,
						// 	mask: true
						// });
					} else if (this.progress_time > this.timeAll) {
						// uni.showToast({
						// 	title: '指定时间不能大于总时间',
						// 	icon: 'none',
						// 	duration: 1500,
						// 	mask: true
						// });
					} else {
						this.resetData()
					}
				})
			},
			setIn(e) { //计时器
				this.timer = setInterval(() => {
					let times_num = this.times - 100
					this.$emit('update:times', times_num)
					if (e) {
						this.pause_text = 'running'
					}
					if (this.times <= 100) {
						this.finish = true
						this.is_continue = true
						setTimeout(() => {
							this.pause_text = 'paused'
							this.$emit('endTime')
							this.$emit('update:times', this.timeAll)
						}, 100)
						clearInterval(this.timer)
					}
				}, 100)
			},
			pause() { //暂停
				if (!this.finish || this.pause_text == 'paused') return
				this.pause_text = 'paused'
				this.is_continue = false
				clearInterval(this.timer)
				this.$emit('pauseNum', this.times)
			},
			goOn() { //继续
				if (this.is_continue) return
				this.is_continue = true
				this.setIn(true)
			},
			reset() { //重置
				clearTimeout(this.timereset)
				this.timereset = setTimeout(() => {
					this.stycircle2 = 'none'
					this.stycircle1 = 'none'
					this.assignDelay = ''
					this.animationDelay = ''
					this.pause_text = 'paused'
					clearInterval(this.timer)
					this.finish = false
					this.is_continue = true
					this.$emit('update:times', this.timeAll)

					clearTimeout(this.timereseta)
					this.timereseta = setTimeout(() => {
						this.resetData()
					}, 50)
				}, 100)
			},
			resetData() { //重置动画数据
				this.assignDelay = `-${this.progress_time}ms`
				let time = Number((this.times / 2).toFixed(2))
				this.stycircle2 = `progross2 ${time}ms linear forwards paused`
				this.animationDelay = `${time - this.progress_time}ms`
				this.stycircle1 = `progross1 ${time}ms linear forwards paused`
				this.pause_text = 'paused'
				let times_nums = this.times - this.progress_time
				this.$emit('update:times', times_nums)
			},
		},
		computed: {
			circle() {
				const {
					widths,
					defaultColor
				} = this
				return {
					width: `${widths}rpx`,
					height: `${widths}rpx`,
					background: defaultColor
				}
			},
			size() {
				const {
					widths
				} = this
				return {
					width: `${widths/2 + 2}rpx`,
					height: `${widths + 2}rpx`
				}
			},
			leftCircle() {
				const {
					widths,
					activeColor
				} = this
				return {
					borderTopLeftRadius: `${widths}rpx`,
					borderBottomLeftRadius: `${widths}rpx`,
					background: activeColor
				}
			},
			rightcircle() {
				const {
					widths,
					activeColor
				} = this
				return {
					borderTopRightRadius: `${widths}rpx`,
					borderBottomRightRadius: `${widths}rpx`,
					background: activeColor
				}
			},
			inner() {
				const {
					widths,
					breadth,
					bgColor
				} = this
				return {
					width: `${widths - breadth  }rpx`,
					height: `${widths - breadth }rpx`,
					background: `${bgColor}`,
					border: `2rpx solid ${bgColor}`
				}
			},
			scale() {
				const {
					defaultColor,
					scaleNun,
					scaleAngle
				} = this
				return {
					background: `repeating-conic-gradient(${defaultColor} 0 ${scaleAngle}deg, rgba(0, 0, 0, 0) 0deg ${359 / scaleNun}deg)`
				}
			}
		},
		watch: {
			progress_time: {
				handler(news, old) {
					this.setProgressTime()
				},
				deep: true,
				immediate: true,
			},
		},
	}
</script>
<!-- #endif -->

<style>
	.circle {
		border-radius: 50%;
		position: relative;
		overflow: hidden;
	}

	.circle_line {
		width: 100%;
		height: 100%;
		border-radius: 50%;
		position: absolute;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);
		overflow: hidden;
	}

	.left,
	.right {
		position: absolute;
		overflow: hidden;
	}

	.left-circle {
		/* transition: all 0s; */
		transform-origin: 100% 50%;
	}

	.right-circle {
		/* transition: all 0s; */
		transform-origin: 0% 50%;
	}

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

	.right {
		right: 0;
	}

	.inner {
		position: absolute;
		z-index: 999;
		border-radius: 50%;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);
		box-sizing: border-box;
		overflow: hidden;
		display: flex;
		align-items: center;
		justify-content: center;
	}
</style>
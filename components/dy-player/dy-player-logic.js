/**
 * 抖音式播放器公共逻辑 mixin
 * 提供：数据标准化、平台判断、Android 安全调用、时间格式化、video 上下文获取、安全播放控制
 */
export default {
	data() {
		return {
			// 播放器容器尺寸
			playerStyle: {
				width: '750rpx',
				height: '100vh'
			},
			screenWidth: 0,
			screenHeight: 0,
			statusBarHeight: 0,
			safeAreaBottom: 0,
			// 平台信息
			platform: '',
			isApp: false,
			isAndroid: false,
			isiOS: false,
			isH5: false,
			isMP: false
		}
	},
	created() {
		const sys = uni.getSystemInfoSync()
		this.screenWidth = sys.screenWidth
		this.screenHeight = sys.screenHeight
		this.statusBarHeight = sys.statusBarHeight || 0
		this.safeAreaBottom = (sys.safeAreaInsets && sys.safeAreaInsets.bottom) || 0
		// 若宿主组件声明了 tabBarHeight，则安全减去，避免视频遮挡 TabBar
		const tabBarHeight = Number(this.tabBarHeight) || 0
		const expectedHeight = Math.max(1, sys.screenHeight - this.statusBarHeight - tabBarHeight)
		const windowHeight = sys.windowHeight || expectedHeight
		this.playerStyle = {
			width: sys.screenWidth + 'px',
			height: Math.min(windowHeight, expectedHeight) + 'px'
		}

		// 平台判断
		// #ifdef APP-PLUS
		this.isApp = true
		this.isAndroid = sys.platform === 'android'
		this.isiOS = sys.platform === 'ios'
		this.platform = sys.platform
		// #endif
		// #ifdef H5
		this.isH5 = true
		this.platform = 'h5'
		// #endif
		// #ifdef MP
		this.isMP = true
		this.platform = 'mp'
		// #endif
	},
	methods: {
		/**
		 * 标准化视频数据项
		 */
		normalizeVideoItem(item, index, options = {}) {
			const isDrama = !!options.isDrama
			// 后端已把娱乐模块视频统一转码为 9:16，此处统一 cover 全屏，几乎不裁切
			const objectFit = 'cover'
			return {
				...item,
				videoIndex: index,
				videoUrl: item.videoUrl || item.src || '',
				titleImg: item.titleImg || item.coverImg || item.videoCover || '',
				name: item.name || item.title || '',
				content: item.content || item.details || '',
				objectFit,
				coverShow: item.coverShow !== false,
				coverOpacity: item.coverShow !== false,
				pauseShow: false,
				loadingShow: false,
				vodPaly: false,
				likeShow: item.isLike == 1 || item.likeShow === true,
				collectShow: item.isCollect == 1 || item.collectShow === true,
				likeCount: item.likeCount || 0,
				collectCount: item.collectCount || 0,
				selectWork: isDrama,
				gather: item.gather || index + 1,
				dramaId: item.dramaId || '',
				dramaDetailsId: item.dramaDetailsId || '',
				isEnd: item.isEnd || 0,
				// 加载重试计数
				loadRetryCount: 0,
				maxRetryCount: 2
			}
		},

		/**
		 * 安全调用 Android 原生方法（仅 APP-PLUS 环境）
		 */
		callAndroid(method, ...args) {
			// #ifdef APP-PLUS
			if (typeof window !== 'undefined' && window.rh_android && typeof window.rh_android[method] === 'function') {
				try {
					return window.rh_android[method](...args)
				} catch (e) {
					console.error('[callAndroid] error:', method, e)
				}
			} else {
				console.warn('[callAndroid] method not available:', method)
			}
			// #endif
		},

		/**
		 * 秒数转 mm:ss / hh:mm:ss
		 */
		formatSeconds(seconds) {
			if (!seconds || seconds < 0) return '00:00'
			const h = Math.floor(seconds / 3600)
			const m = Math.floor((seconds % 3600) / 60)
			const s = Math.floor(seconds % 60)
			const mm = m < 10 ? '0' + m : m
			const ss = s < 10 ? '0' + s : s
			if (h > 0) {
				const hh = h < 10 ? '0' + h : h
				return `${hh}:${mm}:${ss}`
			}
			return `${mm}:${ss}`
		},

		/**
		 * 获取 video 上下文
		 */
		getVideoContext(id) {
			if (!id) return null
			try {
				return uni.createVideoContext(id, this)
			} catch (e) {
				console.error('[getVideoContext] error:', id, e)
				return null
			}
		},

		/**
		 * 安全播放（带失败重试）
		 * @param {String} id video 上下文 id
		 * @param {String|Number} rate 播放倍速
		 */
		safePlay(id, item) {
			const ctx = this.getVideoContext(id)
			if (!ctx) return
			try {
				ctx.play()
				this.safePlaybackRate(ctx, item)
			} catch (e) {
				console.error('[safePlay] error:', e)
			}
		},

		/**
		 * 安全暂停
		 */
		safePause(id) {
			const ctx = this.getVideoContext(id)
			if (!ctx) return
			try {
				ctx.pause()
			} catch (e) {
				console.error('[safePause] error:', e)
			}
		},

		/**
		 * 安全设置倍速（部分 Android 真机不支持）
		 */
		safePlaybackRate(ctx, rate) {
			if (!ctx || !ctx.playbackRate) return
			const r = Number(rate) || 1
			try {
				ctx.playbackRate(r)
			} catch (e) {
				console.warn('[safePlaybackRate] not supported:', r, e)
			}
		},

		/**
		 * 尝试重载视频（用于加载失败时）
		 */
		reloadVideo(id, item) {
			if (!item) return false
			if (item.loadRetryCount >= item.maxRetryCount) return false
			item.loadRetryCount++
			console.log('[reloadVideo] retry:', item.loadRetryCount, item.videoUrl)
			const ctx = this.getVideoContext(id)
			if (ctx && ctx.seek) {
				try {
					ctx.seek(0)
					ctx.play()
					return true
				} catch (e) {
					console.error('[reloadVideo] error:', e)
				}
			}
			return false
		},

		/**
		 * 格式化数字（万+）
		 */
		formatCount(count) {
			const n = Number(count) || 0
			if (n >= 10000) return (n / 10000).toFixed(0) + '万+'
			return n
		},

		/**
		 * px 转 rpx
		 */
		pxToRpx(px) {
			if (!this.screenWidth) return px
			return 750 * Number(px) / this.screenWidth
		},

		/**
		 * 简单节流
		 */
		throttle(fn, wait) {
			let last = 0
			return function(...args) {
				const now = Date.now()
				if (now - last >= wait) {
					last = now
					fn.apply(this, args)
				}
			}
		},

		/**
		 * 简单防抖
		 */
		debounce(fn, wait) {
			let timer = null
			return function(...args) {
				if (timer) clearTimeout(timer)
				timer = setTimeout(() => {
					fn.apply(this, args)
				}, wait)
			}
		}
	}
}

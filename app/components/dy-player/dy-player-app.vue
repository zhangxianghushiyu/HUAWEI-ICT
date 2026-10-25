<template>
	<view class="dy-player-app" :style="playerStyle">
		<!-- App 端：video 内部使用 cover-view 全屏覆盖层，
		     覆盖层负责接收滑动/点击/长按，UI 元素阻止事件冒泡避免误触发 -->
		<video v-if="totalList.length > 0 && !showEpisodePanel" id="appVideo" class="dy-video" :style="playerStyle"
			:src="currentItem.videoUrl" :poster="currentItem.titleImg"
			:object-fit="currentItem.objectFit || 'cover'" :loop="loopPlay" :controls="false"
			show-center-play-btn="false" show-play-btn="false" show-fullscreen-btn="false"
			show-loading="false" :enable-progress-gesture="false" :vslide-gesture="false"
			:vslide-gesture-in-fullscreen="false" :enable-play-gesture="false" :autoplay="false"
			:http-cache="true"
			@play="onPlay" @pause="onPause" @waiting="onWaiting" @error="onVideoError"
			@timeupdate="onTimeUpdate" @ended="onEnded" @loadedmetadata="onLoadedMetadata">

			<!-- 全屏 cover-view 覆盖层 -->
			<cover-view class="app-overlay">

				<!-- 手势背景层：盖在 video 上，专门接收滑动/点击/长按 -->
				<cover-view class="app-gesture-bg"
					@touchstart="onTouchStart"
					@touchmove="onTouchMove"
					@touchend="onTouchEnd"
					@touchcancel="onTouchCancel"
					@longpress="onLongPress"
					@tap="onGestureTap">
				</cover-view>

				<!-- 封面层 -->
				<cover-view v-show="currentItem.coverOpacity" class="app-cover" @tap.stop="togglePlay">
					<cover-image class="app-cover-img" :src="currentItem.titleImg" mode="aspectFill" />
				</cover-view>

				<!-- 加载中 -->
				<cover-view v-show="currentItem.loadingShow" class="app-loading">
					<cover-view class="app-loading-text">加载中</cover-view>
				</cover-view>

				<!-- 暂停按钮 -->
				<cover-view v-show="currentItem.pauseShow && !currentItem.loadingShow" class="app-pause" @tap.stop="onPauseTap"
					@touchstart.stop="onStop" @touchmove.stop="onStop" @touchend.stop="onStop" @longpress.stop="onStop">
					<cover-image class="app-pause-img" src="/static/icon/vod-play.png" mode="aspectFit" />
				</cover-view>

				<!-- 加载失败 -->
				<cover-view v-if="currentItem.loadError" class="app-error" @tap.stop="onErrorTap"
					@touchstart.stop="onStop" @touchmove.stop="onStop" @touchend.stop="onStop" @longpress.stop="onStop">
					<cover-view class="app-error-text">视频加载失败，点击重试</cover-view>
				</cover-view>

				<!-- 底部信息 -->
				<cover-view class="app-bottom-info">
					<cover-view class="app-info-name">{{ currentItem.name }}</cover-view>
					<cover-view class="app-info-content">{{ currentItem.content }}</cover-view>
				</cover-view>

				<!-- 进度条（仅横向拖动时显示） -->
				<cover-view v-show="showProgressBar" class="app-progress-drag-time">
					<cover-view class="app-progress-drag-current">{{ formatSeconds(sliderTime) }}</cover-view>
					<cover-view class="app-progress-drag-total"> / {{ formatSeconds(videoTime) }}</cover-view>
				</cover-view>
				<cover-view v-show="showProgressBar" class="app-progress-bar">
					<cover-view class="app-progress-track"></cover-view>
					<cover-view class="app-progress-fill" :style="{ width: progressPercent + '%' }"></cover-view>
				</cover-view>

				<!-- 顶部栏 -->
				<cover-view class="app-top-bar" :style="{ paddingTop: statusBarHeight + 'px' }"
					@touchstart.stop="onStop" @touchmove.stop="onStop" @touchend.stop="onStop" @longpress.stop="onStop">
					<cover-image v-if="showBack" class="app-back" src="/static/icon/black2.png" mode="aspectFit" @tap.stop="onBack" />
					<cover-view v-if="showSearch" class="app-search-btn" @tap.stop="onSearchClick">
						<cover-image src="/static/icon/black2.png" mode="aspectFit" />
						<cover-view>前去搜索</cover-view>
					</cover-view>
					<cover-view v-if="isDrama" class="app-episode-tag">第 {{ currentItem.gather }} 集</cover-view>
				</cover-view>

				<!-- 右侧菜单 -->
				<cover-view class="app-menu" @touchstart.stop="onStop" @touchmove.stop="onStop" @touchend.stop="onStop" @longpress.stop="onStop">
					<cover-view class="app-menu-item">
						<cover-view class="app-menu-icon">
							<cover-image class="app-menu-img" src="/static/juhe/2.png" mode="aspectFit" />
						</cover-view>
						<cover-view class="app-menu-text">{{ rateList[rateIndex] }}x</cover-view>
						<cover-view class="app-menu-hit" @tap.stop="onRateClick"></cover-view>
					</cover-view>

					<cover-view class="app-menu-item">
						<cover-view class="app-menu-icon" :class="{ 'app-menu-icon-active': likeeffectIndex === currentIndex }">
							<cover-image v-if="currentItem.likeShow" class="app-menu-img" src="/static/icon/selectTaoxin.png" mode="aspectFit" />
							<cover-image v-else class="app-menu-img" src="/static/icon/taoxin.png" mode="aspectFit" />
						</cover-view>
						<cover-view class="app-menu-text">{{ formatCount(currentItem.likeCount) }}</cover-view>
						<cover-view class="app-menu-hit" @tap.stop="onMenuAction('like')"></cover-view>
					</cover-view>

					<cover-view class="app-menu-item">
						<cover-view class="app-menu-icon" :class="{ 'app-menu-icon-active': collecteffectIndex === currentIndex }">
							<cover-image v-if="currentItem.collectShow" class="app-menu-img" src="/static/icon/selectStar.png" mode="aspectFit" />
							<cover-image v-else class="app-menu-img" src="/static/icon/star.png" mode="aspectFit" />
						</cover-view>
						<cover-view class="app-menu-text">{{ formatCount(currentItem.collectCount) }}</cover-view>
						<cover-view class="app-menu-hit" @tap.stop="onMenuAction('collect')"></cover-view>
					</cover-view>

					<cover-view v-if="showShare" class="app-menu-item">
						<cover-view class="app-menu-icon">
							<cover-image class="app-menu-img" src="/static/icon/ward.png" mode="aspectFit" />
						</cover-view>
						<cover-view class="app-menu-text">分享</cover-view>
						<cover-view class="app-menu-hit" @tap.stop="onMenuAction('share')"></cover-view>
					</cover-view>

					<cover-view v-if="showReport" class="app-menu-item">
						<cover-view class="app-menu-icon app-menu-icon-text">⚠️</cover-view>
						<cover-view class="app-menu-text">举报</cover-view>
						<cover-view class="app-menu-hit" @tap.stop="onMenuAction('report')"></cover-view>
					</cover-view>

					<cover-view v-if="isDrama" class="app-menu-item">
						<cover-view class="app-menu-icon">
							<cover-image class="app-menu-img" src="/static/icon/selectedWorks.png" mode="aspectFit" />
						</cover-view>
						<cover-view class="app-menu-text">选集</cover-view>
						<cover-view class="app-menu-hit" @tap.stop="onMenuAction('episode')"></cover-view>
					</cover-view>

					<cover-view v-if="!isDrama && currentItem.dramaId" class="app-menu-item">
						<cover-view class="app-menu-icon">
							<cover-image class="app-menu-img" src="/static/icon/selectedWorks.png" mode="aspectFit" />
						</cover-view>
						<cover-view class="app-menu-text">系列</cover-view>
						<cover-view class="app-menu-hit" @tap.stop="onMenuAction('seriesDetail')"></cover-view>
					</cover-view>
				</cover-view>

				<!-- 红包 -->
				<cover-view v-show="showRedPacket && redPacketState.times > -1" class="app-red-packet" @tap.stop="onRedPacketTap"
					@touchstart.stop="onStop" @touchmove.stop="onStop" @touchend.stop="onStop" @longpress.stop="onStop">
					<cover-image class="app-red-packet-img" src="/static/6.png" mode="aspectFit" />
					<cover-view v-if="redPacketState.isEnd" class="app-red-packet-text">立即领取</cover-view>
					<cover-view v-else class="app-red-packet-time">{{ redPacketRemainText }}</cover-view>
				</cover-view>

				<!-- 双击点赞桃心 -->
				<cover-view v-for="(heart, hIdx) in heartList" :key="heart.id" class="app-heart"
					:style="{ top: heart.top, left: heart.left, width: heart.width, height: heart.height, transform: 'rotate(' + heart.rotate + ')' }"
					:class="heart.className">
					<cover-image src="/static/icon/selectTaoxin.png" :style="{ width: heart.width, height: heart.height }" />
				</cover-view>

				<!-- 解锁弹窗 -->
				<cover-view v-if="showUnlockPopup" class="app-unlock-mask" @tap.stop="showUnlockPopup = false"
					@touchstart.stop="onStop" @touchmove.stop="onStop" @touchend.stop="onStop" @longpress.stop="onStop">
					<cover-view class="app-unlock" @tap.stop>
						<cover-view class="app-unlock-title">本集未解锁</cover-view>
						<cover-view class="app-unlock-desc">观看完整内容需要解锁</cover-view>
						<cover-view class="app-unlock-btn" @tap.stop="onUnlockAction('gold')">金币解锁</cover-view>
						<cover-view class="app-unlock-btn app-unlock-btn-ad" @tap.stop="onUnlockAction('ad')">看广告解锁{{ unlockAdCount > 1 ? ' ' + unlockAdCount + ' 集' : '' }}</cover-view>
						<cover-view class="app-unlock-close" @tap.stop="showUnlockPopup = false">稍后再看</cover-view>
					</cover-view>
				</cover-view>
			</cover-view>
		</video>

		<!-- 选集面板：App 端 cover-view 无法滚动，改用页级普通视图 + scroll-view -->
		<view v-if="showEpisodePanel" class="app-episode-mask" @tap.stop="closeEpisodePanel">
			<view class="app-episode" @tap.stop>
				<view class="app-episode-header">
					<view class="app-episode-title">选集</view>
					<view class="app-episode-close" @tap.stop="closeEpisodePanel">✕</view>
				</view>
				<view class="app-episode-count">共 {{ totalList.length }} 集</view>
				<scroll-view scroll-y class="app-episode-scroll">
					<view class="app-episode-grid">
						<view v-for="(ep, epIdx) in totalList" :key="epIdx" class="app-episode-item"
							:class="{ 'app-episode-current': currentIndex === epIdx, 'app-episode-locked': !ep.videoUrl }"
							@tap.stop="onEpisodeSelect(epIdx)">
							<text v-if="ep.videoUrl">{{ epIdx + 1 }}</text>
							<image v-else class="app-episode-lock" src="/static/icon/lock.png" mode="widthFix" />
						</view>
					</view>
				</scroll-view>
			</view>
		</view>

		<!-- 举报弹窗 -->
		<dy-report-popup :visible="showReportPopup" @cancel="showReportPopup = false" @submit="onReportSubmit" />
	</view>
</template>

<script>
	import dyPlayerLogic from './dy-player-logic.js'
	import dyReportPopup from './dy-report-popup.vue'

	export default {
		components: { dyReportPopup },
		mixins: [dyPlayerLogic],
		props: {
			list: {
				type: Array,
				default: () => []
			},
			startIndex: {
				type: Number,
				default: 0
			},
			isDrama: {
				type: Boolean,
				default: false
			},
			info: {
				type: Object,
				default: () => ({})
			},
			autoPlay: {
				type: Boolean,
				default: true
			},
			loopPlay: {
				type: Boolean,
				default: false
			},
			autoNext: {
				type: Boolean,
				default: true
			},
			showBack: {
				type: Boolean,
				default: true
			},
			showSearch: {
				type: Boolean,
				default: false
			},
			showRedPacket: {
				type: Boolean,
				default: true
			},
			redPacketTimes: {
				type: Number,
				default: -1
			},
			// 娱乐页插屏广告专用红包
			showEntRedPacket: {
				type: Boolean,
				default: false
			},
			entRedPacketReady: {
				type: Boolean,
				default: false
			},
			entRedPacketRemain: {
				type: Number,
				default: 0
			},
			entRedPacketProgress: {
				type: Number,
				default: 0
			},
			showGoldUnlock: {
				type: Boolean,
				default: false
			},
			unlockGoldCost: {
				type: [Number, String],
				default: 0
			},
			// 看广告一次解锁的集数
			unlockAdCount: {
				type: [Number, String],
				default: 1
			},
			// 是否显示分享按钮
			showShare: {
				type: Boolean,
				default: true
			},
			// 是否显示举报按钮
			showReport: {
				type: Boolean,
				default: false
			},
			// 娱乐页顶部分类 tab（App端原生video会遮挡H5 DOM，需在cover-view内渲染）
			entTabList: {
				type: Array,
				default: () => []
			},
			entSubTabList: {
				type: Array,
				default: () => []
			},
			entActiveTabIndex: {
				type: Number,
				default: 0
			},
			entSelectedSubId: {
				type: [Number, String],
				default: 0
			},
			// TabBar 高度（单位 px）：在 Tab 页使用时传入，避免视频遮挡底部 TabBar
			tabBarHeight: {
				type: Number,
				default: 0
			}
		},
		data() {
			return {
				totalList: [],
				currentIndex: 0,
				isPlaying: false,
				currentTime: 0,
				videoTime: 0,
				// 横滑 seek 相关
				showProgressBar: false,
				isSeeking: false,
				sliderTime: 0,
				seekStartTime: 0,
				hideProgressTimer: null,
				showEpisodePanel: false,
				// 打开选集面板时记录的播放进度，关闭后恢复（避免整集重播）
				episodePanelSavedTime: 0,
				showUnlockPopup: false,
				showReportPopup: false,
				lockedItem: null,
				// 手势状态
				touchStartX: 0,
				touchStartY: 0,
				touchStartTime: 0,
				touchDirection: '', // '' | 'v' | 'h'
				hasMoved: false,
				touchHasSwiped: false,
				isSwitching: false,
				switchLockTimer: null,
				// 点击/双击
				lastTapTime: 0,
				lastTapTimer: null,
				// 特效索引
				likeeffectIndex: -1,
				collecteffectIndex: -1,
				// 任务计时
				taskTimer: null,
				taskTime: 0,
				playInfoIndex: -1,
				// 红包
				redPacketTimer: null,
				redPacketState: {
					times: -1,
					remainMs: 0,
					isEnd: false
				},
				// 倍速
				rateList: ['0.5', '1.0', '2.0'],
				rateIndex: 1,
				// 桃心动画
				heartList: [],
				heartId: 1,
				// 待播放标记（等待 loadedmetadata）
				pendingPlay: false,
				// 视频准备超时
				metadataTimer: null,
				// App 端原生手势浮层
				gestureView: null,
				// 原生浮层长按定时器
				gestureLongPressTimer: null,
				// App 端娱乐页插屏红包原生浮层
				redPacketView: null,
				// App 端娱乐页顶部分类原生浮层
				entTabView: null,
				// 分类 tab 点击热区
				entTabHits: []
			}
		},
		computed: {
			currentItem() {
				return this.totalList[this.currentIndex] || {}
			},
			progressPercent() {
				const dur = this.videoTime || 0
				if (dur <= 0) return 0
				const t = this.isSeeking ? this.sliderTime : (this.currentTime || 0)
				return Math.min(100, Math.max(0, (t / dur) * 100))
			},
			redPacketRemainText() {
				const ms = this.redPacketState.remainMs || 0
				if (ms <= 0) return '00:00'
				const totalSeconds = Math.ceil(ms / 1000)
				const m = Math.floor(totalSeconds / 60)
				const s = totalSeconds % 60
				return `${m < 10 ? '0' + m : m}:${s < 10 ? '0' + s : s}`
			}
		},
		watch: {
			redPacketTimes: {
				immediate: true,
				handler(val) {
					this.resetRedPacket(val)
				}
			},
			showEntRedPacket(val) {
				// #ifdef APP-PLUS
				if (val) {
					this.showRedPacketOverlay()
				} else {
					this.hideRedPacketOverlay()
				}
				// #endif
			},
			entRedPacketReady() {
				// #ifdef APP-PLUS
				this.updateRedPacketView()
				// #endif
			},
			entRedPacketRemain() {
				// #ifdef APP-PLUS
				if (!this.entRedPacketReady) {
					this.updateRedPacketView()
				}
				// #endif
			},
			entRedPacketProgress() {
				// #ifdef APP-PLUS
				if (!this.entRedPacketReady) {
					this.updateRedPacketView()
				}
				// #endif
			},
			entTabList() {
				// #ifdef APP-PLUS
				this.updateEntTabView()
				// #endif
			},
			entSubTabList() {
				// #ifdef APP-PLUS
				this.updateEntTabView()
				// #endif
			},
			entActiveTabIndex() {
				// #ifdef APP-PLUS
				this.updateEntTabView()
				// #endif
			},
			entSelectedSubId() {
				// #ifdef APP-PLUS
				this.updateEntTabView()
				// #endif
			},
			showEpisodePanel(val) {
				// #ifdef APP-PLUS
				if (val) {
					this.hideGestureOverlayFull()
				} else {
					this.showGestureOverlayFull()
				}
				// #endif
				if (val) {
					// 记录当前播放进度并暂停，面板关闭后恢复（面板打开期间 video 会被卸载以让出滚动层）
					this.episodePanelSavedTime = this.currentTime
					this.pauseCurrent()
				}
			},
			showUnlockPopup(val) {
				// #ifdef APP-PLUS
				if (val) {
					this.hideGestureOverlayFull()
				} else {
					this.showGestureOverlayFull()
				}
				// #endif
			},
			currentIndex(newIdx, oldIdx) {
				if (newIdx !== oldIdx) {
					this.currentTime = 0
					this.videoTime = 0
					this.startTask()
					this.$emit('change', {
						index: newIdx,
						item: this.currentItem
					})
					this.checkAutoLoadMore()
				}
			}
		},
		created() {
			// 覆盖 mixin 默认 playerStyle，按 tabBarHeight 安全计算
			this.recomputePlayerStyle()
		},
		mounted() {
			// #ifdef APP-PLUS
			this.createGestureView()
			if (this.showEntRedPacket) {
				this.createRedPacketView()
			}
			// 只有存在分类 tab 时才创建顶部分类浮层，否则会多出一块黑色半透明阴影遮挡视频顶部
			if (this.entTabList.length > 0 || this.entSubTabList.length > 0) {
				this.createEntTabView()
			}
			// #endif
		},
		beforeDestroy() {
			this.clearTaskTimer()
			this.clearRedPacketTimer()
			this.clearMetadataTimer()
			this.clearSwitchLock()
			this.clearTapTimer()
			if (this.hideProgressTimer) {
				clearTimeout(this.hideProgressTimer)
				this.hideProgressTimer = null
			}
			// #ifdef APP-PLUS
			this.destroyGestureView()
			this.destroyRedPacketView()
			this.destroyEntTabView()
			// #endif
		},
		onShow() {
			// 页面重新显示时（如激励视频广告关闭后）重算高度，防止视频层覆盖 TabBar
			this.recomputePlayerStyle()
		},
		methods: {
			// ================== 初始化 ==================
			initPlayer(list, startIndex = 0) {
				console.log('[dy-player-app] initPlayer, list.length:', list.length, 'startIndex:', startIndex)
				this.totalList = list.map((item, idx) => this.normalizeVideoItem(item, idx, {
					isDrama: this.isDrama
				}))

				let idx = startIndex || 0
				if (idx < 0) idx = 0
				if (idx >= this.totalList.length) idx = this.totalList.length - 1
				this.currentIndex = idx

				this.$nextTick(() => {
					if (this.autoPlay) {
						this.playCurrent()
					}
				})
			},

			/**
			 * 重算播放器容器高度。
			 * 在 Tab 页使用时传入 tabBarHeight，确保 video 不会覆盖底部 TabBar。
			 * 激励视频广告关闭后页面 onShow 会再次调用，防止原生层重布局导致遮挡。
			 */
			recomputePlayerStyle() {
				try {
					const sys = uni.getSystemInfoSync()
					const screenWidth = sys.screenWidth || 375
					const screenHeight = sys.screenHeight || sys.windowHeight || 667
					const statusBarHeight = sys.statusBarHeight || 0
					const safeAreaBottom = (sys.safeAreaInsets && sys.safeAreaInsets.bottom) || 0
					const tabBarHeight = Number(this.tabBarHeight) || 0

					// windowHeight 通常已排除 TabBar，但广告关闭后可能异常，用 expectedHeight 兜底
					const expectedHeight = Math.max(1, screenHeight - statusBarHeight - tabBarHeight)
					const windowHeight = sys.windowHeight || expectedHeight
					const height = Math.min(windowHeight, expectedHeight)

					this.playerStyle = {
						width: screenWidth + 'px',
						height: height + 'px'
					}
					this.screenWidth = screenWidth
					this.screenHeight = screenHeight
					this.statusBarHeight = statusBarHeight
					this.safeAreaBottom = safeAreaBottom
					console.log('[dy-player-app] recomputePlayerStyle:', this.playerStyle, 'tabBarHeight:', tabBarHeight)
				} catch (e) {
					console.error('[dy-player-app] recomputePlayerStyle error:', e)
				}
			},

						// #ifdef APP-PLUS
			// ================== App 端原生手势浮层 ==================
			/**
			 * 创建原生手势浮层，覆盖在 video 主内容区接收滑动事件。
			 *
			 * 问题背景：
			 * cover-view 在部分 Android 机型上无法拦截原生 video 的滑动手势。
			 * 因为原生 video 在收到 ACTION_DOWN 后一旦检测到移动就会自行消费事件。
			 *
			 * 解决方式：
			 * 1. 用 plus.nativeObj.View 只覆盖主内容区（排除右侧菜单、顶部返回栏）
			 * 2. 从 touchstart 就 interceptTouchEvent(true)，彻底阻止 video 抢事件
			 * 3. 由原生浮层自己识别单击、双击、长按、滑动，再调用已有业务方法
			 */
			createGestureView() {
				if (this.gestureView) return
				if (typeof plus === 'undefined' || !plus.nativeObj) {
					console.warn('[dy-player-app] plus.nativeObj not available')
					return
				}

				try {
					const sys = uni.getSystemInfoSync()
					const screenWidth = sys.screenWidth
					const screenHeight = sys.screenHeight
					const statusBarHeight = sys.statusBarHeight || 0
					const rpx = val => val * screenWidth / 750
					// 安全区底部高度：部分机型 safeAreaInsets 不存在，用 safeArea 兜底
					const safeAreaBottom = (sys.safeAreaInsets && sys.safeAreaInsets.bottom) ||
						(sys.safeArea ? Math.max(0, sys.screenHeight - sys.safeArea.bottom) : 0)
					// 顶部栏高约 100rpx + 状态栏
					const topBarHeightPx = statusBarHeight + rpx(180)
					// 底部导航栏高度：优先用外部传入的 tabBarHeight，否则按 100rpx + 安全区兜底
					const tabBarHeightPx = (Number(this.tabBarHeight) > 0 ? Number(this.tabBarHeight) : (rpx(100) + safeAreaBottom))
					// 右侧留出 160rpx，避免遮挡右侧菜单/外部悬浮红包
					const rightMarginPx = rpx(160)
					const gestureWidth = Math.max(1, screenWidth - rightMarginPx)
					const gestureHeight = Math.max(1, screenHeight - topBarHeightPx - tabBarHeightPx)

					this.gestureView = new plus.nativeObj.View('dyGestureView', {
						top: topBarHeightPx + 'px',
						left: '0px',
						width: gestureWidth + 'px',
						height: gestureHeight + 'px',
						backgroundColor: 'rgba(0,0,0,0)'
					})

					let startX = 0
					let startY = 0
					let startTime = 0
					let isSwiping = false
					let localSeeking = false
					let localSeekStart = 0
					let longPressHandled = false
					let hasMoved = false

					const clearGestureLongPress = () => {
						if (this.gestureLongPressTimer) {
							clearTimeout(this.gestureLongPressTimer)
							this.gestureLongPressTimer = null
						}
					}

					this.gestureView.addEventListener('touchstart', (e) => {
						const touch = (e.touches && e.touches[0]) || e
						startX = touch.clientX || touch.pageX || 0
						startY = touch.clientY || touch.pageY || 0
						startTime = Date.now()
						isSwiping = false
						localSeeking = false
						localSeekStart = this.currentTime || 0
						longPressHandled = false
						hasMoved = false

						clearGestureLongPress()

						// 立即拦截，防止原生 video 抢走滑动事件
						if (this.gestureView) this.gestureView.interceptTouchEvent(true)

						// 长按检测
						this.gestureLongPressTimer = setTimeout(() => {
							longPressHandled = true
							this.gestureLongPressTimer = null
							this.onLongPress()
						}, 600)
					})

					this.gestureView.addEventListener('touchmove', (e) => {
						const touch = (e.touches && e.touches[0]) || e
						const x = touch.clientX || touch.pageX || 0
						const y = touch.clientY || touch.pageY || 0
						const dx = x - startX
						const dy = y - startY
						const adx = Math.abs(dx)
						const ady = Math.abs(dy)

						// 移动超过阈值，取消长按/单击判定
						if (adx > 5 || ady > 5) {
							hasMoved = true
							clearGestureLongPress()
						}

						// 检测纵向滑动
						if (!isSwiping && !localSeeking && ady > adx * 1.2 && ady > 20) {
							isSwiping = true
						}
						// 检测横向拖动 -> seek
						if (!isSwiping && !localSeeking && adx > ady * 1.2 && adx > 20 && this.videoTime > 0) {
							localSeeking = true
							this.isSeeking = true
							this.showProgressBar = true
							if (this.hideProgressTimer) {
								clearTimeout(this.hideProgressTimer)
								this.hideProgressTimer = null
							}
						}
						if (localSeeking && this.videoTime > 0) {
							const w = this.screenWidth || 375
							this.sliderTime = Math.min(this.videoTime, Math.max(0, localSeekStart + (dx / w) * this.videoTime))
						}
					})

					this.gestureView.addEventListener('touchend', (e) => {
						clearGestureLongPress()

						// 长按已触发，不再响应单击/滑动
						if (longPressHandled) {
							if (localSeeking) {
								this.finishSeek()
							}
							return
						}

						// 横滑 seek 结束
						if (localSeeking) {
							this.finishSeek()
							return
						}

						const touch = (e.changedTouches && e.changedTouches[0]) || (e.touches && e.touches[0]) || e
						const x = touch.clientX || touch.pageX || 0
						const y = touch.clientY || touch.pageY || 0
						const dx = x - startX
						const dy = y - startY
						const adx = Math.abs(dx)
						const ady = Math.abs(dy)
						const dt = Date.now() - startTime

						// 优先判定滑动：touchmove 已识别为滑动，或最终纵向位移足够
						if (isSwiping || ady > 40 || (ady > 20 && dt > 0 && ady / dt > 0.4)) {
							this.handleSwipeSwitch(dy)
						} else if (!hasMoved && adx < 10 && ady < 10 && dt < 250) {
							// 无明显移动才认为是单击/双击
							this.onGestureTap({ clientX: x, clientY: y })
						}
					})

					this.gestureView.addEventListener('touchcancel', (e) => {
						clearGestureLongPress()
						if (localSeeking) {
							this.finishSeek()
						}
					})

					this.gestureView.show()
					this.gestureView.interceptTouchEvent(true)
					console.log('[dy-player-app] gestureView created')
				} catch (err) {
					console.error('[dy-player-app] createGestureView error:', err)
				}
			},

			destroyGestureView() {
				if (this.gestureLongPressTimer) {
					clearTimeout(this.gestureLongPressTimer)
					this.gestureLongPressTimer = null
				}
				if (!this.gestureView) return
				try {
					this.gestureView.close()
				} catch (e) {}
				this.gestureView = null
				console.log('[dy-player-app] gestureView destroyed')
			},

			/**
			 * 广告（原生 Activity）关闭返回后重建手势浮层。
			 * plus.nativeObj.View 在原生 Activity 切换后位置/尺寸可能偏移，
			 * 导致透明手势层盖到 TabBar 上拦截触摸，需销毁后用最新窗口尺寸重建。
			 */
			rebuildGestureView() {
				if (typeof plus === 'undefined' || !plus.nativeObj) return
				this.destroyGestureView()
				this.createGestureView()
			},

			// ================== App 端顶部分类原生浮层 ==================
			/**
			 * 创建顶部分类原生浮层。
			 * cover-view 在真机 Android 上 flex 布局不可靠、不支持横向滚动，
			 * 改用 plus.nativeObj.View 原生层渲染，层级最高且点击稳定。
			 */
			createEntTabView() {
				if (this.entTabView) return
				if (typeof plus === 'undefined' || !plus.nativeObj) return
				try {
					const sys = uni.getSystemInfoSync()
					const screenWidth = sys.screenWidth
					const statusBarHeight = sys.statusBarHeight || 0
					const rpx = val => val * screenWidth / 750
					this.entTabView = new plus.nativeObj.View('dyEntTabView', {
						top: '0px',
						left: '0px',
						width: screenWidth + 'px',
						height: (statusBarHeight + rpx(180)) + 'px',
						backgroundColor: 'rgba(0,0,0,0)'
					})
					this.entTabView.addEventListener('click', (e) => {
						this.onEntTabViewClick(e)
					})
					this.entTabView.show()
					this.updateEntTabView()
				} catch (err) {
					console.error('[dy-player-app] createEntTabView error:', err)
				}
			},

			/**
			 * 根据 entTabList / entSubTabList / 选中态重绘分类浮层
			 */
			updateEntTabView() {
				if (!this.entTabView) {
					if (this.entTabList.length > 0) this.createEntTabView()
					return
				}
				// 无分类 tab 时不绘制背景阴影，避免遮挡视频顶部
				if (this.entTabList.length === 0 && this.entSubTabList.length === 0) return
				try {
					const sys = uni.getSystemInfoSync()
					const screenWidth = sys.screenWidth
					const statusBarHeight = sys.statusBarHeight || 0
					const rpx = val => val * screenWidth / 750

					const styles = []
					this.entTabHits = []

					// 顶部半透明背景
					styles.push({
						tag: 'rect',
						id: 'bg',
						color: 'rgba(0,0,0,0.55)',
						position: { top: '0px', left: '0px', width: screenWidth + 'px', height: (statusBarHeight + rpx(180)) + 'px' }
					})

					// 一级分类 tab
					const tabFontSize = rpx(30)
					const tabH = rpx(52)
					const y1 = statusBarHeight + rpx(48)
					let x = rpx(20)
					this.entTabList.forEach((tab, idx) => {
						const text = String(tab.name || '')
						const w = this.estimateTextWidth(text, tabFontSize) + rpx(28)
						const active = this.entActiveTabIndex === idx
						styles.push({
							tag: 'font',
							id: 'tab_' + idx,
							text: text,
							position: { top: y1 + 'px', left: x + 'px', width: w + 'px', height: tabH + 'px' },
							textStyles: { align: 'center', color: active ? '#ffffff' : 'rgba(255,255,255,0.7)', size: tabFontSize + 'px', weight: active ? 'bold' : 'normal' }
						})
						if (active) {
							styles.push({
								tag: 'rect',
								id: 'tab_bar_' + idx,
								color: '#e84335',
								position: { top: (y1 + tabH - rpx(5)) + 'px', left: (x + w / 2 - rpx(18)) + 'px', width: rpx(36) + 'px', height: rpx(5) + 'px' },
								rectStyles: { radius: rpx(3) + 'px' }
							})
						}
						this.entTabHits.push({ type: 'tab', index: idx, x: x, y: y1, w: w, h: tabH })
						x += w + rpx(8)
					})

					// 二级分类 tab
					if (this.entSubTabList.length > 0) {
						const subFontSize = rpx(24)
						const subH = rpx(46)
						const y2 = y1 + tabH + rpx(6)
						let sx = rpx(20)
						this.entSubTabList.forEach((sub) => {
							const text = String(sub.name || '')
							const w = this.estimateTextWidth(text, subFontSize) + rpx(40)
							const active = this.entSelectedSubId === sub.id
							styles.push({
								tag: 'rect',
								id: 'sub_bg_' + sub.id,
								color: active ? '#e84335' : 'rgba(255,255,255,0.18)',
								position: { top: y2 + 'px', left: sx + 'px', width: w + 'px', height: subH + 'px' },
								rectStyles: { radius: rpx(30) + 'px' }
							})
							styles.push({
								tag: 'font',
								id: 'sub_' + sub.id,
								text: text,
								position: { top: y2 + 'px', left: sx + 'px', width: w + 'px', height: subH + 'px' },
								textStyles: { align: 'center', color: active ? '#ffffff' : 'rgba(255,255,255,0.7)', size: subFontSize + 'px' }
							})
							this.entTabHits.push({ type: 'sub', id: sub.id, x: sx, y: y2, w: w, h: subH })
							sx += w + rpx(12)
						})
					}

					this.entTabView.draw(styles)
				} catch (err) {
					console.error('[dy-player-app] updateEntTabView error:', err)
				}
			},

			/**
			 * 销毁分类浮层
			 */
			destroyEntTabView() {
				if (!this.entTabView) return
				try {
					this.entTabView.close()
				} catch (e) {}
				this.entTabView = null
			},

			showEntTabView() {
				if (!this.entTabView) {
					if (this.entTabList.length > 0) this.createEntTabView()
					return
				}
				try {
					this.entTabView.show()
				} catch (e) {
					console.warn('[dy-player-app] showEntTabView error:', e)
				}
			},

			hideEntTabView() {
				if (!this.entTabView) return
				try {
					this.entTabView.hide()
				} catch (e) {
					console.warn('[dy-player-app] hideEntTabView error:', e)
				}
			},

			/**
			 * 分类浮层点击：按坐标命中热区后转发对应事件
			 */
			onEntTabViewClick(e) {
				const x = e.clientX || 0
				const y = e.clientY || 0
				const hits = this.entTabHits || []
				for (const hit of hits) {
					if (x >= hit.x && x <= hit.x + hit.w && y >= hit.y && y <= hit.y + hit.h) {
						if (hit.type === 'tab') {
							const tab = this.entTabList[hit.index]
							if (tab) this.$emit('entTabClick', { item: tab, index: hit.index })
						} else {
							const sub = this.entSubTabList.find(s => s.id === hit.id)
							if (sub) this.$emit('entSubTabClick', { item: sub })
						}
						break
					}
				}
			},

			/**
			 * 估算文字渲染宽度（中文约等于字号，半角约 0.55 倍）
			 */
			estimateTextWidth(text, fontSize) {
				let w = 0
				const s = String(text || '')
				for (let i = 0; i < s.length; i++) {
					w += (s.charCodeAt(i) > 255) ? fontSize : fontSize * 0.55
				}
				return w
			},

			/**
			 * 创建娱乐页插屏红包原生浮层
			 * 原生浮层层级最高，可覆盖原生 video 并稳定接收点击
			 */
			createRedPacketView() {
				if (this.redPacketView) return
				if (typeof plus === 'undefined' || !plus.nativeObj) {
					console.warn('[dy-player-app] plus.nativeObj not available')
					return
				}

				try {

					const sys = uni.getSystemInfoSync()
					const screenWidth = sys.screenWidth
					const screenHeight = sys.screenHeight || sys.windowHeight || 0
					const statusBarHeight = sys.statusBarHeight || 0
					const rpx = val => val * screenWidth / 750
					const size = rpx(160)

					// 红包浮层位置：右侧 24rpx，顶部 120rpx（和非 App 端样式保持一致）
					const top = statusBarHeight + rpx(90)
					const right = rpx(24)
					const left = screenWidth - right - size

					this.redPacketView = new plus.nativeObj.View('dyRedPacketView', {
						top: top + 'px',
						left: left + 'px',
						width: size + 'px',
						height: size + 'px',
						backgroundColor: 'rgba(0,0,0,0)'
					})

					this.redPacketView.addEventListener('click', () => {
						console.log('[dy-player-app] 原生红包浮层被点击，ready=', this.entRedPacketReady)
						this.$emit('entRedPacketTap')
					})

					this.updateRedPacketView()
					this.redPacketView.show()
					console.log('[dy-player-app] redPacketView created')
				} catch (err) {
					console.error('[dy-player-app] createRedPacketView error:', err)
				}
			},

			destroyRedPacketView() {
				if (!this.redPacketView) return
				try {
					this.redPacketView.close()
				} catch (e) {}
				this.redPacketView = null
				console.log('[dy-player-app] redPacketView destroyed')
			},

			showRedPacketOverlay() {
				if (!this.redPacketView) {
					this.createRedPacketView()
					return
				}
				try {
					this.updateRedPacketView()
					this.redPacketView.show()
					console.log('[dy-player-app] redPacketView shown')
				} catch (e) {
					console.warn('[dy-player-app] showRedPacketOverlay error:', e)
				}
			},

			hideRedPacketOverlay() {
				if (!this.redPacketView) return
				try {
					this.redPacketView.hide()
					console.log('[dy-player-app] redPacketView hidden')
				} catch (e) {
					console.warn('[dy-player-app] hideRedPacketOverlay error:', e)
				}
			},

			updateRedPacketView() {
				if (!this.redPacketView) return
				const label = this.entRedPacketReady ? '领金币' : (this.entRedPacketRemain || 0) + '秒'
				// 内容未变化时跳过原生重绘，避免 timeupdate 每帧触发 draw 造成卡顿
				const key = this.entRedPacketReady + '|' + label
				if (this._lastRedPacketKey === key) return
				this._lastRedPacketKey = key
				try {
					const sys = uni.getSystemInfoSync()
					const screenWidth = sys.screenWidth
					const rpx = val => val * screenWidth / 750
					const size = rpx(160)
					const imgSize = rpx(110)
					const imgLeft = (size - imgSize) / 2
					const imgTop = (size - imgSize) / 2 - rpx(6)
					const ringColor = this.entRedPacketReady ? 'rgba(255,107,107,0.85)' : 'rgba(0,0,0,0.35)'
					const iconSize = rpx(80)
					const iconLeft = (size - iconSize) / 2
					const iconTop = (size - iconSize) / 2 - rpx(8)

					const styles = []
					// 背景光圈
					styles.push({
						tag: 'rect',
						id: 'ring',
						color: ringColor,
						position: { top: '0px', left: '0px', width: size + 'px', height: size + 'px' },
						rectStyles: { radius: (size / 2) + 'px' }
					})
					// 红包图标
					styles.push({
						tag: 'img',
						id: 'icon',
						src: '_www/static/6.png',
						position: { top: iconTop + 'px', left: iconLeft + 'px', width: iconSize + 'px', height: iconSize + 'px' }
					})
					// 底部文字
					styles.push({
						tag: 'font',
						id: 'label',
						text: label,
						position: { top: (size - rpx(36)) + 'px', left: '0px', width: size + 'px', height: rpx(36) + 'px' },
						textStyles: {
							align: 'center',
							color: '#ffffff',
							size: rpx(20) + 'px',
							weight: 'bold'
						}
					})

					this.redPacketView.draw(styles)
					console.log('[dy-player-app] redPacketView updated, ready=', this.entRedPacketReady, 'label=', label)
				} catch (err) {
					console.error('[dy-player-app] updateRedPacketView error:', err)
				}
			},

			/**
			/**
			 * Tab 切回：启用手势拦截（不做 show/hide，避免原生层重合成引起的闪屏）
			 */
			showGestureOverlay() {
				if (!this.gestureView) {
					this.createGestureView()
					return
				}
				try {
					this.gestureView.interceptTouchEvent(true)
				} catch (e) {
					console.warn('[dy-player-app] showGestureOverlay error:', e)
				}
			},

			/**
			 * Tab 切走：放行触摸事件（不做 hide，不触发原生帧合成），其它 tab 可以正常响应触摸
			 */
			hideGestureOverlay() {
				if (!this.gestureView) return
				try {
					this.gestureView.interceptTouchEvent(false)
				} catch (e) {
					console.warn('[dy-player-app] hideGestureOverlay error:', e)
				}
			},

			/**
			 * 选集面板 / 解锁弹窗打开时真正 hide（需要让出 scroll 层）
			 */
			hideGestureOverlayFull() {
				if (!this.gestureView) return
				try {
					this.gestureView.interceptTouchEvent(false)
					this.gestureView.hide()
				} catch (e) {}
			},

			showGestureOverlayFull() {
				if (!this.gestureView) {
					this.createGestureView()
					return
				}
				try {
					this.gestureView.show()
					this.gestureView.interceptTouchEvent(true)
				} catch (e) {}
			},
			// #endif

			appendVideos(list) {
				if (!list || list.length === 0) return
				const startIdx = this.totalList.length
				const normalized = list.map((item, idx) => this.normalizeVideoItem(item, startIdx + idx, {
					isDrama: this.isDrama
				}))
				this.totalList = [...this.totalList, ...normalized]
			},

			refreshList(list, keepIndex = true) {
				const oldIndex = this.currentIndex
				this.pauseCurrent()
				this.totalList = list.map((item, idx) => this.normalizeVideoItem(item, idx, {
					isDrama: this.isDrama
				}))
				if (!keepIndex) this.currentIndex = 0
				else if (oldIndex >= this.totalList.length) this.currentIndex = this.totalList.length - 1
				else this.currentIndex = oldIndex
				this.$nextTick(() => {
					this.playCurrent()
				})
			},

			// ================== 播放控制 ==================
			playCurrent() {
				const item = this.currentItem
				if (!item) return

				// 短剧未解锁
				if (this.isDrama && !item.videoUrl) {
					this.lockedItem = item
					this.showUnlockPopup = true
					this.pendingPlay = false
					this.clearMetadataTimer()
					return
				}

				if (!item.videoUrl) {
					console.warn('[dy-player-app] playCurrent no videoUrl')
					this.pendingPlay = false
					this.clearMetadataTimer()
					return
				}

				item.pauseShow = false
				item.loadingShow = true
				this.isPlaying = true
				this.pendingPlay = true
				this.setMetadataTimer()

				const ctx = this.getVideoContext('appVideo')
				if (ctx) {
					this.safePlay('appVideo', this.rateList[this.rateIndex])
				}

				// 每次播放/切视频后把原生手势浮层置顶，防止原生 video 重建后盖在上面
				this.showGestureOverlay()
			},

			pauseCurrent() {
				this.isPlaying = false
				this.pendingPlay = false
				this.clearMetadataTimer()
				const item = this.currentItem
				if (item) item.loadingShow = false
				this.safePause('appVideo')
			},

			play() {
				const item = this.currentItem
				if (item) item.pauseShow = false
				this.playCurrent()
			},

			/**
			 * 轻量恢复播放：仅调用 ctx.play()，不重置 loadingShow / pendingPlay 等视觉态。
			 * 用于 Tab 切换回来这种"其实一直在播"的场景，避免 playCurrent 触发的黑闪/loading 闪。
			 */
			resume() {
				const item = this.currentItem
				if (!item || !item.videoUrl) return
				item.pauseShow = false
				this.isPlaying = true
				const ctx = this.getVideoContext('appVideo')
				if (ctx) {
					try { ctx.play() } catch (e) {}
				}
				// #ifdef APP-PLUS
				this.showGestureOverlay()
				// #endif
			},

			pause() {
				this.pauseCurrent()
			},

			/**
			 * 按视频 id 更新 totalList 里对应项的字段。
			 * 用于外部懒加载点赞/收藏状态后回填播放器 UI。
			 */
			updateVideoInfo(id, patch) {
				if (id == null || !patch) return
				const idx = this.totalList.findIndex(v => v && (v.id === id || v.videoId === id))
				if (idx < 0) return
				this.$set(this.totalList, idx, { ...this.totalList[idx], ...patch })
			},

			// ================== Video 事件 ==================
			onPlay() {
				this.isPlaying = true
				const item = this.currentItem
				if (item) {
					item.loadingShow = false
					item.loadError = false
					item.coverOpacity = false
				}
			},

			onPause() {
				this.isPlaying = false
			},

			onWaiting() {
				const item = this.currentItem
				if (item) item.loadingShow = true
			},

			onVideoError(e) {
				console.error('[dy-player-app] onVideoError:', e.detail)
				const item = this.currentItem
				if (!item) return
				item.loadingShow = false
				// 尝试重试
				if (this.reloadVideo('appVideo', item)) {
					item.loadingShow = true
				} else {
					item.loadError = true
					uni.showToast({ title: '视频播放失败', icon: 'none' })
				}
			},

			onErrorTap() {
				const item = this.currentItem
				if (!item) return
				item.loadError = false
				item.loadRetryCount = 0
				this.playCurrent()
			},

			onTimeUpdate(e) {
				const item = this.currentItem
				const detail = e.detail || {}

				this.currentTime = detail.currentTime || 0
				this.videoTime = detail.duration || 0
				if (item) {
					item.loadingShow = false
					if (this.currentTime > 0.1) {
						item.coverOpacity = false
					}
				}

				// 节流：原生 timeupdate 每帧触发，上层仅用于红包计时（秒级），
				// 降低触发频率可减少播放期间 JS 主线程抖动
				const now = Date.now()
				if (now - (this._lastTimeupdateEmit || 0) < 500) return
				this._lastTimeupdateEmit = now

				this.$emit('timeupdate', {
					currentTime: this.currentTime,
					duration: this.videoTime,
					index: this.currentIndex
				})
			},

			onEnded() {
				this.$emit('ended', {
					index: this.currentIndex,
					item: this.currentItem
				})
				if (this.loopPlay) {
					this.playCurrent()
				} else if (this.autoNext && this.currentIndex < this.totalList.length - 1) {
					this.goToIndex(this.currentIndex + 1)
				}
			},

			onLoadedMetadata(e) {
				this.clearMetadataTimer()
				if (this.pendingPlay) {
					this.pendingPlay = false
					this.safePlay('appVideo', this.rateList[this.rateIndex])
				}
				// 选集面板关闭后，视频重挂载，恢复到打开面板时的播放进度
				if (this.episodePanelSavedTime > 0) {
					const ctx = this.getVideoContext('appVideo')
					if (ctx && ctx.seek) {
						ctx.seek(this.episodePanelSavedTime)
					}
					this.episodePanelSavedTime = 0
				}
			},

			setMetadataTimer() {
				this.clearMetadataTimer()
				this.metadataTimer = setTimeout(() => {
					if (this.pendingPlay) {
						this.pendingPlay = false
						this.safePlay('appVideo', this.rateList[this.rateIndex])
					}
				}, 2000)
			},

			clearMetadataTimer() {
				if (this.metadataTimer) {
					clearTimeout(this.metadataTimer)
					this.metadataTimer = null
				}
			},

			// ================== 手势/点击/长按 ==================
			getTouch(e, phase = 'end') {
				// move 阶段优先用 touches，end/cancel 用 changedTouches
				if (phase === 'move' && e.touches && e.touches[0]) return e.touches[0]
				return (e.changedTouches && e.changedTouches[0]) ||
					(e.touches && e.touches[0]) ||
					null
			},

			onTouchStart(e) {
				if (this.isSwitching) return
				const touch = this.getTouch(e, 'start')
				if (!touch) return
				this.touchStartX = touch.clientX
				this.touchStartY = touch.clientY
				this.touchStartTime = Date.now()
				this.touchDirection = ''
				this.hasMoved = false
				this.touchHasSwiped = false
				this.seekStartTime = this.currentTime || 0
				this.sliderTime = this.currentTime || 0
				this.isSeeking = false
			},

			onTouchMove(e) {
				if (this.isSwitching) return
				const touch = this.getTouch(e, 'move')
				if (!touch) return
				const dx = touch.clientX - this.touchStartX
				const dy = touch.clientY - this.touchStartY
				const adx = Math.abs(dx)
				const ady = Math.abs(dy)

				// 方向锁定：纵向 = 切换视频，横向 = 拖动进度条
				if (!this.touchDirection && (adx > 5 || ady > 5)) {
					this.touchDirection = ady > adx * 1.2 ? 'v' : 'h'
					this.hasMoved = true
				}

				// 横向拖动 -> seek
				if (this.touchDirection === 'h' && this.videoTime > 0) {
					this.isSeeking = true
					this.showProgressBar = true
					if (this.hideProgressTimer) {
						clearTimeout(this.hideProgressTimer)
						this.hideProgressTimer = null
					}
					const w = this.screenWidth || 375
					this.sliderTime = Math.min(this.videoTime, Math.max(0, this.seekStartTime + (dx / w) * this.videoTime))
				}

				// 快速滑动时提前触发切换
				if (this.touchDirection === 'v') {
					const dt = Date.now() - this.touchStartTime
					const velocity = dt > 0 ? ady / dt : 0
					if (ady > 60 || (ady > 40 && velocity > 0.8)) {
						this.touchHasSwiped = true
						this.handleSwipeSwitch(dy)
					}
				}
			},

			onTouchEnd(e) {
				if (this.isSwitching) return
				const touch = this.getTouch(e, 'end')
				if (!touch) return
				const dx = touch.clientX - this.touchStartX
				const dy = touch.clientY - this.touchStartY
				const dt = Date.now() - this.touchStartTime
				const adx = Math.abs(dx)
				const ady = Math.abs(dy)

				// 横滑 seek 完成
				if (this.isSeeking) {
					this.finishSeek()
					return
				}

				// 纵向滑动切换：优先使用已锁定的方向；
				// 若 touchmove 未成功设置方向（部分机型事件丢失），用总位移/斜率兜底
				const isVertical = this.touchDirection === 'v' ||
					(this.touchDirection === '' && ady > adx * 1.2 && ady > 15)

				if (isVertical) {
					const velocity = dt > 0 ? ady / dt : 0
					if (ady > 40 || (ady > 20 && velocity > 0.4)) {
						this.touchHasSwiped = true
						this.handleSwipeSwitch(dy)
					}
				}
			},

			finishSeek() {
				const target = this.sliderTime
				const ctx = this.getVideoContext('appVideo')
				if (ctx && ctx.seek) {
					try { ctx.seek(target) } catch (err) {}
				}
				this.currentTime = target
				if (this.hideProgressTimer) clearTimeout(this.hideProgressTimer)
				this.hideProgressTimer = setTimeout(() => {
					this.showProgressBar = false
					this.isSeeking = false
					this.hideProgressTimer = null
				}, 300)
			},

			handleSwipeSwitch(dy) {
				if (this.isSwitching) return
				if (dy > 0 && this.currentIndex > 0) {
					this.goToIndex(this.currentIndex - 1)
				} else if (dy < 0 && this.currentIndex < this.totalList.length - 1) {
					this.goToIndex(this.currentIndex + 1)
				} else if (dy > 50 && this.currentIndex === 0) {
					this.isSwitching = true
					this.$emit('refresh')
					this.lockSwitch(300)
				}
			},

			onTouchCancel() {
				this.touchDirection = ''
				this.hasMoved = false
				if (this.isSeeking) {
					this.finishSeek()
				}
			},

			// 阻止 UI 元素上的触摸事件冒泡到全屏手势层
			onStop() {},

			onGestureTap(e) {
				if (this.touchHasSwiped) return
				const now = Date.now()
				const diff = now - this.lastTapTime
				this.lastTapTime = now

				if (diff < 300) {
					// 双击
					this.clearTapTimer()
					this.addHeart(e)
					this.triggerLike()
				} else {
					// 单击，延迟执行以等待是否双击
					this.lastTapTimer = setTimeout(() => {
						this.togglePlay()
					}, 300)
				}
			},

			clearTapTimer() {
				if (this.lastTapTimer) {
					clearTimeout(this.lastTapTimer)
					this.lastTapTimer = null
				}
			},

			onPauseTap() {
				this.togglePlay()
			},

			togglePlay() {
				if (this.isPlaying) {
					const item = this.currentItem
					if (item) item.pauseShow = true
					this.pauseCurrent()
				} else {
					this.playCurrent()
				}
			},

			onLongPress() {
				this.$emit('longpress', { ...this.currentItem })
			},

			triggerLike() {
				const item = this.currentItem
				if (!item || item.likeShow) return
				item.likeShow = true
				item.likeCount = (Number(item.likeCount) || 0) + 1
				this.likeeffectIndex = this.currentIndex
				setTimeout(() => {
					this.likeeffectIndex = -1
				}, 500)
				this.$emit('like', { ...item })
			},

			addHeart(e) {
				if (!e) return
				let clientX = 0
				let clientY = 0
				if (e.changedTouches && e.changedTouches[0]) {
					clientX = e.changedTouches[0].clientX
					clientY = e.changedTouches[0].clientY
				} else if (e.detail && e.detail.x !== undefined && e.detail.y !== undefined) {
					clientX = e.detail.x
					clientY = e.detail.y
				} else if (e.clientX !== undefined && e.clientY !== undefined) {
					clientX = e.clientX
					clientY = e.clientY
				} else {
					return
				}
				const id = this.heartId++
				const rotates = ['0deg', '15deg', '-15deg']
				const heart = {
					id,
					width: this.pxToRpx(95) + 'rpx',
					height: this.pxToRpx(95) + 'rpx',
					top: this.pxToRpx(clientY) - this.pxToRpx(140) + 'rpx',
					left: this.pxToRpx(clientX) - this.pxToRpx(47.5) + 'rpx',
					rotate: rotates[Math.floor(Math.random() * 3)],
					className: ''
				}
				this.heartList.push(heart)
				setTimeout(() => {
					heart.className = 'app-heart-out'
				}, 100)
				setTimeout(() => {
					this.heartList = this.heartList.filter(h => h.id !== id)
				}, 600)
			},

			// ================== 菜单事件 ==================
			onRateClick() {
				this.rateIndex = (this.rateIndex + 1) % this.rateList.length
				const ctx = this.getVideoContext('appVideo')
				if (ctx) {
					this.safePlaybackRate(ctx, this.rateList[this.rateIndex])
				}
			},

			onMenuAction(type) {
				const item = this.currentItem
				if (!item) return

				switch (type) {
					case 'like': {
						const obj = { ...item }
						obj.likeShow = !obj.likeShow
						obj.likeCount = Number(obj.likeCount) || 0
						if (obj.likeShow) obj.likeCount++
						else obj.likeCount = Math.max(0, obj.likeCount - 1)
						this.updateCurrentItem(obj)
						this.likeeffectIndex = this.currentIndex
						setTimeout(() => {
							this.likeeffectIndex = -1
						}, 500)
						this.$emit('like', { ...obj })
						break
					}
					case 'collect': {
						const obj = { ...item }
						obj.collectShow = !obj.collectShow
						obj.collectCount = Number(obj.collectCount) || 0
						if (obj.collectShow) obj.collectCount++
						else obj.collectCount = Math.max(0, obj.collectCount - 1)
						this.updateCurrentItem(obj)
						this.collecteffectIndex = this.currentIndex
						setTimeout(() => {
							this.collecteffectIndex = -1
						}, 500)
						this.$emit('collect', { ...obj })
						break
					}
					case 'share':
						this.$emit('share', { ...item })
						break
					case 'report':
						this.showReportPopup = true
						break
					case 'episode':
						this.showEpisodePanel = true
						break
					case 'seriesDetail':
						this.$emit('seriesDetail', { ...item })
						break
				}
			},

			/**
			 * 提交举报
			 */
			onReportSubmit({ reason, remark }) {
				const item = this.currentItem
				if (!item) return
				this.showReportPopup = false
				this.$emit('report', {
					item: { ...item },
					reason,
					remark
				})
			},

			updateCurrentItem(obj) {
				const idx = obj.videoIndex
				if (idx >= 0 && idx < this.totalList.length) {
					this.$set(this.totalList, idx, { ...this.totalList[idx], ...obj })
				}
			},

			// ================== 选集/跳转 ==================
			onEpisodeSelect(index) {
				const target = this.totalList[index]
				const isLocked = target && !target.videoUrl
				if (!isLocked) {
					// 切到可播放的新集数，不需要恢复旧进度
					this.episodePanelSavedTime = 0
				}
				this.showEpisodePanel = false
				this.goToIndex(index)
			},

			/**
			 * 关闭选集面板：重新挂载视频并恢复上次播放进度
			 */
			closeEpisodePanel() {
				this.showEpisodePanel = false
				this.$nextTick(() => {
					this.playCurrent()
				})
			},

			goNext() {
				if (this.currentIndex < this.totalList.length - 1) {
					this.goToIndex(this.currentIndex + 1)
				}
			},

			goToIndex(index) {
				if (index < 0 || index >= this.totalList.length) return
				const target = this.totalList[index]
				if (target && !target.videoUrl) {
					this.$emit('lockHit', { index, item: target })
					uni.showToast({ title: '看广告解锁后续剧集', icon: 'none', duration: 1500 })
					return
				}
				if (this.isSwitching) return

				this.isSwitching = true
				this.lockSwitch(250)

				// 切走前：暂停当前、重置状态
				this.pauseCurrent()
				const oldItem = this.currentItem
				if (oldItem) {
					oldItem.coverOpacity = oldItem.coverShow !== false
					oldItem.pauseShow = false
					oldItem.loadError = false
				}

				this.currentIndex = index

				// 新视频
				const newItem = this.currentItem
				if (newItem) {
					// 切换时先显示封面作为加载占位，避免黑屏，等真正开始播放再隐藏封面
					newItem.coverOpacity = true
					newItem.pauseShow = false
					newItem.loadingShow = true
					newItem.loadError = false
				}

				// 等待 DOM 更新后播放（playCurrent 会处理未解锁弹窗）
				this.$nextTick(() => {
					this.playCurrent()
				})
			},

			lockSwitch(ms) {
				// 只清旧定时器，不要重置 isSwitching，否则 goToIndex 设置的锁会失效
				if (this.switchLockTimer) {
					clearTimeout(this.switchLockTimer)
					this.switchLockTimer = null
				}
				this.switchLockTimer = setTimeout(() => {
					this.isSwitching = false
					this.switchLockTimer = null
				}, ms)
			},

			clearSwitchLock() {
				if (this.switchLockTimer) {
					clearTimeout(this.switchLockTimer)
					this.switchLockTimer = null
				}
				this.isSwitching = false
			},

			// ================== 解锁 ==================
			onUnlockAction(type) {
				this.$emit('unlock', {
					type,
					item: this.lockedItem || this.currentItem,
					callback: (rewardNum) => {
						if (!rewardNum) {
							this.showUnlockPopup = false
							this.playCurrent()
						}
					}
				})
			},

			// ================== 红包 ==================
			resetRedPacket(ms) {
				this.clearRedPacketTimer()
				const state = {
					times: ms,
					remainMs: ms > 0 ? ms : 0,
					isEnd: ms <= 0
				}
				this.redPacketState = state
				if (ms > 0) {
					this.redPacketTimer = setInterval(() => {
						state.remainMs -= 100
						if (state.remainMs <= 0) {
							state.remainMs = 0
							state.isEnd = true
							this.clearRedPacketTimer()
							this.$emit('redPacketEnd')
						}
						this.redPacketState = { ...state }
					}, 100)
				}
			},

			clearRedPacketTimer() {
				if (this.redPacketTimer) {
					clearInterval(this.redPacketTimer)
					this.redPacketTimer = null
				}
			},

			onRedPacketTap() {
				if (this.redPacketState.isEnd) {
					this.$emit('redPacketReceive')
				}
			},

			// ================== 返回/加载更多 ==================
			onBack() {
				this.$emit('back')
			},

			onSearchClick() {
				this.$emit('search')
			},

			checkAutoLoadMore() {
				if (this.currentIndex >= this.totalList.length - 2) {
					this.$emit('loadMore')
				}
			},

			// ================== 任务计时 ==================
			startTask() {
				const data = this.currentItem
				if (!data) return

				if (this.playInfoIndex !== -1 && this.taskTime >= 10) {
					this.$emit('taskTime', {
						index: this.playInfoIndex,
						time: this.taskTime,
						item: this.totalList[this.playInfoIndex]
					})
				}

				this.clearTaskTimer()
				this.taskTime = 0
				this.playInfoIndex = this.currentIndex

				if (data.videoUrl) {
					this.taskTimer = setInterval(() => {
						this.taskTime++
					}, 1000)
				}
			},

			clearTaskTimer() {
				if (this.taskTimer) {
					clearInterval(this.taskTimer)
					this.taskTimer = null
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.dy-player-app {
		position: relative;
		width: 100%;
		height: 100%;
		overflow: hidden;
		background-color: #000000;
	}

	.dy-video {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 1;
	}

	/* 1. 封面层 */
	.app-cover {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 5;
		background-color: #000;
	}

	.app-cover-img {
		width: 100%;
		height: 100%;
	}

	/* 2. 加载中 */
	.app-loading {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 6;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.app-loading-text {
		font-size: 28rpx;
		color: #ffffff;
	}

	/* 3. 暂停按钮 */
	.app-pause {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 8;
		display: flex;
		align-items: center;
		justify-content: center;
		background-color: rgba(0, 0, 0, 0.1);
	}

	.app-pause-img {
		width: 134rpx;
		height: 134rpx;
		opacity: 0.5;
	}

	/* 4. 加载失败 */
	.app-error {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 8;
		display: flex;
		align-items: center;
		justify-content: center;
		background-color: rgba(0, 0, 0, 0.5);
	}

	.app-error-text {
		font-size: 28rpx;
		color: #ffffff;
		padding: 20rpx 40rpx;
		background-color: rgba(0, 0, 0, 0.6);
		border-radius: 40rpx;
	}

	/* 5. 底部信息 */
	.app-bottom-info {
		position: absolute;
		left: 0;
		bottom: 120rpx;
		width: 560rpx;
		padding: 0 30rpx;
		z-index: 6;
	}

	.app-info-name {
		font-size: 34rpx;
		font-weight: bold;
		color: #ffffff;
		text-shadow: 0 2rpx 6rpx rgba(0, 0, 0, 0.4);
		margin-bottom: 10rpx;
	}

	.app-info-content {
		font-size: 28rpx;
		color: #ffffff;
		text-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.4);
		line-height: 1.4;
		display: -webkit-box;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical;
		overflow: hidden;
	}

	/* 5.1 横滑进度条 - 拖动中央时间 */
	.app-progress-drag-time {
		position: absolute;
		top: 40%;
		left: 0;
		right: 0;
		z-index: 18;
		display: flex;
		align-items: center;
		justify-content: center;
		text-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.6);
	}

	.app-progress-drag-current {
		font-size: 64rpx;
		color: #ffffff;
		font-weight: bold;
	}

	.app-progress-drag-total {
		font-size: 32rpx;
		color: rgba(255, 255, 255, 0.7);
	}

	/* 5.2 横滑进度条 - 底部条 */
	.app-progress-bar {
		position: absolute;
		left: 30rpx;
		right: 30rpx;
		bottom: 60rpx;
		height: 6rpx;
		z-index: 18;
	}

	.app-progress-track {
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		height: 6rpx;
		background-color: rgba(255, 255, 255, 0.3);
		border-radius: 3rpx;
	}

	.app-progress-fill {
		position: absolute;
		left: 0;
		top: 0;
		height: 6rpx;
		background-color: #ffffff;
		border-radius: 3rpx;
	}

	/* 6. 全屏覆盖层容器 */
	.app-overlay {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 10;
	}

	/* 6.1 手势背景层：单独一个 cover-view 负责接收滑动/点击/长按 */
	.app-gesture-bg {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 1;
		/* 透明背景在某些真机可能不收事件，加一点透明度兜底 */
		background-color: rgba(0, 0, 0, 0.02);
		pointer-events: auto;
		touch-action: none;
	}

	/* 7. 顶部栏 */
	.app-top-bar {
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		z-index: 15;
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 0 30rpx;
	}

	.app-back {
		width: 45rpx;
		height: 45rpx;
	}

	.app-search-btn {
		display: flex;
		align-items: center;
		padding: 16rpx 28rpx;
		background-color: rgba(0, 0, 0, 0.4);
		border-radius: 40rpx;
	}

	.app-search-btn cover-image {
		width: 44rpx;
		height: 44rpx;
		margin-right: 12rpx;
	}

	.app-search-btn cover-view {
		font-size: 30rpx;
		color: #ffffff;
	}

	.app-episode-tag {
		font-size: 30rpx;
		color: #ffffff;
		text-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.3);
	}

	/* 8. 右侧菜单 */
	.app-menu {
		position: absolute;
		right: 20rpx;
		bottom: 220rpx;
		z-index: 20;
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.app-menu-item {
		position: relative;
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-bottom: 36rpx;
	}

	.app-menu-icon {
		width: 84rpx;
		height: 84rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.app-menu-icon-active {
		animation: likeBlink 0.5s ease-in-out;
	}

	.app-menu-icon-text {
		font-size: 44rpx;
	}

	.app-menu-img {
		width: 70rpx;
		height: 70rpx;
	}

	.app-menu-text {
		margin-top: 6rpx;
		font-size: 24rpx;
		color: #ffffff;
		text-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.3);
	}

	.app-menu-hit {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 1;
		background-color: rgba(0, 0, 0, 0.001);
	}

	/* 9. 红包 */
	.app-red-packet {
		position: absolute;
		top: 12vh;
		right: 20rpx;
		z-index: 25;
		width: 120rpx;
		height: 120rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.app-red-packet-img {
		width: 120rpx;
		height: 120rpx;
	}

	.app-red-packet-text,
	.app-red-packet-time {
		position: absolute;
		left: 0;
		bottom: 15rpx;
		width: 100%;
		text-align: center;
		font-size: 22rpx;
		color: #fff;
	}

	/* 10. 双击点赞桃心 */
	.app-heart {
		position: absolute;
		pointer-events: none;
		z-index: 30;
		animation: heartIn 0.3s ease forwards;
	}

	.app-heart-out {
		animation: heartOut 0.4s ease forwards;
	}

	@keyframes heartIn {
		0% {
			transform: scale(0);
			opacity: 0;
		}

		100% {
			transform: scale(1);
			opacity: 1;
		}
	}

	@keyframes heartOut {
		0% {
			transform: scale(1) translateY(0);
			opacity: 1;
		}

		100% {
			transform: scale(1.5) translateY(-80rpx);
			opacity: 0;
		}
	}

	@keyframes likeBlink {
		0% {
			transform: scale(1);
		}

		50% {
			transform: scale(1.3);
		}

		100% {
			transform: scale(1);
		}
	}

	/* 11. 选集面板 */
	.app-episode-mask {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 500;
		background-color: rgba(0, 0, 0, 0.4);
	}

	.app-episode {
		position: absolute;
		left: 0;
		right: 0;
		bottom: 0;
		height: 70vh;
		background-color: #ffffff;
		border-radius: 40rpx 40rpx 0 0;
		padding: 30rpx;
		padding-bottom: calc(30rpx + constant(safe-area-inset-bottom));
		padding-bottom: calc(30rpx + env(safe-area-inset-bottom));
		display: flex;
		flex-direction: column;
	}

	.app-episode-header {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-bottom: 20rpx;
	}

	.app-episode-title {
		font-size: 32rpx;
		font-weight: bold;
		color: #333;
	}

	.app-episode-close {
		font-size: 36rpx;
		color: #999;
		padding: 10rpx;
	}

	.app-episode-count {
		font-size: 24rpx;
		color: #999;
		margin-bottom: 20rpx;
	}

	.app-episode-scroll {
		flex: 1;
		/* 允许 flex 子项收缩到内容以下，保证集数多时在面板内部滚动而不是溢出 */
		min-height: 0;
	}

	.app-episode-grid {
		display: flex;
		flex-wrap: wrap;
	}

	.app-episode-item {
		width: 100rpx;
		height: 100rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		margin: 0 20rpx 20rpx 0;
		background-color: #f5f5f5;
		border-radius: 12rpx;
		font-size: 28rpx;
		color: #333;
	}

	.app-episode-current {
		background-color: #FE6100;
		color: #fff;
	}

	.app-episode-locked {
		background-color: #e0e0e0;
	}

	.app-episode-lock {
		width: 40rpx;
		height: 40rpx;
	}

	/* 12. 解锁弹窗 */
	.app-unlock-mask {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 500;
		background-color: rgba(0, 0, 0, 0.5);
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.app-unlock {
		width: 560rpx;
		background-color: #ffffff;
		border-radius: 24rpx;
		padding: 40rpx;
		text-align: center;
	}

	.app-unlock-title {
		font-size: 34rpx;
		font-weight: bold;
		color: #333;
		margin-bottom: 16rpx;
	}

	.app-unlock-desc {
		font-size: 28rpx;
		color: #999;
		margin-bottom: 40rpx;
	}

	.app-unlock-btn {
		width: 100%;
		height: 84rpx;
		line-height: 84rpx;
		background-color: #FE6100;
		color: #fff;
		font-size: 30rpx;
		border-radius: 42rpx;
		margin-bottom: 24rpx;
	}

	.app-unlock-btn-ad {
		background-color: #FFD700;
		color: #333;
	}

	.app-unlock-close {
		font-size: 28rpx;
		color: #999;
		padding: 10rpx;
	}

</style>
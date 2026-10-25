<template>
	<view class="dy-player" :style="playerStyle">
		<swiper class="dy-swiper" vertical :current="swiperIndex" :circular="circular" :duration="300"
			@change="onSwiperChange" @animationfinish="onSwiperAnimationFinish">
			<swiper-item v-for="(item, index) in displayList" :key="`dy-slot-${index}`" class="dy-swiper-item">
				<view class="dy-video-wrap">
					<video :id="`dyVideo${index}`" :ref="`dyVideo${index}`" class="dy-video"
						:style="{ width: playerStyle.width, height: playerStyle.height }" :src="item.videoUrl"
						:poster="item.titleImg" :object-fit="item.objectFit || 'cover'" :loop="loopPlay"
						:muted="index !== swiperIndex || muted || !item.vodPaly" :controls="false"
						show-center-play-btn="false" show-play-btn="false" show-fullscreen-btn="false"
						show-loading="false" :enable-progress-gesture="false" :http-cache="true" preload="auto"
						@play="onPlay(index, $event)" @pause="onPause(index, $event)"
						@waiting="onWaiting(index, $event)" @error="onVideoError(index, $event)"
						@timeupdate="onTimeUpdate(index, $event)" @ended="onEnded(index, $event)"
						@loadedmetadata="onLoadedMetadata(index, $event)">
					</video>

					<!-- 封面层 -->
					<view v-if="item.coverOpacity" class="dy-cover" @click="onVideoTap(index, $event)">
						<image :src="item.titleImg" mode="aspectFill" class="dy-cover-img" />
					</view>

					<!-- 暂停按钮 -->
					<view v-if="item.pauseShow" class="dy-pause" @click="onVideoTap(index, $event)">
						<image class="dy-pause-img" src="/static/icon/vod-play.png" mode="aspectFit" />
					</view>

					<!-- 加载中 -->
					<view v-if="item.loadingShow" class="dy-loading">
						<uv-loading-icon color="#fff" text="加载中" size="36" :vertical="true" />
					</view>

					<view v-if="item.loadError" class="dy-error" @click="onVideoErrorTap(index, $event)">
						<text class="dy-error-text">视频加载失败，点击重试</text>
					</view>

					<!-- 顶层触摸层 - 避开底部导航栏区域 -->
					<view class="dy-touch-layer" @click="onVideoTap(index, $event)"
						@longpress="onLongPress(item)" @touchstart="onTouchStart"
						@touchmove="onTouchMove" @touchend="onTouchEnd"
						@touchcancel="onTouchCancel"></view>

					<!-- 横滑 seek 进度条（仅拖动时显示） -->
					<view v-show="showProgressBar && index === swiperIndex" class="dy-progress-drag-time">
						<text class="dy-progress-drag-current">{{ formatSeconds(sliderTime) }}</text>
						<text class="dy-progress-drag-total"> / {{ formatSeconds(videoTime) }}</text>
					</view>
					<view v-show="showProgressBar && index === swiperIndex" class="dy-progress-bar">
						<view class="dy-progress-track"></view>
						<view class="dy-progress-fill" :style="{ width: progressPercent + '%' }"></view>
					</view>

					<!-- 顶部信息 -->
					<view class="dy-top-bar" :style="{ paddingTop: statusBarHeight + 'px' }">
						<image v-if="showBack" class="dy-back" src="/static/icon/black2.png" mode="aspectFit" @click="onBack" />
						<view v-if="showSearch" class="dy-search-btn" @click="onSearchClick">
							<image src="/static/icon/black2.png" mode="aspectFit" />
							<text>前去搜索</text>
						</view>
						<text v-if="isDrama" class="dy-episode-tag">第 {{ item.gather }} 集</text>
					</view>

					<!-- 底部信息 -->
					<view class="dy-bottom-info">
						<view class="dy-info-main">
							<text class="dy-info-name">{{ item.name }}</text>
							<text class="dy-info-content">{{ item.content }}</text>
						</view>
					</view>

					<!-- 右侧菜单 -->
					<dy-menu class="dy-menu-layer" :item="item" :is-drama="isDrama" :show-share="showShare" :show-report="showReport" @action="onMenuAction($event)" />

					<!-- 红包 -->
					<dy-red-packet v-if="showRedPacket && index === swiperIndex" class="dy-red-packet-layer"
						:times="redPacketTimes" @end="onRedPacketEnd" @receive="onRedPacketReceive" />

					<!-- 双击点赞桃心 -->
					<view v-for="(heart, hIdx) in heartList" :key="heart.id" class="dy-heart"
						:style="{ top: heart.top, left: heart.left, width: heart.width, height: heart.height, transform: 'rotate(' + heart.rotate + ')' }"
						:class="heart.className">
						<image src="/static/icon/selectTaoxin.png" :style="{ width: heart.width, height: heart.height }" />
					</view>
				</view>
			</swiper-item>
		</swiper>

		<!-- 选集面板 -->
		<dy-episode-panel :visible="showEpisodePanel" :info="info" :list="totalList" :current-index="currentIndex"
			@select="onEpisodeSelect" @close="showEpisodePanel = false" />

		<!-- 解锁弹窗 -->
		<dy-unlock-popup :visible="showUnlockPopup" :reward-num="unlockRewardNum" :unlock-count="unlockAdCount"
			:gold-cost="unlockGoldCost" :show-gold="showGoldUnlock" @unlock="onUnlockAction" @close="showUnlockPopup = false" />

		<!-- 倍速选择 -->
		<view v-if="showRateBox" class="dy-rate-box" @click="showRateBox = false">
			<view class="dy-rate-panel" @click.stop>
				<view class="dy-rate-title">
					<uni-icons type="down" size="22" @click="showRateBox = false" />
					<text>倍速</text>
				</view>
				<view v-for="(rate, idx) in rateList" :key="idx" class="dy-rate-item" @click="onRateSelect(idx)">
					<text :class="{ 'dy-rate-active': rateIndex === idx }">{{ rate }}x</text>
					<image v-if="rateIndex === idx" src="/static/juhe/8.png" mode="aspectFit" />
				</view>
			</view>
		</view>

		<!-- 举报弹窗 -->
		<dy-report-popup :visible="showReportPopup" @cancel="showReportPopup = false" @submit="onReportSubmit" />
	</view>
</template>

<script>
	import dyPlayerLogic from './dy-player-logic.js'
	import dyMenu from './dy-player-menu.vue'
	import dyRedPacket from './dy-red-packet.vue'
	import dyEpisodePanel from './dy-episode-panel.vue'
	import dyUnlockPopup from './dy-unlock-popup.vue'
	import dyReportPopup from './dy-report-popup.vue'

	export default {
		components: {
			dyMenu,
			dyRedPacket,
			dyEpisodePanel,
			dyUnlockPopup,
			dyReportPopup
		},
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
			}
		},
		data() {
			return {
				totalList: [],
				displayList: [],
				currentIndex: 0,
				swiperIndex: 0,
				circular: false,
				muted: false,
				isPlaying: false,
				currentTime: 0,
				videoTime: 0,
				// 横滑 seek 相关
				showProgressBar: false,
				isSeeking: false,
				sliderTime: 0,
				seekStartTime: 0,
				touchStartX: 0,
				touchDirection: '',
				hideProgressTimer: null,
				showEpisodePanel: false,
				showUnlockPopup: false,
				showReportPopup: false,
				unlockRewardNum: '',
				showRateBox: false,
				rateList: ['2.0', '1.0', '0.5'],
				rateIndex: 1,
				// 双击点赞
				lastTapTime: 0,
				lastTapTimer: null,
				heartList: [],
				heartId: 1,
				// 触摸
				touchStartY: 0,
				isTouching: false,
				// 任务计时
				taskTimer: null,
				taskTime: 0,
				playInfoIndex: -1,
				// 当前锁定项
				lockedItem: null,
				// 待播放槽位（DOM 重建后等 metadata 就绪再播）
				pendingPlaySlot: -1
			}
		},
		computed: {
			currentItem() {
				if (this.totalList.length === 0) return {}
				return this.totalList[this.currentIndex] || {}
			},
			progressPercent() {
				const dur = this.videoTime || 0
				if (dur <= 0) return 0
				const t = this.isSeeking ? this.sliderTime : (this.currentTime || 0)
				return Math.min(100, Math.max(0, (t / dur) * 100))
			}
		},
		beforeDestroy() {
			this.clearTaskTimer()
			if (this.hideProgressTimer) {
				clearTimeout(this.hideProgressTimer)
				this.hideProgressTimer = null
			}
		},
		methods: {
			// ================== 初始化/数据更新 ==================
			initPlayer(list, startIndex = 0) {
				console.log('[dy-player] initPlayer, list.length:', list.length, 'startIndex:', startIndex)
				this.totalList = list.map((item, idx) => this.normalizeVideoItem(item, idx, {
					isDrama: this.isDrama
				}))

				let idx = startIndex || 0
				if (idx < 0) idx = 0
				if (idx >= this.totalList.length) idx = this.totalList.length - 1
				this.currentIndex = idx

				this.syncDisplayList(true)

				if (this.autoPlay) {
					this.$nextTick(() => {
						setTimeout(() => {
							this.playCurrent()
						}, 200)
					})
				}
			},

			/**
			 * 追加视频（用于分页加载），不重置当前播放位置
			 */
			appendVideos(list) {
				if (!list || list.length === 0) return
				const startIdx = this.totalList.length
				const normalized = list.map((item, idx) => this.normalizeVideoItem(item, startIdx + idx, {
					isDrama: this.isDrama
				}))
				this.totalList = [...this.totalList, ...normalized]
				// 如果当前在末尾附近，更新 displayList
				if (this.currentIndex >= this.totalList.length - list.length - 1) {
					this.syncDisplayList()
				}
			},

			/**
			 * 同步 displayList 与 swiperIndex
			 * @param {Boolean} init 是否初始化
			 */
			syncDisplayList(init = false) {
				const total = this.totalList.length
				if (total === 0) {
					this.displayList = []
					return
				}

				if (total < 3) {
					this.displayList = this.totalList.map(item => ({ ...item }))
					this.swiperIndex = this.currentIndex
					this.circular = false
					return
				}

				const current = this.currentIndex
				let display = []
				let swiperIdx = 1
				let circ = true

				if (current === 0) {
					display = [this.totalList[0], this.totalList[1], this.totalList[2]]
					swiperIdx = 0
					circ = false
				} else if (current === total - 1) {
					display = [this.totalList[total - 3], this.totalList[total - 2], this.totalList[total - 1]]
					swiperIdx = 2
					circ = false
				} else {
					display = [this.totalList[current - 1], this.totalList[current], this.totalList[current + 1]]
					swiperIdx = 1
					circ = true
				}

				// 深拷贝避免引用问题
				this.displayList = display.map(item => ({ ...item }))

				// 直接设置，避免先关后开导致的闪烁
				this.swiperIndex = swiperIdx
				this.circular = circ
			},

			// ================== Swiper 事件 ==================
			onSwiperChange(e) {
				const newSwiperIndex = e.detail.current

				// 暂停上一个
				this.pauseVideo(this.swiperIndex)
				this.pendingPlaySlot = -1

				// 更新当前 totalList 索引
				const item = this.displayList[newSwiperIndex]
				if (item && typeof item.videoIndex === 'number') {
					this.currentIndex = item.videoIndex
				}
				this.swiperIndex = newSwiperIndex
				this.pendingPlaySlot = newSwiperIndex

				// 重置进度
				this.currentTime = 0
				this.videoTime = 0

				// 上报浏览记录
				this.startTask()

				this.$emit('change', {
					index: this.currentIndex,
					item: this.currentItem
				})
			},

			onSwiperAnimationFinish(e) {
				const idx = e.detail.current

				// 同步 displayList，使当前项居中
				this.syncDisplayList()

				// 播放当前
				this.$nextTick(() => {
					this.playCurrent()
					this.checkAutoLoadMore()
				})
			},

			// ================== 播放控制 ==================
			playCurrent() {
				if (this.totalList.length === 0) return
				const item = this.displayList[this.swiperIndex]
				if (!item) return

				// 检查是否锁定
				if (this.isDrama && !item.videoUrl) {
					this.lockedItem = item
					this.showUnlockPopup = true
					return
				}

				this.isPlaying = true
				item.vodPaly = true
				item.coverOpacity = false
				item.pauseShow = false
				item.loadingShow = true
				item.loadError = false
				this.muted = false
				this.pendingPlaySlot = this.swiperIndex

				const ctx = this.getVideoContext(`dyVideo${this.swiperIndex}`)
				if (ctx) {
					this.safePlay(`dyVideo${this.swiperIndex}`, this.rateList[this.rateIndex])
				}
			},

			pauseCurrent() {
				this.pauseVideo(this.swiperIndex)
			},

			pauseVideo(slotIndex) {
				const item = this.displayList[slotIndex]
				if (item) {
					item.vodPaly = false
					item.loadingShow = false
				}
				if (this.pendingPlaySlot === slotIndex) {
					this.pendingPlaySlot = -1
				}
				this.safePause(`dyVideo${slotIndex}`)
			},

			play() {
				this.playCurrent()
			},

			/**
			 * 轻量恢复：仅唤醒当前 slot 的视频，不重置视觉态。
			 * 用于 Tab 切换回来的场景，避免 playCurrent 引起的闪烁。
			 */
			resume() {
				const item = this.currentItem
				if (!item || !item.videoUrl) return
				const slotIndex = this.swiperIndex
				this.muted = false
				this.safePlay(`dyVideo${slotIndex}`, this.rateList ? this.rateList[this.rateIndex] : 1)
			},

			pause() {
				this.pauseCurrent()
				this.muted = true
			},

			/**
			 * 按视频 id 更新 totalList / displayList 中对应项的字段。
			 * 供外部懒加载点赞/收藏状态后回填。
			 */
			updateVideoInfo(id, patch) {
				if (id == null || !patch) return
				const totalIdx = this.totalList.findIndex(v => v && (v.id === id || v.videoId === id))
				if (totalIdx >= 0) {
					this.$set(this.totalList, totalIdx, { ...this.totalList[totalIdx], ...patch })
				}
				const displayIdx = this.displayList.findIndex(v => v && (v.id === id || v.videoId === id))
				if (displayIdx >= 0) {
					this.$set(this.displayList, displayIdx, { ...this.displayList[displayIdx], ...patch })
				}
			},

			// ================== Video 事件 ==================
			onPlay(index, e) {
				if (index === this.swiperIndex) {
					this.isPlaying = true
					const item = this.displayList[index]
					if (item) {
						item.vodPaly = true
						item.pauseShow = false
						item.loadingShow = false
					}
				}
			},

			onPause(index, e) {
				if (index === this.swiperIndex) {
					this.isPlaying = false
				}
			},

			onWaiting(index, e) {
				if (index === this.swiperIndex) {
					const item = this.displayList[index]
					if (item) item.loadingShow = true
				}
			},

			onVideoError(index, e) {
				console.error('[dy-player] onVideoError:', index, e.detail)
				if (index === this.swiperIndex) {
					const item = this.displayList[index]
					if (!item) return
					// 尝试重试
					if (this.reloadVideo(`dyVideo${index}`, item)) {
						item.loadingShow = true
					} else {
						item.loadingShow = false
						item.loadError = true
						uni.showToast({ title: '视频播放失败', icon: 'none' })
					}
				}
			},

			onVideoErrorTap(index, e) {
				if (index !== this.swiperIndex) return
				const item = this.displayList[index]
				if (!item) return
				item.loadError = false
				item.loadRetryCount = 0
				this.playCurrent()
			},

			onTimeUpdate(index, e) {
				if (this.swiperIndex != index) return
				const detail = e.detail || {}
				this.currentTime = detail.currentTime || 0
				this.videoTime = detail.duration || 0
				this.$emit('timeupdate', {
					currentTime: this.currentTime,
					duration: this.videoTime,
					index: index
				})

				const item = this.displayList[index]
				if (item) {
					item.loadingShow = false
					// 开始播放后隐藏封面
					if (this.currentTime > 0.1) {
						item.coverOpacity = false
					}
				}
			},

			onEnded(index, e) {
				if (this.swiperIndex != index) return
				this.$emit('ended', {
					index: index,
					item: this.displayList[index] || null
				})
				if (this.loopPlay) {
					this.playCurrent()
				} else if (this.autoNext) {
					// 播放下一个
					if (this.currentIndex < this.totalList.length - 1) {
						this.goToIndex(this.currentIndex + 1)
					}
				}
			},

			onLoadedMetadata(index, e) {
				// DOM 重建后，如果之前已经请求播放但视频还没就绪，在这里补一次播放
				if (index === this.pendingPlaySlot && index === this.swiperIndex) {
					this.pendingPlaySlot = -1
					this.safePlay(`dyVideo${index}`, this.rateList[this.rateIndex])
				}
			},

			// ================== 手势/点击 ==================
			onVideoTap(index, e) {
				if (index !== this.swiperIndex) return

				const now = Date.now()
				const diff = now - this.lastTapTime
				this.lastTapTime = now

				if (diff < 300) {
					// 双击
					clearTimeout(this.lastTapTimer)
					this.addHeart(e)
					this.triggerLike()
				} else {
					// 单击，延迟执行
					this.lastTapTimer = setTimeout(() => {
						this.togglePlay()
					}, 300)
				}
			},

			togglePlay() {
				const item = this.displayList[this.swiperIndex]
				if (!item) return

				if (this.isPlaying) {
					this.pauseCurrent()
					item.pauseShow = true
				} else {
					item.pauseShow = false
					this.playCurrent()
				}
			},

			triggerLike() {
				const item = this.displayList[this.swiperIndex]
				if (!item || item.likeShow) return
				item.likeShow = true
				item.likeCount = (Number(item.likeCount) || 0) + 1
				this.updateTotalItem(item)
				this.$emit('like', { ...item })
			},

			onLongPress(item) {
				this.$emit('longpress', item)
			},

			addHeart(e) {
				const touch = e.changedTouches && e.changedTouches[0]
				if (!touch) return
				const id = this.heartId++
				const rotates = ['0deg', '15deg', '-15deg']
				const heart = {
					id,
					width: this.pxToRpx(95) + 'rpx',
					height: this.pxToRpx(95) + 'rpx',
					top: this.pxToRpx(touch.clientY) - this.pxToRpx(140) + 'rpx',
					left: this.pxToRpx(touch.clientX) - this.pxToRpx(47.5) + 'rpx',
					rotate: rotates[Math.floor(Math.random() * 3)],
					className: ''
				}
				this.heartList.push(heart)
				setTimeout(() => {
					heart.className = 'dy-heart-out'
				}, 100)
				setTimeout(() => {
					this.heartList = this.heartList.filter(h => h.id !== id)
				}, 600)
			},

			// 触摸处理：横滑=seek，纵向下拉=刷新
			onTouchStart(e) {
				const t = e.changedTouches[0]
				this.touchStartX = t.clientX
				this.touchStartY = t.clientY
				this.isTouching = true
				this.touchDirection = ''
				this.seekStartTime = this.currentTime || 0
				this.sliderTime = this.currentTime || 0
				this.isSeeking = false
			},

			onTouchMove(e) {
				if (!this.isTouching) return
				const t = e.changedTouches[0]
				const dx = t.clientX - this.touchStartX
				const dy = t.clientY - this.touchStartY
				const adx = Math.abs(dx)
				const ady = Math.abs(dy)

				if (!this.touchDirection && (adx > 10 || ady > 10)) {
					this.touchDirection = adx > ady * 1.2 ? 'h' : 'v'
				}

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
			},

			onTouchEnd(e) {
				if (!this.isTouching) return
				this.isTouching = false
				const t = e.changedTouches[0]
				const dy = t.clientY - this.touchStartY

				if (this.isSeeking) {
					this.finishSeek()
					this.touchDirection = ''
					return
				}

				if (this.currentIndex === 0 && dy > 100) {
					this.$emit('refresh')
				}
				this.touchDirection = ''
			},

			onTouchCancel() {
				this.isTouching = false
				this.touchDirection = ''
				if (this.isSeeking) {
					this.finishSeek()
				}
			},

			finishSeek() {
				const target = this.sliderTime
				const ctx = this.getVideoContext(`dyVideo${this.swiperIndex}`)
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

			// ================== 菜单/进度条事件 ==================
			onMenuAction(e) {
				const { type, item } = e
				// 同步当前 item 到 totalList
				this.updateTotalItem(item)

				switch (type) {
					case 'like':
						this.$emit('like', item)
						break
					case 'collect':
						this.$emit('collect', item)
						break
					case 'share':
						this.$emit('share', item)
						break
					case 'report':
						this.showReportPopup = true
						break
					case 'episode':
						this.showEpisodePanel = true
						break
					case 'seriesDetail':
						this.$emit('seriesDetail', item)
						break
					case 'rate':
						this.showRateBox = true
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
					item,
					reason,
					remark
				})
			},

			updateTotalItem(item) {
				const idx = item.videoIndex
				if (idx >= 0 && idx < this.totalList.length) {
					this.$set(this.totalList, idx, { ...this.totalList[idx], ...item })
				}
				// 同步 displayList
				this.displayList.forEach((displayItem, i) => {
					if (displayItem.videoIndex === idx) {
						this.$set(this.displayList, i, { ...displayItem, ...item })
					}
				})
			},

			// ================== 选集/跳转 ==================
			onEpisodeSelect(index) {
				this.showEpisodePanel = false
				this.goToIndex(index)
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
				this.currentIndex = index
				this.syncDisplayList()
				this.$nextTick(() => {
					this.playCurrent()
				})
			},

			// ================== 解锁 ==================
			onUnlockAction({ type }) {
				this.$emit('unlock', {
					type,
					item: this.lockedItem,
					callback: (rewardNum) => {
						this.unlockRewardNum = rewardNum || ''
						if (!rewardNum) {
							this.showUnlockPopup = false
							this.playCurrent()
						}
					}
				})
			},

			// 外部调用：解锁成功后刷新列表
			refreshList(list, keepIndex = true) {
				const oldIndex = this.currentIndex
				this.totalList = list.map((item, idx) => this.normalizeVideoItem(item, idx, {
					isDrama: this.isDrama
				}))
				if (!keepIndex) this.currentIndex = 0
				this.syncDisplayList()
				this.$nextTick(() => {
					this.playCurrent()
				})
			},

			// ================== 红包 ==================
			onRedPacketEnd() {
				this.$emit('redPacketEnd')
			},

			onRedPacketReceive() {
				this.$emit('redPacketReceive')
			},

			// ================== 倍速 ==================
			onRateSelect(idx) {
				this.rateIndex = idx
				this.showRateBox = false
				const ctx = this.getVideoContext(`dyVideo${this.swiperIndex}`)
				if (ctx) {
					ctx.playbackRate(Number(this.rateList[idx]))
				}
			},

			// ================== 返回 ==================
			onBack() {
				this.$emit('back')
			},

			onSearchClick() {
				this.$emit('search')
			},

			// ================== 自动加载更多 ==================
			checkAutoLoadMore() {
				if (this.currentIndex >= this.totalList.length - 2) {
					this.$emit('loadMore')
				}
			},

			// ================== 任务计时 ==================
			startTask() {
				const data = this.currentItem
				if (!data) return

				// 切换时上报
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
	.dy-player {
		position: relative;
		width: 100%;
		height: 100%;
		overflow: hidden;
		background-color: #000000;
	}

	.dy-swiper {
		width: 100%;
		height: 100%;
	}

	.dy-swiper-item {
		width: 100%;
		height: 100%;
	}

	.dy-video-wrap {
		position: relative;
		width: 100%;
		height: 100%;
	}

	.dy-video {
		position: absolute;
		top: 0;
		left: 0;
		z-index: 1;
	}

	.dy-cover {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 3;
		background-color: #000;
	}

	.dy-cover-img {
		width: 100%;
		height: 100%;
	}

	.dy-pause {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 4;
		display: flex;
		align-items: center;
		justify-content: center;
		background-color: rgba(0, 0, 0, 0.1);
	}

	.dy-pause-img {
		width: 134rpx;
		height: 134rpx;
		opacity: 0.5;
	}

	.dy-loading {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 5;
		display: flex;
		align-items: center;
		justify-content: center;
	}

		.dy-error {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			z-index: 5;
			display: flex;
			align-items: center;
			justify-content: center;
			background-color: rgba(0, 0, 0, 0.5);
		}

		.dy-error-text {
			font-size: 28rpx;
			color: #ffffff;
			padding: 20rpx 40rpx;
			background-color: rgba(0, 0, 0, 0.6);
			border-radius: 40rpx;
		}

		/* 🔧 修复：触摸层避开底部导航栏区域 */
		.dy-touch-layer {
			position: absolute;
			top: 0;
			left: 0;
			right: 160rpx;
			width: auto;
			/* 底部留出导航栏空间：100rpx + safe-area-inset-bottom */
			bottom: calc(100rpx + env(safe-area-inset-bottom));
			z-index: 6;
			pointer-events: auto;
			touch-action: pan-y;
		}

		/* 横滑 seek 进度条 */
		.dy-progress-drag-time {
			position: absolute;
			top: 40%;
			left: 0;
			right: 0;
			z-index: 15;
			display: flex;
			align-items: baseline;
			justify-content: center;
			pointer-events: none;
			text-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.6);
			line-height: 1.2;
		}

		.dy-progress-drag-current {
			font-size: 64rpx;
			color: #ffffff;
			font-weight: bold;
		}

		.dy-progress-drag-total {
			font-size: 32rpx;
			color: rgba(255, 255, 255, 0.7);
		}

		.dy-progress-bar {
			position: absolute;
			left: 30rpx;
			right: 30rpx;
			bottom: calc(100rpx + env(safe-area-inset-bottom));
			height: 6rpx;
			z-index: 15;
			pointer-events: none;
		}

		.dy-progress-track {
			position: absolute;
			left: 0;
			right: 0;
			top: 0;
			height: 6rpx;
			background-color: rgba(255, 255, 255, 0.3);
			border-radius: 3rpx;
		}

		.dy-progress-fill {
			position: absolute;
			left: 0;
			top: 0;
			height: 6rpx;
			background-color: #ffffff;
			border-radius: 3rpx;
		}

	.dy-top-bar {
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		z-index: 10;
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 0 30rpx;
	}

	.dy-back {
		width: 45rpx;
		height: 45rpx;
	}

	.dy-search-btn {
		display: flex;
		align-items: center;
		padding: 16rpx 28rpx;
		background-color: rgba(0, 0, 0, 0.4);
		border-radius: 40rpx;

		image {
			width: 44rpx;
			height: 44rpx;
			margin-right: 12rpx;
		}

		text {
			font-size: 30rpx;
			color: #ffffff;
		}
	}

	.dy-episode-tag {
		font-size: 30rpx;
		color: #ffffff;
		text-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.3);
	}

			.dy-rate {
			display: flex;
			align-items: center;
			padding: 10rpx 16rpx;
			background-color: rgba(0, 0, 0, 0.3);
			border-radius: 30rpx;

			image {
				width: 32rpx;
				height: 32rpx;
				margin-right: 8rpx;
			}

			text {
				font-size: 24rpx;
				color: #ffffff;
			}
		}

.dy-bottom-info {
		position: absolute;
		left: 0;
		bottom: 180rpx;
		z-index: 10;
		width: 560rpx;
		padding: 0 30rpx;
	}

	.dy-info-main {
		display: flex;
		flex-direction: column;
	}

	.dy-info-name {
		font-size: 34rpx;
		font-weight: bold;
		color: #ffffff;
		text-shadow: 0 2rpx 6rpx rgba(0, 0, 0, 0.4);
		margin-bottom: 10rpx;
	}

	.dy-info-content {
		font-size: 28rpx;
		color: #ffffff;
		text-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.4);
		line-height: 1.4;
		display: -webkit-box;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical;
		overflow: hidden;
	}

	.dy-menu-layer {
		position: absolute;
		right: 20rpx;
		bottom: 220rpx;
		z-index: 20;
		pointer-events: auto;
	}

	.dy-red-packet-layer {
		position: absolute;
		top: 12vh;
		right: 20rpx;
		z-index: 30;
	}

	// 倍速弹窗
	.dy-rate-box {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 1000;
		background-color: rgba(0, 0, 0, 0.4);
		display: flex;
		align-items: flex-end;
	}

	.dy-rate-panel {
		width: 100%;
		height: 600rpx;
		background-color: #ffffff;
		border-radius: 40rpx 40rpx 0 0;
		padding: 30rpx;
	}

	.dy-rate-title {
		display: flex;
		align-items: center;
		font-size: 32rpx;
		font-weight: bold;
		color: #333333;
		margin-bottom: 40rpx;

		text {
			margin-left: 240rpx;
		}
	}

	.dy-rate-item {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 30rpx 40rpx;
		font-size: 30rpx;
		color: #333333;

		image {
			width: 32rpx;
			height: 22rpx;
		}
	}

	.dy-rate-active {
		color: #FE6100;
		font-weight: bold;
	}

	// 双击点赞动画
	.dy-heart {
		position: absolute;
		pointer-events: none;
		z-index: 999;
		animation: heartIn 0.3s ease forwards;
	}

	.dy-heart-out {
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
</style>
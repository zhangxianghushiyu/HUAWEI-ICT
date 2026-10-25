/**
 * 视频/短剧播放页公共逻辑 mixin
 * 供 pages/player/index.vue 与 pages/player/drama.vue 使用
 */
export default {
	data() {
		return {
			type: 'video', // video | drama
			targetId: '', // 视频id 或 短剧id
			videoList: [],
			startIndex: 0,
			dramaInfo: {},
			isDrama: false,
			isApp: false,
			isReady: false,
			autoPlay: true,
			loopPlay: false,
			showBack: true,
			showSearch: false,
			showRedPacket: true,
			redPacketTimes: -1,
			showGoldUnlock: false,
			unlockGoldCost: 0,
			userInfo: {},
			versionInfo: {},
			goldNum: 0,
			// uni-ad 激励视频配置（子页面可覆盖）
			rewardedAdpid: '1819020042',
			rewardedAdPurpose: '',
			rewardedAdCallback: null,
			rewardedAdExtraData: '',
			// 广告展示状态
			isAdShowing: false,
			adDidReward: false,
			// 分页加载
			pageNum: 1,
			pageSize: 10,
			isLastPage: false,
			loadingMore: false
		}
	},
	created() {
		// #ifdef APP-PLUS
		this.isApp = true
		// #endif
		// #ifndef APP-PLUS
		this.isApp = false
		// #endif
	},
	onLoad(option) {
		console.log('[player] onLoad option:', JSON.stringify(option))
		this.type = option.type || (this.$options && this.$options.type) || 'video'
		this.isDrama = this.type === 'drama'
		this.targetId = option.id || ''
		this.startIndex = Number(option.index) || 0

		// 根据类型设置页面表现
		this.showBack = this.isDrama
		this.showSearch = false

		this.userInfo = uni.getStorageSync('UserInfo') || {}
		this.versionInfo = uni.getStorageSync('Version') || {}

		// 绑定安卓广告回调
		if (typeof window !== 'undefined') {
			window.goldCoinAndroidCallBack = this.goldCoinAndroidCallBack.bind(this)
		}

		if (this.isDrama) {
			this.loadDramaList()
		} else {
			this.loadVideoMode()
		}
	},
	onShow() {
		console.log('[player] onShow')
		uni.setStorageSync('wxChatPage', false)
		uni.setStorageSync('lookAt', !this.isDrama)
		this.getWalletInfo()
		// #ifdef APP-PLUS
		// 初始化并预加载 uni-ad 激励视频
		this.initTakuRewardedAd()
		// #endif
		this.$nextTick(() => {
			if (this.isReady && this.$refs.playerRef) {
				this.$refs.playerRef.play()
				// 页面重新显示时恢复原生手势浮层
				if (typeof this.$refs.playerRef.showGestureOverlay === 'function') {
					this.$refs.playerRef.showGestureOverlay()
				}
			}
		})
	},
	onHide() {
		console.log('[player] onHide')
		uni.setStorageSync('lookAt', false)
		if (this.$refs.playerRef) {
			this.$refs.playerRef.pause()
			// 页面隐藏时隐藏原生手势浮层，避免遮挡其他 Tab
			if (typeof this.$refs.playerRef.hideGestureOverlay === 'function') {
				this.$refs.playerRef.hideGestureOverlay()
			}
		}
	},
	onUnload() {
		console.log('[player] onUnload')
		uni.setStorageSync('lookAt', false)
		if (this.$refs.playerRef) {
			this.$refs.playerRef.pause()
			if (typeof this.$refs.playerRef.hideGestureOverlay === 'function') {
				this.$refs.playerRef.hideGestureOverlay()
			}
		}
	},
	methods: {
		// ================== 数据加载 ==================
		loadDramaList() {
			// 如果有 globalData 数据且没有指定 id，优先使用 globalData
			const globalList = getApp().globalData.seeVodList || []
			const globalInfo = getApp().globalData.vodinfo || {}

			if (!this.targetId && globalList.length > 0) {
				this.dramaInfo = globalInfo
				this.videoList = this.formatDramaList(globalList)
				this.startIndex = getApp().globalData.seeVodIndex || this.startIndex
				this.showGoldUnlock = false
				getApp().globalData.seeVodList = this.videoList
				getApp().globalData.vodinfo = this.dramaInfo
				this.initPlayerRef()
				return
			}

			// 通过 id 请求：并行加载目标剧集 + 随机视频，一次性初始化
			if (!this.targetId) {
				uni.showToast({ title: '参数错误', icon: 'none' })
				return
			}

			uni.showLoading({ title: '加载中' })
			Promise.all([
				this.$api.getDetailsList({ dramaId: this.targetId }),
				this.fetchRandomDramaVideos()
			]).then(([dramaRes, randomVideos]) => {
				uni.hideLoading()
				const dataList = dramaRes.data || []
				const dramaVideos = this.formatDramaList(dataList)
				const formattedRandom = this.formatDramaList(randomVideos)

				// 合并后全局 Fisher-Yates 洗牌
				const merged = [...dramaVideos, ...formattedRandom]
				for (let i = merged.length - 1; i > 0; i--) {
					const j = Math.floor(Math.random() * (i + 1))
					;[merged[i], merged[j]] = [merged[j], merged[i]]
				}

				this.videoList = merged
				this.dramaInfo = globalInfo.dramaId === this.targetId ? globalInfo : (merged[0] || {})
				this.showGoldUnlock = false
				getApp().globalData.seeVodList = merged
				getApp().globalData.vodinfo = this.dramaInfo
				this.initPlayerRef()
			}).catch(err => {
				uni.hideLoading()
				console.error('[player] loadDramaList error:', err)
				uni.showToast({ title: '加载失败', icon: 'none' })
			})
		},

		/**
		 * 从片库随机拉取其他短剧的集，返回原始数据数组（Promise）
		 */
		fetchRandomDramaVideos() {
			return this.$api.homePage({
				pageNum: Math.floor(Math.random() * 5) + 1,
				pageSize: 5
			}).then(res => {
				const cards = (res.data && res.data.list) || []
				const otherCards = cards.filter(c => c.dramaId !== Number(this.targetId))
				if (otherCards.length === 0) return []

				const tasks = otherCards.slice(0, 4).map(card => {
					return this.$api.getDetailsList({ dramaId: card.dramaId }).then(r => {
						const eps = (r.data || []).filter(e => e.videoUrl)
						if (eps.length === 0) return []
						const pick = Math.min(1 + Math.floor(Math.random() * 2), eps.length)
						const shuffled = [...eps].sort(() => Math.random() - 0.5)
						return shuffled.slice(0, pick).map(e => ({
							...e,
							dramaId: card.dramaId,
							titleImg: card.titleImg || e.titleImg,
							name: card.title || e.title
						}))
					}).catch(() => [])
				})

				return Promise.all(tasks).then(results => results.flat().filter(Boolean))
			}).catch(() => [])
		},

		formatDramaList(list) {
			return list.map(item => {
				return {
					...item,
					coverShow: true,
					object_fit: 'cover',
					sliderShow: true,
					likeShow: item.isLike == 1,
					collectShow: item.isCollect == 1,
					selectWork: true
				}
			})
		},

		loadVideoMode() {
			if (this.targetId) {
				// 指定了视频 id：先获取单视频，再加载列表
				uni.showLoading({ title: '加载中' })
				this.$api.getVideoInfo({ id: this.targetId }).then(res => {
					uni.hideLoading()
					const item = res.data || {}
					this.videoList = [this.formatVideoItem(item)]
					this.startIndex = 0
					this.pageNum = 1
					this.initPlayerRef()
					this.loadVideoList(false)
				}).catch(err => {
					uni.hideLoading()
					console.error('[player] getVideoInfo error:', err)
					// 失败则直接加载列表
					this.loadVideoList(true)
				})
			} else {
				this.loadVideoList(true)
			}
		},

		loadVideoList(refresh = false) {
			if (this.isLastPage || this.loadingMore) return

			this.loadingMore = true
			this.$api.getVideoList().then(res => {
				this.loadingMore = false
				const data = res.data || []
				const formatted = data.map(item => this.formatVideoItem(item))

				if (refresh) {
					this.videoList = formatted
					this.isLastPage = false
					this.initPlayerRef()
				} else {
					const oldLen = this.videoList.length
					this.videoList = [...this.videoList, ...formatted]
					this.isLastPage = data.length === 0
					// 首次加载完成后初始化播放器
					if (oldLen === 0) {
						this.initPlayerRef()
					} else {
						this.$refs.playerRef && this.$refs.playerRef.appendVideos(formatted)
					}
				}
			}).catch(err => {
				this.loadingMore = false
				console.error('[player] loadVideoList error:', err)
			})
		},

		formatVideoItem(item) {
			return {
				...item,
				coverShow: true,
				object_fit: 'cover',
				sliderShow: true,
				likeShow: item.isLike == 1,
				collectShow: item.isCollect == 1,
				selectWork: false
			}
		},

		initPlayerRef() {
			this.$nextTick(() => {
				if (this.$refs.playerRef) {
					this.isReady = true
					this.$refs.playerRef.initPlayer(this.videoList, this.startIndex)
				}
			})
		},

		// ================== 红包/金币 ==================
		getWalletInfo() {
			if (this.isDrama) {
				this.showRedPacket = false
				return
			}
			this.$api.shortWalletInfo().then(res => {
				const data = res.data || {}
				this.redPacketTimes = (data.seconds || 0) * 1000
			}).catch(err => {
				console.error('[player] getWalletInfo error:', err)
			})
		},

		onRedPacketEnd() {
			console.log('[player] red packet end')
		},

		onRedPacketReceive() {
			if (!this.userInfo.id) return
			// 使用 uni-ad 激励视频替代旧版 rh_android 广告
			this.showTakuRewardedVideo('redpacket')
		},

		goldCoinAndroidCallBack(callback) {
			uni.showLoading({ title: '获取结果...' })
			setTimeout(() => {
				uni.hideLoading()
				this.$api.taKuResultByTransId({ transId: callback }).then(res => {
					if (res.data) {
						this.goldNum = res.data.money || 0
						this.$refs.goldPopup.open('center')
						this.getWalletInfo()
					}
				})
			}, 1000)
		},

		closeGoldPopup() {
			this.$refs.goldPopup.close()
			this.goldNum = 0
		},

		// ================== 菜单事件 ==================
		onVideoChange({ index, item }) {
			console.log('[player] video change:', index)
			if (this.isDrama) {
				getApp().globalData.seeVodIndex = index
			}
		},

		onLike(item) {
			const promise = this.isDrama
				? this.$api.dramaLike({ dramaDetailsId: item.dramaDetailsId })
				: this.$api.videoLike({ id: item.id })
			promise.catch(err => console.error('[player] like error:', err))
		},

		onCollect(item) {
			const promise = this.isDrama
				? this.$api.dramaCollect({ dramaId: item.dramaId })
				: this.$api.videoCollect({ id: item.id })
			promise.catch(err => console.error('[player] collect error:', err))
		},

		onShare(item) {
			if (!this.versionInfo.appUrl) return
			// 绑定分享回调
			if (typeof window !== 'undefined') {
				window.shareAndroidCallBack = () => {
					// 分享成功后可以增加积分等
				}
			}
			// 这里简单调用朋友圈/好友分享
			this.callAndroid('shareForFriends', '红樱桃', '快来跟我一起看短剧领金币吧！', this.versionInfo.url, this.versionInfo.appUrl)
		},

		onLongPress(item) {
			console.log('[player] longpress:', item)
		},

		// ================== 分页/刷新 ==================
		onLoadMore() {
			if (this.isDrama) return
			this.loadVideoList(false)
		},

		onRefresh() {
			if (this.isDrama) return
			this.loadVideoList(true)
		},

		// ================== 解锁 ==================
		onUnlock({ type, item, callback }) {
			if (type === 'ad') {
				this.unlockByAd(item, callback)
			} else if (type === 'gold') {
				this.unlockByGold(item, callback)
			}
		},

		unlockByAd(item, callback) {
			if (!this.userInfo.id || !item) return
			// 使用 uni-ad 激励视频替代旧版 rh_android 广告
			this.showTakuRewardedVideo('unlock', () => {
				callback && callback()
				this.refreshDramaList()
			}, item)
		},

		unlockByGold(item, callback) {
			if (!item || !item.dramaId) return
			this.$api.unlockByGold({ dramaId: item.dramaId }).then(res => {
				callback && callback('')
				this.refreshDramaList()
			}).catch(err => {
				console.error('[player] unlockByGold error:', err)
			})
		},

		refreshDramaList() {
			if (!this.targetId && (!this.dramaInfo || !this.dramaInfo.dramaId)) return
			const id = this.targetId || this.dramaInfo.dramaId
			this.$api.getDetailsList({ dramaId: id }).then(res => {
				const dataList = res.data || []
				const formatted = this.formatDramaList(dataList)
				this.videoList = formatted
				// 刷新 globalData
				getApp().globalData.seeVodList = formatted
				// 通知播放器刷新列表
				if (this.$refs.playerRef && typeof this.$refs.playerRef.refreshList === 'function') {
					this.$refs.playerRef.refreshList(formatted, true)
				}
			}).catch(err => {
				console.error('[player] refreshDramaList error:', err)
			})
		},

		// ================== 任务计时 ==================
		onTaskTime({ index, time, item }) {
			if (!item || !item.dramaDetailsId) return
			// 原代码该接口被注释，这里保留调用入口
			// this.$api.dramaTaskHandle({ dramaDetailsId: item.dramaDetailsId, dramaId: item.dramaId, second: time })
		},

		// ================== 返回 ==================
		onBack() {
			// 无论是短视频还是短剧，都返回上一页
			// 短视频从 TabBar 进入时，上一页是其他 Tab 页面
			uni.navigateBack({
				fail: () => {
					// 如果返回失败，回到首页
					uni.switchTab({ url: '/pages/travel/index' })
				}
			})
		},

		goToSearch() {
			uni.navigateTo({ url: '/pages/index/index' })
		},

		// ================== uni-ad 激励视频 ==================
		initTakuRewardedAd() {
			const UniAds = typeof window !== 'undefined' ? window.UniAds : null
			if (!UniAds || !UniAds.loadRewardedVideoAd) {
				console.log('[player] UniAds 未就绪，跳过激励视频初始化')
				return
			}
			const userInfo = this.userInfo || uni.getStorageSync('UserInfo') || {}
			UniAds.loadRewardedVideoAd({
				adpid: this.rewardedAdpid,
				userId: String(userInfo.id || ''),
				extraData: JSON.stringify({ type: '2', value: '3' })
			}, this.rewardedAdListener())
		},

		rewardedAdListener() {
			const self = this
			return {
				onAdLoaded: () => {
					console.log('[player] 激励视频预加载成功')
				},
				onAdFailed: (err) => {
					console.log('[player] 激励视频预加载失败', err)
				},
				onAdShow: () => {
					self.isAdShowing = true
				},
				onAdClosed: () => {
					const didReward = self.adDidReward
					self.isAdShowing = false
					self.adDidReward = false
					self.handleTakuReward(didReward)
				},
				onReward: () => {
					self.adDidReward = true
				},
				onAdClicked: () => {}
			}
		},

		showTakuRewardedVideo(purpose, callback, item) {
			this.rewardedAdPurpose = purpose || ''
			this.rewardedAdCallback = callback || null
			const UniAds = typeof window !== 'undefined' ? window.UniAds : null
			if (!UniAds || !UniAds.showRewardedVideoAd) {
				uni.showToast({ title: '广告组件未就绪', icon: 'none' })
				return
			}
			const userInfo = this.userInfo || uni.getStorageSync('UserInfo') || {}
			if (!userInfo.id) {
				uni.showToast({ title: '请先登录', icon: 'none' })
				return
			}
			// 解锁用途：type=1 真正解锁剧集（后端批量解锁 unlock_num 集）；其余用途走红包奖励
			const extraData = (purpose === 'unlock' && item && item.dramaDetailsId)
				? JSON.stringify({ type: '1', value: String(item.dramaDetailsId) })
				: JSON.stringify({ type: '2', value: '3' })
			this.rewardedAdExtraData = extraData
			this.adDidReward = false
			uni.showToast({ title: '广告加载中，请稍候...', icon: 'none', duration: 2000 })
			UniAds.showRewardedVideoAd({
				adpid: this.rewardedAdpid,
				userId: String(userInfo.id || ''),
				extraData: extraData
			}, this.rewardedAdListener())
		},

		handleTakuReward(didReward) {
			if (this.rewardedAdPurpose === 'redpacket') {
				this.goldNum = 100
				if (this.$refs.goldPopup) {
					this.$refs.goldPopup.open('center')
				}
				this.getWalletInfo()
				// uni-ad 没有服务端验证回调，关闭时看完即发奖励
				this.$api.adRewardToCoin().then(apiRes => {
					if (apiRes.code === 200) {
						uni.showToast({ title: '+100 金币', icon: 'none' })
					}
				}).catch(err => {
					console.error('[player] 红包广告奖励接口失败:', err)
				})
			} else if (this.rewardedAdPurpose === 'unlock' && didReward) {
				if (typeof this.rewardedAdCallback === 'function') {
					this.rewardedAdCallback()
				}
				// uni-ad 没有服务端验证回调，前端上报解锁
				if (this.rewardedAdExtraData) {
					this.$api.uniAdCallback({
						extraData: this.rewardedAdExtraData,
						adpid: this.rewardedAdpid,
						requestId: this.generateAdRequestId(),
						timestamp: Date.now()
					}).then(apiRes => {
						console.log('[player] uni-ad 解锁回调结果:', apiRes)
					}).catch(err => {
						console.error('[player] uni-ad 解锁回调失败:', err)
					})
				}
				this.refreshDramaList()
				// 短剧解锁成功，发放双倍金币奖励
				if (typeof this.claimAdReward === 'function') {
					this.claimAdReward()
				}
			}
			this.rewardedAdPurpose = ''
			this.rewardedAdCallback = null
		},

		// ================== 工具 ==================
		generateAdRequestId() {
			const userInfo = this.userInfo || uni.getStorageSync('UserInfo') || {}
			return `${userInfo.id || 0}_${Date.now()}_${Math.floor(Math.random() * 1000000)}`
		},

		callAndroid(method, ...args) {
			// #ifdef APP-PLUS
			if (typeof window !== 'undefined' && window.rh_android && typeof window.rh_android[method] === 'function') {
				try {
					window.rh_android[method](...args)
				} catch (e) {
					console.error('[player callAndroid] error:', method, e)
				}
			} else {
				console.warn('[player callAndroid] method not available:', method)
			}
			// #endif
		}
	}
}

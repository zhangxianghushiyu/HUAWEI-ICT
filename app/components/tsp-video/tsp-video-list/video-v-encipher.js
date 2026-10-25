/* 
 lodData 上拉加载回调
 refreshData 下拉加载回调
 */
export default {
	props: {
		/* 是否开启下拉刷新 */
		loadOpen: {
			type: Boolean,
			default: true
		},
		/* 底部自适应 */
		autoBottomBar: {
			type: Boolean,
			default: false
		},
		/* tabBar栏 0系统tabBar栏 1自定义tabBar栏 */
		tabBarShow: {
			type: Number,
			default: 0
		},
		/* 自定义底部栏的高度 */
		tabBarHeight: {
			type: Number,
			default: 50
		},
		/* 进度条离底部的距离 px*/
		speedBottom: {
			type: Number,
			default: 0
		},
		/* 初始加载完成是否自动播放 */
		autoplay: {
			type: Boolean,
			default: true
		},
		/* 是否循环播放 */
		loopPlay: {
			type: Boolean,
			default: false
		},
		/* 是否开启自动播放 */
		nextPlay: {
			type: Boolean,
			default: true
		},
		/* 视频总数量，有值才能滑动加载到最后一个视频 */
		totalvod: {
			type: Number,
			default: 0
		},
		/* 是否开启双击点赞 */
		doubleOpen: {
			type: Boolean,
			default: true
		},
		/* 双击点赞屏幕是否显示桃心 */
		doubleHeart: {
			type: Boolean,
			default: true
		},
		/* 多个tab视频时需传入不同的类型 */
		swId: {
			type: String,
			default: ''
		},
		/* 是否有显示 statusBarHeight 状态栏适配 */
		showBarHeight: {
			type: Boolean,
			default: false
		}
	},
	data() {
		return {
			isShowReward: false,
			showRewardGold: false,
			showRewardNum: '',
			videoStyle: {},
			screenWidth: 0,
			//屏幕的宽度
			screenHeight: 0,
			//屏幕的高度
			videoCtx: null,
			vodIndex: 0,
			// 当前视频下标
			currentIndex: 0,
			//定位播放的下标
			contentShow: false,
			loadShow: false,
			autoplayVideo: true,
			// 初始加载完成是否自动播放
			//当前播放的视频数组
			vodList: [
				/* {
					vodUrl:"https://outin-9cc4a7aedd2a11eaabb800163e1a65b6.oss-cn-shanghai.aliyuncs.com/customerTrans/43121ec9955a1ce95146d379f15ea9bd/566eafe2-17b52f0f17b-0007-0f29-cc2-eb217.mp4",
					coverImg:'/static/image/cover1.jpg', //视频封面
					coverShow:false, //是否显示视频封面，vue 小程序端不播放会显示视频，可以不用显示封面，App不播放不会显示视频，就需要封面了
					vodPaly:true, //是否播放、暂停视频
					vodShow:true, //是否显示视频
					object_fit:'contain', //视频的显示类型
					pauseShow:false, //是否显示暂停图标
					loadingShow:false, //是否显示loading加载动画
					fabulousShow:false,//是否点赞
					followReally:false, //是否关注
					sliderShow:true, //是否显示进度条
					rotateImgShow:true, //是否显示旋转头像
				} */
			],
			totalPlayList: [],
			finalList: [],
			changeIndex: 0,
			//current 下标
			beforeVodInfo: {},
			//当前播放视频对象
			vodCurIndex: 0,
			//当前播放视频对象下标
			circular: true,
			durationNum: 300,
			showShade: false,
			//显示遮罩swiper
			openSpot: false,
			shadeNum: 0,
			bufferTime: null,
			//缓冲视频显示加载动画防抖
			sliderProgress: 0,
			//进度条滑动层宽度
			sliderTime: 0,
			isCanTauch: false,
			//显示滑动进度条时变化的时间
			videoTime: 0,
			//视频时长 时间
			endTime: 0,
			//滑动进度条最终的时间
			sliderDrag: false,
			//是否在拖动进度
			brightSlider: false,
			//是否显示光亮的进度条
			sliderEndTime: null,
			//滑动结束隐藏光亮进度条时间延迟
			bufferNum: null,
			//显示loading加载进度的时间条件
			bufferShow: false,
			//显示loading加载进度的条件
			playOpen: false,
			//视频播放失败，是否重播视频的条件
			failTime: null,
			//视频播放失败，重播视频的时间
			repeatTime: null,
			palyCartoon: false,
			//是否播放旋转头像
			shakePlay: false,
			//是否有防抖视频播放
			touchClientY: 0,
			//触碰的坐标
			moveClientY: 0,
			//移动的坐标
			moveOpacity: false,
			//是否透明
			refreshShow: false,
			//是否显示下拉刷新
			refreshOpacity: 0,
			//下拉刷新的透明度
			refreshclientY: 0,
			//下拉刷新的坐标
			refreshOpen: false,
			//下拉刷新的触发条件
			playErrTime: null,
			interceptIndex: [],
			loadTime: null,
			beginLoad: 0,
			loadStart: false,
			muteSetup: false,
			//是否设置为静音
			/* 双击点赞部分 */
			lastTapDiffTime: 0,
			//上次点击时间
			lastTapTimeoutFunc: null,
			//单击事件事件函数
			likeList: [],
			//双击点赞显示的桃心数据
			likeId: 1,
			doubleOutTime: null,
			clearDoubleTime: null,
			versionInfo: {},
			//版本信息
			userInfo: {},
			//用户信息
			taskTimer: null,
			//任务时常容器
			tasktime: 0,
			//任务时常时间
			playInfo: 0,
			//记录当前是否切换视频使用
			changePlayType: false,
			showAd1: 0,
			jiesuoIndex: 0,
			isShowRateBox: false,
			rateList: ['2.0', '1.0', '0.5'],
			defaultRateIndex: 1,
			isShowShare: false,
			boxDown: 0,
			videoInfo: getApp().globalData.vodinfo
		};
	},
	mounted() {
		// uni.showLoading({
		// 	title: '加载中',
		// 	mask: 'true'
		// });
	},
	created() {
		console.log('[video-v] created start');
		this.getBox()
		this.isShow = uni.getStorageSync('lookAt')
		// 防御：避免其他页面把 swiper 触摸锁死后未恢复
		this.isCanTauch = false;
		console.log('[video-v] created lookAt:', this.isShow, '$store.state.isShow:', this.$store.state.isShow, 'isCanTauch reset:', this.isCanTauch);
		this.videoContext = uni.createVideoContext('myVideo' + getApp().globalData.seeVodIndex, this)
		uni.$on('resetIndex', res => {
			this.showAd1 = 0
		})
		uni.$on('touch', res => {
			console.log('[video-v] touch event received, res:', res, 'old isCanTauch:', this.isCanTauch);
			this.isCanTauch = res
		})
		this.userInfo = uni.getStorageSync('UserInfo');
		this.versionInfo = uni.getStorageSync('Version');
		const deviceInfo = uni.getSystemInfoSync();
		let topBarHeight = this.showBarHeight ? deviceInfo.statusBarHeight : 0x0;
		this.videoStyle.statusBarHeight = topBarHeight + 'px';
		this.videoStyle.width = deviceInfo.screenWidth + 'px';
		this.screenWidth = deviceInfo.screenWidth;
		this.screenHeight = deviceInfo.screenHeight;
		if (this.tabBarShow == 0x1) {
			this.videoStyle.height = deviceInfo.screenHeight + 'px';
		} else {
			this.videoStyle.height = deviceInfo.windowHeight - topBarHeight + 'px';
		}
		if (this.autoBottomBar && window.rh_android && window.rh_android.getNavBarHeight) {
			const bottomBarHeight = window.rh_android.getNavBarHeight();
			if (this.tabBarShow == 0x1) {
				this.videoStyle.subHeight = deviceInfo.screenHeight - bottomBarHeight + 'px';
			} else {
				this.videoStyle.subHeight = deviceInfo.windowHeight - topBarHeight - bottomBarHeight + 'px';
			}
		} else if (this.tabBarShow == 0x1) {
			this.videoStyle.subHeight = deviceInfo.screenHeight + 'px';
		} else {
			this.videoStyle.subHeight = deviceInfo.windowHeight - topBarHeight + 'px';
		}
		console.log('[video-v] created videoStyle:', JSON.stringify(this.videoStyle), 'screenWidth:', this.screenWidth, 'screenHeight:', this.screenHeight);
	},
	watch: {
		vodList(newIndex, oldIndex) {
			if (newIndex) {
				uni.$emit('videoChange', true)
			}
		},
		vodCurIndex(newIndex, oldIndex) {
			console.log('[video-v] watch vodCurIndex new:', newIndex, 'old:', oldIndex);
			/* 上一个视频处理 */
			if (oldIndex >= 0) {
				this.vodList.filter((videoItem, index) => {
					if (oldIndex === videoItem.videoIndex) {
						// 根据 coverShow 的值来设置 coverOpacity
						videoItem.coverOpacity = !!videoItem.coverShow;
						// 停止视频播放
						videoItem.vodPaly = false;
						// 隐藏暂停图标
						videoItem.pauseShow = false;
						// 隐藏加载图标
						videoItem.loadingShow = false;
						// 暂停指定的视频
						uni.createVideoContext("myVideo" + index + this.swId, this).pause();
					}
				});
			}
		}
	},
	methods: {
		//获取宝箱信息
		getBox() {
			this.$api.getBoxInfo().then(res => {
				this.boxInfo = res.data
				this.boxDown = 0
				if (this.boxInfo.time) {
					const currentTime = new Date();
					const specifiedTime = new Date(this.boxInfo.time);
					if (currentTime >= specifiedTime) return
					const timeDifferenceInMilliseconds = specifiedTime.getTime() - currentTime.getTime();
					if (timeDifferenceInMilliseconds < 0) {
						return
					}
					this.boxDown = (timeDifferenceInMilliseconds / 1000).toFixed(0);
					//计时器
					clearInterval(this.timer)
					this.timer = setInterval(() => {
						this.boxDown -= 1;
						if (this.boxDown <= 0) {
							clearInterval(this.timer)
						}
					}, 1000);
				}

			})
		},
		receiveBox(index) {
			if (this.boxDown != 0) {
				this.$utils.showTip('点击太频繁，请稍后重试', '提示')
				return
			}

			// const index = this.boxIndex == 1 ? 5 : 1

			this.$refs.goldCoinsPopup.close();

			this.$api.checkAdsNums().then(res => {
				window.rh_android.showAd(this.userInfo.id, JSON.stringify({
					type: 1,
					value: index
				}), 3)
			})
			if (this.boxIndex == 1) {
				console.log('第二次开宝箱');
				this.boxIndex = 0
			} else {
				console.log('第一次开宝箱');
				this.boxIndex = 1
			}
		},
		openRateBox() {
			this.isShowRateBox = true
		},
		//选择倍速
		chooseRate(index) {
			const rate = index == 0 ? '2.0' : index == 1 ? '1' : '0.5'
			this.videoContext.playbackRate(Number(rate))
			this.defaultRateIndex = index
		},
		//关闭奖励弹窗
		closeReward() {
			this.isShowReward = false
			this.showRewardNum = ''
			const item = getApp().globalData.vodinfo;
			//获取视频信息
			this.$api.getDetailsList({
				dramaId: item.dramaId
			}).then(res => {
				const dataList = res.data;
				dataList.filter(it => {
					/** 参数数据自行拼接  */
					it.coverShow = true; //是否显示视频封面，vue 小程序端不播放会显示视频，可以不用显示封面，App不播放不会显示视频，就需要封面了
					it.object_fit = 'cover'; //视频的显示类型 contain cover
					it.sliderShow = true; //是否显示进度条
					it.likeShow = !(item.isLike == 0); //是否点赞
					it.collectShow = !(item.isCollect == 0); //是否收藏
					it.selectWork = true; //是否短剧
				});
				getApp().globalData.seeVodList = dataList;
				this.$emit('queryList', dataList);
				this.initVod(dataList, this.jiesuoIndex - 1)
			});
		},
		/* 初始加载视频 */
		initVod(dataList, index) {
			console.log('[video-v] initVod start, dataList.length:', dataList ? dataList.length : 0, 'index:', index, 'autoplay:', this.autoplay);
			// 将视频解锁的安卓回调函数绑定到 window 对象上
			window.videoUnlockAndroidCallBack = this.videoUnlockAndroidCallBack;
			window.slideShowAdCallBack = this.slideShowAdCallBack
			// 设置自动播放视频的标志
			this.autoplayVideo = this.autoplay;
			// 如果传入了索引，将播放时长重置为 0
			if (index) {
				this.durationNum = 0;
			}

			// 确定开始播放的索引，如果没有传入则从 0 开始
			let playIndex = index ? index : 0;

			// 遍历数据列表，为每个视频项设置相关属性
			dataList.forEach((videoItem, itemIndex) => {
				// 设置视频项的索引
				videoItem.videoIndex = itemIndex;
				// 根据当前索引确定是否播放该视频
				videoItem.vodPaly = itemIndex === playIndex;
				// 隐藏暂停显示
				videoItem.pauseShow = false;
				// 隐藏加载显示
				videoItem.loadingShow = false;
				// 根据封面显示状态设置封面透明度
				videoItem.coverOpacity = !!videoItem.coverShow;
			});

			// 按照视频索引对数据列表进行排序
			dataList.sort((a, b) => {
				return a.videoIndex - b.videoIndex;
			});
			// 将排序后的数据列表赋值给总播放列表
			this.totalPlayList = dataList;
			// 根据数据列表长度确定内容是否显示
			this.contentShow = dataList.length > 0;
			// 根据数据列表长度确定加载状态是否显示
			this.loadShow = dataList.length > 0;
			// 设置当前视频索引
			this.vodCurIndex = playIndex;
			console.log('[video-v] initVod contentShow:', this.contentShow, 'vodCurIndex:', this.vodCurIndex, 'totalPlayList.length:', this.totalPlayList.length);
			let currentNum = 0;

			// 如果总播放列表长度大于等于 3，初始化一个长度为 3 的视频列表
			if (this.totalPlayList.length >= 3) {
				this.vodList = [{}, {}, {}];
			} else {
				// 否则直接使用数据列表作为视频列表
				this.vodList = dataList;
			}

			// 延迟 100 毫秒后执行以下逻辑
			setTimeout(() => {
				console.log('[video-v] initVod setTimeout[100] start, totalPlayList.length:', this.totalPlayList.length, 'playIndex:', playIndex);
				if (this.totalPlayList.length >= 3) {
					// 获取总播放列表的长度
					let totalLength = this.totalPlayList.length;
					// 计算总播放列表长度除以 3 的余数
					let remainder = totalLength % 3;
					// 计算当前播放索引的前一个索引
					let prevIndex = playIndex - 1 < 0 ? 0 : playIndex - 1;
					// 计算当前播放索引的后两个索引
					let nextIndex = playIndex + 2;
					// 计算总播放列表中可以被 3 整除的部分的长度
					let divisibleLength = totalLength - remainder;

					// 如果当前播放索引为 0 或者当前播放索引加 1 大于等于可整除部分的长度
					if (playIndex === 0 || playIndex + 1 >= divisibleLength) {
						// 设置循环标志为 false
						this.circular = false;
						let slicedList = [];

						if (playIndex === 0) {
							// 从总播放列表中截取从 prevIndex 到 playIndex + 3 的部分
							slicedList = JSON.parse(JSON.stringify(this.totalPlayList.slice(prevIndex,
								playIndex + 3)));
						} else {
							// 从总播放列表中截取从 divisibleLength - 3 到 divisibleLength 的部分
							slicedList = JSON.parse(JSON.stringify(this.totalPlayList.slice(divisibleLength - 3,
								divisibleLength)));
						}

						// 设置截取后的列表
						this.setSliceList(slicedList);

						if (playIndex !== 0) {
							// 从总播放列表中截取从 divisibleLength 到 totalLength 的部分
							let remainingList = JSON.parse(JSON.stringify(this.totalPlayList.slice(
								divisibleLength, totalLength)));
							// 将剩余部分添加到视频列表中
							remainingList.forEach(item => {
								this.vodList.push(item);
							});

							// 如果总播放列表长度大于等于总视频数，隐藏加载显示
							if (this.totalPlayList.length >= this.totalvod) {
								this.loadShow = false;
							}
						}
					} else {
						// 设置循环标志为 true
						this.circular = true;
						// 从总播放列表中截取从 prevIndex 到 nextIndex 的部分
						let slicedList = JSON.parse(JSON.stringify(this.totalPlayList.slice(prevIndex,
							nextIndex)));
						// 设置截取后的列表
						this.setSliceList(slicedList);
					}
				} else {
					// 如果总播放列表长度小于 3，设置循环标志为 false
					this.circular = false;
				}

				// 获取视频信息
				this.getVodInfo();

				// 遍历视频列表，找到当前播放视频在视频列表中的索引
				this.vodList.forEach((videoItem, itemIndex) => {
					if (this.vodCurIndex === videoItem.videoIndex) {
						currentNum = itemIndex;
					}
				});

				// 如果当前播放索引加 1 大于等于总播放列表长度减去余数，并且总播放列表长度大于等于 3
				if (this.vodCurIndex + 1 >= this.totalPlayList.length - this.totalPlayList.length % 3 && this
					.totalPlayList.length >= 3) {
					// 设置加载开始标志为 true
					this.loadStart = true;
					// 清除之前的加载定时器
					clearTimeout(this.loadTime);
					// 延迟 300 毫秒后开始加载数据
					this.loadTime = setTimeout(() => {
						this.beginLoad = this.totalPlayList.length;
						// 触发加载数据的事件
						this.$emit("lodData");
					}, 300);
				}

				// 如果播放索引大于 0，设置当前索引
				if (playIndex > 0) {
					this.currentIndex = currentNum;
				} else {
					this.currentIndex = 0
				}

				// 设置更改索引和视频索引
				this.changeIndex = currentNum;
				this.vodIndex = currentNum;
				console.log('[video-v] initVod setTimeout[100] end, currentIndex:', this.currentIndex, 'changeIndex:', this.changeIndex, 'vodIndex:', this.vodIndex, 'circular:', this.circular);
			}, 100);

			// 延迟 200 毫秒后执行以下逻辑
			setTimeout(() => {
				console.log('[video-v] initVod setTimeout[200] start, autoplayVideo:', this.autoplayVideo, 'currentNum:', currentNum, 'beforeVodInfo:', this.beforeVodInfo ? this.beforeVodInfo.videoUrl : 'null');
				// 设置播放时长为 300
				this.durationNum = 300;
				if (this.autoplayVideo) {
					// 如果允许自动播放，开始播放视频
					this.swiperPlay(currentNum);
				} else {
					// 否则设置前一个视频的播放状态为暂停
					this.beforeVodInfo.vodPaly = false;
					this.beforeVodInfo.pauseShow = true;
				}
			}, 200);
		},
		/* 赋值截取的数据 */
		setSliceList(resData) {
			// 遍历截取的数据列表
			resData.forEach((videoItem) => {
				// 根据视频索引对 3 取余的结果，将视频项赋值到对应的 vodList 位置
				const remainder = videoItem.videoIndex % 3;
				if (remainder === 0) {
					this.vodList[0] = videoItem;
				} else if (remainder === 1) {
					this.vodList[1] = videoItem;
				} else if (remainder === 2) {
					this.vodList[2] = videoItem;
				}
			});
		},
		/* 到底加载方法 */
		lodingData(dataList) {
			// 获取当前总播放列表的长度
			let currentTotalLength = this.totalPlayList.length;

			// 遍历新的数据列表，为每个视频项设置相关属性
			dataList.forEach((videoItem, index) => {
				// 设置视频项的索引，基于当前总播放列表长度递增
				videoItem.videoIndex = currentTotalLength + index;
				// 设置视频为未播放状态
				videoItem.vodPaly = false;
				// 隐藏暂停显示
				videoItem.pauseShow = false;
				// 隐藏加载显示
				videoItem.loadingShow = false;
				// 根据封面显示状态设置封面透明度
				videoItem.coverOpacity = !!videoItem.coverShow;
			});

			// 将新的数据列表合并到总播放列表中
			this.totalPlayList = this.totalPlayList.concat(dataList);

			// 在 DOM 更新后执行以下操作
			this.$nextTick(() => {
				// 获取视频切片列表
				this.getVodSliceList();

				// 延迟 100 毫秒后执行以下操作
				setTimeout(() => {
					// 遍历最终的视频列表
					this.finalList.forEach((videoItem) => {
						// 排除当前播放的视频项
						if (videoItem.videoIndex !== this.vodCurIndex) {
							// 根据视频索引对 3 取余的结果，将视频项赋值到对应的 vodList 位置
							const remainder = videoItem.videoIndex % 3;
							if (remainder === 0) {
								this.vodList[0] = videoItem;
							} else if (remainder === 1) {
								this.vodList[1] = videoItem;
							} else if (remainder === 2) {
								this.vodList[2] = videoItem;
							}
						}
					});

					// 计算总视频数能被 3 整除的部分
					const divisibleTotal = this.totalvod - this.totalvod % 3;

					// 满足特定条件时，将后续视频项添加到 vodList 中
					if (this.vodCurIndex !== 0 && this.vodList.length === 3 && this.vodCurIndex + 1 >=
						divisibleTotal && this.totalvod > 0) {
						// 截取当前播放索引之后的视频项
						const remainingVideos = this.totalPlayList.slice(this.vodCurIndex + 1, this
							.totalPlayList.length);
						// 将截取的视频项添加到 vodList 中
						remainingVideos.forEach((videoItem) => {
							this.vodList.push(videoItem);
						});
					}

					// 更新开始加载的位置为当前总播放列表的长度
					this.beginLoad = this.totalPlayList.length;

					// 在 DOM 更新后，设置循环标志为 true
					this.$nextTick(() => {
						this.circular = true;
					});
				}, 100);
			});
		},
		/* 重新加载 */
		refreshSquare(dataList, index) {
			// 确定播放的起始索引，如果未传入 index 则从 0 开始
			let playIndex = index ? index : 0;

			// 重置数据，具体实现由 resetData 方法完成
			this.resetData();

			// 隐藏刷新显示和刷新开关
			this.refreshShow = false;
			this.refreshOpen = false;

			// 重置当前视频索引、当前索引为 0
			this.vodCurIndex = 0;
			this.currentIndex = 0;

			// 清空视频列表和总播放列表
			this.vodList = [];
			this.totalPlayList = [];

			// 延迟 100 毫秒后调用 initVod 方法来初始化视频播放
			setTimeout(() => {
				this.initVod(dataList, playIndex);
			}, 100);
		},
		/* 静音设置 */
		muteVideo(val) {
			this.muteSetup = val;
		},
		/* onShow播放视频 */
		showPlay() {
			console.log('[video-v] showPlay called, vodIndex:', this.vodIndex, 'contentShow:', this.contentShow, 'beforeVodInfo.vodPaly:', this.beforeVodInfo ? this.beforeVodInfo.vodPaly : 'null');
			if (this.vodIndex != null && this.contentShow && !this.beforeVodInfo.vodPaly) {
				this.videoPlay(this.vodIndex);
			}
		},
		/* onHide暂停视频 */
		hidePause() {
			console.log('[video-v] hidePause called, vodIndex:', this.vodIndex, 'contentShow:', this.contentShow, 'beforeVodInfo.vodPaly:', this.beforeVodInfo ? this.beforeVodInfo.vodPaly : 'null');
			if (this.vodIndex != null && this.contentShow && this.beforeVodInfo.vodPaly) {
				this.videoPause(this.vodIndex);
			}
			this.brightSlider = false; //隐藏光亮的进度条
		},

		/* 点击暂停、播放视频 */
		playSpot(index) {
			if (this.beforeVodInfo.vodPaly) {
				//暂停
				this.videoPause(index);
				this.beforeVodInfo.pauseShow = true; //显示暂停图标
			} else {
				//播放
				this.videoPlay(index);
				this.beforeVodInfo.pauseShow = false; //关闭暂停图标
			}
		},

		/* 播放视频 */
		videoPlay(index) {
			console.log('[video-v] videoPlay index:', index, 'swId:', this.swId, 'videoUrl:', this.beforeVodInfo ? this.beforeVodInfo.videoUrl : 'null');
			this.openSpot = true;
			this.getVodInfo();
			this.showShade = false;
			this.beforeVodInfo.vodPaly = true;
			this.beforeVodInfo.pauseShow = false;
			this.brightSlider = false;
			this.loadStart = false;
			this.$nextTick(() => {
				this.moveOpacity = false;
				const ctx = uni.createVideoContext('myVideo' + index + this.swId, this);
				console.log('[video-v] videoPlay createVideoContext id:', 'myVideo' + index + this.swId, 'context exists:', !!ctx);
				if (ctx) ctx.play();
			});
		},
		/* 暂停视频 */
		videoPause(index) {
			// 去除计时
			clearInterval(this.taskTimer);

			// console.log('暂停视频myVideo============>' + index)
			this.getVodInfo();
			clearTimeout(this.sliderEndTime);
			this.beforeVodInfo.vodPaly = false;
			if (this.vodIndex == index && this.beforeVodInfo.sliderShow) {
				this.brightSlider = true;
			}
			this.$nextTick(() => {
				uni.createVideoContext("myVideo" + index + this.swId, this).pause();
			});
		},
		changeSwiper(ev) {
			console.log('[video-v] changeSwiper current:', ev.detail.current, 'isCanTauch:', this.isCanTauch, 'vodList.length:', this.vodList.length);
			//保存任务时常
			this.startStsk(this.vodCurIndex);
			//切换视频任务
			this.changeTask(this.vodCurIndex);

			this.showAd1++
			if (this.showAd1 >= 3) {
				this.showAd1 = 0
				// this.slideShowAd()
			}
			// 获取当前滑动到的索引
			let currentSwiperIndex = ev.detail.current;
			// 根据当前滑动索引获取对应的视频索引
			let currentVideoIndex = this.vodList[currentSwiperIndex].videoIndex;
			uni.$emit('videoIndexChange', currentVideoIndex)
			// 设置移动时的透明度效果为开启状态
			this.moveOpacity = true;
			// 遮罩层计数加 1
			this.shadeNum++;
			// 关闭定位点显示
			this.openSpot = false;
			// 开启视频自动播放
			this.autoplayVideo = true;

			// 计算总播放列表长度除以 3 的余数
			let remainder = this.totalPlayList.length % 3;

			// 判断当前视频索引是否为 0 或者当前视频索引加 1 等于总播放列表长度减去余数，并且遮罩层计数大于 1
			if ((currentVideoIndex === 0 || currentVideoIndex + 1 === this.totalPlayList.length - remainder) && this
				.shadeNum > 1) {
				// 显示遮罩层
				this.showShade = true;
			}

			// 更新当前滑动索引
			this.changeIndex = currentSwiperIndex;
			// 更新当前视频索引
			this.vodCurIndex = currentVideoIndex;
			console.log('[video-v] changeSwiper end, changeIndex:', this.changeIndex, 'vodCurIndex:', this.vodCurIndex, 'showShade:', this.showShade);

			// 获取视频切片列表
			this.getVodSliceList();
			// 获取视频信息
			this.getVodInfo();
		},
		/* 要播放视频的下标 */
		swiperVod(ev) {
			console.log('[video-v] swiperVod start, openSpot:', this.openSpot, 'vodCurIndex:', this.vodCurIndex, 'changeIndex:', this.changeIndex);
			// 如果定位点处于打开状态，则直接返回，不执行后续操作
			if (this.openSpot) {
				console.log('[video-v] swiperVod openSpot=true, return');
				return;
			}

			// 重置数据，具体逻辑由 resetData 方法实现
			this.resetData();

			// 关闭移动时的透明度效果
			this.moveOpacity = false;
			// 重置遮罩层计数为 0
			this.shadeNum = 0;
			// 隐藏遮罩层
			this.showShade = false;

			// 将最终列表的数据赋值到切片列表
			this.setSliceList(this.finalList);

			// 计算总播放列表长度能被 3 整除的最大长度
			let divisibleTotalLength = this.totalPlayList.length - this.totalPlayList.length % 3;

			// 判断当前视频索引是否为 0 或者当前视频索引加 1 大于等于能被 3 整除的最大长度
			if (this.vodCurIndex === 0 || this.vodCurIndex + 1 >= divisibleTotalLength) {
				// 关闭循环播放
				this.circular = false;
				// 计算总视频数能被 3 整除的最大数量
				let appendDivisibleTotal = this.totalvod - this.totalvod % 3;

				// 满足特定条件时，将后续视频添加到视频列表
				if (this.vodCurIndex !== 0 && this.vodList.length === 3 && this.vodCurIndex + 1 >=
					appendDivisibleTotal && this.totalvod > 0) {
					// 截取当前视频索引之后的视频列表
					let remainingVideos = this.totalPlayList.slice(this.vodCurIndex + 1, this.totalPlayList.length);
					// 将截取的视频添加到视频列表
					remainingVideos.forEach(video => {
						this.vodList.push(video);
					});
				}
			} else {
				// 计算开始加载位置能被 3 整除的最大长度
				let loadDivisibleLength = this.beginLoad - this.beginLoad % 3;

				// 判断当前视频索引是否在特定加载范围内
				if (this.beginLoad > 0 && this.vodCurIndex + 1 >= loadDivisibleLength && this.vodCurIndex + 1 <= this
					.beginLoad) {
					// 这里可能后续有处理逻辑，当前为空
				} else {
					// 如果视频列表长度大于 3，将其长度重置为 3
					if (this.vodList.length > 3) {
						this.vodList.length = 3;
					}
					// 重置开始加载位置为 0
					this.beginLoad = 0;
				}

				// 在 DOM 更新后，开启循环播放
				this.$nextTick(() => {
					this.circular = true;
				});
			}

			// 如果总视频数大于 0，根据总播放列表长度和总视频数的关系设置加载显示状态
			if (this.totalvod > 0) {
				this.loadShow = !(this.totalPlayList.length >= this.totalvod);
			}

			// 更新视频索引为当前变更的索引
			this.vodIndex = this.changeIndex;
			console.log('[video-v] swiperVod end, vodIndex:', this.vodIndex, 'circular:', this.circular, 'vodList.length:', this.vodList.length);
			// 开始播放指定索引的视频
			this.swiperPlay(this.vodIndex);

			// 判断当前视频索引加 1 大于等于能被 3 整除的最大长度，且加载未开始
			if (this.vodCurIndex + 1 >= this.totalPlayList.length - this.totalPlayList.length % 3 && !this.loadStart) {
				// 清除之前的加载定时器
				clearTimeout(this.loadTime);
				// 延迟 300 毫秒后触发加载数据事件
				this.loadTime = setTimeout(() => {
					this.$emit("lodData");
				}, 300);
			}
		},
		/* 获取当前视频播放对象 */
		getVodInfo() {
			this.vodList.filter(item => {
				if (this.vodCurIndex == item.videoIndex) {
					this.beforeVodInfo = item;
				}
			});
			uni.setStorageSync('lookVideoDetail', this.beforeVodInfo)
		},
		/* 截取要播放的视频列表 */
		getVodSliceList() {
			// 获取当前要处理的视频在总播放列表中的索引
			let currentVideoIndex = this.vodList[this.changeIndex].videoIndex;

			// 计算截取总播放列表的起始索引，若当前索引减 1 小于 0，则起始索引为 0
			let startIndex = currentVideoIndex - 1 < 0 ? 0 : currentVideoIndex - 1;
			// 计算截取总播放列表的结束索引，为当前索引加 2
			let endIndex = currentVideoIndex + 2;

			// 从总播放列表中截取指定范围的视频项，并进行深拷贝
			let slicedList = JSON.parse(JSON.stringify(this.totalPlayList.slice(startIndex, endIndex)));

			// 遍历截取的视频项列表
			slicedList.forEach((videoItem) => {
				// 根据视频项索引对 3 取余的结果，更新 vodList 中对应位置视频项的索引
				const remainder = videoItem.videoIndex % 3;
				if (remainder === 0) {
					this.vodList[0].videoIndex = videoItem.videoIndex;
				} else if (remainder === 1) {
					this.vodList[1].videoIndex = videoItem.videoIndex;
				} else if (remainder === 2) {
					this.vodList[2].videoIndex = videoItem.videoIndex;
				}
			});

			// 将截取并处理后的列表赋值给 finalList
			this.finalList = slicedList;
		},
		/* swiper播放视频 */
		swiperPlay(newIndex) {
			console.log('[video-v] swiperPlay newIndex:', newIndex, 'swId:', this.swId, 'beforeVodInfo.videoUrl:', this.beforeVodInfo ? this.beforeVodInfo.videoUrl : 'null');
			// 关闭播放开关
			this.playOpen = false;

			// 清除失败重试定时器和重复操作定时器
			clearInterval(this.failTime);
			clearInterval(this.repeatTime);

			// 取消视频静音
			this.muteVideo(false);

			// 尝试创建视频上下文
			const videoContext = uni.createVideoContext('myVideo' + newIndex + this.swId, this);
			console.log('[video-v] swiperPlay videoContext exists:', !!videoContext, 'id:', 'myVideo' + newIndex + this.swId);
			if (videoContext) {
				// 关闭抖动播放效果
				this.shakePlay = false;
				// 设置前一个视频的播放状态为正在播放
				this.beforeVodInfo.vodPaly = true;
				// 调用播放视频的方法
				this.videoPlay(newIndex);
			} else {
				console.error('[video-v] swiperPlay videoContext is null');
			}

			// 重置指定索引菜单的点赞特效
			if (this.$refs["menuRef" + newIndex + ''] && this.$refs["menuRef" + newIndex + ''].length > 0) {
				this.$refs["menuRef" + newIndex + ''][0].likeeffect = null;
			}
		},
		/* 当开始/继续播放时 */
		startPlay(index) {
			console.log('[video-v] startPlay index:', index, 'vodIndex:', this.vodIndex, 'vodCurIndex:', this.vodCurIndex);
			// uni.hideLoading();
			if (this.vodIndex == index) {
				this.playOpen = false;
				if (this.beforeVodInfo.rotateImgShow) {
					this.palyCartoon = true;
				}
				clearInterval(this.failTime);
				clearInterval(this.repeatTime);
			}
		},
		//视频出现缓冲
		bufferVod(index) {
			console.log('[video-v] bufferVod index:', index, 'changeIndex:', this.changeIndex, 'vodIndex:', this.vodIndex, 'bufferShow:', this.bufferShow, 'vodPaly:', this.beforeVodInfo ? this.beforeVodInfo.vodPaly : 'noInfo');
			// console.log('缓冲视频================》'+index)
			// 检查视频列表是否有元素，当前变更索引等于视频索引，缓冲显示为关闭状态，且前一个视频正在播放
			if (this.vodList.length > 0 && this.changeIndex === this.vodIndex && !this.bufferShow && this.beforeVodInfo
				.vodPaly) {
				// 开启播放开关
				this.playOpen = true;
				// 开始调度加载操作
				this.scheduleLoad();
			}
		},
		/* 视频播放失败 */
		errVod(ev, index) {
			console.error('[video-v] errVod index:', index, 'vodIndex:', this.vodIndex, 'changeIndex:', this.changeIndex, 'videoUrl:', this.beforeVodInfo ? this.beforeVodInfo.videoUrl : 'null', 'errDetail:', ev && ev.detail ? JSON.stringify(ev.detail) : 'none');
			// console.log('视频播放失败================》'+index)
			clearTimeout(this.playErrTime);
			this.playErrTime = setTimeout(() => {
				if (this.vodList.length > 0 && this.changeIndex == this.vodIndex && !this.bufferShow && this
					.beforeVodInfo.vodPaly) {
					this.playOpen = true;
					this.palyCartoon = false;
					this.scheduleLoad();
				}
			}, 0x12c);
		},
		/* 视频加载元数据 */
		loadedMetadataVod(ev, index) {
			console.log('[video-v] loadedMetadataVod index:', index, 'duration:', ev && ev.detail ? ev.detail.duration : 'none');
		},
		/* 播放进度变化时触发 */
		timeupdateVod(ev, index) {
			if (this.vodIndex != index) {
				return false;
			}
			// 每 5 秒或首次触发时打印一次，避免日志刷屏
			if (!this.bufferNum || ev.detail.currentTime - this.bufferNum > 5) {
				console.log('[video-v] timeupdateVod index:', index, 'duration:', ev.detail.duration, 'currentTime:', ev.detail.currentTime);
			}
			this.beforeVodInfo.coverOpacity = false;
			if (!this.sliderDrag) {
				this.videoTime = ev.detail.duration;
				this.sliderTime = ev.detail.currentTime;
				this.sliderProgress = ev.detail.currentTime / ev.detail.duration * this.screenWidth;
			}
			if (this.vodList.length > 0) {
				this.bufferNum = ev.detail.currentTime;
				this.bufferShow = false;
				this.beforeVodInfo.loadingShow = false;
				clearTimeout(this.bufferTime);
				clearInterval(this.failTime);
				clearInterval(this.repeatTime);
			}
		},
		/* 显示进度加载动画 */
		scheduleLoad() {
			if (!this.bufferShow) {
				this.bufferShow = true;
				this.bufferTime = setTimeout(() => {
					this.beforeVodInfo.loadingShow = true;
					if (this.playOpen) {
						this.vodLoad();
					}
				}, 2500);
			}
		},
		/* 重新播放视频 */
		vodLoad() {
			/* 视频播放失败，重新播放视频 */
			if (this.playOpen) {
				this.playOpen = false;
				if (this.sliderTime == 0) {
					uni.createVideoContext("myVideo" + this.vodIndex + this.swId, this).stop();
				}
				this.failTime = setTimeout(() => {
					this.playOpen = true;
					this.bufferShow = false;
					this.videoPlay(this.vodIndex);
				}, 1000);
			}
		},
		/* 视频播放结束 */
		endedVod(index) {
			// 打印日志，表明当前视频播放结束，同时显示视频的索引
			console.log('[video-v] endedVod index:', index, 'vodIndex:', this.vodIndex, 'nextPlay:', this.nextPlay, 'new currentIndex:', this.currentIndex);

			// 检查当前视频索引是否与传入的索引一致，并且是否允许播放下一个视频
			if (this.vodIndex === index && this.nextPlay) {
				// 如果当前视频索引小于 2
				if (this.vodIndex < 2) {
					// 视频索引加 1，准备播放下一个视频
					this.vodIndex += 1;
				} else {
					// 若当前视频索引已经达到 2 或更大，将视频索引重置为 0，实现循环播放
					this.vodIndex = 0;
				}
				// 更新当前播放的索引为新的视频索引
				this.currentIndex = this.vodIndex;
			}
		},
		// 根据秒获取时间
		formatSeconds(a) {
			var hh = parseInt(a / 3600);
			var mm = parseInt((a - hh * 3600) / 60);
			if (mm < 10) {
				mm = "0" + mm;
			}
			var ss = parseInt((a - hh * 3600) % 60);
			if (ss < 10) {
				ss = "0" + ss;
			}
			if (hh < 10) {
				hh = hh == 0 ? '' : `0${hh}:`;
			}
			var length = hh + mm + ":" + ss;
			if (a >= 0) {
				return length;
			} else {
				return "00:00";
			}
		},
		/* 进度条滑动 */
		touchmoveSlider(event) {
			// #ifndef APP-NVUE
			let objclientX = event.changedTouches[0].clientX;
			// #endif

			this.sliderDrag = true;
			if (objclientX >= 0 && objclientX <= this.screenWidth - 2) {
				// 清除之前设置的滑块结束计时的定时器
				clearTimeout(this.sliderEndTime);
				// 开启亮度滑块的操作状态
				this.brightSlider = true;
				// 将当前滑块的位置更新为 objclientX 的值
				this.sliderProgress = objclientX;
				// 计算滑块进度占整个滑动范围的比例
				let ratio = this.sliderProgress / (this.screenWidth - 2);
				// 根据比例计算对应的视频时间点，更新滑块对应的视频时间
				this.sliderTime = ratio * this.videoTime;
				// 根据比例计算对应的视频时间点，更新结束时间
				this.endTime = ratio * this.videoTime;
			}
		},
		/* 进度条滑动结束 */
		touchendSlider() {
			this.sliderDrag = false;
			let videoCtx = uni.createVideoContext("myVideo" + this.vodIndex + this.swId, this);
			videoCtx.seek(this.endTime);
			videoCtx.play();
			this.beforeVodInfo.vodPaly = true;
			this.beforeVodInfo.pauseShow = false;
			this.sliderEndTime = setTimeout(() => {
				this.brightSlider = false;
			}, 2000);
		},
		/* 触碰的坐标 */
		vodViewStart(e) {
			// #ifndef APP-NVUE
			this.touchClientY = e.changedTouches[0].clientY;
			// #endif
			// #ifdef APP-NVUE
			this.touchClientY = e.changedTouches[0].screenY;
			// #endif
			console.log('[video-v] vodViewStart touchClientY:', this.touchClientY);
		},

		/* 上下滑动的坐标 */
		vodViewMove(e) {
			// 根据运行环境不同，计算滑动的垂直距离
			// #ifndef APP-NVUE
			this.moveClientY = e.changedTouches[0].clientY - this.touchClientY;
			// #endif
			// #ifdef APP-NVUE
			this.moveClientY = e.changedTouches[0].screenY - this.touchClientY;
			// #endif

			/* 是否透明进度条 */
			// 根据滑动距离判断是否显示透明进度条
			if (this.moveClientY > 0 || this.moveClientY < 0) {
				this.moveOpacity = true;
			} else {
				this.moveOpacity = false;
			}

			// 当加载开启、当前视频索引为 0 且刷新未开启时，处理刷新相关逻辑
			if (this.loadOpen && this.vodCurIndex === 0 && !this.refreshOpen) {
				if (this.moveClientY > 10) {
					this.refreshShow = true;
					// 根据滑动距离更新刷新的透明度和位置
					if (this.moveClientY > 10 && this.moveClientY <= 60 && this.refreshShow) {
						this.refreshOpacity = this.moveClientY / 60;
						this.refreshclientY = this.moveClientY / 2;
					}
				} else {
					this.refreshShow = false;
				}
			}
		},

		/* 滑动结束的坐标 */
		vodViewEnd(e) {
			// 根据运行环境不同，计算滑动的垂直距离
			// #ifndef APP-NVUE
			this.moveClientY = e.changedTouches[0].clientY - this.touchClientY;
			// #endif
			// #ifdef APP-NVUE
			this.moveClientY = e.changedTouches[0].screenY - this.touchClientY;
			// #endif
			console.log('[video-v] vodViewEnd moveClientY:', this.moveClientY, 'refreshShow:', this.refreshShow);

			/* 是否透明进度条 */
			// 滑动结束，关闭透明进度条显示
			this.moveOpacity = false;

			// 当加载开启、当前视频索引为 0 且刷新显示时，处理刷新相关逻辑
			if (this.loadOpen && this.vodCurIndex === 0 && this.refreshShow) {
				if (this.moveClientY > 1 && this.moveClientY < 60 && !this.refreshOpen) {
					// 滑动距离不满足条件，重置刷新的透明度和位置，并延迟隐藏刷新显示
					this.refreshOpacity = 0;
					this.refreshclientY = 0;
					setTimeout(() => {
						this.refreshShow = false;
					}, 300);
				} else {
					if (this.refreshOpen) {
						return;
					}
					// 满足条件，开启刷新并触发刷新数据事件
					this.refreshOpen = true;
					this.$emit("refreshData");
				}
			}
		},

		/* 重置状态数据 */
		resetData() {
			// 重置滑块进度、时间、缓冲等相关数据
			this.sliderProgress = 0;
			this.sliderTime = 0;
			this.videoTime = 0;
			this.endTime = 0;
			this.bufferNum = null;
			this.bufferShow = false;
			this.sliderDrag = false;
			this.brightSlider = false;
			this.sliderEndTime = null;
		},

		// 单击或双击
		handClick(event, index) {
			console.log('[video-v] handClick index:', index, 'vodIndex:', this.vodIndex, 'doubleOpen:', this.doubleOpen);
			if (!this.doubleOpen) {
				// 若双击功能未开启，执行播放点操作
				this.playSpot(index);
				return false;
			}

			const currentTime = new Date().getTime();
			const lastTime = this.lastTapDiffTime;
			this.lastTapDiffTime = currentTime;
			const timeDifference = currentTime - lastTime;

			if (timeDifference < 300) {
				// 两次点击时间间隔小于 300 毫秒，认为是双击
				clearTimeout(this.lastTapTimeoutFunc);
				if (!this.vodList[this.vodIndex].fabulousShow) {
					// 若点赞未显示，触发点赞按钮操作
					this.$refs["menuRef" + this.vodIndex + ''][0].fabulousBtn(index, 0);
				}
				if (!this.doubleHeart) {
					return;
				}
				if (this.doubleOutTime) {
					return;
				}
				this.doubleOutTime = setTimeout(() => {
					const touchY = event.changedTouches[0].clientY;
					const touchX = event.changedTouches[0].clientX;
					const rotationAngles = ["0deg", "15deg", "-15deg"];
					const randomIndex = Math.floor(Math.random() * 3);

					const likeItem = {
						id: this.likeId++,
						width: this.pxToRpx(95) + "rpx",
						height: this.pxToRpx(95) + "rpx",
						top: this.pxToRpx(touchY) - this.pxToRpx(140) + "rpx",
						left: this.pxToRpx(touchX) - this.pxToRpx(47.5) + "rpx",
						rotate: rotationAngles[randomIndex],
						className: '',
						isShow: true
					};
					// 添加点赞元素到列表
					this.likeList.push(likeItem);
					// 为点赞元素添加动画类
					this.doubleAddClass(likeItem.id);
					this.doubleOutTime = null;
					clearTimeout(this.clearDoubleTime);
					this.clearDoubleTime = setTimeout(() => {
						// 延迟清空点赞列表
						this.likeList = [];
					}, 1500);
					// 触发双击事件
					this.$emit("doubleClick", this.vodList[this.vodIndex]);
				}, 50);
			} else {
				// 两次点击时间间隔大于等于 300 毫秒，认为是单击，延迟执行播放点操作
				this.lastTapTimeoutFunc = setTimeout(() => {
					this.playSpot(index);
				}, 300);
			}
		},

		/* px转换rpx */
		pxToRpx(px) {
			// 根据屏幕宽度将 px 转换为 rpx
			return 750 * Number.parseInt(px) / this.screenWidth;
		},
		/* 双击点赞添加移除动画 */
		doubleAddClass(id) {
			// 延迟 500 毫秒后执行以下操作
			setTimeout(() => {
				// 遍历点赞列表
				this.likeList.forEach((likeItem, index) => {
					// 找到与传入 id 匹配的点赞项
					if (likeItem.id === id) {
						// 为匹配的点赞项添加移除动画的类名
						this.likeList[index].className = 'doubleImageRemove';
					}
				});

				// 再延迟 400 毫秒后执行以下操作
				setTimeout(() => {
					// 再次遍历点赞列表
					this.likeList.forEach((likeItem, index) => {
						// 找到与传入 id 匹配的点赞项
						if (likeItem.id === id) {
							// 隐藏匹配的点赞项
							likeItem.isShow = false;
						}
					});
				}, 400);
			}, 500);
		},
		/* 长按当前视频回调 */
		longpress(item) {
			this.$emit('longpress', item);
		},
		/* 操作当前数据 */
		handleInfo(obj, key) {
			this.totalPlayList.filter((item, index) => {
				if (item.videoIndex == obj.videoIndex) {
					this.$set(this.totalPlayList, index, obj);
				}
			});
			this.vodList.filter((item, index) => {
				if (item.videoIndex == obj.videoIndex) {
					this.$set(this.vodList, index, obj);
				}
			});
			if (key == 1) {
				//点赞
				this.likeFn(obj);
			}
			if (key == 2) {
				//收藏
				this.collectFn(obj);
			}
			if (key == 4) {
				const video = this.$refs[`myVideo${this.changeIndex}`][0];
				if (obj.isSpeed) {
					video.playbackRate(2);
				} else {
					video.playbackRate(1);
				}
			}
		},
		//记录时长
		timeTask(index, time) {
			const data = getApp().globalData.seeVodList[index];
			// this.$api.dramaTaskHandle({
			// 	dramaDetailsId: data.dramaDetailsId,
			// 	dramaId: data.dramaId,
			// 	second: time
			// });
		},
		//切换视频
		changeTask(index) {
			const data = getApp().globalData.seeVodList[index];
			getApp().globalData.seeVodIndex = index;
			if (data && !this.changePlayType) {
				this.changePlayType = true;
				setTimeout(() => {
					this.changePlayType = false;
				}, 3000);
				this.$api.dramaLook({
					dramaDetailsId: data.dramaDetailsId
				});
			}
		},
		//开始计时任务
		startStsk(index) {
			const data = getApp().globalData.seeVodList[index];
			if (data) {
				if (this.tasktime >= 10) {
					//记录任务
					this.timeTask(index, this.tasktime);
				}
				if (this.playInfo != index) {
					this.tasktime = 0;
				}
				this.playInfo = index;
				clearInterval(this.taskTimer);
				if (data.videoUrl) {
					this.taskTimer = setInterval(() => {
						this.tasktime++;
					}, 1000);
				}
			}
		},
		//视频点赞
		likeFn(item) {
			if (item.selectWork) {
				this.$api.dramaLike({
					dramaDetailsId: item.dramaDetailsId
				});
			} else {
				this.$api.videoLike({
					id: item.id
				});
			}
		},
		//视频收藏
		collectFn(item) {
			if (item.selectWork) {
				this.$api.dramaCollect({
					dramaId: item.dramaId
				});
			} else {
				this.$api.videoCollect({
					id: item.id
				});
			}
		},
		//分享视频
		share(type) {
			console.log("富聪聪", "快来跟我一起看短剧领金币吧！", this.versionInfo.url, this.versionInfo.appUrl);

			// uni.request({
			// 	url: this['versionInfo'].url,
			// 	method: 'GET',
			// 	responseType: 'arraybuffer',
			// 	success: (res) => {
			// 		console.log(res);
			// 		const base64 = `data:image/${type};base64,${uni.arrayBufferToBase64(res.data)}`
			// 		console.log(base64);
			// 	},
			// 	fail: (err) => {},
			// })

			if (type == 1) {
				// //分享好友
				// uni.share({
				// 	provider: "weixin",
				// 	scene: "WXSceneSession",
				// 	type: 0,
				// 	href: this['versionInfo'].appUrl,
				// 	title: "富聪聪",
				// 	summary: "快来跟我一起看短剧领金币吧！",
				// 	imageUrl: this['versionInfo'].url,
				// 	success: function(res) {
				// 		console.log("success:" + JSON.stringify(res));
				// 	},
				// 	fail: function(err) {
				// 		console.log("fail:" + JSON.stringify(err));
				// 	}
				// });

				window.rh_android.shareForFriends("红樱桃", "快来跟我一起看短剧领金币吧！", this.versionInfo.url, this.versionInfo
					.appUrl);
			}
			if (type == 2) {
				window.rh_android.shareForCircleFriends("红樱桃", "快来跟我一起看短剧领金币吧！", this.versionInfo.url, this
					.versionInfo
					.appUrl);
			}
		},

		//购买本集
		bayThis(item) {
			const info = getApp().globalData.vodinfo;
			const data = {
				name: ` ${info.title} 第${item.gather}集`,
				price: item.price,
				img: item.titleImg,
				detailId: item.dramaDetailsId,
				id: info.dramaId,
				text: item.content,
				type: 3
			};
			uni.setStorageSync('Buy_Drama', data);
			this.$utils.toPage('/pages/index/buy-drama');
		},
		//开通会员
		toMember() {
			this.$utils.toPage('/pages/mine/createVip');
		},
		backUrl() {
			const index = uni.getStorageSync('currentVideo')
			getApp().globalData.seeVodIndex = index - 1
			uni.reLaunch({
				url: `/pages/index/video-see?pageNum=${index-1}`
			})
		},
		//金币解锁
		lookAd2(item, index) {
			this.jiesuoIndex = item.gather
			//开启视频 
			this.$api.unlockByGold({
				dramaId: item.dramaId
			}).then(res => {
				this.closeReward()
			})
		},
		/* 看小视频解锁*/
		lookAd(item, index) {
			this.jiesuoIndex = item.gather
			// this['urlCallback'].extra = {
			// 	type: 2,
			// 	value: item.dramaDetailsId,
			// 	item
			// }

			// console.log(this['urlCallback']);
			//开启视频 
			// this.videoUnlockAndroidCallBack('fdf8b9db8760ec02e3fa6ad3ad8c2a79_8357689_1745559149231', 0)
			this.$api.checkAdsNums().then(res => {
				window.rh_android.showAd(this.userInfo.id, JSON.stringify({
					type: 1,
					value: item.dramaDetailsId
				}), 1);
			})
		},
		/* 滑动小视频弹出广告*/
		slideShowAd() {
			// this['urlCallback'].extra = {
			// 	type: 2,
			// 	value: item.dramaDetailsId,
			// 	item
			// }

			// console.log(this['urlCallback']);
			//开启视频 
			this.$api.checkAdsNums().then(res => {
				window.rh_android.showAd(this['userInfo'].id, JSON.stringify({
					type: 1,
					value: 7
				}), 7)
			})
		},
		slideShowAdCallBack(lookOk, callback) {
			console.log("金币回调", lookOk, callback);
			// 广告回调
			this.$api.getADResult({
				transId: callback
			}).then(res => {
				if (res.data) {
					res.data.money

					this.$emit('callback', res.data.money)
				}
			})
		},
		/* 激励视频钩子*/
		videoUnlockAndroidCallBack(lookOk, callback) {
			setTimeout(() => {
				//获取广告结果
				this.$api.taKuResultByTransId({
					transId: lookOk
				}).then(res => {
					if (res.data.money > 0) {
						this.showRewardGold = true
						this.showRewardNum = res.data.money
					} else {
						this.showRewardGold = false
					}
					this.isShowReward = true
				})
			}, 2000)
			return 'videoUnlockAndroidCallBack调用成功';
		},
		//推出界面销毁计时器
		unload() {
			clearInterval(this.taskTimer);
			if (this.tasktime >= 10) {
				//记录任务
				this.timeTask(index, this.tasktime);
			}
		}
	}
};
import {
	request,
	get,
	post,
	put,
	del,
	patch,
	hideGet,
	hidePost,
	silentGet,
	silentPost
} from "./request.js"
import store from "@/store/switch.js"
import localData from "@/common/api/localData.js"

export default {
	//短剧部分
	//获取验证码
	sendCode(param) {
		return post('common/sendCode', param);
	},
	//登录
	login(param) {
		return post('platform/user/login', param);
	},
	//短剧分类
	getCategory(param) {
		if (store.state.isShow) {
			return hideGet('platform/drama/getCategory', param);
		} else {
			return localData.getCategory()
		}
	},
	//首页信息
	homePage(param) {
		if (store.state.isShow) {
			return hideGet('platform/drama/homePage', param);
		} else {
			return localData.homePage(param)
		}
	},
	//短剧详细信息集合
	getDetailsList(param) {
		if (store.state.isShow) {
			return get('platform/drama/getDetailsList', param);
		} else {
			return localData.getDetailsList(param)
		}
	},
	//搜索短剧
	dramaQuery(param) {
		if (store.state.isShow) {
			return get('platform/drama/query', param);
		} else {
			return localData.dramaQuery(param)
		}
	},
	//短剧点赞
	dramaLike(param) {
		return hideGet('platform/dramaLike/like', param);
	},
	//短剧收藏
	dramaCollect(param) {
		return hideGet('platform/dramaCollect/collect', param);
	},
	//查看某一集短剧
	dramaLook(param) {
		return hideGet('platform/dramaLook/info', param);
	},
	//获取视频列表
	getVideoList() {
		if (store.state.isShow) {
			return hideGet('platform/video/getList');
		} else {
			return localData.getVideoList()
		}
	},
	//获取视频详情
	getVideoInfo(param) {
		return hideGet('platform/video/getInfo', param);
	},
	//获取视频详情（静默：失败不弹 toast，仅用于后台增强请求）
	getVideoInfoSilent(param) {
		return silentGet('platform/video/getInfo', param);
	},
	//视频点赞
	videoLike(param) {
		return hideGet('platform/video/like', param);
	},
	//视频收藏
	videoCollect(param) {
		return hideGet('platform/video/collection', param);
	},
	//举报视频
	reportVideo(param) {
		return post('platform/video/report', param);
	},
	//修改用户信息
	userModify(param) {
		return post('platform/user/modify', param);
	},
	//短剧浏览记录
	getRecordList(param) {
		return get('platform/dramaLook/getRecord', param);
	},
	//修改用户信息
	removeDramaRecord(param) {
		return get('platform/dramaLook/remove', param);
	},
	//短剧点赞列表
	getMyLikeDramaList(param) {
		return get('platform/dramaLike/myLike', param);
	},
	//看看点赞列表
	getMyLikeVideoList(param) {
		return get('platform/video/myLike', param);
	},
	//短剧收藏列表
	getCollectDramaList(param) {
		return get('platform/dramaCollect/myCollect', param);
	},
	//看看收藏列表
	getCollectVideoList(param) {
		return get('platform/video/myCollect', param);
	},
	//获取视频详情
	getVideoDetail(param) {
		return get('platform/video/getInfo', param);
	},
	//获取我的邀请记录
	getMyInvitationList() {
		return get('platform/invitation/myInvitation');
	},
	//填写邀请码
	setInvitationCode(param) {
		return get('platform/invitation/setInvitationCode', param);
	},
	//获取官方客服二维码
	getOfficialCode() {
		return get('common/getOfficialCode');
	},
	//提交反馈
	feedBack(param) {
		return post('platform/feedback/add', param);
	},
	//获取会员列表
	getVipList() {
		return get('platform/vip/list');
	},
	//获取版本号
	getVersion() {
		return get('common/getVersion');
	},
	//申请注销账号
	applyDestroy() {
		return get('platform/user/applyDestroy');
	},
	//获取隐私政策
	getAgreement(param) {
		return get('platform/agreement/get', param);
	},
	//获取隐私政策
	withdrawalTab(param) {
		return get('common/withdrawalTab', param);
	},
	//兑换比例
	getConvertRatio(param) {
		return get('common/getConvertRatio', param);
	},
	//兑换现金
	goldCoinToCash(param) {
		return get('bill/goldCoinToCash', param);
	},
	//新人任务
	getnewPeopleTask(param) {
		return get('platform/newTask/newPeople', param);
	},
	//每日任务
	getdailyTask(param) {
		return get('platform/dailyTask/daily', param);
	},
	//每日签到领金币
	dailySignToGoldCoins(param) {
		return get('platform/dailyTask/dailySignToGoldCoins', param);
	},
	//看广告领金币
	adRewardToCoin(param) {
		return get('platform/dailyTask/adRewardToCoin', param);
	},
	//见面领现金
	meetToReceiveCash(param) {
		return get('platform/newTask/meetToReceiveCash', param);
	},
	//新人签到领金币
	newcomersSignToGoldCoins(param) {
		return get('platform/newTask/newcomersSignToGoldCoins', param);
	},
	//账号密码登录
	accountLogin(param) {
		return post('platform/user/accountLogin', param);
	},
	//注册账号
	registerAccount(param) {
		return post('platform/user/registerAccount', param);
	},
	//修改密码
	modifyPassword(param) {
		return post('platform/user/modifyPassword', param);
	},
	//查询是否已经绑定提现账号
	withdrawalUser(param) {
		return hideGet('bill/withdrawalUser', param);
	},
	//绑定微信提现账号
	bindWxWithdrawalUser(param) {
		return get('bill/bindWxWithdrawalUser', param);
	},
	//绑定支付宝提现账号
	bindAliWithdrawalUser(param) {
		return get('bill/bindAliWithdrawalUser', param);
	},
	//申请提现·
	applyWithdrawal(param) {
		return get('bill/applyWithdrawal', param);
	},
	//现金明细·
	getBillCashList(param) {
		return get('bill/billCash', param);
	},
	//金币明细·
	getBillGoldList(param) {
		return get('bill/billGold', param);
	},
	//短剧任务·
	dramaTaskHandle(param) {
		return get('platform/drama/taskHandle', param);
	},
	//视频任务·已废弃
	videoTaskHandle(param) {
		return get('platform/video/taskHandle', param);
	},
	//宝箱信息·
	getBoxInfo(param) {
		return get('platform/treasureBox/get', param);
	},
	//是否可以领取宝箱·
	receiveBox(param) {
		return get('platform/treasureBox/receive', param);
	},
	//购买会员生成订单·
	createVipOrder(param) {
		return post('platform/vip/createVipOrder', param);
	},
	//购买短剧生成订单·
	createDramaOrder(param) {
		return post('platform/drama/createDramaOrder', param);
	},
	//微信统一下单·
	wxCreateOrder(param) {
		return post('platform/wx/createOrder', param);
	},
	//支付宝统一下单·
	aliCreateOrder(param) {
		return get('platform/ali/createOrder', param);
	},
	//购买会员记录·
	getBayVipRecord(param) {
		return get('platform/order/buyVipList', param);
	},
	//查看支付是否成功·
	getPayInfoByOrderNo(param) {
		return get('platform/order/getPayInfoByOrderNo', param);
	},
	//获取观看广告唯一id·
	getADId(scene) {
		return get('/publicize/look', {
			scene
		});
	},
	//查看广告结果
	getADResult(param) {
		return get('/publicize/resultByTransId', param);
	},
	//TaKu查看广告结果
	taKuResultByTransId(param) {
		return get('/publicize/taKuResultByTransId', param);
	},
	//uni-ad 激励视频回调（替代 Taku 服务端回调）
	uniAdCallback(param) {
		return get('/publicize/uniAdCallback', param);
	},
	//看广告前回调
	lookAdCheck() {
		return get('/publicize/lookAdCheck');
	},
	//申请成为代理
	commanderApply(param) {
		return post('/commander/apply', param);
	},
	//获取地址列表
	getAddress() {
		return get('/receivingAddress/getList');
	},
	//新增地址
	addAddress(param) {
		return post('/receivingAddress/add', param);
	},
	//修改地址
	editAddress(param) {
		return post('/receivingAddress/modify', param);
	},
	//删除地址
	delAddress(param) {
		return get('/receivingAddress/remove', param);
	},
	//代理申请提现
	withdrawalCommander(param) {
		return get('/bill/withdrawalCommander', param);
	},
	//商品列表
	getGoods(param) {
		return get('platform/goods/list', param);
	},
	//购买商品
	buyGoods(param) {
		return post('platform/goods/buy', param);
	},
	//金叶明细
	billLeaf(param) {
		return get('/bill/billLeaf', param);
	},
	//商品订单
	goodsOrderList(param) {
		return get('platform/goods/goodsOrderList', param);
	},
	//确认收货
	takeOver(param) {
		return get('platform/goods/takeOver', param);
	},

	// 游戏部分
	//微信登录
	wxLogin(param) {
		return post('platform/user/wxLogin', param);
	},
	//获取用户信息
	getInfo(param) {
		return get('platform/user/getInfo', param);
	},
	//注销账户
	cancelAccount(param) {
		return get('platform/user/applyDestroy', param);
	},
	//广播信息
	broadcast(param) {
		return get('platform/hrl/get', param);
	},
	//协议信息
	agreement(param) {
		return get('platform/agreement/get', param);
	},
	//歌曲信息
	songsInfo(param) {
		return get('platform/musicStore/get', param);
	},
	//选择答案
	songsAnswer(param) {
		return post('platform/musicStore/answer', param);
	},
	//微信提现
	wxWithdrawal(param) {
		return get('platform/bill/applyWithdrawal', param);
	},
	//红包提现
	redWithdrawal(param) {
		return get('platform/bill/redWithdrawalId', param);
	},
	//红包提现条件
	redWithdrawalCondition(param) {
		return get('platform/bill/redWithdrawalNeed', param);
	},
	//微信收入明细
	wxIncomeList(param) {
		return get('platform/bill/wxIncome', param);
	},
	//帮助中心
	helpCenter(param) {
		return get('platform/helpCenter/get', param);
	},
	//排行榜信息
	rankingList(param) {
		return get('/ranking/get', param);
	},
	//登录日志
	loginLogo(param) {
		return post('platform/user/loginLog', param);
	},
	//关于我们
	aboutUs(param) {
		return get('platform/aboutUs/get', param);
	},
	//广告结果
	adResult(param) {
		return get('/publicize/taKuResultByTransId', param);
	},
	//红包提现记录
	redWalletRecord(param) {
		return get('platform/bill/withdrawalRedRecord', param);
	},
	// 通关奖励
	passLevel(param) {
		return get('platform/musicStore/needMin', param);
	},
	//语音接口
	playSound(param) {
		return get('platform/voice/getInfo', param);
	},
	//删除用户
	deleteUser(param) {
		return get('/common/deleteUser', param);
	},
	//绑定微信
	bindwx(param) {
		return get('platform/user/bindWx', param);
	},
	queryAsdNums(param) {
		return get('/publicize/getTaKuLookCount', param);
	},
	//检查广告次数显示
	// checkAdsNums(param) {
	// 	return get('/publicize/lookAdCount', param);
	// },
	//调广告之前检查
	checkAdsNums(param) {
		return get('/publicize/checkIsLookAd', param);
	},
	//支付宝提现选项
	zfbWithCardList(param) {
		return get('platform/bill/getWithdrawalCard', param);
	},
	//绑定修改支付宝
	bindZfb(param) {
		return post('platform/user/modifyZfb', param);
	},
	//金币解锁短剧
	unlockByGold(param) {
		return get('platform/drama/moneyUnlock', param);
	},
	// ==================== 视频系列 ====================
	//视频系列列表
	getVideoSeriesList(param) {
		return get('platform/videoSeries/list', param);
	},
	//视频系列选集列表
	getVideoSeriesEpisodes(param) {
		return get('platform/videoSeries/episodes', param);
	},
	//金币解锁视频单集
	videoSeriesMoneyUnlock(param) {
		return get('platform/videoSeries/moneyUnlock', param);
	},
	//视频分类列表
	getVideoSeriesCategories(param) {
		return get('platform/videoSeries/categories', param);
	},
	//按视频名称模糊搜索系列
	searchVideoSeries(param) {
		return get('platform/videoSeries/search', param);
	},
	//随机 Feed：每个系列随机取一集，用于无限滚动循环播放
	getVideoSeriesRandomFeed(param) {
		return get('platform/videoSeries/randomFeed', param);
	},
	//获取短剧分类
	getShotType(param) {
		if (store.state.isShow) {
			return get('platform/dramaCategory/fDramaCategoryList', param);
		} else {
			return localData.getShotType(param)
		}
	},
	//获取短剧二级分类
	getShotNextType(param) {
		if (store.state.isShow) {
			return get('platform/dramaCategory/SDramaCategoryList', param);
		} else {
			return localData.getShotNextType(param)
		}
	},
	//获取所有分类
	getAllTypes(param) {
		return get('platform/dramaCategory/dramaCategoryList', param);
	},
	//短视频红包信息
	shortWalletInfo(param) {
		return get('platform/circleRedEnvelopes/info', param);
	},
	//领取短视频红包
	receiveShortWallet(param) {
		return get('platform/circleRedEnvelopes/receive', param);
	},
	//获取用户抽奖次数
	userTurnableNums(param) {
		return get('platform/turntable/getDrawsNums', param);
	},
	// 获取转盘奖品
	awardsList(param) {
		return get('platform/turntable/getTurntablePrizesList', param);
	},
	//中奖名单
	winningList(param) {
		return get('platform/turntable/listOfWinners', param);
	},
	//获取抽奖结果
	turntableResult(param) {
		return get('platform/turntable/getLotteryResult', param);
	},
	//申请代理
	applyAgent(param) {
		return post('platform/popularize/createAgent', param);
	},
	//代理申请信息
	applyAgentInfo(param) {
		return get('platform/popularize/getMyApplyAgentInfo', param);
	},
	//收益信息
	incomeInfo(param) {
		return get('platform/popularize/gainsInfo', param);
	},
	//商品分类
	getGoodsTypes(param) {
		return get('platform/goods/getGoodsTypeList', param);
	},

	//获取文章列表
	getArticlePage(param) {
		return post('platform/article/getArticlePage', param);
	},
	//获取文章详细
	getArticleInfo(param) {
		return get('platform/article/getArticleInfo', param);
	},
	//获取评论列表
	getArticleCommentPage(param) {
		return get('platform/article/getArticleCommentPage', param);
	},
	//点赞
	addLikeArticle(param) {
		return get('platform/article/addLikeArticle', param);
	},
	//评论
	addArticleComment(param) {
		return post('platform/article/addArticleComment', param);
	},

	// ==================== 投票功能 ====================
	// 获取投票活动列表
	getVoteList() {
		return get('platform/vote/list');
	},
	// 获取投票活动详情
	getVoteDetail(param) {
		return get('platform/vote/detail', param);
	},
	// 投票（单选）
	vote(param) {
		return post('platform/vote/vote', param);
	},
	// 投票（多选）
	voteMultiple(activityId, optionIds) {
		return post(`platform/vote/voteMultiple?activityId=${activityId}`, optionIds);
	},
	// 取消投票（单选）
	cancelVote(param) {
		return post('platform/vote/cancelVote', param);
	},
	// 取消投票（多选）
	cancelVoteMultiple(param) {
		return post('platform/vote/cancelVoteMultiple', param);
	},


	// ==================== 活动报名功能 ====================
	// 获取活动列表
	getActivityList(param) {
		return get('platform/activity/list', param);
	},
	// 获取活动详情
	getActivityDetail(param) {
		return get('platform/activity/detail', param);
	},
	// 报名活动
	signupActivity(param) {
		return post('platform/activity/signup', param);
	},
	// 取消报名
	cancelSignup(param) {
		return get('platform/activity/cancelSignup', param);
	},
	// 我的报名记录
	getMySignupList(param) {
		return get('platform/activity/mySignup', param);
	},



}
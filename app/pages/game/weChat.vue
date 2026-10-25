<template>
	<view class="main">
		<view class="top">
			<view class="item">
				<uni-icons @tap="back()" type="left" size="24" color="#000"></uni-icons>
				<span style="margin-left: 260rpx;">红包群</span>
			</view>
		</view>
		<!-- 群聊列表 -->
		<view v-show="!isShowChatDetail" class="group-chat">
			<view @tap="chooseGroup(item, index)" class="group-chat-item" v-for="(item, index) in chatList"
				:key="index">
				<view class="group-chat-item1">
					<image v-for="(item1, index1) in item.groupAvatars" style="width: 30%" :src="item1" mode="widthFix">
					</image>
				</view>
				<view class="group-chat-item2">
					<span style="font-size: 36rpx; font-weight: bold; color: #000000">{{ item.title }}</span>
					<span style="font-size: 24rpx; color: #c8c8c8; margin-top: 10rpx">
						<span v-if="index == 0"
							style="font-size: 24rpx; margin-right: 15rpx; color: #ff3638">红包助手:</span>
						<span v-if="index == 1"
							style="font-size: 24rpx; margin-right: 15rpx; color: #ff3638">[红包]:</span>
						{{ item.latestMessage }}
					</span>
				</view>
				<view class="group-chat-item3" v-if="item.isShowUnread">
					<span>{{ item.unreadMessage }}</span>
				</view>
				<view class="group-chat-line"></view>
			</view>
		</view>
		<!-- 群聊聊天 -->
		<view v-show="isShowChatDetail" class="group-chat-page">
			<view class="group-chat-page1">
				<view class="group-chat-page1-back" @tap="showChatList()">
					<image style="width: 15rpx" src="/static/chats/2.png" mode="widthFix"></image>
					<span>返回</span>
				</view>
				<span style="font-size: 30rpx; color: #000000">{{ chatListData.title }}（372）</span>
			</view>
			<view class="group-chat-page2">
				<scroll-view :scroll-top="scrollTop" :scroll-with-animation="true" scroll-y="true" style="height: 83vh">
					<view class="group-chat-content" :class="{ groupChatContentSelft: item.isSelf }"
						v-for="(item, index) in chatListData.chatData" :key="index">
						<image style="width: 76rpx; height: 76rpx; border-radius: 50%" :src="item.avatar"></image>
						<view class="group-chat-content-info" :class="{ groupChatContentInfos: item.isSelf }">
							<span style="font-size: 24rpx; color: #666666">{{ item.name }}</span>
							<view class="group-chat-content-info-text" v-if="item.type == 'text'">
								<span>{{ item.message }}</span>
							</view>
							<view @tap="redWalletClick(item)" v-else class="group-chat-content-info-wallet"></view>
						</view>
					</view>
				</scroll-view>
			</view>
			<view class="group-chat-page3">
				<view class="group-chat-input">
					<input v-model="sendMessage" type="text" />
				</view>
				<view @tap="sendMessageBtn()" class="group-chat-send">
					<view v-show="!isCanSend" class="disabled"></view>
				</view>
			</view>
		</view>
		<view class="bottom-bar" v-if="false">
			<view class="bottom-bar-body">
				<image @tap="chooseBar(0)" style="width: 112rpx" src="/static/index/6.png" mode="widthFix"></image>
			</view>
		</view>
		<!-- 红包遮罩层 -->
		<view v-if="isShowRedWallet" class="redWalletPop">
			<view class="redWalletPop-title">
				<image style="width: 66rpx; height: 66rpx; margin-right: 20rpx" :src="selectedUser.avatar" mode="">
				</image>
				<span>{{ selectedUser.name }}发出的红包</span>
			</view>
			<view class="redWalletPop-title2">
				<span>恭喜发财 大吉大利</span>
			</view>
			<view @tap="watchAds()" class="redWalletPop-bg">
				<view @tap="isShowRedWallet = false" class="redWalletPop-close"></view>
			</view>
		</view>
		<!-- 红包奖励 -->
		<view v-if="isShowRedWalletReward" class="redWalletRewardPop">
			<view class="redWalletRewardPop-bg">
				<view class="redWalletRewardPop-body">
					<view class="body-title">
						<image style="width: 66rpx; height: 66rpx; margin-right: 20rpx" :src="selectedUser.avatar"
							mode=""></image>
						<span>{{ selectedUser.name }}发出的红包</span>
					</view>
					<view class="body-money">
						<span>
							{{ adsResultData.money }}
							<span style="font-size: 50rpx">金币</span>
						</span>
					</view>
				</view>
			</view>
			<view @tap="receiveMoney()" class="redWalletRewardPop-close"></view>
		</view>
		<!-- 中间的微信图标容器 -->
		<view class="center-container">
			<image v-for="(icon, index) in wxIcons" :key="icon.id" src="/static/index/wallet.png"
				class="floating-wx-icon" :class="{ show: icon.isVisible, flying: icon.isFlying }" :style="{
					left: icon.left + 'rpx',
					top: icon.top + 'rpx',
					transitionDelay: icon.isFlying ? index * 100 + 'ms' : '0ms',
					transform: getTransform(icon),
					opacity: icon.isVisible && !icon.arrived ? 1 : 0
				}"></image>
		</view>
	</view>
</template>

<script>
	import UniAds from '@/common/uniad-sdk.js'

	export default {
		data() {
			return {
				userInfoData: {},
				rewardedAdpid: '1819020042',
				takuInitialized: false,
				adDidReward: false,
				rewardedAdLoaded: false,
				isShowChatDetail: false,
				sendMessage: '',
				isShowRedWallet: false,
				isShowRedWalletReward: false,
				selectedGroupIndex: -1,
				isBack: false,
				adsTimer: null,
				adsSecond: 60,
				selectedUser: null,
				adsResultData: null,
				sendMessageTimer: null, //自动发送消息计时器
				sendRedWalletTimer: null, //自动发送红包计时器
				isCanSend: false,
				chatListData: [],
				scrollTop: 0,
				getedRedMoney: {
					text: '',
					translateY: 0,
					opacity: 0
				},
				wxIcons: [],
				isAnimating: false,
				areaWidth: 300,
				areaHeight: 300,
				iconSize: 40,
				iconCount: 10,
				idCounter: 0,
				// 散落的固定位置数组
				fixedPositions: [{
						x: 50,
						y: 65
					},
					{
						x: 180,
						y: 45
					},
					{
						x: 240,
						y: 120
					},
					{
						x: 60,
						y: 180
					},
					{
						x: 150,
						y: 90
					},
					{
						x: 200,
						y: 200
					},
					{
						x: 90,
						y: 240
					},
					{
						x: 120,
						y: 150
					},
					{
						x: 220,
						y: 160
					},
					{
						x: 30,
						y: 120
					}
				],
				// 用于强制触发滚动
				forceScrollTimer: null,
				chatList: [{
						title: '新人好礼群',
						unreadMessage: '99+',
						isShowUnread: true,
						latestMessage: '新人进群领红包',
						groupAvatars: [
							'/static/avatars/1.jpg',
							'/static/avatars/2.jpg',
							'/static/avatars/3.jpg',
							'/static/avatars/4.jpg',
							'/static/avatars/5.jpg',
							'/static/avatars/6.jpg',
							'/static/avatars/7.jpg',
							'/static/avatars/8.jpg',
							'/static/avatars/9.jpg'
						],
						chatData: [{
								name: '冷清秋',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '无愧任何分合',
								isSelf: false,
								avatar: '/static/avatars/8.jpg',
								message: '/static/chats/4.png',
								type: 'image'
							},
							{
								name: '韶华一世轻浮渡',
								isSelf: false,
								avatar: '/static/avatars/9.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '聆晚雾',
								isSelf: false,
								avatar: '/static/avatars/4.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '墙倒众人推',
								isSelf: false,
								avatar: '/static/avatars/5.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '飞龙成景',
								isSelf: false,
								avatar: '/static/avatars/2.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							}
						]
					},
					{
						title: '同城群',
						unreadMessage: '99+',
						isShowUnread: true,
						latestMessage: '恭喜发财，大吉大利',
						groupAvatars: [
							'/static/avatars/1.jpg',
							'/static/avatars/2.jpg',
							'/static/avatars/3.jpg',
							'/static/avatars/4.jpg',
							'/static/avatars/5.jpg',
							'/static/avatars/6.jpg',
							'/static/avatars/7.jpg',
							'/static/avatars/8.jpg',
							'/static/avatars/9.jpg'
						],
						chatData: [{
								name: '扰川',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '爱のc小调',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '/static/chats/4.png',
								type: 'image'
							},
							{
								name: '旧春光',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '相思赋予谁',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '青桅',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '笙歌君独幽',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '浅暮雪',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '入画浅相思',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '念得小柔',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '枯蝶',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							}
						]
					},
					{
						title: '拼手速群',
						unreadMessage: '99+',
						isShowUnread: true,
						latestMessage: '超大红包，拼手速抢',
						groupAvatars: [
							'/static/avatars/1.jpg',
							'/static/avatars/2.jpg',
							'/static/avatars/3.jpg',
							'/static/avatars/4.jpg',
							'/static/avatars/5.jpg',
							'/static/avatars/6.jpg',
							'/static/avatars/7.jpg',
							'/static/avatars/8.jpg',
							'/static/avatars/9.jpg'
						],
						chatData: [{
								name: '尺素',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '和海看日出',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '/static/chats/4.png',
								type: 'image'
							},
							{
								name: '而长终',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '原谅你',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '舊事別提',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '誮惜颜',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '梦中有个人',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							}
						]
					},
					{
						title: '拼手速群',
						unreadMessage: '99+',
						isShowUnread: true,
						latestMessage: '超大红包，拼手速抢',
						groupAvatars: [
							'/static/avatars/1.jpg',
							'/static/avatars/2.jpg',
							'/static/avatars/3.jpg',
							'/static/avatars/4.jpg',
							'/static/avatars/5.jpg',
							'/static/avatars/6.jpg',
							'/static/avatars/7.jpg',
							'/static/avatars/8.jpg',
							'/static/avatars/9.jpg'
						],
						chatData: [{
								name: '半符堙',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '似繁花',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '/static/chats/4.png',
								type: 'image'
							},
							{
								name: '尽落尘花',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '少女的烦恼',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '霸烈~枪神',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '月光作笺',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '失我者永失',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							},
							{
								name: '蔺玄觞',
								isSelf: false,
								avatar: '/static/avatars/1.jpg',
								message: '真的吗,我得准备好了',
								type: 'text'
							}
						]
					}
				],
				//自动发送消息列表
				autoMessage: [{
						name: '陌上吟归雪',
						isSelf: false,
						avatar: '/static/avatars/1.jpg',
						message: '感谢老板红包，手气最佳！',
						type: 'text'
					},
					{
						name: '罗镜里青鸾',
						isSelf: false,
						avatar: '/static/avatars/2.jpg',
						message: '红包收到，感谢大佬厚爱！',
						type: 'text'
					},
					{
						name: '青栀',
						isSelf: false,
						avatar: '/static/avatars/3.jpg',
						message: '感谢红包，祝您财源滚滚！',
						type: 'text'
					},
					{
						name: '十里红妆梦',
						isSelf: false,
						avatar: '/static/avatars/4.jpg',
						message: '红包已领，感谢老板慷慨！',
						type: 'text'
					},
					{
						name: '独留清风醉',
						isSelf: false,
						avatar: '/static/avatars/5.jpg',
						message: '感谢红包，祝您万事如意！',
						type: 'text'
					},
					{
						name: '第四晚心情',
						isSelf: false,
						avatar: '/static/avatars/6.jpg',
						message: '红包抢到，感谢老板恩赐！',
						type: 'text'
					},
					{
						name: '雨樱',
						isSelf: false,
						avatar: '/static/avatars/7.jpg',
						message: '感谢红包，祝您事业高升！',
						type: 'text'
					},
					{
						name: '风轻花落早',
						isSelf: false,
						avatar: '/static/avatars/8.jpg',
						message: '感谢红包，祝您事业顺利！',
						type: 'text'
					},
					{
						name: '歌长夜',
						isSelf: false,
						avatar: '/static/avatars/9.jpg',
						message: '感谢红包，祝您天天快乐！',
						type: 'text'
					},
					{
						name: '曲终破尘',
						isSelf: false,
						avatar: '/static/avatars/9.jpg',
						message: '红包抢到，感谢老板福气！',
						type: 'text'
					},
					{
						name: '不忘初心',
						isSelf: false,
						avatar: '/static/avatars/8.jpg',
						message: '红包收到，感谢老板大吉！',
						type: 'text'
					},
					{
						name: '寒山转苍翠',
						isSelf: false,
						avatar: '/static/avatars/7.jpg',
						message: '感谢红包，祝您笑口常开！',
						type: 'text'
					},
					{
						name: '夏伤',
						isSelf: false,
						avatar: '/static/avatars/6.jpg',
						message: '红包雨来了，手速要快，运气要好！',
						type: 'text'
					},
					{
						name: '提剑赴雪中',
						isSelf: false,
						avatar: '/static/avatars/5.jpg',
						message: '抢红包啦，拼手速的时刻到了！',
						type: 'text'
					},
					{
						name: '墨竹凉夜影',
						isSelf: false,
						avatar: '/static/avatars/4.jpg',
						message: '红包一现，手指飞舞，抢到就是赚到！',
						type: 'text'
					},
					{
						name: '清笑歌',
						isSelf: false,
						avatar: '/static/avatars/3.jpg',
						message: '红包来袭，准备好你的快手！',
						type: 'text'
					},
					{
						name: '故人衣',
						isSelf: false,
						avatar: '/static/avatars/2.jpg',
						message: '抢红包，拼的是速度，赢的是运气！',
						type: 'text'
					},
					{
						name: '清素笔调',
						isSelf: false,
						avatar: '/static/avatars/1.jpg',
						message: '红包到手，心情大好，感谢发红包的老板！',
						type: 'text'
					},
					{
						name: '醉卧君子膝',
						isSelf: false,
						avatar: '/static/avatars/1.jpg',
						message: '红包一抢，心情舒畅，手快有手慢无！',
						type: 'text'
					},
					{
						name: '小巷乌衣',
						isSelf: false,
						avatar: '/static/avatars/2.jpg',
						message: '抢红包，拼手速，赢好运，快来试试！',
						type: 'text'
					},
					{
						name: '花落君离开',
						isSelf: false,
						avatar: '/static/avatars/3.jpg',
						message: '红包雨下，手快者得，快来抢啊！',
						type: 'text'
					},
					{
						name: '称霸天下',
						isSelf: false,
						avatar: '/static/avatars/4.jpg',
						message: '抢红包，拼的是速度，赢的是心情！',
						type: 'text'
					},
					{
						name: '荒凉乃归宿',
						isSelf: false,
						avatar: '/static/avatars/5.jpg',
						message: '红包一现，手指飞舞，抢到就是赚到！',
						type: 'text'
					},
					{
						name: '在星夜下失眠',
						isSelf: false,
						avatar: '/static/avatars/6.jpg',
						message: '红包来袭，准备好你的快手！',
						type: 'text'
					},
					{
						name: '空叹花语意',
						isSelf: false,
						avatar: '/static/avatars/7.jpg',
						message: '抢红包，拼的是速度，赢的是运气！',
						type: 'text'
					},
					{
						name: '花夕月锦绣',
						isSelf: false,
						avatar: '/static/avatars/8.jpg',
						message: '红包到手，心情大好，感谢发红包的老板！',
						type: 'text'
					},
					{
						name: '遥寄山水',
						isSelf: false,
						avatar: '/static/avatars/9.jpg',
						message: '红包一抢，心情舒畅，手快有手慢无！',
						type: 'text'
					},
					{
						name: '浅浅一笑',
						isSelf: false,
						avatar: '/static/avatars/9.jpg',
						message: '抢红包，拼手速，赢好运，快来试试！',
						type: 'text'
					},
					{
						name: '沫兮',
						isSelf: false,
						avatar: '/static/avatars/8.jpg',
						message: '红包雨下，手快者得，快来抢啊！',
						type: 'text'
					},
					{
						name: '九音引魂箫',
						isSelf: false,
						avatar: '/static/avatars/7.jpg',
						message: '抢红包，拼的是速度，赢的是心情！',
						type: 'text'
					},
					{
						name: '长客生',
						isSelf: false,
						avatar: '/static/avatars/6.jpg',
						message: '红包一现，手指飞舞，抢到就是赚到！',
						type: 'text'
					},
					{
						name: '映萧羽',
						isSelf: false,
						avatar: '/static/avatars/5.jpg',
						message: '红包来袭，准备好你的快手！',
						type: 'text'
					}
				],
				//自动发送红包列表
				autoredWallet: [{
						name: '砚台词',
						isSelf: false,
						avatar: '/static/avatars/1.jpg',
						message: '感谢老板红包，手气最佳！',
						type: 'image'
					},
					{
						name: '无意入江南',
						isSelf: false,
						avatar: '/static/avatars/2.jpg',
						message: '红包收到，感谢大佬厚爱！',
						type: 'image'
					},
					{
						name: '绯烟',
						isSelf: false,
						avatar: '/static/avatars/3.jpg',
						message: '感谢红包，祝您财源滚滚！',
						type: 'image'
					},
					{
						name: '西边林上云',
						isSelf: false,
						avatar: '/static/avatars/4.jpg',
						message: '红包已领，感谢老板慷慨！',
						type: 'image'
					},
					{
						name: '有梦就去追',
						isSelf: false,
						avatar: '/static/avatars/5.jpg',
						message: '感谢红包，祝您万事如意！',
						type: 'image'
					},
					{
						name: '暖风细',
						isSelf: false,
						avatar: '/static/avatars/6.jpg',
						message: '红包抢到，感谢老板恩赐！',
						type: 'image'
					},
					{
						name: '拭朱砂',
						isSelf: false,
						avatar: '/static/avatars/7.jpg',
						message: '感谢红包，祝您事业高升！',
						type: 'image'
					},
					{
						name: '笑眼醉人',
						isSelf: false,
						avatar: '/static/avatars/8.jpg',
						message: '感谢红包，祝您事业顺利！',
						type: 'image'
					},
					{
						name: '枫无痕',
						isSelf: false,
						avatar: '/static/avatars/9.jpg',
						message: '感谢红包，祝您天天快乐！',
						type: 'image'
					},
					{
						name: '如落花有意',
						isSelf: false,
						avatar: '/static/avatars/8.jpg',
						message: '抢红包，拼的是速度，赢的是运气！',
						type: 'image'
					},
					{
						name: '古道印残灯',
						isSelf: false,
						avatar: '/static/avatars/7.jpg',
						message: '抢红包，拼的是速度，赢的是运气！',
						type: 'image'
					},
					{
						name: '微雨燕双飞',
						isSelf: false,
						avatar: '/static/avatars/6.jpg',
						message: '红包到手，心情大好，感谢发红包的老板！',
						type: 'image'
					},
					{
						name: '触碰岁月',
						isSelf: false,
						avatar: '/static/avatars/5.jpg',
						message: '抢红包，拼手速，赢好运，快来试试！',
						type: 'image'
					},
					{
						name: '风止于水',
						isSelf: false,
						avatar: '/static/avatars/4.jpg',
						message: '红包雨下，手快者得，快来抢啊！',
						type: 'image'
					},
					{
						name: '未呓',
						isSelf: false,
						avatar: '/static/avatars/3.jpg',
						message: '红包一现，手指飞舞，抢到就是赚到！',
						type: 'image'
					}
				]
			};
		},
		watch: {
			sendMessage(newVal, oldVal) {
				if (this.sendMessage.length > 0) {
					this.isCanSend = true;
				} else {
					this.isCanSend = false;
				}
			},
			'chatListData.chatData': {
				handler() {
					this.scrollToBottom();
				},
				deep: true
			}
		},
		mounted() {
			this.initIcons();
			this.$nextTick(() => {
				this.scrollToBottom();
			});
		},
		onLoad() {
		    if (typeof window !== 'undefined') {
		        window.musicCallBack = this.musicCallBack;
		        window.adsCloseBack = this.adsCloseBack;
		    }
		},
		onShow() {
		    this.getUserInfo();
		    uni.setStorageSync('wxChatPage', true);
		    const chatList = uni.getStorageSync('chatList');
		    if (chatList) {
		        this.chatList = chatList;
		    }
		    
		    // 初始化 uni-ad 并预加载激励视频
		    if (!this.takuInitialized) {
		        this.initTakuSDK();
		        this.takuInitialized = true;
		    } else {
		        this.preloadRewardedAd();
		    }
		},
        
        onHide() {
            uni.setStorageSync('wxChatPage', false);
            if (typeof rh_android !== 'undefined') {
                if (rh_android.hideBottomBanner) rh_android.hideBottomBanner();
                if (rh_android.hideTopBanner) rh_android.hideTopBanner();
            }
        },
		methods: {
            
			onTopBannerShow() {
				getApp().globalData.isFirstTopBanner = false
			},
			onBottomBannerShow() {
				getApp().globalData.isFirstBottomBanner = false
			},
            back() {
                if (typeof rh_android !== 'undefined') {
                    if (rh_android.hideBottomBanner) rh_android.hideBottomBanner();
                    if (rh_android.hideTopBanner) rh_android.hideTopBanner();
                }
                uni.navigateBack()
            },
			//获取用户信息
            getUserInfo() {
                this.$api.getInfo().then((res) => {
                    this.userInfoData = res.data;
                    this.userInfoData = uni.getStorageSync('userInfo');
                    uni.setStorageSync('userInfo', res.data);
                    if (typeof rh_android !== 'undefined') {
                        if (rh_android.showTopBanner) rh_android.showTopBanner(getApp().globalData.isFirstTopBanner, '#ffffff');
                        if (rh_android.showBottomBanner) rh_android.showBottomBanner(getApp().globalData.isFirstBottomBanner, '#ffffff');
                    }
                    window.onTopBannerShow = this.onTopBannerShow
                    window.onBottomBannerShow = this.onBottomBannerShow
                });
            },
			// 初始化微信图标
			initIcons() {
				this.wxIcons = this.fixedPositions.slice(0, this.iconCount).map((pos, index) => {
					this.idCounter++;
					return {
						id: this.idCounter,
						left: pos.x,
						top: pos.y,
						isVisible: false,
						isFlying: false,
						arrived: false
					};
				});
			},
			getTransform(icon) {
				if (!icon.isFlying) return 'translate(0, 0)';
				const containerRect = document.querySelector('.center-container').getBoundingClientRect();
				const targetX = 20 - (containerRect.left + icon.left);
				const targetY = -420 - (containerRect.top + icon.top);
				return `translate(${targetX}rpx, ${targetY}rpx)`;
			},
			// 微信图标飞行动画
			async wxFlyAnimation(redMoney) {
				if (this.isAnimating) return;
				this.isAnimating = true;
				this.initIcons();
				this.wxIcons.forEach((icon) => {
					icon.isVisible = true;
				});
				await this.sleep(1000);
				for (let i = 0; i < this.wxIcons.length; i++) {
					const icon = this.wxIcons[i];
					icon.isFlying = true;
					icon.isVisible = false;
					// 设置到达状态的延迟
					setTimeout(() => {
						icon.arrived = true;
						let arrivedCount = this.wxIcons.filter((res) => res.arrived == true).length;
						if (arrivedCount > 9) {
							this.startAnimation(redMoney);
						}
					}, i * 50 + 500); // 根据飞行时间调整延迟
				}
				await this.sleep(this.wxIcons.length * 100);
				this.isAnimating = false;
			},
			//动画延迟
			sleep(ms) {
				return new Promise((resolve) => setTimeout(resolve, ms));
			},
			//浮动动画
			startAnimation(redMoney) {
				// 动画参数
				const duration = 2000; // 持续时间
				const distance = 100; // 上升距离
				let newText;
				// 开始动画
				const startTime = Date.now();
				if (redMoney > 0) {
					newText = {
						text: redMoney,
						translateY: 0,
						opacity: 1
					};
					this.getedRedMoney = newText;
					const animate = () => {
						const currentTime = Date.now();
						const progress = (currentTime - startTime) / duration;
						if (progress < 1) {
							// 更新位置和透明度
							this.getedRedMoney.translateY = -distance * progress;
							this.getedRedMoney.opacity = 1 - progress;
							requestAnimationFrame(animate);
						} else {
							// 动画结束，移除这个文字
							this.getedRedMoney.text = '';
						}
					};
					requestAnimationFrame(animate);
				}
			},
			//点击红包看广告
			redWalletClick(item) {
				if (this.adsSecond > 0 && this.adsSecond != 60) {
					uni.showToast({
						title: `抢的太频繁啦，请在${this.adsSecond}秒后重试`,
						icon: 'none',
						duration: 2000
					});
				} else {
					this.isShowRedWallet = true;
					this.selectedUser = item;
				}
			},
			
           // 初始化 uni-ad，成功后预加载激励视频
           initTakuSDK() {
               // uni-ad 不需要 appId/appKey 初始化，直接预加载
               this.preloadRewardedAd()
           },

           // 预加载激励视频（load 时绑定广告事件监听）
           preloadRewardedAd() {
               const userInfo = this.userInfoData || uni.getStorageSync('UserInfo') || {}
               const options = {
                   adpid: this.rewardedAdpid,
                   extraData: JSON.stringify({ type: '2', value: '3' }),
                   userId: String(userInfo.id || '')
               }
               UniAds.loadRewardedVideoAd(options, this.rewardedAdListener())
           },

           rewardedAdListener() {
               const self = this
               return {
                   onAdLoaded: () => {
                       console.log('weChat 激励视频预加载成功')
                       self.rewardedAdLoaded = true
                   },
                   onAdFailed: (err) => {
                       console.log('weChat 激励视频预加载失败', err)
                       self.rewardedAdLoaded = false
                   },
                   onAdShow: () => {},
                   onReward: (res) => {
                       console.log('weChat 激励视频发放奖励', res)
                       self.adDidReward = true
                   },
                   onAdClosed: () => {
                       console.log('weChat 激励视频关闭')
                       self.isShowRedWallet = false
                       if (self.adDidReward) {
                           self.claimAdReward()
                       } else {
                           uni.showToast({ title: '未完整观看视频，无法获得奖励', icon: 'none' })
                       }
                       self.adDidReward = false
                       // 重新预加载下一次广告
                       self.preloadRewardedAd()
                   },
                   onAdClicked: () => {}
               }
           },

           // 点击红包弹窗「看广告领红包」按钮
           watchAds() {
               console.log('🔍 watchAds 被调用')
               this.$api.checkAdsNums().then((res) => {
                   console.log('checkAdsNums 结果:', res)
                   if (!UniAds.initialized) {
                       console.log('⚠️ UniAds 未初始化，降级使用 rh_android')
                       this.showRhAndroidAd()
                       return
                   }
                   const userInfo = this.userInfoData || uni.getStorageSync('UserInfo') || {}
                   if (!userInfo.id) {
                       uni.showToast({ title: '请先登录', icon: 'none' })
                       return
                   }
                   const options = {
                       adpid: this.rewardedAdpid,
                       extraData: JSON.stringify({ type: '2', value: '3' }),
                       userId: String(userInfo.id || '')
                   }
                   console.log('✅ 使用 uni-ad 激励视频')
                   this.adDidReward = false
                   UniAds.showRewardedVideoAd(options, this.rewardedAdListener())
               }).catch(() => {
                   this.showRhAndroidAd()
               })
           },

           // rh_android 兜底广告
           showRhAndroidAd() {
               this.isShowRedWallet = false
               if (typeof rh_android !== 'undefined' && rh_android.showAd) {
                   rh_android.showAd(this.userInfoData.id.toString(), JSON.stringify({
                       type: 2,
                       value: 3
                   }), 4)
               } else {
                   console.log('❌ rh_android 也不可用')
                   uni.showToast({ title: '广告加载失败，请稍后重试', icon: 'none' })
               }
           },

           // 领取广告奖励（复用娱乐页方法）
           claimAdReward() {
               console.log('[红包群] 领取广告奖励')
               this.$api.adRewardToCoin().then(res => {
                   console.log('[红包群] 广告奖励接口返回:', res)
                   if (res.code === 200) {
                       this.adsResultData = { money: 100 }
                       this.isShowRedWalletReward = true
                       this.getUserInfo()
                   }
               }).catch(err => {
                   console.error('[红包群] 广告奖励接口失败:', err)
                   uni.showToast({ title: '奖励发放失败，请稍后重试', icon: 'none' })
               })
           },

        			// 开心收下
			receiveMoney() {
				this.isShowRedWalletReward = false;
				// if (uni.getStorageSync('redMoney')) {
				// 	this.isShowRedWalletReward = false;
				// 	// this.wxFlyAnimation(uni.getStorageSync('redMoney'));
				// 	uni.setStorageSync('redMoney', null);
				// }
			},
			//广告回调
			musicCallBack(id) {
				this.isShowRedWallet = false;
				uni.setStorageSync('adsId', id);
			},
			//广告关闭
			adsCloseBack() {
				if (uni.getStorageSync('adsId')) {
					this.$api
						.adResult({
							transId: uni.getStorageSync('adsId')
						})
						.then((res) => {
							if (this.adsTimer) {
								clearInterval(this.adsTimer);
								this.adsTimer = null;
							} else {
								this.adsTimer = setInterval(() => {
									this.adsSecond--;
									if (this.adsSecond <= 0) {
										clearInterval(this.adsTimer);
										this.adsTimer = null;
										this.adsSecond = 60;
									}
								}, 1000);
							}
							uni.setStorageSync('adsId', null);
							uni.setStorageSync('redMoney', res.data.redMoney);
							this.isShowRedWalletReward = true;
							this.adsResultData = res.data;
							this.getUserInfo();
						});
				}
			},
			// 跳转页面
			toPage(index) {
				if (index == 0) {
					uni.navigateTo({
						url: '/pages/index/withdrawal'
					});
				} else {
					uni.navigateTo({
						url: '/pages/index/redWallet'
					});
				}
			},
			chooseBar(index) {
			    if (index == 0) {
			        uni.setStorageSync('wxChatPage', false);
			        if (typeof rh_android !== 'undefined' && rh_android.continueMusic) {
			            rh_android.continueMusic();
			        }
			        uni.switchTab({
			            url: '/pages/game/index'
			        })
			    }
			},
			//发送消息
			sendMessageBtn() {
				if (this.sendMessage) {
					this.chatListData.chatData.push({
						name: '自己',
						isSelf: true,
						avatar: '/static/avatars/9.jpg',
						message: this.sendMessage,
						type: 'text'
					});
					this.sendMessage = '';
					this.scrollToBottom();
				}
			},
			// 滚动到底部
			scrollToBottom() {
				// 清除之前的定时器
				if (this.forceScrollTimer) {
					clearTimeout(this.forceScrollTimer);
				}

				this.$nextTick(() => {
					// 先设置一个较大的值
					this.scrollTop = 999999;
					if (this.chatListData.chatData) {
						// 强制再次触发滚动
						if (this.chatListData.chatData[this.chatListData.chatData.length - 1].type == 'text') {
							this.forceScrollTimer = setTimeout(() => {
								this.scrollTop = this.scrollTop + 1;
							}, 1000);
						}
						if (this.chatListData.chatData[this.chatListData.chatData.length - 1].type == 'image') {
							this.forceScrollTimer = setTimeout(() => {
								this.scrollTop = this.scrollTop + 5;
							}, 1000);
						}
					}
				});
			},
            chooseGroup(item, index) {
                this.selectedGroupIndex = index;
                this.isShowChatDetail = true;
                this.isBack = false;
                this.chatListData = item;
                if (this.sendMessageTimer) {
                    clearTimeout(this.sendMessageTimer);
                }
                if (this.sendRedWalletTimer) {
                    clearTimeout(this.sendRedWalletTimer);
                }
                this.autoSendMessage();
                this.autoSendRedWallet();
                if (typeof rh_android !== 'undefined') {
                    if (rh_android.hideBottomBanner) rh_android.hideBottomBanner();
                    if (rh_android.hideTopBanner) rh_android.hideTopBanner();
                }
            },
			//随机1-5秒发送消息
			autoSendMessage() {
				const randomDelay = Math.floor(Math.random() * 5 + 1) * 1000;
				const randomNumber = Math.floor(Math.random() * this.autoMessage.length);
				const randomMessage = this.autoMessage[randomNumber];
				this.sendMessageTimer = setTimeout(() => {
					if (this.isBack) {
						clearTimeout(this.sendMessageTimer);
						clearTimeout(this.sendRedWalletTimer);
						this.sendMessageTimer = null;
						this.sendRedWalletTimer = null;
						return;
					}
					this.chatListData.chatData.push(randomMessage);
					this.scrollToBottom();
					this.autoSendMessage();
				}, randomDelay);
			},
			//随机5-25秒发送红包
			autoSendRedWallet() {
				const randomDelay = Math.floor(Math.random() * 25 + 5) * 1000;
				const randomNumber = Math.floor(Math.random() * this.autoredWallet.length);
				const randomMessage = this.autoredWallet[randomNumber];
				this.sendRedWalletTimer = setTimeout(() => {
					if (this.isBack) {
						clearTimeout(this.sendMessageTimer);
						clearTimeout(this.sendRedWalletTimer);
						this.sendMessageTimer = null;
						this.sendRedWalletTimer = null;
						return;
					}
					this.chatListData.chatData.push(randomMessage);
					this.scrollToBottom();
					this.autoSendRedWallet();
				}, randomDelay);
			},
			showChatList() {
			    this.chatList[this.selectedGroupIndex].chatData.concat(this.chatListData.chatData);
			    this.chatListData = [];
			    uni.setStorageSync('chatList', this.chatList);
			    this.isBack = true;
			    this.isShowChatDetail = false;
			    if (typeof rh_android !== 'undefined') {
			        if (rh_android.showTopBanner) rh_android.showTopBanner(getApp().globalData.isFirstBanner);
			        if (rh_android.showBottomBanner) rh_android.showBottomBanner(getApp().globalData.isFirstBanner);
			    }
			}
        }
	};
</script>

<style lang="scss" scoped>
	.main {
		width: 100vw;
		height: 100vh;
		overflow: hidden;
		background: url('/static/juhe/13.png');
		background-size: cover;

		.top {
			width: 750rpx;
			height: 234rpx;

			.item {
				display: flex;
				font-size: 40rpx;
				color: #000;
				position: relative;
				left: 20rpx;
				top: 160rpx;

				.title-bg {
					width: 350rpx;
					height: 52rpx;
					margin-right: 20rpx;
					display: flex;
					justify-content: space-between;
					align-items: center;
					text-align: center;
					background: #E8783B;
				}

				.float-number {
					position: fixed;
					left: 0;
					top: 0;
					overflow: hidden;
					width: 100%;
					height: 100%;
					pointer-events: none;
					font-size: 36rpx;
					font-weight: bold;

					.floating-text2 {
						position: absolute;
						font-size: 16px;
						color: #e80904;
						left: 120rpx;
						min-width: 200rpx;
						top: 50rpx;
						pointer-events: none;
						/* 防止文字影响点击事件 */
					}
				}
			}
		}

		.group-chat {
			display: flex;
			margin-top: 80rpx;
			flex-direction: column;

			.group-chat-item {
				display: flex;
				position: relative;
				align-items: center;
				margin-bottom: 65rpx;

				.group-chat-item1 {
					width: 120rpx;
					height: 120rpx;
					display: flex;
					flex-wrap: wrap;
					padding: 7rpx;
					justify-content: space-between;
					margin-left: 30rpx;
					background: #e6e0e2;
					border-radius: 20rpx;
				}

				.group-chat-item2 {
					display: flex;
					margin-left: 30rpx;
					justify-content: space-around;
					flex-direction: column;
				}

				.group-chat-item3 {
					width: 100rpx;
					height: 57rpx;
					position: absolute;
					right: 40rpx;
					font-weight: bold;
					font-size: 30rpx;
					color: #ffffff;
					text-align: center;
					line-height: 57rpx;
					background: #ff3638;
					border-radius: 29rpx;
				}

				.group-chat-line {
					width: 554rpx;
					height: 2rpx;
					position: absolute;
					bottom: 5rpx;
					right: 10rpx;
					background: #f0edee;
				}
			}
		}

		.group-chat-page {
			position: absolute;
			z-index: 999;
			bottom: 0;
			left: 0;
			right: 0;
			top: 0rpx;
			background: #fff;

			.group-chat-page1 {
				display: flex;
				margin-top: 30rpx;
				justify-content: center;
				position: relative;

				.group-chat-page1-back {
					position: absolute;
					display: flex;
					align-items: center;
					font-size: 30rpx;
					color: #666666;
					left: 20rpx;

					span {
						margin-left: 20rpx;
					}
				}
			}

			.group-chat-page2 {
				position: absolute;
				bottom: 8.5vh;
				left: 0;
				right: 0;
				top: 7vh;
				background: #f5f5f5;

				.group-chat-content {
					display: flex;
					width: 90%;
					margin: 30rpx auto 0 auto;

					.group-chat-content-info {
						display: flex;
						margin-left: 20rpx;
						flex-direction: column;

						.group-chat-content-info-text {
							font-weight: 400;
							font-size: 28rpx;
							color: #333333;
							margin-top: 20rpx;
							text-align: left;
							padding: 10rpx;
							background: #ffffff;
							border-radius: 0rpx 20rpx 20rpx 20rpx;
						}

						.group-chat-content-info-wallet {
							width: 412rpx;
							height: 152rpx;
							margin-top: 20rpx;
							background: url('/static/chats/4.png');
							background-size: cover;
						}
					}

					.groupChatContentInfos {
						span {
							text-align: right;
							margin-right: 10rpx;
						}
					}
				}

				.groupChatContentSelft {
					flex-direction: row-reverse;
				}
			}

			.group-chat-page3 {
				display: flex;
				position: absolute;
				justify-content: space-around;
				bottom: 20rpx;
				left: 0;
				right: 0;

				.group-chat-input {
					width: 550rpx;
					height: 86rpx;
					display: flex;
					align-items: center;
					background: #ededed;
					border-radius: 20rpx;

					input {
						width: 95%;
						font-size: 30rpx;
						margin-left: 15rpx;
					}
				}

				.group-chat-send {
					width: 140rpx;
					height: 86rpx;
					position: relative;
					background: url('/static/chats/1.png');
					background-size: cover;

					.disabled {
						position: absolute;
						left: 0;
						top: 0;
						right: 0;
						bottom: 0;
						background: rgba(245, 245, 245, 0.7);
					}
				}
			}
		}

		.bottom-bar {
			position: absolute;
			display: flex;
			left: 0;
			right: 0;
			bottom: 25vh;

			.bottom-bar-body {
				width: 60%;
				display: flex;
				justify-content: center;
				margin: auto;
			}
		}

		.redWalletPop {
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 999;
			overflow: hidden;
			background: rgba(0, 0, 0, 0.5);

			.redWalletPop-title {
				display: flex;
				position: relative;
				top: 30vh;
				justify-content: center;
				align-items: center;
				font-weight: 400;
				z-index: 9999;
				font-size: 30rpx;
				color: #ffe7b7;
			}

			.redWalletPop-title2 {
				display: flex;
				font-weight: 400;
				font-size: 38rpx;
				color: #ffe7b7;
				position: absolute;
				left: 50%;
				top: 40vh;
				z-index: 9999;
				transform: translateX(-50%);
			}

			.redWalletPop-bg {
				width: 546rpx;
				height: 830rpx;
				position: relative;
				margin: 15vh auto 0 auto;
				background: url('/static/2.png');
				background-size: cover;

				.redWalletPop-close {
					width: 23rpx;
					height: 24rpx;
					top: 30rpx;
					left: 490rpx;
					position: relative;
					background: url('/static/images/3.png');
					background-size: cover;
				}
			}
		}

		.redWalletRewardPop {
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 999;
			background: rgba(0, 0, 0, 0.5);

			.redWalletRewardPop-bg {
				width: 600rpx;
				height: 871rpx;
				position: relative;
				margin: 15vh auto 0 auto;
				background: url('/static/4.png');
				background-size: cover;

				.redWalletRewardPop-body {
					display: flex;
					flex-direction: column;
					align-items: center;

					.body-title {
						margin-top: 150rpx;
						display: flex;
						align-items: center;
						font-weight: 400;
						font-size: 30rpx;
						color: #333333;
					}

					.body-money {
						font-weight: bold;
						font-size: 81rpx;
						color: #fe302a;
					}
				}
			}

			.redWalletRewardPop-close {
				width: 428rpx;
				height: 126rpx;
				position: relative;
				margin: -10vh auto 0 auto;
				background: url('/static/5.png');
				background-size: cover;
			}
		}

		.center-container {
			position: absolute;
			left: 50%;
			bottom: 22.5%;
			pointer-events: none;
			transform: translateX(-50%);
			width: 300rpx;
			height: 300rpx;
			z-index: 99999;

			.floating-wx-icon {
				position: absolute;
				width: 55rpx;
				height: 55rpx;
				opacity: 0;
				transition: all 0.5s ease-in-out;
				will-change: transform, opacity;
			}

			.floating-wx-icon.show {
				opacity: 1;
			}

			.floating-wx-icon.flying {
				transition: all 0.8s cubic-bezier(0.4, 0, 0.2, 1);
			}
		}
	}
</style>
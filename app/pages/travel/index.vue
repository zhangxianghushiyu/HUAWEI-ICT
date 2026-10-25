<template>
	<view class="travel-page">
		<!-- 顶部地址栏 -->
		<view class="search-bar">
			<view class="search-bar-inner">
				<text class="icon-location">📍</text>
				<text class="city">贵州省贵阳市贵安新区六月六星野营地</text>
			</view>
		</view>

		<!-- 欢迎小卡片 -->
		<view class="welcome-card">
			<view class="welcome-emoji">⛺</view>
			<view class="welcome-text">
				<text class="welcome-title">Hi～欢迎来到星野营地 🌿</text>
				<text class="welcome-sub">今天也是个适合出游的好天气呢 ☀️</text>
			</view>
		</view>

		<!-- 轮播图 -->
		<swiper class="banner-swiper" :indicator-dots="true" :autoplay="true" :interval="3000" :duration="500"
			indicator-active-color="#FF8C42">
			<swiper-item>
				<image class="banner-image" src="/static/liuyueliu/yishuguan/159A0528.JPG" mode="aspectFill"></image>
				<view class="banner-title">✨ 六月六星野营地</view>
			</swiper-item>
			<swiper-item>
				<image class="banner-image" src="/static/liuyueliu/xingyeyejing/159A0685_1779118868481.JPG"
					mode="aspectFill"></image>
				<view class="banner-title">🌾 田园露营</view>
			</swiper-item>
			<swiper-item>
				<image class="banner-image" src="/static/liuyueliu/xingyeyejing/159A0609.JPG" mode="aspectFill"></image>
				<view class="banner-title">🎵 布依风情</view>
			</swiper-item>
		</swiper>

		<!-- 分类导航 -->
		<view class="category-nav">
			<view class="category-item" @click="goGonglue">
				<view class="category-icon cat-1">
					<text>📖</text>
				</view>
				<text class="category-text">攻略</text>
			</view>
			<view class="category-item" @click="gocanying">
				<view class="category-icon cat-2">
					<text>🍽️</text>
				</view>
				<text class="category-text">餐饮</text>
			</view>
			<view class="category-item" @click="goty">
				<view class="category-icon cat-3">
					<text>🌾</text>
				</view>
				<text class="category-text">田园</text>
			</view>
			<view class="category-item" @click="goJiu">
				<view class="category-icon cat-4">
					<text>🎁</text>
				</view>
				<text class="category-text">商品</text>
			</view>
			<view class="category-item" @click="gozb">
				<view class="category-icon cat-5">
					<text>🗺️</text>
				</view>
				<text class="category-text">周边</text>
			</view>
		</view>

		<!-- 热门推荐 -->
		<view class="section">
			<view class="section-header">
				<view class="section-title-row">
					<text class="section-emoji">🔥</text>
					<text class="section-title">热门推荐</text>
				</view>
				<text class="section-badge">HOT</text>
			</view>
			<scroll-view class="hot-scroll" scroll-x="true" show-scrollbar="false">
				<view class="hot-item" @click="goDetail('tent')">
					<image class="hot-image" src="/static/liuyueliu/ly.png" mode="aspectFill"></image>
					<view class="hot-info">
						<text class="hot-title">⛺ 星空帐篷露营</text>
						<text class="hot-desc">田园夜景，氛围感拉满</text>
					</view>
				</view>
				<view class="hot-item" @click="goDetail('fireworks')">
					<image class="hot-image" src="/static/liuyueliu/dtyh.png" mode="aspectFill"></image>
					<view class="hot-info">
						<text class="hot-title">🎆 打铁烟花</text>
						<text class="hot-desc">非遗绝技，震撼视觉盛宴</text>
					</view>
				</view>
				<view class="hot-item" @click="goDetail('campfire')">
					<image class="hot-image" src="/static/liuyueliu/ghwh.png" mode="aspectFill"></image>
					<view class="hot-info">
						<text class="hot-title">🔥 篝火晚会</text>
						<text class="hot-desc">布依民俗，夜间狂欢</text>
					</view>
				</view>
				<view class="hot-item" @click="goDetail('tea')">
					<image class="hot-image" src="/static/liuyueliu/xwc.png" mode="aspectFill"></image>
					<view class="hot-info">
						<text class="hot-title">☕ 下午茶</text>
						<text class="hot-desc">田园下午茶，惬意时光</text>
					</view>
				</view>
				<view class="hot-item" @click="goDetail('culture')">
					<image class="hot-image" src="/static/liuyueliu/bywh.png" mode="aspectFill"></image>
					<view class="hot-info">
						<text class="hot-title">🎭 布依文化体验</text>
						<text class="hot-desc">民族风情，非遗文化传承</text>
					</view>
				</view>
			</scroll-view>
		</view>

		<!-- 景区推荐 -->
		<view class="section">
			<view class="section-header">
				<view class="section-title-row">
					<text class="section-emoji">🌟</text>
					<text class="section-title">景区推荐</text>
				</view>
				<text class="section-badge badge-green">推荐</text>
			</view>
			<view class="season-grid">
				<view class="season-item" @click="goDetail('rice')">
					<image class="season-image"
						src="https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=400&fit=crop"
						mode="aspectFill"></image>
					<view class="season-content">
						<text class="season-title">🌾 稻田露营</text>
						<text class="season-tag tag-pink">春日限定</text>
					</view>
				</view>
				<view class="season-item" @click="goDetail('pet')">
					<image class="season-image"
						src="https://images.unsplash.com/photo-1519904981063-b0cf448d479e?w=400&fit=crop"
						mode="aspectFill"></image>
					<view class="season-content">
						<text class="season-title">🐰 萌宠投喂</text>
						<text class="season-tag tag-orange">亲子必玩</text>
					</view>
				</view>
				<view class="season-item" @click="goDetail('train')">
					<image class="season-image"
						src="https://images.unsplash.com/photo-1519681393784-d120267933ba?w=400&fit=crop"
						mode="aspectFill"></image>
					<view class="season-content">
						<text class="season-title">🚂 田园小火车</text>
						<text class="season-tag tag-purple">网红打卡</text>
					</view>
				</view>
				<view class="season-item" @click="goDetail('water')">
					<image class="season-image"
						src="https://images.unsplash.com/photo-1516483638261-f4dbaf036963?w=400&fit=crop"
						mode="aspectFill"></image>
					<view class="season-content">
						<text class="season-title">💦 溪边玩水</text>
						<text class="season-tag tag-blue">夏日避暑</text>
					</view>
				</view>
			</view>
		</view>

		<!-- 攻略游记 -->
		<view class="section">
			<view class="section-header">
				<view class="section-title-row">
					<text class="section-emoji">📝</text>
					<text class="section-title">攻略游记</text>
				</view>
			</view>
			<view class="strategy-list">
				<view class="strategy-item" @click="goGonglue">
					<image class="strategy-image"
						src="https://images.unsplash.com/photo-1551632811-561732d1e306?w=400&fit=crop"
						mode="aspectFill"></image>
					<view class="strategy-info">
						<text class="strategy-title">六月六星野营地游玩全攻略</text>
						<view class="strategy-meta">
							<text class="author">@营地官方</text>
							<text class="views">👁 5.6k</text>
						</view>
					</view>
				</view>
				<view class="strategy-item" @click="goGonglue">
					<image class="strategy-image"
						src="https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&fit=crop"
						mode="aspectFill"></image>
					<view class="strategy-info">
						<text class="strategy-title">贵安新区露营避坑指南</text>
						<view class="strategy-meta">
							<text class="author">@旅游达人</text>
							<text class="views">👁 3.2k</text>
						</view>
					</view>
				</view>
				<view class="strategy-item" @click="goGonglue">
					<image class="strategy-image"
						src="https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=400&fit=crop"
						mode="aspectFill"></image>
					<view class="strategy-info">
						<text class="strategy-title">田园亲子游一日行程</text>
						<view class="strategy-meta">
							<text class="author">@亲子博主</text>
							<text class="views">👁 4.1k</text>
						</view>
					</view>
				</view>
			</view>
		</view>

		<!-- 底部趣味标语 -->
		<view class="footer-banner">
			<text class="footer-emoji">🏕️</text>
			<text class="footer-text">来星野，过一天田园慢生活吧～</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {}
		},
		onShow() {
			// #ifdef APP-PLUS
			if (typeof rh_android !== 'undefined') {
				// rh_android.xxx()
			}
			// #endif
		},
		methods: {
			goGonglue() {
				uni.navigateTo({
					url: '/pages/travel/gonglue'
				})
			},
			goJiu() {
				uni.navigateTo({
					url: '/pages/travel/jiuwenhua'
				})
			},
			gocanying() {
				uni.navigateTo({
					url: '/pages/travel/canying'
				})
			},
			goty() {
				uni.navigateTo({
					url: '/pages/travel/ty'
				})
			},
			gozb() {
				uni.navigateTo({
					url: '/pages/travel/zb'
				})
			},
			goDetail(type) {
				uni.navigateTo({
					url: `/pages/travel/detail?type=${type}`
				})
			}
		}
	}
</script>

<style>
	/* ========== 全局/背景 ========== */
	.travel-page {
		background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 20%, #FFEBD6 50%, #FFE8CF 100%);
		min-height: 100vh;
		padding-bottom: 60rpx;
		box-sizing: border-box;
		position: relative;
		overflow-x: hidden;
	}

	/* 背景装饰泡泡 */
	.travel-page::before {
		content: '';
		position: fixed;
		top: -180rpx;
		right: -120rpx;
		width: 500rpx;
		height: 500rpx;
		border-radius: 50%;
		background: radial-gradient(circle, rgba(255, 180, 120, 0.12) 0%, transparent 70%);
		pointer-events: none;
		z-index: 0;
	}

	.travel-page::after {
		content: '';
		position: fixed;
		bottom: 200rpx;
		left: -140rpx;
		width: 400rpx;
		height: 400rpx;
		border-radius: 50%;
		background: radial-gradient(circle, rgba(255, 200, 150, 0.1) 0%, transparent 70%);
		pointer-events: none;
		z-index: 0;
	}

	/* ========== 顶部地址栏 ========== */
	.search-bar {
		position: sticky;
		top: 0;
		z-index: 100;
		padding: 18rpx 30rpx;
	}

	.search-bar-inner {
		display: flex;
		align-items: center;
		background: rgba(255, 255, 255, 0.85);
		backdrop-filter: blur(20rpx);
		-webkit-backdrop-filter: blur(20rpx);
		padding: 18rpx 28rpx;
		border-radius: 50rpx;
		box-shadow: 0 4rpx 20rpx rgba(200, 130, 60, 0.1);
		border: 1rpx solid rgba(255, 180, 120, 0.15);
	}

	.icon-location {
		font-size: 30rpx;
		margin-right: 10rpx;
	}

	.city {
		color: #6B4226;
		font-size: 26rpx;
		font-weight: 500;
		flex: 1;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	/* ========== 欢迎卡片 ========== */
	.welcome-card {
		position: relative;
		z-index: 1;
		margin: 16rpx 30rpx 20rpx;
		padding: 28rpx 30rpx;
		background: linear-gradient(135deg, #FFF8F0 0%, #FFF3E5 100%);
		border-radius: 32rpx;
		display: flex;
		align-items: center;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.08);
		border: 1rpx solid rgba(255, 180, 120, 0.12);
	}

	.welcome-emoji {
		font-size: 60rpx;
		margin-right: 22rpx;
		animation: bounce-soft 2s ease-in-out infinite;
	}

	@keyframes bounce-soft {
		0%, 100% { transform: translateY(0); }
		50% { transform: translateY(-10rpx); }
	}

	.welcome-text {
		flex: 1;
		display: flex;
		flex-direction: column;
	}

	.welcome-title {
		font-size: 30rpx;
		font-weight: 700;
		color: #5D3A1A;
		margin-bottom: 6rpx;
	}

	.welcome-sub {
		font-size: 24rpx;
		color: #B08060;
	}

	/* ========== 轮播图 ========== */
	.banner-swiper {
		position: relative;
		z-index: 1;
		height: 360rpx;
		margin: 0 30rpx 24rpx;
		border-radius: 32rpx;
		overflow: hidden;
		box-shadow: 0 8rpx 30rpx rgba(200, 130, 60, 0.12);
	}

	.banner-image {
		width: 100%;
		height: 100%;
	}

	.banner-title {
		position: absolute;
		bottom: 0;
		left: 0;
		right: 0;
		padding: 30rpx 28rpx 22rpx;
		background: linear-gradient(transparent, rgba(80, 35, 10, 0.7));
		color: #FFF8F0;
		font-size: 34rpx;
		font-weight: 700;
		letter-spacing: 2rpx;
	}

	/* ========== 分类导航 ========== */
	.category-nav {
		position: relative;
		z-index: 1;
		display: flex;
		flex-wrap: wrap;
		padding: 28rpx 16rpx 20rpx;
		margin: 0 30rpx 24rpx;
		background: rgba(255, 255, 255, 0.9);
		backdrop-filter: blur(20rpx);
		-webkit-backdrop-filter: blur(20rpx);
		border-radius: 36rpx;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
		border: 1rpx solid rgba(255, 180, 120, 0.1);
	}

	.category-item {
		width: 20%;
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 10rpx 0;
		transition: transform 0.2s cubic-bezier(0.175, 0.885, 0.32, 1.275);
	}

	.category-item:active {
		transform: scale(0.88);
	}

	.category-icon {
		width: 88rpx;
		height: 88rpx;
		border-radius: 28rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		margin-bottom: 14rpx;
		font-size: 40rpx;
		box-shadow: 0 6rpx 16rpx rgba(200, 130, 60, 0.12);
		transition: transform 0.2s, box-shadow 0.2s;
	}

	.cat-1 { background: linear-gradient(135deg, #FFB088 0%, #FFCC80 100%); }
	.cat-2 { background: linear-gradient(135deg, #FFAA7B 0%, #FFC89E 100%); }
	.cat-3 { background: linear-gradient(135deg, #B4C88A 0%, #CCD89E 100%); }
	.cat-4 { background: linear-gradient(135deg, #E8A87C 0%, #F0C89E 100%); }
	.cat-5 { background: linear-gradient(135deg, #D4A078 0%, #E0BC90 100%); }

	.category-text {
		color: #6B4226;
		font-size: 24rpx;
		font-weight: 600;
		text-align: center;
	}

	/* ========== 通用 section ========== */
	.section {
		position: relative;
		z-index: 1;
		background: #FFFFFF;
		margin: 0 30rpx 24rpx;
		padding: 32rpx 28rpx;
		border-radius: 36rpx;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.06);
	}

	.section-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 26rpx;
	}

	.section-title-row {
		display: flex;
		align-items: center;
	}

	.section-emoji {
		font-size: 34rpx;
		margin-right: 12rpx;
	}

	.section-title {
		font-size: 34rpx;
		font-weight: 800;
		color: #5D3A1A;
		letter-spacing: 1rpx;
	}

	.section-badge {
		font-size: 20rpx;
		font-weight: 700;
		color: #FFFFFF;
		background: linear-gradient(135deg, #FF8C42 0%, #FFB347 100%);
		padding: 6rpx 18rpx;
		border-radius: 20rpx;
		letter-spacing: 2rpx;
		box-shadow: 0 3rpx 10rpx rgba(255, 140, 66, 0.25);
	}

	.badge-green {
		background: linear-gradient(135deg, #8CB878 0%, #A8D090 100%);
		box-shadow: 0 3rpx 10rpx rgba(140, 184, 120, 0.25);
	}

	/* ========== 热门推荐 ========== */
	.hot-scroll {
		white-space: nowrap;
		width: 100%;
	}

	.hot-item {
		display: inline-block;
		width: 290rpx;
		margin-right: 18rpx;
		background: #FFFDF9;
		border-radius: 28rpx;
		overflow: hidden;
		box-shadow: 0 4rpx 16rpx rgba(200, 130, 60, 0.07);
		vertical-align: top;
		transition: transform 0.25s cubic-bezier(0.175, 0.885, 0.32, 1.275), box-shadow 0.25s;
	}

	.hot-item:active {
		transform: translateY(-6rpx) scale(1.02);
		box-shadow: 0 12rpx 32rpx rgba(200, 130, 60, 0.15);
	}

	.hot-item:last-child { margin-right: 0; }

	.hot-image {
		width: 100%;
		height: 190rpx;
	}

	.hot-info {
		padding: 18rpx 20rpx 22rpx;
	}

	.hot-title {
		display: block;
		font-size: 27rpx;
		font-weight: 700;
		color: #5D3A1A;
		margin-bottom: 8rpx;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.hot-desc {
		display: block;
		font-size: 23rpx;
		color: #A08068;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	/* ========== 景区推荐(2列宫格) ========== */
	.season-grid {
		display: grid;
		grid-template-columns: repeat(2, 1fr);
		gap: 18rpx;
	}

	.season-item {
		background: #FFFDF9;
		border-radius: 28rpx;
		overflow: hidden;
		box-shadow: 0 4rpx 16rpx rgba(200, 130, 60, 0.07);
		transition: transform 0.25s cubic-bezier(0.175, 0.885, 0.32, 1.275), box-shadow 0.25s;
	}

	.season-item:active {
		transform: translateY(-6rpx) scale(1.02);
		box-shadow: 0 12rpx 32rpx rgba(200, 130, 60, 0.15);
	}

	.season-image {
		width: 100%;
		height: 210rpx;
	}

	.season-content {
		padding: 18rpx 20rpx 22rpx;
		display: flex;
		flex-direction: column;
		gap: 10rpx;
	}

	.season-title {
		display: block;
		font-size: 27rpx;
		font-weight: 700;
		color: #5D3A1A;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.season-tag {
		display: inline-block;
		align-self: flex-start;
		padding: 6rpx 18rpx;
		font-size: 20rpx;
		font-weight: 600;
		color: #FFFFFF;
		border-radius: 16rpx;
		letter-spacing: 1rpx;
	}

	.tag-pink  { background: linear-gradient(135deg, #FF9E9E 0%, #FFB8B8 100%); box-shadow: 0 3rpx 10rpx rgba(255,158,158,0.25); }
	.tag-orange{ background: linear-gradient(135deg, #FFB070 0%, #FFC898 100%); box-shadow: 0 3rpx 10rpx rgba(255,176,112,0.25); }
	.tag-purple{ background: linear-gradient(135deg, #C4A0D8 0%, #D8BCE8 100%); box-shadow: 0 3rpx 10rpx rgba(196,160,216,0.25); }
	.tag-blue  { background: linear-gradient(135deg, #88B8D8 0%, #A8D0E8 100%); box-shadow: 0 3rpx 10rpx rgba(136,184,216,0.25); }

	/* ========== 攻略游记 ========== */
	.strategy-list {
		display: flex;
		flex-direction: column;
		gap: 20rpx;
	}

	.strategy-item {
		display: flex;
		background: #FFFDF9;
		border-radius: 28rpx;
		overflow: hidden;
		box-shadow: 0 4rpx 16rpx rgba(200, 130, 60, 0.07);
		transition: transform 0.25s cubic-bezier(0.175, 0.885, 0.32, 1.275), box-shadow 0.25s;
	}

	.strategy-item:active {
		transform: scale(0.97);
		box-shadow: 0 8rpx 28rpx rgba(200, 130, 60, 0.15);
	}

	.strategy-image {
		width: 200rpx;
		height: 150rpx;
		flex-shrink: 0;
	}

	.strategy-info {
		flex: 1;
		padding: 18rpx 22rpx;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}

	.strategy-title {
		font-size: 28rpx;
		font-weight: 700;
		color: #5D3A1A;
		line-height: 1.45;
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
		overflow: hidden;
	}

	.strategy-meta {
		display: flex;
		align-items: center;
		font-size: 22rpx;
		color: #B8977A;
	}

	.author { margin-right: 24rpx; }

	.views { margin-left: auto; }

	/* ========== 底部标语 ========== */
	.footer-banner {
		position: relative;
		z-index: 1;
		margin: 30rpx 30rpx 10rpx;
		padding: 36rpx 0;
		text-align: center;
		background: linear-gradient(135deg, rgba(255,255,255,0.7) 0%, rgba(255,245,235,0.7) 100%);
		border-radius: 36rpx;
		backdrop-filter: blur(10rpx);
		-webkit-backdrop-filter: blur(10rpx);
		display: flex;
		flex-direction: column;
		align-items: center;
		gap: 12rpx;
		border: 1rpx solid rgba(255, 180, 120, 0.1);
	}

	.footer-emoji {
		font-size: 56rpx;
		animation: bounce-soft 2s ease-in-out infinite;
	}

	.footer-text {
		font-size: 28rpx;
		color: #8B6B4A;
		font-weight: 600;
	}
</style>

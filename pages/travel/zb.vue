<template>
	<view class="surround-page">
		<!-- 顶部大图 -->
		<view class="top-banner">
			<image src="/static/liuyueliu/xingyeyejing/159A0616.JPG" mode="aspectFill" class="banner-img"></image>
			<view class="banner-title">
				<text class="main-title">六月六星野营地</text>
				<text class="sub-title">贵阳市贵安新区 · 周边游玩指南</text>
			</view>
		</view>

		<!-- 内容 -->
		<view class="content">
			<!-- 标题 -->
			<view class="section-title">
				<view class="line"></view>
				<text class="title">营地周边 · 好去处</text>
			</view>

			<!-- 介绍 -->
			<view class="desc-card">
				<text class="text">
					六月六星野营地位于贵阳市贵安新区，周边生态环境优越，
					聚集自然景区、高端酒店、民俗打卡点、网红游玩地，
					非常适合度假、家庭出行、朋友聚会、商务休闲。
				</text>
			</view>

			<!-- 周边景点列表 -->
			<view class="spot-list">
				<!-- 1 北斗湾·贵州饭店 -->
				<view class="spot-item">
					<image src="/static/liuyueliu/fandian.jpg" mode="aspectFill" class="spot-img"></image>
					<view class="spot-info">
						<text class="spot-title">北斗湾·贵州饭店</text>
						<text class="spot-desc">
							贵安新区高端星级酒店，集住宿、餐饮、会议、休闲于一体，环境雅致，服务优质，是商务出行与度假首选。
						</text>
						<text class="spot-tag">高端酒店 · 近营地</text>
					</view>
				</view>

				<!-- 2 月亮湖景区 -->
				<view class="spot-item">
					<image src="/static/liuyueliu/ylh.webp" mode="aspectFill" class="spot-img"></image>
					<view class="spot-info">
						<text class="spot-title">月亮湖景区</text>
						<text class="spot-desc">
							贵安新区原生态自然湖泊，湖水清澈碧绿，环湖步道风景优美，适合散步、露营、拍照打卡。
						</text>
						<text class="spot-tag">自然景区 · 10分钟车程</text>
					</view>
				</view>

				<!-- 3 北斗湖湿地公园 -->
				<view class="spot-item">
					<image src="/static/liuyueliu/bdh.webp" mode="aspectFill" class="spot-img"></image>
					<view class="spot-info">
						<text class="spot-title">北斗湖湿地公园</text>
						<text class="spot-desc">
							原生态湿地景观，绿植茂密、空气清新，是天然氧吧，适合休闲漫步、亲子游玩、放松身心。
						</text>
						<text class="spot-tag">湿地公园 · 自然氧吧</text>
					</view>
				</view>

				<!-- 4 贵安樱花园 -->
				<view class="spot-item">
					<image src="/static/liuyueliu/gayhy.jpg" mode="aspectFill" class="spot-img"></image>
					<view class="spot-info">
						<text class="spot-title">贵安樱花园</text>
						<text class="spot-desc">
							世界知名樱花园，万亩樱花绽放如海，春季网红必打卡地，风景如画，出片率极高。
						</text>
						<text class="spot-tag">春季网红 · 花海景区</text>
					</view>
				</view>

				<!-- 5 平寨古镇 -->
				<view class="spot-item">
					<image src="/static/liuyueliu/pzgz.webp" mode="aspectFill" class="spot-img"></image>
					<view class="spot-info">
						<text class="spot-title">平寨古镇</text>
						<text class="spot-desc">
							贵州特色民俗村寨，古朴建筑、民族风情浓厚，体验原生态乡村文化与慢生活。
						</text>
						<text class="spot-tag">民俗文化 · 短途打卡</text>
					</view>
				</view>
			</view>

			<!-- 总结 -->
			<view class="summary-card">
				<text class="summary-title">周边游玩亮点</text>
				<view class="tag-group">
					<text class="tag">景区集中</text>
					<text class="tag">车程近</text>
					<text class="tag">高端住宿</text>
					<text class="tag">自然风景</text>
					<text class="tag">四季可玩</text>
				</view>
			</view>

			<!-- 底部标语 -->
			<view class="footer-slogan">
				<text>来六月六星野营地</text>
				<text>住田园 · 玩周边 · 享慢生活</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: "SurroundPage",
		data() {
			return {};
		}
	};
</script>

<style lang="scss" scoped>
	.surround-page {
		width: 100%;
		min-height: 100vh;
		background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
		padding-bottom: 40rpx;
	}

	/* 顶部banner */
	.top-banner {
		width: 100%;
		height: 440rpx;
		position: relative;

		.banner-img {
			width: 100%;
			height: 100%;
		}

		.banner-title {
			position: absolute;
			bottom: 44rpx;
			left: 30rpx;
			color: #FFF8F0;

			.main-title {
				font-size: 46rpx;
				font-weight: 800;
				letter-spacing: 2rpx;
				text-shadow: 0 3rpx 12rpx rgba(80, 35, 10, 0.45);
			}

			.sub-title {
				font-size: 26rpx;
				margin-top: 12rpx;
				opacity: 0.95;
			}
		}
	}

	/* 内容 */
	.content {
		padding: 30rpx;
	}

	/* 标题 */
	.section-title {
		display: flex;
		align-items: center;
		margin-bottom: 26rpx;

		.line {
			width: 12rpx;
			height: 38rpx;
			background: linear-gradient(180deg, #E8783B 0%, #F4A460 100%);
			border-radius: 8rpx;
			margin-right: 16rpx;
			box-shadow: 0 0 0 6rpx rgba(232, 120, 59, 0.1);
		}

		.title {
			font-size: 36rpx;
			font-weight: 800;
			color: #5D3A1A;
		}
	}

	/* 描述卡片 */
	.desc-card {
		background: #FFFDF9;
		border-radius: 32rpx;
		padding: 32rpx 30rpx;
		margin-bottom: 30rpx;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);

		.text {
			font-size: 28rpx;
			color: #8B6B4A;
			line-height: 1.75;
		}
	}

	/* 景点列表 */
	.spot-list {
		margin-bottom: 30rpx;
	}

	.spot-item {
		background: #FFFFFF;
		border-radius: 32rpx;
		overflow: hidden;
		margin-bottom: 22rpx;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
		transition: transform 0.25s, box-shadow 0.25s;
		&:active {
			transform: scale(0.98);
			box-shadow: 0 10rpx 32rpx rgba(200,130,60,0.14);
		}

		.spot-img {
			width: 100%;
			height: 300rpx;
		}

		.spot-info {
			padding: 28rpx;

			.spot-title {
				font-size: 33rpx;
				font-weight: 800;
				color: #5D3A1A;
				margin-bottom: 12rpx;
			}

			.spot-desc {
				font-size: 26rpx;
				color: #8B6B4A;
				line-height: 1.65;
				margin-bottom: 14rpx;
			}

			.spot-tag {
				font-size: 24rpx;
				font-weight: 600;
				color: #E8783B;
				background: #FFF0E5;
				padding: 8rpx 18rpx;
				border-radius: 20rpx;
				display: inline-block;
				box-shadow: 0 2rpx 8rpx rgba(232,120,59,0.1);
			}
		}
	}

	/* 总结卡片 */
	.summary-card {
		background: linear-gradient(135deg, #FFF8F0 0%, #FFF3E5 100%);
		border-radius: 32rpx;
		padding: 32rpx 30rpx;
		margin-bottom: 30rpx;
		box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
		border: 1rpx solid rgba(255, 180, 120, 0.1);

		.summary-title {
			font-size: 32rpx;
			font-weight: 800;
			color: #5D3A1A;
			margin-bottom: 22rpx;
		}

		.tag-group {
			display: flex;
			flex-wrap: wrap;
			gap: 16rpx;

			.tag {
				background: #FFFFFF;
				color: #E8783B;
				font-size: 26rpx;
				font-weight: 600;
				padding: 12rpx 24rpx;
				border-radius: 30rpx;
				box-shadow: 0 3rpx 12rpx rgba(200,130,60,0.08);
			}
		}
	}

	/* 底部 */
	.footer-slogan {
		text-align: center;
		padding: 50rpx 0;

		text {
			display: block;
			font-size: 28rpx;
			color: #8B6B4A;
			margin-bottom: 10rpx;

			&:last-child {
				font-size: 32rpx;
				color: #E8783B;
				font-weight: 800;
			}
		}
	}
</style>
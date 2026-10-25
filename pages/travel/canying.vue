<template>
  <view class="zone-page">
    <!-- 顶部封面 -->
    <view class="top-banner">
      <image 
        src="/static/liuyueliu/xingyewaijing/159A0599.JPG" 
        mode="aspectFill" 
        class="banner-img"
      ></image>
      <view class="banner-title">
        <text class="main-title">六月六星野营地</text>
        <text class="sub-title">九大特色体验区域 · 一站式度假</text>
      </view>
    </view>

    <view class="content">
      <view class="section-title">
        <view class="line"></view>
        <text class="title">营地全业态体验</text>
      </view>

      <view class="desc-card">
        <text class="text">
          六月六星野营地坐落于贵安新区原生态田园之间，融合户外露营、特色餐饮、民俗文化、康养休闲、田园采摘、农产品展销，吃喝玩乐康养一站式体验，适合家庭出游、朋友聚会、团建度假。
        </text>
      </view>

      <!-- 九大区域列表 -->
      <view class="zone-list">
        <!-- 1 露营区 -->
        <view class="zone-item">
          <image src="/static/liuyueliu/sk.jpg" mode="aspectFill" class="zone-img"></image>
          <view class="zone-info">
            <text class="zone-title">🏕️ 露营区域</text>
            <text class="zone-desc">
              户外轻奢露营基地，配备天幕、帐篷、桌椅，可自助烧烤、星空夜话、篝火团建，沉浸式感受山野晚风与田园星空。
            </text>
            <text class="zone-tag">户外休闲｜团建聚会｜星空露营</text>
          </view>
        </view>
		 <!--2- 酒馆 -->
		 <view class="zone-item">
		   <image src="/static/liuyueliu/jg.jpg" mode="aspectFill" class="zone-img"></image>
		   <view class="zone-info">
		     <text class="zone-title">🍻 星空营地酒馆</text>
		     <text class="zone-desc">
		       山野微醺小酒馆，精酿啤酒、果酒、鸡尾酒、气泡饮齐全，搭配特色小吃、下酒小菜。
		       		      夜晚星空作伴，晚风轻拂，喝酒聊天，享受城市之外的松弛夜晚。
		     </text>
		     <text class="zone-tag">户外休闲｜团建聚会｜星空露营</text>
		   </view>
		 </view>

        <!-- 3 柴火鸡馆 -->
        <view class="zone-item">
          <image src="/static/liuyueliu/chj.webp" mode="aspectFill" class="zone-img"></image>
          <view class="zone-info">
            <text class="zone-title">🍗 柴火鸡馆</text>
            <text class="zone-desc">
              地道贵州农家柴火鸡，土鸡现炒、柴火铁锅焖煮，搭配玉米粑粑、时令配菜，乡土烟火气十足，多人聚餐首选。
            </text>
            <text class="zone-tag">农家美食｜多人套餐｜乡土风味</text>
          </view>
        </view>

        <!-- 4 酸汤鱼馆 -->
        <view class="zone-item">
          <image src="/static/liuyueliu/sty.jpg" mode="aspectFill" class="zone-img"></image>
          <view class="zone-info">
            <text class="zone-title">🐟 酸汤鱼馆</text>
            <text class="zone-desc">
              贵州特色酸汤鱼，古法发酵酸汤，鲜鱼现煮，酸辣开胃，地道黔味，适合家庭聚餐、朋友小聚。
            </text>
            <text class="zone-tag">黔味特色｜酸辣开胃｜特色餐饮</text>
          </view>
        </view>

        <!-- 5 文化馆 -->
        <view class="zone-item">
          <image src="/static/liuyueliu/yishuguan/159A0533.JPG" mode="aspectFill" class="zone-img"></image>
          <view class="zone-info">
            <text class="zone-title">📜 民俗文化馆</text>
            <text class="zone-desc">
              展示贵安本地民俗文化、非遗手作、六月六民族文化，可体验民俗手工、文化打卡，感受民族风情。
            </text>
            <text class="zone-tag">民族文化｜非遗体验｜文化打卡</text>
          </view>
        </view>

        <!-- 6 禅拍养生馆 -->
        <view class="zone-item">
          <image src="/static/liuyueliu/ysg.webp" mode="aspectFill" class="zone-img"></image>
          <view class="zone-info">
            <text class="zone-title">🧘 禅拍养生馆</text>
            <text class="zone-desc">
              禅意康养空间，身心禅拍疗愈，释放压力、舒缓情绪，在山野田园间静心放松，疗愈身心。
            </text>
            <text class="zone-tag">身心疗愈｜禅意放松｜解压康养</text>
          </view>
        </view>

        <!-- 7 养生足疗馆 -->
        <view class="zone-item">
          <image src="/static/liuyueliu/zlg.webp" mode="aspectFill" class="zone-img"></image>
          <view class="zone-info">
            <text class="zone-title">🦶 养生足疗馆</text>
            <text class="zone-desc">
              专业养生足疗、肩颈放松、经络舒缓，游玩之余放松身体，缓解疲惫，度假休闲必备。
            </text>
            <text class="zone-tag">康养放松｜经络养生｜休闲解压</text>
          </view>
        </view>

        <!-- 8 生态农场区域 -->
        <view class="zone-item">
          <image src="/static/liuyueliu/ncp.jpg" mode="aspectFill" class="zone-img"></image>
          <view class="zone-info">
            <text class="zone-title">🌱 生态农场区域</text>
            <text class="zone-desc">
              原生态农田菜地，绿色蔬菜、农作物种植，可农耕体验、亲子研学，感受田园劳作乐趣。
            </text>
            <text class="zone-tag">农耕体验｜亲子研学｜绿色种植</text>
          </view>
        </view>

        <!-- 9 生态果园区域 -->
        <view class="zone-item">
          <image src="/static/liuyueliu/2.jpg" mode="aspectFill" class="zone-img"></image>
          <view class="zone-info">
            <text class="zone-title">🍎 生态果园区域</text>
            <text class="zone-desc">
              四季生态果园，桃子、李子、葡萄、柑橘等应季鲜果，可入园采摘，体验田园采摘乐趣。
            </text>
            <text class="zone-tag">鲜果采摘｜亲子游玩｜四季可玩</text>
          </view>
        </view>

        <!-- 10 农产品区域 -->
        <view class="zone-item">
          <image src="/static/liuyueliu/ncpqy.jpg" mode="aspectFill" class="zone-img"></image>
          <view class="zone-info">
            <text class="zone-title">🌾 农产品展销区</text>
            <text class="zone-desc">
              贵安本地原生态农产品、农家干货、果蔬特产，绿色天然，可选购伴手礼，带走田园风味。
            </text>
            <text class="zone-tag">农家特产｜绿色天然｜伴手礼选购</text>
          </view>
        </view>
      </view>

      <!-- 总结标签 -->
      <view class="summary-card">
        <text class="summary-title">营地游玩亮点</text>
        <view class="tag-group">
          <text class="tag">户外露营</text>
          <text class="tag">特色美食</text>
          <text class="tag">文化体验</text>
          <text class="tag">康养休闲</text>
          <text class="tag">田园采摘</text>
          <text class="tag">一站式度假</text>
        </view>
      </view>

      <!-- 底部标语 -->
      <view class="footer-slogan">
        <text>来六月六星野营地</text>
        <text>吃喝玩乐康养采摘 · 一站式田园度假</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: "ZonePage",
  data() {
    return {};
  },
};
</script>

<style lang="scss" scoped>
.zone-page {
  width: 100%;
  min-height: 100vh;
  background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
  padding-bottom: 40rpx;
}

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

.content {
  padding: 30rpx;
}

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

.zone-list {
  margin-bottom: 30rpx;
}

.zone-item {
  background: #FFFFFF;
  border-radius: 32rpx;
  overflow: hidden;
  margin-bottom: 22rpx;
  box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
  transition: transform 0.25s, box-shadow 0.25s;
  &:active {
    transform: scale(0.98);
    box-shadow: 0 10rpx 32rpx rgba(200, 130, 60, 0.14);
  }
  .zone-img {
    width: 100%;
    height: 300rpx;
  }
  .zone-info {
    padding: 28rpx;
    .zone-title {
      font-size: 33rpx;
      font-weight: 800;
      color: #5D3A1A;
      margin-bottom: 12rpx;
    }
    .zone-desc {
      font-size: 26rpx;
      color: #8B6B4A;
      line-height: 1.65;
      margin-bottom: 14rpx;
    }
    .zone-tag {
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
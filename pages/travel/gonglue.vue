<template>
  <view class="strategy-page">
    <!-- 头部封面 -->
    <view class="top-banner">
      <image 
        src="/static/liuyueliu/xingyewaijing/159A0600.JPG" 
        mode="aspectFill"
        class="banner-img"
      ></image>
      <view class="banner-mask">
        <text class="title">六月六星野营地游玩攻略</text>
        <text class="subtitle">贵安新区 · 田园露营 · 布依风情</text>
      </view>
    </view>

    <!-- 基础信息 -->
    <view class="section">
      <view class="section-title">
        <text class="dot"></text>
        <text>基础信息</text>
      </view>
      <view class="info-list">
        <view class="info-item">
          <text class="icon">📍</text>
          <text class="text">贵阳市贵安新区马场镇六月六星野营地</text>
        </view>
        <view class="info-item">
          <text class="icon">⏰</text>
          <text class="text">最佳游玩时间：4–10月，春夏露营避暑、秋日看田园风光</text>
        </view>
        <view class="info-item">
          <text class="icon">💰</text>
          <text class="text">门票：营地入园免费，露营、天幕、游乐项目单独收费</text>
        </view>
        <view class="info-item">
          <text class="icon">🚗</text>
          <text class="text">贵阳市区自驾约1–1.5小时，路况良好</text>
        </view>
      </view>
    </view>

    <!-- 交通指南 -->
    <view class="section">
      <view class="section-title">
        <text class="dot"></text>
        <text>交通指南</text>
      </view>
      <view class="traffic-item">
        <text class="label">🚙 自驾（最推荐）</text>
        <text class="desc">导航直接搜：六月六星野营地，园区有免费停车场，停车方便。</text>
      </view>
      <view class="traffic-item">
        <text class="label">🚌 公共交通</text>
        <text class="desc">贵阳北站/金阳客车站 → 乘坐前往马场镇大巴 → 打车/拼车约10分钟直达营地（公共交通不便，优先自驾）</text>
      </view>
    </view>

    <!-- 必玩项目 -->
    <view class="section">
      <view class="section-title">
        <text class="dot"></text>
        <text>必玩项目 & 游玩路线</text>
      </view>

      <image src="/static/liuyueliu/xingyewaijing/159A0588.JPG" mode="aspectFill" class="section-img"></image>

      <view class="play-item">
        <text class="play-title">🏕️ 露营野餐</text>
        <text class="play-desc">自带装备可免费扎营；营地可租赁天幕、帐篷、桌椅，直面稻田/山野，傍晚看日落氛围感拉满。</text>
      </view>
      <view class="play-item">
        <text class="play-title">🌾 田园风光打卡</text>
        <text class="play-desc">大片稻田、花海、溪流栈道，适合拍日系田园风、森系照片，稻田秋千、网红打卡点必拍。</text>
      </view>
      <view class="play-item">
        <text class="play-title">👨‍👩‍👧‍👦 亲子轻游乐</text>
        <text class="play-desc">儿童沙池、小火车、萌宠投喂、稻田抓鱼，小朋友可以沉浸式体验田园乐趣。</text>
      </view>
      <view class="play-item">
        <text class="play-title">🪕 民族风情体验</text>
        <text class="play-desc">营地主打布依族“六月六”民俗文化，节假日会有布依族歌舞、篝火晚会、民俗市集。</text>
      </view>
      <view class="play-item">
        <text class="play-title">🌿 户外休闲</text>
        <text class="play-desc">草坪飞盘、桌游、骑行、溪边玩水，适合放松发呆。</text>
      </view>

      <!-- 一日游路线 -->
      <view class="route-box">
        <text class="route-title">📝 一日游路线（轻松版）</text>
        <view class="route-item"><text>10:00</text><text>抵达营地，打卡网红田园点位</text></view>
        <view class="route-item"><text>11:30</text><text>野餐 / 营地简餐</text></view>
        <view class="route-item"><text>13:00</text><text>露营休息、溪边玩水、亲子游乐</text></view>
        <view class="route-item"><text>16:00</text><text>稻田散步、拍日落大片</text></view>
        <view class="route-item"><text>18:00</text><text>烧烤晚餐，节假日可参加篝火晚会</text></view>
        <view class="route-item"><text>20:00</text><text>返程 / 过夜露营</text></view>
      </view>
    </view>

    <!-- 美食推荐 -->
    <view class="section">
      <view class="section-title">
        <text class="dot"></text>
        <text>美食推荐</text>
      </view>
      <image src="/static/liuyueliu/xingyewaijing/159A0593.JPG" mode="aspectFill" class="section-img"></image>
      <view class="food-item">
        <text>🔥 营地烧烤</text>
        <text>可自带食材，也可在营地租赁烤架+采购食材，烧烤配啤酒氛围感十足。</text>
      </view>
      <view class="food-item">
        <text>🥘 本地农家菜</text>
        <text>布依辣子鸡、稻田鱼、农家腊肉、野菜、糯米饭</text>
      </view>
      <view class="food-item">
        <text>🧺 自带野餐</text>
        <text>蛋糕、水果、零食，搭配田园风景拍照超出片。</text>
      </view>
    </view>

    <!-- 住宿 -->
    <view class="section">
      <view class="section-title">
        <text class="dot"></text>
        <text>住宿选择</text>
      </view>
      <view class="stay-item">
        <text>🏕️ 过夜露营</text>
        <text>自带帐篷，营地有卫生间、洗漱区；也可租营地帐篷，配套完善。</text>
      </view>
      <view class="stay-item">
        <text>🏡 周边民宿</text>
        <text>马场镇有田园民宿、农家客栈，性价比高；也可当天往返贵阳市区。</text>
      </view>
    </view>

    <!-- 注意事项 -->
    <view class="section warn-section">
      <view class="section-title">
        <text class="dot"></text>
        <text>实用注意事项 & 避坑指南</text>
      </view>
      <view class="warn-item">👗 穿搭：浅色系衣服最出片；做好防晒、驱蚊，稻田蚊虫较多。</view>
      <view class="warn-item">🎒 装备：防晒帽、墨镜、驱蚊液、湿巾、充电宝、溯溪鞋（玩水）。</view>
      <view class="warn-item">⚠️ 避坑：节假日提前预约天幕/露营装备；不踩踏庄稼；垃圾全部带走。</view>
      <view class="warn-item">☔ 季节：夏季多雨，备好雨具；秋日稻田金黄，拍照最佳。</view>
    </view>

    <!-- 底部留白 -->
    <view style="height: 40rpx"></view>
  </view>
</template>

<script>
export default {
  data() {
    return {}
  }
}
</script>

<style scoped>
.strategy-page {
  background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
  min-height: 100vh;
}

/* 头部封面 */
.top-banner {
  width: 100%;
  height: 420rpx;
  position: relative;
}
.banner-img {
  width: 100%;
  height: 100%;
}
.banner-mask {
  position: absolute;
  left: 0;
  bottom: 0;
  right: 0;
  padding: 44rpx 30rpx;
  background: linear-gradient(transparent, rgba(80, 35, 10, 0.72));
  color: #FFF8F0;
}
.title {
  font-size: 42rpx;
  font-weight: 800;
  display: block;
  margin-bottom: 10rpx;
  letter-spacing: 2rpx;
}
.subtitle {
  font-size: 26rpx;
  opacity: 0.92;
}

/* 模块 */
.section {
  background: #FFFFFF;
  margin: 22rpx;
  border-radius: 36rpx;
  padding: 32rpx 30rpx;
  box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
}
.section-title {
  font-size: 32rpx;
  font-weight: 800;
  color: #5D3A1A;
  margin-bottom: 26rpx;
  display: flex;
  align-items: center;
}
.dot {
  width: 14rpx;
  height: 14rpx;
  background: #E8783B;
  border-radius: 50%;
  margin-right: 16rpx;
  box-shadow: 0 0 0 6rpx rgba(232, 120, 59, 0.12);
}

/* 基础信息 */
.info-list {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}
.info-item {
  display: flex;
  align-items: flex-start;
  font-size: 28rpx;
  color: #5D3A1A;
  line-height: 1.55;
  background: #FFFDF9;
  padding: 18rpx 22rpx;
  border-radius: 20rpx;
}
.icon {
  margin-right: 16rpx;
  font-size: 32rpx;
}
.text { color: #5D3A1A; }

/* 交通 */
.traffic-item {
  margin-bottom: 22rpx;
  background: #FFFDF9;
  padding: 22rpx 24rpx;
  border-radius: 24rpx;
}
.label {
  font-size: 29rpx;
  font-weight: 700;
  color: #5D3A1A;
  display: block;
  margin-bottom: 10rpx;
}
.desc {
  font-size: 26rpx;
  color: #8B6B4A;
  line-height: 1.55;
}

/* 图片 */
.section-img {
  width: 100%;
  height: 340rpx;
  border-radius: 24rpx;
  margin-bottom: 26rpx;
}

/* 必玩项目 */
.play-item {
  margin-bottom: 22rpx;
  background: #FFFDF9;
  padding: 20rpx 22rpx;
  border-radius: 22rpx;
}
.play-title {
  font-size: 29rpx;
  font-weight: 700;
  color: #5D3A1A;
  display: block;
  margin-bottom: 8rpx;
}
.play-desc {
  font-size: 26rpx;
  color: #8B6B4A;
  line-height: 1.55;
}

/* 路线 */
.route-box {
  background: #FFF5EC;
  padding: 26rpx 28rpx;
  border-radius: 28rpx;
  margin-top: 20rpx;
  border: 1rpx solid rgba(232, 120, 59, 0.1);
}
.route-title {
  font-size: 29rpx;
  font-weight: 800;
  color: #E8783B;
  margin-bottom: 18rpx;
  display: block;
}
.route-item {
  display: flex;
  align-items: center;
  margin-bottom: 14rpx;
  font-size: 26rpx;
  gap: 16rpx;
}
.route-item text:first-child {
  min-width: 100rpx;
  color: #E8783B;
  font-weight: 700;
  background: rgba(232, 120, 59, 0.08);
  padding: 6rpx 16rpx;
  border-radius: 14rpx;
  text-align: center;
}
.route-item text:last-child {
  flex: 1;
  color: #8B6B4A;
}

/* 美食 */
.food-item {
  font-size: 26rpx;
  line-height: 1.6;
  margin-bottom: 18rpx;
  background: #FFFDF9;
  padding: 18rpx 22rpx;
  border-radius: 20rpx;
}
.food-item text:first-child {
  font-weight: 700;
  color: #5D3A1A;
  margin-right: 10rpx;
}

/* 住宿 */
.stay-item {
  font-size: 26rpx;
  line-height: 1.6;
  margin-bottom: 18rpx;
  color: #8B6B4A;
  background: #FFFDF9;
  padding: 18rpx 22rpx;
  border-radius: 20rpx;
}

/* 注意事项 */
.warn-section {
  background: #FFF5EC;
  border: 2rpx solid rgba(232, 120, 59, 0.12);
}
.warn-item {
  font-size: 26rpx;
  color: #8B6B4A;
  line-height: 1.65;
  margin-bottom: 16rpx;
  background: rgba(255,255,255,0.6);
  padding: 16rpx 20rpx;
  border-radius: 18rpx;
}
</style>
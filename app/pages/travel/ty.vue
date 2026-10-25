<template>
  <view class="scenery-page">
    <!-- 顶部大图 -->
    <view class="top-banner">
      <image 
        src="/static/liuyueliu/xingyewaijing/159A0587.JPG" 
        mode="aspectFill"
        class="banner-img"
      ></image>
      <view class="banner-title">
        <text class="main-title">六月六星野营地</text>
        <text class="sub-title">贵阳市贵安新区 · 田园果园风光</text>
      </view>
    </view>

    <!-- 内容区域 -->
    <view class="content">
      <!-- 标题 -->
      <view class="section-title">
        <view class="line"></view>
        <text class="title">田园秘境 · 果园飘香</text>
      </view>

      <!-- 描述卡片 -->
      <view class="desc-card">
        <text class="text">
          六月六星野营地被原生态田园环抱，成片田地绿意连绵，果园飘香四溢。
          在这里，你能看见风吹稻浪、漫步田埂、采摘鲜果，感受最治愈的乡村田园生活。
        </text>
      </view>

      <!-- 两大核心风景：田地 + 果园 -->
      <view class="feature-list">
        <view class="feature-item">
          <view class="icon">🌾</view>
          <view class="info">
            <text class="title">原生态田地</text>
            <text class="text">
              成片绿油油的田地一望无际，稻浪随风起伏，菜畦整齐错落，
              充满自然野趣与乡村气息，一步一景皆是治愈。
            </text>
          </view>
        </view>

        <view class="feature-item">
          <view class="icon">🍎</view>
          <view class="info">
            <text class="title">生态果园</text>
            <text class="text">
              营地周边果树成林，四季鲜果飘香，空气清新甘甜。
              可漫步果园、体验采摘，享受田园里的自然乐趣。
            </text>
          </view>
        </view>
      </view>

      <!-- 风景亮点 -->
      <view class="points">
        <view class="point-item">
          <text class="point-icon">🌾</text>
          <text class="point-text">稻浪田地 · 绿意盎然</text>
        </view>
        <view class="point-item">
          <text class="point-icon">🍑</text>
          <text class="point-text">生态果园 · 鲜果飘香</text>
        </view>
        <view class="point-item">
          <text class="point-icon">🌤️</text>
          <text class="point-text">蓝天白云 · 空气清新</text>
        </view>
        <view class="point-item">
          <text class="point-icon">🌿</text>
          <text class="point-text">田园野趣 · 治愈放松</text>
        </view>
      </view>

      <!-- 图片展示 -->
      <view class="image-grid">
        <view class="grid-item">
          <image src="/static/liuyueliu/1.jpg" mode="aspectFill"></image>
        </view>
        <view class="grid-item">
          <image src="/static/liuyueliu/2.jpg" mode="aspectFill"></image>
        </view>
        <view class="grid-item">
          <image src="/static/liuyueliu/3.jpg" mode="aspectFill"></image>
        </view>
        <view class="grid-item">
          <image src="/static/liuyueliu/xingyeyejing/159A0688.JPG" mode="aspectFill"></image>
        </view>
      </view>

      <!-- 详细介绍 -->
      <view class="detail-section">
        <text class="detail-title">田园意境</text>
        <text class="detail-text">
          清晨薄雾漫过田地，午后阳光洒满果园，傍晚晚霞映照整片田园。
          在这里，你可以漫步田埂、穿梭果园、静享时光，感受贵安新区最纯粹的自然之美。
        </text>
        <text class="detail-text">
          没有喧嚣，没有纷扰，只有风吹稻浪、果香弥漫、虫鸣鸟叫，
          是城市人向往的田园后花园，也是治愈心灵的秘境之地。
        </text>
      </view>

      <!-- 底部标语 -->
      <view class="footer-slogan">
        <text>来六月六星野营地</text>
        <text>赏田园风光 · 品果园清香</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: "SceneryPage",
  data() {
    return {};
  },
};
</script>

<style lang="scss" scoped>
.scenery-page {
  width: 100%;
  min-height: 100vh;
  background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
  padding-bottom: 40rpx;
}

/* 顶部大图 */
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

/* 章节标题 */
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

/* 核心特色 */
.feature-list {
  margin-bottom: 30rpx;
  .feature-item {
    background: #FFFFFF;
    border-radius: 32rpx;
    padding: 28rpx;
    margin-bottom: 22rpx;
    display: flex;
    align-items: flex-start;
    box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
    transition: transform 0.25s;
    &:active {
      transform: scale(0.98);
    }
    .icon {
      font-size: 52rpx;
      margin-right: 24rpx;
      padding-top: 4rpx;
    }
    .info {
      flex: 1;
      .title {
        font-size: 33rpx;
        font-weight: 800;
        color: #5D3A1A;
        margin-bottom: 10rpx;
      }
      .text {
        font-size: 26rpx;
        color: #8B6B4A;
        line-height: 1.65;
      }
    }
  }
}

/* 亮点 */
.points {
  background: #FFFFFF;
  border-radius: 32rpx;
  padding: 22rpx 30rpx;
  margin-bottom: 30rpx;
  box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
  .point-item {
    display: flex;
    align-items: center;
    padding: 18rpx 0;
    border-bottom: 1rpx solid rgba(200, 140, 80, 0.08);
    &:last-child {
      border-bottom: none;
    }
    .point-icon {
      font-size: 34rpx;
      margin-right: 18rpx;
    }
    .point-text {
      font-size: 28rpx;
      color: #5D3A1A;
      font-weight: 600;
    }
  }
}

/* 图片宫格 */
.image-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 20rpx;
  margin-bottom: 30rpx;
  .grid-item {
    width: calc(50% - 10rpx);
    height: 250rpx;
    border-radius: 24rpx;
    overflow: hidden;
    box-shadow: 0 4rpx 16rpx rgba(200, 130, 60, 0.08);
    image {
      width: 100%;
      height: 100%;
    }
  }
}

/* 详情 */
.detail-section {
  background: #FFFFFF;
  border-radius: 32rpx;
  padding: 32rpx 30rpx;
  box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
  .detail-title {
    font-size: 33rpx;
    font-weight: 800;
    color: #5D3A1A;
    margin-bottom: 22rpx;
  }
  .detail-text {
    font-size: 28rpx;
    color: #8B6B4A;
    line-height: 1.8;
    margin-bottom: 20rpx;
  }
}

/* 底部标语 */
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
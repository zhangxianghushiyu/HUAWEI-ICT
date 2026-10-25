<template>
  <view class="goods-page">
    <!-- 顶部封面 -->
    <view class="top-banner">
      <image 
        src="/static/liuyueliu/xingyewaijing/159A0588.JPG" 
        mode="aspectFill" 
        class="banner-img"
      ></image>
      <view class="banner-title">
        <text class="main-title">营地商品商城</text>
        <text class="sub-title">美酒｜农产品｜水果｜贵州特产</text>
      </view>
    </view>

    <view class="content">
      <view class="section-title">
        <view class="line"></view>
        <text class="title">营地精选商品</text>
      </view>

      <!-- 商品列表 -->
      <view class="goods-list">

        <!-- ====================== 酒 ====================== -->
        <view class="goods-item">
          <image src="/static/liuyueliu/jg.jpg" mode="aspectFill" class="goods-img"></image>
          <view class="goods-info">
            <text class="goods-title">🍷 精酿啤酒 / 果酒套装</text>
            <text class="goods-desc">
              营地酒馆同款精酿、果味酒、低度微醺，露营聚会必备
            </text>
            <view class="price-row">
              <text class="price">¥68</text>
              <text class="original">¥98</text>
            </view>
            <text class="goods-tag">酒类｜果酒｜精酿｜露营小酌</text>
          </view>
        </view>



        <view class="goods-item">
          <image src="/static/liuyueliu/ncpqy.jpg" mode="aspectFill" class="goods-img"></image>
          <view class="goods-info">
            <text class="goods-title">🌾 农家干货特产</text>
            <text class="goods-desc">
              香菇、木耳、腐竹、土鸡蛋、杂粮，贵州山区原生态干货
            </text>
            <view class="price-row">
              <text class="price">¥49.9</text>
              <text class="original">¥79.9</text>
            </view>
            <text class="goods-tag">农产品｜干货｜农家自产</text>
          </view>
        </view>

        <!-- ====================== 水果 ====================== -->
        <view class="goods-item">
          <image src="/static/liuyueliu/2.jpg" mode="aspectFill" class="goods-img"></image>
          <view class="goods-info">
            <text class="goods-title">🍑 应季鲜采水果</text>
            <text class="goods-desc">
              桃子、李子、葡萄、柑橘，果园现摘，香甜多汁
            </text>
            <view class="price-row">
              <text class="price">¥49.9</text>
              <text class="original">¥69.9</text>
            </view>
            <text class="goods-tag">水果｜现摘｜生态果园</text>
          </view>
        </view>

        <!-- ====================== 贵州特产 ====================== -->
        <view class="goods-item">
          <image src="/static/liuyueliu/sty.jpg" mode="aspectFill" class="goods-img"></image>
          <view class="goods-info">
            <text class="goods-title">🍅 贵州红酸汤底料</text>
            <text class="goods-desc">
              贵州非遗酸汤鱼底料，古法发酵，酸辣开胃，家用商用
            </text>
            <view class="price-row">
              <text class="price">¥29.9</text>
              <text class="original">¥45</text>
            </view>
            <text class="goods-tag">贵州特产｜酸汤｜火锅底料</text>
          </view>
        </view>

        <view class="goods-item">
          <image src="/static/liuyueliu/ljm.webp" mode="aspectFill" class="goods-img"></image>
          <view class="goods-info">
            <text class="goods-title">🌶️ 贵州辣椒调料套装</text>
            <text class="goods-desc">
              糊辣椒、辣椒面、木姜子、豆豉，地道贵州味
            </text>
            <view class="price-row">
              <text class="price">¥35</text>
              <text class="original">¥55</text>
            </view>
            <text class="goods-tag">贵州特产｜辣椒｜调味</text>
          </view>
        </view>

      </view>

      <!-- 底部标语 -->
      <view class="footer-slogan">
        <text>六月六星野营地 · 官方商城</text>
        <text>生态好物 · 贵州特产 · 一站式选购</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: "GoodsPage",
  data() {
    return {};
  },
};
</script>

<style lang="scss" scoped>
.goods-page {
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

.goods-list {
  margin-bottom: 30rpx;
}

.goods-item {
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
  .goods-img {
    width: 100%;
    height: 300rpx;
  }
  .goods-info {
    padding: 28rpx;
    .goods-title {
      font-size: 33rpx;
      font-weight: 800;
      color: #5D3A1A;
      margin-bottom: 12rpx;
    }
    .goods-desc {
      font-size: 26rpx;
      color: #8B6B4A;
      line-height: 1.65;
      margin-bottom: 14rpx;
    }
    .price-row {
      display: flex;
      align-items: center;
      gap: 20rpx;
      margin-bottom: 14rpx;
      .price {
        font-size: 36rpx;
        color: #E8783B;
        font-weight: 800;
      }
      .original {
        font-size: 24rpx;
        color: #B8977A;
        text-decoration: line-through;
      }
    }
    .goods-tag {
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
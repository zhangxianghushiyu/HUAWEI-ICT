<template>
  <view class="container">
    <!-- 全部贴吧列表 -->
    <view class="list-wrapper">
      <view 
        class="item" 
        v-for="item in articleList" 
        :key="item.id"
        @click="toDetail(item)"
      >
        <image :src="item.articleImg || 'https://picsum.photos/300/200'" class="item-img" mode="aspectFill"></image>
        <view class="item-info">
          <text class="item-title">{{ item.articleTitle }}</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      articleList: [] // 全部贴吧列表
    }
  },
  onShow() {
    this.getAllList()
  },
  methods: {
    // 获取全部贴吧数据
    async getAllList() {
      const res = await this.$api.getArticlePage({
        pageNum: 1,
        pageSize: 100
      })
      this.articleList = res.data?.list || []
    },
    
    // 进入详情
    toDetail(item) {
      uni.setStorageSync('articleId', item.id)
      uni.navigateTo({
        url: '/pages/articel/articleDetail'
      })
    }
  }
}
</script>

<style scoped>
  .container {
    padding: 20rpx;
    background: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);
    min-height: 100vh;
  }

  .list-wrapper {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }

  .item {
    width: 48%;
    background: #FFFFFF;
    border-radius: 28rpx;
    overflow: hidden;
    margin-bottom: 20rpx;
    box-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);
    transition: transform 0.25s cubic-bezier(0.175, 0.885, 0.32, 1.275), box-shadow 0.25s;
  }

  .item:active {
    transform: scale(0.97);
    box-shadow: 0 10rpx 32rpx rgba(200, 130, 60, 0.14);
  }

  .item-img {
    width: 100%;
    height: 220rpx;
    display: block;
  }

  .item-info {
    padding: 15rpx;
  }

  .item-title {
    font-size: 28rpx;
    color: #5D3A1A;
    font-weight: 700;
    line-height: 1.4;
  }
</style>
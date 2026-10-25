<template>
  <view v-if="visible" class="global-toast">
    <text>{{ message }}</text>
  </view>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      message: ''
    }
  },
  methods: {
    showToast(msg) {
      this.message = msg
      this.visible = true
      setTimeout(() => {
        this.visible = false
      }, 2000)
    }
  }
}
</script>

<style scoped>
.global-toast {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.7);
  color: white;
  padding: 20rpx 40rpx;
  border-radius: 10rpx;
  z-index: 9999;
}
</style>